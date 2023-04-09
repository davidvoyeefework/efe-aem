package com.efe.core.services.impl.bean;

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
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
}
