package com.efe.core.bean;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The LocationResponse class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationResponse {

    private String officeId;

    private String RD;

    private String officeName;

    private String externalName;

    private String desktopImage;

    private String mobileImage;

    private boolean emergencyClosure;

    private boolean testLocation;

    private String buildingComplexName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String fax;

    private String tollFree;
    private String latitude;
    
    private String longitude;

    private boolean appointmentOnly;

    private String googleReviewLink;
    private List<String> planners;
  
    private boolean overrideCorporateOfficeHours;

    private List<BusinessHours> businessHours;

    private String lastUpdated;

    public String getOfficeId() {
		return officeId;
	}

	public String getRD() {
		return RD;
	}


	public String getOfficeName() {
		return officeName;
	}


	public String getExternalName() {
		return externalName;
	}

	public String getDesktopImage() {
		return desktopImage;
	}


	public String getMobileImage() {
		return mobileImage;
	}


	public boolean isEmergencyClosure() {
		return emergencyClosure;
	}


	public boolean isTestLocation() {
		return testLocation;
	}

	public String getBuildingComplexName() {
		return buildingComplexName;
	}


	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getCity() {
		return city;
	}


	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}


	public String getPhone() {
		return phone;
	}


	public String getFax() {
		return fax;
	}

	public String getTollFree() {
		return tollFree;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public boolean isAppointmentOnly() {
		return appointmentOnly;
	}

	public String getGoogleReviewLink() {
		return googleReviewLink;
	}
	
	  public List<String> getPlanners() { 
		  List plannersList = List.copyOf(planners);
		  return plannersList; }

	 
	public boolean isOverrideCorporateOfficeHours() {
		return overrideCorporateOfficeHours;
	}


	public List<BusinessHours> getBusinessHours() {
		List businessHoursList = List.copyOf(businessHours);
		return businessHoursList;
	}


	public String getLastUpdated() {
		return lastUpdated;
	}

}