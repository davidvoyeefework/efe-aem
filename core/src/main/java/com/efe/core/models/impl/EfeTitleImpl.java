package com.efe.core.models.impl;

import java.util.Objects;

import javax.annotation.PostConstruct;

import com.efe.core.utils.LocationPlannerUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.commons.link.Link;
import com.adobe.cq.wcm.core.components.models.Title;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;

/**
 * The Class EfeTitleImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class }, adapters = Title.class, resourceType = {
		EfeTitleImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class EfeTitleImpl implements Title {

	/** The Constant SELECTOR_PLACEHOLDER_1. */
	private static final String SELECTOR_PLACEHOLDER_1 = "{1}";

	/** The Constant SELECTOR_PLACEHOLDER_0. */
	private static final String SELECTOR_PLACEHOLDER_0 = "{0}";

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/title";

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	/** The title. */
	@Self
	@Via(type = ResourceSuperType.class)
	private Title title;

	/** The include location in title. */
	@ValueMapValue
	private boolean includeLocationInTitle;

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The text. */
	private String text;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	protected void init() {
		if (!Objects.nonNull(title) || !Objects.nonNull(title.getText())) {
			return;
		}
		text = title.getText();
		if (includeLocationInTitle && null != request.getRequestPathInfo().getSelectors()) {
			final String[] selectors = request.getRequestPathInfo().getSelectors();
			if (selectors.length == 2) {
				text = text.replace(SELECTOR_PLACEHOLDER_0, selectors[0]);
				String stateSelector = selectors[0].toLowerCase();
				String citySelector = selectors[1].toLowerCase();
				Resource resourceLocation = LocationPlannerUtil.getLocationResource(resourceResolver, stateSelector, citySelector);
				String city = LocationPlannerUtil.getLocationProperty(resourceResolver, resourceLocation, "city");
				text = text.replace(SELECTOR_PLACEHOLDER_1, city);
			}
		}
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	@Override
	public String getText() {
		return text;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public String getId() {
		return title.getId();
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	@Override
	public ComponentData getData() {
		return title.getData();
	}

	/**
	 * Gets the applied css classes.
	 *
	 * @return the applied css classes
	 */
	@Override
	public String getAppliedCssClasses() {
		return title.getAppliedCssClasses();
	}

	/**
	 * Gets the exported type.
	 *
	 * @return the exported type
	 */
	@Override
	public String getExportedType() {
		return title.getExportedType();
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	@Override
	public String getType() {
		return title.getType();
	}

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	@Override
	public Link getLink() {
		return title.getLink();
	}

	/**
	 * Checks if is link disabled.
	 *
	 * @return true, if is link disabled
	 */
	@Override
	public boolean isLinkDisabled() {
		return title.isLinkDisabled();
	}

}
