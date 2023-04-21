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

}
