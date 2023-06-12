package com.efe.core.models.impl;

import com.efe.core.models.RelatedArticle;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.commons.lang3.reflect.FieldUtils;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
 class RelatedArticleImplTest {
    /** The aem context. */
    private AemContext aemContext = new AemContext();

    /** The Resource resolver. */
    @Mock
    ResourceResolver resolver;

    /** The resource. */
    @Mock
    Resource resource;

    RelatedArticle relatedArticle;

    @InjectMocks
    RelatedArticleImpl relatedArticleImpl;

    @Mock
    private SlingHttpServletRequest request;

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(RelatedArticleImpl.class);

        aemContext.load().json("/com/efe/core/models/RelatedArticle/relatedArticle.json", "/content");
        aemContext.currentResource("/content");
        resolver = aemContext.resourceResolver();
        request = aemContext.request();
        relatedArticle = aemContext.request().adaptTo(RelatedArticle.class);

    }

    @Test
    void getRelatedArticle() throws IllegalAccessException {
        assertEquals("Read More", relatedArticle.getLinkText());
        FieldUtils.writeField(relatedArticleImpl, "text", "text", true);
        assertEquals("text", relatedArticleImpl.getRequestText());
    }

}
