package com.efe.core.models;

/**
 * The Interface ImageCollage.
 */
public interface ImageCollage {

    /**
     * Gets the primary image.
     *
     * @return the primary image
     */
    String getPrimaryImage();

    /**
     * Gets the id.
     *
     * @return the id
     */
    String getId();

    /**
     * Gets the primary image alt text.
     *
     * @return the primary image alt text
     */
    String getPrimaryImageAltText();

    /**
     * Gets the first secondary image.
     *
     * @return the first secondary image
     */
    String getFirstSecondaryImage();

    /**
     * Gets the first secondary image alt text.
     *
     * @return the first secondary image alt text
     */
    String getFirstSecondaryImageAltText();

    /**
     * Gets the second secondary image.
     *
     * @return the second secondary image
     */
    String getSecondSecondaryImage();

    /**
     * Gets the second secondary image alt text.
     *
     * @return the second secondary image alt text
     */
    String getSecondSecondaryImageAltText();

    /**
     * Gets the primary image position
     *
     * @return the primary image position
     */
    String getPrimaryImagePosition();

    /**
     * Gets the first secondary image position
     *
     * @return the first secondary image position
     */
    String getFirstSecondaryImagePosition();

    /**
     * Gets the second secondary image position
     *
     * @return the second secondary image position
     */
    String getSecondSecondaryImagePosition();

}
