package com.efe.core.models;

import java.util.List;

import com.efe.core.models.multifield.Link;
import com.efe.core.models.multifield.SocialLink;
import com.efe.core.models.multifield.VerticalList;

/**
 * The Interface Footer.
 */
public interface Footer {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();

	/**
	 * Gets the logo.
	 *
	 * @return the logo
	 */
	String getFileReference();

	/**
	 * Gets the alt.
	 *
	 * @return the alt
	 */
	String getAlt();

	/**
	 * Gets the logo link.
	 *
	 * @return the logo link
	 */
	String getLogoLink();

	/**
	 * Gets the logo target.
	 *
	 * @return the logo target
	 */
	String getLogoTarget();

	/**
	 * Gets the social links.
	 *
	 * @return the social links
	 */
	List<SocialLink> getSocialLinks();

	/**
	 * Gets the link target.
	 *
	 * @return the link target
	 */
	String getLinkTarget();

	/**
	 * Gets the footer text list.
	 *
	 * @return the footer text list
	 */
	List<Link> getHorizontalList();

	/**
	 * Gets the vertical list.
	 *
	 * @return the vertical list
	 */
	List<VerticalList> getVerticalList();
	
    /**
     * Gets the jsonLd.
     *
     * @return jsonLD
     */
	String getJsonLd();
	
	/**
	 * Checks if is enable one trust.
	 *
	 * @return true, if is enable one trust
	 */
	boolean isEnableOneTrust();

}
