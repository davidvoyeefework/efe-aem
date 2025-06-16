package com.efe.core.utils;

import java.util.List;
import java.util.Objects;
import javax.jcr.Session;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.commons.JcrUtils;
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

			officesLocations.removeIf( obj -> obj.getName() == "Phoenix - Virtual Advisors");

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
				
				if(null == plannerOfficesLocationsResource) {
					LOGGER.info("plannerOfficesLocationsResource resource not found : {}", officesLocationsFragmentName);
					continue;
				}
				
				Node plannerOfficesLocationsNode = plannerOfficesLocationsResource.adaptTo(Node.class);

				// If master node inside array of OfficeLocations has Phoenix for Prop value delete
				String propertyName = plannerOfficesLocationsNode.getProperty("city").toString();
				// if (propertyName == "Phoenix") {
				// 	Repository repository = JcrUtils.getRepository("http://localhost:4502/crx/server");
				// 	javax.jcr.Session session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
				// 	plannerOfficesLocationsNode.remove();
				// 	session.save();
				// 	session.logout();
				}



				NodePropertyManagerUtil.setPropertyIfNonNull(plannerOfficesLocationsNode, PlannerLocationConstants.ID,
						officesLocationsObj.getId());

				if (officesLocationsObj.getCity() != "Phoenix") {	
					NodePropertyManagerUtil.setPropertyIfNonNull(plannerOfficesLocationsNode, PlannerLocationConstants.CITY,
					officesLocationsObj.getCity());					
				}					

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
