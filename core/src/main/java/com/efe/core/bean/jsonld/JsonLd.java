
package com.efe.core.bean.jsonld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonLd {

	@SerializedName("@context")
	@Expose
	private String context;

	@SerializedName("@type")
	@Expose
	private String type;

	@Expose
	private String name;

	@Expose
	private Address address;

	@Expose
	private String telePhone;

	@Expose
	private String url;

	@Expose
	private String logo;

	@Expose
	private List<String> sameAs;

	@Expose
	private Geo geo;

	@Expose
	private List<ContactPoint> contactPoint;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getTelePhone() {
		return telePhone;
	}

	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}


	/**
	 * @return the sameAs
	 */
	public List<String> getSameAs() {
		if(null == sameAs) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(sameAs);
	}

	/**
	 * @param sameAs the sameAs to set
	 */
	public void setSameAs(List<String> sameAs) {
		this.sameAs = new ArrayList<>(sameAs);
	}

	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return the contactPoint
	 */
	public List<ContactPoint> getContactPoint() {
		if(null == contactPoint) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(contactPoint);
	}

	/**
	 * @param contactPoint the contactPoint to set
	 */
	public void setContactPoint(List<ContactPoint> contactPoint) {
		this.contactPoint = new ArrayList<>(contactPoint);
	}

}
