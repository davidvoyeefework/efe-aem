package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.models.LocationList;
import com.efe.core.services.EfeService;
import com.efe.core.services.impl.EfeServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class LocationListImplTest.
 */
@ExtendWith(AemContextExtension.class)
class LocationListImplTest {
	
	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/locationlist.json";

	/** The aem context. */
	private AemContext aemContext = new AemContext();
	
	/** The resource. */
	private Resource resource;
	
	/** The model. */
	private LocationList model;
	
	private EfeServiceImpl efeService = new EfeServiceImpl();
	
	/**
	 * Sets the up.
	 */
	@BeforeEach
	public void setUp() {
		Class<LocationList> modelClass = LocationList.class;
		aemContext.load().json(RESOURCE_CONTENT, PlannerLocationConstants.LOCATION_PATH + "/al");
		aemContext.registerInjectActivateService(efeService);
		aemContext.addModelsForClasses(modelClass);
		resource = aemContext.currentResource(PlannerLocationConstants.LOCATION_PATH + "/al");
		model = resource.adaptTo(modelClass);
		
		
	}

	/**
	 * Verifies that the {@link Model#getStates()} method returns a Map containing
	 * state names as keys and another Map as values, which in turn contains
	 * state attributes as keys and their corresponding values as values. The
	 * test also checks that the size and contents of the returned Maps are correct.
	 */
	@Test
	public void testGetStates() {
		Map<String, Map<String, String>> states = model.getStates();
		assertNotNull(states);
		assertEquals(1, states.size());
		Map<String, String> cities = states.get("Alabama");
		assertNotNull(cities);
		assertEquals(2, cities.size());
		assertTrue(cities.get("Birmingham").contains(".AL.Birmingham"));
		assertTrue(cities.get("Huntsville").contains(".AL.Huntsville"));
	}

}
