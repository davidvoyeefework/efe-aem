package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.efe.core.bean.Articles;
import com.efe.core.models.ArticleDetails;
import com.efe.core.services.DynamicMediaService;
import com.efe.core.services.EfeService;
import com.efe.core.services.SeoService;
import com.efe.core.utils.ArticleDetailUtil;
import com.efe.core.utils.SeoUtil;

import org.apache.lucene.queryparser.flexible.standard.processors.BooleanSingleChildOptimizationQueryNodeProcessor;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.efe.core.utils.ResourceUtil;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * The Class ArticleDetailsImpl.
 */
@Model(
    adaptables = { Resource.class,
        SlingHttpServletRequest.class }, adapters = com.efe.core.models.ArticleDetails.class, resourceType = {
    ArticleDetailsImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ArticleDetailsImpl implements ArticleDetails {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/components/articledetails";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDetailsImpl.class);

    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

    /** The current resource. */
    @SlingObject
    private Resource resource;

    @OSGiService
    private SeoService seoService;

    @OSGiService
    private EfeService efeService;

    @Self
    private SlingHttpServletRequest request;

    @OSGiService
    private Externalizer externalizer;
    
    @OSGiService
	private DynamicMediaService dynamicMediaService;
   
    @ScriptVariable
    private Page currentPage;

    /** The article fragment path. */
    @ValueMapValue
    private String articleFragmentPath;

    /** the tags *. */
    @ValueMapValue
    private String[] tags;
    /** the mappedPage *. */
    @ValueMapValue
    private String mappedPage;

    /** the id *. */
    @ValueMapValue
    private String id;

    /** ArticleDetail. */
    private Articles articleDetails;

    /** The json ld. */
    private String jsonLd;

    // Show CTA Flag
    private String showCta;

    // CTA Heading
    private String ctaHeading;

    // CTA Body Copy
    private String ctaBodyCopy;    

    // CTA Body Copy
    private String ctaText;     

    // CTA Link
    private String ctaLink;    
    
    // Resource Path
    @ValueMapValue    
    String resourcePath; 
    
    // CTA Heading
    String resourceProperty;

    // Artice Sidebar Show or NoShow
    String sidebar;

    /**
     * Inits the Model.
     */
    @PostConstruct
    public void init() {

        // Fetch inline section CTA heading
        resourceProperty = "heading";
        ctaHeading = ResourceUtil.getProperty(resourceResolver, resourcePath, resourceProperty);

        // Fetch inline section CTA bodycopy
        String resourcePropertyBodyCopy = "bodycopy";
        ctaBodyCopy = ResourceUtil.getProperty(resourceResolver, resourcePath, resourcePropertyBodyCopy);

        // Fetch inline section CTA text
        String resourcePropertyCtaText = "buttontext";
        ctaText = ResourceUtil.getProperty(resourceResolver, resourcePath, resourcePropertyCtaText);

        // Fetch inline section CTA link
        String resourcePropertyCtaLink = "buttonLink";
        ctaLink = ResourceUtil.getProperty(resourceResolver, resourcePath, resourcePropertyCtaLink);        

        // Fetch Article Sidebar Show or NoShow Value
        String resourcePropertyShowSidebar = "sidebar";
        sidebar = ResourceUtil.getProperty(resourceResolver, resourcePath, resourcePropertyShowSidebar);     
        
        



        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        articleDetails = new Articles();
        if (null != articleFragmentPath) {
            Resource articlesFragmentResource = resourceResolver.getResource(articleFragmentPath);
            if (null != articlesFragmentResource) {
                articleDetails = ArticleDetailUtil.getArticleDetails(articlesFragmentResource, resourceResolver, tags, mappedPage,
                    pageManager, efeService, dynamicMediaService);
                if (Objects.nonNull(articleDetails)) {
                    jsonLd = SeoUtil.getArticleSchema(seoService, efeService, externalizer, resourceResolver, currentPage,
                        articleDetails);
                }
            }
        } else {
            LOGGER.error("Article Content fragment not found : {}", articleFragmentPath);
        }

    }

    /**
     * Gets the ArticleFragmentPath.
     *
     * @return the articleDetails
     */
    @Override
    public Articles getArticleFragmentPath() {
        return articleDetails;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Gets the json ld.
     *
     * @return the jsonLd
     */
    @Override
    public String getJsonLd() {
        return jsonLd;
    }

    // Gets flag for inline CTA Section Block
    public String getShowCta() {
        return showCta;
    }

    // Gets CTA Heading
    public String getCtaHeading() {
        return ctaHeading;
    }

    // Gets CTA Body Copy
    public String getBodyCopy() {
        return ctaBodyCopy;
    }    

    // Gets CTA Text
    public String getCtaText() {
        return ctaText;
    }  
    
    // Gets CTA Link
    public String getCtaLink() {
        return ctaLink;
    }    
    
    // Return flag for Article Sidbar
    public String getSidebar() {
        return sidebar;
    }
}
