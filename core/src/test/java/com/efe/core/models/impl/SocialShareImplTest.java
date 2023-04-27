package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.commons.Externalizer;
import com.efe.core.models.multifield.SocialMediaSharing;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class SocialShareImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class SocialShareImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/socialshare/content.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/home";

	/** The Constant RESOURCE. */
	private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/container/socialshare";

	/** The model. */
	private SocialShareImpl model;

	/** The resource. */
	private Resource resource;

	/** The Mock Externalizer. */
	@Mock
	Externalizer externalizer;

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/**
	 * Sets the up.
	 */
	@BeforeEach
	public void setup() {
		Class<SocialShareImpl> modelClass = SocialShareImpl.class;
		MockSlingHttpServletRequest request = aemContext.request();
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.addModelsForClasses(modelClass);
		aemContext.request().setPathInfo(TEST_CONTENT_ROOT);
		request.setResource(aemContext.resourceResolver().getResource(RESOURCE));
		resource = aemContext.currentResource(RESOURCE);
		lenient().when(externalizer.publishLink(Mockito.any(ResourceResolver.class), Mockito.any()))
				.thenReturn("http://localhost:4502/content/efe/us/en/home.html");
		aemContext.registerService(Externalizer.class, externalizer);
		model = resource.adaptTo(modelClass);
	}

	/**
	 * Simple load and getter test.
	 */
	@Test
	void simpleLoadAndGetterTest() {
		SocialMediaSharing bean = model.getSocialMediaSharing().get(2);
		assertEquals(5, model.getSocialMediaSharing().size());
		assertEquals("fb", bean.getIcon());
		assertEquals("https://www.facebook.com/sharer/sharer.php?u=http://localhost:4503/content/efe/us/en/home.html",
				bean.getLink());
		assertEquals("_blank", model.getLinkTarget());
		assertEquals("socialshare-208183d129", model.getId());
		assertEquals("", model.getSocialMediaSharing().get(3).getLink());
	}

	/**
	 * Test null attributes.
	 */
	@Test
	void testNullAttributes() {
		model = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/container/socialshare2")
				.adaptTo(SocialShareImpl.class);
		assertEquals(0, model.getSocialMediaSharing().size());
		assertEquals("1234", model.getId());
	}

}
