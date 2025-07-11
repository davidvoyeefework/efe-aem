package com.efe.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.CDTScheduler;
import javax.annotation.PostConstruct;
import com.efe.core.services.EfeService;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

/**
 * The Class FormsSelectorImpl.
 */
@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = CDTScheduler.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = CDTSchedulerImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CDTSchedulerImpl implements CDTScheduler {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "efe/components/cdtscheduler";
        
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

	/** The SalesForce lead ID. */
	@ValueMapValue
	private String SFLeadId;
        
        /** The showDisclosure flag. */
        @ValueMapValue
	private String showDisclosure;
        
        /** The disableHeader flag. */
	@ValueMapValue
        private String disableHeader;
        
        /**
	 * Inits the model.
	 */
	@PostConstruct
	public void init() {
            String[] selectors = request.getRequestPathInfo().getSelectors();
            if(selectors.length > 0) {
                SFLeadId = selectors[0];
            }
        }

	/**
	 * Gets the SalesForce lead ID.
	 *
	 * @return the SalesForce lead ID.
	 */
        @Override
	public String getSFLeadId() {
            return SFLeadId;
	}
        
        /**
         * Gets flag to determine whether to display the disclosure
         * 
         * @return the showDisclosure flag
         */
        @Override
        public String getShowDisclosure() {
            if(!showDisclosure.isEmpty()) {
                return showDisclosure;
            } else {
                return "false";
            }
        }
        
        /**
         * Gets flag to determine whether to display the header
         * 
         * @return the showHeader flag
         */
        @Override
        public String getDisableHeader() {
            if(!disableHeader.isEmpty()) {
                return disableHeader;
            } else {
                return "true";
            }
        }
        
        /**
	 * Gets the CDT scheduler embed URL.
	 *
	 * @return the CDT scheduler embed URL.
	 */
        @Override
        public String getCDTEmbedURL() {
            return efeService.getCDTEmbedURL();
        }
}
