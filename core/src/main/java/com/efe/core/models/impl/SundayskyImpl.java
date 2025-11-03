package com.efe.core.models.impl;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.util.AbstractComponentImpl;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.efe.core.models.SundayskyModel;
import com.efe.core.services.SeoService;

/**
 * The Class SundayskyImpl.clean up code
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, 
				adapters = {SundayskyModel.class,ComponentExporter.class }, 
				resourceType = {SundayskyImpl.RESOURCE_TYPE }, 
				defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class SundayskyImpl extends AbstractComponentImpl implements SundayskyModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "efe/components/embed/embeddable/sundaysky";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SundayskyImpl.class);

	/** The seo service. */
	@OSGiService
	private SeoService seoService;

	/** The externalizer. */
	@OSGiService
	private Externalizer externalizer;

	/** The current page. */
	@ScriptVariable
	private Page currentPage;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	@ValueMapValue
    private final String baseUrl = "https://myvideo.sundaysky.com/embed/";

	@ValueMapValue
	private String videoUrl;

	@ValueMapValue
	private String programId;

	@ValueMapValue
	private String title;

	@ValueMapValue
	private String height;

	@ValueMapValue
	private String width;

	@ValueMapValue
	private String scrolling;


    @Override
    public String getVideoUrl() {
		
        return this.baseUrl + "?programId=" + this.programId;
    }

		/**
	 * @return programId
	 */
	public String getProgramId() {
		return programId;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @return width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * Gets the exported type.
	 *
	 * @return the exported type
	 */
	@Override
	public String getExportedType() {
		return "efe/components/embed/embeddable/sundaysky";
	}

}
