package com.efe.core.utils;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * The class LinkUtil.
 */
public class LinkUtil {

	/** The Constant DAM_PATH. */
	public static final String DAM_PATH = "/content/dam";

	/** The Constant CONTENT_PATH. */
	public static final String CONTENT_PATH = "/content";

	/** The Constant HTML_EXTENSION. */
	public static final String HTML_EXTENSION = ".html";

	/** The Constant QUESTION_MARK. */
	public static final String QUESTION_MARK = "?";

	/** The Constant HASH. */
	public static final String HASH = "#";

	/**
	 * Instantiates a new link util.
	 */
	private LinkUtil() {
		/*
		 * adding a private constructor to hide the implicit one
		 */
	}

	/**
	 * Gets the formatted link.
	 *
	 * @param link     the link
	 * @param resolver the resolver
	 * @return the formatted link
	 */
	public static String getFormattedLink(String link, ResourceResolver resolver) {
		if (StringUtils.isNotBlank(link) && Objects.nonNull(resolver)) {
			if (StringUtils.equals(link, HASH)) {
				return HASH;
			} else if (isExternalLink(link)) {
				return link;
			} else if (isDam(link)) {
				return resolver.map(link);
			} else if (StringUtils.contains(link, CONTENT_PATH)) {
				if (!link.contains(HTML_EXTENSION) && link.contains(QUESTION_MARK)) {
					String[] linkWithQuery = StringUtils.split(link, QUESTION_MARK);
					return resolver.map(linkWithQuery[0] + HTML_EXTENSION) + QUESTION_MARK + linkWithQuery[1];
				}
				if (!link.contains(HTML_EXTENSION) && !link.contains(HASH)) {
					return resolver.map(link + HTML_EXTENSION);
				} else if (!link.contains(HTML_EXTENSION) && link.contains(HASH)) {
					String[] hashString = StringUtils.split(link, HASH);
					return resolver.map(hashString[0] + HTML_EXTENSION) + HASH + hashString[1];

				} else {
					return resolver.map(link);
				}
			}
		}
		return link;

	}

	/**
	 * Checks if is external link.
	 *
	 * @param link the link
	 * @return true, if is external link
	 */
	public static boolean isExternalLink(String link) {
		return link.startsWith("http") || link.startsWith("https");
	}

	/**
	 * Checks if is dam or external.
	 *
	 * @param link the link
	 * @return true, if is dam
	 */
	private static boolean isDam(String link) {
		return StringUtils.contains(link, DAM_PATH);
	}

}
