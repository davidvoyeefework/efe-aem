package com.efe.core.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The LocationResponse class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationResponse {

	/* officeId */
	private String officeId;

	/* RD */
	private String RD;

	/* officeName */
	private String officeName;

	/* externalName */
	private String externalName;

	/* desktopImage */
	private String desktopImage;

	/* mobileImage */
	private String mobileImage;

	/* emergencyClosure */
	private boolean emergencyClosure;

	/* testLocation */
	private boolean testLocation;

	/* buildingComplexName */
	private String buildingComplexName;

	/* address1 */
	private String address1;

	/* address2 */
	private String address2;

	/* city */
	private String city;

	/* state */
	private String state;

	/* zip */
	private String zip;

	/* phone */
	private String phone;

	/* fax */
	private String fax;

	/* tollFree */
	private String tollFree;

	/* latitude */
	private String latitude;

	/* longitude */
	private String longitude;

	/* appointmentOnly */
	private boolean appointmentOnly;

	/* googleReviewLink */
	private String googleReviewLink;

	/* planners */
	private List<String> planners;

	/* overrideCorporateOfficeHours */
	private boolean overrideCorporateOfficeHours;

	/* businessHours */
	private List<BusinessHours> businessHours;

	/* lastUpdated */
	private String lastUpdated;

	/**
	 * Returns the Office Id.
	 *
	 * @return the Office Id
	 */
	public String getOfficeId() {
		return officeId;
	}

	/**
	 * Returns the RD.
	 *
	 * @return the RD
	 */
	public String getRD() {
		return RD;
	}

	/**
	 * Returns the Office Name.
	 *
	 * @return the Office Name
	 */
	public String getOfficeName() {
		return officeName;
	}

	/**
	 * Returns the External Name.
	 *
	 * @return the External Name
	 */
	public String getExternalName() {
		return externalName;
	}

	/**
	 * Returns the Desktop Image.
	 *
	 * @return the Desktop Image
	 */
	public String getDesktopImage() {
		return desktopImage;
	}

	/**
	 * Returns the Mobile Image.
	 *
	 * @return the Mobile Image
	 */
	public String getMobileImage() {
		return mobileImage;
	}

	/**
	 * Returns whether this object is marked for emergency closure.
	 *
	 * @return true if this object is marked for emergency closure, false otherwise
	 */
	public boolean isEmergencyClosure() {
		return emergencyClosure;
	}

	/**
	 * Returns whether this object is a test location.
	 *
	 * @return true if this object is a test location, false otherwise
	 */
	public boolean isTestLocation() {
		return testLocation;
	}

	/**
	 * Returns the Building Complex Name.
	 *
	 * @return the Building Complex Name
	 */
	public String getBuildingComplexName() {
		return buildingComplexName;
	}

	/**
	 * Returns the Address1.
	 *
	 * @return the Address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * Returns the Address2.
	 *
	 * @return the Address2
	 */
	public String getAddress2() {
		return address2;
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
	 * Returns the Phone.
	 *
	 * @return the Phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Returns the Fax.
	 *
	 * @return the Fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Returns the Toll Free.
	 *
	 * @return the Toll Free
	 */
	public String getTollFree() {
		return tollFree;
	}

	/**
	 * Returns the Latitude.
	 *
	 * @return the Latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * Returns the Longitude.
	 *
	 * @return the Longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Returns whether this location is appointment only.
	 *
	 * @return true if this location is appointment only, false otherwise
	 */
	public boolean isAppointmentOnly() {
		return appointmentOnly;
	}

	/**
	 * Returns the Google Review Link.
	 *
	 * @return the Google Review Link
	 */
	public String getGoogleReviewLink() {
		return googleReviewLink;
	}

	/**
	 * Returns the Planners.
	 *
	 * @return the Planners
	 */
	public List<String> getPlanners() {
		if (Objects.nonNull(planners)) {
			return new ArrayList<>(planners);
		}
		return Collections.emptyList();
	}

	/**
	 * Returns whether the corporate office hours are overridden for this object.
	 *
	 * @return true if the corporate office hours are overridden for this object,
	 *         false otherwise
	 */
	public boolean isOverrideCorporateOfficeHours() {
		return overrideCorporateOfficeHours;
	}

	/**
	 * Returns the Business Hours.
	 *
	 * @return the Business Hours
	 */
	public List<BusinessHours> getBusinessHours() {
		if (Objects.nonNull(businessHours)) {
			return new ArrayList<>(businessHours);
		}
		return Collections.emptyList();
	}

	/**
	 * Returns the timestamp when this object was last updated.
	 *
	 * @return the timestamp when this object was last updated
	 */
	public String getLastUpdated() {
		return lastUpdated;
	}

}