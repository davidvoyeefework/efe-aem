package com.efe.core.models;

/**
 * The Interface Video.
 */
public interface Video {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();

	/**
	 * Gets the video id.
	 *
	 * @return the video id
	 */
	String getVideoId();

	/**
	 * Gets the video thumbnail.
	 *
	 * @return the video thumbnail
	 */
	String getVideoThumbnail();

	/**
	 * Gets the button icon.
	 *
	 * @return the button icon
	 */
	String getButtonIcon();

	/**
	 * Gets the video title.
	 *
	 * @return the video title
	 */
	String getVideoTitle();

	/**
	 * Method to return the JSON LD Schema.
	 *
	 * @return json ld
	 */
	String getJsonLd();

	/**
	 * Gets the data layer.
	 *
	 * @return the dataLayer
	 */
	String getDataLayer();
	
	/**
	 * Checks if is empty.
	 *
	 * @return the empty
	 */
	boolean isEmpty();

	// Alt attribute for thumbnail
	String getThumbnailAlt();

}
