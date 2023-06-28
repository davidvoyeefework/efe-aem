package com.efe.core.services;


/**
 * The Interface FormHandler.
 */
public interface FormHandler {
	
	 /**
     * Forward form data to a remote service.
     *
     * @param formData    the form data Json String
     * @param endPointUrl the URL of the remote service
     * @return true if the remote request was successful, otherwise false
     * @since com.adobe.cq.wcm.core.components.services.form 1.0.0
     */
    boolean forwardFormData(String formData, String endPointUrl);
}
