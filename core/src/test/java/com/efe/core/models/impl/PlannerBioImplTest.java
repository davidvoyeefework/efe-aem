package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Iterator;

import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.efe.core.services.impl.DynamicMediaServiceImpl;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.servlet.MockRequestPathInfo;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.granite.references.Reference;
import com.adobe.granite.references.ReferenceAggregator;
import com.adobe.granite.references.ReferenceList;
import com.day.cq.commons.Externalizer;
import com.efe.core.models.PlannerBio;
import com.efe.core.services.EfeService;
import com.efe.core.services.SeoService;
import com.efe.core.services.impl.EfeServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class PlannerBioImplTest {

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/** The EfeServiceImpl. */
	@Mock
	private EfeServiceImpl efeService;
	
	@Mock
	private Externalizer externalizer;

	/** The seo service. */
	@Mock
	private SeoService seoService;

	/** The queryBuilder. */
	@Mock
	private QueryBuilder queryBuilder;


	/** The aggregator. */
	@Mock
	private ReferenceAggregator aggregator;
	
	@Mock
	private ReferenceList referenceList;
	
	@Mock
	private Iterator<Reference> referenceItr;

	@Mock
	private Reference reference;
	
	@Mock
	private Resource resource;

	/** The DynamicMediaServiceImpl. */
	private DynamicMediaServiceImpl dynamicMediaService = new DynamicMediaServiceImpl();

	/** The configuration. */
	@Mock
	private EfeServiceImpl.Config configuration;

	
	@BeforeEach
	void init() {
		
		aemContext.addModelsForClasses(ContentFragment.class);
		aemContext.registerService(Externalizer.class, externalizer);
		aemContext.registerService(EfeService.class, efeService);
		aemContext.registerService(SeoService.class, seoService);
		aemContext.registerService(ReferenceAggregator.class, aggregator);
		aemContext.registerService(QueryBuilder.class, queryBuilder);
		
		aemContext.load().json("/com/efe/core/models/maps/mapdirection.json", "/content");
        aemContext.load().json("/com/efe/core/models/articleDetails/planner.json", "/content/dam/efe/cf/plannerlocation/planners/179/fragment_johnathan_179");
        aemContext.load().json("/com/efe/core/models/articleDetails/education.json", "/content/dam/efe/test-education-cf");
        aemContext.load().json("/com/efe/core/models/articleDetails/certification.json", "/content/dam/efe/test-certification");
		aemContext.registerInjectActivateService(dynamicMediaService);
		
	}
	@Test
	void testAllDialogValues() {
		aemContext.currentResource("/content/efe/jcr:content/plannerbio");
		PlannerBio plannerBio = aemContext.request().adaptTo(PlannerBio.class);
		
		assertEquals("/content/efe.html", plannerBio.getAppointmentCta());
		assertEquals("Request An Appoitnment", plannerBio.getAppointmentCtaLabel());
		assertEquals("Best Interests", plannerBio.getAboutSectionHeading());
		assertEquals("About Me", plannerBio.getAboutMeHeading());
		assertEquals("Educations", plannerBio.getEducationHeading());
		assertEquals("Certifications", plannerBio.getCertificationHeading());
		assertEquals("Visit one of our locations", plannerBio.getLocationSectionHeading());
		assertEquals("Explore locations", plannerBio.getExploreLinkLabel());
		assertEquals("1", plannerBio.getId());
		assertTrue(plannerBio.isEmpty());
		assertEquals(0, plannerBio.getOfficeLocations().size());
		assertEquals("#", plannerBio.getFileReference());
	}
	
	@Test
	void testPlannerDetails() {
		MockSlingHttpServletRequest request = aemContext.request();
		MockRequestPathInfo requestPathInfo = (MockRequestPathInfo) request.getRequestPathInfo();
		requestPathInfo.setSelectorString("Johnathan.middlename.179");
        resource = aemContext.currentResource("/content/efe/jcr:content/plannerbio");
    	PlannerBio plannerBio = aemContext.request().adaptTo(PlannerBio.class);
		Query query = mock(Query.class);
		lenient().when(queryBuilder.createQuery(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(query);
        assertEquals("Johnathan", plannerBio.getPlannerResponse().getFirstName());
		assertEquals(0, plannerBio.getOfficeLocations().size());
	}
}
