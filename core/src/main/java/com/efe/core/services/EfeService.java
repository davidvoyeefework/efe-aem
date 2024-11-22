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

        String getPrintClientId();
        String getPrintClientSecret();
        String getPartnerAPIAuthURL();
        String getPartnerAPIAuthIssuer();
        String getPartnerAPIAuthAudience();
        String getPartnerAPIAuthSub();
        String getPartnerAPIAuthKID();
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

	/*
	 * Gets the form base URL.
	 *
	 * @return the form base URL
	 */
	String getFormBaseUrl();

	/**
	 * Gets the form js URL.
	 *
	 * @return the form js URL
	 */
	String getFormJsUrl();

	/**
	 * Gets the form auth header.
	 *
	 * @return the form auth header
	 */
	String getFormAuthHeader();

	/**
	 * Checks if is enabled GA.
	 *
	 * @return true, if is enabled GA
	 */
	boolean isEnabledGA();

	/**
	 * Gets the ga tag value.
	 *
	 * @return the ga tag value
	 */
	String getGaTagValue();
	
	
	/**
	 * Gets the omny playlist api.
	 *
	 * @return the omny playlist api
	 */
	String getOmnyPlaylistApi();
	
	
	/**
	 * Gets the omny episode api.
	 *
	 * @return the omny episode api
	 */
	String getOmnyEpisodeApi();
	
	
	/**
	 * Gets the omny org id.
	 *
	 * @return the omny org id
	 */
	String getOmnyOrgId();
	
	
	/**
	 * Gets the jquery url.
	 *
	 * @return the jquery url
	 */
	String getJqueryUrl();
        
        String getFPIDLibraryURL();

	/**
	 * Gets the external libraries.
	 *
	 * @return the externalLibraries
	 */
	String getExternalLibraries();

	/**
	 * Gets the nationalAdvisorCenter.
	 *
	 * @return the nationalAdvisorCenter
	 */
	String getNationalAdvisorCenter();

}
