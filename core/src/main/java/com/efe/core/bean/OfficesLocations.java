package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The OfficesLocations class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfficesLocations {
	
	/* id */
    private String id;
    
    /* name */
    private String name;
    
    /* city */
    private String city;
    
    /* state */
    private String state;
    
    /* zip */
    private String zip;
    
    /**
     * Returns the Id.
     *
     * @return the Id
     */
	public String getId() {
		return id;
	}

	/**
     * Returns the Name.
     *
     * @return the Name
     */
	public String getName() {
		return name;
	}

	/**
     * Returns the City.
     *
     * @return the City
     */
	public String getCity() {
		return city;
	}

	/**
     * Returns the State.
     *
     * @return the State
     */
	public String getState() {
		return state;
	}

	/**
     * Returns the Zip.
     *
     * @return the Zip
     */
	public String getZip() {
		return zip;
	}


}
