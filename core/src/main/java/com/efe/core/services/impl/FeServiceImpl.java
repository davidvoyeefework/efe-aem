package com.efe.core.services.impl;

import java.util.Arrays;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.services.FeService;

/**
 * The Class FeServiceImpl.
 */
@Designate(ocd = FeServiceImpl.Config.class)
@Component(service = FeService.class)
public class FeServiceImpl implements FeService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FeServiceImpl.class);

    /** The pageFrameApi. */
    private String pageFrameApi;

    /** The aggregateApi. */
    private String aggregateApi;

    /** The forKeyApi. */
    private String forKeyApi;

    /** The authenticateApi. */
    private String authenticateApi;

    /** The dynamicVariableList. */
    private String[] dynamicVariableList;

    /** The sponsorLogoPath. */
    private String sponsorLogoPath;

    /** The apiDomain. */
    private String apiDomain;


    /**
     * The Interface Config.
     */
    @ObjectClassDefinition(name = "FE Sponsor Details Api", description = "FE Sponsor Details Api")
    public static @interface Config {

        /**
         * PageFrame Api.
         *
         * @return pageFrameApi
         */
        @AttributeDefinition(name="PageFrame Api", description="PageFrame Api")
        String pageFrameApi();

        /**
         * Aggregate Api.
         *
         * @return aggregateApi
         */
        @AttributeDefinition(name="Aggregate Api", description="Aggregate Api")
        String aggregateApi();

        /**
         * For Key Api.
         *
         * @return forKeyApi
         */
        @AttributeDefinition(name="For Key Api", description="For Key Api")
        String forKeyApi();

        /**
         * Authenticate Api.
         *
         * @return authenticateApi
         */
        @AttributeDefinition(name="Authenticate Api", description="Authenticate Api")
        String authenticateApi();

        /**
         * Unbounce Dynamic Variables.
         *
         * @return dynamicVariableList
         */
        @AttributeDefinition(name = "Unbounce Dynamic Variables", description = "Unbounce Dynamic Variables")
        String[] dynamicVariableList();

        /**
         * Sponsor Logo Path.
         *
         * @return sponsorLogoPath
         */
        @AttributeDefinition(name = "Sponsor Logo Path", description = "Sponsor Logo Path")
        String sponsorLogoPath();

        /**
         * Api Domain.
         *
         * @return apiDomain
         */
        @AttributeDefinition(name = "Api Domain", description = "Api Domain")
        String apiDomain();
    }

    /**
     * Activate method to initialize stuff.
     *
     * @param config the config
     */
    @Activate
    @Modified
    public void activate(final FeServiceImpl.Config config) {
        LOGGER.debug("DynamicVariableServiceImpl.activate method called {}", "{}");
        this.pageFrameApi = config.pageFrameApi();
        this.aggregateApi = config.aggregateApi();
        this.forKeyApi = config.forKeyApi();
        this.authenticateApi = config.authenticateApi();
        this.dynamicVariableList = config.dynamicVariableList();
        this.sponsorLogoPath = config.sponsorLogoPath();
        this.apiDomain = config.apiDomain();
    }

    /**
     * Gets the PageFrameApi.
     *
     * @return the PageFrameApi
     */
    @Override
    public String getPageFrameApi() {
        return pageFrameApi;
    }

    /**
     * Gets the AggregateApi.
     *
     * @return the AggregateApi
     */
    @Override
    public String getAggregateApi() {
        return aggregateApi;
    }

    /**
     * Gets the ForKeyApi.
     *
     * @return the ForKeyApi
     */
    @Override
    public String getForKeyApi() {
        return forKeyApi;
    }

    /**
     * Gets the AuthenticateApi.
     *
     * @return the AuthenticateApi
     */
    @Override
    public String getAuthenticateApi() {
        return authenticateApi;
    }

    /**
     * Gets the DynamicVariableList.
     *
     * @return the DynamicVariableList
     */
    @Override
    public String[] getDynamicVariableList() {
    	String []variables = {};
    	if(null != dynamicVariableList) {
    		variables = Arrays.copyOf(dynamicVariableList, dynamicVariableList.length);
    	}
    	return variables;   
    }

    /**
     * Gets the SponsorLogoPath.
     *
     * @return the SponsorLogoPath
     */
    @Override
    public String getSponsorLogoPath() {
        return sponsorLogoPath;
    }

    /**
     * Gets the ApiDomain.
     *
     * @return the ApiDomain
     */
    @Override
    public String getApiDomain() {
        return apiDomain;
    }
}
