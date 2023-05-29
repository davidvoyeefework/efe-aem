package com.efe.core.services;

import org.apache.sling.api.resource.ResourceResolver;

/**
 * The Interface DynamicMediaService.
 */
public interface DynamicMediaService {

	/**
	 * Get dynamic media path of Image
	 *
	 * @param resourceResolver
	 * @param imagePath
	 * @return dynamic media path of Image
	 */
	String getDmImagePath(ResourceResolver resourceResolver, String imagePath);
}
