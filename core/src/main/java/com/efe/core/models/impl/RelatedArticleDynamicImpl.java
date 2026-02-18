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
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
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
        Session session = resourceResolver.adaptTo(Session.class);
        if (session == null || queryBuilder == null) return;

        Mode mode = resolveMode();
        Integer limit = resolveLimitOrNull();

        if (mode == Mode.LOCATION) {
            String stateCode = resolveSelector(0);
            if (StringUtils.isBlank(stateCode)) return;

            String stateTagId = STATE_TAG_PREFIX + stateCode.toLowerCase(Locale.US);
            relatedArticlePagePaths = findEducationPagesByStateTag(stateTagId, session, limit);
        }

        if (mode == Mode.PLANNER) {
            String plannerId = resolveSelector(2);
            if (StringUtils.isBlank(plannerId)) return;

            relatedArticlePagePaths = findEducationPagesByPlanner(plannerId, session, limit);
        }
    }

    /* ===================== PUBLIC API ===================== */

    @Override
    public List<Resource> getRelatedTeasers() {
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
        String[] selectors = request.getRequestPathInfo().getSelectors();
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

    private List<String> findEducationPagesByPlanner(String plannerId, Session session, Integer limit) {
        Map<String, String> p = new HashMap<>();
        p.put("path", ARTICLES_CF_ROOT);
        p.put("type", "dam:Asset");
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
                Resource cf = resourceResolver.getResource(hit.getPath());
                if (cf == null) continue;

                String articlePage = resolveArticlePageFromFragment(cf);
                if (articlePage != null) {
                    pages.add(articlePage);
                }
            } catch (RepositoryException ignore) {}
        }

        return limit != null && pages.size() > limit
            ? pages.subList(0, limit)
            : pages;
    }

    /* ===================== SHARED HELPERS ===================== */

    private List<String> executePageQuery(Map<String, String> predicates, Session session, Integer limit) {
        Query q = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
        SearchResult result = q.getResult();

        List<String> pages = new ArrayList<>();
        for (Hit hit : result.getHits()) {
            try {
                pages.add(StringUtils.substringBefore(hit.getPath(), "/jcr:content"));
            } catch (RepositoryException ignore) {}
        }

        return limit != null && pages.size() > limit
            ? pages.subList(0, limit)
            : pages;
    }

    private String resolveArticlePageFromFragment(Resource cf) {
        ResourceResolver rr = cf.getResourceResolver();
        String fragmentPath = cf.getPath();

        Iterator<Resource> refs = rr.findResources(
            "SELECT * FROM [nt:unstructured] AS s WHERE s.[articleFragmentPath] = '" + fragmentPath + "'",
            javax.jcr.query.Query.JCR_SQL2
        );

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
        Resource content = page.getContentResource();
        if (content == null) return null;

        Resource details = findArticleDetailsComponent(content);
        return details != null
            ? details.getValueMap().get(ARTICLE_FRAGMENT_PROP, String.class)
            : null;
    }

    private Resource findArticleDetailsComponent(Resource root) {
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
        if (StringUtils.isBlank(fragmentPath)) return null;
        Resource cfRes = resourceResolver.getResource(fragmentPath);
        if (cfRes == null) return null;

        ContentFragment cf = cfRes.adaptTo(ContentFragment.class);
        if (cf == null) return null;

        ContentElement el = cf.getElement(name);
        return el != null ? el.getContent() : null;
    }

    private String stripHtml(String html) {
        return html != null ? html.replaceAll("<[^>]*>", "").trim() : null;
    }
}
