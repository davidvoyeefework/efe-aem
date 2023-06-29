package com.efe.core.models;

import com.efe.core.models.bean.Podcast;

/**
 * The Interface PodcastArchiveList.
 */
public interface PodcastModel {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();

	/**
	 * Gets the podcast.
	 *
	 * @return the podcast
	 */
	Podcast getPodcast(); 
	/**
	 * Checks if is empty.
	 *
	 * @return the empty
	 */
	boolean isEmpty();

	/**
	 * Checks if is api error.
	 *
	 * @return the isApiError
	 */
	boolean isApiError();

	/**
	 * Gets the file reference.
	 *
	 * @return the fileReference
	 */
	String getFileReference();

	/**
	 * Gets the json ld.
	 *
	 * @return the jsonLd
	 */
	String getJsonLd();

}
