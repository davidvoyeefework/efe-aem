
package com.efe.core.bean.jsonld;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class Address.
 */
public class Address {

	/** The type. */
	@SerializedName("@type")
	@Expose
	private String type;

	/** The street address. */
	@Expose
	private String streetAddress;

	/** The address locality. */
	@Expose
	private String addressLocality;

	/** The address region. */
	@Expose
	private String addressRegion;

	/** The postal code. */
	@Expose
	private String postalCode;

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the street address.
	 *
	 * @param streetAddress the new street address
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * Sets the address locality.
	 *
	 * @param addressLocality the new address locality
	 */
	public void setAddressLocality(String addressLocality) {
		this.addressLocality = addressLocality;
	}

	/**
	 * Sets the address region.
	 *
	 * @param addressRegion the new address region
	 */
	public void setAddressRegion(String addressRegion) {
		this.addressRegion = addressRegion;
	}

	/**
	 * Sets the postal code.
	 *
	 * @param postalCode the new postal code
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
