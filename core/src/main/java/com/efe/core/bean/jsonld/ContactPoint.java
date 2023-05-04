package com.efe.core.bean.jsonld;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactPoint {

	@SerializedName("@type")
	@Expose
	private String type;

	@Expose
	private String telephone;

	@Expose
	private String contactType;

	@Expose
	private String contactOption;

	@Expose
	private String areaServed;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the contactType
	 */
	public String getContactType() {
		return contactType;
	}

	/**
	 * @param contactType the contactType to set
	 */
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	/**
	 * @return the contactOption
	 */
	public String getContactOption() {
		return contactOption;
	}

	/**
	 * @param contactOption the contactOption to set
	 */
	public void setContactOption(String contactOption) {
		this.contactOption = contactOption;
	}

	/**
	 * @return the areaServed
	 */
	public String getAreaServed() {
		return areaServed;
	}

	/**
	 * @param areaServed the areaServed to set
	 */
	public void setAreaServed(String areaServed) {
		this.areaServed = areaServed;
	}

}
