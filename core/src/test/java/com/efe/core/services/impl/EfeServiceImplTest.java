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
	
	/** The one trust script. */
	private final String ONE_TRUST_SCRIPT = "https://sdk-jd.js";
	
	/** The one trust script id. */
	private final String ONE_TRUST_SCRIPT_ID = "111-222-333";


	/** The efeServiceImpl. */
	private EfeServiceImpl efeServiceImpl = new EfeServiceImpl();

	@Mock
	/** The configuration. */
	private EfeServiceImpl.Config configuration;

	public final AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() {
		aemContext.registerService(EfeServiceImpl.class, efeServiceImpl);
		configuration = Mockito.mock(EfeServiceImpl.Config.class);
	}

	@Test
	void testActivate() {
		Mockito.lenient().when(configuration.plannersAPIEndpoint()).thenReturn(PLANNER_API_ENDPOINT);
		Mockito.lenient().when(configuration.locationsAPIEndpoint()).thenReturn(LOCATION_API_ENDPOINT);
		Mockito.lenient().when(configuration.authHeader()).thenReturn(AUTH_HEAD);
		Mockito.lenient().when(configuration.plannerPageUrl()).thenReturn(PLANNER_PAGE_URL);
		Mockito.lenient().when(configuration.plannerBioPageUrl()).thenReturn(PLANNER_BIO_PAGE_URL);
		Mockito.lenient().when(configuration.onetrustScript()).thenReturn(ONE_TRUST_SCRIPT);
		Mockito.lenient().when(configuration.onetrustScriptId()).thenReturn(ONE_TRUST_SCRIPT_ID);
		Mockito.lenient().when(configuration.googleMapPublicApi()).thenReturn("googleMapApi");
		Mockito.lenient().when(configuration.googleDirectionPrefixUrl()).thenReturn("url");
		Mockito.lenient().when(configuration.analyticsSiteRootLevel()).thenReturn(2);
		Mockito.lenient().when(configuration.linkTrackingListPath()).thenReturn("listPath");
		Mockito.lenient().when(configuration.formBaseUrl()).thenReturn("formBaseUrl");
		Mockito.lenient().when(configuration.formJsUrl()).thenReturn("formJsUrl");
		Mockito.lenient().when(configuration.formAuthHeader()).thenReturn("formAuthHeader");
		Mockito.lenient().when(configuration.enableGA()).thenReturn(true);
		Mockito.lenient().when(configuration.gaTagValue()).thenReturn("tagValue");
		Mockito.lenient().when(configuration.omnyPlaylistApi()).thenReturn("omnyPlaylistApi");
		Mockito.lenient().when(configuration.omnyEpisodeApi()).thenReturn("episode12");
		Mockito.lenient().when(configuration.omnyOrgId()).thenReturn("orgId2314");

		efeServiceImpl.activate(configuration);

		assertEquals("googleMapApi", efeServiceImpl.getGooglePublicKey());
		assertEquals("url", efeServiceImpl.getGoogleDirectionPrefixUrl());
		assertEquals(2, efeServiceImpl.getAnalyticsSiteRootLevel());
		assertEquals("listPath", efeServiceImpl.getLinkTrackingListPath());
		assertEquals("formBaseUrl", efeServiceImpl.getFormBaseUrl());
		assertEquals("formJsUrl", efeServiceImpl.getFormJsUrl());
		assertEquals("formAuthHeader", efeServiceImpl.getFormAuthHeader());
		assertTrue(efeServiceImpl.isEnabledGA());
		assertEquals("tagValue", efeServiceImpl.getGaTagValue());
		assertEquals("omnyPlaylistApi", efeServiceImpl.getOmnyPlaylistApi());
		assertEquals("episode12", efeServiceImpl.getOmnyEpisodeApi());
		assertEquals("orgId2314", efeServiceImpl.getOmnyOrgId());
		assertEquals(efeServiceImpl.getPlannersAPIEndpoint(), PLANNER_API_ENDPOINT);
		assertEquals(efeServiceImpl.getLocationsAPIEndpoint(), LOCATION_API_ENDPOINT);
		assertEquals(efeServiceImpl.getAuthHeader(), AUTH_HEAD);
		assertEquals(efeServiceImpl.getPlannerPageUrl(), PLANNER_PAGE_URL);
		assertEquals(efeServiceImpl.getPlannerBioPageUrl(), PLANNER_BIO_PAGE_URL);
		assertEquals(ONE_TRUST_SCRIPT, efeServiceImpl.getOneTrustScript());
		assertEquals(ONE_TRUST_SCRIPT_ID, efeServiceImpl.getOneTrustScriptId());
	}

}
