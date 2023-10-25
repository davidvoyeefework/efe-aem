package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.efe.core.models.FeProperties;
import com.efe.core.services.FeService;
import com.efe.core.services.impl.FeServiceImplTest;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class FePropertiesImplTest.
 */
@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class FePropertiesImplTest {

	/** The aem context. */
	AemContext aemContext = new AemContext();

	/** The fe service. */
	@Mock
	FeService feService;

	/** The fe properties. */
	FeProperties feProperties;

	/**
	 * Test.
	 */
	@Test
	void test() {

		aemContext.registerService(FeService.class, feService);

		aemContext.load().json("/com/efe/core/models/efedatalayer.json", "/content");
		aemContext.currentResource("/content/efe/page1/page2");

		when(feService.getAggregateApi()).thenReturn(FeServiceImplTest.AGG_API);
		when(feService.getApiDomain()).thenReturn(FeServiceImplTest.DOMAIN);
		when(feService.getAuthenticateApi()).thenReturn(FeServiceImplTest.AUTH_API);
		when(feService.getPageFrameApi()).thenReturn(FeServiceImplTest.PAGE_FRAME_API);
		when(feService.getFeGtmTagId()).thenReturn(FeServiceImplTest.GTM_TAG_ID);
		when(feService.getForKeyApi()).thenReturn(FeServiceImplTest.FOR_KEYS_API);
		when(feService.getSponsorLogoPath()).thenReturn(FeServiceImplTest.LOGO_PATH);
		when(feService.enableFeGtm()).thenReturn(false);

		feProperties = aemContext.request().adaptTo(FeProperties.class);

		assertEquals(FeServiceImplTest.AGG_API, feProperties.getAggregateApi());
		assertEquals(FeServiceImplTest.DOMAIN, feProperties.getApiDomain());
		assertEquals(FeServiceImplTest.AUTH_API, feProperties.getAuthenticateApi());
		assertEquals(FeServiceImplTest.PAGE_FRAME_API, feProperties.getPageFrameApi());
		assertEquals(FeServiceImplTest.GTM_TAG_ID, feProperties.getFeGtmTagId());
		assertEquals(FeServiceImplTest.FOR_KEYS_API, feProperties.getForKeyApi());
		assertEquals(FeServiceImplTest.LOGO_PATH, feProperties.getSponsorLogoPath());
		assertEquals(false, feProperties.isFeGtmEnabled());

	}

}
