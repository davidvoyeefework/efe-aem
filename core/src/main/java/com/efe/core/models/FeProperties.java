package com.efe.core.models;

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
     * Gets the AuthenticateApi.
     *
     * @return the AuthenticateApi
     */
    String getAuthenticateApi();

    /**
     * Gets the sponsor logo path.
     *
     * @return the sponsorLogoPath
     */
    String getSponsorLogoPath();

    /**
     * Gets the ApiDomain.
     *
     * @return the ApiDomain
     */
    String getApiDomain();

	/**
	 * Gets the fe gtm tag id.
	 *
	 * @return the fe gtm tag id
	 */
	String getFeGtmTagId();

	/**
	 * Checks if is fe gtm enabled.
	 *
	 * @return true, if is fe gtm enabled
	 */
	boolean isFeGtmEnabled();
}
