package com.efe.core.models;

/**
 * The Interface ContentCollage.
 */
public interface ContentCollage {

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
     * Gets the secondary image.
     *
     * @return the secondary image
     */
    String getSecondaryImage();

    /**
     * Gets the secondary image alt text.
     *
     * @return the secondary image alt text
     */
    String getSecondaryImageAltText();

    /**
     * Gets the content card.
     *
     * @return the content card
     */
    String getContentCard();
}
