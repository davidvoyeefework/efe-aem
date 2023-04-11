package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Certifications class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Certifications {

	/* abbreviation */
    private String abbreviation;
    
    /* legalComplianceDisclosure */
    private String legalComplianceDisclosure;
    
    /* marketingDisclosure */
    private String marketingDisclosure;
    
    /* name */
    private String name;
	public String getAbbreviation() {
		return abbreviation;
	}

	public String getLegalComplianceDisclosure() {
		return legalComplianceDisclosure;
	}

	public String getMarketingDisclosure() {
		return marketingDisclosure;
	}

	public String getName() {
		return name;
	}

}
