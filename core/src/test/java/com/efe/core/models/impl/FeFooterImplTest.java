package com.efe.core.models.impl;

import com.efe.core.models.FeFooter;
import com.efe.core.services.impl.DynamicMediaServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class FeFooterImplTest.
 */
@ExtendWith(AemContextExtension.class)
 class FeFooterImplTest {

    /** The Constant RESOURCE_CONTENT. */
    private static final String RESOURCE_CONTENT = "/com/efe/core/models/feFooter.json";

    /** The Constant TEST_CONTENT_ROOT. */
    private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

    /** The Constant RESOURCE. */
    private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/feFooter";

    /** The model. */
    private FeFooter feFooterModel;

    /** The resource. */
    private Resource resource;

    /** The aem context. */
    private AemContext aemContext = new AemContext();

    /** The DynamicMediaServiceImpl. */
    private DynamicMediaServiceImpl dynamicMediaService;

    /**
     * Sets the up.
     */
    @BeforeEach
    public void setup() {
        Class<FeFooter> modelClass = FeFooter.class;
        aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
        dynamicMediaService = new DynamicMediaServiceImpl();
        aemContext.registerInjectActivateService(dynamicMediaService);
        aemContext.addModelsForClasses(modelClass);
        resource = aemContext.currentResource(RESOURCE);
        feFooterModel = resource.adaptTo(modelClass);
    }

    /**
     * Simple load and getter test.
     */
    @Test
    void modelGetterTest() {
        assertEquals("/content/dam/test1.svg", feFooterModel.getFileReference());
        assertEquals("alt text1", feFooterModel.getAltText());
        assertEquals("feFooter-2f5a06c0fb", feFooterModel.getId());
    }

}
