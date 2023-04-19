package com.efe.core.models.impl;

import com.efe.core.models.PlannerList;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class PlannerListImplTest.
 */
@ExtendWith(AemContextExtension.class)
class PlannerListImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/plannerlist.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

	/** The Constant RESOURCE. */
	private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/plannerlist";

	/** The model. */
	private PlannerList model;

	/** The resource. */
	private Resource resource;

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/**
	 * Sets the up.
	 */
	@BeforeEach
	public void setup() {
		Class<PlannerList> modelClass = PlannerList.class;
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
		assertEquals("_blank", model.getPlannerTarget());
		assertEquals("THE {0}  , {1}, TEAM", model.getPlannerTitle());
		assertEquals("Read Bio", model.getCtaLabel());
		assertEquals("westminster", model.getCity());
		assertEquals("co", model.getState());
		assertEquals("Chad", model.getPlannerList().get(0).getFirstName());
		assertEquals("Ernzen", model.getPlannerList().get(0).getLastName());
		assertEquals("https://planners.edelmanfinancialengines.com/media/1085/efe_ernzen_chad_897_desktop_308x308.png", model.getPlannerList().get(0).getDesktopurl());
		assertEquals("Executive Director, Financial Planning", model.getPlannerList().get(0).getTitle());
		assertEquals("/content/efe/us/en/plannerdata.Chad.Ernzen.27.html", model.getPlannerList().get(0).getButtonurl());
	}

	/**
	 * Test header null attributes.
	 */
	@Test
	void testPlannerListNullAttributes() {
		model = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/plannerlist1")
				.adaptTo(PlannerList.class);
		assertEquals(0, model.getPlannerList().size());
		assertEquals("list-2833b18972", model.getId());
	}


}
