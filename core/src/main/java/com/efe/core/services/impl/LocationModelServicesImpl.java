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
import com.efe.core.services.LocationModelServices;
import com.efe.core.services.PlannerApiService;
import com.efe.core.services.RestService;
import com.efe.core.utils.ResourceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.adobe.cq.dam.cfm.ContentFragmentException;

/**
 * The Class LocationModelServicesImpl
 *
 */
@Component(service = LocationModelServices.class, immediate = true)
public class LocationModelServicesImpl implements LocationModelServices {

	/**
	 * Root Folder Path
	 */
	private static final String ROOT_FOLDER_PATH = "/content/dam/efe/plannerlocation";

	/**
	 * Location Folder Name
	 */
	private static final String LOCATIONS = "locations";

	/**
	 * Office Id
	 */
	private static final String OFFICE_ID = "officeId";

	/**
	 * RD in model
	 */
	private static final String RD_FROM_JSON = "RD";

	/**
	 * RD in model
	 */
	private static final String RD_IN_MODEL = "rd";

	/**
	 * Office Id
	 */
	private static final String OFFICE_NAME = "officeName";

	/**
	 * Sling Ordered Folder
	 */
	private static final String SLING_ORDERED_FOLDER = "sling:OrderedFolder";

	/**
	 * Fragment Name Prefix
	 */
	private static final String FRAGMENT_Name_PREFIX = "fragment_";

	/**
	 * Underscore
	 */
	private static final String UNDERSCORE = "_";

	/**
	 * Forward Slash
	 */
	private static final String FORWARD_SLASH = "/";

	/**
	 * Jcr Title
	 */
	private static final String JCR_TITLE_LOCATION = "location fragment";

	/**
	 * Forward Slash
	 */
	private static final String LOCATION_MODEL = "/conf/efe/settings/dam/cfm/models/location";

	/**
	 * Forward Slash
	 */
	private static final String MASTER_NODE = "/jcr:content/data/master";

	/**
	 * Forward Slash
	 */
	private static final String EMERGENCY_CLOSURE = "emergencyClosure";

	/**
	 * Test Location
	 */
	private static final String TEST_LOCATION = "testLocation";

	/**
	 * Appointment Only
	 */
	private static final String APPOINTMENT_ONLY = "appointmentOnly";

	/**
	 * Override corporate office hours
	 */
	private static final String OVERRIDE_CORPORATE_OFFICE_HOURS = "overrideCorporateOfficeHours";

	/**
	 * Override corporate office hours
	 */
	private static final String EXTERNAL_NAME = "externalName";

	/**
	 * Desktop Name
	 */
	private static final String DESKTOP_NAME = "desktopImage";

	/**
	 * Mobile Image
	 */
	private static final String MOBILE_IMAGE = "mobileImage";

	/**
	 * Building Complex Name
	 */
	private static final String Building_COMPLEX_NAME = "buildingComplexName";

	/**
	 * Address 1
	 */
	private static final String ADDRESS_1 = "address1";

	/**
	 * Address 1
	 */
	private static final String ADDRESS_2 = "address2";

	/**
	 * City
	 */
	private static final String CITY = "city";

	/**
	 * State
	 */
	private static final String STATE = "state";

	/**
	 * State
	 */
	private static final String ZIP = "zip";

	/**
	 * State
	 */
	private static final String PHONE = "phone";

	/**
	 * Toll Free
	 */
	private static final String TOLL_FREE = "tollFree";

	/**
	 * Latitude
	 */
	private static final String LATITUDE = "latitude";

	/**
	 * Longitude
	 */
	private static final String LONGITUTE = "longitude";

	/**
	 * Longitude
	 */
	private static final String GOOGLE_REVIEW_LINK = "googleReviewLink";

	/**
	 * Longitude
	 */
	private static final String LAST_UPDATED = "lastUpdated";

	/**
	 * Longitude
	 */
	private static final String PLANNERS = "planners";

	/**
	 * Longitude
	 */
	private static final String BUSINESS_HOURS = "businessHours";

	/**
	 * Longitude
	 */
	private static final String BUSINESS_HOURS_FRAGMENT_PREFIX = "businessHoursfragment_";

	/**
	 * Longitude
	 */
	private static final String BUSINESS_HOUR_MODEL = "/conf/efe/settings/dam/cfm/models/businesshour";

	/**
	 * Longitude
	 */
	private static final String BUSINESS_HOUR_MODEL_DESCRIPTION = "businessHours fragment";

	/**
	 * Longitude
	 */
	private static final String CLOSING_HOURS = "closingHours";

	/**
	 * Longitude
	 */
	private static final String DAY = "day";

	/**
	 * Longitude
	 */
	private static final String OPENING_HOURS = "openingHours";

	/**
	 * Longitude
	 */
	private static final String IS_CLOSED = "isClosed";

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
	 * 
	 * @param parentPath
	 * @param folderName
	 * @param resourceResolver
	 * @return
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
	 * This method is used to create and update Fragments for Locations
	 */
	public void createFragmentLocation(ResourceResolver resourceResolver) {
		String jsonObjectLocation = restService.getData(plannerApiService.getLocationsAPIEndpoint(),plannerApiService.getAuthHeader());
		Gson gson = new Gson();
		JsonElement jsonElement = gson.fromJson(jsonObjectLocation, JsonElement.class);
		JsonArray jsonArray = jsonElement.getAsJsonArray();

		String rootPath = createFolder(ROOT_FOLDER_PATH, LOCATIONS, resourceResolver);
		for (Object obj : jsonArray) {			
			JsonObject jsonObj = (JsonObject) obj;
			String officeId = jsonObj.get(OFFICE_ID).toString();
			String officeName = jsonObj.get(OFFICE_NAME).toString();
			String childPathLocation = createFolder(rootPath, officeName + officeId, resourceResolver);
			String fragmentName = FRAGMENT_Name_PREFIX + officeName + UNDERSCORE + officeId;

			Resource existingFragement = resourceResolver.getResource(childPathLocation + FORWARD_SLASH + fragmentName);
			if (existingFragement == null) {
				Resource templateOrModelRsc = resourceResolver.getResource(LOCATION_MODEL);
				Resource parentRsc = resourceResolver.getResource(childPathLocation);
				FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
				try {
					tpl.createFragment(parentRsc, fragmentName, JCR_TITLE_LOCATION);
					if (resourceResolver.hasChanges()) {
						resourceResolver.commit();
					}
				} catch (ContentFragmentException e) {
					LOGGER.error("ContentFragmentException:", e);
				} catch (PersistenceException e) {
					LOGGER.error("PersistenceException:", e);
				}
			}
			Resource locationMasterResource = resourceResolver.getResource(childPathLocation + FORWARD_SLASH + fragmentName + MASTER_NODE);
			Node locationMasterNode = locationMasterResource.adaptTo(Node.class);

			try {

				if (jsonObj.get(EMERGENCY_CLOSURE) != null && !jsonObj.get(EMERGENCY_CLOSURE).isJsonNull()) {
					locationMasterNode.setProperty(EMERGENCY_CLOSURE, jsonObj.get(EMERGENCY_CLOSURE).getAsBoolean());
				}
				if (jsonObj.get(TEST_LOCATION) != null && !jsonObj.get(TEST_LOCATION).isJsonNull()) {
					locationMasterNode.setProperty(TEST_LOCATION, jsonObj.get(TEST_LOCATION).getAsBoolean());
				}
				if (jsonObj.get(APPOINTMENT_ONLY) != null && !jsonObj.get(APPOINTMENT_ONLY).isJsonNull()) {
					locationMasterNode.setProperty(APPOINTMENT_ONLY, jsonObj.get(APPOINTMENT_ONLY).getAsBoolean());
				}
				if (jsonObj.get(OVERRIDE_CORPORATE_OFFICE_HOURS) != null
						&& !jsonObj.get(OVERRIDE_CORPORATE_OFFICE_HOURS).isJsonNull()) {
					locationMasterNode.setProperty(OVERRIDE_CORPORATE_OFFICE_HOURS,
							jsonObj.get(OVERRIDE_CORPORATE_OFFICE_HOURS).getAsBoolean());
				}
				if (jsonObj.get(OFFICE_ID) != null && !jsonObj.get(OFFICE_ID).isJsonNull()) {
					locationMasterNode.setProperty(OFFICE_ID, jsonObj.get(OFFICE_ID).toString());
				}
				if (jsonObj.get(RD_FROM_JSON) != null && !jsonObj.get(RD_FROM_JSON).isJsonNull()) {
					locationMasterNode.setProperty(RD_IN_MODEL, jsonObj.get(RD_FROM_JSON).toString());
				}
				if (jsonObj.get(OFFICE_NAME) != null && !jsonObj.get(OFFICE_NAME).isJsonNull()) {
					locationMasterNode.setProperty(OFFICE_NAME, jsonObj.get(OFFICE_NAME).toString());
				}

				if (jsonObj.get(EXTERNAL_NAME) != null && !jsonObj.get(EXTERNAL_NAME).isJsonNull()) {
					locationMasterNode.setProperty(EXTERNAL_NAME, jsonObj.get(EXTERNAL_NAME).toString());
				}
				if (jsonObj.get(DESKTOP_NAME) != null && !jsonObj.get(DESKTOP_NAME).isJsonNull()) {
					locationMasterNode.setProperty(DESKTOP_NAME, jsonObj.get(DESKTOP_NAME).toString());
				}
				if (jsonObj.get(MOBILE_IMAGE) != null && !jsonObj.get(MOBILE_IMAGE).isJsonNull()) {
					locationMasterNode.setProperty(MOBILE_IMAGE, jsonObj.get(MOBILE_IMAGE).toString());
				}
				if (jsonObj.get(Building_COMPLEX_NAME) != null && !jsonObj.get(Building_COMPLEX_NAME).isJsonNull()) {
					locationMasterNode.setProperty(Building_COMPLEX_NAME,
							jsonObj.get(Building_COMPLEX_NAME).toString());
				}
				if (jsonObj.get(ADDRESS_1) != null && !jsonObj.get(ADDRESS_1).isJsonNull()) {
					locationMasterNode.setProperty(ADDRESS_1, jsonObj.get(ADDRESS_1).toString());
				}
				if (jsonObj.get(ADDRESS_2) != null && !jsonObj.get(ADDRESS_2).isJsonNull()) {
					locationMasterNode.setProperty(ADDRESS_2, jsonObj.get(ADDRESS_2).toString());
				}
				if (jsonObj.get(CITY) != null && !jsonObj.get(CITY).isJsonNull()) {
					locationMasterNode.setProperty(CITY, jsonObj.get(CITY).toString());
				}
				if (jsonObj.get(STATE) != null && !jsonObj.get(STATE).isJsonNull()) {
					locationMasterNode.setProperty(STATE, jsonObj.get(STATE).toString());
				}
				if (jsonObj.get(ZIP) != null && !jsonObj.get(ZIP).isJsonNull()) {
					locationMasterNode.setProperty(ZIP, jsonObj.get(ZIP).toString());
				}
				if (jsonObj.get(PHONE) != null && !jsonObj.get(PHONE).isJsonNull()) {
					locationMasterNode.setProperty(PHONE, jsonObj.get(PHONE).toString());
				}
				if (jsonObj.get(TOLL_FREE) != null && !jsonObj.get(TOLL_FREE).isJsonNull()) {
					locationMasterNode.setProperty(TOLL_FREE, jsonObj.get(TOLL_FREE).toString());
				}
				if (jsonObj.get(LATITUDE) != null && !jsonObj.get(LATITUDE).isJsonNull()) {
					locationMasterNode.setProperty(LATITUDE, jsonObj.get(LATITUDE).toString());
				}
				if (jsonObj.get(LONGITUTE) != null && !jsonObj.get(LONGITUTE).isJsonNull()) {
					locationMasterNode.setProperty(LONGITUTE, jsonObj.get(LONGITUTE).toString());
				}
				if (jsonObj.get(GOOGLE_REVIEW_LINK) != null && !jsonObj.get(GOOGLE_REVIEW_LINK).isJsonNull()) {
					locationMasterNode.setProperty(GOOGLE_REVIEW_LINK, jsonObj.get(GOOGLE_REVIEW_LINK).toString());
				}
				if (jsonObj.get(LAST_UPDATED) != null && !jsonObj.get(LAST_UPDATED).isJsonNull()) {
					locationMasterNode.setProperty(LAST_UPDATED, jsonObj.get(LAST_UPDATED).toString());
				}
				JsonArray plannersArr = jsonObj.get(PLANNERS).getAsJsonArray();
				String[] plannersStringArray = new String[plannersArr.size()];
				if (plannersArr != null) {
					for (int i = 0; i < plannersArr.size(); i++) {
						plannersStringArray[i] = plannersArr.get(i).getAsString();
					}
					if (plannersStringArray != null) {

						locationMasterNode.setProperty(PLANNERS, plannersStringArray);
					}
				}
				createBusinessHoursFragmentLocation(childPathLocation, jsonObj, resourceResolver);

			} catch (Exception e) {
				LOGGER.error("Exception occured.");
			}		
		}
	}


	/**
	 * This method is used to create and update Fragments for Business Hours
	 */
	public void createBusinessHoursFragmentLocation(String childPathLocation, JsonObject jsonObj,ResourceResolver resourceResolver) {
		try {
			JsonArray businessHoursArr = jsonObj.get(BUSINESS_HOURS).getAsJsonArray();
			String businessHoursRootPath = createFolder(childPathLocation, BUSINESS_HOURS, resourceResolver);
			int businessHoursCount = 1;
			for (Object businessHoursObj : businessHoursArr) {

				JsonObject businessHours = (JsonObject) businessHoursObj;

				String businessHoursFragmentName = BUSINESS_HOURS_FRAGMENT_PREFIX
						+ Integer.toString(businessHoursCount);
				Resource businessHoursExistingFragement = resourceResolver
						.getResource(businessHoursRootPath + FORWARD_SLASH + businessHoursFragmentName);
				if (businessHoursExistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver.getResource(BUSINESS_HOUR_MODEL);
					Resource parentRsc = resourceResolver.getResource(businessHoursRootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
					try {
						tpl.createFragment(parentRsc, businessHoursFragmentName, BUSINESS_HOUR_MODEL_DESCRIPTION);
						if (resourceResolver.hasChanges()) {
							resourceResolver.commit();
						}
					} catch (ContentFragmentException e) {
						LOGGER.error("ContentFragmentException:", e);
					} catch (PersistenceException e) {
						LOGGER.error("PersistenceException:", e);
					}
				}
				Resource locationBusinessHoursResource = resourceResolver
						.getResource(businessHoursRootPath + FORWARD_SLASH + businessHoursFragmentName + MASTER_NODE);
				Node locationBusinessHoursNode = locationBusinessHoursResource.adaptTo(Node.class);

				if (businessHours.get(CLOSING_HOURS) != null && !businessHours.get(CLOSING_HOURS).isJsonNull()) {

					locationBusinessHoursNode.setProperty(CLOSING_HOURS,
							businessHours.get(CLOSING_HOURS).getAsString());
				}
				if (businessHours.get(DAY) != null && !businessHours.get(DAY).isJsonNull()) {

					locationBusinessHoursNode.setProperty(DAY, businessHours.get(DAY).getAsString());
				}
				if (businessHours.get(OPENING_HOURS) != null && !businessHours.get(OPENING_HOURS).isJsonNull()) {

					locationBusinessHoursNode.setProperty(OPENING_HOURS,
							businessHours.get(OPENING_HOURS).getAsString());
				}
				if (businessHours.get(IS_CLOSED) != null && !businessHours.get(IS_CLOSED).isJsonNull()) {

					locationBusinessHoursNode.setProperty(IS_CLOSED, businessHours.get(IS_CLOSED).getAsBoolean());
				}
				businessHoursCount++;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured.");
		}
	}

	/**
	 * This method is called by Scheduler to create fragment and adds data to that
	 * fragment
	 */
	@Override
	public void addDataToCFModelLocation() {
		ResourceResolver resourceResolver = ResourceUtil.getServiceResourceResolver(resourceResolverFactory);
		createFragmentLocation(resourceResolver);
		LOGGER.info("Content Fragment created for Location");
	}

}
