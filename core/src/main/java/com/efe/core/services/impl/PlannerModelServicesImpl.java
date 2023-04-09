package com.efe.core.services.impl;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.services.PlannerApiService;
import com.efe.core.services.PlannerModelServices;
import com.efe.core.services.RestService;
import com.efe.core.services.impl.bean.Certifications;
import com.efe.core.services.impl.bean.Education;
import com.efe.core.services.impl.bean.EmploymentHistory;
import com.efe.core.services.impl.bean.HonorAward;
import com.efe.core.services.impl.bean.IndustryExams;
import com.efe.core.services.impl.bean.OfficesLocations;
import com.efe.core.services.impl.bean.PlannerResponse;
import com.efe.core.services.impl.bean.PrimaryOffice;
import com.efe.core.utils.FolderUtil;
import com.efe.core.utils.NodePropertyManagerUtil;
import com.efe.core.utils.ResourceUtil;
import com.google.gson.Gson;

/**
 * The Class PlannerModelServicesImpl
 *
 */
@Component(service = PlannerModelServices.class, immediate = true)
public class PlannerModelServicesImpl implements PlannerModelServices {

	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerModelServicesImpl.class);

	/**
	 * ResourceResolverFactory injected
	 */
	@Reference
	private transient ResourceResolverFactory resourceResolverFactory;

	/**
	 * RestService injected
	 */
	@Reference
	private RestService restService;

	/**
	 * PlannerApiService injected
	 */
	@Reference
	private PlannerApiService plannerApiService;

	/**
	 * This method is used to create Fragment for Planner
	 */
	public void createFragmentPlanner(ResourceResolver resourceResolver) {
		String jsonObjectPlanner = restService.getData(plannerApiService.getPlannersAPIEndpoint(),
				plannerApiService.getAuthHeader());// Calling API to Get JSON Response
		Gson gson = new Gson();

		PlannerResponse[] jsonElement = gson.fromJson(jsonObjectPlanner, PlannerResponse[].class);

		String rootPath = FolderUtil.createFolder(PlannerLocationConstants.ROOT_FOLDER_PATH,
				PlannerLocationConstants.PLANNER, resourceResolver);
		for (PlannerResponse jsonObj : jsonElement) {

			String firstName = jsonObj.getfirstName();
			int id = jsonObj.getId();


			String childPathPlanner = FolderUtil.createFolder(rootPath, firstName + Integer.toString(id),
					resourceResolver);

			String fragmentName = PlannerLocationConstants.FRAGMENT_NAME_PREFIX + firstName.trim()
					+ PlannerLocationConstants.UNDERSCORE + Integer.toString(id);

			Resource existingFragement = resourceResolver
					.getResource(childPathPlanner + PlannerLocationConstants.FORWARD_SLASH + fragmentName);
			if (existingFragement == null) {
				Resource templateOrModelRsc = resourceResolver.getResource(PlannerLocationConstants.PLANNER_MODEL);
				Resource parentRsc = resourceResolver.getResource(childPathPlanner);
				FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
				try {
					tpl.createFragment(parentRsc, fragmentName, PlannerLocationConstants.JCR_TITLE_PLANNER);
					if (resourceResolver.hasChanges()) {
						resourceResolver.commit();
					}
				} catch (ContentFragmentException e) {
					LOGGER.error("ContentFragmentException:", e);
				} catch (PersistenceException e) {
					LOGGER.error("PersistenceException:", e);
				}
			}
			Resource plannerMasterResource = resourceResolver
					.getResource(childPathPlanner + PlannerLocationConstants.FORWARD_SLASH + fragmentName
							+ PlannerLocationConstants.MASTER_NODE);
			Node plannerMasterNode = plannerMasterResource.adaptTo(Node.class);

			updatePlannerFragmentProperties(resourceResolver, plannerMasterNode, jsonObj, childPathPlanner, firstName,
					id);

		}
	}

	private void updatePlannerFragmentProperties(ResourceResolver resourceResolver, Node plannerMasterNode,
			PlannerResponse jsonObj, String childPathPlanner, String firstName, int id) {

		try {

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.INCLUDE_IN_ADV2B,
					jsonObj.isIncludeInADV2B());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.INCLUDE_IN_API_PAYLOAD, jsonObj.isIncludeInApiPayload());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.FIRST_NAME,
					jsonObj.getfirstName());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.FIRST_NAME_ALIAS,
					jsonObj.getFirstNameAlias());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.MIDDLE_NAME,
					jsonObj.getMiddleName());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.LAST_NAME,
					jsonObj.getLastName());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.ID,
					jsonObj.getId());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.SUFFIX,
					jsonObj.getSuffix());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.TITLE,
					jsonObj.getTitle());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.BIRTH_YEAR,
					jsonObj.getBirthYear());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.YEAR_JOINED,
					jsonObj.getYearJoined());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.DIRECT_LINE_PHONE,
					jsonObj.getDirectLinePhone());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.ADVISOR_CRD,
					jsonObj.getAdvisorCrd());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.DESKTOP_IMAGE_URL,
					jsonObj.getDesktopImageUrl());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.MOBILE_IMAGE_URL,
					jsonObj.getMobileImageUrl());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.CIRCLE_IMAGE,
					jsonObj.getCircleImageUrl());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.EDUCATION_INDICATOR, jsonObj.getEducationIndicator());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.TAX_PLANNER,
					jsonObj.isTaxPlanner());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.ADVANCED_PLANNING,
					jsonObj.isAdvancedPlanning());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.ESTATE_PLANNING,
					jsonObj.isEstatePlanning());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.INSURANCE_PLANNING,
					jsonObj.isInsurancePlanning());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.INVESTMENT_MANAGEMENT, jsonObj.isInvestmentManagement());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.BIO,
					jsonObj.getBio());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.EMAIL,
					jsonObj.getEmail());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.TEAM_DISTRIBUTION_ADDRESS, jsonObj.getTeamDistributionEmailAddress());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.YEAR_STARTED_INDUSTRY, jsonObj.getYearStartedIndustry());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.SMART_VESTOR_PRO_INDICATOR, jsonObj.isSmartVestorProIndicator());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.FUN_FACTS,
					jsonObj.getFunFacts());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.FAVOURITE_SPORT,
					jsonObj.getFavoriteSport());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.FAVOURITE_SPORTS_TEAM, jsonObj.getFavoriteSportsTeam());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.FAVOURITE_LIFE_HACK, jsonObj.getFavoriteLifeHack());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.LINKED_IN_URL,
					jsonObj.getLinkedInUrl());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.MOST_INSPIRATION_MOMENT, jsonObj.getMostInspirationalMoment());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.DISCIPLINARY_INFORMATION_TEXT, jsonObj.getDisciplinaryInformationText());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.ANY_BUSINESS_RELATED_ACTIVITIES_COMMISSIONS,
					jsonObj.isAnyBusinessRelatedActivitiesCommissions());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.BUSINESS_RELATED_ACTIVITIES_COMMISSIONS_TEXT,
					jsonObj.getBusinessRelatedActivitiesCommissionsText());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.ANY_ADDITIONAL_COMPENSATION, jsonObj.isAnyAdditionalCompensation());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.ADDITIONAL_COMPENSATION_TEXT, jsonObj.getAdditionalCompensationText());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.EFE_URL,
					jsonObj.getEfeUrl());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.ADV2B_URL,
					jsonObj.getAdv2bUrl());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.HTML_URL,
					jsonObj.getHtmlUrl());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.LAST_UPDATED,
					jsonObj.getLastUpdated());

			String[] interestsHobbiesArr = jsonObj.getInterestsHobbies().toArray(new String[0]);
			plannerMasterNode.setProperty(PlannerLocationConstants.INTERESTS_HOBBIES, interestsHobbiesArr);

			createChildFragment(childPathPlanner, jsonObj, firstName, id, resourceResolver);

		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
	}

	/**
	 * 
	 * This method is used to create child fragments
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param firstName
	 * @param id
	 * @param resourceResolver
	 */
	private void createChildFragment(String childPathPlanner, PlannerResponse jsonObj, String firstName, int id,
			ResourceResolver resourceResolver) {
		createPrimaryOfficeFragmentPlanner(childPathPlanner, jsonObj, firstName, id, resourceResolver);
		createEducationFragmentPlanner(childPathPlanner, jsonObj, resourceResolver);
		createIndustryExamFragmentPlanner(childPathPlanner, jsonObj, resourceResolver);
		createHonorAwardFragmentPlanner(childPathPlanner, jsonObj, resourceResolver);
		createCertificationsFragmentPlanner(childPathPlanner, jsonObj, resourceResolver);
		createEmploymentHistoryFragmentPlanner(childPathPlanner, jsonObj, resourceResolver);
		createOfficesLocationsFragmentPlanner(childPathPlanner, jsonObj, resourceResolver);
	}

	/**
	 * This method is used to create and update Primary Office Fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param firstName
	 * @param id
	 * @param resourceResolver
	 */
	private void createPrimaryOfficeFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj, String firstName,
			int id, ResourceResolver resourceResolver) {

		try {

			PrimaryOffice primaryOffice = jsonObj.getPrimaryOffice();

			if (primaryOffice != null) {

				String primaryOfficeFragmentName = PlannerLocationConstants.FRAGMENT_NAME_PREFIX + firstName
						+ PlannerLocationConstants.UNDERSCORE + Integer.toString(id)
						+ PlannerLocationConstants.PRIMARY_OFFICE_POSTFIX;

				Resource primaryOfficeExistingFragement = resourceResolver.getResource(
						childPathPlanner + PlannerLocationConstants.FORWARD_SLASH + primaryOfficeFragmentName);
				if (primaryOfficeExistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver.getResource(PlannerLocationConstants.ADDRESS_MODEL);
					Resource parentRsc = resourceResolver.getResource(childPathPlanner);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, primaryOfficeFragmentName,
								PlannerLocationConstants.JCR_TITLE_PRIMARY_OFFICE);
						if (resourceResolver.hasChanges()) {
							resourceResolver.commit();
						}
					} catch (ContentFragmentException e) {
						LOGGER.error("ContentFragmentException:", e);
					} catch (PersistenceException e) {
						LOGGER.error("PersistenceException:", e);
					}
				}
				Resource plannerPrimaryOfficeResource = resourceResolver
						.getResource(childPathPlanner + PlannerLocationConstants.FORWARD_SLASH
								+ primaryOfficeFragmentName + PlannerLocationConstants.MASTER_NODE);
				Node plannerPrimaryOfficeNode = plannerPrimaryOfficeResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerPrimaryOfficeNode, PlannerLocationConstants.ID,
						primaryOffice.getId());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerPrimaryOfficeNode, PlannerLocationConstants.NAME,
						primaryOffice.getName());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerPrimaryOfficeNode, PlannerLocationConstants.STATE,
						primaryOffice.getState());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerPrimaryOfficeNode, PlannerLocationConstants.ZIP,
						primaryOffice.getZip());

			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}

	}

	/**
	 * This method is used to create and update fragments for office locations
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	private void createOfficesLocationsFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {

		try {
			List<OfficesLocations> officesLocations = jsonObj.getOfficesLocations();

			String officesLocationsRootPath = FolderUtil.createFolder(childPathPlanner,
					PlannerLocationConstants.OFFICE_LOCATIONS, resourceResolver);
			int officesLocationsCount = 1;
			for (OfficesLocations officesLocationsObj : officesLocations) {

				String officesLocationsFragmentName = PlannerLocationConstants.OFFICE_LOCATIONS_PREFIX
						+ Integer.toString(officesLocationsCount);
				Resource officesLocationsExistingFragement = resourceResolver.getResource(officesLocationsRootPath
						+ PlannerLocationConstants.FORWARD_SLASH + officesLocationsFragmentName);
				if (officesLocationsExistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver.getResource(PlannerLocationConstants.ADDRESS_MODEL);
					Resource parentRsc = resourceResolver.getResource(officesLocationsRootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, officesLocationsFragmentName,
								PlannerLocationConstants.JCR_TITLE_OFFICE_LOCATIONS);
						if (resourceResolver.hasChanges()) {
							resourceResolver.commit();
						}
					} catch (ContentFragmentException e) {
						LOGGER.error("ContentFragmentException:", e);
					} catch (PersistenceException e) {
						LOGGER.error("PersistenceException:", e);
					}
				}
				Resource plannerOfficesLocationsResource = resourceResolver
						.getResource(officesLocationsRootPath + PlannerLocationConstants.FORWARD_SLASH
								+ officesLocationsFragmentName + PlannerLocationConstants.MASTER_NODE);
				Node plannerOfficesLocationsNode = plannerOfficesLocationsResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerOfficesLocationsNode, PlannerLocationConstants.ID,
						officesLocationsObj.getId());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerOfficesLocationsNode, PlannerLocationConstants.CITY,
						officesLocationsObj.getCity());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerOfficesLocationsNode, PlannerLocationConstants.NAME,
						officesLocationsObj.getName());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerOfficesLocationsNode,
						PlannerLocationConstants.STATE, officesLocationsObj.getState());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerOfficesLocationsNode, PlannerLocationConstants.ZIP,
						officesLocationsObj.getZip());

				officesLocationsCount++;
			}
		} catch (RepositoryException e) {
			LOGGER.error("Exception occured.", e);
		}
	}

	/**
	 * This method is used to create and update Employment History Fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	private void createEmploymentHistoryFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {

		try {
			List<EmploymentHistory> employmentHistory = jsonObj.getEmploymentHistory();

			String employmentHistoryRootPath = FolderUtil.createFolder(childPathPlanner,
					PlannerLocationConstants.EMPLOYMENT_HISTORY, resourceResolver);
			int employmentHistoryCount = 1;
			for (EmploymentHistory employmentHistoryObj : employmentHistory) {

				String employmentHistoryFragmentName = PlannerLocationConstants.EMPLOYMENT_HISTORY_PREFIX
						+ Integer.toString(employmentHistoryCount);
				Resource employmentHistoryExistingFragement = resourceResolver.getResource(employmentHistoryRootPath
						+ PlannerLocationConstants.FORWARD_SLASH + employmentHistoryFragmentName);
				if (employmentHistoryExistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver
							.getResource(PlannerLocationConstants.EMPLOYMENT_HISTORY_MODEL);
					Resource parentRsc = resourceResolver.getResource(employmentHistoryRootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, employmentHistoryFragmentName,
								PlannerLocationConstants.JCR_TITLE_EMPLOYMENT_HISTORY);
						if (resourceResolver.hasChanges()) {
							resourceResolver.commit();
						}
					} catch (ContentFragmentException e) {
						LOGGER.error("ContentFragmentException:", e);
					} catch (PersistenceException e) {
						LOGGER.error("PersistenceException:", e);
					}
				}
				Resource plannerEmploymentHistoryResource = resourceResolver
						.getResource(employmentHistoryRootPath + PlannerLocationConstants.FORWARD_SLASH
								+ employmentHistoryFragmentName + PlannerLocationConstants.MASTER_NODE);
				Node plannerEmploymentHistoryExamNode = plannerEmploymentHistoryResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEmploymentHistoryExamNode,
						PlannerLocationConstants.START_DATE, employmentHistoryObj.getStartDate());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEmploymentHistoryExamNode,
						PlannerLocationConstants.END_DATE, employmentHistoryObj.getEndDate());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEmploymentHistoryExamNode,
						PlannerLocationConstants.JOB_TITLE, employmentHistoryObj.getJobTitle());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEmploymentHistoryExamNode,
						PlannerLocationConstants.COMPANY_NAME, employmentHistoryObj.getCompanyName());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEmploymentHistoryExamNode,
						PlannerLocationConstants.CURRENT, employmentHistoryObj.isCurrent());

				employmentHistoryCount++;
			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}

	}

	/**
	 * This method is used to create and update certification fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	private void createCertificationsFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {

		try {
			List<Certifications> certifications = jsonObj.getCertifications();

			String certificationsRootPath = FolderUtil.createFolder(childPathPlanner,
					PlannerLocationConstants.CERTIFICATIONS, resourceResolver);
			int certificationsCount = 1;
			for (Certifications certificationsObj : certifications) {

				String certificationFragmentName = PlannerLocationConstants.CERTIFICATION_PREFIX
						+ Integer.toString(certificationsCount);
				Resource certificationExistingFragement = resourceResolver.getResource(
						certificationsRootPath + PlannerLocationConstants.FORWARD_SLASH + certificationFragmentName);
				if (certificationExistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver
							.getResource(PlannerLocationConstants.CERTIFICATION_MODEL);
					Resource parentRsc = resourceResolver.getResource(certificationsRootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, certificationFragmentName,
								PlannerLocationConstants.JCR_TITLE_CERTIFICATION);
						if (resourceResolver.hasChanges()) {
							resourceResolver.commit();
						}
					} catch (ContentFragmentException e) {
						LOGGER.error("ContentFragmentException:", e);
					} catch (PersistenceException e) {
						LOGGER.error("PersistenceException:", e);
					}
				}
				Resource plannerCertificationResource = resourceResolver
						.getResource(certificationsRootPath + PlannerLocationConstants.FORWARD_SLASH
								+ certificationFragmentName + PlannerLocationConstants.MASTER_NODE);
				Node plannerCertificationExamNode = plannerCertificationResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerCertificationExamNode,
						PlannerLocationConstants.ABBREVIATION, certificationsObj.getAbbreviation());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerCertificationExamNode,
						PlannerLocationConstants.LEGAL_COMPLIANCE_DISCLOSURE,
						certificationsObj.getLegalComplianceDisclosure());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerCertificationExamNode,
						PlannerLocationConstants.MARKETING_DISCLOSURE, certificationsObj.getMarketingDisclosure());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerCertificationExamNode,
						PlannerLocationConstants.NAME, certificationsObj.getName());
				certificationsCount++;
			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
	}

	/**
	 * This method is used to create and update honor award fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	private void createHonorAwardFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {

		try {
			List<HonorAward> honorAward = jsonObj.getHonorAward();

			String honorAwardrootPath = FolderUtil.createFolder(childPathPlanner, PlannerLocationConstants.HONOR_AWARD,
					resourceResolver);
			int honorAwardCount = 1;
			for (HonorAward honorAwardObj : honorAward) {

				String honorAwardFragmentName = PlannerLocationConstants.HONOR_AWARD
						+ Integer.toString(honorAwardCount);
				Resource honorAwardexistingFragement = resourceResolver.getResource(
						honorAwardrootPath + PlannerLocationConstants.FORWARD_SLASH + honorAwardFragmentName);
				if (honorAwardexistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver
							.getResource(PlannerLocationConstants.HONOR_AWARD_MODEL);
					Resource parentRsc = resourceResolver.getResource(honorAwardrootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, honorAwardFragmentName,
								PlannerLocationConstants.JCR_TITLE_HONOR_AWARD);
						if (resourceResolver.hasChanges()) {
							resourceResolver.commit();
						}
					} catch (ContentFragmentException e) {
						LOGGER.error("ContentFragmentException:", e);
					} catch (PersistenceException e) {
						LOGGER.error("PersistenceException:", e);
					}
				}
				Resource plannerHonorAwardResource = resourceResolver
						.getResource(honorAwardrootPath + PlannerLocationConstants.FORWARD_SLASH
								+ honorAwardFragmentName + PlannerLocationConstants.MASTER_NODE);
				Node honorAwardNode = plannerHonorAwardResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(honorAwardNode, PlannerLocationConstants.DATE_OF_AWARD,
						honorAwardObj.getDateOfAward());

				NodePropertyManagerUtil.setPropertyIfNonNull(honorAwardNode, PlannerLocationConstants.DISCLOSURE,
						honorAwardObj.getDisclosure());

				NodePropertyManagerUtil.setPropertyIfNonNull(honorAwardNode, PlannerLocationConstants.HONOR_AWARD_NAME,
						honorAwardObj.getHonorAwardName());

				NodePropertyManagerUtil.setPropertyIfNonNull(honorAwardNode, PlannerLocationConstants.ORGANISATION,
						honorAwardObj.getOrganization());
				honorAwardCount++;
			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
	}

	/**
	 * This method is used to create and update education fragments
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	private void createEducationFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {

		try {
			List<Education> education = jsonObj.getEducation();

			String educationRootPath = FolderUtil.createFolder(childPathPlanner, PlannerLocationConstants.EDUCATION,
					resourceResolver);
			int educationCount = 1;
			for (Education eduObj : education) {

				String educationFragmentName = PlannerLocationConstants.EDUCATION_FRAGMENT_PREFIX
						+ Integer.toString(educationCount);
				Resource educationexistingFragement = resourceResolver.getResource(
						educationRootPath + PlannerLocationConstants.FORWARD_SLASH + educationFragmentName);
				if (educationexistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver
							.getResource(PlannerLocationConstants.EDUCATION_MODEL);
					Resource parentRsc = resourceResolver.getResource(educationRootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, educationFragmentName,
								PlannerLocationConstants.JCR_TITLE_EDUCATION);
						if (resourceResolver.hasChanges()) {
							resourceResolver.commit();
						}
					} catch (ContentFragmentException e) {
						LOGGER.error("ContentFragmentException:", e);
					} catch (PersistenceException e) {
						LOGGER.error("PersistenceException:", e);
					}
				}
				Resource plannerEducationResource = resourceResolver
						.getResource(educationRootPath + PlannerLocationConstants.FORWARD_SLASH + educationFragmentName
								+ PlannerLocationConstants.MASTER_NODE);
				Node plannerEducationNode = plannerEducationResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEducationNode, PlannerLocationConstants.DEGREE,
						eduObj.getDegree());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEducationNode, PlannerLocationConstants.MAJOR,
						eduObj.getMajor());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEducationNode, PlannerLocationConstants.UNIVERSITY,
						eduObj.getUniversity());
				educationCount++;
			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
	}

	/**
	 * This method is used to create and update Industry Exam Fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	private void createIndustryExamFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {

		try {
			List<IndustryExams> industryExams = jsonObj.getIndustryExams();
			String industryExamRootPath = FolderUtil.createFolder(childPathPlanner,
					PlannerLocationConstants.INDUSTRY_EXAMS, resourceResolver);
			int industryExamCount = 1;
			for (IndustryExams industryExamObj : industryExams) {
				String industryExamFragmentName = PlannerLocationConstants.INDUSTRY_EXAMS_PREFIX
						+ Integer.toString(industryExamCount);
				Resource industryExamexistingFragement = resourceResolver.getResource(
						industryExamRootPath + PlannerLocationConstants.FORWARD_SLASH + industryExamFragmentName);
				if (industryExamexistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver
							.getResource(PlannerLocationConstants.INDUSTRY_EXAM_MODEL);
					Resource parentRsc = resourceResolver.getResource(industryExamRootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, industryExamFragmentName,
								PlannerLocationConstants.JCR_TITLE_INDUSTRY_EXAM);
						if (resourceResolver.hasChanges()) {
							resourceResolver.commit();
						}
					} catch (ContentFragmentException e) {
						LOGGER.error("ContentFragmentException:", e);
					} catch (PersistenceException e) {
						LOGGER.error("PersistenceException:", e);
					}
				}
				Resource plannerIndustryExamResource = resourceResolver
						.getResource(industryExamRootPath + PlannerLocationConstants.FORWARD_SLASH
								+ industryExamFragmentName + PlannerLocationConstants.MASTER_NODE);
				Node plannerIndustryExamNode = plannerIndustryExamResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerIndustryExamNode,
						PlannerLocationConstants.EXAM_NAME_LONG, industryExamObj.getExamNameLong());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerIndustryExamNode,
						PlannerLocationConstants.EXAM_NAME_SHORT, industryExamObj.getExamNameShort());
				industryExamCount++;
			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
	}

	/**
	 * This method is called by Scheduler to create fragment and adds data to that
	 * fragment
	 */
	public void addDataToCFModelPlanner() {
		ResourceResolver resourceResolver = ResourceUtil.getServiceResourceResolver(resourceResolverFactory);
		createFragmentPlanner(resourceResolver);
		LOGGER.info("Content Fragment created and Updated for Planner");
	}
}
