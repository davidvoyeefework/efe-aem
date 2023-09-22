package com.efe.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.util.AbstractComponentImpl;
import com.efe.core.models.BrightCove;

/**
 * The Class BrightCoveImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class },
	   adapters = { BrightCove.class, ComponentExporter.class },
	   resourceType = { BrightCoveImpl.RESOURCE_TYPE },
	   defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class BrightCoveImpl extends AbstractComponentImpl implements BrightCove {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/fe-components/content/brightcove";

	/** The video url. */
	@ValueMapValue
	private String videoUrl;

	/**
	 * Gets the video url.
	 *
	 * @return the video url
	 */
	@Override
	public String getVideoUrl() {
		return videoUrl;
	}
}
