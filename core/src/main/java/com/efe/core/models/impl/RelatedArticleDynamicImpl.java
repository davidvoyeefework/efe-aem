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

    public static final String RESOURCE_TYPE = "efe/components/relatedarticle-dynamic";

    private static final String EDUCATION_ROOT = "/content/efe/us/en/education";
    private static final String ARTICLES_CF_ROOT = "/content/dam/efe/cf/corporate/articles";

    private static final String PLANNER_CF_ROOT = "/content/dam/efe/cf/plannerlocation/planners";

    private static final String TEASER_RT = "core/wcm/components/teaser/v1/teaser";
    private static final String STATE_TAG_PREFIX = "efe:content-type/location/us-state/";
    private static final String ARTICLE_DETAILS_NODE_NAME = "articledetails";
    private static final String ARTICLE_DETAILS_RESOURCE_TYPE = "efe/components/articledetails";
    private static final String ARTICLE_FRAGMENT_PROP = "articleFragmentPath";

    private static final String ASSET_TYPE_TAG_PREFIX = "efe:asset-type/";

    @Self
    private SlingHttpServletRequest request;

    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    private QueryBuilder queryBuilder;

    /** Dialog field */
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
        if (resourceResolver == null || queryBuilder == null || request == null || request.getRequestPathInfo() == null) {
            return;
        }

        Session session = resourceResolver.adaptTo(Session.class);
        if (session == null) return;

        Mode mode = resolveMode();
        Integer limit = resolveLimitOrNull();

        if (mode == Mode.LOCATION) {
            String stateCode = resolveSelector(0);
            if (StringUtils.isBlank(stateCode)) return;

            String stateTagId = STATE_TAG_PREFIX + stateCode.toLowerCase(Locale.US);
            relatedArticlePagePaths = findEducationPagesByStateTag(stateTagId, session, limit);
            return;
        }

        if (mode == Mode.PLANNER) {
            String plannerId = resolveSelector(2); // 3rd selector
            if (StringUtils.isBlank(plannerId)) return;

            // 1) Primary attempt: authored articles (CF planner property)
            List<String> pages = findEducationPagesByPlanner(plannerId, session, limit);

            // 2) Fallback: if 0 results, use planner primary office state => treat like location state
            if (pages == null || pages.isEmpty()) {
                String stateFromPrimaryOffice = resolvePlannerPrimaryOfficeState(plannerId);
                if (StringUtils.isNotBlank(stateFromPrimaryOffice)) {
                    String stateTagId = STATE_TAG_PREFIX + stateFromPrimaryOffice.toLowerCase(Locale.US);
                    pages = findEducationPagesByStateTag(stateTagId, session, limit);
                }
            }

            relatedArticlePagePaths = (pages != null) ? pages : Collections.emptyList();
        }
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
                // Key MUST match the HTL canonicalKey logic (page path with no .html)
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

    /**
     * Returns 0 when unlimited (blank or 0 in dialog).
     */
    @Override
    public int getMaxItems() {
        Integer limit = resolveLimitOrNull();
        return limit != null ? limit : 0;
    }

    /* ===================== MODE ===================== */

    private Mode resolveMode() {
        String[] selectors = request.getRequestPathInfo().getSelectors();
        // planner URLs have at least 3 selectors: First.Last.ID
        if (selectors != null && selectors.length >= 3) {
            return Mode.PLANNER;
        }
        return Mode.LOCATION;
    }

    private String resolveSelector(int index) {
        String[] selectors = request.getRequestPathInfo().getSelectors();
        if (selectors != null && selectors.length > index) {
            return selectors[index];
        }
        return null;
    }

    /**
     * @return Integer limit when maxItems > 0, otherwise null (unlimited)
     */
    private Integer resolveLimitOrNull() {
        return (maxItems == null || maxItems.intValue() <= 0) ? null : maxItems.intValue();
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
     * Planner mode:
     * 1) Find article CFs under /content/dam/efe/cf/corporate/articles where master node has "planner" containing /planners/{id}/
     * 2) Convert those CF paths into education pages by matching articledetails.articleFragmentPath == CF path
     */
    private List<String> findEducationPagesByPlanner(String plannerId, Session session, Integer limit) {
        List<String> articleCfPaths = findArticleCfPathsForPlanner(plannerId, session, limit);
        if (articleCfPaths.isEmpty()) return Collections.emptyList();
        return findEducationPagesByArticleFragmentPaths(articleCfPaths, session, limit);
    }

    /**
     * Query master nodes directly: /.../jcr:content/data/master
     * planner is a String[] on master node.
     */
    private List<String> findArticleCfPathsForPlanner(String plannerId, Session session, Integer limit) {
        Map<String, String> p = new HashMap<>();

        p.put("path", ARTICLES_CF_ROOT);
        // master nodes are nt:unstructured
        p.put("type", "nt:unstructured");

        // planner must exist + contain /planners/{id}/
        p.put("1_property", "planner");
        p.put("1_property.operation", "exists");

        p.put("2_property", "planner");
        p.put("2_property.operation", "like");
        p.put("2_property.value", "%/planners/" + plannerId + "/%");

        // order newest-ish (if datePublished exists on CF master)
        p.put("orderby", "@datePublished");
        p.put("orderby.sort", "desc");

        if (limit != null) {
            p.put("p.limit", limit.toString());
        }

        Query q = queryBuilder.createQuery(PredicateGroup.create(p), session);
        SearchResult result = q.getResult();

        Set<String> cfPaths = new LinkedHashSet<>();

        for (Hit hit : result.getHits()) {
            try {
                String masterPath = hit.getPath(); // .../jcr:content/data/master
                if (!StringUtils.endsWith(masterPath, "/jcr:content/data/master")) {
                    continue;
                }
                String cfAssetPath = StringUtils.substringBefore(masterPath, "/jcr:content/data/master");
                if (StringUtils.isNotBlank(cfAssetPath)) {
                    cfPaths.add(cfAssetPath);
                }
            } catch (RepositoryException ignore) {}
        }

        List<String> out = new ArrayList<>(cfPaths);
        if (limit != null && out.size() > limit) {
            return out.subList(0, limit);
        }
        return out;
    }

    /**
     * Find education pages that reference any of the CF paths in articledetails.articleFragmentPath.
     */
    private List<String> findEducationPagesByArticleFragmentPaths(List<String> cfPaths, Session session, Integer limit) {
        if (cfPaths == null || cfPaths.isEmpty()) return Collections.emptyList();

        Map<String, String> p = new HashMap<>();
        p.put("path", EDUCATION_ROOT);
        p.put("type", "nt:unstructured");

        // OR group over articleFragmentPath equals cfPath
        p.put("group.p.or", "true");

        int idx = 1;
        for (String cfPath : cfPaths) {
            if (StringUtils.isBlank(cfPath)) continue;
            p.put("group." + idx + "_property", ARTICLE_FRAGMENT_PROP);
            p.put("group." + idx + "_property.value", cfPath);
            idx++;
        }

        if (idx == 1) return Collections.emptyList();

        Query q = queryBuilder.createQuery(PredicateGroup.create(p), session);
        SearchResult r = q.getResult();

        Set<String> pagePaths = new LinkedHashSet<>();

        for (Hit hit : r.getHits()) {
            try {
                String nodePath = hit.getPath();
                String pagePath = StringUtils.substringBefore(nodePath, "/jcr:content");
                if (StringUtils.isNotBlank(pagePath)) {
                    pagePaths.add(pagePath);
                }
            } catch (RepositoryException ignore) {}
        }

        List<String> out = new ArrayList<>(pagePaths);
        if (limit != null && out.size() > limit) {
            return out.subList(0, limit);
        }
        return out;
    }

    /* ===================== PLANNER PRIMARY OFFICE FALLBACK ===================== */

    /**
     * If a planner has no authored articles, determine their primaryOffice state.
     *
     * Example path shape:
     * /content/dam/efe/cf/plannerlocation/planners/581/fragment_*_581_primaryoffice/jcr:content/data/master
     *
     * We do NOT hardcode the fragment name; we search under planners/{id} for a child whose name ends with "_primaryoffice".
     */
    private String resolvePlannerPrimaryOfficeState(String plannerId) {
        if (resourceResolver == null || StringUtils.isBlank(plannerId)) return null;

        Resource plannerFolder = resourceResolver.getResource(PLANNER_CF_ROOT + "/" + plannerId);
        if (plannerFolder == null) return null;

        for (Resource child : plannerFolder.getChildren()) {
            String name = child.getName();
            if (!StringUtils.endsWithIgnoreCase(name, "_primaryoffice")) {
                continue;
            }

            Resource master = resourceResolver.getResource(child.getPath() + "/jcr:content/data/master");
            if (master == null) continue;

            String state = master.getValueMap().get("state", String.class);
            if (StringUtils.isNotBlank(state)) {
                return StringUtils.trimToNull(state);
            }
        }

        return null;
    }

    /* ===================== SHARED HELPERS ===================== */

    private List<String> executePageQuery(Map<String, String> predicates, Session session, Integer limit) {
        Query q = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
        SearchResult result = q.getResult();

        Set<String> pages = new LinkedHashSet<>();
        for (Hit hit : result.getHits()) {
            try {
                pages.add(StringUtils.substringBefore(hit.getPath(), "/jcr:content"));
            } catch (RepositoryException ignore) {}
        }

        List<String> out = new ArrayList<>(pages);
        return (limit != null && out.size() > limit) ? out.subList(0, limit) : out;
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
        String teaserText = StringUtils.defaultIfBlank(subtitle, page.getDescription());

        props.put("text", teaserText);
        props.put("description", teaserText);
        props.put("jcr:description", teaserText);

        props.put("textFromPage", false);
        props.put("descriptionFromPage", false);

        addAssetTypeTagsToProps(page, props);

        return new ValueMapDecorator(props);
    }

    private void addAssetTypeTagsToProps(Page page, Map<String, Object> props) {
        if (page == null || props == null || resourceResolver == null) return;

        Resource content = page.getContentResource();
        if (content == null) return;

        String[] allTagIds = content.getValueMap().get("cq:tags", String[].class);
        if (allTagIds == null || allTagIds.length == 0) return;

        List<String> assetTagIds = new ArrayList<>();
        for (String id : allTagIds) {
            if (StringUtils.startsWith(id, ASSET_TYPE_TAG_PREFIX)) {
                assetTagIds.add(id);
            }
        }
        if (assetTagIds.isEmpty()) return;

        props.put("assetTypeTagIds", assetTagIds.toArray(new String[0]));
    }

    private String getArticleFragmentPathFromArticleDetails(Page page) {
        if (page == null) return null;

        Resource content = page.getContentResource();
        if (content == null) return null;

        Resource details = findArticleDetailsComponent(content);
        return details != null ? details.getValueMap().get(ARTICLE_FRAGMENT_PROP, String.class) : null;
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