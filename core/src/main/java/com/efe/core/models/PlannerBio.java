package com.efe.core.models;

import java.util.List;

import com.efe.core.bean.LocationResponse;
import com.efe.core.bean.PlannerResponse;

/**
 * The Planner Bio Model.
 */
public interface PlannerBio {
	
	/**
	 * Gets the planner response.
	 *
	 * @return the planner response
	 */
	PlannerResponse getPlannerResponse();

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();

	/**
	 * Checks if is empty.
	 *
	 * @return the empty
	 */
	boolean isEmpty();

	/**
	 * Gets the json ld.
	 *
	 * @return the json ld
	 */
	String getJsonLd();

	/**
	 * Gets the office locations.
	 *
	 * @return the officeLocations
	 */
	List<LocationResponse> getOfficeLocations();
}
