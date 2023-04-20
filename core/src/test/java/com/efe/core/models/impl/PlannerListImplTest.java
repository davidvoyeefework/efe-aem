package com.efe.core.models.impl;

import com.efe.core.models.PlannerList;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.servlet.MockRequestPathInfo;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
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
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/plannerlist/plannerlist.json";

	/** The Constant CF_CONTENT. */
	private static final String CF_CONTENT = "/com/efe/core/models/plannerlist/cf.json";

	/** The Constant PLANNER_CONTENT. */
	private static final String PLANNER_CONTENT = "/com/efe/core/models/plannerlist/planner.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

	/** The Constant RESOURCE. */
	private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/plannerlist";

	/** The Constant CF_PATH. */
	private static final String CF_PATH = "/content/dam/efe/cf/plannerlocation/locations/al/birmingham/fragment_birmingham_28";

	/** The Constant PLANNER_PATH. */
	private static final String PLANNER_PATH = "/content/dam/efe/cf/plannerlocation/planners/29/fragment_dale_29/jcr:content/data/master";
	/** The model. */
	private PlannerList model;

	/** The resource. */
	private Resource resource;

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	public void setup() throws Exception {

		MockSlingHttpServletRequest request = aemContext.request();
		Class<PlannerList> modelClass = PlannerList.class;
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.load().json(CF_CONTENT, CF_PATH);
		aemContext.load().json(PLANNER_CONTENT, PLANNER_PATH);
		aemContext.addModelsForClasses(modelClass);
		resource = aemContext.currentResource(RESOURCE);
		MockRequestPathInfo requestPathInfo = (MockRequestPathInfo) request.getRequestPathInfo();
		requestPathInfo.setResourcePath(TEST_CONTENT_ROOT);
		requestPathInfo.setSelectorString("AL.Birmingham");
		model = aemContext.request().adaptTo(PlannerList.class);
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
		assertEquals("BIRMINGHAM", model.getCity());
		assertEquals("AL", model.getState());
		assertEquals("Dale", model.getPlannerList().get(0).getFirstName());
		assertEquals("Hansen", model.getPlannerList().get(0).getLastName());
		assertEquals("https://planners.edelmanfinancialengines.com/media/1112/efe_hansen_dale_016_desktop_308x308.png",
				model.getPlannerList().get(0).getDesktopUrl());
		assertEquals("Director, Financial Planning", model.getPlannerList().get(0).getTitle());
		assertEquals("/content/efe/us/en/plannerdata.Dale.Hansen.29.html",
				model.getPlannerList().get(0).getButtonUrl());
	}

}
