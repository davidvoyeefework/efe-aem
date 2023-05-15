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
	 * Sets the degree
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}

	/**
	 * Sets the Major.
	 */
	public void setMajor(String major) {
		this.major = major;
	}

	/**
	 * Sets the University.
	 */
	public void setUniversity(String university) {
		this.university = university;
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
