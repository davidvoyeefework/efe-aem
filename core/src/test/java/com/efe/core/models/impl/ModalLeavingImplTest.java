package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.resourceresolver.MockResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.efe.core.models.ModalLeaving;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class ModalLeavingImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ModalLeavingImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/modal/modal-leaving.json";

	/** The Constant GENERIC_LIST_CONTENT. */
	private static final String GENERIC_LIST_CONTENT = "/com/efe/core/models/modal/genericlist.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

	/** The Constant RESOURCE. */
	private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/modalleaving";

	/** The Constant GENERIC_LIST_RESOURCE. */
	private static final String GENERIC_LIST_RESOURCE = "/etc/acs-commons/lists/efe/modal-exclusion/jcr:content/list";
	/** The model. */
	private ModalLeaving model;

	/** The resource. */
	private Resource resource;

	/** The asset resource. */
	private Resource assetResource;

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/**
	 * Sets the up.
	 *
	 * @throws LoginException the login exception
	 */
	@BeforeEach
	public void setup() throws LoginException {
		Class<ModalLeaving> modelClass = ModalLeaving.class;
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.load().json(GENERIC_LIST_CONTENT, GENERIC_LIST_RESOURCE);
		aemContext.addModelsForClasses(modelClass);
		resource = aemContext.currentResource(RESOURCE);
		final Map<String, Object> subServiceUser = new ConcurrentHashMap<>();
		subServiceUser.put(ResourceResolverFactory.SUBSERVICE, "efe-service-user");
		assetResource = aemContext.currentResource(GENERIC_LIST_RESOURCE);
		aemContext.registerService(ResourceResolver.class, resourceResolver);
		aemContext.registerService(ResourceResolverFactory.class, new MockResourceResolverFactory());
		lenient().when(resourceResolverFactory.getServiceResourceResolver(subServiceUser)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource(GENERIC_LIST_RESOURCE)).thenReturn(assetResource);
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
		assertEquals(
				"{\"Edelman\":\"https://edelmandev.wpengine.com/\",\"Edelman Engine\":\"https://www.edelmanfinancialengines.com/\",\"Facebook\":\"https://www.facebook.com/edelmanfinancialengines/\",\"Twitter\":\"https://twitter.com/edelmanfe\",\"Instagram\":\"https://www.instagram.com/edelmanfinancialengines/\",\"Linkedin\":\"https://www.linkedin.com/company/edelman-financial-engines/\",\"Jobvite\":\"https://www.jobvite.com/edelmanfinancialengines\",\"Event\":\"https://event.on24.com/wcc/r/3650900/14658F6E878B575EFBB30EF0AA357733/3428297\"}",
				model.getModalList().toString());
	}

	/**
	 * Test id null attributes.
	 */
	@Test
	void testIdNullAttributes() {

		model = aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/modalleaving2")
				.adaptTo(ModalLeaving.class);
		assertEquals("modalleaving2-e6ba2ac1bc", model.getId());
	}

}
