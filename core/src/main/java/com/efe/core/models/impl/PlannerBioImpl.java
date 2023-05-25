package com.efe.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

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
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.models.PlannerBio;
import com.efe.core.services.EfeService;
import com.efe.core.services.SeoService;
import com.efe.core.utils.ArticleDetailUtil;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;
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

	/** The efe service. */
	@OSGiService
	private EfeService efeService;

	/** The current resource. */
	@SlingObject
	private Resource resource;

	/** The resource resolver. */
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
	
	/** The filter. */
	private String []filter;

	/** The aggregator. */
	@OSGiService
	private ReferenceAggregator aggregator;

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
				ReferenceList referenceList = aggregator.createReferenceList(plannerResource, filter);
				officeLocations = new ArrayList<>();
				addPlannerOffices(referenceList);

			}
		}
	}

	/**
	 * Adds the planner offices.
	 *
	 * @param referenceList the reference list
	 */
	private void addPlannerOffices(ReferenceList referenceList) {
		for (final Reference reference : referenceList) {
			if (reference.getTarget() != null) {
				String refPath = reference.getTarget().getPath();
				Resource locationresource = resourceResolver.getResource(refPath);
				if (null != locationresource) {
					ContentFragment fragment = locationresource.adaptTo(ContentFragment.class);
					if (null != fragment && isLocationsCF(fragment)) {
						LocationResponse locationResponse = LocationPlannerUtil.getLocationInfo(locationresource);
						locationResponse.setUrl(LinkUtil.getFormattedLink(efeService.getPlannerPageUrl()
								+ PlannerLocationConstants.DOT
								+ locationresource.getParent().getParent().getName().toUpperCase()
								+ PlannerLocationConstants.DOT
								+ LocationPlannerUtil.toCamelCase(locationresource.getParent().getName())
										.replaceAll(PlannerLocationConstants.SPACE, PlannerLocationConstants.HYPHEN),
								resourceResolver));
						officeLocations.add(locationResponse);

					}

				}

			}
		}
	}

	/**
	 * Checks if is locations CF.
	 *
	 * @param fragment the fragment
	 * @return true, if is locations CF
	 */
	private boolean isLocationsCF(ContentFragment fragment) {
		boolean isLocationsCF = false;
		if (null != fragment && fragment.getTemplate() != null) {
			Resource fragmentResource = fragment.getTemplate().adaptTo(Resource.class);
			if (fragmentResource.getPath().startsWith("/conf/efe/settings/dam/cfm/models/locations")) {
				isLocationsCF = true;
			}
		}
		return isLocationsCF;
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
		if (null != plannerResponse) {
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

	/**
	 * Gets the office locations.
	 *
	 * @return the officeLocations
	 */
	@Override
	public List<LocationResponse> getOfficeLocations() {
		if (Objects.nonNull(officeLocations)) {
			return new ArrayList<>(officeLocations);
		}
		return Collections.emptyList();
	}

}