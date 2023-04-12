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

	/* getter FirstName */
	public String getFirstName() {
		return firstName;
	}

	/* getter FirstNameAlias */
	public String getFirstNameAlias() {
		return firstNameAlias;
	}

	/* getter MiddleName */
	public String getMiddleName() {
		return middleName;
	}

	/* getter LastName */
	public String getLastName() {
		return lastName;
	}

	/* getter LastName */
	public int getId() {
		return id;
	}
	
	/* getter Suffix */
	public String getSuffix() {
		return suffix;
	}

	/* getter Title */
	public String getTitle() {
		return title;
	}

	/* getter BirthYear */
	public int getBirthYear() {
		return birthYear;
	}

	/* getter YearJoined */
	public int getYearJoined() {
		return yearJoined;
	}

	/* getter DirectLinePhone */
	public String getDirectLinePhone() {
		return directLinePhone;
	}

	/* getter AdvisorCRD */
	public int getAdvisorCRD() {
		return advisorCRD;
	}

	/* getter DesktopImageUrl */
	public String getDesktopImageUrl() {
		return desktopImageUrl;
	}

	/* getter MobileImageUrl */
	public String getMobileImageUrl() {
		return mobileImageUrl;
	}

	/* getter CircleImageUrl */
	public String getCircleImageUrl() {
		return circleImageUrl;
	}

	/* getter PrimaryOffice */
	public PrimaryOffice getPrimaryOffice() {
		return primaryOffice;
	}

	/* getter OfficesLocations */
	public List<OfficesLocations> getOfficesLocations() {
        if (Objects.nonNull(officesLocations)) {
			return new ArrayList<>(officesLocations);
		}
		return Collections.emptyList();
	}

	/* getter EducationIndicator */
	public String getEducationIndicator() {
		return educationIndicator;
	}

	/* getter Education */
	public List<Education> getEducation() {
        if (Objects.nonNull(education)) {
			return new ArrayList<>(education);
		}
		return Collections.emptyList();
	}

	/* getter TaxPlanner */
	public boolean isTaxPlanner() {
		return taxPlanner;
	}

	/* getter AdvancedPlanning */
	public boolean isAdvancedPlanning() {
		return advancedPlanning;
	}

	/* getter EstatePlanning */
	public boolean isEstatePlanning() {
		return estatePlanning;
	}

	/* getter InsurancePlanning */
	public boolean isInsurancePlanning() {
		return insurancePlanning;
	}

	/* getter InvestmentManagement */
	public boolean isInvestmentManagement() {
		return investmentManagement;
	}

	/* getter EmploymentHistory */
	public List<EmploymentHistory> getEmploymentHistory() {
        if (Objects.nonNull(employmentHistory)) {
			return new ArrayList<>(employmentHistory);
		}
		return Collections.emptyList();
	}

	/* getter Certifications */
	public List<Certifications> getCertifications() {
        if (Objects.nonNull(certifications)) {
			return new ArrayList<>(certifications);
		}
		return Collections.emptyList();
	}

	/* getter Bio */
	public String getBio() {
		return bio;
	}

	/* getter Email */
	public String getEmail() {
		return email;
	}

	/* getter TeamDistributionEmailAddress */
	public String getTeamDistributionEmailAddress() {
		return teamDistributionEmailAddress;
	}

	/* getter YearStartedIndustry */
	public int getYearStartedIndustry() {
		return yearStartedIndustry;
	}

	/* getter SmartVestorProIndicator */
	public boolean isSmartVestorProIndicator() {
		return smartVestorProIndicator;
	}

	/* getter InterestsHobbies */
	public List<String> getInterestsHobbies() {
        if (Objects.nonNull(interestsHobbies)) {
			return new ArrayList<>(interestsHobbies);
		}
		return Collections.emptyList();
	}

	/* getter FunFacts */
	public String getFunFacts() {
		return funFacts;
	}

	/* getter HonorAward */
	public List<HonorAward> getHonorAward() {
		 if (Objects.nonNull(honorAward)) {
			return new ArrayList<>(honorAward);
		}
		return Collections.emptyList();
	}

	/* getter FavoriteSport */
	public String getFavoriteSport() {
		return favoriteSport;
	}

	/* getter FavoriteSportsTeam */
	public String getFavoriteSportsTeam() {
		return favoriteSportsTeam;
	}

	/* getter FavoriteLifeHack */
	public String getFavoriteLifeHack() {
		return favoriteLifeHack;
	}

	/* getter LinkedInUrl */
	public String getLinkedInUrl() {
		return linkedInUrl;
	}

	/* getter MostInspirationalMoment */
	public String getMostInspirationalMoment() {
		return mostInspirationalMoment;
	}

	/* getter IndustryExams */
	public List<IndustryExams> getIndustryExams() {
        if (Objects.nonNull(industryExams)) {
			return new ArrayList<>(industryExams);
		}
		return Collections.emptyList();
	}

	/* getter HasDisciplinaryInformation */
	public boolean isHasDisciplinaryInformation() {
		return hasDisciplinaryInformation;
	}

	/* getter DisciplinaryInformationText */
	public String getDisciplinaryInformationText() {
		return disciplinaryInformationText;
	}

	/* getter AnyBusinessRelatedActivitiesCommissions */
	public boolean isAnyBusinessRelatedActivitiesCommissions() {
		return anyBusinessRelatedActivitiesCommissions;
	}

	/* getter BusinessRelatedActivitiesCommissionsText */
	public String getBusinessRelatedActivitiesCommissionsText() {
		return businessRelatedActivitiesCommissionsText;
	}

	/* getter AnyAdditionalCompensation */
	public boolean isAnyAdditionalCompensation() {
		return anyAdditionalCompensation;
	}

	/* getter AdditionalCompensationText */
	public String getAdditionalCompensationText() {
		return additionalCompensationText;
	}

	/* getter EfeUrl */
	public String getEfeUrl() {
		return efeUrl;
	}

	/* getter Adv2bUrl */
	public String getAdv2bUrl() {
		return adv2bUrl;
	}

	/* getter HtmlUrl */
	public String getHtmlUrl() {
		return htmlUrl;
	}

}
