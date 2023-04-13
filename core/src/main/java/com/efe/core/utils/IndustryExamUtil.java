package com.efe.core.utils;

import java.util.List;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.bean.IndustryExams;
import com.efe.core.bean.PlannerResponse;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.services.impl.PlannerModelServicesImpl;

/**
 * IndustryExamUtil
 *
 */
public class IndustryExamUtil {

	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(IndustryExamUtil.class);

	/**
	 * This method is used to create and update Industry Exam Fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public static void createIndustryExamFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {

		try {
			List<IndustryExams> industryExams = jsonObj.getIndustryExams();
			String industryExamRootPath = FolderUtil.createFolder(childPathPlanner,
					PlannerLocationConstants.INDUSTRY_EXAMS, resourceResolver);
			int industryExamCount = 1;
			for (IndustryExams industryExamObj : industryExams) {
				String industryExamFragmentName = PlannerLocationConstants.INDUSTRY_EXAMS_PREFIX
						+ Integer.toString(industryExamCount);
				Resource industryExamexistingFragement = resourceResolver.getResource(
						industryExamRootPath + PlannerLocationConstants.FORWARD_SLASH + industryExamFragmentName);
				if (Objects.isNull(industryExamexistingFragement)) {

					FragmentUtil.createFragment(PlannerLocationConstants.INDUSTRY_EXAM_MODEL, industryExamRootPath,
							industryExamFragmentName, PlannerLocationConstants.JCR_TITLE_INDUSTRY_EXAM,
							resourceResolver);
				}
				Resource plannerIndustryExamResource = resourceResolver
						.getResource(industryExamRootPath + PlannerLocationConstants.FORWARD_SLASH
								+ industryExamFragmentName + PlannerLocationConstants.MASTER_NODE);
				Node plannerIndustryExamNode = plannerIndustryExamResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerIndustryExamNode,
						PlannerLocationConstants.EXAM_NAME_LONG, industryExamObj.getExamNameLong());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerIndustryExamNode,
						PlannerLocationConstants.EXAM_NAME_SHORT, industryExamObj.getExamNameShort());
				industryExamCount++;
			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
	}
}
