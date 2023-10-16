package com.efe.core.utils;

import java.util.List;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.bean.SupportStaff;
import com.efe.core.bean.PlannerResponse;
import com.efe.core.constants.PlannerLocationConstants;

public class SupportStaffPlannerUtil {

	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SupportStaffPlannerUtil.class);

	/**
	 * This method is used to create and update education fragments
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public static void createSupportStaffFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {

		try {
			List<SupportStaff> supportStaff = jsonObj.getSupportStaff();

			String supportStaffRootPath = FolderUtil.createFolder(childPathPlanner, PlannerLocationConstants.SUPPORT_STAFF,
					PlannerLocationConstants.SUPPORT_STAFF, resourceResolver);
			int staffCount = 1;
			for (SupportStaff staffObj : supportStaff) {

				String staffFragmentName = PlannerLocationConstants.SUPPORT_STAFF_FRAGMENT_PREFIX
						+ Integer.toString(staffCount);
				Resource staffExistingFragement = resourceResolver.getResource(
						supportStaffRootPath + PlannerLocationConstants.FORWARD_SLASH + staffFragmentName);
				if (Objects.isNull(staffExistingFragement)) {

					FragmentUtil.createFragment(PlannerLocationConstants.SUPPORT_STAFF_MODEL, supportStaffRootPath,
							staffFragmentName, PlannerLocationConstants.JCR_TITLE_SUPPORT_STAFF, resourceResolver);

				}
				Resource plannerStaffResource = resourceResolver
						.getResource(supportStaffRootPath + PlannerLocationConstants.FORWARD_SLASH + staffFragmentName
								+ PlannerLocationConstants.MASTER_NODE);
				
				if(null == plannerStaffResource) {
					LOGGER.info("Education resource not found : {}", staffFragmentName);
					continue;
				}
				
				Node plannerStaffNode = plannerStaffResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerStaffNode, PlannerLocationConstants.FIRST_NAME,
						staffObj.getFirstName());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerStaffNode, PlannerLocationConstants.LAST_NAME,
						staffObj.getLastName());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerStaffNode, PlannerLocationConstants.PHOTO,
						staffObj.getPhoto());
				staffCount++;
			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
	}

}
