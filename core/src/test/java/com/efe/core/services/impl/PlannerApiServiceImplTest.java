package com.efe.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;

@ExtendWith(MockitoExtension.class)
class PlannerApiServiceImplTest {

	/** Planner API END POINT **/
	private final String PLANNER_API_ENDPOINT = "https://planners.edelmanfinancialengines.com/api/v2/planners";

	/** Planner API END POINT **/
	private final String LOCATION_API_ENDPOINT = "https://planners.edelmanfinancialengines.com/api/v2/locations";

	/** Planner AUTH_HEAD **/
	private final String AUTH_HEAD = "dummy header";

	@Mock
	/** The plannerApiServiceImpl. */
	private PlannerApiServiceImpl plannerApiServiceImpl;

	@Mock
	/** The configuration. */
	private PlannerApiServiceImpl.Config configuration;

	public final AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() throws Exception {

		plannerApiServiceImpl = aemContext.registerService(new PlannerApiServiceImpl());

		aemContext.registerService(PlannerApiServiceImpl.class, plannerApiServiceImpl);
		configuration = Mockito.mock(PlannerApiServiceImpl.Config.class);
		aemContext.addModelsForClasses(PlannerApiServiceImpl.class);
	}

	@Test
	void testActivate() {
		Mockito.lenient().when(configuration.plannersAPIEndpoint()).thenReturn(PLANNER_API_ENDPOINT);
		Mockito.lenient().when(configuration.locationsAPIEndpoint()).thenReturn(LOCATION_API_ENDPOINT);
		Mockito.lenient().when(configuration.authHeader()).thenReturn(AUTH_HEAD);

		plannerApiServiceImpl.getPlannersAPIEndpoint();
		plannerApiServiceImpl.getLocationsAPIEndpoint();
		plannerApiServiceImpl.getAuthHeader();

		assertNotNull(plannerApiServiceImpl);

		assertEquals(configuration.plannersAPIEndpoint(), PLANNER_API_ENDPOINT);
		assertEquals(configuration.locationsAPIEndpoint(), LOCATION_API_ENDPOINT);
		assertEquals(configuration.authHeader(), AUTH_HEAD);
		plannerApiServiceImpl.activate(configuration);

	}

}
