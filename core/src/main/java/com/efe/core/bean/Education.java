package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Education class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Education {
	
	/* degree */
    private String degree;
    
    /* major */
    private String major;
    
    /* university */
    private String university;
    
    /* getter for Degree */
	public String getDegree() {
		return degree;
	}

	/* getter for Major */
	public String getMajor() {
		return major;
	}
	
	/* getter for university */
	public String getUniversity() {
		return university;
	}
}
