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
	private String name;
	
	/** The accepted answer. */
	private Answer acceptedAnswer;

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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the accepted answer.
	 *
	 * @return the acceptedAnswer
	 */
	public Answer getAcceptedAnswer() {
		return acceptedAnswer;
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
