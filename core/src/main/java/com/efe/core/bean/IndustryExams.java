package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The IndustryExams class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndustryExams {

	/* examNameShort */
	private String examNameShort;

	/* examNameLong */
	private String examNameLong;

	/**
	 * Returns the Exam Name Short.
	 *
	 * @return the Exam Name Short
	 */
	public String getExamNameShort() {
		return examNameShort;
	}

	/**
	 * Returns the Exam Name Long.
	 *
	 * @return the Exam Name Long
	 */
	public String getExamNameLong() {
		return examNameLong;
	}
}
