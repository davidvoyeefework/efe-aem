package com.efe.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.util.ComponentUtils;

/**
 * The Class EFEUtil.
 */
public class EFEUtil {

	/** The Constant ID_HASH_LENGTH. */
	private static final int ID_HASH_LENGTH = 10;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EFEUtil.class);

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

	/**
	 * Format date.
	 *
	 * @param inputFormat  the input format
	 * @param outputFormat the output format
	 * @param inputDate    the input date
	 * @return the string
	 */
	public static String formatDate(String inputFormat, String outputFormat, String inputDate) {

		String formattedDate = null;

		if (null != inputFormat && null != outputFormat && null != inputDate) {
			try {
				DateFormat inputDateFormat = new SimpleDateFormat(inputFormat);
				DateFormat outputDateFormat = new SimpleDateFormat(outputFormat);

				Date date = inputDateFormat.parse(inputDate);
				formattedDate = outputDateFormat.format(date);
			} catch (IllegalArgumentException | ParseException e) {
				LOGGER.error("Error while parsing date input", e);
			}
		}

		return formattedDate;

	}

}
