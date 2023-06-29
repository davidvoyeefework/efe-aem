package com.efe.core.utils;

import java.util.Objects;
import java.util.Optional;

import com.day.cq.dam.api.DamConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.efe.core.bean.LocationResponse;
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
	
	/**
	 * Gets the planner resource.
	 *
	 * @param resourceResolver the resource resolver
	 * @param firstName the first name
	 * @param id the id
	 * @return the planner resource
	 */
	public static Resource getPlannerResource(ResourceResolver resourceResolver, String firstName, String id) {
		//content/dam/efe/cf/plannerlocation/planners/179/fragment_johnathan_179		
		String plannerPath = PlannerLocationConstants.ROOT_FOLDER_PATH + PlannerLocationConstants.FORWARD_SLASH
				+ PlannerLocationConstants.PLANNERS + PlannerLocationConstants.FORWARD_SLASH + id + PlannerLocationConstants.FORWARD_SLASH + "fragment_"+ firstName.toLowerCase() + "_" + id;
		
		return resourceResolver.getResource(plannerPath);
	}

	/**
	 * Method to return the location info from the location content fragment
	 * 
	 * @param locationCFResource location content fragment resource
	 * @return location bean object
	 */
	public static LocationResponse getLocationInfo(Resource locationCFResource) {

		LocationResponse locationResponse = new LocationResponse();

		Optional<ContentFragment> locationCF = Optional.ofNullable(locationCFResource.adaptTo(ContentFragment.class));

		locationResponse.setCity(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.CITY))
				.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
		locationResponse.setOfficeName(locationCF.map(cf -> cf.getElement("officeName"))
				.map(ContentElement::getContent).orElse(StringUtils.EMPTY));

		locationResponse.setState(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.STATE))
				.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
		locationResponse.setAddress1(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.ADDRESS_1))
				.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
		locationResponse.setAddress2(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.ADDRESS_2))
				.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
		locationResponse.setZip(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.ZIP))
				.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
		locationResponse.setLatitude(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.LATITUDE))
				.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
		locationResponse.setLongitude(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.LONGITUTE))
				.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
		locationResponse.setExternalName(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.EXTERNAL_NAME))
				.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
		locationResponse
				.setGoogleReviewLink(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.GOOGLE_REVIEW_LINK))
						.map(ContentElement::getContent).orElse(StringUtils.EMPTY));

		return locationResponse;
	}
	
	/**
	 * Converts a given string to camel case format.
	 * 
	 * @param inputString the string to be converted to camel case.
	 * @return the string in camel case format.
	 */
	public static String toCamelCase(String inputString) {
		String[] stringArr = inputString.split(PlannerLocationConstants.SPACE);
		StringBuilder formattedStringSb = new StringBuilder();
		formattedStringSb.append(StringUtils.capitalize(stringArr[0]));

		for (int i=1; i<stringArr.length; i++){
			formattedStringSb.append(PlannerLocationConstants.SPACE + StringUtils.capitalize(stringArr[i]));
		}
		return formattedStringSb.toString();
	}

	/**
	 * Method to return the name of the city.
	 *
	 * @param resourceResolver the resourceResolver.
	 * @param cityResource the cityResource.
	 * @return the city name.
	 */
	public static String getLocationProperty(ResourceResolver resourceResolver, Resource cityResource, String property) {
		String propertyValue = StringUtils.EMPTY;
		if(Objects.nonNull(cityResource)) {
			for (Resource childCityResource : cityResource.getChildren()) {
				if (childCityResource.isResourceType(DamConstants.NT_DAM_ASSET)) {
					String masterResourcePath = childCityResource.getPath() + "/jcr:content/data/master";
					propertyValue = ResourceUtil.getProperty(resourceResolver, masterResourcePath, property);
				}
			}
		}
		return propertyValue;
	}
}
