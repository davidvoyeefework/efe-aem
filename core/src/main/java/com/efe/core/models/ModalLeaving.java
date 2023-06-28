package com.efe.core.models;

import com.google.gson.JsonObject;

/**
 * The Interface ModalLeaving Model.
 */

public interface ModalLeaving {

    /**
     * Gets the Logo.
     *
     * @return the Logo
     */
    String getLogo();

    /**
     * Gets the Logo Alt text.
     *
     * @return the Logo Alt text
     */
    String getLogoAltText();

    /**
     * Gets the Modal title.
     *
     * @return the Modal title
     */
    String getModalTitle();

    /**
     * Gets the Modal Description text.
     *
     * @return the Modal Description text
     */
    String getModalDescriptionText();

    /**
     * Gets the Modal Leave text.
     *
     * @return the Modal Leave text
     */
    String getModalLeaveText();

    /**
     * Gets the Modal Cancel text.
     *
     * @return the Modal Cancel text
     */
    String getModalCancelText();

    /**
     * Gets the id.
     *
     * @return the id
     */
    String getId();

    /**
     * Gets the modal exclusion list.
     *
     * @return the modal exclusion list
     */
    JsonObject getModalList();
}
