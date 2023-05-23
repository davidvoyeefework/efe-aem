package com.efe.core.models;

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

}
