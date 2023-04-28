package com.efe.core.models;

import java.util.List;

import com.efe.core.models.multifield.SocialMediaSharing;

/**
 * The Interface SocialShare.
 */
public interface SocialShare {

	/**
	 * Gets the social media sharing links.
	 *
	 * @return social media sharing links
	 */
	public List<SocialMediaSharing> getSocialMediaSharing();

	/**
	 * Gets the link target.
	 *
	 * @return the link target
	 */
	public String getLinkTarget();

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId();
}
