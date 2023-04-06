package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.efe.core.models.FAQAccordion;
import com.efe.core.models.multifield.FAQ;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class FooterImplTest.
 */
@ExtendWith(AemContextExtension.class)
class FAQAccordionImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/faqaccordion.json";

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
		aemContext.addModelsForClasses(modelClass);
		resource = aemContext.currentResource(RESOURCE);
		model = resource.adaptTo(modelClass);
	}

	/**
	 * Simple load and getter test.
	 */
	@Test
	public void simpleLoadAndGetterTest() {
		assertEquals("faq-9819dd0ec1", model.getId());
		FAQ faq = model.getFaqList().get(0);
		assertEquals("sample question", faq.getQuestion());
		assertEquals("sample answer", faq.getAnswer());

	}

	/**
	 * Test footer null attributes.
	 */
	@Test
	void testFooterNullAttributes() {
		model = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/faqaccordion1")
				.adaptTo(FAQAccordion.class);
		assertEquals(0, model.getFaqList().size());
	}
}
