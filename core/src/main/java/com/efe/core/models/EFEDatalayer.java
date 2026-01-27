package com.efe.core.models;

/**
 * The Interface EFEDatalayer.
 */
public interface EFEDatalayer {

	/**
	 * Gets the data layer.
	 *
	 * @return the dataLayer
	 */
	String getDataLayer();

	/**
	 * Gets the one trust script.
	 *
	 * @return the one trust script
	 */
	String getOneTrustScript();

	/**
	 * Gets the one trust script id.
	 *
	 * @return the one trust script id
	 */
	String getOneTrustScriptId();

	/**
	 * Gets the tracking links json.
	 *
	 * @return the trackingLinksJson
	 */
	String getTrackingLinksJson();

	/**
	 * Gets the form name.
	 *
	 * @return the formName
	 */
	String getFormName();

	/**
	 * Gets the ga tag value.
	 *
	 * @return the gaTagValue
	 */
	String getGaTagValue();

	/**
	 * Checks if is enable GA.
	 *
	 * @return the enableGA
	 */
	boolean isEnableGA();

	/**
	 * Gets the FE one trust script.
	 *
	 * @return the FE one trust script
	 */
	String getOneTrustFEScript();

    /**
     * Gets the FE one trust script id.
     *
     * @return the FE one trust script id
     */
    String getOneTrustFEScriptId();

}
