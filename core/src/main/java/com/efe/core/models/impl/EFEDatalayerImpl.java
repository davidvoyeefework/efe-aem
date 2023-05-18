package com.efe.core.models.impl;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
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

	/** The resolver factory. */
	@OSGiService
	private ResourceResolverFactory resolverFactory;

	/** The type. */
	@RequestAttribute
	private String type;

	/** The data layer. */
	private String dataLayer;

	/** The tracking links json. */
	private String trackingLinksJson;
	
	/** The form name. */
	private String formName;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	protected void init() {
		DataLayerObj dataLayerObj = null;

		if ("page".equals(type)) {
			dataLayerObj = DataLayerUtils.createPageLoadEventObj(efeService, currentPage, request, resolver,
					externalizer);
			trackingLinksJson = DataLayerUtils.getTrackingLinksList(resolverFactory, efeService);
			
			if(request.getRequestPathInfo().getSelectors() != null) {
				String []requestSelectors = request.getRequestPathInfo().getSelectors();
				if(requestSelectors.length >= 2 && "formevent".equals(requestSelectors[0])) {
					formName = requestSelectors[1];
				}
			}
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

	/**
	 * Gets the tracking links json.
	 *
	 * @return the trackingLinksJson
	 */
	@Override
	public String getTrackingLinksJson() {
		return trackingLinksJson;
	}

	/**
	 * Gets the form name.
	 *
	 * @return the formName
	 */
	@Override
	public String getFormName() {
		return formName;
	}

	/**
	 * Checks if is enable GA.
	 *
	 * @return the enableGA
	 */
	@Override
	public boolean isEnableGA() {
		return efeService.isEnabledGA();
	}

	/**
	 * Gets the ga tag value.
	 *
	 * @return the gaTagValue
	 */
	@Override
	public String getGaTagValue() {
		return efeService.getGaTagValue();
	}

}
