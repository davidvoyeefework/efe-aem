package com.efe.core.services.impl.bean;

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

	public String getExamNameShort() {
		return examNameShort;
	}

	public void setExamNameShort(String examNameShort) {
		this.examNameShort = examNameShort;
	}

	public String getExamNameLong() {
		return examNameLong;
	}

	public void setExamNameLong(String examNameLong) {
		this.examNameLong = examNameLong;
	}

}
