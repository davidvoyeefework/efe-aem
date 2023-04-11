package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The OfficesLocations class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfficesLocations {
    private String id;
    private String name;
    private String city;
    private String state;
    private String zip;
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}


}
