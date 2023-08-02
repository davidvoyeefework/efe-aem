package com.efe.core.models;

import java.util.Map;

/**
 * The Interface FeProperties.
 */
public interface FeProperties {

    /**
     * Gets the PageFrameApi.
     *
     * @return the PageFrameApi
     */
    String getPageFrameApi();

    /**
     * Gets the AggregateApi.
     *
     * @return the AggregateApi
     */
    String getAggregateApi();

    /**
     * Gets the ForKeyApi.
     *
     * @return the ForKeyApi
     */
    String getForKeyApi();

    /**
     * Gets the SoftAuthApi.
     *
     * @return the SoftAuthApi
     */
    String getSoftAuthApi();

    /**
     * Gets the SignupApi.
     *
     * @return the SignupApi
     */
    String getSignupApi();

    /**
     * Gets the ScheduleApi.
     *
     * @return the ScheduleApi
     */
    String getScheduleApi();

    /**
     * Gets the AuthenticateApi.
     *
     * @return the AuthenticateApi
     */
    String getAuthenticateApi();

    /**
     * Gets the CallBackApi.
     *
     * @return the CallBackApi
     */
    String getCallBackApi();

    /**
     * Gets the dynamic variables.
     *
     * @return the dynamicVariables
     */
    Map<String, String> getDynamicVariables();

	/**
	 * Gets the record keeper.
	 *
	 * @return the recordKeeper
	 */
	String getRecordKeeper();

	/**
	 * Gets the sponsor id.
	 *
	 * @return the sponsorId
	 */
	String getSponsorId();
}
