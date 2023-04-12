package com.efe.core.models;

import com.efe.core.models.multifield.Link;
import java.util.List;

/**
 * The Interface Header Model.
 */

public interface Header {


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
     * Gets the CTA Label.
     *
     * @return the CTA Label
     */
    String getCtaTitle();


    /**
     * Gets the CTA link.
     *
     * @return the CTA link
     */
    String getCtaLink();

    /**
     * Gets the CTA target.
     *
     * @return the CTA target
     */
    String getCtaTarget();

    /**
     * Gets the Search Label.
     *
     * @return the Search Label
     */
    String getSearchLabel();

    /**
     * Gets the Search Title.
     *
     * @return the Search Title
     */
    String getSearchBtn();

    /**
     * Gets the Login label.
     *
     * @return the Login label
     */
    String getLoginTitle();

    /**
     * Gets the Login link.
     *
     * @return the Login link
     */
    String getLoginLink();

    /**
     * Gets the Login target.
     *
     * @return the Login target
     */
    String getLoginTarget();


    /**
     * Gets the Contact title.
     *
     * @return the Contact title
     */
    String getContactTitle();

    /**
     * Gets the Contact number.
     *
     * @return the Contact number
     */
    String getContactNumber();

    /**
     * Gets the header list.
     *
     * @return the header list
     */
    List<Link> getHeaderList();
}
