package com.efe.core.models.impl;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.PromotionFormLayoutContainer;

/**
 * The Class PromotionFormLayoutContainerImpl.
 */
@Model(adaptables = { Resource.class }, adapters = PromotionFormLayoutContainer.class, resourceType = {
		FeFooterImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class PromotionFormLayoutContainerImpl implements PromotionFormLayoutContainer {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "efe/fe-components/content/layoutcomponent";

	/** The Constant TEASER_RESOURCE_TYPE. */
	protected static final String TEASER_RESOURCE_TYPE = "efe/components/teaser";
	
	/** The Constant IMAGE_RESOURCE_TYPE. */
	protected static final String IMAGE_RESOURCE_TYPE = "efe/components/image";
	
	/** The variation. */
	@ValueMapValue
	private String variation;

	/** The hero section resource type. */
	private String heroSectionResourceType;

	/** The is configured. */
	private boolean isConfigured;
	
	/** The hero section node name. */
	private String heroSectionNodeName;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	protected void init() {
		if (Objects.isNull(variation)) {
			return;
		}
		isConfigured = true;

		if ("variation-1".equalsIgnoreCase(variation)) {
			heroSectionResourceType = TEASER_RESOURCE_TYPE;
			heroSectionNodeName = "teaser";
		} else {
			heroSectionResourceType = IMAGE_RESOURCE_TYPE;
			heroSectionNodeName = "image";
		}
	}

	/**
	 * Gets the variation.
	 *
	 * @return the variation
	 */
	@Override
	public String getVariation() {
		return variation;
	}

	/**
	 * Gets the hero section resource type.
	 *
	 * @return the heroSectionResourceType
	 */
	@Override
	public String getHeroSectionResourceType() {
		return heroSectionResourceType;
	}

	/**
	 * Checks if is configured.
	 *
	 * @return the isConfigured
	 */
	@Override
	public boolean isConfigured() {
		return isConfigured;
	}

	/**
	 * Gets the hero section node name.
	 *
	 * @return the heroSectionNodeName
	 */
	@Override
	public String getHeroSectionNodeName() {
		return heroSectionNodeName;
	}

}
