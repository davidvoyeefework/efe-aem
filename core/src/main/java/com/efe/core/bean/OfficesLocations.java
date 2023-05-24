package com.efe.core.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The OfficesLocations class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfficesLocations {

	/** The id. */
	/* id */
	private String id;

	/** The name. */
	/* name */
	private String name;

	/** The city. */
	/* city */
	private String city;

	/** The state. */
	/* state */
	private String state;

	/** The zip. */
	/* zip */
	private String zip;

	/**
	 * Returns the Id.
	 *
	 * @return the Id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the Name.
	 *
	 * @return the Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the City.
	 *
	 * @return the City
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Returns the State.
	 *
	 * @return the State
	 */
	public String getState() {
		return state;
	}

	/**
	 * Returns the Zip.
	 *
	 * @return the Zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * Sets the city.
	 *
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Sets the zip.
	 *
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

}
