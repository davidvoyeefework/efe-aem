package com.efe.core.utils;

import java.util.Objects;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.constants.PlannerLocationConstants;

/**
 * The class FolderUtil.
 *
 */
public class FolderUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FolderUtil.class);

	/**
	 * This method is used to create Folder
	 * 
	 * @param parentPath
	 * @param folderName
	 * @param resourceResolver
	 * @return
	 */
	public static String createFolder(String parentPath, String folderName, ResourceResolver resourceResolver) {
		Resource parentResource = resourceResolver.getResource(parentPath);
		// Check if folder already exists
		if (Objects.isNull(parentResource.getChild(folderName))) {
			// Create new folder node
			Node parentNode = parentResource.adaptTo(Node.class);
			try {
				parentNode.addNode(folderName, PlannerLocationConstants.SLING_ORDERED_FOLDER);
			} catch (Exception e) {
				LOGGER.error("ContentFragmentException:", e);
			}
			try {
				resourceResolver.commit();
			} catch (Exception e) {
				LOGGER.error("ContentFragmentException:", e);
			}
		}

		return (parentPath + PlannerLocationConstants.FORWARD_SLASH + folderName);
	}
}
