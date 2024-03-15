package com.efe.core.models;

import java.util.List;

/**
 * The Interface PageModel.
 */
public interface PageModel {

	/**
	 * Gets the canonical link.
	 *
	 * @return the canonical link
	 */
	String getCanonicalLink();

	/**
	 * Gets the thumbnail.
	 *
	 * @return the thumbnail
	 */
	String getThumbnail();
	
	
	/**
	 * Gets the jquery url.
	 *
	 * @return the jquery url
	 */
	String getJqueryUrl();
        
        /**
	 * Gets the description or a version of the description for locations/planners.
	 *
	 * @return the modified description
	 */
        String getModifiedDescription();
        
        /**
	 * Gets the title or a version of the title for locations/planners.
	 *
	 * @return the modified title
	 */
        String getModifiedTitle();
        String getFPIDLibURL();

	/**
	 * Gets the external libraries.
	 *
	 * @return the external libraries
	 */
	List<String> getExternalLibraries();

}
