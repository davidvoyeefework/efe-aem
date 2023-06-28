package com.efe.core.bean.jsonld;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class MainEntity.
 */
public class MainEntity {

	/** The type. */
	@SerializedName("@type")
	@Expose
	private String type;

	/** The name. */
	@Expose
	private String name;

	/** The accepted answer. */
	@Expose
	private Answer acceptedAnswer;

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
	 * Sets the accepted answer.
	 *
	 * @param acceptedAnswer the acceptedAnswer to set
	 */
	public void setAcceptedAnswer(Answer acceptedAnswer) {
		this.acceptedAnswer = acceptedAnswer;
	}
}
