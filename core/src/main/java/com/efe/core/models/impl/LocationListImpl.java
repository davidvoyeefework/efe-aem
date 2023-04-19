package com.efe.core.models.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.constants.StatesEnum;
import com.efe.core.models.LocationList;
import com.efe.core.utils.LinkUtil;

/**
 * The Class LocationListImpl.
 */
@Model(adaptables = Resource.class, adapters = LocationList.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LocationListImpl implements LocationList {
	
	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LocationListImpl.class);

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The States Map. */
	@Inject
	private Map<String, Map<String, String>> states;

	/**
	 * Returns a Map containing state names as keys and another Map as city and url.
	 * The returned Map represents a collection of states and their .associated
	 * cities and url
	 *
	 * @return a Map containing state names and their associated cities and url.
	 */
	public Map<String, Map<String, String>> getStates() {
		return states;
	}

	/**
	 * This method generates a list of locations grouped by state and city, and
	 * stores it in the "states" HashMap.
	 */
	@PostConstruct
	public void generateLocationList() {
		states = new HashMap<String, Map<String, String>>();
		Resource locationResource = resourceResolver.getResource(PlannerLocationConstants.LOCATION_PATH);
		if (Objects.nonNull(locationResource)) {
			for (Resource stateResource : locationResource.getChildren()) {
				if (stateResource.isResourceType(JcrResourceConstants.NT_SLING_ORDERED_FOLDER)) {
					Map<String, String> cityMap = new HashMap<String, String>();
					for (Resource cityResource : stateResource.getChildren()) {
						String cityUrl = LinkUtil.getFormattedLink(
								PlannerLocationConstants.FORWARD_SLASH + PlannerLocationConstants.LOCATIONS + PlannerLocationConstants.DOT + stateResource.getName() + PlannerLocationConstants.DOT + cityResource.getName().replace(" ", "-") + PlannerLocationConstants.HTML,
								resourceResolver);
						LOGGER.debug("City Url {}", cityUrl);
						cityMap.put(toCamelCase(cityResource.getName()), cityUrl);
					}
					 StatesEnum stateEnum = StatesEnum.valueOf(stateResource.getName().toUpperCase());
					 states.put(stateEnum.getStateName(), cityMap);
				}
			}
		}
	}
	
	/**
	 * Converts a given string to camel case format.
	 * @param str the string to be converted to camel case.
	 * @return the string in camel case format.
	 */
	private String toCamelCase(String str) {		
	String[] stringArr = str.split(PlannerLocationConstants.SPACE);
	String resString="";
	for(String a : stringArr ) {
		resString = resString.concat(PlannerLocationConstants.SPACE).concat(a.substring(0, 1).toUpperCase()).concat(a.substring(1));
	} 		
		return resString.trim();
		
	}
}
