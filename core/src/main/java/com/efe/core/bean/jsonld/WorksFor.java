
package com.efe.core.bean.jsonld;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class WorksFor.
 */
public class WorksFor {

	/** The type. */
	@SerializedName("@type")
	@Expose
	public String type = "Organization";

	/** The name. */
	@Expose
	public String name;

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

}
