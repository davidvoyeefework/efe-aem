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
    adaptables = { SlingHttpServletRequest.class },
    adapters = RelatedArticleDynamic.class,
    resourceType = RelatedArticleDynamicImpl.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class RelatedArticleDynamicImpl implements RelatedArticleDynamic {

    public static final String RESOURCE_TYPE = "efe/components/relatedarticle-dynamic";

    private static final String EDUCATION_ROOT = "/content/efe/us/en/education";
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

    /**
     * Dialog field: name="./maxItems"
     * blank or 0 => unlimited
     */
    @ValueMapValue(name = "maxItems")
    private Integer maxItems;

    private List<String> relatedArticlePagePaths = Collections.emptyList();

    // canonical page path (no .html) -> hero image (from CF)
    private final Map<String, String> heroImageByPagePath = new HashMap<>();

    @PostConstruct
    protected void init() {
        Session session = resourceResolver != null ? resourceResolver.adaptTo(Session.class) : null;
        if (session == null || queryBuilder == null) return;

        String stateCode = resolveStateCode();
        if (StringUtils.isBlank(stateCode)) return;

        String stateTagId = STATE_TAG_PREFIX + stateCode.toLowerCase(Locale.US);

        // null => unlimited
        Integer limit = resolveLimitOrNull();

        this.relatedArticlePagePaths = findEducationPagesByStateTag(stateTagId, session, limit);
    }

    @Override
    public List<Resource> getRelatedTeasers() {
        if (resourceResolver == null || relatedArticlePagePaths == null) {
            return Collections.emptyList();
        }

        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        if (pageManager == null) {
            return Collections.emptyList();
        }

        heroImageByPagePath.clear();

        List<Resource> teasers = new ArrayList<>();
        int i = 0;

        for (String path : relatedArticlePagePaths) {
            Page page = pageManager.getPage(path);
            if (page == null) continue;

            ValueMap teaserProps = buildTeaserProps(page);

            // populate hero map using canonical page path key (no .html)
            String fragmentPath = getArticleFragmentPathFromArticleDetails(page);
            String hero = getHeroImageFromContentFragment(fragmentPath);
            if (StringUtils.isNotBlank(hero)) {
                heroImageByPagePath.put(page.getPath(), hero);
            }

            Resource syntheticTeaser = new SyntheticResource(
                resourceResolver,
                path + "/_syntheticTeaser_" + (i++),
                TEASER_RT
            ) {
                @Override
                public ValueMap getValueMap() {
                    return teaserProps;
                }
            };

            teasers.add(syntheticTeaser);
        }

        return teasers;
    }

    @Override
    public List<String> getRelatedArticlePagePaths() {
        return relatedArticlePagePaths;
    }

    @Override
    public Map<String, String> getTeaserHeroImages() {
        return heroImageByPagePath;
    }

    @Override
    public String getLinkText() {
        return null;
    }

    @Override
    public String getRequestText() {
        return null;
    }

    /**
     * Returns 0 when unlimited (blank or 0 in dialog).
     */
    @Override
    public int getMaxItems() {
        Integer limit = resolveLimitOrNull();
        return limit != null ? limit : 0;
    }

    /* ---------------- Helpers ---------------- */

    /**
     * @return Integer limit when maxItems > 0, otherwise null (unlimited)
     */
    private Integer resolveLimitOrNull() {
        if (maxItems == null || maxItems.intValue() <= 0) {
            return null; // unlimited
        }
        return maxItems.intValue();
    }

    private String resolveStateCode() {
        if (request == null || request.getRequestPathInfo() == null) {
            return request != null ? request.getParameter("state") : null;
        }
        String[] selectors = request.getRequestPathInfo().getSelectors();
        if (selectors != null && selectors.length > 0 && StringUtils.isNotBlank(selectors[0])) {
            return selectors[0];
        }
        return request.getParameter("state");
    }

    private List<String> findEducationPagesByStateTag(String stateTagId, Session session, Integer limitOrNull) {
        Map<String, String> p = new HashMap<>();

        p.put("path", EDUCATION_ROOT);
        p.put("type", "cq:PageContent");
        p.put("property", "cq:tags");
        p.put("property.value", stateTagId);

        p.put("orderby", "@cq:lastModified");
        p.put("orderby.sort", "desc");

        // ✅ Only set p.limit when author specified maxItems > 0
        if (limitOrNull != null) {
            p.put("p.limit", String.valueOf(limitOrNull));
        }

        Query query = queryBuilder.createQuery(PredicateGroup.create(p), session);
        SearchResult result = query.getResult();

        List<String> pages = new ArrayList<>();

        for (Hit hit : result.getHits()) {
            try {
                String contentPath = hit.getPath(); // /page/jcr:content
                pages.add(StringUtils.substringBefore(contentPath, "/jcr:content"));
            } catch (RepositoryException e) {
                // ignore
            }
        }

        // ✅ Safety trim ONLY when a limit was specified
        if (limitOrNull != null && pages.size() > limitOrNull) {
            return new ArrayList<>(pages.subList(0, limitOrNull));
        }

        return pages;
    }

    private ValueMap buildTeaserProps(Page page) {
        Map<String, Object> props = new HashMap<>();

        String title = StringUtils.defaultIfBlank(
            page.getNavigationTitle(),
            StringUtils.defaultIfBlank(page.getPageTitle(), page.getTitle())
        );

        props.put("jcr:title", title);

        // ✅ Always link to .html
        props.put("linkURL", page.getPath() + ".html");

        // Subtitle from CF -> teaser text
        String fragmentPath = getArticleFragmentPathFromArticleDetails(page);
        String subtitleHtml = getSubtitleFromContentFragment(fragmentPath);
        String teaserText = StringUtils.defaultIfBlank(stripHtml(subtitleHtml), page.getDescription());

        props.put("text", teaserText);
        props.put("description", teaserText);
        props.put("jcr:description", teaserText);
        props.put("textFromPage", false);
        props.put("descriptionFromPage", false);

        // asset type tags
        addAssetTypeTagsToProps(page, props);

        return new ValueMapDecorator(props);
    }

    private void addAssetTypeTagsToProps(Page page, Map<String, Object> props) {
        if (page == null || props == null) return;

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

        TagManager tagManager = resourceResolver != null ? resourceResolver.adaptTo(TagManager.class) : null;
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

        Resource articleDetails = findArticleDetailsComponent(content);
        if (articleDetails == null) return null;

        return articleDetails.getValueMap().get(ARTICLE_FRAGMENT_PROP, String.class);
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

    private String getSubtitleFromContentFragment(String fragmentPath) {
        return getElementFromContentFragment(fragmentPath, "subtitle");
    }

    private String getHeroImageFromContentFragment(String fragmentPath) {
        return getElementFromContentFragment(fragmentPath, "heroImage");
    }

    private String getElementFromContentFragment(String fragmentPath, String elementName) {
        if (StringUtils.isBlank(fragmentPath) || StringUtils.isBlank(elementName) || resourceResolver == null) return null;

        Resource cfResource = resourceResolver.getResource(fragmentPath);
        if (cfResource == null) return null;

        ContentFragment cf = cfResource.adaptTo(ContentFragment.class);
        if (cf == null) return null;

        ContentElement el = cf.getElement(elementName);
        return el != null ? StringUtils.trimToNull(el.getContent()) : null;
    }

    private String stripHtml(String html) {
        return html != null ? html.replaceAll("<[^>]*>", "").trim() : null;
    }
}
