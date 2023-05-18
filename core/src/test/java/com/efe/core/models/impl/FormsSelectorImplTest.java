package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.efe.core.models.FormsSelector;
import com.efe.core.services.impl.EfeServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class FormsSelectorImplTest.
 */
@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
public class FormsSelectorImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/formselector/content.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

	/** The Constant RESOURCE. */
	private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/container/formselector";

	/** The model. */
	private FormsSelector model;

	/** The resource. */
	private Resource resource;

	/** The EfeServiceImpl. */
	private EfeServiceImpl efeService;

	/** The configuration. */
	@Mock
	/** The configuration. */
	private EfeServiceImpl.Config configuration;

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/**
	 * Sets the up.
	 */
	@BeforeEach
	public void setup() {
		Class<FormsSelector> modelClass = FormsSelector.class;
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		efeService = aemContext.registerService(new EfeServiceImpl());
		EfeServiceImpl.Config config = mock(EfeServiceImpl.Config.class);
		when(config.formBaseUrl()).thenReturn("formBaseUrl");
		when(config.formJsUrl()).thenReturn("formJsUrl");
		efeService.activate(config);
		aemContext.addModelsForClasses(modelClass);
		resource = aemContext.currentResource(RESOURCE);
		model = resource.adaptTo(modelClass);
	}

	/**
	 * Simple load and getter test.
	 */
	@Test
	public void simpleLoadAndGetterTest() {
		assertEquals("formselector-c75bf1fd34", model.getId());
		assertEquals("7f579d0245f57ec5d39795d061aab415", model.getFormId());
		assertEquals("formBaseUrl?id=7f579d0245f57ec5d39795d061aab415", model.getFormUrl());
		assertEquals("formJsUrl", model.getFormJsUrl());
	}

	/**
	 * Test ID attributes.
	 */
	@Test
	void testIdAttributes() {
		model = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/container/formselector2")
				.adaptTo(FormsSelector.class);
		assertEquals("1234", model.getId());
	}

}
