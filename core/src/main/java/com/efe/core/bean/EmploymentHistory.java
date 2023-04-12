package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The EmploymentHistory class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmploymentHistory {
	
	/* startDate */
	private String startDate;
	
	/* endDate */
	private String endDate;
	
	/* jobTitle */
	private String jobTitle;
	
	/* companyName */
	private String companyName;
	
	/* current */
    private boolean current;
    
    /**
     * Returns the Company Name.
     *
     * @return the Company Name
     */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Returns whether this object is current.
	 *
	 * @return true if this object is current, false otherwise
	 */
	public boolean isCurrent() {
		return current;
	}

	/**
     * Returns the Start Date.
     *
     * @return the Start Date
     */
	public String getStartDate() {
		return startDate;
	}

	/**
     * Returns the End Date.
     *
     * @return the End Date
     */
	public String getEndDate() {
		return endDate;
	}
	
	/**
     * Returns the Job Title.
     *
     * @return the Job Title
     */
	public String getJobTitle() {
		return jobTitle;
	}

}
