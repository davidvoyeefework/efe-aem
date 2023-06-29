package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.commons.Externalizer;
import com.efe.core.models.EFEDatalayer;
import com.efe.core.services.EfeService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class EFEDatalayerImplTest {

	private AemContext aemContext = new AemContext();

	@Mock
	private Externalizer externalizer;

	@Mock
	private EfeService efeService;
	
	private EFEDatalayer datalayer;

	
	@Test
	void test() {
		
		aemContext.registerService(Externalizer.class, externalizer);
		aemContext.registerService(EfeService.class, efeService);
		
		aemContext.request().setAttribute("type", "page");
		aemContext.requestPathInfo().setSelectorString("formevent.form name");
		aemContext.load().json("/com/efe/core/models/efedatalayer.json", "/content");
		aemContext.currentResource("/content/efe/page1/page2");
		
		when(efeService.getOneTrustScript()).thenReturn("test");
		when(efeService.getOneTrustScriptId()).thenReturn("id");
		when(efeService.getGaTagValue()).thenReturn("GaTagValue");
		datalayer = aemContext.request().adaptTo(EFEDatalayer.class);
		
		assertNotNull(datalayer.getDataLayer());
		assertEquals("test", datalayer.getOneTrustScript());
		assertEquals("id", datalayer.getOneTrustScriptId());
		assertEquals("form name", datalayer.getFormName());
		assertEquals("[]", datalayer.getTrackingLinksJson());
		assertEquals(false, datalayer.isEnableGA());
		assertEquals("GaTagValue", datalayer.getGaTagValue());
	}

}
