package com.efe.core.models;

import java.util.Map;

/**
 * This interface defines a location list that contains information about states and their cities.
 *
 */
public interface LocationList {
	
	/**
     * Gets the id.
     *
     * @return the id
     */
    String getId();

	/**
	 * Returns a map of states and their cities.
	 * @return a map of states and their cities.
	 */
	public Map<String, Map<String,String>> getStates();
		
	
}
