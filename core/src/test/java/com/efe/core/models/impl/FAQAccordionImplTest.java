package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.efe.core.models.FAQAccordion;
import com.efe.core.models.multifield.FAQ;
import com.efe.core.services.SeoService;
import com.efe.core.services.impl.SeoServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class FAQAccordionImplTest.
 */
@ExtendWith(AemContextExtension.class)
class FAQAccordionImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/faqaccordion.json";

    private static final String CF_RESOURCE_CONTENT = "/com/efe/core/models/faq.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

	/** The Constant RESOURCE. */
	private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/faqaccordion";

	/** The model. */
	private FAQAccordion model;

	/** The resource. */
	private Resource resource;

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/**
	 * Sets the up.
	 */
	@BeforeEach
	public void setup() {
		Class<FAQAccordion> modelClass = FAQAccordion.class;
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
        aemContext.load().json(CF_RESOURCE_CONTENT, "/cf");
		aemContext.addModelsForClasses(modelClass);
		aemContext.registerService(SeoService.class, new SeoServiceImpl());
		resource = aemContext.currentResource(RESOURCE);
		model = resource.adaptTo(modelClass);
	}

	/**
	 * Simple load and getter test.
	 */
	@Test
	public void simpleLoadAndGetterTest() {
		assertEquals("faqaccordion-ad22d8b121", model.getId());
 		FAQ faq = model.getFaqList().get(0);
 		assertEquals("/cf/dam/content-fragment", faq.getFragmentPath());
 		assertEquals("content-fragment-2d166d187a", faq.getId());
		assertEquals("sample question\n", faq.getQuestion());
		assertEquals("<p>sample answer</p>\n", faq.getAnswer());
		assertNotNull(model.getJsonLd());
	}

	/**
	 * Test FAQ null attributes.
	 */
	@Test
	void testFAQNullAttributes() {
		model = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/faqaccordion1")
				.adaptTo(FAQAccordion.class);
		assertEquals(0, model.getFaqList().size());
	}
}
