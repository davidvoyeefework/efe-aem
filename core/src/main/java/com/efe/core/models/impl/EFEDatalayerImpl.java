package com.efe.core.models.impl;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.efe.core.bean.datalayer.DataLayerObj;
import com.efe.core.models.EFEDatalayer;
import com.efe.core.services.EfeService;
import com.efe.core.utils.DataLayerUtils;
import com.google.gson.Gson;

/**
 * The Class EFEDatalayerImpl.
 */
@Model(adaptables = {
		SlingHttpServletRequest.class }, adapters = EFEDatalayer.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class EFEDatalayerImpl implements EFEDatalayer {

	/** The current page. */
	@ScriptVariable
	private Page currentPage;

	/** The resolver. */
	@SlingObject
	private ResourceResolver resolver;

	/** The externalizer. */
	@OSGiService
	private Externalizer externalizer;
	
	/** The efe service. */
	@OSGiService
	private EfeService efeService;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	@RequestAttribute
	private String type;

	/** The data layer. */
	private String dataLayer;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	protected void init() {
		DataLayerObj dataLayerObj = null;

		System.out.println(request.getAttribute("test"));
		if ("page".equals(type)) {
			dataLayerObj = DataLayerUtils.createPageLoadEventObj(currentPage, request, resolver, externalizer);
		}
		if (null != dataLayerObj) {
			dataLayer = new Gson().toJson(dataLayerObj, DataLayerObj.class);
		}
	}

	/**
	 * Gets the data layer.
	 *
	 * @return the dataLayer
	 */
	@Override
	public String getDataLayer() {
		return dataLayer;
	}
	
	/**
	 * Gets the one trust script.
	 *
	 * @return the one trust script
	 */
	@Override
	public String getOneTrustScript() {
		return efeService.getOneTrustScript();
	}
	
	/**
	 * Gets the one trust script id.
	 *
	 * @return the one trust script id
	 */
	@Override
	public String getOneTrustScriptId() {
		return efeService.getOneTrustScriptId();
	}

}
