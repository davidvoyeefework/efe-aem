package com.efe.core.models.impl;

import java.util.*;
import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.*;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.search.*;
import com.day.cq.search.result.*;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.efe.core.models.RelatedArticleDynamic;

@Model(
    adaptables = SlingHttpServletRequest.class,
    adapters = RelatedArticleDynamic.class,
    resourceType = RelatedArticleDynamicImpl.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class RelatedArticleDynamicImpl implements RelatedArticleDynamic {

    // Constants 
    public static final String RESOURCE_TYPE = "efe/components/relatedarticle-dynamic";
    private static final String EDUCATION_ROOT = "/content/efe/us/en/education";
    private static final String ARTICLES_CF_ROOT = "/content/dam/efe/cf/corporate/articles";
    private static final String PLANNER_CF_ROOT = "/content/dam/efe/cf/plannerlocation/planners";
    private static final String TEASER_RT = "core/wcm/components/teaser/v1/teaser";
    private static final String STATE_TAG_PREFIX = "efe:content-type/location/us-state/";
    private static final String ARTICLE_DETAILS_NODE_NAME = "articledetails";
    private static final String ARTICLE_DETAILS_RESOURCE_TYPE = "efe/components/articledetails";
    private static final String ARTICLE_FRAGMENT_PROP = "articleFragmentPath";

    @Self
    private SlingHttpServletRequest request;

    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    private QueryBuilder queryBuilder;

    /** Dialog field: name="./maxItems" ; blank or 0 => unlimited */
    @ValueMapValue(name = "maxItems")
    private Integer maxItems;

    private List<String> relatedArticlePagePaths = Collections.emptyList();

    private final Map<String, String> heroImageByPagePath = new HashMap<>();

    private enum Mode {
        LOCATION,
        PLANNER
    }

    /* ===================== INIT ===================== */

    @PostConstruct
    protected void init() {
        if (resourceResolver == null || queryBuilder == null || request == null) {
            return;
        }

        Session session = resourceResolver.adaptTo(Session.class);
        if (session == null) return;

        // Determins if on location or planner page based on selectors, then runs the appropriate query to find related article pages.
        Mode mode = resolveMode();
        Integer limit = resolveLimitOrNull();

        if (mode == Mode.LOCATION) {
            String stateCode = resolveSelector(0);
            if (StringUtils.isBlank(stateCode)) return;

            String stateTagId = STATE_TAG_PREFIX + stateCode.toLowerCase(Locale.US);
            relatedArticlePagePaths = findEducationPagesByStateTag(stateTagId, session, limit);
            return;
        }

        // PLANNER mode:
        // 1) authored-by-planner articles first
        // 2) then state-based articles for planner primaryOffice state
        // 3) dedupe and apply maxItems at the end
        String plannerId = resolveSelector(2);
        if (StringUtils.isBlank(plannerId)) return;

        List<String> authored = findEducationPagesByPlanner(plannerId, session, null); 

        String primaryState = resolvePlannerPrimaryOfficeState(plannerId);
        List<String> stateBased = Collections.emptyList();
        if (StringUtils.isNotBlank(primaryState)) {
            String stateTagId = STATE_TAG_PREFIX + primaryState.toLowerCase(Locale.US);
            stateBased = findEducationPagesByStateTag(stateTagId, session, null); // don't limit yet; limit after merge
        }

        relatedArticlePagePaths = mergeAuthoredThenState(authored, stateBased, limit);
    }

    /* ===================== PUBLIC API ===================== */

    @Override
    public List<Resource> getRelatedTeasers() {
        if (resourceResolver == null || relatedArticlePagePaths == null || relatedArticlePagePaths.isEmpty()) {
            return Collections.emptyList();
        }

        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        if (pageManager == null) return Collections.emptyList();

        heroImageByPagePath.clear();

        List<Resource> teasers = new ArrayList<>();
        int i = 0;

        for (String path : relatedArticlePagePaths) {
            Page page = pageManager.getPage(path);
            if (page == null) continue;

            ValueMap props = buildTeaserProps(page);

            String fragmentPath = getArticleFragmentPathFromArticleDetails(page);
            String hero = getHeroImageFromContentFragment(fragmentPath);
            if (StringUtils.isNotBlank(hero)) {
                heroImageByPagePath.put(page.getPath(), hero);
            }

            teasers.add(new SyntheticResource(
                resourceResolver,
                path + "/_synthetic_" + (i++),
                TEASER_RT
            ) {
                @Override
                public ValueMap getValueMap() {
                    return props;
                }
            });
        }

        return teasers;
    }

    @Override
    public Map<String, String> getTeaserHeroImages() {
        return heroImageByPagePath;
    }

    @Override
    public List<String> getRelatedArticlePagePaths() {
        return relatedArticlePagePaths;
    }

    @Override
    public int getMaxItems() {
        Integer limit = resolveLimitOrNull();
        return limit != null ? limit : 0;
    }

    /* ===================== MODE ===================== */

    private Mode resolveMode() {
        // planner pages have 3 selectors: First.Last.ID
        String[] selectors = request.getRequestPathInfo() != null ? request.getRequestPathInfo().getSelectors() : null;
        if (selectors != null && selectors.length >= 3) return Mode.PLANNER;
        return Mode.LOCATION;
    }

    private String resolveSelector(int index) {
        if (request.getRequestPathInfo() == null) return null;
        String[] selectors = request.getRequestPathInfo().getSelectors();
        if (selectors != null && selectors.length > index) {
            return selectors[index];
        }
        return null;
    }

    private Integer resolveLimitOrNull() {
        return (maxItems == null || maxItems <= 0) ? null : maxItems;
    }

    /* ===================== LOCATION QUERY ===================== */

    private List<String> findEducationPagesByStateTag(String tagId, Session session, Integer limit) {
        Map<String, String> p = new HashMap<>();
        p.put("path", EDUCATION_ROOT);
        p.put("type", "cq:PageContent");
        p.put("property", "cq:tags");
        p.put("property.value", tagId);
        p.put("orderby", "@cq:lastModified");
        p.put("orderby.sort", "desc");

        if (limit != null) p.put("p.limit", limit.toString());

        return executePageQuery(p, session, limit);
    }

    /* ===================== PLANNER QUERY ===================== */

    /**
     * Finds education pages whose *article content fragment* has a multi-value property "planner"
     * containing a reference to the planner CF folder path with the given plannerId.
     *
     * Example CF property value:
     *   /content/dam/efe/cf/plannerlocation/planners/581/fragment_carrington_581
     */
    private List<String> findEducationPagesByPlanner(String plannerId, Session session, Integer limit) {
        Map<String, String> p = new HashMap<>();
        p.put("path", ARTICLES_CF_ROOT);
        p.put("type", "dam:Asset");

        // CF master node property path
        p.put("property", "jcr:content/data/master/planner");
        p.put("property.operation", "like");
        p.put("property.value", "%/planners/" + plannerId + "/%");

        p.put("orderby", "@jcr:content/jcr:lastModified");
        p.put("orderby.sort", "desc");

        if (limit != null) p.put("p.limit", limit.toString());

        Query q = queryBuilder.createQuery(PredicateGroup.create(p), session);
        SearchResult result = q.getResult();

        List<String> pages = new ArrayList<>();

        for (Hit hit : result.getHits()) {
            try {
                Resource cfAsset = resourceResolver.getResource(hit.getPath());
                if (cfAsset == null) continue;

                String articlePage = resolveArticlePageFromFragment(cfAsset);
                if (StringUtils.isNotBlank(articlePage)) {
                    pages.add(articlePage);
                }
            } catch (RepositoryException ignore) {
                // ignore
            }
        }

        return (limit != null && pages.size() > limit)
            ? new ArrayList<>(pages.subList(0, limit))
            : pages;
    }

    /**
     * If the planner has a primary office CF, pull its "state" property from:
     * /content/dam/efe/cf/plannerlocation/planners/{id}/primaryofficejcr:content/data/master
     **/
    private String resolvePlannerPrimaryOfficeState(String plannerId) {
        if (resourceResolver == null || StringUtils.isBlank(plannerId)) return null;

        String plannerFolderPath = PLANNER_CF_ROOT + "/" + plannerId;
        Resource plannerFolder = resourceResolver.getResource(plannerFolderPath);
        if (plannerFolder == null) return null;

        for (Resource child : plannerFolder.getChildren()) {
            String name = child.getName();
            if (StringUtils.containsIgnoreCase(name, "primaryoffice")) {
                Resource master = resourceResolver.getResource(child.getPath() + "/jcr:content/data/master");
                if (master == null) continue;

                String state = master.getValueMap().get("state", String.class);
                if (StringUtils.isNotBlank(state)) {
                    return state.trim();
                }
            }
        }
        return null;
    }

    /**
     * Merge authored first, then append state list (minus duplicates),
     * finally apply maxItems overall.
     */
    private List<String> mergeAuthoredThenState(List<String> authored, List<String> stateBased, Integer limitOrNull) {
        LinkedHashSet<String> merged = new LinkedHashSet<>();

        if (authored != null) merged.addAll(authored);

        if (stateBased != null) {
            for (String p : stateBased) {
                // only append if not already present
                merged.add(p);
            }
        }

        List<String> out = new ArrayList<>(merged);

        if (limitOrNull != null && out.size() > limitOrNull) {
            return new ArrayList<>(out.subList(0, limitOrNull));
        }
        return out;
    }

    /* ===================== SHARED HELPERS ===================== */

    private List<String> executePageQuery(Map<String, String> predicates, Session session, Integer limit) {
        Query q = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
        SearchResult result = q.getResult();

        List<String> pages = new ArrayList<>();
        for (Hit hit : result.getHits()) {
            try {
                pages.add(StringUtils.substringBefore(hit.getPath(), "/jcr:content"));
            } catch (RepositoryException ignore) {
                // ignore
            }
        }

        return (limit != null && pages.size() > limit)
            ? new ArrayList<>(pages.subList(0, limit))
            : pages;
    }

    /**
     * Given a CF asset at /content/dam/.../some-article,
     * find the education page which has an articledetails node with articleFragmentPath == that CF asset path.
     *
     * NOTE: Use javax.jcr.query.Query.JCR_SQL2 (NOT com.day.cq.search.Query)
     */
    private String resolveArticlePageFromFragment(Resource cfAsset) {
        if (cfAsset == null) return null;

        String fragmentPath = cfAsset.getPath();

        // escape single quotes for SQL2 safety
        String safePath = fragmentPath.replace("'", "''");

        String sql2 =
            "SELECT * FROM [nt:unstructured] AS s " +
            "WHERE s.[" + ARTICLE_FRAGMENT_PROP + "] = '" + safePath + "'";

        Iterator<Resource> refs = resourceResolver.findResources(sql2, javax.jcr.query.Query.JCR_SQL2);

        if (refs.hasNext()) {
            Resource articleDetails = refs.next();
            return StringUtils.substringBefore(articleDetails.getPath(), "/jcr:content");
        }
        return null;
    }

    /* ===================== TEASER BUILD ===================== */

    private ValueMap buildTeaserProps(Page page) {
        Map<String, Object> props = new HashMap<>();

        String title = StringUtils.defaultIfBlank(
            page.getNavigationTitle(),
            StringUtils.defaultIfBlank(page.getPageTitle(), page.getTitle())
        );

        props.put("jcr:title", title);
        props.put("linkURL", page.getPath() + ".html");

        String fragmentPath = getArticleFragmentPathFromArticleDetails(page);
        String subtitle = stripHtml(getElementFromContentFragment(fragmentPath, "subtitle"));

        props.put("text", StringUtils.defaultIfBlank(subtitle, page.getDescription()));
        props.put("textFromPage", false);

        return new ValueMapDecorator(props);
    }

    private String getArticleFragmentPathFromArticleDetails(Page page) {
        if (page == null) return null;

        Resource content = page.getContentResource();
        if (content == null) return null;

        Resource details = findArticleDetailsComponent(content);
        return details != null
            ? details.getValueMap().get(ARTICLE_FRAGMENT_PROP, String.class)
            : null;
    }

    private Resource findArticleDetailsComponent(Resource root) {
        if (root == null) return null;

        for (Resource child : root.getChildren()) {
            if (ARTICLE_DETAILS_NODE_NAME.equals(child.getName()) ||
                ARTICLE_DETAILS_RESOURCE_TYPE.equals(child.getResourceType())) {
                return child;
            }
            Resource found = findArticleDetailsComponent(child);
            if (found != null) return found;
        }
        return null;
    }

    private String getHeroImageFromContentFragment(String fragmentPath) {
        return getElementFromContentFragment(fragmentPath, "heroImage");
    }

    private String getElementFromContentFragment(String fragmentPath, String name) {
        if (StringUtils.isBlank(fragmentPath) || resourceResolver == null) return null;

        Resource cfRes = resourceResolver.getResource(fragmentPath);
        if (cfRes == null) return null;

        ContentFragment cf = cfRes.adaptTo(ContentFragment.class);
        if (cf == null) return null;

        ContentElement el = cf.getElement(name);
        return el != null ? StringUtils.trimToNull(el.getContent()) : null;
    }

    private String stripHtml(String html) {
        return html != null ? html.replaceAll("<[^>]*>", "").trim() : null;
    }
}