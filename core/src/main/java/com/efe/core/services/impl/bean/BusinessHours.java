package com.efe.core.services.impl.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The BusinessHours class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessHours {
    
	/* day */
	private String day; 
	
	/* openingHours */
    private String openingHours;

    /* closingHours */
    private String closingHours;

    /* isClosed */
    private boolean isClosed;
    
    public void setDay(String day) {
         this.day = day;
     }
     public String getDay() {
         return day;
     }
	public String getOpeningHours() {
		return openingHours;
	}
	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}
	public String getClosingHours() {
		return closingHours;
	}
	public void setClosingHours(String closingHours) {
		this.closingHours = closingHours;
	}
	public boolean isClosed() {
		return isClosed;
	}
	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
}