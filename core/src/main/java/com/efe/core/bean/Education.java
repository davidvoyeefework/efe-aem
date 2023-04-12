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
    
    /**
     * Returns the Degree.
     *
     * @return the Degree
     */
	public String getDegree() {
		return degree;
	}

	/**
     * Returns the Major.
     *
     * @return the Major
     */
	public String getMajor() {
		return major;
	}
	
	/**
     * Returns the University.
     *
     * @return the University
     */
	public String getUniversity() {
		return university;
	}
}
