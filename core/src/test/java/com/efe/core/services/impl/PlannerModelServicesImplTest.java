package com.efe.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jcr.Node;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.services.PlannerApiService;
import com.efe.core.services.RestService;
import com.efe.core.utils.EducationPlannerUtil;
import com.efe.core.utils.FolderUtil;
import com.efe.core.utils.NodePropertyManagerUtil;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class PlannerModelServicesImplTest.
 *
 */
@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class PlannerModelServicesImplTest {

	/** The aem context. */
	private final AemContext aemContext = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

	/** The resourceResolver. */
	@Mock
	private ResourceResolver resourceResolver;
	
	/** Mock ResourceResolverFactory. */
	@Mock
	private ResourceResolverFactory resourceResolverFactory;

	/** Mock RestService. */
	@Mock
	private RestService restService;

	/** Mock Resource. */
	@Mock
	private Resource templateOrModelRsc, templateOrModelRscPo, templateOrModelRscEd, templateOrModelIex,
			templateOrModelRscCer, templateOrModelRscEh, templateOrModelRscAdd,templateOrModelRscHr,parentResource,honorAwardrootPathResource;

	/** Mock FragmentTemplate. */
	@Mock
	private FragmentTemplate tpl, tplPo, tplEd, tplIex, tplCer, tplEh, tplAdd, tplHr;

	/** Mock Resource. */
	@Mock
	private Resource parentRsc,existingFragement;


	/** The Resource. */
	@Mock
	private Resource plannerMasterResource, plannerPrimaryOfficeResource, plannerEducationResource,
			plannerIndustryExamResource, plannerCertificationResource, plannerEmploymentHistoryResource,
			plannerOfficesLocationsResource,plannerHonorAwardResource,rootPathParentResource,educationRootPathResource,industryExamRootPathResource,certificationsRootPathResource,employmentHistoryRootPathResource,officesLocationsRootPathResource;

	/** The Node. */
	@Mock
	private Node parentNode, plannerMasterNode, plannerPrimaryOfficeNode, plannerEducationNode;

	/** The plannerApiService. */
	@Mock
	private PlannerApiService plannerApiService;
	
	private MockedStatic<FolderUtil> mockStatic1;
	private MockedStatic<NodePropertyManagerUtil> mockStatic2;

	/** The plannerModelServicesImpl. */
	@InjectMocks
	PlannerModelServicesImpl plannerModelServicesImpl = new PlannerModelServicesImpl();

	/**
	 * Set up
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		final Map<String, Object> subServiceUser = new ConcurrentHashMap<>();
		subServiceUser.put(ResourceResolverFactory.SUBSERVICE,"efe-service-user");

		aemContext.registerService(ResourceResolver.class, resourceResolver);
		aemContext.registerService(ResourceResolverFactory.class, resourceResolverFactory);
		lenient().when(resourceResolverFactory.getServiceResourceResolver(subServiceUser)).thenReturn(resourceResolver);
		
		aemContext.registerService(RestService.class, restService);
		aemContext.registerService(PlannerApiService.class, plannerApiService);
		aemContext.registerService(FragmentTemplate.class, tpl);
		aemContext.registerService(Resource.class, existingFragement);
		aemContext.registerService(Resource.class, templateOrModelRsc);
		aemContext.registerService(Resource.class, parentRsc);
		aemContext.registerService(Resource.class, plannerMasterResource);
		aemContext.registerService(Resource.class, plannerPrimaryOfficeResource);
		aemContext.registerService(Resource.class, plannerEducationResource);
		aemContext.registerService(Resource.class, plannerIndustryExamResource);
		aemContext.registerService(Resource.class, plannerCertificationResource);
		aemContext.registerService(Resource.class, plannerEmploymentHistoryResource);
		aemContext.registerService(Resource.class, plannerOfficesLocationsResource);
		aemContext.registerService(Resource.class, plannerHonorAwardResource);
		aemContext.registerService(Resource.class, parentResource);
		aemContext.registerService(Resource.class, employmentHistoryRootPathResource);
		
		aemContext.registerService(Node.class, plannerMasterNode);
		aemContext.registerService(Node.class, plannerPrimaryOfficeNode);
		aemContext.registerService(Node.class, plannerEducationNode);
		aemContext.registerService(Resource.class, templateOrModelRscPo);
		aemContext.registerService(Resource.class, templateOrModelRscEd);
		aemContext.registerService(Resource.class, templateOrModelRscEh);

		aemContext.registerService(FragmentTemplate.class, tplPo);
		aemContext.registerService(FragmentTemplate.class, tplEd);
		aemContext.registerService(Resource.class, templateOrModelIex);
		aemContext.registerService(Resource.class, templateOrModelRscCer);
		aemContext.registerService(Resource.class, templateOrModelRscAdd);
		aemContext.registerService(Resource.class, templateOrModelRscHr);
		aemContext.registerService(Resource.class, rootPathParentResource);
		aemContext.registerService(Resource.class, educationRootPathResource);
		aemContext.registerService(Resource.class, industryExamRootPathResource);
		aemContext.registerService(Resource.class, certificationsRootPathResource); 
		aemContext.registerService(Resource.class, officesLocationsRootPathResource);
		aemContext.registerService(Resource.class, honorAwardrootPathResource);
		
		aemContext.registerService(FragmentTemplate.class, tplIex);
		aemContext.registerService(FragmentTemplate.class, tplCer);
		aemContext.registerService(FragmentTemplate.class, tplEh);
		aemContext.registerService(FragmentTemplate.class, tplAdd);
		aemContext.registerService(FragmentTemplate.class, tplHr);
		

		mockStatic1 = Mockito.mockStatic(FolderUtil.class);
		mockStatic2 = Mockito.mockStatic(NodePropertyManagerUtil.class);
		//mockStatic3 = Mockito.mockStatic(EducationPlannerUtil.class);
		//mockStatic4 = Mockito.mockStatic(FragmentUtil.class);

	}



	@AfterEach
	void close() {
		mockStatic1.close();
		mockStatic2.close();
		//mockStatic3.close();
		//mockStatic4.close();
	}

	@Test
	void testCreateFragmentPlanner() throws ContentFragmentException, PersistenceException {

		String jsonObjectPlanner = "[\r\n" + "    {\r\n" + "        \"includeInADV2B\": true,\r\n"
				+ "        \"includeInApiPayload\": true,\r\n" + "        \"firstName\": \"Johnathan\",\r\n"
				+ "        \"firstNameAlias\": \"Aaron\",\r\n" + "        \"middleName\": \"Aaron\",\r\n"
				+ "        \"lastName\": \"Cherry\",\r\n" + "        \"id\": 179,\r\n" + "        \"suffix\": \"\",\r\n"
				+ "        \"title\": \"Director, Financial Planning\",\r\n" + "        \"birthYear\": 1977,\r\n"
				+ "        \"yearJoined\": 2016,\r\n" + "        \"directLinePhone\": \"(469) 250-5659\",\r\n"
				+ "        \"advisorCRD\": 4350375,\r\n"
				+ "        \"desktopImageUrl\": \"https://planners.edelmanfinancialengines.com/media/1051/efe_cherry_aaron_076_desktop_308x308.png\",\r\n"
				+ "        \"mobileImageUrl\": \"https://planners.edelmanfinancialengines.com/media/1509/efe_cherry_aaron_076_mobile_150x150.png\",\r\n"
				+ "        \"circleImageUrl\": \"https://planners.edelmanfinancialengines.com/media/1320/efe_cherry_aaron_076_circle_320x320.png\",\r\n"
				+ "        \"primaryOffice\": {\r\n" + "            \"id\": \"154\",\r\n"
				+ "            \"name\": \"Dallas II - Dallas\",\r\n" + "            \"city\": \"Dallas\",\r\n"
				+ "            \"state\": \"TX\",\r\n" + "            \"zip\": \"75240\"\r\n" + "        },\r\n"
				+ "        \"officesLocations\": [\r\n" + "            {\r\n" + "                \"id\": \"154\",\r\n"
				+ "                \"name\": \"Dallas II - Dallas\",\r\n" + "                \"city\": \"Dallas\",\r\n"
				+ "                \"state\": \"TX\",\r\n" + "                \"zip\": \"75240\"\r\n"
				+ "            }\r\n" + "        ],\r\n" + "        \"educationIndicator\": \"\",\r\n"
				+ "        \"education\": [\r\n" + "            {\r\n"
				+ "                \"degree\": \"Bachelor of Business Administration\",\r\n"
				+ "                \"major\": \"Marketing\",\r\n"
				+ "                \"university\": \"Stephen F. Austin State University\"\r\n" + "            }\r\n"
				+ "        ],\r\n" + "        \"taxPlanner\": false,\r\n" + "        \"advancedPlanning\": false,\r\n"
				+ "        \"estatePlanning\": false,\r\n" + "        \"insurancePlanning\": false,\r\n"
				+ "        \"investmentManagement\": false,\r\n" + "        \"employmentHistory\": [\r\n"
				+ "            {\r\n" + "                \"startDate\": \"2016\",\r\n"
				+ "                \"endDate\": \"2020\",\r\n"
				+ "                \"jobTitle\": \"Director, Financial Planning\",\r\n"
				+ "                \"companyName\": \"Edelman Financial Engines\",\r\n"
				+ "                \"current\": true\r\n" + "            }\r\n" + "        ],\r\n"
				+ "        \"certifications\": [\r\n" + "            {\r\n"
				+ "                \"abbreviation\": \"CFP®\",\r\n"
				+ "                \"legalComplianceDisclosure\": \"Important Information About the CERTIFIED FINANCIAL PLANNER™ (CFP®) Designation: Those with the CFP® certification have demonstrated competency in all areas of finance related to financial planning. Candidates for the CFP® mark must pass a certification exam administered by the Certified Financial Planner Board of Standards, Inc. that focuses on more than 100 topics of concern to the financial planning field. In addition to passing the CFP® exam, candidates must also complete qualifying work experience and agree to the CFP Board’s Standards and Policies, which include CFP® Certification Requirements, Code of Ethics and Standards of Conduct, Fitness Standards for Candidates and Professionals Eligible for Reinstatement, Procedural Rules, Guide to Use of the CFP® Certification Marks, and Privacy Policy. Finally, certified CFP® professionals must complete 30 hours of continuing education every two years. Certified Financial Planner Board of Standards Inc. owns the marks CFP® certification and CERTIFIED FINANCIAL PLANNER™ certification in the U.S., which it awards to individuals who successfully complete CFP Board's initial and ongoing certification requirements.\",\r\n"
				+ "                \"marketingDisclosure\": \"Those with the CFP® certification have demonstrated competency in all areas of finance related to financial planning. Candidates for the CFP® mark must pass a certification exam administered by the Certified Financial Planner Board of Standards, Inc. that focuses on more than 100 topics of concern to the financial planning field. In addition to passing the CFP® exam, candidates must also complete qualifying work experience and agree to the CFP Board’s Standards and Policies, which include CFP® Certification Requirements, Code of Ethics and Standards of Conduct, Fitness Standards for Candidates and Professionals Eligible for Reinstatement, Procedural Rules, Guide to Use of the CFP® Certification Marks, and Privacy Policy. Finally, certified CFP® professionals must complete 30 hours of continuing education every two years. Certified Financial Planner Board of Standards Inc. owns the marks CFP® certification and CERTIFIED FINANCIAL PLANNER™ certification in the U.S., which it awards to individuals who successfully complete CFP Board's initial and ongoing certification requirements.\",\r\n"
				+ "                \"name\": \"CERTIFIED FINANCIAL PLANNER™\"\r\n" + "            }\r\n"
				+ "        ],\r\n"
				+ "        \"bio\": \"As a financial planner since 2006, I enjoy serving families and business owners in the areas of investments, financial planning, estate planning, income distribution strategies, insurance planning and multi-generational wealth accumulation and transfer. Outside of work I enjoy spending time with my family, the outdoors and reading.\",\r\n"
				+ "        \"email\": \"acherry@EdelmanFinancialEngines.com\",\r\n"
				+ "        \"teamDistributionEmailAddress\": \"TeamAaronCherry@edelmanfinancialengines.com\",\r\n"
				+ "        \"yearStartedIndustry\": 2001,\r\n" + "        \"smartVestorProIndicator\": false,\r\n"
				+ "        \"interestsHobbies\": [\r\n" + "            \"Faith\",\r\n"
				+ "            \"Recreation\",\r\n" + "            \"Family\",\r\n" + "            \"Reading\"\r\n"
				+ "        ],\r\n" + "        \"funFacts\": \"\",\r\n" 
				+ "        \"honorAward\": [\r\n"
				+ "            {\r\n"
				+ "                \"dateOfAward\": \"2010-01-01\",\r\n"
				+ "                \"disclosure\": \"\",\r\n"
				+ "                \"honorAwardName\": \"Hewett-Kautz Fellowship\",\r\n"
				+ "                \"organization\": \"Carl H. Lindner College of Business - Department of Economics\"\r\n"
				+ "            }\r\n"
				+ "        ],\r\n"
				+ "        \"favoriteSport\": \"\",\r\n" 
				+ "        \"favoriteSportsTeam\": \"\",\r\n"
				+ "        \"favoriteLifeHack\": \"\",\r\n" 
				+ "        \"linkedInUrl\": \"\",\r\n"
				+ "        \"mostInspirationalMoment\": \"\",\r\n" + "        \"industryExams\": [\r\n"
				+ "            {\r\n" + "                \"examNameShort\": \"Series 66\",\r\n"
				+ "                \"examNameLong\": \"Series 66\"\r\n" + "            }\r\n" + "        ],\r\n"
				+ "        \"hasDisciplinaryInformation\": false,\r\n"
				+ "        \"disciplinaryInformationText\": \"\",\r\n"
				+ "        \"anyBusinessRelatedActivitiesCommissions\": false,\r\n"
				+ "        \"businessRelatedActivitiesCommissionsText\": \"\",\r\n"
				+ "        \"anyAdditionalCompensation\": false,\r\n"
				+ "        \"additionalCompensationText\": \"\",\r\n"
				+ "        \"efeUrl\": \"https://www.edelmanfinancialengines.com/financial-planners/advisor/?id=179\",\r\n"
				+ "        \"adv2bUrl\": \"https://planners.edelmanfinancialengines.com/api/v2/pdf/aaron-cherry/\",\r\n"
				+ "        \"htmlUrl\": \"https://planners.edelmanfinancialengines.com/planners/aaron-cherry/\",\r\n"
				+ "        \"lastUpdated\": \"2022-07-27T10:03:23.233Z\"\r\n" + "    }]";
		String firstName = "Johnathan";
		int id = 179;
		int educationCount = 1;

		String rootPath = "/content/dam/efe/plannerlocation/planners";
		String childPathPlanner = "/content/dam/efe/plannerlocation/planners/" + firstName + Integer.toString(id);
		String educationRootPath = "/content/dam/efe/plannerlocation/planners/Johnathan179/education";
		String industryExamRootPath = "/content/dam/efe/plannerlocation/planners/Johnathan179/industryExams";
		String certificationsRootPath = "/content/dam/efe/plannerlocation/planners/Johnathan179/certifications";
		String employmentHistoryRootPath = "/content/dam/efe/plannerlocation/planners/Johnathan179/employmentHistory";
		String officesLocationsRootPath = "/content/dam/efe/plannerlocation/planners/Johnathan179/officesLocations";
        String honorAwardrootPath = "/content/dam/efe/plannerlocation/planners/Johnathan179/honorAward";
		
		String fragmentName = PlannerLocationConstants.FRAGMENT_NAME_PREFIX + firstName
				+ PlannerLocationConstants.UNDERSCORE + Integer.toString(id);
		String primaryOfficeFragmentName = fragmentName + PlannerLocationConstants.PRIMARY_OFFICE_POSTFIX;
		String educationFragmentName = PlannerLocationConstants.EDUCATION_FRAGMENT_PREFIX
				+ Integer.toString(educationCount);
		String industryExamFragmentName = PlannerLocationConstants.INDUSTRY_EXAMS_PREFIX + "1";
		String certificationFragmentName = PlannerLocationConstants.CERTIFICATION_PREFIX + "1";
		String employmentHistoryFragmentName = PlannerLocationConstants.EMPLOYMENT_HISTORY_PREFIX + "1";
		String officesLocationsFragmentName = PlannerLocationConstants.OFFICE_LOCATIONS_PREFIX + "1";
		String honorAwardFragmentName = PlannerLocationConstants.HONOR_AWARD + "1";

		lenient().when(
				restService.getData(plannerApiService.getPlannersAPIEndpoint(), plannerApiService.getAuthHeader()))
				.thenReturn(jsonObjectPlanner);
        
		lenient().when(resourceResolver.getResource(PlannerLocationConstants.PLANNER_MODEL))
		.thenReturn(parentRsc);
		
		
		/*
		 * lenient().when(resourceResolver.getResource(PlannerLocationConstants.
		 * ROOT_FOLDER_PATH)) .thenReturn(parentResource);
		 */
		
		  lenient().when(FolderUtil.createFolder(PlannerLocationConstants.
		  ROOT_FOLDER_PATH, PlannerLocationConstants.PLANNER,
		  resourceResolver)).thenReturn(rootPath);
		 
		
			/*
			 * lenient().when(resourceResolver.getResource(rootPath))
			 * .thenReturn(rootPathParentResource);
			 */
		
		  lenient().when(FolderUtil.createFolder(rootPath, firstName + id,
		  resourceResolver)) .thenReturn(childPathPlanner);
		 
		
			/*
			 * lenient().when(resourceResolver.getResource(educationRootPath))
			 * .thenReturn(educationRootPathResource);
			 */
		
		
		  lenient().when(FolderUtil.createFolder(childPathPlanner,
		  PlannerLocationConstants.EDUCATION, resourceResolver))
		  .thenReturn(educationRootPath);
		 
		
			/*
			 * lenient().when(resourceResolver.getResource(industryExamRootPath))
			 * .thenReturn(industryExamRootPathResource);
			 */
		
		  lenient().when( FolderUtil.createFolder(childPathPlanner,
		  PlannerLocationConstants.INDUSTRY_EXAMS, resourceResolver))
		  .thenReturn(industryExamRootPath);
		 
		
			/*
			 * lenient().when(resourceResolver.getResource(certificationsRootPath))
			 * .thenReturn(certificationsRootPathResource);
			 */
		
		  lenient().when( FolderUtil.createFolder(childPathPlanner,
		  PlannerLocationConstants.CERTIFICATIONS, resourceResolver))
		  .thenReturn(certificationsRootPath);
		 
		
			/*
			 * lenient().when(resourceResolver.getResource(employmentHistoryRootPath))
			 * .thenReturn(employmentHistoryRootPathResource);
			 */
			
			  lenient().when(FolderUtil.createFolder(childPathPlanner,
			  PlannerLocationConstants.EMPLOYMENT_HISTORY,
			  resourceResolver)).thenReturn(employmentHistoryRootPath);
			 
		
				/*
				 * lenient().when(resourceResolver.getResource(officesLocationsRootPath))
				 * .thenReturn(officesLocationsRootPathResource);
				 */
		
				
				  lenient().when(FolderUtil.createFolder(childPathPlanner,
				  PlannerLocationConstants.OFFICE_LOCATIONS, resourceResolver))
				  .thenReturn(officesLocationsRootPath);
				 
		
		
		  lenient().when(FolderUtil.createFolder(childPathPlanner,
		  PlannerLocationConstants.HONOR_AWARD, resourceResolver))
		  .thenReturn(honorAwardrootPath);
		 

		
		lenient().when(resourceResolver.getResource(PlannerLocationConstants.PLANNER_MODEL))
				.thenReturn(templateOrModelRsc);

		lenient().when(resourceResolver.getResource(childPathPlanner)).thenReturn(parentRsc);
		lenient()
				.when(resourceResolver
						.getResource(childPathPlanner + PlannerLocationConstants.FORWARD_SLASH + fragmentName))
				.thenReturn(null);
		lenient()
				.when(resourceResolver.getResource(
						childPathPlanner + PlannerLocationConstants.FORWARD_SLASH + primaryOfficeFragmentName))
				.thenReturn(null);
		lenient().when(resourceResolver.getResource(childPathPlanner + PlannerLocationConstants.FORWARD_SLASH
				+ fragmentName + PlannerLocationConstants.MASTER_NODE)).thenReturn(plannerMasterResource);

		lenient().when(templateOrModelRsc.adaptTo(FragmentTemplate.class)).thenReturn(tpl);
		lenient().when(resourceResolver.hasChanges()).thenReturn(true);

		lenient().when(plannerMasterResource.adaptTo(Node.class)).thenReturn(plannerMasterNode);

		lenient().when(resourceResolver.getResource(PlannerLocationConstants.ADDRESS_MODEL))
				.thenReturn(templateOrModelRscPo);
		lenient().when(resourceResolver.getResource(PlannerLocationConstants.EDUCATION_MODEL))
				.thenReturn(templateOrModelRscEd);

		lenient().when(templateOrModelRscPo.adaptTo(Node.class)).thenReturn(plannerPrimaryOfficeNode);
		lenient().when(templateOrModelRscEd.adaptTo(Node.class)).thenReturn(plannerEducationNode);

		lenient()
				.when(resourceResolver.getResource(childPathPlanner + PlannerLocationConstants.FORWARD_SLASH
						+ primaryOfficeFragmentName + PlannerLocationConstants.MASTER_NODE))
				.thenReturn(plannerPrimaryOfficeResource);

		lenient()
				.when(resourceResolver.getResource(educationRootPath + PlannerLocationConstants.FORWARD_SLASH
						+ educationFragmentName + PlannerLocationConstants.MASTER_NODE))
				.thenReturn(plannerEducationResource);

		lenient().when(templateOrModelRscPo.adaptTo(FragmentTemplate.class)).thenReturn(tplPo);
		lenient().when(templateOrModelRscEd.adaptTo(FragmentTemplate.class)).thenReturn(tplEd);

		lenient()
				.when(resourceResolver.getResource(
						industryExamRootPath + PlannerLocationConstants.FORWARD_SLASH + industryExamFragmentName))
				.thenReturn(null);
		lenient().when(resourceResolver.getResource(PlannerLocationConstants.INDUSTRY_EXAM_MODEL))
				.thenReturn(templateOrModelIex);
		lenient().when(templateOrModelIex.adaptTo(FragmentTemplate.class)).thenReturn(tplIex);
		lenient()
				.when(resourceResolver.getResource(industryExamRootPath + PlannerLocationConstants.FORWARD_SLASH
						+ industryExamFragmentName + PlannerLocationConstants.MASTER_NODE))
				.thenReturn(plannerIndustryExamResource);

		lenient()
				.when(resourceResolver.getResource(
						certificationsRootPath + PlannerLocationConstants.FORWARD_SLASH + certificationFragmentName))
				.thenReturn(null);
		lenient().when(resourceResolver.getResource(PlannerLocationConstants.CERTIFICATION_MODEL))
				.thenReturn(templateOrModelRscCer);
		lenient().when(templateOrModelRscCer.adaptTo(FragmentTemplate.class)).thenReturn(tplCer);
		lenient()
				.when(resourceResolver.getResource(certificationsRootPath + PlannerLocationConstants.FORWARD_SLASH
						+ certificationFragmentName + PlannerLocationConstants.MASTER_NODE))
				.thenReturn(plannerCertificationResource);

		lenient().when(resourceResolver.getResource(
				employmentHistoryRootPath + PlannerLocationConstants.FORWARD_SLASH + employmentHistoryFragmentName))
				.thenReturn(null);
		lenient().when(resourceResolver.getResource(PlannerLocationConstants.EMPLOYMENT_HISTORY_MODEL))
				.thenReturn(templateOrModelRscEh);
		lenient().when(templateOrModelRscEh.adaptTo(FragmentTemplate.class)).thenReturn(tplEh);
		lenient()
				.when(resourceResolver.getResource(employmentHistoryRootPath + PlannerLocationConstants.FORWARD_SLASH
						+ employmentHistoryFragmentName + PlannerLocationConstants.MASTER_NODE))
				.thenReturn(plannerEmploymentHistoryResource);

		lenient().when(resourceResolver.getResource(officesLocationsRootPath + PlannerLocationConstants.FORWARD_SLASH + officesLocationsFragmentName))
				.thenReturn(null);
		lenient().when(resourceResolver.getResource(PlannerLocationConstants.ADDRESS_MODEL))
				.thenReturn(templateOrModelRscAdd);
		lenient().when(templateOrModelRscAdd.adaptTo(FragmentTemplate.class)).thenReturn(tplAdd);
		lenient().when(resourceResolver.getResource(officesLocationsRootPath + PlannerLocationConstants.FORWARD_SLASH
						+ officesLocationsFragmentName + PlannerLocationConstants.MASTER_NODE))
				.thenReturn(plannerOfficesLocationsResource);

		lenient().when(resourceResolver.getResource(honorAwardrootPath + PlannerLocationConstants.FORWARD_SLASH + honorAwardFragmentName))
		.thenReturn(null);
		lenient().when(resourceResolver.getResource(PlannerLocationConstants.HONOR_AWARD_MODEL))
		.thenReturn(templateOrModelRscHr);
		lenient().when(templateOrModelRscHr.adaptTo(FragmentTemplate.class)).thenReturn(tplHr);
		lenient().when(resourceResolver.getResource(honorAwardrootPath + PlannerLocationConstants.FORWARD_SLASH
				+ honorAwardFragmentName + PlannerLocationConstants.MASTER_NODE))
		.thenReturn(plannerHonorAwardResource);
		
		plannerModelServicesImpl.addDataToCFModelPlanner(resourceResolver);

		assertNotNull(plannerModelServicesImpl);
	}

}