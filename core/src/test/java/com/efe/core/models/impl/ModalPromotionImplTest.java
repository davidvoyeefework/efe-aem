package com.efe.core.models.impl;

import com.efe.core.models.ModalPromotion;
import com.efe.core.services.impl.DynamicMediaServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class ModalPromotionImplTest.
 */
@ExtendWith(AemContextExtension.class)
class ModalPromotionImplTest {

    /** The Constant RESOURCE_CONTENT. */
    private static final String RESOURCE_CONTENT = "/com/efe/core/models/modal/modal-promotion.json";

    /** The Constant TEST_CONTENT_ROOT. */
    private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

    /** The Constant RESOURCE. */
    private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/modalpromotion";

    /** The model. */
    private ModalPromotion modalPromotionModel;

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
        Class<ModalPromotion> modelClass = ModalPromotion.class;
        aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
        aemContext.registerInjectActivateService(dynamicMediaService);
        aemContext.addModelsForClasses(modelClass);
        resource = aemContext.currentResource(RESOURCE);
        modalPromotionModel = resource.adaptTo(modelClass);
    }

    /**
     * Simple load and getter test.
     */
    @Test
    void modelGetterTest() {
        assertEquals("modalpromotion-f9526089d6", modalPromotionModel.getId());
        assertEquals("pretitle test", modalPromotionModel.getPretitle());
        assertEquals("title test", modalPromotionModel.getTitle());
        assertEquals("description test", modalPromotionModel.getDescription());
        assertEquals("https://abc.com", modalPromotionModel.getButtonUrl());
        assertEquals("get started", modalPromotionModel.getButtonText());
        assertEquals("/content/dam/test.jpg", modalPromotionModel.getPromotionImage());
        assertEquals("alt1", modalPromotionModel.getPromotionImageAltText());
        assertEquals("white", modalPromotionModel.getCloseButtonColor());
        assertEquals("alt2", modalPromotionModel.getCloseButtonAltText());
        assertEquals("2023-05-11T12:56:00Z", modalPromotionModel.getStartDate());
        assertEquals("2023-05-11T12:58:00Z", modalPromotionModel.getEndDate());
    }
}
