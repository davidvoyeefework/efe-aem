package com.efe.core.services.impl;

import java.util.List;
import java.util.Objects;

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
import com.day.cq.commons.jcr.JcrConstants;
import com.efe.core.bean.BusinessHours;
import com.efe.core.bean.LocationResponse;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.services.LocationModelServices;
import com.efe.core.services.EfeService;
import com.efe.core.services.RestService;
import com.efe.core.utils.FolderUtil;
import com.efe.core.utils.NodePropertyManagerUtil;
import com.efe.core.utils.ResourceUtil;
import com.google.gson.Gson;
import com.day.cq.dam.commons.util.DamUtil;

/**
 * The Class LocationModelServicesImpl
 *
 */
@Component(service = LocationModelServices.class, immediate = true)
public class LocationModelServicesImpl implements LocationModelServices {

	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LocationModelServicesImpl.class);

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
	 * EfeService injected
	 */
	@Reference
	private EfeService efeService;

	/**
	 * This method is used to create Fragment for Location
	 */
	private void createFragment(String modelResource, String rootPath, String fragmentName, String fragmentResource,
								ResourceResolver resourceResolver) {
		Resource templateOrModelRsc = resourceResolver.getResource(modelResource);
		FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
		if (Objects.nonNull(tpl)) {
			try {
				Resource parentRsc = resourceResolver.getResource(rootPath);
				tpl.createFragment(parentRsc, fragmentName, fragmentResource);
				sessionSave(resourceResolver);
			} catch (ContentFragmentException e) {
				LOGGER.error("ContentFragmentException:", e);
			}
		}
	}

	private void sessionSave(ResourceResolver resourceResolver) {
		try {
			if(resourceResolver.hasChanges()) {
				resourceResolver.commit();
			}
		} catch (PersistenceException e) {
			LOGGER.error("PersistenceException:", e);
		}
	}

	/**
	 * This method is used to create Fragments for Locations
	 */
	private void createFragmentLocation(ResourceResolver resourceResolver) {
		String jsonObjectLocation = restService.getData(efeService.getLocationsAPIEndpoint(),
				efeService.getAuthHeader());
		Gson gson = new Gson();
		LocationResponse[] jsonElement = gson.fromJson(jsonObjectLocation, LocationResponse[].class);
		String rootPath = FolderUtil.createFolder(PlannerLocationConstants.ROOT_FOLDER_PATH,
				PlannerLocationConstants.LOCATIONS, PlannerLocationConstants.LOCATIONS, resourceResolver);
		for (LocationResponse jsonObj : jsonElement) {
			String officeId = jsonObj.getOfficeId();
			String stateFolderName = jsonObj.getState().toLowerCase();
			String cityFolderName = jsonObj.getExternalName();
			String officeName = jsonObj.getOfficeName().toLowerCase();
			String stateFolderPath = rootPath + PlannerLocationConstants.FORWARD_SLASH + stateFolderName;
			Boolean testLocation = jsonObj.isTestLocation();

			//validation of json data
			if (stateFolderName.trim().isEmpty() || cityFolderName.trim().isEmpty() || testLocation) {
				continue;
			}
			Resource childResource = resourceResolver.getResource(stateFolderPath);
			if (Objects.isNull(childResource)) {
				stateFolderPath = FolderUtil.createFolder(rootPath, stateFolderName, stateFolderName, resourceResolver);
			}

			String childPathLocation = FolderUtil.createFolder(stateFolderPath, DamUtil.getSanitizedFolderName(cityFolderName), cityFolderName,
					resourceResolver);

			// create business hrs folder first then create fragments as a dependency on
			// business hours
			createBusinessHoursFragmentLocation(childPathLocation, jsonObj, resourceResolver);

			// start creating the fragment folder
			String fragmentName = PlannerLocationConstants.FRAGMENT_NAME_PREFIX + officeName
					+ PlannerLocationConstants.UNDERSCORE + officeId;

			Resource existingFragement = resourceResolver
					.getResource(childPathLocation + PlannerLocationConstants.FORWARD_SLASH + fragmentName);
			if (Objects.isNull(existingFragement)) {

				createFragment(PlannerLocationConstants.LOCATION_MODEL, childPathLocation, fragmentName,
						PlannerLocationConstants.JCR_TITLE_LOCATION, resourceResolver);
				Resource locationMasterResource = resourceResolver.getResource(childPathLocation
						+ PlannerLocationConstants.FORWARD_SLASH + fragmentName + PlannerLocationConstants.MASTER_NODE);
				Node locationMasterNode = locationMasterResource.adaptTo(Node.class);
				updateLocationFragmentProperties(resourceResolver, locationMasterNode, jsonObj, childPathLocation);
			}
		}
		sessionSave(resourceResolver);
	}

	/**
	 * This method is used to create Fragments for Business Hours
	 */
	private void createBusinessHoursFragmentLocation(String childPathLocation, LocationResponse jsonObj,
													 ResourceResolver resourceResolver) {
		List<BusinessHours> businessHours = jsonObj.getBusinessHours();
		String businessHoursRootPath = FolderUtil.createFolder(childPathLocation,
				PlannerLocationConstants.BUSINESS_HOURS, PlannerLocationConstants.BUSINESS_HOURS, resourceResolver);
		int businessHoursCount = 1;
		for (BusinessHours businessHoursObj : businessHours) {
			String businessHoursFragmentName = PlannerLocationConstants.BUSINESS_HOURS_FRAGMENT_PREFIX
					+ Integer.toString(businessHoursCount);
			Resource businessHoursExistingFragement = resourceResolver.getResource(
					businessHoursRootPath + PlannerLocationConstants.FORWARD_SLASH + businessHoursFragmentName);
			if (Objects.isNull(businessHoursExistingFragement)) {
				createFragment(PlannerLocationConstants.BUSINESS_HOUR_MODEL, businessHoursRootPath,
						businessHoursFragmentName, PlannerLocationConstants.BUSINESS_HOUR_MODEL_DESCRIPTION,
						resourceResolver);

			}
			updateBusinessHoursFragmentLocation(resourceResolver, businessHoursRootPath, businessHoursFragmentName,
					businessHoursObj);

			businessHoursCount++;
		}
	}

	/**
	 * This method is used to update the properties of Location Fragments
	 */
	private void updateLocationFragmentProperties(ResourceResolver resourceResolver, Node locationMasterNode,
												  LocationResponse jsonObj, String childPathLocation) {
		try {
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.EMERGENCY_CLOSURE,
					jsonObj.isEmergencyClosure());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.TEST_LOCATION,
					jsonObj.isTestLocation());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.APPOINTMENT_ONLY,
					jsonObj.isAppointmentOnly());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode,
					PlannerLocationConstants.OVERRIDE_CORPORATE_OFFICE_HOURS, jsonObj.isOverrideCorporateOfficeHours());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.OFFICE_ID,
					jsonObj.getOfficeId());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.RD_IN_MODEL,
					jsonObj.getRD());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.OFFICE_NAME,
					jsonObj.getOfficeName());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.EXTERNAL_NAME,
					jsonObj.getExternalName());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.DESKTOP_IMAGE,
					jsonObj.getDesktopImage());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.MOBILE_IMAGE,
					jsonObj.getMobileImage());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode,
					PlannerLocationConstants.BUILDING_COMPLEX_NAME, jsonObj.getBuildingComplexName());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.ADDRESS_1,
					jsonObj.getAddress1());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.ADDRESS_2,
					jsonObj.getAddress2());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.CITY,
					jsonObj.getCity());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.STATE,
					jsonObj.getState());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.ZIP,
					jsonObj.getZip());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.PHONE,
					jsonObj.getPhone());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.FAX,
					jsonObj.getFax());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.TOLL_FREE,
					jsonObj.getTollFree());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.LATITUDE,
					jsonObj.getLatitude());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.LONGITUTE,
					jsonObj.getLongitude());

			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode,
					PlannerLocationConstants.GOOGLE_REVIEW_LINK, jsonObj.getGoogleReviewLink());

			NodePropertyManagerUtil.setPropertyIfNonNull(locationMasterNode, PlannerLocationConstants.LAST_UPDATED,
					jsonObj.getLastUpdated());

			locationMasterNode.setProperty(PlannerLocationConstants.PLANNERS,
					createPlannerFragmentReference(jsonObj, resourceResolver));

			locationMasterNode
					.setProperty(PlannerLocationConstants.BUSINESS_HOURS,
							ResourceUtil.getResourceChildNames(childPathLocation
											+ PlannerLocationConstants.FORWARD_SLASH + PlannerLocationConstants.BUSINESS_HOURS,
									resourceResolver));
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occurred.", e);
		}
	}

	/**
	 * This method creates an array of strings representing planner fragments based on the given LocationResponse object and
	 * ResourceResolver.
	 * @param jsonObj
	 * @param resourceResolver
	 * @return an array of strings representing the planner fragments for the given location
	 */
	private String[] createPlannerFragmentReference(LocationResponse jsonObj, ResourceResolver resourceResolver) {
		List<String> planners = jsonObj.getPlanners();
		String[] plannersStringArray = new String[planners.size()];
		int i = 0;
		for (String id : planners) {
			String refPath = PlannerLocationConstants.ROOT_FOLDER_PATH + PlannerLocationConstants.FORWARD_SLASH
					+ PlannerLocationConstants.PLANNER + PlannerLocationConstants.FORWARD_SLASH + id;
			String fragmentNameSuffix = ResourceUtil.getProperty(resourceResolver, refPath, JcrConstants.JCR_TITLE);
			if (Objects.nonNull(fragmentNameSuffix)) {
				plannersStringArray[i] = refPath + PlannerLocationConstants.FORWARD_SLASH
						+ PlannerLocationConstants.FRAGMENT_NAME_PREFIX + fragmentNameSuffix;
				i++;
			}
		}
		return plannersStringArray;
	}

	/**
	 * This method is used to update Fragments for Business Hours
	 */
	private void updateBusinessHoursFragmentLocation(ResourceResolver resourceResolver, String businessHoursRootPath,
													 String businessHoursFragmentName, BusinessHours businessHoursObj) {
		try {
			Resource locationBusinessHoursResource = resourceResolver
					.getResource(businessHoursRootPath + PlannerLocationConstants.FORWARD_SLASH
							+ businessHoursFragmentName + PlannerLocationConstants.MASTER_NODE);
			Node locationBusinessHoursNode = locationBusinessHoursResource.adaptTo(Node.class);
			NodePropertyManagerUtil.setPropertyIfNonNull(locationBusinessHoursNode,
					PlannerLocationConstants.CLOSING_HOURS, businessHoursObj.getClosingHours());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationBusinessHoursNode, PlannerLocationConstants.DAY,
					businessHoursObj.getDay());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationBusinessHoursNode,
					PlannerLocationConstants.OPENING_HOURS, businessHoursObj.getOpeningHours());
			NodePropertyManagerUtil.setPropertyIfNonNull(locationBusinessHoursNode, PlannerLocationConstants.IS_CLOSED,
					businessHoursObj.isClosed());
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException", e);
		}
	}

	/**
	 * This method is called by Scheduler to create fragment and adds data to that
	 * fragment
	 */
	@Override
	public void addDataToCFModelLocation(ResourceResolver resourceResolver) {
		createFragmentLocation(resourceResolver);
		LOGGER.info("Content Fragment created for Location");
	}
}