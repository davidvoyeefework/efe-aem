package com.efe.core.models.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
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

	/** The States Map. */
	private Map<String, Map<String, String>> states;

	/**
	 * Returns a Map containing state names as keys and another Map as city and url.
	 * The returned Map represents a collection of states and their .associated
	 * cities and url
	 *
	 * @return a Map containing state names and their associated cities and url.
	 */
	public Map<String, Map<String, String>> getStates() {
		if (Objects.nonNull(states)) {
			return new HashMap<String, Map<String, String>>(states);
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
		states = new HashMap<String, Map<String, String>>();
		Resource locationResource = resourceResolver.getResource(PlannerLocationConstants.LOCATION_PATH);
		if (Objects.nonNull(locationResource)) {
			for (Resource stateResource : locationResource.getChildren()) {
				if (stateResource.isResourceType(JcrResourceConstants.NT_SLING_ORDERED_FOLDER)) {
					Map<String, String> cityMap = new HashMap<String, String>();
					for (Resource cityResource : stateResource.getChildren()) {
						String cityUrl = LinkUtil.getFormattedLink(
								efeService.getPlannerPageUrl()
										+ PlannerLocationConstants.DOT + stateResource.getName().toUpperCase()
										+ PlannerLocationConstants.DOT + toCamelCase(cityResource.getName()).replaceAll(
												PlannerLocationConstants.SPACE, PlannerLocationConstants.HYPHEN),
								resourceResolver);
						LOGGER.info("City Url {}", cityUrl);
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
	 * 
	 * @param inputString the string to be converted to camel case.
	 * @return the string in camel case format.
	 */
	private String toCamelCase(String inputString) {
		String[] stringArr = inputString.split(PlannerLocationConstants.SPACE);
		StringBuilder formattedStringSb = new StringBuilder();
		formattedStringSb.append(StringUtils.capitalize(stringArr[0]));

		for (int i=1; i<stringArr.length; i++){
			formattedStringSb.append(PlannerLocationConstants.SPACE + StringUtils.capitalize(stringArr[i]));
		}
		return formattedStringSb.toString();
	}
}