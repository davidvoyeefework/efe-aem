package com.efe.core.services.impl.bean;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Junit for LocationResponse Class
 *
 */
class LocationResponseTest {
	private LocationResponse location;

	@BeforeEach
	void setUp() throws Exception {
		location = new LocationResponse("officeId", "RD");
	}

	@Test
	public void testGetAndSetOfficeId() {
		location.setOfficeId("newOfficeId");
		assertEquals("newOfficeId", location.getOfficeId());
	}

	@Test
	public void testGetAndSetRD() {
		location.setRD("newRD");
		assertEquals("newRD", location.getRD());
	}

	@Test
	public void testGetAndSetOfficeName() {
		location.setOfficeName("newOfficeName");
		assertEquals("newOfficeName", location.getOfficeName());
	}

	@Test
	public void testGetAndSetExternalName() {
		location.setExternalName("newExternalName");
		assertEquals("newExternalName", location.getExternalName());
	}

	@Test
	public void testGetAndSetDesktopImage() {
		location.setDesktopImage("newDesktopImage");
		assertEquals("newDesktopImage", location.getDesktopImage());
	}

	@Test
	public void testGetAndSetMobileImage() {
		location.setMobileImage("newMobileImage");
		assertEquals("newMobileImage", location.getMobileImage());
	}

	@Test
	public void testGetAndSetEmergencyClosure() {
		location.setEmergencyClosure(true);
		assertTrue(location.isEmergencyClosure());
		location.setEmergencyClosure(false);
		assertFalse(location.isEmergencyClosure());
	}

	@Test
	public void testGetAndSetTestLocation() {
		location.setTestLocation(true);
		assertTrue(location.isTestLocation());
		location.setTestLocation(false);
		assertFalse(location.isTestLocation());
	}

	@Test
	public void testGetAndSetBuildingComplexName() {
		location.setBuildingComplexName("newBuildingComplexName");
		assertEquals("newBuildingComplexName", location.getBuildingComplexName());
	}

	@Test
	public void testGetAndSetAddress1() {
		location.setAddress1("newAddress1");
		assertEquals("newAddress1", location.getAddress1());
	}

	@Test
	public void testGetAndSetAddress2() {
		location.setAddress2("newAddress2");
		assertEquals("newAddress2", location.getAddress2());
	}

	@Test
	public void testGetAndSetCity() {
		location.setCity("newCity");
		assertEquals("newCity", location.getCity());
	}

	@Test
	void testGetAndSetState() {
		String state = "California";
		location.setState(state);
		assertEquals(state, location.getState());
	}

	@Test
	void testGetAndSetZip() {
		String zip = "90210";
		location.setZip(zip);
		assertEquals(zip, location.getZip());
	}

	@Test
	void testGetAndSetPhone() {
		String phone = "555-555-5555";
		location.setPhone(phone);
		assertEquals(phone, location.getPhone());
	}

	@Test
	void testGetAndSetFax() {
		String fax = "555-555-5556";
		location.setFax(fax);
		assertEquals(fax, location.getFax());
	}

	@Test
	void testGetAndSetTollFree() {
		String tollFree = "800-555-5555";
		location.setTollFree(tollFree);
		assertEquals(tollFree, location.getTollFree());
	}

	@Test
	void testGetAndSetLatitude() {
		String latitude = "37.7749° N";
		location.setLatitude(latitude);
		assertEquals(latitude, location.getLatitude());
	}

	@Test
	void testGetAndSetLongitude() {
		String longitude = "122.4194° W";
		location.setLongitude(longitude);
		assertEquals(longitude, location.getLongitude());
	}

	@Test
	void testIsAndSetAppointmentOnly() {
		boolean appointmentOnly = true;
		location.setAppointmentOnly(appointmentOnly);
		assertEquals(appointmentOnly, location.isAppointmentOnly());
	}

	@Test
	void testGetAndSetGoogleReviewLink() {
		String googleReviewLink = "http://example.com/review";
		location.setGoogleReviewLink(googleReviewLink);
		assertEquals(googleReviewLink, location.getGoogleReviewLink());
	}

	@Test
	void testGetAndSetPlanners() {
		List<String> planners = new ArrayList<>();
		planners.add("John Smith");
		planners.add("Jane Doe");
		location.setPlanners(planners);
		assertEquals(planners, location.getPlanners());
	}

	@Test
	void testIsAndSetOverrideCorporateOfficeHours() {
		boolean overrideCorporateOfficeHours = true;
		location.setOverrideCorporateOfficeHours(overrideCorporateOfficeHours);
		assertEquals(overrideCorporateOfficeHours, location.isOverrideCorporateOfficeHours());
	}

	@Test
	void testGetAndSetBusinessHours() {
		List<BusinessHours> businessHours = new ArrayList<>();
		BusinessHours bh1 = new BusinessHours();
		bh1.setDay("1");
		bh1.setOpeningHours("9:00 AM");
		bh1.setClosingHours("5:00 PM");
		businessHours.add(bh1);
		BusinessHours bh2 = new BusinessHours();
		bh2.setDay("2");
		bh2.setOpeningHours("10:00 AM");
		bh2.setClosingHours("4:00 PM");
		businessHours.add(bh2);
		location.setBusinessHours(businessHours);
		assertEquals(businessHours, location.getBusinessHours());
	}

	@Test
	void testLastUpdated() {
		location.setLastUpdated("2022-12-20T12:10:38.167Z");
		assertEquals(location.getLastUpdated(), "2022-12-20T12:10:38.167Z");
	}

}
