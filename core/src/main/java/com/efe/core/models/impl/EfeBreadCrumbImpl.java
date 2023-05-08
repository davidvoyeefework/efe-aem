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

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.Breadcrumb;
import com.adobe.cq.wcm.core.components.models.NavigationItem;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.efe.core.models.EfeBreadCrumb;
import com.efe.core.services.SeoService;
import com.efe.core.utils.SeoUtil;

/**
 * The Class EfeBreadCrumbImpl.
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = EfeBreadCrumb.class, resourceType = {
		EfeBreadCrumbImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class EfeBreadCrumbImpl implements EfeBreadCrumb {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "efe/components/breadcrumb";

	@OSGiService
	private SeoService seoService;

	@OSGiService
	private Externalizer externalizer;

	@ScriptVariable
	private Page currentPage;

	@OSGiService
	private ModelFactory modelFactory;

	@Self
	private SlingHttpServletRequest request;

	@ValueMapValue
	private boolean showSelectorAsLeaf;

	@ValueMapValue
	private int selectorIndex;

	/** The json ld. */
	private String jsonLd;

	private Breadcrumb breadcrumb;

	@PostConstruct
	protected void init() {

		breadcrumb = modelFactory.getModelFromWrappedRequest(request, request.getResource(), Breadcrumb.class);
		if (null != breadcrumb) {
			jsonLd = SeoUtil.getBreadCrumbSEOSchema(seoService, request, externalizer, breadcrumb.getItems(),
					showSelectorAsLeaf, selectorIndex);
		}

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

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	@Override
	public Collection<NavigationItem> getItems() {
		return breadcrumb.getItems();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public String getId() {
		return breadcrumb.getId();
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	@Override
	public ComponentData getData() {
		return breadcrumb.getData();
	}

	/**
	 * Gets the applied css classes.
	 *
	 * @return the applied css classes
	 */
	@Override
	public String getAppliedCssClasses() {
		return breadcrumb.getAppliedCssClasses();
	}

	/**
	 * Gets the exported type.
	 *
	 * @return the exported type
	 */
	@Override
	public String getExportedType() {
		return breadcrumb.getExportedType();
	}

}
