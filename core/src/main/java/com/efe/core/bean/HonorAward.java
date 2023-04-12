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
	
	/* getter Disclosure */
	public String getDisclosure() {
		return disclosure;
	}
	
	/* getter HonorAwardName */
	public String getHonorAwardName() {
		return honorAwardName;
	}
	
	/* getter Organization */
	public String getOrganization() {
		return organization;
	}  
}
