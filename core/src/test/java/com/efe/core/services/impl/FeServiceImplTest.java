package com.efe.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;

/**
 * The Class FeServiceImplTest.
 */
@ExtendWith(MockitoExtension.class)
public class FeServiceImplTest {

	/** The feServiceImpl. */
	private FeServiceImpl feServiceImpl = new FeServiceImpl();

	/** The configuration. */
	@Mock
	/** The configuration. */
	private FeServiceImpl.Config configuration;

	/** The aem context. */
	public final AemContext aemContext = new AemContext();

	/** The Constant AGG_API. */
	public final static String AGG_API = "/aggregate/api";

	/** The Constant PAGE_FRAME_API. */
	public final static String PAGE_FRAME_API = "/pageframe/api";

	/** The Constant FOR_KEYS_API. */
	public final static String FOR_KEYS_API = "/keys/api";

	/** The Constant AUTH_API. */
	public final static String AUTH_API = "/auth/api";

	/** The Constant LOGO_PATH. */
	public final static String LOGO_PATH = "/content/dam/efe/logo/test-{}.png";

	/** The Constant GTM_TAG_ID. */
	public final static String GTM_TAG_ID = "GTM-999";

	/** The Constant DOMAIN. */
	public final static String DOMAIN = "https://feitest.com";

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		aemContext.registerService(FeServiceImpl.class, feServiceImpl);
		configuration = Mockito.mock(FeServiceImpl.Config.class);
	}

	/**
	 * Test activate.
	 */
	@Test
	void testActivate() {

		when(configuration.aggregateApi()).thenReturn(AGG_API);
		when(configuration.apiDomain()).thenReturn(DOMAIN);
		when(configuration.authenticateApi()).thenReturn(AUTH_API);
		when(configuration.pageFrameApi()).thenReturn(PAGE_FRAME_API);
		when(configuration.dynamicVariableList()).thenReturn(new String[0]);
		when(configuration.feGtmEnabled()).thenReturn(false);
		when(configuration.feGtmTagId()).thenReturn(GTM_TAG_ID);
		when(configuration.forKeyApi()).thenReturn(FOR_KEYS_API);
		when(configuration.sponsorLogoPath()).thenReturn(LOGO_PATH);

		feServiceImpl.activate(configuration);

		assertEquals(AGG_API, feServiceImpl.getAggregateApi());
		assertEquals(DOMAIN, feServiceImpl.getApiDomain());
		assertEquals(AUTH_API, feServiceImpl.getAuthenticateApi());
		assertEquals(PAGE_FRAME_API, feServiceImpl.getPageFrameApi());
		assertEquals(GTM_TAG_ID, feServiceImpl.getFeGtmTagId());
		assertEquals(FOR_KEYS_API, feServiceImpl.getForKeyApi());
		assertEquals(LOGO_PATH, feServiceImpl.getSponsorLogoPath());
		assertEquals(false, feServiceImpl.enableFeGtm());
		assertEquals(0, feServiceImpl.getDynamicVariableList().length);

	}

}
