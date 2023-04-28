package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.aem.wcm.seo.SeoTags;
import com.day.cq.commons.Externalizer;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class PageImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/socialshare/content.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/home";

	/** The Constant RESOURCE. */
	private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/container/socialshare";

	/** The model. */
	private PageImpl model;

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
		Class<PageImpl> modelClass = PageImpl.class;
		MockSlingHttpServletRequest request = aemContext.request();
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.addModelsForClasses(modelClass);
		aemContext.request().setPathInfo(TEST_CONTENT_ROOT);
		request.setResource(aemContext.resourceResolver().getResource(RESOURCE));
		resource = aemContext.currentResource(TEST_CONTENT_ROOT+"/jcr:content");
		lenient().when(externalizer.publishLink(Mockito.any(ResourceResolver.class), Mockito.any()))
				.thenReturn("http://localhost:4502/content/efe/us/en/home.html");
		aemContext.registerService(Externalizer.class, externalizer);
		SeoTags seoTags = Mockito.mock(SeoTags.class);
		aemContext.registerService(seoTags);
		aemContext.registerAdapter(Resource.class, SeoTags.class, seoTags);
		List<String> robotsTags=new ArrayList<>();
		//robotsTags.add(Constants.NOINDEX);
		lenient().when(seoTags.getRobotsTags()).thenReturn(robotsTags);
		model = request.adaptTo(modelClass);
		
	}

	/**
	 * Simple load and getter test.
	 */
	@Test
	void simpleLoadAndGetterTest() {
		assertEquals("http://localhost:4503/content/efe/us/en/home.html", model.getCanonicalLink());
		assertEquals("http://localhost:4503/content/efe/us/en/home.thumb.800.480.png", model.getThumbnail());
	}

}
