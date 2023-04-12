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
    
    /* getter CompanyName */
	public String getCompanyName() {
		return companyName;
	}

	/* getter isCurrent */
	public boolean isCurrent() {
		return current;
	}

	/* getter StartDate */
	public String getStartDate() {
		return startDate;
	}

	/* getter EndDate */
	public String getEndDate() {
		return endDate;
	}
	
	/* getter JobTitle */
	public String getJobTitle() {
		return jobTitle;
	}

}
