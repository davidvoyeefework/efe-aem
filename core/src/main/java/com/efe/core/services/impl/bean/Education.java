package com.efe.core.services.impl.bean;

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
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}

}
