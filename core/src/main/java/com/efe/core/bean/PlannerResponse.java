package com.efe.core.bean; 

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The PlannerResponse class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlannerResponse {

	/* includeInADV2B */
	private boolean includeInADV2B;

	/* includeInApiPayload */
	private boolean includeInApiPayload;

	/* firstName */
	private String firstName;

	/* firstNameAlias */
	private String firstNameAlias;

	/* middleName */
	private String middleName;

	/* lastName */
	private String lastName;

	/* id */
	private int id;

	/* suffix */
	private String suffix;

	/* title */
	private String title;

	/* birthYear */
	private int birthYear;

	/* yearJoined */
	private int yearJoined;

	/* directLinePhone */
	private String directLinePhone;

	/* advisorCRD */
	private int advisorCRD;

	/* supportStaff */
	private List<SupportStaff> supportStaff;

	/* desktopImageUrl */
	private String desktopImageUrl;

	/* mobileImageUrl */
	private String mobileImageUrl;

	/* circleImageUrl */
	private String circleImageUrl;

	/* primaryOffice */
	private PrimaryOffice primaryOffice;

	/* officesLocations */
	private List<OfficesLocations> officesLocations;

	/* educationIndicator */
	private String educationIndicator;

	/* education */
	private List<Education> education;

	/* taxPlanner */
	private boolean taxPlanner;

	/* advancedPlanning */
	private boolean advancedPlanning;

	/* estatePlanning */
	private boolean estatePlanning;

	/* insurancePlanning */
	private boolean insurancePlanning;

	/* investmentManagement */
	private boolean investmentManagement;

	/* employmentHistory */
	private List<EmploymentHistory> employmentHistory;

	/* certifications */
	private List<Certifications> certifications;
	
	/** The certifications abbrevations. */
	private List<String> certificationsAbbrevations;

	/* bio */
	private String bio;

	/* email */
	private String email;

	/* teamDistributionEmailAddress */
	private String teamDistributionEmailAddress;

	/* yearStartedIndustry */
	private int yearStartedIndustry;

	/* smartVestorProIndicator */
	private boolean smartVestorProIndicator;

	/* interestsHobbies */
	private List<String> interestsHobbies;

	/* funFacts */
	private String funFacts;

	/* honorAward */
	private List<HonorAward> honorAward;

	/* favoriteSport */
	private String favoriteSport;
        
	/* bioVideo */
	private String bioVideo;

	/* favoriteSportsTeam */
	private String favoriteSportsTeam;

	/* favoriteLifeHack */
	private String favoriteLifeHack;

	/* linkedInUrl */
	private String linkedInUrl;

	/* mostInspirationalMoment */
	private String mostInspirationalMoment;

	/* industryExams */
	private List<IndustryExams> industryExams;

	/* hasDisciplinaryInformation */
	private boolean hasDisciplinaryInformation;

	/* disciplinaryInformationText */
	private String disciplinaryInformationText;

	/* anyBusinessRelatedActivitiesCommissions */
	private boolean anyBusinessRelatedActivitiesCommissions;

	/* businessRelatedActivitiesCommissionsText */
	private String businessRelatedActivitiesCommissionsText;

	/* anyAdditionalCompensation */
	private boolean anyAdditionalCompensation;

	/* additionalCompensationText */
	private String additionalCompensationText;

	/* efeUrl */
	private String efeUrl;

	/* adv2bUrl */
	private String adv2bUrl;

	/* htmlUrl */
	private String htmlUrl;

	/* lastUpdated */
	private String lastUpdated;

	/* url */
	private String url;

	/* Sets firstName */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 *  Returns the url.
	 *
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/* Sets url */
	public void setUrl(String url) {
		this.url = url;
	}

	/* Sets middleName */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/* Sets lastName */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/* Sets id */
	public void setId(int id) {
		this.id = id;
	}

	/* Sets title */
	public void setTitle(String title) {
		this.title = title;
	}

	/* Sets desktopImageUrl */
	public void setDesktopImageUrl(String desktopImageUrl) {
		this.desktopImageUrl = desktopImageUrl;
	}

	public void setSupportStaff(List<SupportStaff> supportStaff) {
	    this.supportStaff = new ArrayList<>(supportStaff);
	}

	/* Sets mobileImageUrl */
	public void setMobileImageUrl(String mobileImageUrl) {
		this.mobileImageUrl = mobileImageUrl;
	}

	/* Sets education */
	public void setEducation(List<Education> education) {

		this.education = new ArrayList<>(education);
	}

	/* Sets certifications */
	public void setCertifications(List<Certifications> certifications) {

		this.certifications = new ArrayList<>(certifications);
	}

	/* Sets bio */
	public void setBio(String bio) {
		this.bio = bio;
	}

	// Sets Bio Video
	public void setBioVideo(String bioVideo) {
		this.bioVideo = bioVideo;
	}

	/* Sets firstNameAlias */
	public void setFirstNameAlias(String firstNameAlias) {
		this.firstNameAlias = firstNameAlias;
	}

	/* getter lastUpdated */
	public String getLastUpdated() {
		return lastUpdated;
	}

	/* getter IncludeInADV2B */
	public boolean isIncludeInADV2B() {
		return includeInADV2B;
	}

	/* getter IncludeInApiPayload */
	public boolean isIncludeInApiPayload() {
		return includeInApiPayload;
	}

	/**
	 * Returns the First Name.
	 *
	 * @return the First Name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the First Name Alias.
	 *
	 * @return the First Name Alias
	 */
	public String getFirstNameAlias() {
		return firstNameAlias;
	}

	/**
	 * Returns the Middle Name.
	 *
	 * @return the Middle Name
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Returns the Last Name.
	 *
	 * @return the Last Name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the Id.
	 *
	 * @return the Id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the Suffix.
	 *
	 * @return the Suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * Returns the Title.
	 *
	 * @return the Title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the Birth Year.
	 *
	 * @return the Birth Year
	 */
	public int getBirthYear() {
		return birthYear;
	}

	/**
	 * Returns the Year Joined.
	 *
	 * @return the Year Joined
	 */
	public int getYearJoined() {
		return yearJoined;
	}

	/**
	 * Returns the Direct Line Phone.
	 *
	 * @return the Direct Line Phone
	 */
	public String getDirectLinePhone() {
		return directLinePhone;
	}

	/**
	 * Returns the Advisor CRD.
	 *
	 * @return the Advisor CRD
	 */
	public int getAdvisorCRD() {
		return advisorCRD;
	}

	public List<SupportStaff> getSupportStaff() {
            if (Objects.nonNull(supportStaff)) {
			return new ArrayList<>(supportStaff);
		}
		return Collections.emptyList();
	}

	/**
	 * Returns the Desktop Image Url.
	 *
	 * @return the Desktop Image Url
	 */
	public String getDesktopImageUrl() {
		return desktopImageUrl;
	}

	/**
	 * Returns the Mobile Image Url.
	 *
	 * @return the Mobile Image Url
	 */
	public String getMobileImageUrl() {
		return mobileImageUrl;
	}

	/**
	 * Returns the Circle Image Url.
	 *
	 * @return the Circle Image Url
	 */
	public String getCircleImageUrl() {
		return circleImageUrl;
	}

	/**
	 * Returns the Primary Office.
	 *
	 * @return the Primary Office
	 */
	public PrimaryOffice getPrimaryOffice() {
		return primaryOffice;
	}

	/**
	 * Returns the Offices Locations.
	 *
	 * @return the Offices Locations
	 */
	public List<OfficesLocations> getOfficesLocations() {
		if (Objects.nonNull(officesLocations)) {
			return new ArrayList<>(officesLocations);
		}
		return Collections.emptyList();
	}

	/**
	 * Sets the offices locations.
	 *
	 * @param officesLocations the officesLocations to set
	 */
	public void setOfficesLocations(List<OfficesLocations> officesLocations) {
		this.officesLocations = new ArrayList<>(officesLocations);
	}

	/**
	 * Returns the Education Indicator.
	 *
	 * @return the Education Indicator
	 */
	public String getEducationIndicator() {
		return educationIndicator;
	}

	/**
	 * Returns the Education.
	 *
	 * @return the Education
	 */
	public List<Education> getEducation() {
		if (Objects.nonNull(education)) {
			return new ArrayList<>(education);
		}
		return Collections.emptyList();
	}

	/**
	 * Returns whether this object is a tax planner.
	 *
	 * @return true if this object is a tax planner, false otherwise
	 */
	public boolean isTaxPlanner() {
		return taxPlanner;
	}

	/**
	 * Returns whether this object offers advanced planning services.
	 *
	 * @return true if this object offers advanced planning services, false
	 *         otherwise
	 */
	public boolean isAdvancedPlanning() {
		return advancedPlanning;
	}

	/**
	 * Returns whether this object offers estate planning services.
	 *
	 * @return true if this object offers estate planning services, false otherwise
	 */
	public boolean isEstatePlanning() {
		return estatePlanning;
	}

	/**
	 * Returns whether this object offers insurance planning services.
	 *
	 * @return true if this object offers insurance planning services, false
	 *         otherwise
	 */
	public boolean isInsurancePlanning() {
		return insurancePlanning;
	}

	/**
	 * Returns whether this object offers investment management services.
	 *
	 * @return true if this object offers investment management services, false
	 *         otherwise
	 */
	public boolean isInvestmentManagement() {
		return investmentManagement;
	}

	/**
	 * Returns the Employment History.
	 *
	 * @return the Employment History
	 */
	public List<EmploymentHistory> getEmploymentHistory() {
		if (Objects.nonNull(employmentHistory)) {
			return new ArrayList<>(employmentHistory);
		}
		return Collections.emptyList();
	}

	/**
	 * Returns the Certifications.
	 *
	 * @return the Certifications
	 */
	public List<Certifications> getCertifications() {
		if (Objects.nonNull(certifications)) {
			return new ArrayList<>(certifications);
		}
		return Collections.emptyList();
	}

	/**
	 * Returns the Bio.
	 *
	 * @return the Bio
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * Returns the Email.
	 *
	 * @return the Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the Team Distribution EmailAddress.
	 *
	 * @return the Team Distribution EmailAddress
	 */
	public String getTeamDistributionEmailAddress() {
		return teamDistributionEmailAddress;
	}

	/**
	 * Returns the Year Started Industry.
	 *
	 * @return the Year Started Industry
	 */
	public int getYearStartedIndustry() {
		return yearStartedIndustry;
	}

	/**
	 * Returns the Zip.
	 *
	 * @return the Zip
	 */
	public boolean isSmartVestorProIndicator() {
		return smartVestorProIndicator;
	}

	/**
	 * Returns the Zip.
	 *
	 * @return the Zip
	 */
	public List<String> getInterestsHobbies() {
		if (Objects.nonNull(interestsHobbies)) {
			return new ArrayList<>(interestsHobbies);
		}
		return Collections.emptyList();
	}

	/**
	 * Returns the Fun Facts.
	 *
	 * @return the Fun Facts
	 */
	public String getFunFacts() {
		return funFacts;
	}

	/**
	 * Returns the Honor Award.
	 *
	 * @return the Honor Award
	 */
	public List<HonorAward> getHonorAward() {
		if (Objects.nonNull(honorAward)) {
			return new ArrayList<>(honorAward);
		}
		return Collections.emptyList();
	}

	/**
	 * Returns the Favorite Sport.
	 *
	 * @return the Favorite Sport
	 */
	public String getBioVideo() {
		return bioVideo;
	}

        /**
	 * Returns the Planner Video.
	 *
	 * @return the Planner Video
	 */
	public String getFavoriteSport() {
		return favoriteSport;
	}
	/**
	 * Returns the Favorite Sports Team.
	 *
	 * @return the Favorite Sports Team
	 */
	public String getFavoriteSportsTeam() {
		return favoriteSportsTeam;
	}

	/**
	 * Returns the Favorite Life Hack.
	 *
	 * @return the Favorite Life Hack
	 */
	public String getFavoriteLifeHack() {
		return favoriteLifeHack;
	}

	/**
	 * Returns the Linked In Url.
	 *
	 * @return the Linked In Url
	 */
	public String getLinkedInUrl() {
		return linkedInUrl;
	}

	/**
	 * Returns the Most Inspirational Moment.
	 *
	 * @return the Most Inspirational Moment
	 */
	public String getMostInspirationalMoment() {
		return mostInspirationalMoment;
	}

	/**
	 * Returns the Industry Exams.
	 *
	 * @return the Industry Exams
	 */
	public List<IndustryExams> getIndustryExams() {
		if (Objects.nonNull(industryExams)) {
			return new ArrayList<>(industryExams);
		}
		return Collections.emptyList();
	}

	/**
	 * Returns whether this object has any disciplinary information on record.
	 *
	 * @return true if this object has disciplinary information on record, false
	 *         otherwise
	 */
	public boolean isHasDisciplinaryInformation() {
		return hasDisciplinaryInformation;
	}

	/**
	 * Returns the Disciplinary Information Text.
	 *
	 * @return the Disciplinary Information Text
	 */
	public String getDisciplinaryInformationText() {
		return disciplinaryInformationText;
	}

	/**
	 * Returns whether this object offers any commissions related to
	 * business-related activities.
	 *
	 * @return true if this object offers commissions related to business-related
	 *         activities, false otherwise
	 */
	public boolean isAnyBusinessRelatedActivitiesCommissions() {
		return anyBusinessRelatedActivitiesCommissions;
	}

	/**
	 * Returns the Business Related Activities Commissions Text.
	 *
	 * @return the Business Related Activities Commissions Text
	 */
	public String getBusinessRelatedActivitiesCommissionsText() {
		return businessRelatedActivitiesCommissionsText;
	}

	/**
	 * Returns whether this object offers any additional compensation for employees.
	 *
	 * @return true if this object offers additional compensation, false otherwise
	 */
	public boolean isAnyAdditionalCompensation() {
		return anyAdditionalCompensation;
	}

	/**
	 * Returns the Additional Compensation Text.
	 *
	 * @return the Additional Compensation Text
	 */
	public String getAdditionalCompensationText() {
		return additionalCompensationText;
	}

	/**
	 * Returns the EfeUrl.
	 *
	 * @return the EfeUrl
	 */
	public String getEfeUrl() {
		return efeUrl;
	}

	/**
	 * Returns the Adv2bUrl.
	 *
	 * @return the Adv2bUrl
	 */
	public String getAdv2bUrl() {
		return adv2bUrl;
	}

	/**
	 * Returns the HtmlUrl.
	 *
	 * @return the HtmlUrl
	 */
	public String getHtmlUrl() {
		return htmlUrl;
	}

	/**
	 * Sets the primary office.
	 *
	 * @param primaryOffice the primaryOffice to set
	 */
	public void setPrimaryOffice(PrimaryOffice primaryOffice) {
		this.primaryOffice = primaryOffice;
	}

	/**
	 * Gets the certifications abbrevations.
	 *
	 * @return the certificationsAbbrevations
	 */
	public List<String> getCertificationsAbbrevations() {
		if (Objects.nonNull(certificationsAbbrevations)) {
			return new ArrayList<>(certificationsAbbrevations);
		}
		return Collections.emptyList();
	}

	/**
	 * Sets the certifications abbrevations.
	 *
	 * @param certificationsAbbrevations the certificationsAbbrevations to set
	 */
	public void setCertificationsAbbrevations(List<String> certificationsAbbrevations) {
		this.certificationsAbbrevations = new ArrayList<>(certificationsAbbrevations);
	}

}
