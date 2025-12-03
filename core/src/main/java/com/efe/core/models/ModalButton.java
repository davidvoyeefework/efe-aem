/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
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
