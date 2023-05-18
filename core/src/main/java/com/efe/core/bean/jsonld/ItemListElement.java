package com.efe.core.bean.jsonld;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class ItemListElement.
 */
public class ItemListElement {

	/** The type. */
	@SerializedName("@type")
	@Expose
	private String type;

	/** The position. */
	@Expose
	private int position;

	/** The name. */
	@Expose
	private String name;

	/** The item. */
	@Expose
	private String item;

	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the position.
	 *
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
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
	 * Sets the item.
	 *
	 * @param item the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

}
