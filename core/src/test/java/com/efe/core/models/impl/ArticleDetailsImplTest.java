package com.efe.core.models.impl;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.efe.core.bean.ArticleDetailsBean;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class ArticleDetailsImplTest {
    /** The aem context. */
    private AemContext aemContext = new AemContext();

    /** The Resource resolver. */
    @Mock
    ResourceResolver resolver;

    /** The resource. */
    @Mock
    Resource resource;
    
    com.efe.core.models.ArticleDetails articleDetails;
    
    ContentFragment contentFragment;

    @BeforeEach
    void setUp() {
    	aemContext.addModelsForClasses(ContentFragment.class);
    	aemContext.addModelsForClasses(ArticleDetailsImpl.class);
    	aemContext.load().json("/com/efe/core/models/articleDetails/articleDetails.json", "/content");
        aemContext.load().json("/com/efe/core/models/articleDetails/artcleFragment.json", "/content/dam/efe/test-article-cf/master");
        aemContext.load().json("/com/efe/core/models/articleDetails/regularAuthor.json", "/content/dam/efe/author1");
        aemContext.load().json("/com/efe/core/models/articleDetails/planner.json", "/content/dam/efe/cf/plan1");
        aemContext.load().json("/com/efe/core/models/articleDetails/education.json", "/content/dam/efe/test-education-cf");
        aemContext.load().json("/com/efe/core/models/articleDetails/certification.json", "/content/dam/efe/test-certification");

        aemContext.currentResource("/content/efe/jcr:content/articleDetails");
        resolver = aemContext.resourceResolver();
    	articleDetails = aemContext.request().adaptTo(com.efe.core.models.ArticleDetails.class);
    	
    	Resource res = aemContext.currentResource("/content/dam/efe/test-article-cf/master");
    	contentFragment = res.adaptTo(ContentFragment.class);
    	
    	Resource authorFragment = aemContext.currentResource("/content/dam/efe/author1");
    	contentFragment = authorFragment.adaptTo(ContentFragment.class);

        ArticleDetailsBean articleDetailsBean = new ArticleDetailsBean();

    }

    @Test
    void getArticleFragmentPath() {
    	assertNotNull(articleDetails.getArticleFragmentPath());

    }
}