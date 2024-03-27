package com.efe.core.services.impl;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.scene7.api.constants.Scene7Constants;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.services.DynamicMediaService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * The Class DynamicMediaServiceImpl.
 */
@Component(service = DynamicMediaService.class, immediate = true)
public class DynamicMediaServiceImpl implements DynamicMediaService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicMediaServiceImpl.class);

	/** The Constant DM_IS_IMAGE. */
	public static final String DM_IS_IMAGE = "is/image/";

	public static final String DM_IS_CONTENT = "is/content/";

	/** The Constant METADATA. */
	public static final String METADATA = "metadata";

	/**
	 * Get dynamic media path of Image
	 *
	 * @param resourceResolver
	 * @param imagePath
	 * @return dynamic media path of Image
	 */
	@Override
	public String getDmImagePath(ResourceResolver resourceResolver, String imagePath) {
		if (Objects.nonNull(resourceResolver) && StringUtils.isNotBlank(imagePath) && !(imagePath.endsWith(".svg"))) {
			Resource imageRes = resourceResolver.getResource(imagePath + PlannerLocationConstants.FORWARD_SLASH + JcrConstants.JCR_CONTENT
					+ PlannerLocationConstants.FORWARD_SLASH + METADATA);
			if (Objects.nonNull(imageRes)) {
				ValueMap vMap = imageRes.getValueMap();
				String dmImagePath = vMap.get(Scene7Constants.PN_S7_FILE, StringUtils.EMPTY);
				String dmPublishUrl = vMap.get(Scene7Constants.PN_S7_DOMAIN, StringUtils.EMPTY);
				String dmFileStatus = vMap.get(Scene7Constants.PN_S7_FILE_STATUS, StringUtils.EMPTY);
				if (!StringUtils.isBlank(dmImagePath) && !StringUtils.isBlank(dmPublishUrl)
						&& dmFileStatus.equals(Scene7Constants.PV_S7_PUBLISH_COMPLETE)) {
					if (imagePath.endsWith(".gif"))
						imagePath = dmPublishUrl + DM_IS_CONTENT + dmImagePath;
					else
						imagePath = dmPublishUrl + DM_IS_IMAGE + dmImagePath;
				}
			} else {
				LOGGER.debug("Image Resource not found for Dynamic Media : {}", imagePath);
			}
		}
		return imagePath;
	}
}