package com.efe.core.bean.datalayer;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class WebDetails.
 */
public class WebDetails {

	/** The name. */
	@Expose
	private String name;

	/** The site section. */
	@Expose
	private String siteSection = StringUtils.EMPTY;

	/** The url. */
	@SerializedName("URL")
	@Expose
	private String url;

	/** The server. */
	@Expose
	private String server;

	/** The error page. */
	@Expose
	private String errorPage;

	/** The site section level 2. */
	@Expose
	private String siteSectionLevel2 = StringUtils.EMPTY;

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the site section.
	 *
	 * @param siteSection the siteSection to set
	 */
	public void setSiteSection(String siteSection) {
		this.siteSection = siteSection;
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
	 * Sets the server.
	 *
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * Sets the error page.
	 *
	 * @param errorPage the errorPage to set
	 */
	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	/**
	 * Sets the site section level 2.
	 *
	 * @param siteSectionLevel2 the siteSectionLevel2 to set
	 */
	public void setSiteSectionLevel2(String siteSectionLevel2) {
		this.siteSectionLevel2 = siteSectionLevel2;
	}
}
