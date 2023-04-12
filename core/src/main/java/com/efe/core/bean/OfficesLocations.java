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
    
    /* getter id */
	public String getId() {
		return id;
	}

	/* getter name */
	public String getName() {
		return name;
	}

	/* getter city */
	public String getCity() {
		return city;
	}

	/* getter State */
	public String getState() {
		return state;
	}

	/* getter Zip */
	public String getZip() {
		return zip;
	}


}
