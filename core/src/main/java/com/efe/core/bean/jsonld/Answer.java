package com.efe.core.bean.jsonld;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class Answer.
 */
public class Answer {

	/** The type. */
	@SerializedName("@type")
	@Expose
	private String type;

	/** The text. */
	private String text;

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
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
}
