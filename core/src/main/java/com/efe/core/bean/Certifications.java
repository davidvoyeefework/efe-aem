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

	/**
	 * Returns the Legal Compliance Disclosure.
	 *
	 * @return the Legal Compliance Disclosure
	 */
	public String getLegalComplianceDisclosure() {
		return legalComplianceDisclosure;
	}

	/**
	 * Returns the Marketing Disclosure.
	 *
	 * @return the Marketing Disclosure
	 */
	public String getMarketingDisclosure() {
		return marketingDisclosure;
	}

	/**
	 * Returns the Name.
	 *
	 * @return the Name
	 */
	public String getName() {
		return name;
	}

}
