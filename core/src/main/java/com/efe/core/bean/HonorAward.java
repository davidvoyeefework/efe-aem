package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The HonorAward class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HonorAward {
    private String dateOfAward;   
    private String disclosure;   
    private String honorAwardName;   
    private String organization;
	public String getDateOfAward() {
		return dateOfAward;
	}
	public String getDisclosure() {
		return disclosure;
	}
	public String getHonorAwardName() {
		return honorAwardName;
	}
	public String getOrganization() {
		return organization;
	}  
}
