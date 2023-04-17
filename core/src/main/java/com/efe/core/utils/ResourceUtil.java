package com.efe.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ResourceUtil
 *
 */
public class ResourceUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtil.class);

	/**
	 * @param resourceResolverFactory
	 * @return ResourceResolver
	 */
	public static ResourceResolver getServiceResourceResolver(final ResourceResolverFactory resourceResolverFactory) {
		final Map<String, Object> subServiceUser = new ConcurrentHashMap<>();
		subServiceUser.put(ResourceResolverFactory.SUBSERVICE, "efe-service-user");
		/** Get the instance of Service Resource Resolver */
		try {
			return resourceResolverFactory.getServiceResourceResolver(subServiceUser);
		} catch (LoginException e) {
			LOGGER.error("Could not login as SubService user {}", subServiceUser, e);
			return null;
		}
	}
	
	/**
	 * Retrieves a specific property from a resource identified by its path, using a given ResourceResolver.
	 * @param resourceResolver The ResourceResolver to use to access the resource.
	 * @param resourcePath The path of the resource to retrieve the property from.
	 * @param propertyName The name of the property to retrieve.
	 * @return The value of the requested property as a String, or null if the resource or property cannot be found.
	 */
	public static String getProperty(ResourceResolver resourceResolver, String resourcePath, String propertyName) {	
		Resource resource = resourceResolver.getResource(resourcePath);
		if (Objects.nonNull(resource)) {
			ValueMap property = resource.adaptTo(ValueMap.class);
			return property.get(propertyName, String.class);		
		}
		return null;		
	}
	
	/**
	 * Returns an array of child resource names for the given resource path using the provided {@link ResourceResolver}.
	 * @param resourcePath the path of the parent resource whose children are to be retrieved
	 * @param resourceResolver the {@link ResourceResolver} instance to be used for retrieving the resources
	 * @return an array of child resource names for the given parent resource path; an empty array is returned if no child resources exist
	 */
	public static String[] getResourceChildNames(String resourcePath,
			ResourceResolver resourceResolver) {
		List<String> businessHoursResources = new ArrayList<>();
		Resource locationBusinessHoursResource = resourceResolver.getResource(resourcePath);
		if (Objects.nonNull(locationBusinessHoursResource)) {
			for (Resource r : locationBusinessHoursResource.getChildren()) {
				businessHoursResources.add(r.getPath());
			}
		}
		return businessHoursResources.toArray(new String[0]);
	}
}