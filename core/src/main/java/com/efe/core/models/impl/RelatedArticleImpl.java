/**
 * 
 */
package com.efe.core.models.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.RelatedArticle;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = RelatedArticle.class, resourceType = {
		RelatedArticleImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class RelatedArticleImpl implements RelatedArticle{
	
	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/relatedarticle";

	@ValueMapValue
	private String linkText;
	
	@Self
	private SlingHttpServletRequest request;
	
	@SlingObject
	private Resource resource;
	
	@RequestAttribute
	private String text;
	
	@PostConstruct
	private void init() {
		if(StringUtils.isNotBlank(linkText)) {
			request.setAttribute("text", linkText);
		}
	}

	@Override
	public String getLinkText() {
		return linkText;
	}
	
	@Override
	public String getRequestText() {
		return text;
	}

}
