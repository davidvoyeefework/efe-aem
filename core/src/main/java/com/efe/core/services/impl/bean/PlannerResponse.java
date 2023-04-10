package com.efe.core.services.impl.bean;

import java.util.List;

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
    
    /* otherBooks */
    private List<String> otherBooks;
    
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

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public boolean isIncludeInADV2B() {
		return includeInADV2B;
	}

	public void setIncludeInADV2B(boolean includeinadv2b) {
		this.includeInADV2B = includeinadv2b;
	}

	public boolean isIncludeInApiPayload() {
		return includeInApiPayload;
	}

	public void setIncludeInApiPayload(boolean includeInApiPayload) {
		this.includeInApiPayload = includeInApiPayload;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public String getFirstNameAlias() {
		return firstNameAlias;
	}

	public void setFirstNameAlias(String firstNameAlias) {
		this.firstNameAlias = firstNameAlias;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public int getYearJoined() {
		return yearJoined;
	}

	public void setYearJoined(int yearJoined) {
		this.yearJoined = yearJoined;
	}

	public String getDirectLinePhone() {
		return directLinePhone;
	}

	public void setDirectLinePhone(String directLinePhone) {
		this.directLinePhone = directLinePhone;
	}

	public int getAdvisorCRD() {
		return advisorCRD;
	}

	public void setAdvisorCRD(int advisorCRD) {
		this.advisorCRD = advisorCRD;
	}

	public String getDesktopImageUrl() {
		return desktopImageUrl;
	}

	public void setDesktopImageUrl(String desktopImageUrl) {
		this.desktopImageUrl = desktopImageUrl;
	}

	public String getMobileImageUrl() {
		return mobileImageUrl;
	}

	public void setMobileImageUrl(String mobileImageUrl) {
		this.mobileImageUrl = mobileImageUrl;
	}

	public String getCircleImageUrl() {
		return circleImageUrl;
	}

	public void setCircleImageUrl(String circleImageUrl) {
		this.circleImageUrl = circleImageUrl;
	}

	public PrimaryOffice getPrimaryOffice() {
		return primaryOffice;
	}

	public void setPrimaryOffice(PrimaryOffice primaryOffice) {
		this.primaryOffice = primaryOffice;
	}

	public List<OfficesLocations> getOfficesLocations() {
		return officesLocations;
	}

	public void setOfficesLocations(List<OfficesLocations> officesLocations) {
		this.officesLocations = officesLocations;
	}

	public String getEducationIndicator() {
		return educationIndicator;
	}

	public void setEducationIndicator(String educationIndicator) {
		this.educationIndicator = educationIndicator;
	}

	public List<Education> getEducation() {
		return education;
	}

	public void setEducation(List<Education> education) {
		this.education = education;
	}

	public boolean isTaxPlanner() {
		return taxPlanner;
	}

	public void setTaxPlanner(boolean taxPlanner) {
		this.taxPlanner = taxPlanner;
	}

	public boolean isAdvancedPlanning() {
		return advancedPlanning;
	}

	public void setAdvancedPlanning(boolean advancedPlanning) {
		this.advancedPlanning = advancedPlanning;
	}

	public boolean isEstatePlanning() {
		return estatePlanning;
	}

	public void setEstateplanning(boolean estatePlanning) {
		this.estatePlanning = estatePlanning;
	}

	public boolean isInsurancePlanning() {
		return insurancePlanning;
	}

	public void setInsurancePlanning(boolean insurancePlanning) {
		this.insurancePlanning = insurancePlanning;
	}

	public boolean isInvestmentManagement() {
		return investmentManagement;
	}

	public void setInvestmentManagement(boolean investmentManagement) {
		this.investmentManagement = investmentManagement;
	}

	public List<EmploymentHistory> getEmploymentHistory() {
		return employmentHistory;
	}

	public void setEmploymentHistory(List<EmploymentHistory> employmentHistory) {
		this.employmentHistory = employmentHistory;
	}

	public List<Certifications> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<Certifications> certifications) {
		this.certifications = certifications;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTeamDistributionEmailAddress() {
		return teamDistributionEmailAddress;
	}

	public void setTeamDistributionEmailaddress(String teamDistributionEmailAddress) {
		this.teamDistributionEmailAddress = teamDistributionEmailAddress;
	}

	public int getYearStartedIndustry() {
		return yearStartedIndustry;
	}

	public void setYearStartedIndustry(int yearStartedIndustry) {
		this.yearStartedIndustry = yearStartedIndustry;
	}

	public boolean isSmartVestorProIndicator() {
		return smartVestorProIndicator;
	}

	public void setSmartVestorProindicator(boolean smartVestorProIndicator) {
		this.smartVestorProIndicator = smartVestorProIndicator;
	}

	public List<String> getInterestsHobbies() {
		return interestsHobbies;
	}

	public void setInterestsHobbies(List<String> interestsHobbies) {
		this.interestsHobbies = interestsHobbies;
	}

	public String getFunFacts() {
		return funFacts;
	}

	public void setFunfacts(String funfacts) {
		this.funFacts = funFacts;
	}

	public List<HonorAward> getHonorAward() {
		return honorAward;
	}

	public void setHonorAward(List<HonorAward> honorAward) {
		this.honorAward = honorAward;
	}

	public String getFavoriteSport() {
		return favoriteSport;
	}

	public void setFavoriteSport(String favoriteSport) {
		this.favoriteSport = favoriteSport;
	}

	public String getFavoriteSportsTeam() {
		return favoriteSportsTeam;
	}

	public void setFavoriteSportSteam(String favoriteSportsTeam) {
		this.favoriteSportsTeam = favoriteSportsTeam;
	}

	public String getFavoriteLifeHack() {
		return favoriteLifeHack;
	}

	public void setFavoriteLifeHack(String FavoriteLifehack) {
		this.favoriteLifeHack = favoriteLifeHack;
	}

	public String getLinkedInUrl() {
		return linkedInUrl;
	}

	public void setLinkedInUrl(String linkedInUrl) {
		this.linkedInUrl = linkedInUrl;
	}

	public String getMostInspirationalMoment() {
		return mostInspirationalMoment;
	}

	public void setMostInspirationalMoment(String mostInspirationalMoment) {
		this.mostInspirationalMoment = mostInspirationalMoment;
	}

	public List<IndustryExams> getIndustryExams() {
		return industryExams;
	}

	public void setIndustryExams(List<IndustryExams> industryExams) {
		this.industryExams = industryExams;
	}

	public boolean isHasDisciplinaryInformation() {
		return hasDisciplinaryInformation;
	}

	public void setHasDisciplinaryInformation(boolean hasDisciplinaryInformation) {
		this.hasDisciplinaryInformation = hasDisciplinaryInformation;
	}

	public String getDisciplinaryInformationText() {
		return disciplinaryInformationText;
	}

	public void setDisciplinaryInformationText(String disciplinaryInformationText) {
		this.disciplinaryInformationText = disciplinaryInformationText;
	}

	public boolean isAnyBusinessRelatedActivitiesCommissions() {
		return anyBusinessRelatedActivitiesCommissions;
	}

	public void setAnyBusinessRelatedActivitiesCommissions(boolean anyBusinessRelatedActivitiesCommissions) {
		this.anyBusinessRelatedActivitiesCommissions = anyBusinessRelatedActivitiesCommissions;
	}

	public String getBusinessRelatedActivitiesCommissionsText() {
		return businessRelatedActivitiesCommissionsText;
	}

	public void setBusinessRelatedActivitiesCommissionstext(String businessRelatedActivitiesCommissionsText) {
		this.businessRelatedActivitiesCommissionsText = businessRelatedActivitiesCommissionsText;
	}

	public boolean isAnyAdditionalCompensation() {
		return anyAdditionalCompensation;
	}

	public void setAnyAdditionalCompensation(boolean anyAdditionalCompensation) {
		this.anyAdditionalCompensation = anyAdditionalCompensation;
	}

	public String getAdditionalCompensationText() {
		return additionalCompensationText;
	}

	public void setAdditionalCompensationText(String additionalCompensationText) {
		this.additionalCompensationText = additionalCompensationText;
	}

	public List<String> getOtherBooks() {
		return otherBooks;
	}

	public void setOtherBooks(List<String> otherBooks) {
		this.otherBooks = otherBooks;
	}

	public String getEfeUrl() {
		return efeUrl;
	}

	public void setEfeUrl(String efeUrl) {
		this.efeUrl = efeUrl;
	}

	public String getAdv2bUrl() {
		return adv2bUrl;
	}

	public void setAdv2bUrl(String adv2bUrl) {
		this.adv2bUrl = adv2bUrl;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}
    
    
}
