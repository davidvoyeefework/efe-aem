package com.efe.core.utils;

import java.util.List;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.bean.Education;
import com.efe.core.bean.PlannerResponse;
import com.efe.core.constants.PlannerLocationConstants;

public class EducationPlannerUtil {

	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EducationPlannerUtil.class);

	/**
	 * This method is used to create and update education fragments
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public static void createEducationFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {

		try {
			List<Education> education = jsonObj.getEducation();

			String educationRootPath = FolderUtil.createFolder(childPathPlanner, PlannerLocationConstants.EDUCATION,
					PlannerLocationConstants.EDUCATION, resourceResolver);
			int educationCount = 1;
			for (Education eduObj : education) {

				String educationFragmentName = PlannerLocationConstants.EDUCATION_FRAGMENT_PREFIX
						+ Integer.toString(educationCount);
				Resource educationExistingFragement = resourceResolver.getResource(
						educationRootPath + PlannerLocationConstants.FORWARD_SLASH + educationFragmentName);
				if (Objects.isNull(educationExistingFragement)) {

					FragmentUtil.createFragment(PlannerLocationConstants.EDUCATION_MODEL, educationRootPath,
							educationFragmentName, PlannerLocationConstants.JCR_TITLE_EDUCATION, resourceResolver);

				}
				Resource plannerEducationResource = resourceResolver
						.getResource(educationRootPath + PlannerLocationConstants.FORWARD_SLASH + educationFragmentName
								+ PlannerLocationConstants.MASTER_NODE);
				
				if(null == plannerEducationResource) {
					LOGGER.info("Education resource not found : {}", educationFragmentName);
					continue;
				}
				
				Node plannerEducationNode = plannerEducationResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEducationNode, PlannerLocationConstants.DEGREE,
						eduObj.getDegree());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEducationNode, PlannerLocationConstants.MAJOR,
						eduObj.getMajor());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEducationNode, PlannerLocationConstants.UNIVERSITY,
						eduObj.getUniversity());
				educationCount++;
			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
	}

}
