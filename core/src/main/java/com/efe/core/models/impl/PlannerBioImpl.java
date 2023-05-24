package com.efe.core.models.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.granite.references.Reference;
import com.adobe.granite.references.ReferenceAggregator;
import com.adobe.granite.references.ReferenceList;
import com.day.cq.commons.Externalizer;
import com.efe.core.bean.LocationResponse;
import com.efe.core.bean.PlannerResponse;
import com.efe.core.models.PlannerBio;
import com.efe.core.services.SeoService;
import com.efe.core.utils.ArticleDetailUtil;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LocationPlannerUtil;

/**
 * The Class PlannerBioImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class }, adapters = PlannerBio.class, resourceType = {
		PlannerBioImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PlannerBioImpl implements PlannerBio {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/plannerbio";

	/** The SlingHttpServletRequest. */
	@Self
	private SlingHttpServletRequest request;

	/** The externalizer. */
	@OSGiService
	private Externalizer externalizer;

	/** The seo service. */
	@OSGiService
	private SeoService seoService;

	/** The current resource. */
	@SlingObject
	private Resource resource;

	@SlingObject
	private ResourceResolver resourceResolver;

	/** The id. */
	@ValueMapValue
	private String id;

	/** The json ld. */
	private String jsonLd;

	/** The planner response. */
	private PlannerResponse plannerResponse;

	/** The office locations. */
	private List<LocationResponse> officeLocations;

	@OSGiService
	ReferenceAggregator aggregator;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	public void init() {
		String[] selectors = request.getRequestPathInfo().getSelectors();
		if (selectors.length == 3) {
			Resource plannerResource = LocationPlannerUtil.getPlannerResource(resourceResolver, selectors[0],
					selectors[2]);
			if (null != plannerResource && null != plannerResource.adaptTo(ContentFragment.class)) {
				plannerResponse = ArticleDetailUtil.getPlannerDetails(resourceResolver, plannerResource);
			
				String[] filters = new String[1];

				ReferenceList referenceList = aggregator.createReferenceList(plannerResource, null);

				for (final Reference reference : referenceList) {
					if (reference.getTarget() != null) {
						String refPath = reference.getTarget().getPath();
						Resource locationresource = resourceResolver.getResource(refPath);
						if (null != locationresource) {
						
							ContentFragment fragment =  locationresource.adaptTo(ContentFragment.class);
							
							System.out.println(fragment.getTemplate().getTitle());

						}

					}
				}

			}
		}
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
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

	/**
	 * Checks if is empty.
	 *
	 * @return the empty
	 */
	@Override
	public boolean isEmpty() {
		boolean isEmpty = true;
		if (StringUtils.isNotEmpty(id)) {
			isEmpty = false;
		}
		return isEmpty;
	}

	/**
	 * Gets the planner response.
	 *
	 * @return the planner response
	 */
	@Override
	public PlannerResponse getPlannerResponse() {
		return plannerResponse;
	}

}