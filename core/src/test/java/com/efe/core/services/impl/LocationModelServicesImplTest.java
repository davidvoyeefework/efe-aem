/*
 * package com.efe.core.services.impl;
 * 
 * import static org.junit.jupiter.api.Assertions.assertNotNull; import static
 * org.mockito.Mockito.lenient;
 * 
 * import javax.jcr.Node;
 * 
 * import org.apache.sling.api.resource.PersistenceException; import
 * org.apache.sling.api.resource.Resource; import
 * org.apache.sling.api.resource.ResourceResolver; import
 * org.apache.sling.testing.mock.sling.ResourceResolverType; import
 * org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test; import
 * org.junit.jupiter.api.extension.ExtendWith; import org.mockito.InjectMocks;
 * import org.mockito.Mock; import org.mockito.Mockito; import
 * org.mockito.junit.jupiter.MockitoExtension;
 * 
 * import com.adobe.cq.dam.cfm.ContentFragmentException; import
 * com.adobe.cq.dam.cfm.FragmentTemplate; import
 * com.efe.core.constants.PlannerLocationConstants; import
 * com.efe.core.services.PlannerApiService; import
 * com.efe.core.services.RestService; import com.efe.core.utils.FolderUtil;
 * import com.efe.core.utils.NodePropertyManagerUtil; import
 * com.google.gson.Gson;
 * 
 * import io.wcm.testing.mock.aem.junit5.AemContext; import
 * io.wcm.testing.mock.aem.junit5.AemContextExtension;
 * 
 *//**
	 * The Class LocationModelServicesImplTest.
	 *
	 */
/*
 * @ExtendWith({MockitoExtension.class, AemContextExtension.class}) class
 * LocationModelServicesImplTest {
 * 
 *//** The aem context. */
/*
 * private final AemContext aemContext = new
 * AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);
 * 
 *//** The aem context. */
/*
 * @Mock private ResourceResolver resourceResolver;
 * 
 *//** The aem context. */
/*
 * @Mock private RestService restService;
 * 
 *//** The aem context. */
/*
 * @Mock private PlannerApiService plannerApiService;
 * 
 *//** The gson. */
/*
 * @Mock private Gson gson;
 * 
 *//** The FragmentTemplate. */
/*
 * @Mock private FragmentTemplate tpl,tplBh;
 * 
 *//** The aem context. */
/*
 * @Mock private Resource
 * existingFragement,templateOrModelRscBh,locationBusinessHoursResource;
 * 
 *//** The aem context. *//*
							 * @Mock private Resource templateOrModelRsc;
							 * 
							 * @Mock private Resource parentRsc;
							 * 
							 * @Mock private Resource locationMasterResource;
							 * 
							 * @Mock private Node parentNode,locationMasterNode,locationBusinessHoursNode;
							 * 
							 * @InjectMocks LocationModelServicesImpl locationModelServicesImpl = new
							 * LocationModelServicesImpl();
							 * 
							 * @BeforeEach void setUp() {
							 * 
							 * //Mockito.reset(FolderUtil.class); // resourceResolver =
							 * mock(ResourceResolver.class); // restService = mock(RestService.class); //
							 * plannerApiService = mock(PlannerApiService.class); // gson =
							 * mock(Gson.class);
							 * 
							 * //aemContext.load().json("/com/efe/core/services/location.json", DEST_PATH);
							 * 
							 * aemContext.registerService(ResourceResolver.class, resourceResolver);
							 * aemContext.registerService(RestService.class, restService);
							 * aemContext.registerService(PlannerApiService.class, plannerApiService);
							 * aemContext.registerService(Gson.class, gson);
							 * aemContext.registerService(FragmentTemplate.class, tpl);
							 * aemContext.registerService(FragmentTemplate.class, tplBh);
							 * aemContext.registerService(Resource.class, existingFragement);
							 * aemContext.registerService(Resource.class, templateOrModelRsc);
							 * aemContext.registerService(Resource.class, parentRsc);
							 * aemContext.registerService(Resource.class, locationMasterResource);
							 * 
							 * aemContext.registerService(Resource.class, locationBusinessHoursResource);
							 * aemContext.registerService(Node.class, locationBusinessHoursNode);
							 * 
							 * aemContext.registerService(Resource.class, templateOrModelRscBh);
							 * 
							 * aemContext.registerService(Node.class, parentNode);
							 * aemContext.registerService(Node.class, locationMasterNode);
							 * Mockito.mockStatic(FolderUtil.class);
							 * //Mockito.mockStatic(NodePropertyManagerUtil.class);
							 * 
							 * 
							 * }
							 * 
							 * //@Test void testCreateFragmentLocation() throws ContentFragmentException,
							 * PersistenceException { // setup test data String jsonObjectLocation = "[\r\n"
							 * + "    {\r\n" + "        \"officeId\": \"28\",\r\n" +
							 * "        \"RD\": \"Matthew Ulrick\",\r\n" +
							 * "        \"officeName\": \"Birmingham\",\r\n" +
							 * "        \"externalName\": \"Birmingham\",\r\n" +
							 * "        \"desktopImage\": \"https://planners.edelmanfinancialengines.com/media/2511/birmingham-desktop-780x508.png\",\r\n"
							 * +
							 * "        \"mobileImage\": \"https://planners.edelmanfinancialengines.com/media/2358/birmingham-mobile-414x192.png\",\r\n"
							 * + "        \"emergencyClosure\": false,\r\n" +
							 * "        \"testLocation\": false,\r\n" +
							 * "        \"buildingComplexName\": \"\",\r\n" +
							 * "        \"address1\": \"3595 Grandview Parkway\",\r\n" +
							 * "        \"address2\": \"Suite 475\",\r\n" +
							 * "        \"city\": \"Birmingham\",\r\n" + "        \"state\": \"AL\",\r\n" +
							 * "        \"zip\": \"35243\",\r\n" + "        \"phone\": \"\",\r\n" +
							 * "        \"fax\": \"(866) 742-7222\",\r\n" +
							 * "        \"tollFree\": \"(833) PLAN-EFE\",\r\n" +
							 * "        \"latitude\": \"33.437489\",\r\n" +
							 * "        \"longitude\": \"-86.719533\",\r\n" +
							 * "        \"appointmentOnly\": false,\r\n" +
							 * "        \"googleReviewLink\": \"https://g.page/r/CfKJkFPHD7lFEBM/review\",\r\n"
							 * + "        \"planners\": [\r\n" + "            \"29\",\r\n" +
							 * "            \"358\"\r\n" + "        ],\r\n" +
							 * "        \"overrideCorporateOfficeHours\": false,\r\n" +
							 * "        \"businessHours\": [\r\n" + "            {\r\n" +
							 * "                \"day\": \"Monday\",\r\n" +
							 * "                \"openingHours\": \"8:30 AM\",\r\n" +
							 * "                \"closingHours\": \"5:00 PM\",\r\n" +
							 * "                \"isClosed\": false\r\n" + "            }\r\n" +
							 * "        ],\r\n" +
							 * "        \"lastUpdated\": \"2022-12-06T11:45:44.173Z\"\r\n" + "    }\r\n" +
							 * "  ]";
							 * 
							 * String officeName = "Birmingham"; String officeId = "28";
							 * 
							 * String rootPath = "/content/dam/efe/plannerlocation/locations"; String
							 * childPathLocation = "/content/dam/efe/plannerlocation/locations/" +
							 * officeName + officeId; String businessHoursRootPath = childPathLocation +
							 * "/businessHours";
							 * 
							 * String fragmentName = PlannerLocationConstants.FRAGMENT_NAME_PREFIX +
							 * officeName + PlannerLocationConstants.UNDERSCORE + officeId; String
							 * businessHoursFragmentName =
							 * PlannerLocationConstants.BUSINESS_HOURS_FRAGMENT_PREFIX + "1";
							 * 
							 * // mock method calls
							 * lenient().when(restService.getData(plannerApiService.getLocationsAPIEndpoint(
							 * ), plannerApiService.getAuthHeader())).thenReturn(jsonObjectLocation);
							 * 
							 * lenient().when(FolderUtil.createFolder(PlannerLocationConstants.
							 * ROOT_FOLDER_PATH, PlannerLocationConstants.LOCATIONS,
							 * resourceResolver)).thenReturn(rootPath);
							 * 
							 * lenient().when(FolderUtil.createFolder(rootPath, officeName + officeId ,
							 * resourceResolver)).thenReturn(childPathLocation);
							 * 
							 * 
							 * lenient().when(resourceResolver.getResource(PlannerLocationConstants.
							 * LOCATION_MODEL)).thenReturn(templateOrModelRsc);
							 * lenient().when(resourceResolver.getResource(childPathLocation)).thenReturn(
							 * parentRsc); lenient().when(resourceResolver.getResource(childPathLocation +
							 * PlannerLocationConstants.FORWARD_SLASH + fragmentName)).thenReturn(null);
							 * lenient().when(resourceResolver.getResource(childPathLocation+
							 * PlannerLocationConstants.FORWARD_SLASH + fragmentName +
							 * PlannerLocationConstants.MASTER_NODE)).thenReturn(locationMasterResource);
							 * 
							 * lenient().when(templateOrModelRsc.adaptTo(FragmentTemplate.class)).thenReturn
							 * (tpl); lenient().when(resourceResolver.hasChanges()).thenReturn(true);
							 * 
							 * lenient().when(locationMasterResource.adaptTo(Node.class)).thenReturn(
							 * locationMasterNode);
							 * 
							 * 
							 * lenient().when(FolderUtil.createFolder(childPathLocation,
							 * PlannerLocationConstants.BUSINESS_HOURS,
							 * resourceResolver)).thenReturn(businessHoursRootPath);
							 * 
							 * lenient().when(resourceResolver.getResource(businessHoursRootPath +
							 * PlannerLocationConstants.FORWARD_SLASH +
							 * businessHoursFragmentName)).thenReturn(null);
							 * lenient().when(resourceResolver.getResource(PlannerLocationConstants.
							 * BUSINESS_HOUR_MODEL)).thenReturn(templateOrModelRscBh);
							 * 
							 * lenient().when(templateOrModelRscBh.adaptTo(FragmentTemplate.class)).
							 * thenReturn(tplBh);
							 * 
							 * lenient().when(resourceResolver.getResource(businessHoursRootPath +
							 * PlannerLocationConstants.FORWARD_SLASH + businessHoursFragmentName +
							 * PlannerLocationConstants.MASTER_NODE)).thenReturn(
							 * locationBusinessHoursResource);
							 * 
							 * lenient().when(locationBusinessHoursResource.adaptTo(Node.class)).thenReturn(
							 * locationBusinessHoursNode);
							 * 
							 * 
							 * 
							 * // invoke method under test
							 * locationModelServicesImpl.createFragmentLocation(resourceResolver);
							 * 
							 * assertNotNull(locationModelServicesImpl); } }
							 */