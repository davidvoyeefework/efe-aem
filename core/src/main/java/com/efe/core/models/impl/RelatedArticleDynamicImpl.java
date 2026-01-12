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

    // Only tags under this namespace
    private static final String ASSET_TYPE_TAG_PREFIX = "efe:asset-type/";

    // Optional: if you have a known DM host, set this and it will be used.
    // Example: "https://s7d9.scene7.com"
    @ValueMapValue(name = "dynamicMediaHost")
    private String dynamicMediaHost;

    @Self
    private SlingHttpServletRequest request;

    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    private QueryBuilder queryBuilder;

    private List<String> relatedArticlePagePaths = Collections.emptyList();

    // canonical page path -> hero image URL (DM webp if possible)
    private final Map<String, String> heroImageByCanonicalPagePath = new HashMap<>();

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

        heroImageByCanonicalPagePath.clear();

        List<Resource> teasers = new ArrayList<>();
        int i = 0;

        for (String pagePath : relatedArticlePagePaths) {
            Page page = pageManager.getPage(pagePath);
            if (page == null) continue;

            // Build teaser props
            ValueMap teaserProps = buildTeaserProps(page);

            // Build hero image map entry
            String fragmentPath = getArticleFragmentPathFromArticleDetails(page);
            String heroDamPath = getHeroImageFromContentFragment(fragmentPath); // often /content/dam/...jpg
            String heroUrl = toDynamicMediaWebpUrl(heroDamPath);
            if (StringUtils.isNotBlank(heroUrl)) {
                heroImageByCanonicalPagePath.put(canonicalPageKey(page.getPath()), heroUrl);
            }

            // Synthetic resource for Core Teaser
            Resource syntheticTeaser = new SyntheticResource(
                resourceResolver,
                page.getPath() + "/_syntheticTeaser_" + (i++),
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
        return heroImageByCanonicalPagePath;
    }

    @Override
    public String getLinkText() {
        return null;
    }

    @Override
    public String getRequestText() {
        return null;
    }

    /* ------------------------------------------------------------
       State / Query
       ------------------------------------------------------------ */

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
                // ignore invalid hit
            }
        }
        return pages;
    }

    /* ------------------------------------------------------------
       Teaser Props
       ------------------------------------------------------------ */

    private ValueMap buildTeaserProps(Page page) {
        Map<String, Object> props = new HashMap<>();

        String title = StringUtils.defaultIfBlank(
            page.getNavigationTitle(),
            StringUtils.defaultIfBlank(page.getPageTitle(), page.getTitle())
        );

        // IMPORTANT: linkURL should include .html, but hero lookup should use canonical key
        props.put("jcr:title", title);
        props.put("linkURL", page.getPath() + ".html");

        // Subtitle from CF
        String fragmentPath = getArticleFragmentPathFromArticleDetails(page);
        String subtitleHtml = getSubtitleFromContentFragment(fragmentPath);
        String teaserText = StringUtils.defaultIfBlank(stripHtml(subtitleHtml), page.getDescription());
        teaserText = StringUtils.trimToNull(teaserText);

        // Core Teaser often reads description/text; we set both to be safe
        props.put("text", teaserText);
        props.put("description", teaserText);
        props.put("jcr:description", teaserText);
        props.put("textFromPage", false);
        props.put("descriptionFromPage", false);

        // Asset type tags
        addAssetTypeTagsToProps(page, props);

        return new ValueMapDecorator(props);
    }

    private void addAssetTypeTagsToProps(Page page, Map<String, Object> props) {
        if (page == null || props == null || resourceResolver == null) return;

        Resource content = page.getContentResource();
        if (content == null) return;

        String[] allTagIds = content.getValueMap().get("cq:tags", String[].class);
        if (allTagIds == null || allTagIds.length == 0) return;

        // Filter only efe:asset-type/*
        LinkedHashSet<String> assetTagIds = new LinkedHashSet<>();
        for (String id : allTagIds) {
            if (StringUtils.startsWith(id, ASSET_TYPE_TAG_PREFIX)) {
                assetTagIds.add(id);
            }
        }
        if (assetTagIds.isEmpty()) return;

        props.put("assetTypeTagIds", assetTagIds.toArray(new String[0]));

        // Resolve to titles (optional)
        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        if (tagManager == null) return;

        List<String> titles = new ArrayList<>();
        for (String id : assetTagIds) {
            Tag t = tagManager.resolve(id);
            if (t != null && StringUtils.isNotBlank(t.getTitle())) {
                titles.add(t.getTitle());
            }
        }
        if (!titles.isEmpty()) {
            props.put("assetTypeTagTitles", titles.toArray(new String[0]));
        }
    }

    /* ------------------------------------------------------------
       Article Details lookup
       ------------------------------------------------------------ */

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

    /* ------------------------------------------------------------
       Content Fragment
       ------------------------------------------------------------ */

    private String getSubtitleFromContentFragment(String fragmentPath) {
        return getElementFromContentFragment(fragmentPath, "subtitle");
    }

    private String getHeroImageFromContentFragment(String fragmentPath) {
        return getElementFromContentFragment(fragmentPath, "heroImage");
    }

    private String getElementFromContentFragment(String fragmentPath, String elementName) {
        if (resourceResolver == null) return null;
        if (StringUtils.isBlank(fragmentPath) || StringUtils.isBlank(elementName)) return null;

        Resource cfResource = resourceResolver.getResource(fragmentPath);
        if (cfResource == null) return null;

        ContentFragment cf = cfResource.adaptTo(ContentFragment.class);
        if (cf == null) return null;

        ContentElement el = cf.getElement(elementName);
        return el != null ? StringUtils.trimToNull(el.getContent()) : null;
    }

    /* ------------------------------------------------------------
       Canonical key + Dynamic Media WebP
       ------------------------------------------------------------ */

    /**
     * Canonicalize: remove trailing slash and .html
     * so /page, /page/, /page.html all map to same key.
     */
    private String canonicalPageKey(String pathOrUrl) {
        if (StringUtils.isBlank(pathOrUrl)) return null;

        String v = pathOrUrl.trim();

        // If someone passed an absolute URL, strip to path portion
        int idx = v.indexOf("://");
        if (idx > -1) {
            int slash = v.indexOf('/', idx + 3);
            if (slash > -1) {
                v = v.substring(slash);
            }
        }

        // Strip query/hash
        int q = v.indexOf('?');
        if (q > -1) v = v.substring(0, q);
        int h = v.indexOf('#');
        if (h > -1) v = v.substring(0, h);

        if (v.endsWith(".html")) {
            v = v.substring(0, v.length() - 5);
        }
        while (v.endsWith("/")) {
            v = v.substring(0, v.length() - 1);
        }
        return v;
    }

    /**
     * Turn a DAM file path OR an existing DM image URL into a DM webp URL.
     *
     * Supported inputs:
     *  - /content/dam/.../file.jpg  => returns a DM URL only if we can infer a DM "public ID" (see notes)
     *  - https://s7d9.scene7.com/is/image/financialengines/someId?... => returns same with fmt=webp, size params
     *
     * If we cannot convert, returns the original path (so at least author/local works).
     */
    private String toDynamicMediaWebpUrl(String heroValue) {
        if (StringUtils.isBlank(heroValue)) return null;

        String v = heroValue.trim();

        // Case 1: already a Scene7 is/image URL
        if (StringUtils.startsWithIgnoreCase(v, "http") && v.contains("/is/image/")) {
            return ensureWebpAndSize(v);
        }

        // Case 2: DAM path: if you *also* store a DM public ID somewhere, swap here.
        // Many setups store heroImage as DAM path; converting that to Scene7 requires the Scene7 file / public ID.
        // If you cannot resolve to DM, we fallback to DAM path (may be heavy).
        //
        // If your CF "heroImage" can instead be authored as the DM URL or DM public ID, do that and this will work.
        //
        // Optional: If dynamicMediaHost is set AND heroValue looks like a Scene7 public ID (no slashes), build URL.
        if (StringUtils.isNotBlank(dynamicMediaHost) && !v.startsWith("/") && !v.contains(" ")) {
            String url = dynamicMediaHost;
            if (url.endsWith("/")) url = url.substring(0, url.length() - 1);
            // Assumes "financialengines" company name; change if different
            url = url + "/is/image/financialengines/" + v;
            return ensureWebpAndSize(url);
        }

        // Fallback: return DAM path as-is
        return v;
    }

    /**
     * Adds DM params to target webp and reduce size.
     * You can tune wid/qlt to hit <200kb.
     */
    private String ensureWebpAndSize(String url) {
        // Strip existing fmt if present; then append our params
        String base = url;
        String query = "";

        int q = url.indexOf('?');
        if (q > -1) {
            base = url.substring(0, q);
            query = url.substring(q + 1);
        }

        // Remove any existing fmt= in query to avoid duplicates
        Map<String, String> params = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(query)) {
            for (String part : query.split("&")) {
                if (StringUtils.isBlank(part)) continue;
                int eq = part.indexOf('=');
                String k = eq > -1 ? part.substring(0, eq) : part;
                String val = eq > -1 ? part.substring(eq + 1) : "";
                if (!"fmt".equalsIgnoreCase(k)) {
                    params.put(k, val);
                }
            }
        }

        // Tune these to your needs
        params.put("fmt", "webp");
        params.put("qlt", "70");
        params.put("wid", "720");
        params.put("fit", "constrain");
        params.put("dpr", "off");

        StringBuilder sb = new StringBuilder(base);
        sb.append('?');

        boolean first = true;
        for (Map.Entry<String, String> e : params.entrySet()) {
            if (!first) sb.append('&');
            first = false;
            sb.append(e.getKey());
            if (StringUtils.isNotBlank(e.getValue())) {
                sb.append('=').append(e.getValue());
            }
        }
        return sb.toString();
    }

    private String stripHtml(String html) {
        if (html == null) return null;
        return html.replaceAll("<[^>]*>", "").trim();
    }
}
