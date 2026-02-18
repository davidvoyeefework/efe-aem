package com.efe.core.models;
/**
 *
 * @author jrodriquez
 */
public interface ModalButton {
    /**
     * Gets the id.
     *
     * @return the id
    */
    String getId();

    /**
     * Gets the Button id.
     *
     * @return the id
    */

    String getButtonId();
    
    /**
     * Gets the experience fragment reference.
     *
     * @return the experience fragment location
    */
    String getEmbedFragment();

    /**
     * Gets the button text.
     *
     * @return button text/
    */
    String getButtonText();

    /* Get open Button event*/

    String getOpenModalButton();

    /* Get close Button event*/

    String getCloseModalButton();

    String getOpenModalEvent();

    String getCloseModalEvent();
}
