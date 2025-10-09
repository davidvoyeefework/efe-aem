package com.efe.core.models.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import com.day.cq.dam.api.DamConstants;
import com.day.cq.dam.commons.util.DamUtil;
import com.efe.core.utils.ResourceUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.constants.StatesEnum;
import com.efe.core.models.LocationList;
import com.efe.core.services.EfeService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;

/**
 * The Class LocationListImpl.
 */
@Model(adaptables = Resource.class, adapters = LocationList.class, resourceType = {
		LocationListImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class LocationListImpl implements LocationList {

	/** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE="efe/components/locationlist";
    
	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LocationListImpl.class);

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/**
	 * Injecting efeService
	 * 
	 */
	@OSGiService
	private EfeService efeService;
	
	/**
	 * The current resource.
	 */
	@Self
	private Resource resource;
	
	/** The id. */
	@ValueMapValue
	private String id;

	// State Selection Drop Down
	@ValueMapValue
	private String state;
	
	/** The Tree Map. */
	private TreeMap<String, Map<String, String>> states = new TreeMap<>();

	/**
	 * Returns a Map containing state names as keys and another Map as city and url.
	 * The returned Map represents a collection of states and their .associated
	 * cities and url
	 *
	 * @return a Map containing state names and their associated cities and url.
	 */
	public Map<String, Map<String, String>> getStates() {
		if (Objects.nonNull(states)) {
			return new TreeMap<String, Map<String, String>>(states);
		}

		return Collections.emptyMap();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		if (id == null) {
			id = EFEUtil.getId(resource);
		}
		return id;
	}
	
	/**
	 * This method generates a list of locations grouped by state and city, and
	 * stores it in the "states" HashMap.
	 */
	@PostConstruct
	public void init() {
		
		HashMap<String, Map<String, String>> unsortedStates = new HashMap<String, Map<String, String>>();
		Resource locationResource = resourceResolver.getResource(PlannerLocationConstants.LOCATION_PATH);
		String allStates = "all";
		if (Objects.nonNull(locationResource)) {

			if (state == null) {
				state="all";
			}			

			// Return list of all states and associated cities
			if (state.equals(allStates)) {
				for (Resource stateResource : locationResource.getChildren()) {
					if (stateResource.isResourceType(JcrResourceConstants.NT_SLING_ORDERED_FOLDER)) {
						Map<String, String> unsortedCityMap = new HashMap<String, String>();
						for (Resource cityResource : stateResource.getChildren()) {
							createCityUrl(stateResource, cityResource, unsortedCityMap);
						}
						if(unsortedCityMap.size() > 0) {
							StatesEnum stateEnum = StatesEnum.valueOf(stateResource.getName().toUpperCase());
							unsortedStates.put(stateEnum.getStateName(),sortCity(unsortedCityMap));
						}
					}
				}
			}
			// Return list of selected state and associated cities
			else {
				Resource locationResourceIsolatedState = resourceResolver.getResource(PlannerLocationConstants.LOCATION_PATH + PlannerLocationConstants.FORWARD_SLASH + state );
				if (locationResourceIsolatedState.isResourceType(JcrResourceConstants.NT_SLING_ORDERED_FOLDER)) {
						Map<String, String> unsortedCityMap = new HashMap<String, String>();
						for (Resource cityResource : locationResourceIsolatedState.getChildren()) {
							createCityUrl(locationResourceIsolatedState, cityResource, unsortedCityMap);
						}
						if(unsortedCityMap.size() > 0) {
							StatesEnum stateEnum = StatesEnum.valueOf(locationResourceIsolatedState.getName().toUpperCase());
							unsortedStates.put(stateEnum.getStateName(),sortCity(unsortedCityMap));
						}					
				}

			}

	        // Copy all data from hashMap into TreeMap
	        states.putAll(unsortedStates);
		}
	}

	/**
	 * Method to create City Url.
	 *
	 * @param stateResource state resource
	 * @param cityResource  city resource
	 * @param unsortedCityMap city map in which city and cityUrls will be added
	 */
	private void createCityUrl(Resource stateResource, Resource cityResource, Map<String, String> unsortedCityMap) {
		for (Resource childCityResource : cityResource.getChildren()) {
			if (childCityResource.isResourceType(DamConstants.NT_DAM_ASSET)) {
				String masterResourcePath = childCityResource.getPath() + "/jcr:content/data/master";
				String cityName = ResourceUtil.getProperty(resourceResolver, masterResourcePath, "externalName");
				if(Objects.nonNull(cityName)) {
					String cityUrl = LinkUtil.getFormattedLink(
							efeService.getPlannerPageUrl()
									+ PlannerLocationConstants.DOT + stateResource.getName().toUpperCase()
									+ PlannerLocationConstants.DOT + DamUtil.getSanitizedFolderName(cityName, false),
							resourceResolver);
					LOGGER.info("City Url {}", cityUrl);
					if(!cityName.equalsIgnoreCase("National Advisor Center")) {
						unsortedCityMap.put(cityName, cityUrl);
					}
				}
			}
		}
	}


	/**
	 * Converts a city map in sorted format.
	 *
	 * @return the map in sorted format.
	 */
	public static TreeMap<String, String> sortCity(Map<String,String> map)
	{
		// TreeMap to store values of HashMap
		TreeMap<String, String> cities = new TreeMap<>();

		// Copy all data from hashMap into TreeMap
		cities.putAll(map);
		return  cities;
	}
        
	public static String stateURL(String stateName) {
		String stateReturn = stateName.trim().toLowerCase();
		stateReturn = stateReturn.replace(' ', '-');
		return "/locations/" + stateReturn;
	}

	public String getStateSelector() {	
		return state;
	}

	public static String stateURL(String stateName) {
		String stateReturn = stateName.trim().toLowerCase();
		stateReturn = stateReturn.replace(' ', '-');
		return "/locations/" + stateReturn;
	}	

	public String getStateURL(String stateName) {
		return stateURL(stateName); // reuse your existing logic
	}	
}