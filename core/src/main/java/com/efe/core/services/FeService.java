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

    /**
     * Gets the ApiDomain.
     *
     * @return the ApiDomain
     */
    String getApiDomain();
    
    /**
     * Gets the FE gtm tag id.
     *
     * @return the FE gtm tag id
     */
    String getFeGtmTagId();

    /**
	 * Gets the FE one trust script.
	 *
	 * @return the FE one trust script
	 */
	String getFEOneTrustScript();

	/**
	 * Gets the FE one trust script id.
	 *
	 * @return the FE one trust script id
	 */
	String getFEOneTrustScriptId();
    
    /**
     * Enable fe gtm.
     *
     * @return true, if successful
     */
    boolean enableFeGtm();
}
