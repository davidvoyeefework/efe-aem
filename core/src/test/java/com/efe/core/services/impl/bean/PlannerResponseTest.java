package com.efe.core.services.impl.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Junit for PlannerResponse Class
 *
 */
class PlannerResponseTest {

	PlannerResponse response = new PlannerResponse();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testLastUpdated() {
		response.setLastUpdated("2022-12-20T12:10:38.167Z");
		assertEquals(response.getLastUpdated(), "2022-12-20T12:10:38.167Z");
	}

	@Test
	public void testIncludeInADV2B() {
		response.setIncludeInADV2B(true);
		assertTrue(response.isIncludeInADV2B());
	}

	@Test
	public void testIncludeInApiPayload() {
		response.setIncludeInApiPayload(true);
		assertTrue(response.isIncludeInApiPayload());
	}

	@Test
	public void testFirstName() {
		response.setFirstName("John");
		assertEquals("John", response.getFirstName());
	}

	@Test
	public void testFirstNameAlias() {
		response.setFirstNameAlias("Johnny");
		assertEquals("Johnny", response.getFirstNameAlias());
	}

	@Test
	public void testMiddleName() {
		response.setMiddleName("David");
		assertEquals("David", response.getMiddleName());
	}

	@Test
	public void testLastName() {
		response.setLastName("Doe");
		assertEquals("Doe", response.getLastName());
	}

	@Test
	public void testId() {
		response.setId(1);
		assertEquals(1, response.getId());
	}

	@Test
	public void testSuffix() {
		response.setSuffix("Jr.");
		assertEquals("Jr.", response.getSuffix());
	}

	@Test
	public void testTitle() {
		response.setTitle("Mr.");
		assertEquals("Mr.", response.getTitle());
	}

	@Test
	public void testBirthYear() {
		response.setBirthYear(1980);
		assertEquals(1980, response.getBirthYear());
	}

	@Test
	public void testGetYearJoined() {
		response.setYearJoined(2022);

		assertEquals(2022, response.getYearJoined());
	}

	@Test
	public void testGetDirectLinePhone() {
		response.setDirectLinePhone("1234567890");

		assertEquals("1234567890", response.getDirectLinePhone());
	}

	@Test
	public void testGetAdvisorCRD() {
		response.setAdvisorCRD(123456);

		assertEquals(123456, response.getAdvisorCRD());
	}

	@Test
	public void testGetDesktopImageUrl() {
		response.setDesktopImageUrl("https://example.com/desktop.png");

		assertEquals("https://example.com/desktop.png", response.getDesktopImageUrl());
	}

	@Test
	public void testGetMobileImageUrl() {
		response.setMobileImageUrl("https://example.com/mobile.png");

		assertEquals("https://example.com/mobile.png", response.getMobileImageUrl());
	}

	@Test
	public void testGetCircleImageUrl() {
		response.setCircleImageUrl("https://example.com/circle.png");

		assertEquals("https://example.com/circle.png", response.getCircleImageUrl());
	}

	@Test
	public void testGetPrimaryOffice() {
		PrimaryOffice primaryOffice = new PrimaryOffice();
		primaryOffice.setName("Example Office");
		primaryOffice.setCity("123 Main St");
		response.setPrimaryOffice(primaryOffice);

		assertEquals("Example Office", response.getPrimaryOffice().getName());
		assertEquals("123 Main St", response.getPrimaryOffice().getCity());
	}

	@Test
	public void testGetOfficesLocations() {
		PlannerResponse response = new PlannerResponse();
		List<OfficesLocations> officesLocations = new ArrayList<>();
		OfficesLocations office1 = new OfficesLocations();
		office1.setName("Example Office 1");
		office1.setCity("123 Main St");
		officesLocations.add(office1);
		OfficesLocations office2 = new OfficesLocations();
		office2.setName("Example Office 2");
		office2.setCity("456 Main St");
		officesLocations.add(office2);
		response.setOfficesLocations(officesLocations);

		assertEquals("Example Office 1", response.getOfficesLocations().get(0).getName());
		assertEquals("123 Main St", response.getOfficesLocations().get(0).getCity());
		assertEquals("Example Office 2", response.getOfficesLocations().get(1).getName());
		assertEquals("456 Main St", response.getOfficesLocations().get(1).getCity());
	}

	@Test
	void testGetLastUpdated() {
		String expectedLastUpdated = "2023-04-10";
		response.setLastUpdated(expectedLastUpdated);
		assertEquals(expectedLastUpdated, response.getLastUpdated());
	}

	@Test
	void testIsIncludeInADV2B() {
		assertFalse(response.isIncludeInADV2B());
	}

	@Test
	void testSetIncludeInADV2B() {
		response.setIncludeInADV2B(true);
		assertTrue(response.isIncludeInADV2B());
	}

	@Test
	void testEmploymentHistory() {
		EmploymentHistory emp1 = new EmploymentHistory();
		EmploymentHistory emp2 = new EmploymentHistory();
		List<EmploymentHistory> expectedEmpHistory = new ArrayList<>(Arrays.asList(emp1, emp2));
		response.setEmploymentHistory(expectedEmpHistory);
		assertEquals(expectedEmpHistory, response.getEmploymentHistory());
	}

	@Test
	void testCertifications() {
		Certifications cert1 = new Certifications();
		Certifications cert2 = new Certifications();
		List<Certifications> expectedCertifications = new ArrayList<>(Arrays.asList(cert1, cert2));
		response.setCertifications(expectedCertifications);
		assertEquals(expectedCertifications, response.getCertifications());
	}

	@Test
	void testInterestsHobbies() {
		List<String> expectedInterests = new ArrayList<>(Arrays.asList("Interest A", "Interest B"));
		response.setInterestsHobbies(expectedInterests);
		assertEquals(expectedInterests, response.getInterestsHobbies());
	}

	@Test
	void testHonorAward() {
		HonorAward award1 = new HonorAward();
		HonorAward award2 = new HonorAward();
		List<HonorAward> expectedAwards = new ArrayList<>(Arrays.asList(award1, award2));
		response.setHonorAward(expectedAwards);
		assertEquals(expectedAwards, response.getHonorAward());
	}

	@Test
	void testIndustryExams() {
		IndustryExams exam1 = new IndustryExams();
		IndustryExams exam2 = new IndustryExams();
		List<IndustryExams> expectedExams = new ArrayList<>(Arrays.asList(exam1, exam2));
		response.setIndustryExams(expectedExams);
		assertEquals(expectedExams, response.getIndustryExams());
	}

	@Test
	void testSetIndustryExams() {
		List<IndustryExams> exams = new ArrayList<>();
		exams.add(new IndustryExams());
		exams.add(new IndustryExams());

		response.setIndustryExams(exams);

		assertEquals(exams, response.getIndustryExams());
	}

	@Test
	void testSetHasDisciplinaryInformation() {
		response.setHasDisciplinaryInformation(true);
		assertTrue(response.isHasDisciplinaryInformation());

		response.setHasDisciplinaryInformation(false);
		assertFalse(response.isHasDisciplinaryInformation());
	}

	@Test
	void testSetDisciplinaryInformationText() {
		String text = "Some disciplinary information";
		response.setDisciplinaryInformationText(text);
		assertEquals(text, response.getDisciplinaryInformationText());
	}

	@Test
	void testSetAnyBusinessRelatedActivitiesCommissions() {
		response.setAnyBusinessRelatedActivitiesCommissions(true);
		assertTrue(response.isAnyBusinessRelatedActivitiesCommissions());

		response.setAnyBusinessRelatedActivitiesCommissions(false);
		assertFalse(response.isAnyBusinessRelatedActivitiesCommissions());
	}

	@Test
	void testSetBusinessRelatedActivitiesCommissionsText() {
		String text = "Some business related activities commissions";
		response.setBusinessRelatedActivitiesCommissionstext(text);
		assertEquals(text, response.getBusinessRelatedActivitiesCommissionsText());
	}

	@Test
	void testSetAnyAdditionalCompensation() {
		response.setAnyAdditionalCompensation(true);
		assertTrue(response.isAnyAdditionalCompensation());

		response.setAnyAdditionalCompensation(false);
		assertFalse(response.isAnyAdditionalCompensation());
	}

	@Test
	void testSetAdditionalCompensationText() {
		String text = "Some additional compensation";
		response.setAdditionalCompensationText(text);
		assertEquals(text, response.getAdditionalCompensationText());
	}

	@Test
	void testSetOtherBooks() {
		List<String> books = new ArrayList<>();
		books.add("book1");
		books.add("book2");

		response.setOtherBooks(books);

		assertEquals(books, response.getOtherBooks());
	}

	@Test
	void testSetEfeUrl() {
		String url = "http://example.com/efe";
		response.setEfeUrl(url);
		assertEquals(url, response.getEfeUrl());
	}

	@Test
	void testSetAdv2bUrl() {
		String url = "http://example.com/adv2b";
		response.setAdv2bUrl(url);
		assertEquals(url, response.getAdv2bUrl());
	}

	@Test
	void testSetHtmlUrl() {
		String url = "http://example.com/html";
		response.setHtmlUrl(url);
		assertEquals(url, response.getHtmlUrl());
	}
}
