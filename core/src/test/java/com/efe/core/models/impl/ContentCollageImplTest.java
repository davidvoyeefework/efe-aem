package com.efe.core.models.impl;

import com.efe.core.models.ContentCollage;
import com.efe.core.services.impl.DynamicMediaServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class ContentCollageImplTest.
 */
@ExtendWith(AemContextExtension.class)
public class ContentCollageImplTest {

    /** The Constant RESOURCE_CONTENT. */
    private static final String RESOURCE_CONTENT = "/com/efe/core/models/contentCollage.json";

    /** The Constant TEST_CONTENT_ROOT. */
    private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

    /** The Constant RESOURCE. */
    private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/contentCollage";

    /** The model. */
    private ContentCollage contentCollageModel;

    /** The resource. */
    private Resource resource;

    /** The aem context. */
    private AemContext aemContext = new AemContext();

    /** The DynamicMediaServiceImpl. */
    private DynamicMediaServiceImpl dynamicMediaService = new DynamicMediaServiceImpl();

    /**
     * Sets the up.
     */
    @BeforeEach
    public void setup() {
        Class<ContentCollage> modelClass = ContentCollage.class;
        aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
        aemContext.registerInjectActivateService(dynamicMediaService);
        aemContext.addModelsForClasses(modelClass);
        resource = aemContext.currentResource(RESOURCE);
        contentCollageModel = resource.adaptTo(modelClass);
    }

    /**
     * Simple load and getter test.
     */
    @Test
    public void modelGetterTest() {
        assertEquals("contentCollage-242e1d2d50", contentCollageModel.getId());
        assertEquals("/content/dam/test1.png", contentCollageModel.getPrimaryImage());
        assertEquals("/content/dam/test2.png", contentCollageModel.getSecondaryImage());
        assertEquals("alt text1", contentCollageModel.getPrimaryImageAltText());
        assertEquals("alt text2", contentCollageModel.getSecondaryImageAltText());
        assertEquals("test quote", contentCollageModel.getContentCard());
    }
}
