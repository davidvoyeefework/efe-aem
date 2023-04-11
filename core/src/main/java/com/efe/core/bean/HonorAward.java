package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The HonorAward class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HonorAward {
	
	/* dateOfAward */
    private String dateOfAward;   
    
    /* disclosure */
    private String disclosure; 
    
    /* honorAwardName */
    private String honorAwardName;   
    
    /* organization */
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
