package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.commons.Externalizer;
import com.efe.core.models.Footer;
import com.efe.core.models.multifield.Link;
import com.efe.core.models.multifield.SocialLink;
import com.efe.core.models.multifield.VerticalList;
import com.efe.core.services.SeoService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;


/**
 * The Class FooterImplTest.
 */
@ExtendWith({AemContextExtension.class,  MockitoExtension.class})
class FooterImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/footer/footer.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

	/** The Constant RESOURCE. */
	private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/footer";

	/** The model. */
	private Footer model;

	/** The resource. */
	private Resource resource;

	/** The aem context. */
	private AemContext aemContext = new AemContext();
	
	/** The seo service. */
	@Mock
	SeoService seoService;
	
	/** The externalizer. */
	@Mock
	Externalizer externalizer;


	/**
	 * Sets the up.
	 */
	@BeforeEach
	public void setup() {
		Class<Footer> modelClass = Footer.class;
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.addModelsForClasses(modelClass);
		
		aemContext.registerService(Externalizer.class, externalizer);
		aemContext.registerService(SeoService.class, seoService);
		
		resource = aemContext.currentResource(RESOURCE);
		model = resource.adaptTo(modelClass);
	}

	/**
	 * Simple load and getter test.
	 */
	@Test
	public void simpleLoadAndGetterTest() {
		assertEquals("footer-9819dd0ec1", model.getId());
		assertEquals("/content/dam/efe/logo-official.svg", model.getFileReference());
		assertEquals("Alt text", model.getAlt());
		assertEquals("_blank", model.getLinkTarget());
		assertEquals("/content/efe/us/en/corp/home.html?q=1", model.getLogoLink());
		assertEquals("_self", model.getLogoTarget());
		SocialLink socialLinks = model.getSocialLinks().get(0);
		assertEquals("efe-facebook.svg", socialLinks.getIcon());
		assertEquals("https://www.facebook.com", socialLinks.getLink());
		assertEquals("facebook", socialLinks.getAlt());
		VerticalList verticalList = model.getVerticalList().get(0);
		assertEquals("Quick links", verticalList.getHeading());
		Link verticalLinks = verticalList.getVerticalLinks().get(0);
		assertEquals("Location", verticalLinks.getLabel());
		assertEquals("/content/efe/us/en/corp/home.html#data", verticalLinks.getLink());
		assertEquals("_self", verticalLinks.getTarget());
		Link link = verticalList.getVerticalLinks().get(1);
		assertEquals("/content/dam/efe/education.jpg", link.getLink());
		Link horizontalList = model.getHorizontalList().get(0);
		assertEquals("Legal Information", horizontalList.getLabel());
		assertEquals("#", horizontalList.getLink());
		assertEquals("_blank", horizontalList.getTarget());
		Link horizontalList1 = model.getHorizontalList().get(1);
		assertEquals("/content/efe/us/en/corp/home.html", horizontalList1.getLink());
		Link horizontalList2 = model.getHorizontalList().get(2);
		assertEquals("/content/efe/us/en/corp/home.html", horizontalList2.getLink());
	}

	/**
	 * Test footer null attributes.
	 */
	@Test
	void testFooterNullAttributes() {
		model = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/footer1")
				.adaptTo(Footer.class);
		assertEquals(0, model.getHorizontalList().size());
		assertEquals(0, model.getVerticalList().size());
		assertEquals(0, model.getSocialLinks().size());
	}

	/**
	 * Test vertical links null attributes.
	 */
	@Test
	void testVerticalLinksNullAttributes() {
		model = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/footer2")
				.adaptTo(Footer.class);
		VerticalList verticalList = model.getVerticalList().get(0);
		assertEquals(0, verticalList.getVerticalLinks().size());
	}

}
