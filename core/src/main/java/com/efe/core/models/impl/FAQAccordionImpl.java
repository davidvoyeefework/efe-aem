package com.efe.core.models.impl;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.wcm.core.components.models.Component;
import com.efe.core.models.FAQAccordion;
import com.efe.core.models.multifield.FAQ;
import com.efe.core.utils.EFEUtil;
import com.adobe.cq.export.json.ExporterConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Model(adaptables = {Resource.class}, adapters = FAQAccordion.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = FAQAccordionImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FAQAccordionImpl implements FAQAccordion{

    protected static final String RESOURCE_TYPE = "efe/components/faqaccordion";

    private static final Logger LOG = LoggerFactory.getLogger(FAQAccordion.class);
    
	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/**
	 * The current resource.
	 */
	@Self
	private Resource resource;

	/** The id. */
	@ValueMapValue
	private String id;


    @ScriptVariable
    private Component component;

    /** The social links. */
	@Inject
	private List<FAQ> faqList;

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
}
