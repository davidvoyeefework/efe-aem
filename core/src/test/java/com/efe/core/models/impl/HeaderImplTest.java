package com.efe.core.models.impl;

import com.efe.core.models.Header;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class HeaderModelImplTest.
 */
@ExtendWith(AemContextExtension.class)
class HeaderImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/header/header.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

	/** The Constant RESOURCE. */
	private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/header";

	/** The model. */
	private Header model;

	/** The resource. */
	private Resource resource;

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/**
	 * Sets the up.
	 */
	@BeforeEach
	public void setup() {
		Class<Header> modelClass = Header.class;
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
		assertEquals("2332", model.getId());
		assertEquals("/content/dam/efe/logo-official.svg", model.getFileReference());
		assertEquals("Alt text", model.getAlt());
		assertEquals("_self", model.getLogoTarget());
		assertEquals("/content/efe/us/en.html", model.getLogoLink());
		assertEquals("/content/efe/us/en.html", model.getLoginLink());
		assertEquals("_blank", model.getLoginTarget());
		assertEquals("Log In", model.getLoginTitle());
		assertEquals("Search..", model.getSearchLabel());
		assertEquals("Search", model.getSearchBtn());
		assertEquals("Planner CTA", model.getCtaTitle());
		assertEquals("/content/efe/us/en.html", model.getCtaLink());
		assertEquals("Home", model.getHeaderList().get(0).getLabel());
		assertEquals("Customer", model.getHeaderList().get(1).getLabel());
		assertEquals("Products", model.getHeaderList().get(2).getLabel());
	}


}
