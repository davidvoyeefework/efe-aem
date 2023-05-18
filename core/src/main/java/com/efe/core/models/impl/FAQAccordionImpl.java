package com.efe.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.Component;
import com.efe.core.models.FAQAccordion;
import com.efe.core.models.multifield.FAQ;
import com.efe.core.services.SeoService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.SeoUtil;

/**
 * The Class FAQAccordionImpl.
 */
@Model(adaptables = {
		Resource.class }, adapters = FAQAccordion.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = FAQAccordionImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FAQAccordionImpl implements FAQAccordion {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "efe/components/faqaccordion";

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;
	
	/** The seo service. */
	@OSGiService
	private SeoService seoService;

	/**
	 * The current resource.
	 */
	@Self
	private Resource resource;

	/** The id. */
	@ValueMapValue
	private String id;

	/** The component. */
	@ScriptVariable
	private Component component;

	/** The faq list. */
	@Inject
	private List<FAQ> faqList;
	
	/** The json ld. */
	private String jsonLd;
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		if(Objects.nonNull(faqList) && !faqList.isEmpty()) {
			jsonLd = SeoUtil.getFaqSchema(seoService, faqList);
		}
	}

	/**
	 * Gets the faq list.
	 *
	 * @return the faq list
	 */
	public List<FAQ> getFaqList() {
		if (Objects.nonNull(faqList)) {
			return new ArrayList<>(faqList);
		}
		return Collections.emptyList();
	}

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
	 * Gets the json ld.
	 *
	 * @return the json ld
	 */
	@Override
	public String getJsonLd() {
		return jsonLd;
	}
}
