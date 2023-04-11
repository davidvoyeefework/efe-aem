package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The EmploymentHistory class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmploymentHistory {
	private String startDate;
	private String endDate;
	private String jobTitle;
	private String companyName;
    private boolean current;
    
	public String getCompanyName() {
		return companyName;
	}

	public boolean isCurrent() {
		return current;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
	public String getJobTitle() {
		return jobTitle;
	}

}
