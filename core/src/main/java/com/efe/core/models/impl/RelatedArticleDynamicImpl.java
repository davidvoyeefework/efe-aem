package com.efe.core.models.impl;

import java.util.*;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.DamConstants;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.*;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.search.*;
import com.day.cq.search.result.*;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.tagging.TagConstants;
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

    private static final Logger log = LoggerFactory.getLogger(RelatedArticleDynamicImpl.class);  

    public static final String RESOURCE_TYPE = "efe/components/relatedarticle-dynamic";

    private static final String EDUCATION_ROOT = "/content/efe/us/en/education";
    private static final String ARTICLES_CF_ROOT = "/content/dam/efe/cf/corporate/articles";

    private static final String PLANNER_CF_BASE = "/content/dam/efe/cf/plannerlocation/planners";

    private static final String TEASER_RT = "core/wcm/components/teaser/v1/teaser";
    private static final String STATE_TAG_PREFIX = "efe:content-type/location/us-state/";

    private static final String ARTICLE_DETAILS_NODE_NAME = "articledetails";
    private static final String ARTICLE_DETAILS_RESOURCE_TYPE = "efe/components/articledetails";
    private static final String ARTICLE_FRAGMENT_PROP = "articleFragmentPath";

    // Article CF field names
    private static final String CF_EL_SUBTITLE = "subtitle";
    private static final String CF_EL_HERO_IMAGE = "heroImage";

    // Article CF “planner” property location (array of strings)
    private static final String ARTICLE_CF_PLANNER_PROP = "jcr:content/data/master/planner";

    // Planner Primary Office CF “state” property location
    private static final String PLANNER_OFFICE_STATE_PROP = "jcr:content/data/master/state";

    // Used to extract planner id from a full planner CF reference path
    private static final Pattern PLANNER_ID_IN_PATH = Pattern.compile("/planners/(\\d+)/");

    @Self
    private SlingHttpServletRequest request;

    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    private QueryBuilder queryBuilder;

    /** Dialog field: blank or 0 => unlimited */
    @ValueMapValue(name = "maxItems")
    private Integer maxItems;

    private List<String> relatedArticlePagePaths = Collections.emptyList();

    // canonical page path (no .html) -> hero image (from article CF)
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

        Mode mode = resolveMode();
        Integer limit = resolveLimitOrNull();

        if (mode == Mode.LOCATION) {
            String stateCode = resolveSelector(0);
            if (StringUtils.isBlank(stateCode)) return;

            String stateTagId = STATE_TAG_PREFIX + stateCode.toLowerCase(Locale.US);
            this.relatedArticlePagePaths = applyFinalLimit(
                findEducationPagesByStateTag(stateTagId, session, null),
                limit
            );
            return;
        }

        // PLANNER mode:
        String plannerId = resolveSelector(2);
        if (StringUtils.isBlank(plannerId)) {
            // If URL doesn't have 3 selectors, nothing to do in planner mode.
            return;
        }

        // 1) planner-authored first
        List<String> authored = findEducationPagesByPlanner(plannerId, session);

        // 2) then state fallback based on planner primary office state
        List<String> fallback = Collections.emptyList();
        String primaryOfficeState = resolvePlannerPrimaryOfficeState(plannerId);
        if (StringUtils.isNotBlank(primaryOfficeState)) {
            String stateTagId = STATE_TAG_PREFIX + primaryOfficeState.toLowerCase(Locale.US);
            fallback = findEducationPagesByStateTag(stateTagId, session, null);
        }

        // 3) merge: authored first, then fallback, dedupe while preserving order
        this.relatedArticlePagePaths = applyFinalLimit(
            mergeDedupPreserveOrder(authored, fallback),
            limit
        );
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

            // populate hero map using canonical page path key (no .html)
            String fragmentPath = getArticleFragmentPathFromArticleDetails(page);
            String hero = getHeroImageFromContentFragment(fragmentPath);
            if (StringUtils.isNotBlank(hero)) {
                heroImageByPagePath.put(page.getPath(), hero);
            }

            Resource syntheticTeaser = new SyntheticResource(
                resourceResolver,
                path + "/_synthetic_" + (i++),
                TEASER_RT
            ) {
                @Override
                public ValueMap getValueMap() {
                    return props;
                }
            };

            teasers.add(syntheticTeaser);
        }

        return teasers;
    }

    @Override
    public Map<String, String> getTeaserHeroImages() {
        if (heroImageByPagePath == null || heroImageByPagePath.isEmpty()) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(new HashMap<>(heroImageByPagePath));
    }

    @Override
    public List<String> getRelatedArticlePagePaths() {
        if (relatedArticlePagePaths == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList<>(relatedArticlePagePaths));
    }

    @Override
    public int getMaxItems() {
        Integer limit = resolveLimitOrNull();
        return limit != null ? limit : 0;
    }

    /* ===================== MODE ===================== */

    private Mode resolveMode() {
        // If there are 3+ selectors, treat as planner page (financial-planner.First.Last.581)
        String[] selectors = request.getRequestPathInfo() != null ? request.getRequestPathInfo().getSelectors() : null;
        if (selectors != null && selectors.length >= 3) {
            return Mode.PLANNER;
        }
        return Mode.LOCATION;
    }

    private String resolveSelector(int index) {
        if (request.getRequestPathInfo() == null) return null;
        String[] selectors = request.getRequestPathInfo().getSelectors();
        if (selectors != null && selectors.length > index) {
            return StringUtils.trimToNull(selectors[index]);
        }
        return null;
    }

    private Integer resolveLimitOrNull() {
        return (maxItems == null || maxItems.intValue() <= 0) ? null : maxItems.intValue();
    }

    /* ===================== LOCATION QUERY (state-tagged pages) ===================== */

    private List<String> findEducationPagesByStateTag(String tagId, Session session, Integer limitOrNull) {
        Map<String, String> p = new HashMap<>();
        p.put("path", EDUCATION_ROOT);
        p.put("type", "cq:PageContent");
        p.put("property", TagConstants.PN_TAGS);
        p.put("property.value", tagId);
        p.put("orderby", "@cq:lastModified");
        p.put("orderby.sort", "desc");

        if (limitOrNull != null) {
            p.put("p.limit", String.valueOf(limitOrNull));
        }

        return executePageQuery(p, session, limitOrNull);
    }

    private List<String> executePageQuery(Map<String, String> predicates, Session session, Integer limitOrNull) {
        Query q = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
        SearchResult result = q.getResult();

        List<String> pages = new ArrayList<>();
        for (Hit hit : result.getHits()) {
            try {
                pages.add(StringUtils.substringBefore(hit.getPath(), "/jcr:content"));
                } catch (RepositoryException e) {
                    log.warn("Failed to access repository node", e);
                }
        }

        return applyFinalLimit(pages, limitOrNull);
    }

    /* ===================== PLANNER QUERY (planner-authored pages) ===================== */

    private List<String> findEducationPagesByPlanner(String plannerId, Session session) {
        // Step 1: find article CFs that contain planner reference
        List<String> matchingArticleCFPaths = findArticleCFPathsByPlanner(plannerId, session);

        if (matchingArticleCFPaths.isEmpty()) {
            return Collections.emptyList();
        }

        // Step 2: resolve each CF path to an education page by finding articledetails components
        // that have articleFragmentPath == that CF path.
        // We’ll preserve CF order (most recent first), and dedupe pages.
        LinkedHashSet<String> pages = new LinkedHashSet<>();

        for (String fragmentPath : matchingArticleCFPaths) {
            String pagePath = resolveEducationPageFromArticleFragmentPath(fragmentPath, session);
            if (StringUtils.isNotBlank(pagePath)) {
                pages.add(pagePath);
            }
        }

        return new ArrayList<>(pages);
    }

    private List<String> findArticleCFPathsByPlanner(String plannerId, Session session) {
        Map<String, String> p = new HashMap<>();
        p.put("path", ARTICLES_CF_ROOT);
        p.put("type", DamConstants.NT_DAM_ASSET);

        // planner is String[] at jcr:content/data/master/planner
        // Use like to match any element containing /planners/{id}/
        p.put("property", ARTICLE_CF_PLANNER_PROP);
        p.put("property.operation", "like");
        p.put("property.value", "%/planners/" + plannerId + "/%");

        p.put("orderby", "@jcr:content/jcr:lastModified");
        p.put("orderby.sort", "desc");

        // Do NOT apply maxItems here; we merge later.
        // But protect performance a bit to avoid scanning a massive set:
        p.put("p.limit", "50");

        Query q = queryBuilder.createQuery(PredicateGroup.create(p), session);
        SearchResult result = q.getResult();

        List<String> cfPaths = new ArrayList<>();
        for (Hit hit : result.getHits()) {
            try {
                cfPaths.add(hit.getPath());
                } catch (RepositoryException e) {
                    log.warn("Failed to access repository node", e);
                }
        }
        return cfPaths;
    }

    private String resolveEducationPageFromArticleFragmentPath(String fragmentPath, Session session) {
        // Find the articledetails component under /content/efe/us/en/education whose
        // articleFragmentPath property equals fragmentPath.
        //
        // This avoids SQL2 and stays within QueryBuilder.
        Map<String, String> p = new HashMap<>();
        p.put("path", EDUCATION_ROOT);

        // We only care about the articledetails component nodes
        p.put("type", JcrConstants.NT_UNSTRUCTURED);

        // Ensure it's an articledetails component
        p.put("1_property", JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY);
        p.put("1_property.value", ARTICLE_DETAILS_RESOURCE_TYPE);

        // Match fragment path exactly
        p.put("property", ARTICLE_FRAGMENT_PROP);
        p.put("property.value", fragmentPath);

        // Only need one
        p.put("p.limit", "1");

        Query q = queryBuilder.createQuery(PredicateGroup.create(p), session);
        SearchResult result = q.getResult();

        for (Hit hit : result.getHits()) {
            try {
                String articledetailsPath = hit.getPath(); // /content/.../jcr:content/.../articledetails
                return StringUtils.substringBefore(articledetailsPath, "/jcr:content");
                } catch (RepositoryException e) {
                    log.warn("Failed to access repository node", e);
                }
        }
        return null;
    }

    /* ===================== PLANNER PRIMARY OFFICE STATE ===================== */

    private String resolvePlannerPrimaryOfficeState(String plannerId) {
        if (resourceResolver == null) return null;

        // Path: /content/dam/efe/cf/plannerlocation/planners/{id}
        String plannerFolderPath = PLANNER_CF_BASE + "/" + plannerId;
        Resource plannerFolder = resourceResolver.getResource(plannerFolderPath);
        if (plannerFolder == null) return null;

        // Find a child dam:Asset whose name contains "primaryoffice"
        Resource primaryOfficeAsset = null;
        for (Resource child : plannerFolder.getChildren()) {
            if (!child.isResourceType(DamConstants.NT_DAM_ASSET)) {
                // isResourceType may not work reliably here; keep it simple:
            }
            String name = child.getName();
            if (StringUtils.containsIgnoreCase(name, "primaryoffice")) {
                primaryOfficeAsset = child;
                break;
            }
        }

        if (primaryOfficeAsset == null) {
            // Nothing found
            return null;
        }

        // Read state from /jcr:content/data/master/state
        Resource master = resourceResolver.getResource(primaryOfficeAsset.getPath() + "/jcr:content/data/master");
        if (master == null) return null;

        return StringUtils.trimToNull(master.getValueMap().get("state", String.class));
    }

    /* ===================== MERGE + LIMIT ===================== */

    private List<String> mergeDedupPreserveOrder(List<String> first, List<String> second) {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        if (first != null) set.addAll(first);
        if (second != null) set.addAll(second);
        return new ArrayList<>(set);
    }

    private List<String> applyFinalLimit(List<String> items, Integer limitOrNull) {
        if (items == null) return Collections.emptyList();
        if (limitOrNull == null || limitOrNull.intValue() <= 0) return items;

        int limit = limitOrNull.intValue();
        if (items.size() <= limit) return items;
        return new ArrayList<>(items.subList(0, limit));
    }

    /* ===================== TEASER BUILD ===================== */

    private ValueMap buildTeaserProps(Page page) {
        Map<String, Object> props = new HashMap<>();

        String title = StringUtils.defaultIfBlank(
            page.getNavigationTitle(),
            StringUtils.defaultIfBlank(page.getPageTitle(), page.getTitle())
        );

        props.put(JcrConstants.JCR_TITLE, title);
        props.put("linkURL", page.getPath() + ".html");

        String fragmentPath = getArticleFragmentPathFromArticleDetails(page);
        String subtitle = stripHtml(getElementFromContentFragment(fragmentPath, CF_EL_SUBTITLE));

        String teaserText = StringUtils.defaultIfBlank(subtitle, page.getDescription());
        props.put("text", teaserText);
        props.put("description", teaserText);
        props.put(JcrConstants.JCR_DESCRIPTION, teaserText);
        props.put("textFromPage", false);
        props.put("descriptionFromPage", false);

        // Keep your asset type logic if needed (optional)
        addAssetTypeTagsToProps(page, props);

        return new ValueMapDecorator(props);
    }

    private void addAssetTypeTagsToProps(Page page, Map<String, Object> props) {
        if (page == null || props == null || resourceResolver == null) return;

        Resource content = page.getContentResource();
        if (content == null) return;

        String[] allTagIds = content.getValueMap().get(TagConstants.PN_TAGS, String[].class);
        if (allTagIds == null || allTagIds.length == 0) return;

        // Only keep efe:asset-type/*
        List<String> assetTagIds = new ArrayList<>();
        for (String id : allTagIds) {
            if (StringUtils.startsWith(id, "efe:asset-type/")) {
                assetTagIds.add(id);
            }
        }
        if (assetTagIds.isEmpty()) return;

        props.put("assetTypeTagIds", assetTagIds.toArray(new String[0]));

        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        if (tagManager == null) return;

        List<String> assetTagTitles = new ArrayList<>();
        for (String id : assetTagIds) {
            Tag t = tagManager.resolve(id);
            if (t != null && StringUtils.isNotBlank(t.getTitle())) {
                assetTagTitles.add(t.getTitle());
            }
        }
        if (!assetTagTitles.isEmpty()) {
            props.put("assetTypeTagTitles", assetTagTitles.toArray(new String[0]));
        }
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
            if (ARTICLE_DETAILS_NODE_NAME.equals(child.getName())) return child;
            if (ARTICLE_DETAILS_RESOURCE_TYPE.equals(child.getResourceType())) return child;

            Resource found = findArticleDetailsComponent(child);
            if (found != null) return found;
        }
        return null;
    }

    private String getHeroImageFromContentFragment(String fragmentPath) {
        return getElementFromContentFragment(fragmentPath, CF_EL_HERO_IMAGE);
    }

    private String getElementFromContentFragment(String fragmentPath, String elementName) {
        if (StringUtils.isBlank(fragmentPath) || StringUtils.isBlank(elementName) || resourceResolver == null) {
            return null;
        }

        Resource cfRes = resourceResolver.getResource(fragmentPath);
        if (cfRes == null) return null;

        ContentFragment cf = cfRes.adaptTo(ContentFragment.class);
        if (cf == null) return null;

        ContentElement el = cf.getElement(elementName);
        return el != null ? StringUtils.trimToNull(el.getContent()) : null;
    }

    private String stripHtml(String html) {
        return html != null ? html.replaceAll("<[^>]*>", "").trim() : null;
    }
}