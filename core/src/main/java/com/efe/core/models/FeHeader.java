package com.efe.core.models;

/**
 * The Interface FeHeader.
 */
public interface FeHeader {

    /**
     * Gets the type.
     *
     * @return the type
     */
    String getType();

    /**
     * Gets the primary logo.
     *
     * @return the primary logo
     */
    String getPrimaryLogo();

    /**
     * Gets the secondary logo.
     *
     * @return the secondary logo
     */
    String getSecondaryLogo();

    /**
     * Gets the primary logo link.
     *
     * @return the primary logo link
     */
    String getPrimaryLogoAltText();

    /**
     * Gets the secondary logo link.
     *
     * @return the secondary logo link
     */
    String getSecondaryLogoAltText();

    /**
     * Gets the id.
     *
     * @return the id
     */
    String getId();

    /**
     * Gets the Sponsor Details.
     *
     * @return the Sponsor Details
     */
    String getSponsorDetails();

    /**
     * Gets the dynamic variables.
     *
     * @return the dynamicVariables
     */
    String getDynamicVariables();
}
