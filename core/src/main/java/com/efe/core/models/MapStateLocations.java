package com.efe.core.models;

/**
 * The Interface MapSearchResults Model.
 */
public interface MapStateLocations {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();

	/**
	 * Gets offices information in JSON format.
	 *
	 * @return the Office Location Details
	 */
	String getOfficesJson();

	/**
	 * Method to return Planner search input label.
	 *
	 * @return the label
	 */
	String getPlannerSearchLabel();

	/**
	 * Method to return Planner search input placeholder text.
	 *
	 * @return input place holder text
	 */
	String getPlannerSearchPlaceHolder();

	/**
	 * Method to return the search planner button label.
	 *
	 * @return search planner button label
	 */
	String getPlannerButtonLabel();

	/**
	 * Method to return the offices search button label.
	 *
	 * @return search button label
	 */
	String getSearchButtonLabel();

	/**
	 * Method to return the explore link label.
	 *
	 * @return explore link label
	 */
	String getExploreLinkLabel();

	/**
	 * Method to return the Map Key.
	 *
	 * @return the mapKey
	 */
	String getMapKey();

	/**
	 * Method to return National Advisor Title.
	 *
	 * @return the title
	 */
	String getNationalTitle();

	/**
	 * Method to return National Advisor Callback Number.
	 *
	 * @return the callback number
	 */
	String getNationalCallbackNumber();
	
	/**
	 * Method to return National Advisor Callback label.
	 *
	 * @return the callback label
	 */
	String getNationalCallbackLabel();

	/**
	 * Method to return National Advisor CTA Link.
	 *
	 * @return the ctalink
	 */
	String getNationalLink();
        
        /**
	 * Method to return the state to display.
	 *
	 * @return the mapState
	 */
        String getMapState();
        
        String getStateName();

	/**
	 * Method to return National Advisor Details.
	 *
	 * @return the details
	 */
	String getNationalDetails();

}
