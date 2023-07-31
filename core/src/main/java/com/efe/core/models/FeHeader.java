package com.efe.core.models;

/**
 * The Interface FeHeader.
 */
public interface FeHeader {

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
    String getPrimaryLogoLink();

    /**
     * Gets the secondary logo link.
     *
     * @return the secondary logo link
     */
    String getSecondaryLogoLink();

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
