package com.efe.core.schedulers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.resourceresolver.MockResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.efe.core.services.impl.LocationModelServicesImpl;
import com.efe.core.services.impl.PlannerModelServicesImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class SlingJobTest
 *
 */
@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class SlingJobTest {

	/** The aem context. */
	private final AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);

	/** The resourceResolver. */
	@Mock
	private ResourceResolver resourceResolver;

	/** Mock ResourceResolverFactory. */
	@Mock
	private ResourceResolverFactory resourceResolverFactory;

	/** Mock locationModelServices. */
	@Mock
	private LocationModelServicesImpl locationModelServices;

	/** Mock plannerModelServices. */
	@Mock
	private PlannerModelServicesImpl plannerModelServices;

	/** Mock mockJob. */
	@Mock
	private Job mockJob;

	/** Mock slingJob. */
	@InjectMocks
	private SlingJob slingJob = new SlingJob();

	/** set Up method. */
	@BeforeEach
	void setUp() throws Exception {

		final Map<String, Object> subServiceUser = new ConcurrentHashMap<>(); 
		subServiceUser.put(ResourceResolverFactory.SUBSERVICE, "efe-service-user");

		aemContext.registerService(ResourceResolver.class, resourceResolver);
		aemContext.registerService(ResourceResolverFactory.class, new MockResourceResolverFactory());
		lenient().when(resourceResolverFactory.getServiceResourceResolver(subServiceUser)).thenReturn(resourceResolver);

		aemContext.registerService(LocationModelServicesImpl.class, locationModelServices);
		aemContext.registerService(PlannerModelServicesImpl.class, plannerModelServices);

	}

	@Test
	void testProcess() {
		assertEquals(JobConsumer.JobResult.OK, slingJob.process(mockJob));
	}

}
