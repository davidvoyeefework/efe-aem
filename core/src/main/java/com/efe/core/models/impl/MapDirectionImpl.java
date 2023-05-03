package com.efe.core.models.impl;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.Externalizer;
import com.day.cq.dam.api.DamConstants;
import com.efe.core.bean.LocationResponse;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.models.MapDirection;
import com.efe.core.services.EfeService;
import com.efe.core.services.SeoService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LocationPlannerUtil;
import com.efe.core.utils.SeoUtil;

/**
 * The Class MapDirectionImpl.
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = MapDirection.class, resourceType = {
		LocationListImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class MapDirectionImpl implements MapDirection {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/mapdirection";

	/**
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MapDirectionImpl.class);

	/** The SlingHttpServletRequest. */
	@SlingObject
	private SlingHttpServletRequest request;

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;
	
	@OSGiService
	private SeoService seoService;

	/** The current resource. */
	@Self
	private Resource resource;

	/** The id. */
	@ValueMapValue
	private String id;

	/** The address heading. */
	@ValueMapValue
	private String heading;

	/** The zoom level. */
	@ValueMapValue
	@Default(intValues = 15)
	private int zoomLevel;

	@ValueMapValue
	private String directionButtonLabel;

	@ValueMapValue
	private String reviewQuestion;

	@ValueMapValue
	private String reviewLinkLabel;

	@OSGiService
	private EfeService efeService;
	
	@OSGiService
	private Externalizer externalizer;
	
	private LocationResponse locationResponse;

	private boolean isEmpty;

	private String googleDirectionPath;

	private String mapKey;

	private String jsonLd;

	@PostConstruct
	public void init() {
		isEmpty = true;
		locationResponse = new LocationResponse();
		String[] selectors = request.getRequestPathInfo().getSelectors();
		if (selectors.length == 2) {
			final String state = selectors[0].toLowerCase();
			final String city = selectors[1].toLowerCase().replaceAll(PlannerLocationConstants.HYPHEN,
					PlannerLocationConstants.SPACE);
			final Resource resourceLocation = LocationPlannerUtil.getLocationResource(resourceResolver, state, city);
			if (Objects.nonNull(resourceLocation)) {

				for (Resource item : resourceLocation.getChildren()) {

					if (item.isResourceType(DamConstants.NT_DAM_ASSET)) {

						Optional<ContentFragment> locationCF = Optional.ofNullable(item.adaptTo(ContentFragment.class));

						locationResponse.setCity(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.CITY))
								.map(ContentElement::getContent).orElse(StringUtils.EMPTY));

						locationResponse.setState(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.STATE))
								.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
						locationResponse
								.setAddress1(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.ADDRESS_1))
										.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
						locationResponse
								.setAddress2(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.ADDRESS_2))
										.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
						locationResponse.setZip(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.ZIP))
								.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
						locationResponse
								.setLatitude(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.LATITUDE))
										.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
						locationResponse
								.setLongitude(locationCF.map(cf -> cf.getElement(PlannerLocationConstants.LONGITUTE))
										.map(ContentElement::getContent).orElse(StringUtils.EMPTY));
						locationResponse.setGoogleReviewLink(
								locationCF.map(cf -> cf.getElement(PlannerLocationConstants.GOOGLE_REVIEW_LINK))
										.map(ContentElement::getContent).orElse(StringUtils.EMPTY));

						googleDirectionPath = efeService.getGoogleDirectionPrefixUrl() +locationResponse.getAddress1()
								+ "," + locationResponse.getCity() + "+" + locationResponse.getState();
						
						mapKey = efeService.getGooglePublicKey();
						
						jsonLd = SeoUtil.getLocationSEO(request, externalizer, locationResponse, seoService);
						
						isEmpty = false;
					}
				}

			} else {
				LOGGER.error("Location Content fragment not found : State={}, City={}", state, city);
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

	@Override
	public LocationResponse getLocationResponse() {
		return locationResponse;
	}

	/**
	 * @return the heading
	 */
	@Override
	public String getHeading() {
		return heading;
	}

	/**
	 * @return the zoomLevel
	 */
	@Override
	public int getZoomLevel() {
		return zoomLevel;
	}

	/**
	 * @return the isEmpty
	 */
	@Override
	public boolean isEmpty() {
		return isEmpty;
	}

	/**
	 * @return the googleDirectionPath
	 */
	@Override
	public String getGoogleDirectionPath() {
		return googleDirectionPath;
	}

	/**
	 * @return the directionButtonLabel
	 */
	@Override
	public String getDirectionButtonLabel() {
		return directionButtonLabel;
	}

	/**
	 * @return the reviewQuestion
	 */
	@Override
	public String getReviewQuestion() {
		return reviewQuestion;
	}

	/**
	 * @return the reviewLinkLabel
	 */
	@Override
	public String getReviewLinkLabel() {
		return reviewLinkLabel;
	}

	/**
	 * @return the mapKey
	 */
	@Override
	public String getMapKey() {
		return mapKey;
	}

	@Override
	public String getJsonLd() {
		return jsonLd;
	}

}