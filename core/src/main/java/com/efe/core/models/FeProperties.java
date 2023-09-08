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
     * Gets the AuthenticateApi.
     *
     * @return the AuthenticateApi
     */
    String getAuthenticateApi();

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

    /**
     * Gets the sponsor logo path.
     *
     * @return the sponsorLogoPath
     */
    String getSponsorLogoPath();
}
