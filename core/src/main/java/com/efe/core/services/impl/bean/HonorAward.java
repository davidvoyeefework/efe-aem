package com.efe.core.services.impl.bean;

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
	public void setDateOfAward(String dateOfAward) {
		this.dateOfAward = dateOfAward;
	}
	public String getDisclosure() {
		return disclosure;
	}
	public void setDisclosure(String disclosure) {
		this.disclosure = disclosure;
	}
	public String getHonorAwardName() {
		return honorAwardName;
	}
	public void setHonorAwardName(String honorAwardName) {
		this.honorAwardName = honorAwardName;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}   
}
