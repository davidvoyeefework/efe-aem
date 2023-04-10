package com.efe.core.utils;

import java.util.List;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.services.impl.PlannerModelServicesImpl;
import com.efe.core.services.impl.bean.HonorAward;
import com.efe.core.services.impl.bean.PlannerResponse;

public class HonorAwardUtil {
	
	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerModelServicesImpl.class);
	
	/**
	 * This method is used to create and update honor award fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public static void createHonorAwardFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {

		try {
			List<HonorAward> honorAward = jsonObj.getHonorAward();

			String honorAwardRootPath = FolderUtil.createFolder(childPathPlanner, PlannerLocationConstants.HONOR_AWARD,
					resourceResolver);
			int honorAwardCount = 1;
			for (HonorAward honorAwardObj : honorAward) {

				String honorAwardFragmentName = PlannerLocationConstants.HONOR_AWARD
						+ Integer.toString(honorAwardCount);
				Resource honorAwardexistingFragement = resourceResolver.getResource(
						honorAwardRootPath + PlannerLocationConstants.FORWARD_SLASH + honorAwardFragmentName);
				if (Objects.isNull(honorAwardexistingFragement)) {

					FragmentUtil.createFragment(PlannerLocationConstants.HONOR_AWARD_MODEL, honorAwardRootPath,
							honorAwardFragmentName, PlannerLocationConstants.JCR_TITLE_HONOR_AWARD, resourceResolver);

				}
				Resource plannerHonorAwardResource = resourceResolver
						.getResource(honorAwardRootPath + PlannerLocationConstants.FORWARD_SLASH
								+ honorAwardFragmentName + PlannerLocationConstants.MASTER_NODE);
				Node honorAwardNode = plannerHonorAwardResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(honorAwardNode, PlannerLocationConstants.DATE_OF_AWARD,
						honorAwardObj.getDateOfAward());

				NodePropertyManagerUtil.setPropertyIfNonNull(honorAwardNode, PlannerLocationConstants.DISCLOSURE,
						honorAwardObj.getDisclosure());

				NodePropertyManagerUtil.setPropertyIfNonNull(honorAwardNode, PlannerLocationConstants.HONOR_AWARD_NAME,
						honorAwardObj.getHonorAwardName());

				NodePropertyManagerUtil.setPropertyIfNonNull(honorAwardNode, PlannerLocationConstants.ORGANISATION,
						honorAwardObj.getOrganization());
				honorAwardCount++;
			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
	}

}
