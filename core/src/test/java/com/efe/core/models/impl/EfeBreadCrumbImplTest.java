package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.ModelFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.Constants;

import com.adobe.cq.wcm.core.components.commons.link.Link;
import com.adobe.cq.wcm.core.components.internal.models.v1.datalayer.ComponentDataImpl;
import com.adobe.cq.wcm.core.components.models.Breadcrumb;
import com.adobe.cq.wcm.core.components.models.NavigationItem;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.efe.core.models.EfeBreadCrumb;
import com.efe.core.services.SeoService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class EfeBreadCrumbImplTest {

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	@Mock
	private SeoService seoService;

	@Mock
	private Breadcrumb breadcrumb;

	@Mock
	private Externalizer externalizer;

	@Mock
	private ModelFactory modelFactory;

	@Mock
	NavigationItem item;
	
	@Mock
	Link<Page> link;

	@Test
	void testBreadCrumb() {

		lenient().when(modelFactory.getModelFromWrappedRequest(eq(aemContext.request()), any(Resource.class),
				eq(Breadcrumb.class))).thenReturn(breadcrumb);

		aemContext.registerService(ModelFactory.class, modelFactory, Constants.SERVICE_RANKING, Integer.MAX_VALUE);
		aemContext.registerService(SeoService.class, seoService);
		aemContext.registerService(Externalizer.class, externalizer);

		List<NavigationItem> items = new ArrayList<>();
		items.add(item);
		
		ComponentData data = new ComponentDataImpl(null);
		when(breadcrumb.getItems()).thenReturn(items);
		when(breadcrumb.getId()).thenReturn("id");
		when(breadcrumb.getAppliedCssClasses()).thenReturn("class");
		when(breadcrumb.getExportedType()).thenReturn("resourceType");
		when(breadcrumb.getData()).thenReturn(data);
		when(item.getLink()).thenReturn(link);

		aemContext.load().json("/com/efe/core/models/breadcrumb.json", "/content");
		aemContext.currentResource("/content/efe/page1/page2/jcr:content/breadcrumb");
		aemContext.requestPathInfo().setSelectorString("AL");

		EfeBreadCrumb breadCrumb = aemContext.request().adaptTo(EfeBreadCrumb.class);
		
		assertEquals("id", breadCrumb.getId());
		assertEquals("class", breadCrumb.getAppliedCssClasses());
		assertEquals("resourceType", breadCrumb.getExportedType());
		assertNotNull(breadCrumb.getData());
		assertNotNull(breadCrumb.getItems());
		assertNotNull(breadCrumb.getJsonLd());
	}

}
