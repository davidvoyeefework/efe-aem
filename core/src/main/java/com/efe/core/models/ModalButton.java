package com.efe.core.models;
import com.adobe.cq.xf.ExperienceFragmentVariation;
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
}
