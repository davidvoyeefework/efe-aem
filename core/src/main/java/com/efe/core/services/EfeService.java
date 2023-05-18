package com.efe.core.services;

/**
 * The Interface EfeService.
 */
public interface EfeService {

	/**
	 * Gets the planners.
	 *
	 * @return the planners
	 */
	String getPlannersAPIEndpoint();

	/**
	 * Gets the locations.
	 *
	 * @return the locations
	 */
	String getLocationsAPIEndpoint();

	/**
	 * Gets the authHeader.
	 *
	 * @return the username
	 */
	String getAuthHeader();
	
	/**
	 * Gets the plannerPageUrl.
	 *
	 * @return the plannerPageUrl
	 */
	String getPlannerPageUrl();
	
	/**
	 * Gets the plannerBioPageUrl.
	 *
	 * @return the plannerBioPageUrl
	 */
	String getPlannerBioPageUrl();
	
	/**
	 * Method to get the Google Public Key.
	 *
	 * @return the GooglePublicKey
	 */
	String getGooglePublicKey();
	
	/**
	 * Method to get Google direction prefix URL.
	 *
	 * @return the GoogleDirectionPrefixUrl
	 */
	String getGoogleDirectionPrefixUrl();
	
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
	 * Gets the analytics site root level.
	 *
	 * @return the analyticsSiteRootLevel
	 */
	int getAnalyticsSiteRootLevel();

	/**
	 * Gets the link tracking list path.
	 *
	 * @return the linkTrackingListPath
	 */
	String getLinkTrackingListPath();

}
