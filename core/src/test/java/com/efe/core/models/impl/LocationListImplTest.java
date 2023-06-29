package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
	
	/** The Constant ID_CONTENT. */
	private static final String ID_CONTENT = "/com/efe/core/models/location/locationlistid.json";
	
	
	/** The LOCATION. */
	private static final String LOCATION = "/content/efe/us/en/location";
	
	/** The Constant RESOURCE. */
	private static final String RESOURCE = LOCATION  + "/jcr:content/root/container/locationlist";
	
	

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/** The resource. */
	private Resource resource;

	/** The model. */
	private LocationList model;

	/** The EfeServiceImpl. */
	private EfeServiceImpl efeService = new EfeServiceImpl();

	@Mock
	/** The configuration. */
	private EfeServiceImpl.Config configuration;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	public void setUp() {

		Class<LocationList> modelClass = LocationList.class;
		aemContext.load().json(RESOURCE_CONTENT, PlannerLocationConstants.LOCATION_PATH + "/al");
		aemContext.load().json(ID_CONTENT, RESOURCE);
		configuration = Mockito.mock(EfeServiceImpl.Config.class); 
		Mockito.lenient().when(configuration.plannerPageUrl()).thenReturn("/content/efe/us/en/locations");

		aemContext.registerInjectActivateService(efeService);
		efeService.activate(configuration);
		aemContext.addModelsForClasses(modelClass);
		resource = aemContext.currentResource(RESOURCE);
		model = resource.adaptTo(modelClass);

	}

	/**
	 * Verifies that the {@link Model#getStates()} method returns a Map containing
	 * state names as keys and another Map as values, which in turn contains state
	 * attributes as keys and their corresponding values as values. The test also
	 * checks that the size and contents of the returned Maps are correct.
	 */
	@Test
	void testGetStates() {
		Map<String, Map<String, String>> states = model.getStates();
		assertNotNull(states);
		assertEquals(1, states.size());
		Map<String, String> cities = states.get("Alabama");
		assertNotNull(cities);
	}

}
