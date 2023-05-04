package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.efe.core.models.MapSearchResults;
import com.efe.core.services.impl.EfeServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class MapSearchResultsImplTest.
 */
@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class MapSearchResultsImplTest {

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/** The EfeServiceImpl. */
	private EfeServiceImpl efeService = new EfeServiceImpl();

	/** The configuration. */
	@Mock
	/** The configuration. */
	private EfeServiceImpl.Config configuration;

	/**
	 * Test positive.
	 */
	@Test
	void testPositive() {

		aemContext.addModelsForClasses(ContentFragment.class);
		aemContext.registerInjectActivateService(efeService);
		aemContext.load().json("/com/efe/core/models/maps/mapdirection.json", "/content");
		aemContext.currentResource("/content/efe/jcr:content/mapsearchresults");

		MapSearchResults mapSearchResults = aemContext.request().adaptTo(MapSearchResults.class);

		assertEquals(
				"[{\"externalName\":\"Wichita\",\"address1\":\"8621 E. 21st Street North\",\"address2\":\"Suite 225\",\"city\":\"Wichita\",\"latitude\":\"37.721030\",\"longitude\":\"-97.23856\",\"zip\":\"67206\",\"state\":\"Kansas\",\"stateCode\":\"KS\",\"link\":\"null.KS.Wichita\"}]",
				mapSearchResults.getOfficesJson());

		assertEquals("Minneapolis, MN", mapSearchResults.getPlannerSearchPlaceHolder());
		assertEquals("Explore location", mapSearchResults.getExploreLinkLabel());
		assertEquals("Find a Planner", mapSearchResults.getPlannerButtonLabel());
		assertEquals("City, State or ZIP Code", mapSearchResults.getPlannerSearchLabel());
		assertEquals("SEARCH LOCATIONS", mapSearchResults.getSearchButtonLabel());
		assertEquals("title", mapSearchResults.getNationalTitle());
		assertEquals("111", mapSearchResults.getNationalCallbackNumber());
		assertEquals("link", mapSearchResults.getNationalLink());
		assertEquals("details", mapSearchResults.getNationalDetails());
		assertEquals("1", mapSearchResults.getId());
		assertNull(mapSearchResults.getMapKey());

	}

}
