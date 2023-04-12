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

	/* getter OfficeId */
	public String getOfficeId() {
		return officeId;
	}

	/* getter RD */
	public String getRD() {
		return RD;
	}

	/* getter OfficeName */
	public String getOfficeName() {
		return officeName;
	}

	/* getter ExternalName */
	public String getExternalName() {
		return externalName;
	}

	/* getter DesktopImage */
	public String getDesktopImage() {
		return desktopImage;
	}

	/* getter MobileImage */
	public String getMobileImage() {
		return mobileImage;
	}
	
	/* getter EmergencyClosure */
	public boolean isEmergencyClosure() {
		return emergencyClosure;
	}

	/* getter TestLocation */
	public boolean isTestLocation() {
		return testLocation;
	}

	/* getter BuildingComplexName */
	public String getBuildingComplexName() {
		return buildingComplexName;
	}

	/* getter Address1 */
	public String getAddress1() {
		return address1;
	}

	/* getter Address2 */
	public String getAddress2() {
		return address2;
	}

	/* getter City */
	public String getCity() {
		return city;
	}

	/* getter State */
	public String getState() {
		return state;
	}

	/* getter Zip */
	public String getZip() {
		return zip;
	}

	/* getter Phone */
	public String getPhone() {
		return phone;
	}

	/* getter Fax */
	public String getFax() {
		return fax;
	}

	/* getter TollFree */
	public String getTollFree() {
		return tollFree;
	}

	/* getter Latitude */
	public String getLatitude() {
		return latitude;
	}

	/* getter Longitude */
	public String getLongitude() {
		return longitude;
	}

	/* getter AppointmentOnly */
	public boolean isAppointmentOnly() {
		return appointmentOnly;
	}

	/* getter GoogleReviewLink */
	public String getGoogleReviewLink() {
		return googleReviewLink;
	}

	/* getter Planners */
	public List<String> getPlanners() {
        if (Objects.nonNull(planners)) {
			return new ArrayList<>(planners);
		}
		return Collections.emptyList();
	}

	/* getter OverrideCorporateOfficeHours */
	public boolean isOverrideCorporateOfficeHours() {
		return overrideCorporateOfficeHours;
	}

	/* getter BusinessHours */
	public List<BusinessHours> getBusinessHours() {
        if (Objects.nonNull(businessHours)) {
			return new ArrayList<>(businessHours);
		}
		return Collections.emptyList();
	}

	/* getter LastUpdated */
	public String getLastUpdated() {
		return lastUpdated;
	}

}