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
     * Gets the primary logo alt text.
     *
     * @return the primary logo alt text
     */
    String getPrimaryLogoAltText();

    /**
     * Gets the secondary logo alt text.
     *
     * @return the secondary logo alt text
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
