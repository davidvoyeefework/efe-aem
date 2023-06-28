package com.efe.core.constants;

/**
 * The StatesEnum class.
 * An enum representing the states.
 *
 */
public enum StatesEnum {

	 AA("Armed Forces Americas"), 
	 AE("Armed Forces Europe / Canada / Middle East / Africa"), 
	 AK("Alaska"), 
	 AL("Alabama"),
	 AP("Armed Forces Pacific"),
	 AR("Arkansas"),
	 AS("American Samoa"),
	 AZ("Arizona"),
	 CA("California"),
	 CO("Colorado"),
	 CT("Connecticut"),
	 DC("District of Columbia"),
	 DE("Delaware"),
	 FL("Florida"),
	 FM("Federated States of Micronesia"),
	 GA("Georgia"),
	 GU("Guam GU"),
	 HI("Hawaii"),
	 IA("Iowa"),
	 ID("Idaho"),
	 IL("Illinois"),
	 IN("Indiana"),
	 KS("Kansas"),
	 KY("Kentucky"),
	 LA("Louisiana"),
	 MA("Massachusetts"),
	 MD("Maryland"),
	 Me("Maine"),
	 MH("Marshall Islands"),
	 MI("Michigan"),
	 MN("Minnesota"),
	 MO("Missouri"),
	 MP("Northern Mariana Islands"),
	 MS("Mississippi"),
	 MT("Montana"),
	 NC("North Carolina"),
	 ND("North Dakota"),
	 NE("Nebraska"),
	 NH("New Hampshire"),
	 NJ("New Jersey"),
	 NM("New Mexico"),
	 NV("Nevada"),
	 NY("New York"),
	 OH("Ohio"),
	 OK("Oklahoma"),
	 OR("Oregon"),
	 PA("Pennsylvania"),
	 PR("Puerto Rico"),
	 PW("Palau"),
	 RI("Rhode Island"),
	 SC("South Carolina"),
	 SD("South Dakota"),
	 TN("Tennessee"),
	 TX("Texas"),
	 UT("Utah"),
	 VA("Virginia"),
	 VI("Virgin Islands"),
	 VT("Vermont"),
	 WA("Washington"),
	 WI("Wisconsin"),
	 WV("West Virginia"),
	 WY("Wyoming");

	/**
	 * The name of the state
	 */
	private String stateName;

	/**
	 * Returns the name of the state.
	 * @return the name of the state
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * Constructs a new State object with the specified state name.
	 * @param stateName the name of the state.
	 */
	StatesEnum(String stateName) {
		this.stateName = stateName;
	}
}
