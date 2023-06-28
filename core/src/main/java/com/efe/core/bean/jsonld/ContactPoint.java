package com.efe.core.bean.jsonld;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class ContactPoint.
 */
public class ContactPoint {

	/** The type. */
	@SerializedName("@type")
	@Expose
	private String type;

	/** The telephone. */
	@Expose
	private String telephone;

	/** The contact type. */
	@Expose
	private String contactType;

	/** The contact option. */
	@Expose
	private String contactOption;

	/** The area served. */
	@Expose
	private String areaServed;

	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the telephone.
	 *
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * Sets the contact type.
	 *
	 * @param contactType the contactType to set
	 */
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	/**
	 * Sets the contact option.
	 *
	 * @param contactOption the contactOption to set
	 */
	public void setContactOption(String contactOption) {
		this.contactOption = contactOption;
	}

	/**
	 * Sets the area served.
	 *
	 * @param areaServed the areaServed to set
	 */
	public void setAreaServed(String areaServed) {
		this.areaServed = areaServed;
	}

}
