package com.efe.core.services.impl.bean;
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

	public LocationResponse(String string, String string2) {
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getRD() {
		return RD;
	}

	public void setRD(String rD) {
		RD = rD;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getExternalName() {
		return externalName;
	}

	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}

	public String getDesktopImage() {
		return desktopImage;
	}

	public void setDesktopImage(String desktopImage) {
		this.desktopImage = desktopImage;
	}

	public String getMobileImage() {
		return mobileImage;
	}

	public void setMobileImage(String mobileImage) {
		this.mobileImage = mobileImage;
	}

	public boolean isEmergencyClosure() {
		return emergencyClosure;
	}

	public void setEmergencyClosure(boolean emergencyClosure) {
		this.emergencyClosure = emergencyClosure;
	}

	public boolean isTestLocation() {
		return testLocation;
	}

	public void setTestLocation(boolean testLocation) {
		this.testLocation = testLocation;
	}

	public String getBuildingComplexName() {
		return buildingComplexName;
	}

	public void setBuildingComplexName(String buildingComplexName) {
		this.buildingComplexName = buildingComplexName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTollFree() {
		return tollFree;
	}

	public void setTollFree(String tollFree) {
		this.tollFree = tollFree;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public boolean isAppointmentOnly() {
		return appointmentOnly;
	}

	public void setAppointmentOnly(boolean appointmentOnly) {
		this.appointmentOnly = appointmentOnly;
	}

	public String getGoogleReviewLink() {
		return googleReviewLink;
	}

	public void setGoogleReviewLink(String googleReviewLink) {
		this.googleReviewLink = googleReviewLink;
	}

	
	  public List<String> getPlanners() { return planners; }
	  
	  public void setPlanners(List<String> planners) { this.planners = planners; }
	 
	public boolean isOverrideCorporateOfficeHours() {
		return overrideCorporateOfficeHours;
	}

	public void setOverrideCorporateOfficeHours(boolean overrideCorporateOfficeHours) {
		this.overrideCorporateOfficeHours = overrideCorporateOfficeHours;
	}

	public List<BusinessHours> getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(List<BusinessHours> businessHours) {
		this.businessHours = businessHours;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}