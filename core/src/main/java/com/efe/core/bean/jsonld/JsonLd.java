
package com.efe.core.bean.jsonld;

import java.util.ArrayList;
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
	
	/** The description. */
	@Expose
	private String description;
	
	/** The thumbnail url. */
	@Expose
	private String thumbnailUrl;
	
	/** The upload date. */
	@Expose
	private String uploadDate;
	
	/** The duration. */
	@Expose
	private String duration;
	
	/** The embed url. */
	@Expose
	private String embedUrl;

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
	@Expose
	private List<MainEntity> mainEntity;

	/** The item list element. */
	@Expose
	private List<ItemListElement> itemListElement;
	
	/** The heading. */
	@Expose
	private String heading;
	
	/** The alternative headline. */
	@Expose
	private String alternativeHeadline;
	
	/** The image. */
	@Expose
	private String image;
	
	/** The wordcount. */
	@Expose
	private String wordcount;
	
	/** The date published. */
	@Expose
	private String datePublished;
	
	/** The date modified. */
	@Expose
	private String dateModified;
	
	/** The author. */
	@Expose
	private Author author;
	

	/**
	 * Sets the context.
	 *
	 * @param context the new context
	 */
	public void setContext(String context) {
		this.context = context;
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
	 * the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Sets the tele phone.
	 *
	 * @param telePhone the new tele phone
	 */
	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
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
	 * Sets the geo.
	 *
	 * @param geo the new geo
	 */
	public void setGeo(Geo geo) {
		this.geo = geo;
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
	 * Sets the logo.
	 *
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
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
	 * Sets the main entity.
	 *
	 * @param mainEntity the mainEntity to set
	 */
	public void setMainEntity(List<MainEntity> mainEntity) {
		this.mainEntity = new ArrayList<>(mainEntity);
	}

	/**
	 * Sets the item list element.
	 *
	 * @param itemListElement the itemListElement to set
	 */
	public void setItemListElement(List<ItemListElement> itemListElement) {
		this.itemListElement = new ArrayList<>(itemListElement);
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the thumbnail url.
	 *
	 * @param thumbnailUrl the thumbnailUrl to set
	 */
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	/**
	 * Sets the upload date.
	 *
	 * @param uploadDate the uploadDate to set
	 */
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Sets the embed url.
	 *
	 * @param embedUrl the embedUrl to set
	 */
	public void setEmbedUrl(String embedUrl) {
		this.embedUrl = embedUrl;
	}

	/**
	 * Sets the heading.
	 *
	 * @param heading the heading to set
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	/**
	 * Sets the alternative headline.
	 *
	 * @param alternativeHeadline the alternativeHeadline to set
	 */
	public void setAlternativeHeadline(String alternativeHeadline) {
		this.alternativeHeadline = alternativeHeadline;
	}

	/**
	 * Sets the image.
	 *
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Sets the wordcount.
	 *
	 * @param wordcount the wordcount to set
	 */
	public void setWordcount(String wordcount) {
		this.wordcount = wordcount;
	}

	/**
	 * Sets the date published.
	 *
	 * @param datePublished the datePublished to set
	 */
	public void setDatePublished(String datePublished) {
		this.datePublished = datePublished;
	}

	/**
	 * Sets the date modified.
	 *
	 * @param dateModified the dateModified to set
	 */
	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}

}
