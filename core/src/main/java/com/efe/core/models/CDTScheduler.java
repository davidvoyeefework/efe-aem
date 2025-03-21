package com.efe.core.models;

/**
 * The Interface Forms.
 */
public interface CDTScheduler {

	/**
	 * Gets the SalesForce lead ID.
	 *
	 * @return the SalesForce lead ID.
	 */
	String getSFLeadId();
        
        /**
	 * Gets the CDT scheduler embed URL.
	 *
	 * @return the CDT scheduler embed URL.
	 */
        String getCDTEmbedURL();
        
        /**
         * Gets flag to determine whether to display the header
         * 
         * @return the disableHeader flag
         */
        //boolean getDisableHeader();
        
        /**
         * Gets flag to determine whether to display the disclosure
         * 
         * @return the showDisclosure flag
         */
        boolean getShowDisclosure();

}
