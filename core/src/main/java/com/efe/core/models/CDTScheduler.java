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
         * Gets flag to determine whether to display the header
         * 
         * @return the disableHeader flag
         */
        boolean getDisableHeader();

}
