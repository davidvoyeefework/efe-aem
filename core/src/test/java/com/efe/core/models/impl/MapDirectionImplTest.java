package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.sling.testing.mock.sling.servlet.MockRequestPathInfo;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.commons.Externalizer;
import com.efe.core.models.MapDirection;
import com.efe.core.services.impl.EfeServiceImpl;
import com.efe.core.services.impl.SeoServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class MapDirectionImplTest {

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/** The EfeServiceImpl. */
	private EfeServiceImpl efeService;
	
	private SeoServiceImpl seoServiceImpl = new SeoServiceImpl();
	
	@Mock
	private Externalizer externalizer;

	@Mock
	/** The configuration. */
	private EfeServiceImpl.Config configuration;
	
	private MapDirection mapDirectionModel;

	@Test
	void testPositive() {

		MockSlingHttpServletRequest request = aemContext.request();
		MockRequestPathInfo requestPathInfo = (MockRequestPathInfo) request.getRequestPathInfo();
		aemContext.addModelsForClasses(ContentFragment.class);
		efeService = aemContext.registerService(new EfeServiceImpl());
		EfeServiceImpl.Config config = mock(EfeServiceImpl.Config.class);
		when(config.googleMapPublicApi()).thenReturn("googleMapPublicApi");
		efeService.activate(config);
		
		aemContext.registerInjectActivateService(seoServiceImpl);
		aemContext.registerService(externalizer);
		aemContext.load().json("/com/efe/core/models/maps/mapdirection.json", "/content");
		aemContext.currentResource("/content/efe/jcr:content/mapdirection");
		aemContext.request().setPathInfo("/content/efe/location.ks.wichita.html");
		requestPathInfo.setSelectorString("ks.wichita");

		mapDirectionModel = aemContext.request().adaptTo(MapDirection.class);
		assertEquals("37.721030", mapDirectionModel.getLocationResponse().getLatitude());
		assertEquals("-97.23856", mapDirectionModel.getLocationResponse().getLongitude());
		assertEquals("8621 E. 21st Street North", mapDirectionModel.getLocationResponse().getAddress1());
		assertEquals("Suite 225", mapDirectionModel.getLocationResponse().getAddress2());
		assertEquals("67206", mapDirectionModel.getLocationResponse().getZip());
		assertEquals("KS", mapDirectionModel.getLocationResponse().getState());
		assertEquals("https://g.page/r/CZ9_QBnGKAw0EBM/review",
				mapDirectionModel.getLocationResponse().getGoogleReviewLink());

		assertEquals("Leave a review.", mapDirectionModel.getReviewLinkLabel());
		assertEquals("VISITING OUR OFFICE", mapDirectionModel.getHeading());
		assertEquals("Already work with us?", mapDirectionModel.getReviewQuestion());
		assertEquals(9, mapDirectionModel.getZoomLevel());
		assertEquals("DIRECTIONS", mapDirectionModel.getDirectionButtonLabel());
		assertEquals("22", mapDirectionModel.getId());
		assertNotNull(mapDirectionModel.getGoogleDirectionPath());
		assertFalse(mapDirectionModel.isEmpty());
		assertEquals("googleMapPublicApi", mapDirectionModel.getMapKey());
		assertNotNull( mapDirectionModel.getJsonLd());

	}

}
