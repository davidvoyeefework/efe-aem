package com.efe.core.services.impl;

import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.bean.PlannerResponse;
import com.efe.core.bean.PrimaryOffice;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.services.PlannerApiService;
import com.efe.core.services.PlannerModelServices;
import com.efe.core.services.RestService;
import com.efe.core.utils.CertificationsUtil;
import com.efe.core.utils.EducationPlannerUtil;
import com.efe.core.utils.EmploymentHistoryUtil;
import com.efe.core.utils.FolderUtil;
import com.efe.core.utils.FragmentUtil;
import com.efe.core.utils.HonorAwardUtil;
import com.efe.core.utils.IndustryExamUtil;
import com.efe.core.utils.NodePropertyManagerUtil;
import com.efe.core.utils.OfficeLocationsUtil;
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
	 * This method is called by Scheduler to create fragment and adds data to that
	 * fragment
	 */
	public void addDataToCFModelPlanner(ResourceResolver resourceResolver) {
		createFragmentPlanner(resourceResolver);
		LOGGER.info("Content Fragment created and Updated for Planner");
	}

	/**
	 * This method is used to create Fragment for Planner
	 */
	private void createFragmentPlanner(ResourceResolver resourceResolver) {
		String jsonObjectPlanner = restService.getData(plannerApiService.getPlannersAPIEndpoint(),
				plannerApiService.getAuthHeader());
		Gson gson = new Gson();

		PlannerResponse[] jsonElement = gson.fromJson(jsonObjectPlanner, PlannerResponse[].class);

		String rootPath = FolderUtil.createFolder(PlannerLocationConstants.ROOT_FOLDER_PATH,
				PlannerLocationConstants.PLANNER, PlannerLocationConstants.PLANNER, resourceResolver);
		for (PlannerResponse jsonObj : jsonElement) {

			String firstName = jsonObj.getFirstName();
			int id = jsonObj.getId();

			String childPathPlanner = FolderUtil.createFolder(rootPath, Integer.toString(id),
					firstName + PlannerLocationConstants.UNDERSCORE + Integer.toString(id), resourceResolver);

			// create child folder first then create fragments as a dependency on
			// child folder
			EducationPlannerUtil.createEducationFragmentPlanner(childPathPlanner, jsonObj, resourceResolver);
			IndustryExamUtil.createIndustryExamFragmentPlanner(childPathPlanner, jsonObj, resourceResolver);
			HonorAwardUtil.createHonorAwardFragmentPlanner(childPathPlanner, jsonObj, resourceResolver);
			CertificationsUtil.createCertificationsFragmentPlanner(childPathPlanner, jsonObj, resourceResolver);
			EmploymentHistoryUtil.createEmploymentHistoryFragmentPlanner(childPathPlanner, jsonObj, resourceResolver);
			OfficeLocationsUtil.createOfficesLocationsFragmentPlanner(childPathPlanner, jsonObj, resourceResolver);

			// create content fragment for primary office
			createPrimaryOfficeFragmentPlanner(childPathPlanner, jsonObj, firstName, id, resourceResolver);

			// start creating the planner fragment
			String fragmentName = PlannerLocationConstants.FRAGMENT_NAME_PREFIX + firstName.trim()
					+ PlannerLocationConstants.UNDERSCORE + Integer.toString(id);

			Resource existingFragement = resourceResolver
					.getResource(childPathPlanner + PlannerLocationConstants.FORWARD_SLASH + fragmentName);
			if (Objects.isNull(existingFragement)) {
				FragmentUtil.createFragment(PlannerLocationConstants.PLANNER_MODEL, childPathPlanner, fragmentName,
						PlannerLocationConstants.JCR_TITLE_PLANNER, resourceResolver);
			}

			Resource plannerMasterResource = resourceResolver.getResource(childPathPlanner
					+ PlannerLocationConstants.FORWARD_SLASH + fragmentName + PlannerLocationConstants.MASTER_NODE);
			Node plannerMasterNode = plannerMasterResource.adaptTo(Node.class);

			updatePlannerFragmentProperties(plannerMasterNode, jsonObj, childPathPlanner, resourceResolver);
			updatePlannerFragmentReferences(plannerMasterNode, childPathPlanner, firstName, id, resourceResolver);

		}
	}

	/**
	 * This method is used to update Fragment for Planner
	 */
	private void updatePlannerFragmentProperties(Node plannerMasterNode, PlannerResponse jsonObj,
			String childPathPlanner, ResourceResolver resourceResolver) {

		try {

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.INCLUDE_IN_ADV2B,
					jsonObj.isIncludeInADV2B());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode,
					PlannerLocationConstants.INCLUDE_IN_API_PAYLOAD, jsonObj.isIncludeInApiPayload());

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.FIRST_NAME,
					jsonObj.getFirstName());

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
					jsonObj.getAdvisorCRD());

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
					PlannerLocationConstants.HAS_DISCIPLINARY_INFORMATION_TEXT, jsonObj.isHasDisciplinaryInformation());

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

		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
	}

	private void updatePlannerFragmentReferences(Node plannerMasterNode, String childPathPlanner, String firstName,
			int id, ResourceResolver resourceResolver) {
		// updating content fragment reference
		try {
			String primaryOfficeFragmentName = PlannerLocationConstants.FRAGMENT_NAME_PREFIX + firstName
					+ PlannerLocationConstants.UNDERSCORE + Integer.toString(id)
					+ PlannerLocationConstants.PRIMARY_OFFICE_POSTFIX;

			NodePropertyManagerUtil.setPropertyIfNonNull(plannerMasterNode, PlannerLocationConstants.PRIMARY_OFFICE,
					childPathPlanner + PlannerLocationConstants.FORWARD_SLASH + primaryOfficeFragmentName);

			plannerMasterNode.setProperty(PlannerLocationConstants.EDUCATION, ResourceUtil.getResourceChildNames(
					childPathPlanner + PlannerLocationConstants.FORWARD_SLASH + PlannerLocationConstants.EDUCATION,
					resourceResolver));

			plannerMasterNode.setProperty(PlannerLocationConstants.INDUSTRY_EXAMS, ResourceUtil.getResourceChildNames(
					childPathPlanner + PlannerLocationConstants.FORWARD_SLASH + PlannerLocationConstants.INDUSTRY_EXAMS,
					resourceResolver));

			plannerMasterNode.setProperty(PlannerLocationConstants.HONOR_AWARD, ResourceUtil.getResourceChildNames(
					childPathPlanner + PlannerLocationConstants.FORWARD_SLASH + PlannerLocationConstants.HONOR_AWARD,
					resourceResolver));

			plannerMasterNode.setProperty(PlannerLocationConstants.CERTIFICATIONS, ResourceUtil.getResourceChildNames(
					childPathPlanner + PlannerLocationConstants.FORWARD_SLASH + PlannerLocationConstants.CERTIFICATIONS,
					resourceResolver));

			plannerMasterNode.setProperty(PlannerLocationConstants.EMPLOYMENT_HISTORY,
					ResourceUtil.getResourceChildNames(childPathPlanner + PlannerLocationConstants.FORWARD_SLASH
							+ PlannerLocationConstants.EMPLOYMENT_HISTORY, resourceResolver));

			plannerMasterNode.setProperty(PlannerLocationConstants.OFFICE_LOCATIONS,
					ResourceUtil.getResourceChildNames(childPathPlanner + PlannerLocationConstants.FORWARD_SLASH
							+ PlannerLocationConstants.OFFICE_LOCATIONS, resourceResolver));
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
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

			if (Objects.nonNull(primaryOffice)) {

				String primaryOfficeFragmentName = PlannerLocationConstants.FRAGMENT_NAME_PREFIX + firstName
						+ PlannerLocationConstants.UNDERSCORE + Integer.toString(id)
						+ PlannerLocationConstants.PRIMARY_OFFICE_POSTFIX;

				Resource primaryOfficeExistingFragement = resourceResolver.getResource(
						childPathPlanner + PlannerLocationConstants.FORWARD_SLASH + primaryOfficeFragmentName);
				if (Objects.isNull(primaryOfficeExistingFragement)) {

					FragmentUtil.createFragment(PlannerLocationConstants.ADDRESS_MODEL, childPathPlanner,
							primaryOfficeFragmentName, PlannerLocationConstants.JCR_TITLE_PRIMARY_OFFICE,
							resourceResolver);

				}
				Resource plannerPrimaryOfficeResource = resourceResolver
						.getResource(childPathPlanner + PlannerLocationConstants.FORWARD_SLASH
								+ primaryOfficeFragmentName + PlannerLocationConstants.MASTER_NODE);
				Node plannerPrimaryOfficeNode = plannerPrimaryOfficeResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerPrimaryOfficeNode, PlannerLocationConstants.ID,
						primaryOffice.getId());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerPrimaryOfficeNode, PlannerLocationConstants.NAME,
						primaryOffice.getName());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerPrimaryOfficeNode, PlannerLocationConstants.CITY,
						primaryOffice.getCity());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerPrimaryOfficeNode, PlannerLocationConstants.STATE,
						primaryOffice.getState());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerPrimaryOfficeNode, PlannerLocationConstants.ZIP,
						primaryOffice.getZip());

			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
	}
}