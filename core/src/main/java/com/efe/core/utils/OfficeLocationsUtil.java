package com.efe.core.utils;

import java.util.List;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.bean.OfficesLocations;
import com.efe.core.bean.PlannerResponse;
import com.efe.core.constants.PlannerLocationConstants;

public class OfficeLocationsUtil {

	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EmploymentHistoryUtil.class);

	/**
	 * This method is used to create and update fragments for office locations
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public static void createOfficesLocationsFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {
		try {
			List<OfficesLocations> officesLocations = jsonObj.getOfficesLocations();

			String officesLocationsRootPath = FolderUtil.createFolder(childPathPlanner,
					PlannerLocationConstants.OFFICE_LOCATIONS,PlannerLocationConstants.OFFICE_LOCATIONS, resourceResolver);
			int officesLocationsCount = 1;
			for (OfficesLocations officesLocationsObj : officesLocations) {

				String officesLocationsFragmentName = PlannerLocationConstants.OFFICE_LOCATIONS_PREFIX
						+ Integer.toString(officesLocationsCount);
				Resource officesLocationsExistingFragement = resourceResolver.getResource(officesLocationsRootPath
						+ PlannerLocationConstants.FORWARD_SLASH + officesLocationsFragmentName);
				if (Objects.isNull(officesLocationsExistingFragement)) {

					FragmentUtil.createFragment(PlannerLocationConstants.ADDRESS_MODEL, officesLocationsRootPath,
							officesLocationsFragmentName, PlannerLocationConstants.JCR_TITLE_OFFICE_LOCATIONS,
							resourceResolver);

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
}
