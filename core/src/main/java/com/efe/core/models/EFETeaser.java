package com.efe.core.models;

import java.util.List;

import com.adobe.cq.wcm.core.components.models.Teaser;
import com.efe.core.bean.LinkBean;

/**
 * The Interface EFETeaser Model.
 */

public interface EFETeaser extends Teaser {


	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	List<LinkBean> getTags();

		/**
	 * Gets the subtitle.
	 *
	 * @return the subtitle
	 */
	String getSubtitle();

	String getPagePreviewText();

	String getAuthorTitle();

}
