package com.efe.core.bean.datalayer;

import com.google.gson.annotations.Expose;

/**
 * The Class DataLayerObj.
 */
public class DataLayerObj {

	/** The event. */
	@Expose
	private String event;

	/** The web. */
	@Expose
	private Web web;

	/**
	 * Sets the event.
	 *
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * Sets the web.
	 *
	 * @param web the web to set
	 */
	public void setWeb(Web web) {
		this.web = web;
	}
	
	
}
