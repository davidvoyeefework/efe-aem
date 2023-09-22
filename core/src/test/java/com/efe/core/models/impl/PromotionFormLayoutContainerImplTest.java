package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.efe.core.models.PromotionFormLayoutContainer;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class PromotionFormLayoutContainerImplTest.
 */
@ExtendWith(AemContextExtension.class)
class PromotionFormLayoutContainerImplTest {

	/** The Constant RESOURCE_CONTENT. */
    private static final String RESOURCE_CONTENT = "/com/efe/core/models/promotionformlayout.json";

    /** The Constant TEST_CONTENT_ROOT. */
    private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/promotion";

    /** The model. */
    private PromotionFormLayoutContainer promotionFormLayoutContainer;

    /** The resource. */
    private Resource resource;

    /** The aem context. */
    private AemContext aemContext = new AemContext();

    /**
     * Simple load and getter test.
     */
    @Test
    void testWhenVariation1() {
        aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
        resource = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/promotionformlayout_var1");
        promotionFormLayoutContainer = resource.adaptTo(PromotionFormLayoutContainer.class);
        assertEquals("variation-1", promotionFormLayoutContainer.getVariation());
        assertEquals("standard", promotionFormLayoutContainer.getTeaserVariation());
        assertTrue(promotionFormLayoutContainer.isAddContainer());
        assertEquals(PromotionFormLayoutContainerImpl.TEASER_RESOURCE_TYPE , promotionFormLayoutContainer.getHeroSectionResourceType());
        assertEquals("teaser", promotionFormLayoutContainer.getHeroSectionNodeName());
        assertTrue(promotionFormLayoutContainer.isConfigured());
      }

    /**
     * Test when variation 2.
     */
    @Test
    void testWhenVariation2() {
        aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
        resource = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/promotionformlayout_var2");
        promotionFormLayoutContainer = resource.adaptTo(PromotionFormLayoutContainer.class);
        assertEquals("variation-2", promotionFormLayoutContainer.getVariation());
        assertEquals("image", promotionFormLayoutContainer.getHeroSectionNodeName());
        assertEquals(PromotionFormLayoutContainerImpl.IMAGE_RESOURCE_TYPE , promotionFormLayoutContainer.getHeroSectionResourceType());
        assertTrue(promotionFormLayoutContainer.isConfigured());
    }
    
    /**
     * Test when empty.
     */
    @Test
    void testWhenEmpty() {
        aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
        resource = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/promotionformlayout_empty");
        promotionFormLayoutContainer = resource.adaptTo(PromotionFormLayoutContainer.class); 
        assertNull( promotionFormLayoutContainer.getHeroSectionNodeName());
        assertNull( promotionFormLayoutContainer.getVariation());
        assertFalse(promotionFormLayoutContainer.isConfigured());
    }


}
