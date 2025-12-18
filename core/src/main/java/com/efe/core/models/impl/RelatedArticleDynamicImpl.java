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

import com.day.cq.search.*;
import com.day.cq.search.result.*;
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

    @Self
    private SlingHttpServletRequest request;

    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    private QueryBuilder queryBuilder;

    private List<String> relatedArticlePagePaths = Collections.emptyList();


    @PostConstruct
    protected void init() {
        Session session = resourceResolver.adaptTo(Session.class);
        if (session == null || queryBuilder == null) {
            return;
        }

        String stateCode = resolveStateCode();
        if (StringUtils.isBlank(stateCode)) {
            return;
        }

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

        for (String path : relatedArticlePagePaths) {
            Page page = pageManager.getPage(path);
            if (page == null) continue;

            ValueMap teaserProps = buildTeaserProps(page);

            Resource syntheticTeaser = new SyntheticResource(
                resourceResolver,
                path + "/_syntheticTeaser",
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
    public String getLinkText() {
        return null;
    }

    @Override
    public String getRequestText() {
        return null;
    }

    // Helpers 
    private String resolveStateCode() {
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
        props.put("description", page.getDescription());
        props.put("linkURL", page.getPath());

        Resource content = page.getContentResource();
        if (content != null) {
            ValueMap c = content.getValueMap();

            String fileRef = c.get("image/fileReference", String.class);
            if (StringUtils.isBlank(fileRef)) {
                fileRef = c.get("featuredImage", String.class);
            }
            if (StringUtils.isNotBlank(fileRef)) {
                props.put("fileReference", fileRef);
            }
        }

        return new ValueMapDecorator(props);
    }
}
