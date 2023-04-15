
package com.efe.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.ValueFactory;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import com.day.cq.commons.jcr.JcrConstants;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.services.PlannerApiService;
import com.efe.core.services.RestService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class LocationModelServicesImplTest.
 *
 */
@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class LocationModelServicesImplTest {

	/** The aem context. */
	private final AemContext aemContext = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

	/** Mock ResourceResolver. */
	@Mock
	private ResourceResolver resourceResolver;

	@Mock
	private Session locationMasterNodeSession, locationBusinessHoursNodeSession;

	/** Mock ResourceResolverFactory. */
	@Mock
	private ResourceResolverFactory resourceResolverFactory;

	/** Mock RestService. */
	@Mock
	private RestService restService;

	/** Mock PlannerApiService. */
	@Mock
	private PlannerApiService plannerApiService;

	@Mock
	private ValueFactory valueFactory, valueFactoryBh;
	
	@Mock
	private ValueMap plannerProperty;

	/** Mock FragmentTemplate. */
	@Mock
	private FragmentTemplate tpl, tplBh;

	/** Mock Resource. */
	@Mock
	private Resource existingFragement, templateOrModelRscBh, locationBusinessHoursResource, templateOrModelRsc,
			parentRsc, locationMasterResource, parentResource, rootPathParentResource, cityPathLocationResource,
			statePathResource, rootPathStateResource, locationResource, locationPathResource, businessHoursResource,
			plannerRefResource;

	/** Mock Node. */
	@Mock
	private Node parentNode, locationMasterNode, locationBusinessHoursNode, parentPathNode, rootPathParentNode,
			cityPathNode, statePathNode, locationPathNode, stateFolderNode, cityFolderNode, businessHoursNode,
			businessHoursFolderNode;

	/** Mock LocationModelServicesImpl. */
	@InjectMocks
	private LocationModelServicesImpl locationModelServicesImpl = new LocationModelServicesImpl();

	@BeforeEach
	void setUp() throws LoginException {
		final Map<String, Object> subServiceUser = new ConcurrentHashMap<>();
		subServiceUser.put(ResourceResolverFactory.SUBSERVICE, "efe-service-user");

		aemContext.registerService(ResourceResolver.class, resourceResolver);
		aemContext.registerService(ResourceResolverFactory.class, resourceResolverFactory);
		lenient().when(resourceResolverFactory.getServiceResourceResolver(subServiceUser)).thenReturn(resourceResolver);

		aemContext.registerService(RestService.class, restService);
		aemContext.registerService(PlannerApiService.class, plannerApiService);
		aemContext.registerService(FragmentTemplate.class, tpl);
		aemContext.registerService(FragmentTemplate.class, tplBh);
		aemContext.registerService(Resource.class, existingFragement);
		aemContext.registerService(Resource.class, templateOrModelRsc);
		aemContext.registerService(Resource.class, parentRsc);
		aemContext.registerService(Resource.class, locationMasterResource);
		aemContext.registerService(Resource.class, parentResource);
		aemContext.registerService(Resource.class, locationBusinessHoursResource);
		aemContext.registerService(Node.class, locationBusinessHoursNode);
		aemContext.registerService(Node.class, businessHoursNode);

		aemContext.registerService(Resource.class, templateOrModelRscBh);
		aemContext.registerService(Resource.class, rootPathParentResource);
		aemContext.registerService(Resource.class, cityPathLocationResource);
		aemContext.registerService(Resource.class, rootPathStateResource);
		aemContext.registerService(Resource.class, locationResource);
		aemContext.registerService(Resource.class, locationPathResource);
		aemContext.registerService(Resource.class, businessHoursResource);
		aemContext.registerService(Resource.class, plannerRefResource);

		aemContext.registerService(Node.class, parentNode);
		aemContext.registerService(Node.class, locationMasterNode);
		aemContext.registerService(Node.class, parentPathNode);
		aemContext.registerService(Node.class, rootPathParentNode);
		aemContext.registerService(Node.class, cityPathNode);
		aemContext.registerService(Node.class, locationPathNode);
		aemContext.registerService(Node.class, stateFolderNode);
		aemContext.registerService(Node.class, cityFolderNode);
		aemContext.registerService(Node.class, businessHoursFolderNode);

		aemContext.registerService(Resource.class, statePathResource);
		aemContext.registerService(Node.class, statePathNode);

		aemContext.registerService(ValueFactory.class, valueFactory);
		aemContext.registerService(ValueFactory.class, valueFactoryBh);
		aemContext.registerService(Session.class, locationMasterNodeSession);
		aemContext.registerService(Session.class, locationBusinessHoursNodeSession); 
		aemContext.registerService(ValueMap.class, plannerProperty);
	}

	@Test
	void testCreateFragmentLocation() throws ContentFragmentException, PersistenceException,
			UnsupportedRepositoryOperationException, RepositoryException

	{
		// setup test data
		String jsonObjectLocation = "[\r\n" + "    {\r\n" + "        \"officeId\": \"28\",\r\n"
				+ "        \"RD\": \"Matthew Ulrick\",\r\n" + "        \"officeName\": \"Birmingham\",\r\n"
				+ "        \"externalName\": \"Birmingham\",\r\n"
				+ "        \"desktopImage\": \"https://planners.edelmanfinancialengines.com/media/2511/birmingham-desktop-780x508.png\",\r\n"
				+ "        \"mobileImage\": \"https://planners.edelmanfinancialengines.com/media/2358/birmingham-mobile-414x192.png\",\r\n"
				+ "        \"emergencyClosure\": false,\r\n" + "        \"testLocation\": false,\r\n"
				+ "        \"buildingComplexName\": \"\",\r\n" + "        \"address1\": \"3595 Grandview Parkway\",\r\n"
				+ "        \"address2\": \"Suite 475\",\r\n" + "        \"city\": \"Birmingham\",\r\n"
				+ "        \"state\": \"AL\",\r\n" + "        \"zip\": \"35243\",\r\n" + "        \"phone\": \"\",\r\n"
				+ "        \"fax\": \"(866) 742-7222\",\r\n" + "        \"tollFree\": \"(833) PLAN-EFE\",\r\n"
				+ "        \"latitude\": \"33.437489\",\r\n" + "        \"longitude\": \"-86.719533\",\r\n"
				+ "        \"appointmentOnly\": false,\r\n"
				+ "        \"googleReviewLink\": \"https://g.page/r/CfKJkFPHD7lFEBM/review\",\r\n"
				+ "        \"planners\": [\r\n" + "            \"29\"\r\n"
				+ "        ],\r\n" + "        \"overrideCorporateOfficeHours\": false,\r\n"
				+ "        \"businessHours\": [\r\n" + "            {\r\n" + "                \"day\": \"Monday\",\r\n"
				+ "                \"openingHours\": \"8:30 AM\",\r\n"
				+ "                \"closingHours\": \"5:00 PM\",\r\n" + "                \"isClosed\": false\r\n"
				+ "            }\r\n" + "        ],\r\n" + "        \"lastUpdated\": \"2022-12-06T11:45:44.173Z\"\r\n"
				+ "    }\r\n" + "  ]";

		String officeName = "Birmingham";
		String officeId = "28";
		String plannerId = "29";
		String folderNameLocation = "locations";

		String folderNameBusiness = "businessHours";
		String stateFolderName = "AL";
		String cityFolderName = "Birmingham";

		String rootPath = "/content/dam/efe/cf/plannerlocation/locations";
		String stateFolderPath = rootPath + PlannerLocationConstants.FORWARD_SLASH + stateFolderName;
		String cityPathLocation = stateFolderPath + PlannerLocationConstants.FORWARD_SLASH + cityFolderName;
		String businessHoursRootPath = cityPathLocation + PlannerLocationConstants.FORWARD_SLASH + folderNameBusiness;

		String refPath = PlannerLocationConstants.ROOT_FOLDER_PATH + PlannerLocationConstants.FORWARD_SLASH
				+ PlannerLocationConstants.PLANNER + PlannerLocationConstants.FORWARD_SLASH + plannerId;

		String fragmentName = PlannerLocationConstants.FRAGMENT_NAME_PREFIX + officeName
				+ PlannerLocationConstants.UNDERSCORE + officeId;
		String businessHoursFragmentName = PlannerLocationConstants.BUSINESS_HOURS_FRAGMENT_PREFIX + "1";

		// mock method calls
		lenient().when(
				restService.getData(plannerApiService.getLocationsAPIEndpoint(), plannerApiService.getAuthHeader()))
				.thenReturn(jsonObjectLocation);
		// root path plannerlocation
		lenient().when(resourceResolver.getResource(PlannerLocationConstants.ROOT_FOLDER_PATH))
				.thenReturn(parentResource);
		lenient().when(parentResource.getChild(folderNameLocation)).thenReturn(null);
		lenient().when(parentResource.adaptTo(Node.class)).thenReturn(parentPathNode);
		lenient().when(parentPathNode.addNode(folderNameLocation, JcrResourceConstants.NT_SLING_ORDERED_FOLDER))
				.thenReturn(rootPathParentNode);

		// root path location
		lenient().when(rootPathParentResource.getChild(stateFolderName)).thenReturn(null); // null check for state
																							// folder creation

		lenient().when(resourceResolver.getResource("/content/dam/efe/cf/plannerlocation/locations"))
				.thenReturn(locationPathResource);
		lenient().when(locationPathResource.getChild(stateFolderName)).thenReturn(null);
		lenient().when(locationPathResource.adaptTo(Node.class)).thenReturn(locationPathNode);
		lenient().when(locationPathNode.addNode(stateFolderName, JcrResourceConstants.NT_SLING_ORDERED_FOLDER))
				.thenReturn(stateFolderNode);

		// lenient().when(rootPathParentResource.adaptTo(Node.class)).thenReturn(rootPathParentNode);

		// city folder creation
		lenient().when(resourceResolver.getResource("/content/dam/efe/cf/plannerlocation/locations/AL"))
				.thenReturn(statePathResource);
		lenient().when(statePathResource.getChild(cityFolderName)).thenReturn(null);
		lenient().when(statePathResource.adaptTo(Node.class)).thenReturn(statePathNode);
		lenient().when(statePathNode.addNode(cityFolderName, JcrResourceConstants.NT_SLING_ORDERED_FOLDER))
				.thenReturn(cityFolderNode);

		// lenient().when(rootPathParentResource.adaptTo(Node.class)).thenReturn(rootPathParentNode);

		lenient().when(resourceResolver.getResource(PlannerLocationConstants.LOCATION_MODEL))
				.thenReturn(templateOrModelRsc);
		lenient()
				.when(resourceResolver
						.getResource(cityPathLocation + PlannerLocationConstants.FORWARD_SLASH + fragmentName))
				.thenReturn(null);
		lenient().when(resourceResolver.getResource(cityPathLocation + PlannerLocationConstants.FORWARD_SLASH
				+ fragmentName + PlannerLocationConstants.MASTER_NODE)).thenReturn(locationMasterResource);

		lenient().when(templateOrModelRsc.adaptTo(FragmentTemplate.class)).thenReturn(tpl);
		lenient().when(resourceResolver.hasChanges()).thenReturn(true);

		lenient().when(locationMasterResource.adaptTo(Node.class)).thenReturn(locationMasterNode);
		lenient().when(locationMasterNode.getSession()).thenReturn(locationMasterNodeSession);
		lenient().when(locationMasterNode.getSession().getValueFactory()).thenReturn(valueFactory);

		lenient()
				.when(resourceResolver.getResource(
						businessHoursRootPath + PlannerLocationConstants.FORWARD_SLASH + businessHoursFragmentName))
				.thenReturn(null);
		lenient().when(resourceResolver.getResource(PlannerLocationConstants.BUSINESS_HOUR_MODEL))
				.thenReturn(templateOrModelRscBh);

		lenient().when(templateOrModelRscBh.adaptTo(FragmentTemplate.class)).thenReturn(tplBh);

		lenient()
				.when(resourceResolver.getResource(businessHoursRootPath + PlannerLocationConstants.FORWARD_SLASH
						+ businessHoursFragmentName + PlannerLocationConstants.MASTER_NODE))
				.thenReturn(locationBusinessHoursResource);

		lenient().when(locationBusinessHoursResource.adaptTo(Node.class)).thenReturn(locationBusinessHoursNode);
		lenient().when(locationBusinessHoursNode.getSession()).thenReturn(locationBusinessHoursNodeSession);
		lenient().when(locationBusinessHoursNode.getSession().getValueFactory()).thenReturn(valueFactoryBh);

		lenient().when(resourceResolver.getResource("/content/dam/efe/cf/plannerlocation/locations/AL/Birmingham"))
				.thenReturn(businessHoursResource);
		lenient().when(businessHoursResource.getChild(folderNameBusiness)).thenReturn(null);
		lenient().when(businessHoursResource.adaptTo(Node.class)).thenReturn(businessHoursNode);
		lenient().when(businessHoursNode.addNode(folderNameBusiness, JcrResourceConstants.NT_SLING_ORDERED_FOLDER))
				.thenReturn(businessHoursFolderNode);

		lenient().when(businessHoursResource.getChild(folderNameBusiness)).thenReturn(null);

		lenient().when(resourceResolver.getResource(refPath)).thenReturn(plannerRefResource);

		lenient().when(plannerRefResource.adaptTo(ValueMap.class)).thenReturn(plannerProperty);
		
		lenient().when(plannerProperty.get(JcrConstants.JCR_TITLE, String.class)).thenReturn("abcd");


		// invoke method under test
		locationModelServicesImpl.addDataToCFModelLocation(resourceResolver);

		assertNotNull(locationModelServicesImpl);
	}

}
