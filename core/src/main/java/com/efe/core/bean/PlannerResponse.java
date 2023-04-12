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

	public String getLastUpdated() {
		return lastUpdated;
	}

	public boolean isIncludeInADV2B() {
		return includeInADV2B;
	}

	public boolean isIncludeInApiPayload() {
		return includeInApiPayload;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFirstNameAlias() {
		return firstNameAlias;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getId() {
		return id;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getTitle() {
		return title;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public int getYearJoined() {
		return yearJoined;
	}

	public String getDirectLinePhone() {
		return directLinePhone;
	}

	public int getAdvisorCRD() {
		return advisorCRD;
	}

	public String getDesktopImageUrl() {
		return desktopImageUrl;
	}

	public String getMobileImageUrl() {
		return mobileImageUrl;
	}

	public String getCircleImageUrl() {
		return circleImageUrl;
	}

	public PrimaryOffice getPrimaryOffice() {
		return primaryOffice;
	}

	public List<OfficesLocations> getOfficesLocations() {
        if (Objects.nonNull(officesLocations)) {
			return new ArrayList<>(officesLocations);
		}
		return Collections.emptyList();
	}

	public String getEducationIndicator() {
		return educationIndicator;
	}

	public List<Education> getEducation() {
        if (Objects.nonNull(education)) {
			return new ArrayList<>(education);
		}
		return Collections.emptyList();
	}

	public boolean isTaxPlanner() {
		return taxPlanner;
	}

	public boolean isAdvancedPlanning() {
		return advancedPlanning;
	}

	public boolean isEstatePlanning() {
		return estatePlanning;
	}

	public boolean isInsurancePlanning() {
		return insurancePlanning;
	}

	public boolean isInvestmentManagement() {
		return investmentManagement;
	}

	public List<EmploymentHistory> getEmploymentHistory() {
        if (Objects.nonNull(employmentHistory)) {
			return new ArrayList<>(employmentHistory);
		}
		return Collections.emptyList();
	}

	public List<Certifications> getCertifications() {
        if (Objects.nonNull(certifications)) {
			return new ArrayList<>(certifications);
		}
		return Collections.emptyList();
	}

	public String getBio() {
		return bio;
	}

	public String getEmail() {
		return email;
	}

	public String getTeamDistributionEmailAddress() {
		return teamDistributionEmailAddress;
	}

	public int getYearStartedIndustry() {
		return yearStartedIndustry;
	}

	public boolean isSmartVestorProIndicator() {
		return smartVestorProIndicator;
	}

	public List<String> getInterestsHobbies() {
        if (Objects.nonNull(interestsHobbies)) {
			return new ArrayList<>(interestsHobbies);
		}
		return Collections.emptyList();
	}

	public String getFunFacts() {
		return funFacts;
	}

	public List<HonorAward> getHonorAward() {
		 if (Objects.nonNull(honorAward)) {
			return new ArrayList<>(honorAward);
		}
		return Collections.emptyList();
	}

	public String getFavoriteSport() {
		return favoriteSport;
	}

	public String getFavoriteSportsTeam() {
		return favoriteSportsTeam;
	}

	public String getFavoriteLifeHack() {
		return favoriteLifeHack;
	}

	public String getLinkedInUrl() {
		return linkedInUrl;
	}

	public String getMostInspirationalMoment() {
		return mostInspirationalMoment;
	}

	public List<IndustryExams> getIndustryExams() {
        if (Objects.nonNull(industryExams)) {
			return new ArrayList<>(industryExams);
		}
		return Collections.emptyList();
	}

	public boolean isHasDisciplinaryInformation() {
		return hasDisciplinaryInformation;
	}

	public String getDisciplinaryInformationText() {
		return disciplinaryInformationText;
	}

	public boolean isAnyBusinessRelatedActivitiesCommissions() {
		return anyBusinessRelatedActivitiesCommissions;
	}

	public String getBusinessRelatedActivitiesCommissionsText() {
		return businessRelatedActivitiesCommissionsText;
	}

	public boolean isAnyAdditionalCompensation() {
		return anyAdditionalCompensation;
	}

	public String getAdditionalCompensationText() {
		return additionalCompensationText;
	}

	public String getEfeUrl() {
		return efeUrl;
	}

	public String getAdv2bUrl() {
		return adv2bUrl;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

}
