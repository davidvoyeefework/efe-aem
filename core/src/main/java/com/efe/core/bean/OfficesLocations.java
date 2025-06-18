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

	// External name for office
	private String externalName;


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

	public String getExternalName() {
		return externalName;
	}
}
