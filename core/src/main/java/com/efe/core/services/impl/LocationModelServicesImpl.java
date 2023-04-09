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
import com.efe.core.services.LocationModelServices;
import com.efe.core.services.PlannerApiService;
import com.efe.core.services.RestService;
import com.efe.core.services.impl.bean.BusinessHours;
import com.efe.core.services.impl.bean.LocationResponse;
import com.efe.core.utils.FolderUtil;
import com.efe.core.utils.NodePropertyManagerUtil;
import com.efe.core.utils.ResourceUtil;
import com.google.gson.Gson;

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
	 * PlannerApiService injected
	 */
	@Reference
	private PlannerApiService plannerApiService;

	/**
	 * This method is used to create and update Fragments for Locations
	 */
	public void createFragmentLocation(ResourceResolver resourceResolver) {
		String jsonObjectLocation = restService.getData(plannerApiService.getLocationsAPIEndpoint(),
				plannerApiService.getAuthHeader());
		Gson gson = new Gson();
		LocationResponse[] jsonElement = gson.fromJson(jsonObjectLocation, LocationResponse[].class);

		String rootPath = FolderUtil.createFolder(PlannerLocationConstants.ROOT_FOLDER_PATH,
				PlannerLocationConstants.LOCATIONS, resourceResolver);
		for (LocationResponse jsonObj : jsonElement) {

			String officeId = jsonObj.getOfficeId();
			String officeName = jsonObj.getOfficeName();
			String childPathLocation = FolderUtil.createFolder(rootPath, officeName + officeId, resourceResolver);
			String fragmentName = PlannerLocationConstants.FRAGMENT_NAME_PREFIX + officeName
					+ PlannerLocationConstants.UNDERSCORE + officeId;

			Resource existingFragement = resourceResolver.getResource(childPathLocation + PlannerLocationConstants.FORWARD_SLASH + fragmentName);
			if (existingFragement == null) {
				Resource templateOrModelRsc = resourceResolver.getResource(PlannerLocationConstants.LOCATION_MODEL);
				Resource parentRsc = resourceResolver.getResource(childPathLocation);
				FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
				try {
					tpl.createFragment(parentRsc, fragmentName, PlannerLocationConstants.JCR_TITLE_LOCATION);
					if (resourceResolver.hasChanges()) {
						resourceResolver.commit();
					}
				} catch (ContentFragmentException e) {
					LOGGER.error("ContentFragmentException:", e);
				} catch (PersistenceException e) {
					LOGGER.error("PersistenceException:", e);
				}
				Resource locationMasterResource = resourceResolver.getResource(childPathLocation+ PlannerLocationConstants.FORWARD_SLASH + fragmentName + PlannerLocationConstants.MASTER_NODE);
				Node locationMasterNode = locationMasterResource.adaptTo(Node.class);
				updateLocationFragmentProperties(resourceResolver, locationMasterNode, jsonObj, childPathLocation);
			}

		}
	}

	/**
	 * This method is used to create and update Fragments for Business Hours
	 */
	public void createBusinessHoursFragmentLocation(String childPathLocation, LocationResponse jsonObj,
			ResourceResolver resourceResolver) {
			List<BusinessHours> businessHours = jsonObj.getBusinessHours();
			String businessHoursRootPath = FolderUtil.createFolder(childPathLocation,
					PlannerLocationConstants.BUSINESS_HOURS, resourceResolver);
			int businessHoursCount = 1;
			for (BusinessHours businessHoursObj : businessHours) {
				String businessHoursFragmentName = PlannerLocationConstants.BUSINESS_HOURS_FRAGMENT_PREFIX
						+ Integer.toString(businessHoursCount);
				Resource businessHoursExistingFragement = resourceResolver.getResource(
						businessHoursRootPath + PlannerLocationConstants.FORWARD_SLASH + businessHoursFragmentName);
				if (businessHoursExistingFragement == null) {
					Resource templateOrModelRsc = resourceResolver
							.getResource(PlannerLocationConstants.BUSINESS_HOUR_MODEL);
					Resource parentRsc = resourceResolver.getResource(businessHoursRootPath);
					FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class); 
					try {
						tpl.createFragment(parentRsc, businessHoursFragmentName,
								PlannerLocationConstants.BUSINESS_HOUR_MODEL_DESCRIPTION);
						if (resourceResolver.hasChanges()) {
							resourceResolver.commit();
						}
					} catch (ContentFragmentException e) {
						LOGGER.error("ContentFragmentException:", e);
					} catch (PersistenceException e) {
						LOGGER.error("PersistenceException:", e);
					}
				}
				updateBusinessHoursFragmentLocation(resourceResolver, businessHoursRootPath, businessHoursFragmentName,
						businessHoursObj);

				businessHoursCount++;
			}
	}

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
					PlannerLocationConstants.Building_COMPLEX_NAME, jsonObj.getBuildingComplexName());
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
					jsonObj.getZip());
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

			String[] plannersStringArray = jsonObj.getPlanners().toArray(new String[0]);
			locationMasterNode.setProperty(PlannerLocationConstants.PLANNERS, plannersStringArray);
			createBusinessHoursFragmentLocation(childPathLocation, jsonObj, resourceResolver);
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occurred.", e);
		}
	}

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
	public void addDataToCFModelLocation() {
		ResourceResolver resourceResolver = ResourceUtil.getServiceResourceResolver(resourceResolverFactory);
		createFragmentLocation(resourceResolver);
		LOGGER.info("Content Fragment created for Location");
	}
}
