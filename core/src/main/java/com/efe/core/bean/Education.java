package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Education class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Education {
    private String degree;
    private String major;
    private String university;
	public String getDegree() {
		return degree;
	}

	public String getMajor() {
		return major;
	}
	public String getUniversity() {
		return university;
	}
}
