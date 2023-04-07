package com.efe.core.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

import com.adobe.cq.wcm.core.components.util.ComponentUtils;

/**
 * The Class EFEUtil.
 */
public class EFEUtil {

	/** The Constant ID_HASH_LENGTH. */
	private static final int ID_HASH_LENGTH = 10;

	/**
	 * Instantiates a new EFE util.
	 */
	private EFEUtil() {
		/*
		 * adding a private constructor to hide the implicit one
		 */
	}

	/**
	 * Gets the id.
	 *
	 * @param resource the resource
	 * @return the id
	 */
	public static String getId(Resource resource) {
		return StringUtils.join(resource.getName(), ComponentUtils.ID_SEPARATOR,
				StringUtils.substring(DigestUtils.sha256Hex(resource.getPath()), 0, ID_HASH_LENGTH));
	}

}
