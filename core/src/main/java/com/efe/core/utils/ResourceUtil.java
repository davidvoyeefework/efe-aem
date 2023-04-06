package com.efe.core.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ResourceUtil
 *
 */
public class ResourceUtil{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtil.class);
	
	/**
	 * @param resourceResolverFactory
	 * @return ResourceResolver
	 */
	public static ResourceResolver getServiceResourceResolver(final ResourceResolverFactory resourceResolverFactory) {
		final Map<String, Object> subServiceUser = new ConcurrentHashMap<>();
		subServiceUser.put(ResourceResolverFactory.SUBSERVICE,"efe-service-user");
		/** Get the instance of Service Resource Resolver */
        try {
			return resourceResolverFactory.getServiceResourceResolver(subServiceUser);
		} catch (LoginException e) {
			LOGGER.error("Could not login as SubService user {}", subServiceUser, e);
			return null;
		}
	}
}