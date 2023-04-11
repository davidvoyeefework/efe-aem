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
	
	public String getDay() {
        return day;
    }
	
	/* openingHours */
    private String openingHours;

    /* closingHours */
    private String closingHours;

    /* isClosed */
    private boolean isClosed;
    
	public String getOpeningHours() {
		return openingHours;
	}
	public String getClosingHours() {
		return closingHours;
	}
	public boolean isClosed() {
		return isClosed;
	}

}