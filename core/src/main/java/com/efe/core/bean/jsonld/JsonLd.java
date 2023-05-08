
package com.efe.core.bean.jsonld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class JsonLd.
 */
public class JsonLd {

	/** The context. */
	@SerializedName("@context")
	@Expose
	private String context;

	/** The type. */
	@SerializedName("@type")
	@Expose
	private String type;

	/** The name. */
	@Expose
	private String name;

	/** The address. */
	@Expose
	private Address address;

	/** The tele phone. */
	@Expose
	private String telePhone;

	/** The url. */
	@Expose
	private String url;

	/** The logo. */
	@Expose
	private String logo;

	/** The same as. */
	@Expose
	private List<String> sameAs;

	/** The geo. */
	@Expose
	private Geo geo;

	/** The contact point. */
	@Expose
	private List<ContactPoint> contactPoint;

	/** The main entity. */
	private List<MainEntity> mainEntity;

	/** The item list element. */
	private List<ItemListElement> itemListElement;

	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * Sets the context.
	 *
	 * @param context the new context
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Gets the tele phone.
	 *
	 * @return the tele phone
	 */
	public String getTelePhone() {
		return telePhone;
	}

	/**
	 * Sets the tele phone.
	 *
	 * @param telePhone the new tele phone
	 */
	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the geo.
	 *
	 * @return the geo
	 */
	public Geo getGeo() {
		return geo;
	}

	/**
	 * Sets the geo.
	 *
	 * @param geo the new geo
	 */
	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	/**
	 * Gets the same as.
	 *
	 * @return the sameAs
	 */
	public List<String> getSameAs() {
		if (null == sameAs) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(sameAs);
	}

	/**
	 * Sets the same as.
	 *
	 * @param sameAs the sameAs to set
	 */
	public void setSameAs(List<String> sameAs) {
		this.sameAs = new ArrayList<>(sameAs);
	}

	/**
	 * Gets the logo.
	 *
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * Sets the logo.
	 *
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * Gets the contact point.
	 *
	 * @return the contactPoint
	 */
	public List<ContactPoint> getContactPoint() {
		if (null == contactPoint) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(contactPoint);
	}

	/**
	 * Sets the contact point.
	 *
	 * @param contactPoint the contactPoint to set
	 */
	public void setContactPoint(List<ContactPoint> contactPoint) {
		this.contactPoint = new ArrayList<>(contactPoint);
	}

	/**
	 * Gets the main entity.
	 *
	 * @return the mainEntity
	 */
	public List<MainEntity> getMainEntity() {
		if (null == mainEntity) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(mainEntity);

	}

	/**
	 * Sets the main entity.
	 *
	 * @param mainEntity the mainEntity to set
	 */
	public void setMainEntity(List<MainEntity> mainEntity) {
		this.mainEntity = new ArrayList<>(mainEntity);
	}

	/**
	 * Gets the item list element.
	 *
	 * @return the itemListElement
	 */
	public List<ItemListElement> getItemListElement() {
		if (null == itemListElement) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(itemListElement);
	}

	/**
	 * Sets the item list element.
	 *
	 * @param itemListElement the itemListElement to set
	 */
	public void setItemListElement(List<ItemListElement> itemListElement) {
		this.itemListElement = new ArrayList<>(itemListElement);
	}

}
