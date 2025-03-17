package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.servlet.MockRequestPathInfo;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.efe.core.models.PlannerList;
import com.efe.core.services.impl.EfeServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

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

	/** The EfeServiceImpl. */
	private EfeServiceImpl efeService = new EfeServiceImpl();

	/** The configuration. */
	@Mock
	/** The configuration. */
	private EfeServiceImpl.Config configuration;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	public void setup() throws Exception {

		Class<PlannerList> modelClass = PlannerList.class;
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.load().json(CF_CONTENT, CF_PATH);
		aemContext.load().json(PLANNER_CONTENT, PLANNER_PATH);
		configuration = Mockito.mock(EfeServiceImpl.Config.class);
		Mockito.lenient().when(configuration.plannerBioPageUrl()).thenReturn("/content/efe/us/en/financial-planners");
		aemContext.registerInjectActivateService(efeService);
		efeService.activate(configuration);
		aemContext.addModelsForClasses(modelClass);
	}

	/**
	 * Simple load and getter test.
	 */
	@Test
	public void simpleLoadAndGetterTest() {
		resource = aemContext.currentResource(RESOURCE);
		MockSlingHttpServletRequest request = aemContext.request();
		MockRequestPathInfo requestPathInfo = (MockRequestPathInfo) request.getRequestPathInfo();
		requestPathInfo.setResourcePath(TEST_CONTENT_ROOT);
		requestPathInfo.setSelectorString("AL.Birmingham");
		model = aemContext.request().adaptTo(PlannerList.class);
		// assertEquals("2332", model.getId());
		// assertEquals("_blank", model.getPlannerTarget());
		// assertEquals("THE Birmingham  , al, TEAM", model.getPlannerTitle());
		// assertEquals("Read Bio", model.getCtaLabel());
		// assertEquals("BIRMINGHAM", model.getCity());
		// assertEquals("AL", model.getState());
		// assertEquals("Dale", model.getPlannerList().get(0).getFirstName());
		// assertEquals("Hansen", model.getPlannerList().get(0).getLastName());
		// assertEquals("https://planners.edelmanfinancialengines.com/media/1112/efe_hansen_dale_016_desktop_308x308.png",
		// 		model.getPlannerList().get(0).getDesktopUrl());
		// assertEquals("Director, Financial Planning", model.getPlannerList().get(0).getTitle());
		// assertEquals("/content/efe/us/en/financial-planners.Dale.Hansen.29.html",
		// 		model.getPlannerList().get(0).getButtonUrl());
	}
	
	/**
	 * Test null attribute.
	 */
	@Test
    void testNullAttribute() {
        resource = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/plannerlist2");
		MockSlingHttpServletRequest request = aemContext.request();
        model = aemContext.request().adaptTo(PlannerList.class);
        // assertEquals("plannerlist2-ed6a28fce7", model.getId());
    }

}
