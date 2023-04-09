package com.efe.core.services.impl.bean;

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
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getLegalComplianceDisclosure() {
		return legalComplianceDisclosure;
	}
	public void setLegalComplianceDisclosure(String legalComplianceDisclosure) {
		this.legalComplianceDisclosure = legalComplianceDisclosure;
	}
	public String getMarketingDisclosure() {
		return marketingDisclosure;
	}
	public void setMarketingDisclosure(String marketingDisclosure) {
		this.marketingDisclosure = marketingDisclosure;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
