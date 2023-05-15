package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.bean.ArticleDetailsBean;
import com.efe.core.models.ArticleDetails;
import com.efe.core.utils.ArticleDetailUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = com.efe.core.models.ArticleDetails.class, resourceType = {
        ArticleDetailsImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ArticleDetailsImpl implements ArticleDetails {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/components/articledetails";

    /**
     * The Constant LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDetailsImpl.class);

    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

    /** The current resource. */
    @SlingObject
    private Resource resource;

    /** The article fragment path. */
    @ValueMapValue
    String articleFragmentPath;

    ArticleDetailsBean articleDetails;

    @PostConstruct
    public void init() {
        articleDetails = new ArticleDetailsBean();
        if(null != articleFragmentPath){
            Resource articlesFragmentResource = resourceResolver.getResource(articleFragmentPath);
            if(null != articlesFragmentResource) {
                articleDetails = ArticleDetailUtil.getArticleDetails(articlesFragmentResource, resourceResolver);
            }
        }
        else {
            LOGGER.error("Article Content fragment not found");
        }

    }


    /**
     * Gets the ArticleFragmentPath.
     *
     * @return the articleDetails
     */
    @Override
    public ArticleDetailsBean getArticleFragmentPath() {
        return articleDetails;
    }


}
