package com.efe.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.FeProperties;
import com.efe.core.services.FeService;

/**
 * The Class FePropertiesImpl.
 */
@Model(adaptables = SlingHttpServletRequest.class, adapters = FeProperties.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FePropertiesImpl implements FeProperties {
    /**
     * The fe service.
     */
    @OSGiService
    private FeService feService;

    /**
     * The request.
     */
    @Self
    private SlingHttpServletRequest request;

    /**
     * Gets the PageFrameApi.
     *
     * @return the PageFrameApi
     */
    @Override
    public String getPageFrameApi() {
        return feService.getPageFrameApi();
    }

    /**
     * Gets the AggregateApi.
     *
     * @return the AggregateApi
     */
    @Override
    public String getAggregateApi() {
        return feService.getAggregateApi();
    }

    /**
     * Gets the ForKeyApi.
     *
     * @return the ForKeyApi
     */
    @Override
    public String getForKeyApi() {
        return feService.getForKeyApi();
    }

    /**
     * Gets the AuthenticateApi.
     *
     * @return the AuthenticateApi
     */
    @Override
    public String getAuthenticateApi() {
        return feService.getAuthenticateApi();
    }

    /**
     * Gets the sponsor logo path.
     *
     * @return the sponsorLogoPath
     */
    @Override
    public String getSponsorLogoPath() {
        return feService.getSponsorLogoPath();
    }
}