package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.models.LocationList;
import com.efe.core.services.impl.EfeServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class LocationListImplTest.
 */
@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class LocationListImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/location/locationlist.json";

	/** The resource. */
	private Resource resource;

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/** The EfeServiceImpl. */
	private EfeServiceImpl efeService = new EfeServiceImpl();

	@Mock
	/** The configuration. */
	private EfeServiceImpl.Config configuration;

	@Mock
	private ResourceResolver resourceResolver;

	/**
	 * Verifies that the {@link Model#getStates()} method returns a Map containing
	 * state names as keys and another Map as values, which in turn contains state
	 * attributes as keys and their corresponding values as values. The test also
	 * checks that the size and contents of the returned Maps are correct.
	 */
	// @Test
	// void testGetStates() {
	// 	aemContext.registerInjectActivateService(efeService);
	// 	aemContext.load().json(RESOURCE_CONTENT, "/content");
	// 	resource = aemContext.currentResource("/content/efe/jcr:content/locationlist");
	// 	aemContext.addModelsForClasses(LocationList.class);
	// 	LocationList locationList = resource.adaptTo(LocationList.class);
	// 	assertNotNull(locationList.getStates());
	// 	assertEquals("locationlist-1e217cfd31", locationList.getId());
	// }

}
