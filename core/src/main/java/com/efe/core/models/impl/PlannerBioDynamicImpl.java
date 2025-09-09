package com.efe.core.models.impl;

import java.io.IOException;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.day.cq.dam.api.DamConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.efe.core.constants.Constants;
import com.efe.core.services.DynamicMediaService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.Externalizer;
import com.efe.core.bean.LocationResponse;
import com.efe.core.bean.PlannerResponse;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.models.PlannerBioDynamic;
import com.efe.core.services.EfeService;
import com.efe.core.services.SeoService;
import com.efe.core.utils.ArticleDetailUtil;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;
import com.efe.core.utils.LocationPlannerUtil;
import com.efe.core.utils.SeoUtil;

/**
 * The Class PlannerBioImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class }, adapters = PlannerBioDynamic.class, resourceType = {
		PlannerBioDynamicImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PlannerBioDynamicImpl implements PlannerBioDynamic {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/plannerbio";
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerBioImpl.class);


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

	/** The dynamicMediaService. */
	@OSGiService
	private DynamicMediaService dynamicMediaService;

	/** The current resource. */
	@SlingObject
	private Resource resource;

	@SlingObject
	private SlingHttpServletResponse response;

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The id. */
	@ValueMapValue
	private String id;

	/** The appointment cta label. */
	@ValueMapValue
	private String appointmentCtaLabel;

	/** The appointment cta. */
	@ValueMapValue
	private String appointmentCta;

	/** The about section heading. */
	@ValueMapValue
	private String aboutSectionHeading;

	/** The about me heading. */
	@ValueMapValue
	private String aboutMeHeading;

	/** The certification heading. */
	@ValueMapValue
	private String certificationHeading;

	/** The education heading. */
	@ValueMapValue
	private String educationHeading;

	/** The location section heading. */
	@ValueMapValue
	private String locationSectionHeading;

	/** The explore link label. */
	@ValueMapValue
	private String exploreLinkLabel;
	
	/** The file reference. */
	@ValueMapValue
	private String fileReference;

	@OSGiService
	private QueryBuilder queryBuilder;

	/** The json ld. */
	private String jsonLd;

	/** The planner response. */
	private PlannerResponse plannerResponse;

	/** The office locations. */
	private List<LocationResponse> officeLocations;

	/** The filter. */
	private String[] filter;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	public void init() { 
		String[] selectors = request.getRequestPathInfo().getSelectors();
		if (selectors.length == 3) {
			officeLocations = new ArrayList<>();
			Resource plannerResource = LocationPlannerUtil.getPlannerResource(resourceResolver, selectors[0],
					selectors[2]);
			if (null != plannerResource && null != plannerResource.adaptTo(ContentFragment.class)) {
				plannerResponse = ArticleDetailUtil.getPlannerDetails(resourceResolver, plannerResource, efeService);
				String firstNameAlias = plannerResponse.getFirstNameAlias();
				if(StringUtils.isNotEmpty(firstNameAlias)) {
					plannerResponse.setFirstName(firstNameAlias);
				}
				List<Resource> referenceResourceList = createReferenceResourceList(plannerResource);
				if (!referenceResourceList.isEmpty()) {
					addPlannerOffices(referenceResourceList);
				}
				jsonLd = SeoUtil.getPlannerSchema(seoService, efeService, externalizer, resourceResolver, plannerResponse);	
			} else {
					throw new RuntimeException();
			}

		}
	}

	private List<Resource> createReferenceResourceList(Resource plannerResource) {
		List<Resource> resources = new ArrayList<Resource>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("path", PlannerLocationConstants.LOCATION_PATH);
		map.put("type", DamConstants.NT_DAM_ASSET);
		map.put("1_property", "jcr:content/data/master/planners");
		map.put("1_property.value", plannerResource.getPath());
		map.put("2_property", "jcr:content/data/cq:model");
		map.put("2_property.value", PlannerLocationConstants.LOCATIONS_CF_MODEL_PATH);
		map.put("p.guessTotal", "true");
		map.put("p.limit", "100");
		Query query = queryBuilder.createQuery(PredicateGroup.create(map), resourceResolver.adaptTo(Session.class));
		if(Objects.nonNull(query)) {
			SearchResult result = query.getResult();
			ResourceResolver leakingResourceResolver = null;
			if(Objects.nonNull(result)) {
				try {
					for (final Hit hit : result.getHits()) {
						if (leakingResourceResolver == null) {
							leakingResourceResolver = hit.getResource().getResourceResolver();
						}
						Resource hitResource = resourceResolver.getResource(hit.getPath());
						if(Objects.nonNull(hitResource)) {
							resources.add(hitResource);
						}
					}
				} catch (RepositoryException e) {
					LOGGER.error("Error collecting search results", e);
				} finally {
					if (leakingResourceResolver != null) {
						leakingResourceResolver.close();
					}
				}
			}
		}
		return resources;
	}

	/**
	 * Adds the planner offices.
	 *
	 * @param referenceList the reference list
	 */
	private void addPlannerOffices(List<Resource> referenceResourceList) {
		final Iterator<Resource> referenceItr = referenceResourceList.iterator();
		List<String> locations = new ArrayList<>();
		while (referenceItr.hasNext()) {
			Resource locationResource = referenceItr.next();
			if (Objects.nonNull(locationResource)) {
				String refPath = locationResource.getPath();
				if(locations.contains(refPath)) {
					LOGGER.debug("Duplicate reference : {}", refPath);
					continue;
				}
								
				Resource locationresource = resourceResolver.getResource(refPath);
				if (null != locationresource) {
					ContentFragment fragment = locationresource.adaptTo(ContentFragment.class);
					if (null != fragment && isLocationsCF(fragment) && locationresource.getParent() != null
							&& locationresource.getParent().getParent() != null) {
						LOGGER.debug("Office location : {}", locationresource.getPath());
						LocationResponse locationResponse = LocationPlannerUtil.getLocationInfo(locationresource);

						// set the cta url
						locationResponse.setUrl(LinkUtil.getFormattedLink(efeService.getPlannerPageUrl()
								+ PlannerLocationConstants.DOT
								+ locationresource.getParent().getParent().getName().toUpperCase()
								+ PlannerLocationConstants.DOT
								+ LocationPlannerUtil.toCamelCase(locationresource.getParent().getName())
										.replaceAll(PlannerLocationConstants.SPACE, PlannerLocationConstants.HYPHEN),
								resourceResolver));
						officeLocations.add(locationResponse);
						locations.add(refPath);
					}
				}
				LOGGER.debug("Locations array : {}", locations);
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
		if (null != fragment) {
			Resource fragmentResource = fragment.adaptTo(Resource.class);
			if (fragmentResource.hasChildren() && null != fragmentResource.getChild("jcr:content/data")) {
				final String modelPath = fragmentResource.getChild("jcr:content/data").getValueMap().get("cq:model", StringUtils.EMPTY);
				if (modelPath.startsWith("/conf/efe/settings/dam/cfm/models/locations")) {
					isLocationsCF = true;
				}
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

	/**
	 * Gets the appointment cta label.
	 *
	 * @return the appointmentCtaLabel
	 */
	@Override
	public String getAppointmentCtaLabel() {
		return appointmentCtaLabel;
	}

	/**
	 * Gets the appointment cta.
	 *
	 * @return the appointmentCta
	 */
	@Override
	public String getAppointmentCta() {
		if (StringUtils.isNotEmpty(appointmentCta)) {
			appointmentCta = LinkUtil.getFormattedLink(appointmentCta, resourceResolver);
		}
		return appointmentCta;
	}

	/**
	 * Gets the about section heading.
	 *
	 * @return the aboutSectionHeading
	 */
	@Override
	public String getAboutSectionHeading() {
		return aboutSectionHeading;
	}

	/**
	 * Gets the about me heading.
	 *
	 * @return the aboutMeHeading
	 */
	@Override
	public String getAboutMeHeading() {
		return aboutMeHeading;
	}

	/**
	 * Gets the certification heading.
	 *
	 * @return the certificationHeading
	 */
	@Override
	public String getCertificationHeading() {
		return certificationHeading;
	}

	/**
	 * Gets the education heading.
	 *
	 * @return the educationHeading
	 */
	@Override
	public String getEducationHeading() {
		return educationHeading;
	}

	/**
	 * Gets the location section heading.
	 *
	 * @return the locationSectionHeading
	 */
	@Override
	public String getLocationSectionHeading() {
		return locationSectionHeading;
	}

	/**
	 * Gets the explore link label.
	 *
	 * @return the exploreLinkLabel
	 */
	@Override
	public String getExploreLinkLabel() {
		return exploreLinkLabel;
	}

	/**
	 * Gets the file reference.
	 *
	 * @return the fileReference
	 */
	@Override
	public String getFileReference() {
		return dynamicMediaService.getDmImagePath(resourceResolver, fileReference);
	}

}