package com.efe.core.models.impl;

import com.efe.core.models.PlannerList;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
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
	public void setup() throws Exception{


		Class<PlannerList> modelClass = PlannerList.class;
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.addModelsForClasses(modelClass);
		resource = aemContext.currentResource(RESOURCE);
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
	}


}
