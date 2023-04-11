package com.efe.core.bean;
import java.util.List;

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