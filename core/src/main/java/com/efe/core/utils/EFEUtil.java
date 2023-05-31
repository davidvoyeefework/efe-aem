package com.efe.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.util.ComponentUtils;
import com.efe.core.models.bean.Podcast;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

	/**
	 * Gets the pod cast obj.
	 *
	 * @param episode the episode
	 * @return the pod cast obj
	 */
	public static Podcast getPodCastObj(JsonElement episode) {
		Podcast podcast = new Podcast();
		if (null != episode && episode.isJsonObject()) {
			JsonObject clipObj = episode.getAsJsonObject();
			podcast.setId(clipObj.get("Id").getAsString());
			podcast.setTitle(clipObj.get("Title").getAsString());
			
			String descHtml = clipObj.get("DescriptionHtml").getAsString();
			if (StringUtils.isNotEmpty(descHtml)) {			
				Pattern pattern = Pattern.compile("<p>(.*?)</p>"); // Pattern to match <p> tags
				Matcher matcher = pattern.matcher(descHtml);
				if (matcher.find()) {
					podcast.setShortDescriptionHtml(matcher.group(1)); // Extract the content within the first <p> tag
				}
				podcast.setDescriptionHtml(descHtml);		
			}
		
			podcast.setEmbedUrl(clipObj.get("EmbedUrl").getAsString());

			if (clipObj.has("Season")) {
				podcast.setSeason(clipObj.get("Season").getAsInt());
			}

			if (clipObj.has("Episode")) {
				podcast.setEpisode(clipObj.get("Episode").getAsInt());
			}

		}
		return podcast;
	}

	/**
	 * Traverse resource hierarchy.
	 *
	 * @param resource     the resource
	 * @param resourceType the resource type
	 * @return the resource
	 */
	public static Resource traverseResourceHierarchy(Resource resource, String resourceType) {
		if (resource != null) {
			// Check if the current resource matches the specified resource type
			if (resource.isResourceType(resourceType)) {
				return resource;
			}

			// Recursively check child resources
			Iterable<Resource> childResources = resource.getChildren();
			for (Resource childResource : childResources) {
				Resource targetResource = traverseResourceHierarchy(childResource, resourceType);
				if (targetResource != null) {
					return targetResource;
				}
			}
		}

		return null;
	}
}
