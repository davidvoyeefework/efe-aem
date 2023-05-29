package com.efe.core.models;

import java.util.List;

import com.efe.core.models.bean.Podcast;

/**
 * The Interface PodcastArchiveList.
 */
public interface PodcastArchiveList {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();

	/**
	 * Gets the podcasts.
	 *
	 * @return the podcasts
	 */
	List<Podcast> getPodcasts(); 
	/**
	 * Checks if is empty.
	 *
	 * @return the empty
	 */
	boolean isEmpty();

}
