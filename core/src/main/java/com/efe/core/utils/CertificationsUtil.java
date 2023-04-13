package com.efe.core.utils;

import java.util.List;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.bean.Certifications;
import com.efe.core.bean.PlannerResponse;
import com.efe.core.constants.PlannerLocationConstants;

public class CertificationsUtil {

	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CertificationsUtil.class);

	/**
	 * This method is used to create and update certification fragment
	 * 
	 * @param childPathPlanner
	 * @param jsonObj
	 * @param resourceResolver
	 */
	public static void createCertificationsFragmentPlanner(String childPathPlanner, PlannerResponse jsonObj,
			ResourceResolver resourceResolver) {

		try {
			List<Certifications> certifications = jsonObj.getCertifications();

			String certificationsRootPath = FolderUtil.createFolder(childPathPlanner,
					PlannerLocationConstants.CERTIFICATIONS, resourceResolver);
			int certificationsCount = 1;
			for (Certifications certificationsObj : certifications) {

				String certificationFragmentName = PlannerLocationConstants.CERTIFICATION_PREFIX
						+ Integer.toString(certificationsCount);
				Resource certificationExistingFragement = resourceResolver.getResource(
						certificationsRootPath + PlannerLocationConstants.FORWARD_SLASH + certificationFragmentName);
				if (Objects.isNull(certificationExistingFragement)) {

					FragmentUtil.createFragment(PlannerLocationConstants.CERTIFICATION_MODEL, certificationsRootPath,
							certificationFragmentName, PlannerLocationConstants.JCR_TITLE_CERTIFICATION,
							resourceResolver);
				}
				Resource plannerCertificationResource = resourceResolver
						.getResource(certificationsRootPath + PlannerLocationConstants.FORWARD_SLASH
								+ certificationFragmentName + PlannerLocationConstants.MASTER_NODE);
				Node plannerCertificationExamNode = plannerCertificationResource.adaptTo(Node.class);

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerCertificationExamNode,
						PlannerLocationConstants.ABBREVIATION, certificationsObj.getAbbreviation());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerCertificationExamNode,
						PlannerLocationConstants.LEGAL_COMPLIANCE_DISCLOSURE,
						certificationsObj.getLegalComplianceDisclosure());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerCertificationExamNode,
						PlannerLocationConstants.MARKETING_DISCLOSURE, certificationsObj.getMarketingDisclosure());

				NodePropertyManagerUtil.setPropertyIfNonNull(plannerCertificationExamNode,
						PlannerLocationConstants.NAME, certificationsObj.getName());
				certificationsCount++;
			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException occured.", e);
		}
	}

}
