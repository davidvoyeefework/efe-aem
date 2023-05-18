package com.efe.core.models.impl;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.constants.Constants;
import com.efe.core.models.FormsSelector;
import com.efe.core.services.EfeService;
import com.efe.core.utils.EFEUtil;

/**
 * The Class FormsSelectorImpl.
 */
@Model(adaptables = {
		Resource.class }, adapters = FormsSelector.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = FormsSelectorImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FormsSelectorImpl implements FormsSelector {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "efe/components/formselector";
	
	/**
	 * The current resource.
	 */
	@Self
	private Resource resource;

	/** The id. */
	@ValueMapValue
	private String id;

	/** The form id. */
	@ValueMapValue
	private String formId;

	/** The efe service. */
	@OSGiService
	private EfeService efeService;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		if (id == null) {
			id = EFEUtil.getId(resource);
		}
		return id;
	}

	/**
	 * Gets the form id.
	 *
	 * @return the form id
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * Gets the form url.
	 *
	 * @return the form url
	 */
	public String getFormUrl() {
		return efeService.getFormBaseUrl() + Constants.QUERY_PARAMETER_ID + getFormId();
	}

	/**
	 * Gets the form js url.
	 *
	 * @return the form js url
	 */
	public String getFormJsUrl() {
		return efeService.getFormJsUrl();
	}

}
