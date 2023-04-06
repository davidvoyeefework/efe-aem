package com.efe.core.services.impl;

import javax.jcr.Node;
import javax.jcr.Session;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.FragmentTemplate;
import com.efe.core.services.PlannerApiService;
import com.efe.core.services.PlannerModelServices;
import com.efe.core.services.RestService;
import com.efe.core.utils.ResourceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.adobe.cq.dam.cfm.ContentFragmentException;

/**
 * The Class PlannerModelServicesImpl
 *
 */
@Component(service = PlannerModelServices.class, immediate = true)
public class PlannerModelServicesImpl implements PlannerModelServices {

	/**
	 * Root Folder Path
	 */
	private static final String ROOT_FOLDER_PATH = "/content/dam/efe/plannerlocation";

	/**
	 * Planner Folder Name
	 */
	private static final String PLANNER = "planners";

	/**
	 * Planner Model
	 */
	private static final String PLANNER_MODEL = "/conf/efe/settings/dam/cfm/models/planner";

	/**
	 * Planner Model
	 */
	private static final String FIRST_NAME = "firstName";

	/**
	 * Forward Slash
	 */
	private static final String FORWARD_SLASH = "/";

	/**
	 * Sling Ordered Folder
	 */
	private static final String SLING_ORDERED_FOLDER = "sling:OrderedFolder";

	/**
	 * Forward Slash
	 */
	private static final String MASTER_NODE = "/jcr:content/data/master";

	/**
	 * Underscore
	 */
	private static final String UNDERSCORE = "_";

	/**
	 * Fragment Name Prefix
	 */
	private static final String FRAGMENT_NAME_PREFIX = "fragment_";

	/**
	 * ID
	 */
	private static final String ID = "id";

	/**
	 * Jcr Title
	 */
	private static final String JCR_TITLE_PLANNER = "Planner Fragment";

	/**
	 * Include In Adv2b
	 */
	private static final String INCLUDE_IN_ADV2B = "includeInADV2B";

	/**
	 * Include In API PAYLOAD
	 */
	private static final String INCLUDE_IN_API_PAYLOAD = "includeInApiPayload";

	/**
	 * First Name Alias
	 */
	private static final String FIRST_NAME_ALIAS = "firstNameAlias";

	/**
	 * Middle Name
	 */
	private static final String MIDDLE_NAME = "middleName";

	/**
	 * Last Name
	 */
	private static final String LAST_NAME = "lastName";

	/**
	 * Suffix
	 */
	private static final String SUFFIX = "suffix";

	/**
	 * Title
	 */
	private static final String TITLE = "title";

	/**
	 * Birth Year
	 */
	private static final String BIRTH_YEAR = "birthYear";

	/**
	 * Year Joined
	 */
	private static final String YEAR_JOINED = "yearJoined";

	/**
	 * Direct Line Phone
	 */
	private static final String DIRECT_LINE_PHONE = "directLinePhone";

	/**
	 * Advisor Crd From Json
	 */
	private static final String ADVISOR_CRD_FROM_JSON = "advisorCRD";

	/**
	 * Advisor Crd To Fragment
	 */
	private static final String ADVISOR_CRD_TO_FRAGMENT = "advisorCrd";

	/**
	 * Desktop Image Url
	 */
	private static final String DESKTOP_IMAGE_URL_FROM_JSON = "desktopImageUrl";

	/**
	 * Desktop Image Url
	 */
	private static final String DESKTOP_IMAGE_URL_TO_FRAGMENT = "desktopImageurl";

	/**
	 * Mobile Image Url
	 */
	private static final String MOBILE_IMAGE_URL_FROM_URL = "mobileImageUrl";

	/**
	 * Mobile Image Url
	 */
	private static final String MOBILE_IMAGE_URL_TO_FRAGMENT = "mobileImageurl";

	/**
	 * Circle Image Url
	 */
	private static final String CIRCLE_IMAGE_URL_FROM_JSON = "circleImageUrl";

	/**
	 * Circle Image Url
	 */
	private static final String CIRCLE_IMAGE_URL_TO_FRAGMENT = "circleImageurl";

	/**
	 * Education Indicator
	 */
	private static final String EDUCATION_INDICATOR = "educationIndicator";

	/**
	 * Tax Planner
	 */
	private static final String TAX_PLANNER = "taxPlanner";

	/**
	 * Advanced Planning
	 */
	private static final String ADVANCED_PLANNING = "advancedPlanning";

	/**
	 * Estate Planning
	 */
	private static final String ESTATE_PLANNING = "estatePlanning";

	/**
	 * Insurance Planning
	 */
	private static final String INSURANCE_PLANNING = "insurancePlanning";

	/**
	 * Investment Management
	 */
	private static final String INVESTMENT_MANAGEMENT = "investmentManagement";

	/**
	 * Bio
	 */
	private static final String BIO = "bio";

	/**
	 * Email
	 */
	private static final String EMAIL = "email";

	/**
	 * Team Distribution Address
	 */
	private static final String TEAM_DISTRIBUTION_ADDRESS = "teamDistributionEmailAddress";

	/**
	 * Year Started Industry
	 */
	private static final String YEAR_STARTED_INDUSTRY = "yearStartedIndustry";

	/**
	 * Smart Vestor Indicator
	 */
	private static final String SMART_VESTOR_PRO_INDICATOR = "smartVestorProIndicator";

	/**
	 * Fun Facts
	 */
	private static final String FUN_FACTS = "funFacts";

	/**
	 * Favourite Sport
	 */
	private static final String FAVOURITE_SPORT = "favoriteSport";

	/**
	 * Favourite Sports Team
	 */
	private static final String FAVOURITE_SPORTS_TEAM = "favoriteSportsTeam";

	/**
	 * Favourite Life Hack
	 */
	private static final String FAVOURITE_LIFE_HACK = "favoriteLifeHack";

	/**
	 * Linked In Url
	 */
	private static final String LINKED_IN_URL_FROM_JSON = "linkedInUrl";

	/**
	 * Linked In Url
	 */
	private static final String LINKED_IN_URL_TO_FRAGMENT = "linkedinUrl";

	/**
	 * Most Inspiration Moment
	 */
	private static final String MOST_INSPIRATION_MOMENT = "mostInspirationalMoment";

	/**
	 * Disciplinary Information Text
	 */
	private static final String DISCIPLINARY_INFORMATION_TEXT = "disciplinaryInformationText";

	/**
	 * Any Business Related Activities Commissions
	 */
	private static final String ANY_BUSINESS_RELATED_ACTIVITIES_COMMISSIONS = "anyBusinessRelatedActivitiesCommissions";

	/**
	 * Business Related Activities Commissions
	 */
	private static final String BUSINESS_RELATED_ACTIVITIES_COMMISSIONS_TEXT = "businessRelatedActivitiesCommissionsText";

	/**
	 * Any Additional Compensation
	 */
	private static final String ANY_ADDITIONAL_COMPENSATION = "anyAdditionalCompensation";

	/**
	 * Additional Compensation Text
	 */
	private static final String ADDITIONAL_COMPENSATION_TEXT = "additionalCompensationText";

	/**
	 * EFE Url
	 */
	private static final String EFE_URL = "efeUrl";

	/**
	 * Adv2b url
	 */
	private static final String ADV2B_URL = "adv2bUrl";

	/**
	 * Html Url
	 */
	private static final String HTML_URL = "htmlUrl";

	/**
	 * Last Updated
	 */
	private static final String LAST_UPDATED = "lastUpdated";

	/**
	 * Primary Office
	 */
	private static final String PRIMARY_OFFICE = "primaryOffice";

	/**
	 * Primary Office Postfix
	 */
	private static final String PRIMARY_OFFICE_POSTFIX = "_primaryOffice";

	/**
	 * Interests Hobbies
	 */
	private static final String INTERESTS_HOBBIES = "interestsHobbies";

	/**
	 * Address Model
	 */
	private static final String ADDRESS_MODEL = "/conf/efe/settings/dam/cfm/models/address";

	/**
	 * Jcr Title Primary Office
	 */
	private static final String JCR_TITLE_PRIMARY_OFFICE = "primary Office fragment";

	/**
	 * Name
	 */
	private static final String NAME = "name";

	/**
	 * City
	 */
	private static final String CITY = "city";

	/**
	 * State
	 */
	private static final String STATE = "state";

	/**
	 * Interests Hobbies
	 */
	private static final String ZIP = "zip";

	/**
	 * Office Locations
	 */
	private static final String OFFICE_LOCATIONS = "officesLocations";

	/**
	 * Office Locations Prefix
	 */
	private static final String OFFICE_LOCATIONS_PREFIX = "officesLocations_";

	/**
	 * Jcr Title Office Locations
	 */
	private static final String JCR_TITLE_OFFICE_LOCATIONS = "officesLocations fragment";

	/**
	 * Employment History
	 */
	private static final String EMPLOYMENT_HISTORY = "employmentHistory";

	/**
	 * Employment History Prefix
	 */
	private static final String EMPLOYMENT_HISTORY_PREFIX = "employmentHistory_";

	/**
	 * Employment History Model
	 */
	private static final String EMPLOYMENT_HISTORY_MODEL = "/conf/efe/settings/dam/cfm/models/employment-history";

	/**
	 * Jrc Title Employment History
	 */
	private static final String JCR_TITLE_EMPLOYMENT_HISTORY = "employmentHistory fragment";

	/**
	 * Start Date
	 */
	private static final String START_DATE = "startDate";

	/**
	 * End Date
	 */
	private static final String END_DATE = "endDate";

	/**
	 * Job Title
	 */
	private static final String JOB_TITLE = "jobTitle";

	/**
	 * Company Name
	 */
	private static final String COMPANY_NAME = "companyName";

	/**
	 * Current
	 */
	private static final String CURRENT = "current";

	/**
	 * Certifications
	 */
	private static final String CERTIFICATIONS = "certifications";

	/**
	 * Certification Prefix
	 */
	private static final String CERTIFICATION_PREFIX = "certification_";

	/**
	 * Job Title
	 */
	private static final String CERTIFICATION_MODEL = "/conf/efe/settings/dam/cfm/models/certification";

	/**
	 * Jcr Certification
	 */
	private static final String JCR_TITLE_CERTIFICATION = "certification fragment";

	/**
	 * Abbreviation
	 */
	private static final String ABBREVIATION = "abbreviation";

	/**
	 * Marketing Disclosure
	 */
	private static final String MARKETING_DISCLOSURE = "marketingDisclosure";

	/**
	 * Legal Compliance Disclosure
	 */
	private static final String LEGAL_COMPLIANCE_DISCLOSURE = "legalComplianceDisclosure";

	/**
	 * Honor Award
	 */
	private static final String HONOR_AWARD = "honorAward";

	/**
	 * Honor Award Model
	 */
	private static final String HONOR_AWARD_MODEL = "/conf/efe/settings/dam/cfm/models/honor-award";

	/**
	 * Jcr Title Honor Award
	 */
	private static final String JCR_TITLE_HONOR_AWARD = "honorAward fragment";

	/**
	 * Date Of Award
	 */
	private static final String DATE_OF_AWARD = "dateOfAward";

	/**
	 * Disclosure
	 */
	private static final String DISCLOSURE = "disclosure";

	/**
	 * Honor Award Name
	 */
	private static final String HONOR_AWARD_NAME = "honorAwardName";

	/**
	 * Organisation
	 */
	private static final String ORGANISATION = "organization";

	/**
	 * Education
	 */
	private static final String EDUCATION = "education";

	/**
	 * Education Fragment Prefix
	 */
	private static final String EDUCATION_FRAGMENT_PREFIX = "educationfragment_";

	/**
	 * Education Model
	 */
	private static final String EDUCATION_MODEL = "/conf/efe/settings/dam/cfm/models/education";

	/**
	 * Jcr Title Education
	 */
	private static final String JCR_TITLE_EDUCATION = "education fragment";

	/**
	 * Honor Award
	 */
	private static final String DEGREE = "degree";

	/**
	 * Major
	 */
	private static final String MAJOR = "major";

	/**
	 * Major
	 */
	private static final String UNIVERSITY = "university";

	/**
	 * Industry Exams
	 */
	private static final String INDUSTRY_EXAMS = "industryExams";

	/**
	 * Major
	 */
	private static final String INDUSTRY_EXAMS_PREFIX = "industryExam_";

	/**
	 * Industry Exam Model
	 */
	private static final String INDUSTRY_EXAM_MODEL = "/conf/efe/settings/dam/cfm/models/industry-exam";

	/**
	 * Jcr Title Industry Exam
	 */
	private static final String JCR_TITLE_INDUSTRY_EXAM = "industryExam fragment";

	/**
	 * Exam Name Long
	 */
	private static final String EXAM_NAME_LONG = "examNameLong";

	/**
	 * Exam Name Short
	 */
	private static final String EXAM_NAME_SHORT = "examNameShort";

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
	 * This method is used to create Folder
	 */
	public String createFolder(String parentPath, String folderName, ResourceResolver resourceResolver) {
		Session session = resourceResolver.adaptTo(Session.class);
		Resource parentResource = resourceResolver.getResource(parentPath);

		// Check if folder already exists
		if (parentResource.getChild(folderName) == null) {
			// Create new folder node
			Node parentNode = parentResource.adaptTo(Node.class);
			try {
				Node folderNode = parentNode.addNode(folderName, SLING_ORDERED_FOLDER);
			} catch (Exception e) {
				LOGGER.error("ContentFragmentException:", e);
			}
			try {
				session.save();
			} catch (Exception e) {
				LOGGER.error("ContentFragmentException:", e);
			}
		}
		return (parentPath + FORWARD_SLASH + folderName);
	}

	/**
	 * This method is used to create Fragment for Planner
	 */
	public void createFragmentPlanner(ResourceResolver resourceResolver) {
		String jsonObjectPlanner = restService.getData(plannerApiService.getPlannersAPIEndpoint(),
				plannerApiService.getAuthHeader());// Calling API to Get JSON Response
		Gson gson = new Gson();
		JsonElement jsonElement = gson.fromJson(jsonObjectPlanner, JsonElement.class);
		JsonArray jsonArray = jsonElement.getAsJsonArray();

		String rootPath = createFolder(ROOT_FOLDER_PATH, PLANNER, resourceResolver);
		for (Object obj : jsonArray) {

			JsonObject jsonObj = (JsonObject) obj;
			String firstName = jsonObj.get(FIRST_NAME).toString();
			int id = Integer.parseInt(jsonObj.get(ID).getAsString());

			String childPathPlanner = createFolder(rootPath, firstName + Integer.toString(id), resourceResolver);

			String fragmentNamePlanners = FRAGMENT_NAME_PREFIX + firstName + UNDERSCORE + Integer.toString(id);

			Resource existingFragement = resourceResolver
					.getResource(childPathPlanner + FORWARD_SLASH + fragmentNamePlanners);
			if (existingFragement == null) {
				Resource templateOrModelRsc = resourceResolver.getResource(PLANNER_MODEL);
				Resource parentRsc = resourceResolver.getResource(childPathPlanner);
				FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
				try {
					tpl.createFragment(parentRsc, fragmentNamePlanners, JCR_TITLE_PLANNER);
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
					.getResource(childPathPlanner + FORWARD_SLASH + fragmentNamePlanners + MASTER_NODE);
			Node plannerMasterNode = plannerMasterResource.adaptTo(Node.class);

			try {

				if (jsonObj.get(INCLUDE_IN_ADV2B) != null && !jsonObj.get(INCLUDE_IN_ADV2B).isJsonNull()) {
					plannerMasterNode.setProperty(INCLUDE_IN_ADV2B, jsonObj.get(INCLUDE_IN_ADV2B).getAsBoolean());
				}
				if (jsonObj.get(INCLUDE_IN_API_PAYLOAD) != null && !jsonObj.get(INCLUDE_IN_API_PAYLOAD).isJsonNull()) {
					plannerMasterNode.setProperty(INCLUDE_IN_API_PAYLOAD,
							jsonObj.get(INCLUDE_IN_API_PAYLOAD).getAsBoolean());
				}
				if (jsonObj.get(FIRST_NAME) != null && !jsonObj.get(FIRST_NAME).isJsonNull()) {
					plannerMasterNode.setProperty(FIRST_NAME, jsonObj.get(FIRST_NAME).toString());

				}
				if (jsonObj.get(FIRST_NAME_ALIAS) != null && !jsonObj.get(FIRST_NAME_ALIAS).isJsonNull()) {
					plannerMasterNode.setProperty(FIRST_NAME_ALIAS, jsonObj.get(FIRST_NAME_ALIAS).toString());
				}
				if (jsonObj.get(MIDDLE_NAME) != null && !jsonObj.get(MIDDLE_NAME).isJsonNull()) {
					plannerMasterNode.setProperty(MIDDLE_NAME, jsonObj.get(MIDDLE_NAME).toString());
				}
				if (jsonObj.get(LAST_NAME) != null && !jsonObj.get(LAST_NAME).isJsonNull()) {
					plannerMasterNode.setProperty(LAST_NAME, jsonObj.get(LAST_NAME).toString());
				}
				if (jsonObj.get(ID) != null && !jsonObj.get(ID).isJsonNull()) {
					plannerMasterNode.setProperty(ID, Integer.parseInt(jsonObj.get(ID).getAsString()));
				}
				if (jsonObj.get(SUFFIX) != null && !jsonObj.get(SUFFIX).isJsonNull()) {
					plannerMasterNode.setProperty(SUFFIX, jsonObj.get(SUFFIX).toString());
				}
				if (jsonObj.get(TITLE) != null && !jsonObj.get(TITLE).isJsonNull()) {
					plannerMasterNode.setProperty(TITLE, jsonObj.get(TITLE).toString());
				}
				if (jsonObj.get(BIRTH_YEAR) != null && !jsonObj.get(BIRTH_YEAR).isJsonNull()) {
					plannerMasterNode.setProperty(BIRTH_YEAR, Integer.parseInt(jsonObj.get(BIRTH_YEAR).getAsString()));
				}
				if (jsonObj.get(YEAR_JOINED) != null && !jsonObj.get(YEAR_JOINED).isJsonNull()) {
					plannerMasterNode.setProperty(YEAR_JOINED,Integer.parseInt(jsonObj.get(YEAR_JOINED).getAsString()));
				}
				if (jsonObj.get(DIRECT_LINE_PHONE) != null && !jsonObj.get(DIRECT_LINE_PHONE).isJsonNull()) {
					plannerMasterNode.setProperty(DIRECT_LINE_PHONE, jsonObj.get(DIRECT_LINE_PHONE).toString());
				}
				if (jsonObj.get(ADVISOR_CRD_FROM_JSON) != null && !jsonObj.get(ADVISOR_CRD_FROM_JSON).isJsonNull()) {
					plannerMasterNode.setProperty(ADVISOR_CRD_TO_FRAGMENT,Integer.parseInt(jsonObj.get(ADVISOR_CRD_FROM_JSON).getAsString()));
				}
				if (jsonObj.get(DESKTOP_IMAGE_URL_FROM_JSON) != null
						&& !jsonObj.get(DESKTOP_IMAGE_URL_FROM_JSON).isJsonNull()) {
					plannerMasterNode.setProperty(DESKTOP_IMAGE_URL_TO_FRAGMENT,
							jsonObj.get(DESKTOP_IMAGE_URL_FROM_JSON).toString());
				}
				if (jsonObj.get(MOBILE_IMAGE_URL_FROM_URL) != null
						&& !jsonObj.get(MOBILE_IMAGE_URL_FROM_URL).isJsonNull()) {
					plannerMasterNode.setProperty(MOBILE_IMAGE_URL_TO_FRAGMENT,
							jsonObj.get(MOBILE_IMAGE_URL_FROM_URL).toString());
				}
				if (jsonObj.get(CIRCLE_IMAGE_URL_FROM_JSON) != null
						&& !jsonObj.get(CIRCLE_IMAGE_URL_FROM_JSON).isJsonNull()) {
					plannerMasterNode.setProperty(CIRCLE_IMAGE_URL_TO_FRAGMENT,
							jsonObj.get(CIRCLE_IMAGE_URL_FROM_JSON).toString());
				}
				if (jsonObj.get(EDUCATION_INDICATOR) != null && !jsonObj.get(EDUCATION_INDICATOR).isJsonNull()) {
					plannerMasterNode.setProperty(EDUCATION_INDICATOR, jsonObj.get(EDUCATION_INDICATOR).toString());
				}

				if (jsonObj.get(TAX_PLANNER) != null && !jsonObj.get(TAX_PLANNER).isJsonNull()) {
					plannerMasterNode.setProperty(TAX_PLANNER, jsonObj.get(TAX_PLANNER).toString());
				}
				if (jsonObj.get(ADVANCED_PLANNING) != null && !jsonObj.get(ADVANCED_PLANNING).isJsonNull()) {
					plannerMasterNode.setProperty(ADVANCED_PLANNING, jsonObj.get(ADVANCED_PLANNING).getAsBoolean());
				}
				if (jsonObj.get(ESTATE_PLANNING) != null && !jsonObj.get(ESTATE_PLANNING).isJsonNull()) {
					plannerMasterNode.setProperty(ESTATE_PLANNING, jsonObj.get(ESTATE_PLANNING).getAsBoolean());
				}
				if (jsonObj.get(INSURANCE_PLANNING) != null && !jsonObj.get(INSURANCE_PLANNING).isJsonNull()) {
					plannerMasterNode.setProperty(INSURANCE_PLANNING, jsonObj.get(INSURANCE_PLANNING).getAsBoolean());
				}
				if (jsonObj.get(INVESTMENT_MANAGEMENT) != null && !jsonObj.get(INVESTMENT_MANAGEMENT).isJsonNull()) {
					plannerMasterNode.setProperty(INVESTMENT_MANAGEMENT,
							jsonObj.get(INVESTMENT_MANAGEMENT).getAsBoolean());
				}
				if (jsonObj.get(BIO) != null && !jsonObj.get(BIO).isJsonNull()) {
					plannerMasterNode.setProperty(BIO, jsonObj.get(BIO).toString());
				}
				if (jsonObj.get(EMAIL) != null && !jsonObj.get(EMAIL).isJsonNull()) {
					plannerMasterNode.setProperty(EMAIL, jsonObj.get(EMAIL).toString());
				}
				if (jsonObj.get(TEAM_DISTRIBUTION_ADDRESS) != null
						&& !jsonObj.get(TEAM_DISTRIBUTION_ADDRESS).isJsonNull()) {
					plannerMasterNode.setProperty(TEAM_DISTRIBUTION_ADDRESS,
							jsonObj.get(TEAM_DISTRIBUTION_ADDRESS).toString());
				}
				if (jsonObj.get(YEAR_STARTED_INDUSTRY) != null && !jsonObj.get(YEAR_STARTED_INDUSTRY).isJsonNull()) {
					plannerMasterNode.setProperty(YEAR_STARTED_INDUSTRY,
							Integer.parseInt(jsonObj.get(YEAR_STARTED_INDUSTRY).getAsString()));
				}
				if (jsonObj.get(SMART_VESTOR_PRO_INDICATOR) != null
						&& !jsonObj.get(SMART_VESTOR_PRO_INDICATOR).isJsonNull()) {
					plannerMasterNode.setProperty(SMART_VESTOR_PRO_INDICATOR,
							jsonObj.get(SMART_VESTOR_PRO_INDICATOR).getAsBoolean());
				}
				if (jsonObj.get(FUN_FACTS) != null && !jsonObj.get(FUN_FACTS).isJsonNull()) {
					plannerMasterNode.setProperty(FUN_FACTS, jsonObj.get(FUN_FACTS).toString());
				}
				if (jsonObj.get(FAVOURITE_SPORT) != null && !jsonObj.get(FAVOURITE_SPORT).isJsonNull()) {
					plannerMasterNode.setProperty(FAVOURITE_SPORT, jsonObj.get(FAVOURITE_SPORT).toString());
				}
				if (jsonObj.get(FAVOURITE_SPORTS_TEAM) != null && !jsonObj.get(FAVOURITE_SPORTS_TEAM).isJsonNull()) {
					plannerMasterNode.setProperty(FAVOURITE_SPORTS_TEAM, jsonObj.get(FAVOURITE_SPORTS_TEAM).toString());
				}
				if (jsonObj.get(FAVOURITE_LIFE_HACK) != null && !jsonObj.get(FAVOURITE_LIFE_HACK).isJsonNull()) {
					plannerMasterNode.setProperty(FAVOURITE_LIFE_HACK, jsonObj.get(FAVOURITE_LIFE_HACK).toString());
				}
				if (jsonObj.get(LINKED_IN_URL_FROM_JSON) != null
						&& !jsonObj.get(LINKED_IN_URL_FROM_JSON).isJsonNull()) {
					plannerMasterNode.setProperty(LINKED_IN_URL_TO_FRAGMENT,
							jsonObj.get(LINKED_IN_URL_FROM_JSON).toString());
				}
				if (jsonObj.get(MOST_INSPIRATION_MOMENT) != null
						&& !jsonObj.get(MOST_INSPIRATION_MOMENT).isJsonNull()) {
					plannerMasterNode.setProperty(MOST_INSPIRATION_MOMENT,
							jsonObj.get(MOST_INSPIRATION_MOMENT).toString());
				}
				if (jsonObj.get(DISCIPLINARY_INFORMATION_TEXT) != null
						&& !jsonObj.get(DISCIPLINARY_INFORMATION_TEXT).isJsonNull()) {
					plannerMasterNode.setProperty(DISCIPLINARY_INFORMATION_TEXT,
							jsonObj.get(DISCIPLINARY_INFORMATION_TEXT).toString());
				}
				if (jsonObj.get(ANY_BUSINESS_RELATED_ACTIVITIES_COMMISSIONS) != null
						&& !jsonObj.get(ANY_BUSINESS_RELATED_ACTIVITIES_COMMISSIONS).isJsonNull()) {
					plannerMasterNode.setProperty(ANY_BUSINESS_RELATED_ACTIVITIES_COMMISSIONS,
							jsonObj.get(ANY_BUSINESS_RELATED_ACTIVITIES_COMMISSIONS).getAsBoolean());
				}
				if (jsonObj.get(BUSINESS_RELATED_ACTIVITIES_COMMISSIONS_TEXT) != null
						&& !jsonObj.get(BUSINESS_RELATED_ACTIVITIES_COMMISSIONS_TEXT).isJsonNull()) {
					plannerMasterNode.setProperty(BUSINESS_RELATED_ACTIVITIES_COMMISSIONS_TEXT,
							jsonObj.get(BUSINESS_RELATED_ACTIVITIES_COMMISSIONS_TEXT).toString());
				}
				if (jsonObj.get(ANY_ADDITIONAL_COMPENSATION) != null
						&& !jsonObj.get(ANY_ADDITIONAL_COMPENSATION).isJsonNull()) {
					plannerMasterNode.setProperty(ANY_ADDITIONAL_COMPENSATION,
							jsonObj.get(ANY_ADDITIONAL_COMPENSATION).getAsBoolean());
				}
				if (jsonObj.get(ADDITIONAL_COMPENSATION_TEXT) != null
						&& !jsonObj.get(ADDITIONAL_COMPENSATION_TEXT).isJsonNull()) {
					plannerMasterNode.setProperty(ADDITIONAL_COMPENSATION_TEXT,
							jsonObj.get(ADDITIONAL_COMPENSATION_TEXT).toString());
				}
				if (jsonObj.get(EFE_URL) != null && !jsonObj.get(EFE_URL).isJsonNull()) {
					plannerMasterNode.setProperty(EFE_URL, jsonObj.get(EFE_URL).toString());
				}
				if (jsonObj.get(ADV2B_URL) != null && !jsonObj.get(ADV2B_URL).isJsonNull()) {
					plannerMasterNode.setProperty(ADV2B_URL, jsonObj.get(ADV2B_URL).toString());
				}
				if (jsonObj.get(HTML_URL) != null && !jsonObj.get(HTML_URL).isJsonNull()) {
					plannerMasterNode.setProperty(HTML_URL, jsonObj.get(HTML_URL).toString());
				}
				if (jsonObj.get(LAST_UPDATED) != null && !jsonObj.get(LAST_UPDATED).isJsonNull()) {
					plannerMasterNode.setProperty(LAST_UPDATED, jsonObj.get(LAST_UPDATED).toString());
				}
				JsonArray interestsHobbiesArr = jsonObj.get(INTERESTS_HOBBIES).getAsJsonArray();

				String[] interestsHobbiesStringArray = new String[interestsHobbiesArr.size()];
				if (interestsHobbiesArr != null) {
					for (int i = 0; i < interestsHobbiesArr.size(); i++) {
						interestsHobbiesStringArray[i] = interestsHobbiesArr.get(i).getAsString();
					}
					if (interestsHobbiesStringArray != null) {
						plannerMasterNode.setProperty(INTERESTS_HOBBIES, interestsHobbiesStringArray);
					}
				}
				createChildFragment(childPathPlanner, jsonObj, firstName, id, resourceResolver);

			} catch (Exception e) {
				LOGGER.error("Exception occured.");
			}

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
	public void createChildFragment(String childPathPlanner, JsonObject jsonObj, String firstName, int id,
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
	public void createPrimaryOfficeFragmentPlanner(String childPathPlanner, JsonObject jsonObj, String firstName,
			int id, ResourceResolver resourceResolver) {
		try {
			JsonObject primaryOffice = jsonObj.get(PRIMARY_OFFICE).getAsJsonObject();
			if (primaryOffice != null) {

				String primaryOfficeFragmentName = FRAGMENT_NAME_PREFIX + firstName + UNDERSCORE + Integer.toString(id)
						+ PRIMARY_OFFICE_POSTFIX;

				Resource primaryOfficeexistingFragement = resourceResolver
						.getResource(childPathPlanner + FORWARD_SLASH + primaryOfficeFragmentName);
				if (primaryOfficeexistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver.getResource(ADDRESS_MODEL);
					Resource parentRsc = resourceResolver.getResource(childPathPlanner);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, primaryOfficeFragmentName, JCR_TITLE_PRIMARY_OFFICE);
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
						.getResource(childPathPlanner + FORWARD_SLASH + primaryOfficeFragmentName + MASTER_NODE);
				Node plannerPrimaryOfficeNode = plannerPrimaryOfficeResource.adaptTo(Node.class);

				if (primaryOffice.get(ID) != null && !primaryOffice.get(ID).isJsonNull()) {

					plannerPrimaryOfficeNode.setProperty(ID, primaryOffice.get(ID).getAsString());
				}

				if (primaryOffice.get(NAME) != null && !primaryOffice.get(NAME).isJsonNull()) {

					plannerPrimaryOfficeNode.setProperty(NAME, primaryOffice.get(NAME).getAsString());
				}

				if (primaryOffice.get(CITY) != null && !primaryOffice.get(CITY).isJsonNull()) {

					plannerPrimaryOfficeNode.setProperty(CITY, primaryOffice.get(CITY).getAsString());
				}

				if (primaryOffice.get(STATE) != null && !primaryOffice.get(STATE).isJsonNull()) {

					plannerPrimaryOfficeNode.setProperty(STATE, primaryOffice.get(STATE).getAsString());
				}

				if (primaryOffice.get(ZIP) != null && !primaryOffice.get(ZIP).isJsonNull()) {

					plannerPrimaryOfficeNode.setProperty(ZIP, primaryOffice.get(ZIP).getAsString());
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured.");
		}
	}

	/**
	 * This method is used to create and update fragments for office locations
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public void createOfficesLocationsFragmentPlanner(String childPathPlanner, JsonObject jsonObj,
			ResourceResolver resourceResolver) {
		try {
			JsonArray officesLocationsArr = jsonObj.get(OFFICE_LOCATIONS).getAsJsonArray();
			String officesLocationsRootPath = createFolder(childPathPlanner, OFFICE_LOCATIONS, resourceResolver);
			int officesLocationsCount = 1;
			for (Object officesLocationsObj : officesLocationsArr) {

				JsonObject officesLocations = (JsonObject) officesLocationsObj;

				String officesLocationsFragmentName = OFFICE_LOCATIONS_PREFIX + Integer.toString(officesLocationsCount);
				Resource officesLocationsExistingFragement = resourceResolver
						.getResource(officesLocationsRootPath + FORWARD_SLASH + officesLocationsFragmentName);
				if (officesLocationsExistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver.getResource(ADDRESS_MODEL);
					Resource parentRsc = resourceResolver.getResource(officesLocationsRootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, officesLocationsFragmentName, JCR_TITLE_OFFICE_LOCATIONS);
						if (resourceResolver.hasChanges()) {
							resourceResolver.commit();
						}
					} catch (ContentFragmentException e) {
						LOGGER.error("ContentFragmentException:", e);
					} catch (PersistenceException e) {
						LOGGER.error("PersistenceException:", e);
					}
				}
				Resource plannerOfficesLocationsResource = resourceResolver.getResource(
						officesLocationsRootPath + FORWARD_SLASH + officesLocationsFragmentName + MASTER_NODE);
				Node plannerOfficesLocationsNode = plannerOfficesLocationsResource.adaptTo(Node.class);

				if (officesLocations.get(ID) != null && !officesLocations.get(ID).isJsonNull()) {

					plannerOfficesLocationsNode.setProperty(ID, officesLocations.get(ID).getAsString());
				}

				if (officesLocations.get(CITY) != null && !officesLocations.get(CITY).isJsonNull()) {

					plannerOfficesLocationsNode.setProperty(CITY, officesLocations.get(CITY).getAsString());
				}
				if (officesLocations.get(NAME) != null && !officesLocations.get(NAME).isJsonNull()) {

					plannerOfficesLocationsNode.setProperty(NAME, officesLocations.get(NAME).getAsString());
				}

				if (officesLocations.get(STATE) != null && !officesLocations.get(STATE).isJsonNull()) {

					plannerOfficesLocationsNode.setProperty(STATE, officesLocations.get(STATE).getAsString());
				}
				if (officesLocations.get(ZIP) != null && !officesLocations.get(ZIP).isJsonNull()) {

					plannerOfficesLocationsNode.setProperty(ZIP, officesLocations.get(ZIP).getAsString());
				}

				officesLocationsCount++;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured.");
		}
	}

	/**
	 * This method is used to create and update Employment History Fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public void createEmploymentHistoryFragmentPlanner(String childPathPlanner, JsonObject jsonObj,
			ResourceResolver resourceResolver) {
		try {
			JsonArray employmentHistoryArr = jsonObj.get(EMPLOYMENT_HISTORY).getAsJsonArray();
			String employmentHistoryRootPath = createFolder(childPathPlanner, EMPLOYMENT_HISTORY, resourceResolver);
			int employmentHistoryCount = 1;
			for (Object employmentHistoryObj : employmentHistoryArr) {

				JsonObject employmentHistory = (JsonObject) employmentHistoryObj;

				String employmentHistoryFragmentName = EMPLOYMENT_HISTORY_PREFIX
						+ Integer.toString(employmentHistoryCount);
				Resource employmentHistoryExistingFragement = resourceResolver
						.getResource(employmentHistoryRootPath + FORWARD_SLASH + employmentHistoryFragmentName);
				if (employmentHistoryExistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver.getResource(EMPLOYMENT_HISTORY_MODEL);
					Resource parentRsc = resourceResolver.getResource(employmentHistoryRootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, employmentHistoryFragmentName, JCR_TITLE_EMPLOYMENT_HISTORY);
						if (resourceResolver.hasChanges()) {
							resourceResolver.commit();
						}
					} catch (ContentFragmentException e) {
						LOGGER.error("ContentFragmentException:", e);
					} catch (PersistenceException e) {
						LOGGER.error("PersistenceException:", e);
					}
				}
				Resource plannerEmploymentHistoryResource = resourceResolver.getResource(
						employmentHistoryRootPath + FORWARD_SLASH + employmentHistoryFragmentName + MASTER_NODE);
				Node plannerEmploymentHistoryExamNode = plannerEmploymentHistoryResource.adaptTo(Node.class);

				if (employmentHistory.get(START_DATE) != null && !employmentHistory.get(START_DATE).isJsonNull()) {

					plannerEmploymentHistoryExamNode.setProperty(START_DATE,
							employmentHistory.get(START_DATE).getAsString());
				}

				if (employmentHistory.get(END_DATE) != null && !employmentHistory.get(END_DATE).isJsonNull()) {

					plannerEmploymentHistoryExamNode.setProperty(END_DATE,
							employmentHistory.get(END_DATE).getAsString());
				}
				if (employmentHistory.get(JOB_TITLE) != null && !employmentHistory.get(JOB_TITLE).isJsonNull()) {

					plannerEmploymentHistoryExamNode.setProperty(JOB_TITLE,
							employmentHistory.get(JOB_TITLE).getAsString());
				}

				if (employmentHistory.get(COMPANY_NAME) != null && !employmentHistory.get(COMPANY_NAME).isJsonNull()) {

					plannerEmploymentHistoryExamNode.setProperty(COMPANY_NAME,
							employmentHistory.get(COMPANY_NAME).getAsString());
				}
				if (employmentHistory.get(CURRENT) != null && !employmentHistory.get(CURRENT).isJsonNull()) {
					plannerEmploymentHistoryExamNode.setProperty(CURRENT,
							employmentHistory.get(CURRENT).getAsBoolean());
				}

				employmentHistoryCount++;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured.");
		}
	}

	/**
	 * This method is used to create and update certification fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public void createCertificationsFragmentPlanner(String childPathPlanner, JsonObject jsonObj,
			ResourceResolver resourceResolver) {
		try {
			JsonArray certificationArr = jsonObj.get(CERTIFICATIONS).getAsJsonArray();
			String certificationsRootPath = createFolder(childPathPlanner, CERTIFICATIONS, resourceResolver);
			int certificationsCount = 1;
			for (Object certificationsObj : certificationArr) {

				JsonObject certification = (JsonObject) certificationsObj;

				String certificationFragmentName = CERTIFICATION_PREFIX + Integer.toString(certificationsCount);
				Resource certificationExistingFragement = resourceResolver
						.getResource(certificationsRootPath + FORWARD_SLASH + certificationFragmentName);
				if (certificationExistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver.getResource(CERTIFICATION_MODEL);
					Resource parentRsc = resourceResolver.getResource(certificationsRootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, certificationFragmentName, JCR_TITLE_CERTIFICATION);
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
						.getResource(certificationsRootPath + FORWARD_SLASH + certificationFragmentName + MASTER_NODE);
				Node plannerCertificationExamNode = plannerCertificationResource.adaptTo(Node.class);

				if (certification.get(ABBREVIATION) != null && !certification.get(ABBREVIATION).isJsonNull()) {

					plannerCertificationExamNode.setProperty(ABBREVIATION,
							certification.get(ABBREVIATION).getAsString());
				}

				if (certification.get(LEGAL_COMPLIANCE_DISCLOSURE) != null
						&& !certification.get(LEGAL_COMPLIANCE_DISCLOSURE).isJsonNull()) {

					plannerCertificationExamNode.setProperty(LEGAL_COMPLIANCE_DISCLOSURE,
							certification.get(LEGAL_COMPLIANCE_DISCLOSURE).getAsString());
				}
				if (certification.get(MARKETING_DISCLOSURE) != null
						&& !certification.get(MARKETING_DISCLOSURE).isJsonNull()) {

					plannerCertificationExamNode.setProperty(MARKETING_DISCLOSURE,
							certification.get(MARKETING_DISCLOSURE).getAsString());
				}

				if (certification.get(NAME) != null && !certification.get(NAME).isJsonNull()) {

					plannerCertificationExamNode.setProperty(NAME, certification.get(NAME).getAsString());
				}

				certificationsCount++;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured.");
		}
	}

	/**
	 * This method is used to create and update honor award fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public void createHonorAwardFragmentPlanner(String childPathPlanner, JsonObject jsonObj,
			ResourceResolver resourceResolver) {
		try {
			JsonArray HonorAward = jsonObj.get(HONOR_AWARD).getAsJsonArray();
			String honorAwardrootPath = createFolder(childPathPlanner, HONOR_AWARD, resourceResolver);
			int honorAwardCount = 1;
			for (Object honorAwardObj : HonorAward) {

				JsonObject honorAward = (JsonObject) honorAwardObj;

				String honorAwardFragmentName = HONOR_AWARD + Integer.toString(honorAwardCount);
				Resource honorAwardexistingFragement = resourceResolver
						.getResource(honorAwardrootPath + FORWARD_SLASH + honorAwardFragmentName);
				if (honorAwardexistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver.getResource(HONOR_AWARD_MODEL);
					Resource parentRsc = resourceResolver.getResource(honorAwardrootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, honorAwardFragmentName, JCR_TITLE_HONOR_AWARD);
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
						.getResource(honorAwardrootPath + FORWARD_SLASH + honorAwardFragmentName + MASTER_NODE);
				Node HonorAwardNode = plannerHonorAwardResource.adaptTo(Node.class);

				if (honorAward.get(DATE_OF_AWARD) != null && !honorAward.get(DATE_OF_AWARD).isJsonNull()) {

					HonorAwardNode.setProperty(DATE_OF_AWARD, honorAward.get(DATE_OF_AWARD).getAsString());
				}
				if (honorAward.get(DISCLOSURE) != null && !honorAward.get(DISCLOSURE).isJsonNull()) {

					HonorAwardNode.setProperty(DISCLOSURE, honorAward.get(DISCLOSURE).getAsString());
				}
				if (honorAward.get(HONOR_AWARD_NAME) != null && !honorAward.get(HONOR_AWARD_NAME).isJsonNull()) {

					HonorAwardNode.setProperty(HONOR_AWARD_NAME, honorAward.get(HONOR_AWARD_NAME).getAsString());
				}
				if (honorAward.get(ORGANISATION) != null && !honorAward.get(ORGANISATION).isJsonNull()) {

					HonorAwardNode.setProperty(ORGANISATION, honorAward.get(ORGANISATION).getAsString());
				}

				honorAwardCount++;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured.");
		}
	}

	/**
	 * This method is used to create and update education fragments
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public void createEducationFragmentPlanner(String childPathPlanner, JsonObject jsonObj,
			ResourceResolver resourceResolver) {
		try {
			JsonArray education = jsonObj.get(EDUCATION).getAsJsonArray();
			String educationrootPath = createFolder(childPathPlanner, EDUCATION, resourceResolver);
			int educationCount = 1;
			for (Object eduObj : education) {
				JsonObject edu = (JsonObject) eduObj;

				String educationFragmentName = EDUCATION_FRAGMENT_PREFIX + Integer.toString(educationCount);
				Resource educationexistingFragement = resourceResolver
						.getResource(educationrootPath + FORWARD_SLASH + educationFragmentName);
				if (educationexistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver.getResource(EDUCATION_MODEL);
					Resource parentRsc = resourceResolver.getResource(educationrootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, educationFragmentName, JCR_TITLE_EDUCATION);
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
						.getResource(educationrootPath + FORWARD_SLASH + educationFragmentName + MASTER_NODE);
				Node plannerEducationNode = plannerEducationResource.adaptTo(Node.class);

				if (edu.get(DEGREE) != null && !edu.get(DEGREE).isJsonNull()) {

					plannerEducationNode.setProperty(DEGREE, edu.get(DEGREE).getAsString());
				}

				if (edu.get(MAJOR) != null && !edu.get(MAJOR).isJsonNull()) {

					plannerEducationNode.setProperty(MAJOR, edu.get(MAJOR).getAsString());
				}

				if (edu.get(UNIVERSITY) != null && !edu.get(UNIVERSITY).isJsonNull()) {

					plannerEducationNode.setProperty(UNIVERSITY, edu.get(UNIVERSITY).getAsString());
				}
				educationCount++;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured.");
		}
	}

	/**
	 * This method is used to create and update Industry Exam Fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public void createIndustryExamFragmentPlanner(String childPathPlanner, JsonObject jsonObj,
			ResourceResolver resourceResolver) {
		try {
			JsonArray industryExam = jsonObj.get(INDUSTRY_EXAMS).getAsJsonArray();
			String industryExamrootPath = createFolder(childPathPlanner, INDUSTRY_EXAMS, resourceResolver);
			int industryExamCount = 1;
			for (Object industryExamObj : industryExam) {

				JsonObject industryexam = (JsonObject) industryExamObj;

				String industryExamFragmentName = INDUSTRY_EXAMS_PREFIX + Integer.toString(industryExamCount);
				Resource industryExamexistingFragement = resourceResolver
						.getResource(industryExamrootPath + FORWARD_SLASH + industryExamFragmentName);
				if (industryExamexistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver.getResource(INDUSTRY_EXAM_MODEL);
					Resource parentRsc = resourceResolver.getResource(industryExamrootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, industryExamFragmentName, JCR_TITLE_INDUSTRY_EXAM);
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
						.getResource(industryExamrootPath + FORWARD_SLASH + industryExamFragmentName + MASTER_NODE);
				Node plannerIndustryExamNode = plannerIndustryExamResource.adaptTo(Node.class);

				if (industryexam.get(EXAM_NAME_LONG) != null && !industryexam.get(EXAM_NAME_LONG).isJsonNull()) {

					plannerIndustryExamNode.setProperty(EXAM_NAME_LONG, industryexam.get(EXAM_NAME_LONG).getAsString());
				}

				if (industryexam.get(EXAM_NAME_SHORT) != null && !industryexam.get(EXAM_NAME_SHORT).isJsonNull()) {

					plannerIndustryExamNode.setProperty(EXAM_NAME_SHORT,
							industryexam.get(EXAM_NAME_SHORT).getAsString());
				}

				industryExamCount++;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured.");
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
