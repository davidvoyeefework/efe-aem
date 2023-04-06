package com.efe.core.services;

/**
 * The Interface PlannerApiService.
 */
public interface PlannerApiService {

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

}
