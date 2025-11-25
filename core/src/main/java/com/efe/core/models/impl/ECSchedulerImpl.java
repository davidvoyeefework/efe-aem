package com.efe.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.ECScheduler;
import javax.annotation.PostConstruct;
import com.efe.core.services.EfeService;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

/**
 * The Class FormsSelectorImpl.
 */
@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = ECScheduler.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = ECSchedulerImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ECSchedulerImpl implements ECScheduler {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "efe/components/ecscheduler";
        
        /** The SlingHttpServletRequest. */
	@Self
	private SlingHttpServletRequest request;

        /**
	 * The current resource.
	 */
	@SlingObject
	private Resource resource;
        
        /** The efe service. */
	@OSGiService
	private EfeService efeService;

	/** The Container div ID. */
	@ValueMapValue
	private String ContainerID;
        
        /**
	 * Inits the model.
	 */
	@PostConstruct
	public void init() {

        }

	/**
	 * Gets the SalesForce lead ID.
	 *
	 * @return the SalesForce lead ID.
	 */
        @Override
	public String getContainerID() {
            return ContainerID;
	}

        /**
	 * Gets the CDT scheduler embed URL.
	 *
	 * @return the CDT scheduler embed URL.
	 */
        @Override
        public String getECEmbedURL() {
            return efeService.getECEmbedURL();
        }
}
