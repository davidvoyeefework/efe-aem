package com.efe.core.utils;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.adobe.cq.dam.cfm.FragmentTemplate;

public class FragmentUtil {

	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FragmentUtil.class);

	/**
	 * This method is used to create Fragment for Planner
	 */
	public static void createFragment(String modelResource, String rootPath, String fragmentName,
									  String fragmentResource, ResourceResolver resourceResolver) {
		Resource templateOrModelRsc = resourceResolver.getResource(modelResource);
		FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
		if (Objects.nonNull(tpl)) {
			try {
				Resource parentRsc = resourceResolver.getResource(rootPath);
				tpl.createFragment(parentRsc, fragmentName, fragmentResource);
				if (resourceResolver.hasChanges()) {
					resourceResolver.commit();
				}
			} catch (ContentFragmentException e) {
				LOGGER.error("ContentFragmentException:", e);
			} catch (PersistenceException e) {
				LOGGER.error("PersistenceException:", e);
			}
		}
	}

	public static String populateCFContent(String resourcePath, ResourceResolver resourceResolver, String fragmentProperty, String fragmentVariationsProperty) {
		String fragmentPropertyValue = ResourceUtil.getProperty(resourceResolver, resourcePath, fragmentProperty);
		String fragmentVariationsPropertyValue = ResourceUtil.getProperty(resourceResolver, resourcePath, fragmentVariationsProperty);
		if (fragmentPropertyValue != null) {
			String updatedPropertyValue = fragmentPropertyValue + "/jcr:content/data/master";
			if (StringUtils.isNotEmpty(fragmentVariationsPropertyValue)) {
				updatedPropertyValue = fragmentPropertyValue + "/jcr:content/data/" + fragmentVariationsPropertyValue;
			}
			return ResourceUtil.getProperty(resourceResolver, updatedPropertyValue, "content");
		} else {
			return StringUtils.EMPTY;
		}
	}
}