
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
import com.efe.core.utils.FolderUtil;
import com.efe.core.utils.NodePropertyManagerUtil;

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
	private Session locationMasterNodeSession,locationBusinessHoursNodeSession;
	
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
	private ValueFactory valueFactory,valueFactoryBh;

	/** Mock FragmentTemplate. */
	@Mock
	private FragmentTemplate tpl, tplBh;

	/** Mock Resource. */
	@Mock
	private Resource existingFragement, templateOrModelRscBh, locationBusinessHoursResource, templateOrModelRsc,
			parentRsc, locationMasterResource,parentResource,rootPathParentResource,childPathLocationResource;

	/** Mock Node. */
	@Mock
	private Node parentNode, locationMasterNode, locationBusinessHoursNode;

	/** Mock LocationModelServicesImpl. */
	@InjectMocks
	private LocationModelServicesImpl locationModelServicesImpl = new LocationModelServicesImpl();

	/** mockStatic for FolderUtil. */
	//private MockedStatic<FolderUtil> mockStatic1;

	/** mockStatic for NodePropertyManagerUtil. */
	//private MockedStatic<NodePropertyManagerUtil> mockStatic2;

	@BeforeEach
	void setUp() throws LoginException {
		final Map<String, Object> subServiceUser = new ConcurrentHashMap<>();
		subServiceUser.put(ResourceResolverFactory.SUBSERVICE,"efe-service-user");

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

		aemContext.registerService(Resource.class, templateOrModelRscBh); 
		aemContext.registerService(Resource.class, rootPathParentResource);
		aemContext.registerService(Resource.class, childPathLocationResource);

		aemContext.registerService(Node.class, parentNode);
		aemContext.registerService(Node.class, locationMasterNode);

		aemContext.registerService(ValueFactory.class, valueFactory);
		aemContext.registerService(ValueFactory.class, valueFactoryBh);
		aemContext.registerService(Session.class, locationMasterNodeSession);
		aemContext.registerService(Session.class, locationBusinessHoursNodeSession);
		
		
		
		
		//mockStatic1 = Mockito.mockStatic(FolderUtil.class);
		//mockStatic2 = Mockito.mockStatic(NodePropertyManagerUtil.class);
	}

	@AfterEach
	void close() {
		//mockStatic1.close();
		//mockStatic2.close();
	}

	@Test
	void testCreateFragmentLocation() throws ContentFragmentException, PersistenceException, UnsupportedRepositoryOperationException, RepositoryException

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
				+ "        \"planners\": [\r\n" + "            \"29\",\r\n" + "            \"358\"\r\n"
				+ "        ],\r\n" + "        \"overrideCorporateOfficeHours\": false,\r\n"
				+ "        \"businessHours\": [\r\n" + "            {\r\n" + "                \"day\": \"Monday\",\r\n"
				+ "                \"openingHours\": \"8:30 AM\",\r\n"
				+ "                \"closingHours\": \"5:00 PM\",\r\n" + "                \"isClosed\": false\r\n"
				+ "            }\r\n" + "        ],\r\n" + "        \"lastUpdated\": \"2022-12-06T11:45:44.173Z\"\r\n"
				+ "    }\r\n" + "  ]";

		String officeName = "Birmingham";
		String officeId = "28";

		String rootPath = "/content/dam/efe/plannerlocation/locations";
		String childPathLocation = "/content/dam/efe/plannerlocation/locations/" + officeName + officeId;
		String businessHoursRootPath = childPathLocation + "/businessHours";

		String fragmentName = PlannerLocationConstants.FRAGMENT_NAME_PREFIX + officeName
				+ PlannerLocationConstants.UNDERSCORE + officeId;
		String businessHoursFragmentName = PlannerLocationConstants.BUSINESS_HOURS_FRAGMENT_PREFIX + "1";

		// mock method calls
		
		lenient().when(
				restService.getData(plannerApiService.getLocationsAPIEndpoint(), plannerApiService.getAuthHeader()))
				.thenReturn(jsonObjectLocation);

		lenient().when(resourceResolver.getResource(PlannerLocationConstants.ROOT_FOLDER_PATH))
		.thenReturn(parentResource);
		
		/*
		 * lenient().when(FolderUtil.createFolder(PlannerLocationConstants.
		 * ROOT_FOLDER_PATH, PlannerLocationConstants.LOCATIONS,
		 * resourceResolver)).thenReturn(rootPath);
		 */
		lenient().when(resourceResolver.getResource(rootPath))
		.thenReturn(rootPathParentResource);
		
		/*
		 * lenient().when(FolderUtil.createFolder(rootPath, officeName + officeId,
		 * resourceResolver)) .thenReturn(childPathLocation);
		 */
         
		lenient().when(resourceResolver.getResource(childPathLocation))
		.thenReturn(childPathLocationResource);
		
		/*
		 * lenient().when( FolderUtil.createFolder(childPathLocation,
		 * PlannerLocationConstants.BUSINESS_HOURS, resourceResolver))
		 * .thenReturn(businessHoursRootPath);
		 */

		lenient().when(resourceResolver.getResource(PlannerLocationConstants.LOCATION_MODEL))
				.thenReturn(templateOrModelRsc);
		lenient().when(resourceResolver.getResource(childPathLocation)).thenReturn(parentRsc);
		lenient()
				.when(resourceResolver
						.getResource(childPathLocation + PlannerLocationConstants.FORWARD_SLASH + fragmentName))
				.thenReturn(null);
		lenient().when(resourceResolver.getResource(childPathLocation + PlannerLocationConstants.FORWARD_SLASH
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

		// invoke method under test
		locationModelServicesImpl.addDataToCFModelLocation();

		assertNotNull(locationModelServicesImpl);
	}

}
