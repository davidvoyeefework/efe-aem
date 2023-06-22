
package com.efe.core.bean.jsonld;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class Author.
 */
public class Author {

	/** The context. */
	@SerializedName("@context")
	@Expose
	private String context;

	/** The type. */
	@SerializedName("@type")
	@Expose
	public String type = "Person";

	/** The name. */
	@Expose
	public String name;

	/** The url. */
	@Expose
	public String url;

	/** The address. */
	@Expose
	public Address address;

	/** The has credential. */
	@Expose
	public String hasCredential;

	/** The image. */
	@Expose
	public String image;

	/** The job title. */
	@Expose
	public String jobTitle;

	/** The knows about. */
	@Expose
	public KnowsAbout knowsAbout;

	/** The works for. */
	@Expose
	public WorksFor worksFor;

	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Sets the checks for credential.
	 *
	 * @param hasCredential the hasCredential to set
	 */
	public void setHasCredential(String hasCredential) {
		this.hasCredential = hasCredential;
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
	 * Sets the job title.
	 *
	 * @param jobTitle the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * Sets the knows about.
	 *
	 * @param knowsAbout the knowsAbout to set
	 */
	public void setKnowsAbout(KnowsAbout knowsAbout) {
		this.knowsAbout = knowsAbout;
	}

	/**
	 * Sets the works for.
	 *
	 * @param worksFor the worksFor to set
	 */
	public void setWorksFor(WorksFor worksFor) {
		this.worksFor = worksFor;
	}

	/**
	 * Sets the context.
	 *
	 * @param context the new context
	 */
	public void setContext(String context) {
		this.context = context;
	}

}
