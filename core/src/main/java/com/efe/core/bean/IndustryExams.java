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

    /* getter ExamNameShort */
	public String getExamNameShort() {
		return examNameShort;
	}
	
	/* getter ExamNameLong */
	public String getExamNameLong() {
		return examNameLong;
	}
}
