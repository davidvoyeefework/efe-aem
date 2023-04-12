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
	
	/**
     * Returns the Disclosure.
     *
     * @return the Disclosure
     */
	public String getDisclosure() {
		return disclosure;
	}
	
	/**
     * Returns the Honor Award Name.
     *
     * @return the Honor Award Name
     */
	public String getHonorAwardName() {
		return honorAwardName;
	}
	
	/**
     * Returns the Organization.
     *
     * @return the Organization
     */
	public String getOrganization() {
		return organization;
	}  
}
