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

	/**
	 * Returns the Opening Hours.
	 *
	 * @return the name of this person
	 */
	public String getOpeningHours() {
		return openingHours;
	}

	/**
	 * Returns the Closing Hours.
	 *
	 * @return the Closing Hours
	 */
	public String getClosingHours() {
		return closingHours;
	}

	/**
	 * Returns whether this object is closed.
	 *
	 * @return true if this object is closed, false otherwise
	 */
	public boolean isClosed() {
		return isClosed;
	}
}