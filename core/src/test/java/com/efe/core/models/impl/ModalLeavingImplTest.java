package com.efe.core.models.impl;

import com.efe.core.models.ModalLeaving;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class ModalLeavingImplTest.
 */
@ExtendWith(AemContextExtension.class)
class ModalLeavingImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/modal/modal-leaving.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

	/** The Constant RESOURCE. */
	private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/modalleaving";

	/** The model. */
	private ModalLeaving model;

	/** The resource. */
	private Resource resource;

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/**
	 * Sets the up.
	 */
	@BeforeEach
	public void setup() {
		Class<ModalLeaving> modelClass = ModalLeaving.class;
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
		assertEquals("/content/dam/efe/corporate-brand/efe/logos/logo-official.svg", model.getLogo());
		assertEquals("logo-alt", model.getLogoAltText());
		assertEquals("Leaving Title", model.getModalTitle());
		assertEquals("Edelman Financial Engines", model.getModalDescriptionText());
		assertEquals("Leave", model.getModalLeaveText());
		assertEquals("Cancel", model.getModalCancelText());
	}


}
