package com.efe.core.models;

/**
 * The Interface Forms.
 */
public interface ECScheduler {

	/**
	 * Gets the SalesForce lead ID.
	 *
	 * @return the SalesForce lead ID.
	 */
	String getContainerID();
        
        /**
	 * Gets the CDT scheduler embed URL.
	 *
	 * @return the CDT scheduler embed URL.
	 */
        String getECEmbedURL();
}
