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

    /** The softAuthApi. */
    private String softAuthApi;

    /** The signupApi. */
    private String signupApi;

    /** The scheduleApi. */
    private String scheduleApi;

    /** The authenticateApi. */
    private String authenticateApi;

    /** The callBackApi. */
    private String callBackApi;

    /** The dynamicVariableList. */
    private String[] dynamicVariableList;

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
         * Soft Auth Api.
         *
         * @return softAuthApi
         */
        @AttributeDefinition(name="Soft Auth Api", description="Soft Auth Api")
        String softAuthApi();

        /**
         * Signup Api.
         *
         * @return signupApi
         */
        @AttributeDefinition(name="Signup Api", description="Signup Api")
        String signupApi();

        /**
         * Schedule Api.
         *
         * @return scheduleApi
         */
        @AttributeDefinition(name="Schedule Api", description="Schedule Api")
        String scheduleApi();

        /**
         * Authenticate Api.
         *
         * @return authenticateApi
         */
        @AttributeDefinition(name="Authenticate Api", description="Authenticate Api")
        String authenticateApi();

        /**
         * CallBack Api.
         *
         * @return callBackApi
         */
        @AttributeDefinition(name = "CallBack Api", description = "CallBack Api")
        String callBackApi();

        /**
         * Unbounce Dynamic Variables.
         *
         * @return dynamicVariableList
         */
        @AttributeDefinition(name = "Unbounce Dynamic Variables", description = "Unbounce Dynamic Variables")
        String[] dynamicVariableList();
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
        this.callBackApi = config.callBackApi();
        this.authenticateApi = config.authenticateApi();
        this.scheduleApi = config.scheduleApi();
        this.signupApi = config.signupApi();
        this.softAuthApi = config.softAuthApi();
        this.dynamicVariableList = config.dynamicVariableList();
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
     * Gets the SoftAuthApi.
     *
     * @return the SoftAuthApi
     */
    @Override
    public String getSoftAuthApi() {
        return softAuthApi;
    }

    /**
     * Gets the SignupApi.
     *
     * @return the SignupApi
     */
    @Override
    public String getSignupApi() {
        return signupApi;
    }

    /**
     * Gets the ScheduleApi.
     *
     * @return the ScheduleApi
     */
    @Override
    public String getScheduleApi() {
        return scheduleApi;
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
     * Gets the CallBackApi.
     *
     * @return the CallBackApi
     */
    @Override
    public String getCallBackApi() {
        return callBackApi;
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
}
