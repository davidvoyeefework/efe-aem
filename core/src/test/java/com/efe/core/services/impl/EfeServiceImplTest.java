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
class EfeServiceImplTest {

	/** Planner API END POINT **/
	private final String PLANNER_API_ENDPOINT = "https://planners.edelmanfinancialengines.com/api/v2/planners";

	/** Planner API END POINT **/
	private final String LOCATION_API_ENDPOINT = "https://planners.edelmanfinancialengines.com/api/v2/locations";

	/** Planner AUTH_HEAD **/
	private final String AUTH_HEAD = "dummy header";
	
	/** Planner PLANNER_PAGE_URL **/
	private final String PLANNER_PAGE_URL = "/content/efe/us/en/location";
	
	/** Planner PLANNER_BIO_PAGE_URL **/
	private final String PLANNER_BIO_PAGE_URL = "/content/efe/us/en/financial-planners";

	@Mock
	/** The efeServiceImpl. */
	private EfeServiceImpl efeServiceImpl;

	@Mock
	/** The configuration. */
	private EfeServiceImpl.Config configuration;

	public final AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() throws Exception {

		efeServiceImpl = aemContext.registerService(new EfeServiceImpl());

		aemContext.registerService(EfeServiceImpl.class, efeServiceImpl);
		configuration = Mockito.mock(EfeServiceImpl.Config.class);
		aemContext.addModelsForClasses(EfeServiceImpl.class);
	}

	@Test
	void testActivate() {
		Mockito.lenient().when(configuration.plannersAPIEndpoint()).thenReturn(PLANNER_API_ENDPOINT);
		Mockito.lenient().when(configuration.locationsAPIEndpoint()).thenReturn(LOCATION_API_ENDPOINT);
		Mockito.lenient().when(configuration.authHeader()).thenReturn(AUTH_HEAD);
		Mockito.lenient().when(configuration.plannerPageUrl()).thenReturn(PLANNER_PAGE_URL);
		Mockito.lenient().when(configuration.plannerBioPageUrl()).thenReturn(PLANNER_BIO_PAGE_URL);

		efeServiceImpl.getPlannersAPIEndpoint();
		efeServiceImpl.getLocationsAPIEndpoint();
		efeServiceImpl.getAuthHeader();
		efeServiceImpl.getPlannerPageUrl();
		efeServiceImpl.getPlannerBioPageUrl();

		assertNotNull(efeServiceImpl);

		assertEquals(configuration.plannersAPIEndpoint(), PLANNER_API_ENDPOINT);
		assertEquals(configuration.locationsAPIEndpoint(), LOCATION_API_ENDPOINT);
		assertEquals(configuration.authHeader(), AUTH_HEAD);
		assertEquals(configuration.plannerPageUrl(), PLANNER_PAGE_URL);
		assertEquals(configuration.plannerBioPageUrl(), PLANNER_BIO_PAGE_URL);
		efeServiceImpl.activate(configuration);

	}

}
