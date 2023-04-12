package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The BusinessHours class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessHours {
    
	/* day */
	private String day; 
	
	/* Getter for day */
	public String getDay() {
        return day;
    }
	
	/* openingHours */
    private String openingHours;

    /* closingHours */
    private String closingHours;

    /* isClosed */
    private boolean isClosed;
    
    /* Getter for getOpeningHours */
	public String getOpeningHours() {
		return openingHours;
	}
	
	/* Getter for getClosingHours */
	public String getClosingHours() {
		return closingHours;
	}
	
	/* Getter for isClosed */
	public boolean isClosed() {
		return isClosed;
	}
}