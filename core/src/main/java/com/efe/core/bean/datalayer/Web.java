package com.efe.core.bean.datalayer;

import com.google.gson.annotations.Expose;

/**
 * The Class Web.
 */
public class Web {

	/** The web page details. */
	@Expose
	private WebDetails webPageDetails;

	/**
	 * Sets the web page details.
	 *
	 * @param webPageDetails the webPageDetails to set
	 */
	public void setWebPageDetails(WebDetails webPageDetails) {
		this.webPageDetails = webPageDetails;
	}
	
}
