package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The PrimaryOffice class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrimaryOffice {
	
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

	/* getter Name */
	public String getName() {
		return name;
	}

	/* getter City */
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
