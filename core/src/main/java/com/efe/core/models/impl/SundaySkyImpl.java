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
import org.apache.sling.models.factory.ModelFactory;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.util.AbstractComponentImpl;
import com.adobe.cq.wcm.core.components.models.NavigationItem;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.efe.core.models.SundayskyModel;
import com.efe.core.services.SeoService;
import com.efe.core.utils.SeoUtil;

/**
 * The Class SundayskyImpl.
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, 
				adapters = {SundayskyModel.class,ComponentExporter.class }, 
				resourceType = {SundaySkyImpl.RESOURCE_TYPE }, 
				defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class SundaySkyImpl extends AbstractComponentImpl implements SundayskyModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "efe/components/embed/embeddable/sundaysky";

	/** The seo service. */
	@OSGiService
	private SeoService seoService;

	/** The externalizer. */
	@OSGiService
	private Externalizer externalizer;

	/** The current page. */
	@ScriptVariable
	private Page currentPage;

	/** The model factory. */
	@OSGiService
	private ModelFactory modelFactory;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	@ValueMapValue
	private String baseUrl;

	@ValueMapValue
	private String videoUrl;

	@ValueMapValue
	private String programId;

	@ValueMapValue
	private String title;

	@ValueMapValue
	private String path;

	@ValueMapValue
	private String height;

	@ValueMapValue
	private String width;

	@ValueMapValue
	private String scrolling;

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {

		 // Placeholder for client-side personakey injection
		videoUrl = baseUrl; // Will be completed in HTL with JS

	}

	public String getBaseUrl() {
        return baseUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
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
	 * @return path
	 */
	public String getPath() {
		return path;
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
	 * @return scrolling
	 */
	public String getScrolling() {
		return scrolling;
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
