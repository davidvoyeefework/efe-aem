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

    private List<String> relatedArticlePagePaths = Collections.emptyList();

    // linkURL/page-path variants -> hero image src
    private final Map<String, String> heroImageByLinkUrl = new HashMap<>();

    @PostConstruct
    protected void init() {
        Session session = resourceResolver != null ? resourceResolver.adaptTo(Session.class) : null;
        if (session == null || queryBuilder == null) return;

        String stateCode = resolveStateCode();
        if (StringUtils.isBlank(stateCode)) return;

        String stateTagId = STATE_TAG_PREFIX + stateCode.toLowerCase(Locale.US);
        this.relatedArticlePagePaths = findEducationPagesByStateTag(stateTagId, session);
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

        List<Resource> teasers = new ArrayList<>();
        int i = 0;

        heroImageByLinkUrl.clear();

        for (String path : relatedArticlePagePaths) {
            Page page = pageManager.getPage(path);
            if (page == null) continue;

            ValueMap teaserProps = buildTeaserProps(page);

            // Pull hero from Content Fragment
            String fragmentPath = getArticleFragmentPathFromArticleDetails(page);
            String hero = getHeroImageFromContentFragment(fragmentPath);

            // ✅ Map DAM URL for Dev/Dispatcher environments
            if (StringUtils.isNotBlank(hero) && resourceResolver != null) {
                hero = resourceResolver.map(hero);
            }

            // ✅ Store hero under multiple key variants so HTL lookup never misses
            if (StringUtils.isNotBlank(hero)) {
                storeHeroForAllKeys(page, teaserProps, hero);
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
        return heroImageByLinkUrl;
    }

    @Override
    public String getLinkText() {
        return null;
    }

    @Override
    public String getRequestText() {
        return null;
    }

    /* ---------------- Dev-safe hero map helpers ---------------- */

    private void storeHeroForAllKeys(Page page, ValueMap teaserProps, String hero) {
        if (page == null || StringUtils.isBlank(hero)) return;

        // This is the exact key HTL uses:
        // pagePath = teaser.valueMap.linkURL
        String linkUrl = teaserProps != null ? teaserProps.get("linkURL", String.class) : null;

        String rawPagePath = page.getPath();              // /content/.../page
        String rawPagePathHtml = rawPagePath + ".html";   // /content/.../page.html

        String mappedPagePath = resourceResolver != null ? resourceResolver.map(rawPagePath) : rawPagePath;
        String mappedPagePathHtml = mappedPagePath.endsWith(".html") ? mappedPagePath : mappedPagePath + ".html";

        putHero(rawPagePath, hero);
        putHero(rawPagePathHtml, hero);

        putHero(mappedPagePath, hero);
        putHero(mappedPagePathHtml, hero);

        // Also store under the actual linkURL (what HTL uses)
        if (StringUtils.isNotBlank(linkUrl)) {
            putHero(linkUrl, hero);

            // Store mapped linkURL too (some envs map /content away)
            if (resourceResolver != null) {
                String mappedLinkUrl = resourceResolver.map(linkUrl);
                if (StringUtils.isNotBlank(mappedLinkUrl)) {
                    putHero(mappedLinkUrl, hero);
                    if (!mappedLinkUrl.endsWith(".html")) {
                        putHero(mappedLinkUrl + ".html", hero);
                    }
                }
            }
        }
    }

    private void putHero(String key, String hero) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(hero)) return;
        heroImageByLinkUrl.put(key, hero);
    }

    /* ---------------- Existing helpers ---------------- */

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

    private List<String> findEducationPagesByStateTag(String stateTagId, Session session) {
        Map<String, String> p = new HashMap<>();
        p.put("path", EDUCATION_ROOT);
        p.put("type", "cq:PageContent");
        p.put("property", "cq:tags");
        p.put("property.value", stateTagId);

        p.put("orderby", "@cq:lastModified");
        p.put("orderby.sort", "desc");
        p.put("p.limit", "12");

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
        return pages;
    }

    private ValueMap buildTeaserProps(Page page) {
        Map<String, Object> props = new HashMap<>();

        String title = StringUtils.defaultIfBlank(
            page.getNavigationTitle(),
            StringUtils.defaultIfBlank(page.getPageTitle(), page.getTitle())
        );

        props.put("jcr:title", title);
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

        // Only efe:asset-type/* tags
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
