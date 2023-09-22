package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.efe.core.models.BrightCove;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class BrightCoveImplTest.
 */
@ExtendWith(AemContextExtension.class)
class BrightCoveImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/brightcove.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/brightcove";

	/** The model. */
	private BrightCove brightCove;

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/**
	 * Simple load and getter test.
	 */
	@Test
	void testWithUrl() {
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/brightcove");
		brightCove = aemContext.request().adaptTo(BrightCove.class);
		assertEquals("testUrl", brightCove.getVideoUrl());
	}

	/**
	 * Test with out url.
	 */
	@Test
	void testWithOutUrl() {
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/brightcove_empty");
		brightCove = aemContext.request().adaptTo(BrightCove.class);
		assertNull(brightCove.getVideoUrl());
	}
}
