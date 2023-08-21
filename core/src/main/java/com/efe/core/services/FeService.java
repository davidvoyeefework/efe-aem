package com.efe.core.services;

/**
 * The Interface FeService.
 */
public interface FeService {

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
     * Gets the DynamicVariableList.
     *
     * @return the DynamicVariableList
     */
    String[] getDynamicVariableList();

    /**
     * Gets the SponsorLogoPath.
     *
     * @return the SponsorLogoPath
     */
    String getSponsorLogoPath();
}
