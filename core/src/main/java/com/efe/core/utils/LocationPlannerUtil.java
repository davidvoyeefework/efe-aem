package com.efe.core.utils;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.efe.core.constants.PlannerLocationConstants;

public class LocationPlannerUtil {

	private LocationPlannerUtil() {
	}

	/**
	 * Method to get the Location CF Resource Node
	 * 
	 * @param resourceResolver Resource Resolver
	 * @param state            state ABBV
	 * @param city             city Name
	 * @return Location CF Resource
	 */
	public static Resource getLocationResource(ResourceResolver resourceResolver, String state, String city) {
		String locationPath = PlannerLocationConstants.ROOT_FOLDER_PATH + PlannerLocationConstants.FORWARD_SLASH
				+ PlannerLocationConstants.LOCATIONS + PlannerLocationConstants.FORWARD_SLASH + state
				+ PlannerLocationConstants.FORWARD_SLASH + city;
		return resourceResolver.getResource(locationPath);
	}
}
