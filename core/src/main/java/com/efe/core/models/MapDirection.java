package com.efe.core.models;

import com.efe.core.bean.LocationResponse;

/**
 * The Interface MapDirection Model.
 */

public interface MapDirection {


    /**
     * Gets the id.
     *
     * @return the id
     */
    String getId();
    
    /**
     * Gets the location details from request selector information
     * 
     * @return the Location Details
     */
    LocationResponse getLocationResponse();

	/**
	 * Method to return the Address Heading
	 * 
	 * @return the heading
	 */
	String getHeading();

	/**
	 * Method to return map zoom level
	 * 
	 * @return the zoomLevel
	 */
	int getZoomLevel();

	/**
	 * Method to return is component has value
	 * 
	 * @return the isEmpty
	 */
	boolean isEmpty();

	/**
	 * Method to return the Google Direction google link
	 * 
	 * @return the googleDirectionPath
	 */
	String getGoogleDirectionPath();

	/**
	 * Method to return the Review Link Label
	 * 
	 * @return the reviewLinkLabel
	 */
	String getReviewLinkLabel();

	/**
	 * Method to return the Review Question
	 * 
	 * @return the reviewQuestion
	 */
	String getReviewQuestion();

	/**
	 * Method to return the 
	 * 
	 * @return the directionButtonLabel
	 */
	String getDirectionButtonLabel();

	/**
	 * Method to return the Map Key
	 * 
	 * @return the mapKey
	 */
	String getMapKey();
	
	/**
	 * Method to return the JSON LD Schema
	 * 
	 * @return json ld
	 */
	String getJsonLd();

	Boolean getShowMap();

   }
