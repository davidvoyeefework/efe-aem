package com.efe.core.models.impl;

import com.efe.core.models.Footer;
import com.efe.core.models.HeaderNav;
import com.efe.core.models.multifield.Link;
import com.efe.core.models.multifield.NavigationList;
import com.efe.core.models.multifield.VerticalList;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class HeaderNavImplTest.
 */
@ExtendWith(AemContextExtension.class)
class HeaderNavImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/header/header-nav.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

	/** The Constant RESOURCE. */
	private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/header_nav";

	/** The model. */
	private HeaderNav model;

	/** The resource. */
	private Resource resource;

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/**
	 * Sets the up.
	 */
	@BeforeEach
	public void setup() {
		Class<HeaderNav> modelClass = HeaderNav.class;
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
		NavigationList navigationList = model.getPrimaryList().get(0);
		assertEquals("working", navigationList.getHeading());
		assertEquals("/content/efe/us/en", navigationList.getHeadingLink());
		assertEquals("_blank", navigationList.getHeadingTarget());
		Link verticalLinks = navigationList.getSecondaryLinks().get(0);
		assertEquals("Location", verticalLinks.getLabel());
		assertEquals("/content/efe/us/en.html", verticalLinks.getLink());
		assertEquals("_self", verticalLinks.getTarget());

	}

	/**
	 * Test secondary links null attributes.
	 */
	@Test
	void testVerticalLinksNullAttributes() {
		model = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/header_nav1")
				.adaptTo(HeaderNav.class);
		NavigationList navigationList = model.getPrimaryList().get(0);
		assertEquals(0, navigationList.getSecondaryLinks().size());
	}


}
