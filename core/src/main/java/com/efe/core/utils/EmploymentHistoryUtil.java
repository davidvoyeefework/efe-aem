package com.efe.core.utils;

import java.util.List;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.bean.EmploymentHistory;
import com.efe.core.bean.PlannerResponse;
import com.efe.core.constants.PlannerLocationConstants;

public class EmploymentHistoryUtil {

	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EmploymentHistoryUtil.class);

	/**
	 * This method is used to create and update Employment History Fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public static void createEmploymentHistoryFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {
		try {
			List<EmploymentHistory> employmentHistory = jsonObj.getEmploymentHistory();

			String employmentHistoryRootPath = FolderUtil.createFolder(childPathPlanner,
					PlannerLocationConstants.EMPLOYMENT_HISTORY, resourceResolver);
			int employmentHistoryCount = 1;
			for (EmploymentHistory employmentHistoryObj : employmentHistory) {
				String employmentHistoryFragmentName = PlannerLocationConstants.EMPLOYMENT_HISTORY_PREFIX
						+ Integer.toString(employmentHistoryCount);
				Resource employmentHistoryExistingFragement = resourceResolver.getResource(employmentHistoryRootPath
						+ PlannerLocationConstants.FORWARD_SLASH + employmentHistoryFragmentName);
				if (Objects.isNull(employmentHistoryExistingFragement)) {
					FragmentUtil.createFragment(PlannerLocationConstants.EMPLOYMENT_HISTORY_MODEL,
							employmentHistoryRootPath, employmentHistoryFragmentName,
							PlannerLocationConstants.JCR_TITLE_EMPLOYMENT_HISTORY, resourceResolver);
				}
				Resource plannerEmploymentHistoryResource = resourceResolver
						.getResource(employmentHistoryRootPath + PlannerLocationConstants.FORWARD_SLASH
								+ employmentHistoryFragmentName + PlannerLocationConstants.MASTER_NODE);
				Node plannerEmploymentHistoryExamNode = plannerEmploymentHistoryResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEmploymentHistoryExamNode,
						PlannerLocationConstants.START_DATE, employmentHistoryObj.getStartDate());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEmploymentHistoryExamNode,
						PlannerLocationConstants.END_DATE, employmentHistoryObj.getEndDate());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEmploymentHistoryExamNode,
						PlannerLocationConstants.JOB_TITLE, employmentHistoryObj.getJobTitle());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEmploymentHistoryExamNode,
						PlannerLocationConstants.COMPANY_NAME, employmentHistoryObj.getCompanyName());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerEmploymentHistoryExamNode,
						PlannerLocationConstants.CURRENT, employmentHistoryObj.isCurrent());

				employmentHistoryCount++;
			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}

	}

}
