package com.efe.core.models.impl;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MapDirectionImpl.class);

	/** The SlingHttpServletRequest. */
	@SlingObject
	private SlingHttpServletRequest request;

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;
	
	/** The seo service. */
	@OSGiService
	private SeoService seoService;

	/** The current resource. */
	@SlingObject
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

	/** The direction button label. */
	@ValueMapValue
	private String directionButtonLabel;

	/** The review question. */
	@ValueMapValue
	private String reviewQuestion;

	/** The review link label. */
	@ValueMapValue
	private String reviewLinkLabel;

	/** The efe service. */
	@OSGiService
	private EfeService efeService;
	
	/** The externalizer. */
	@OSGiService
	private Externalizer externalizer;
	
	/** The location response. */
	private LocationResponse locationResponse;

	/** The is empty. */
	private boolean isEmpty;

	/** The google direction path. */
	private String googleDirectionPath;

	/** The map key. */
	private String mapKey;

	/** The json ld. */
	private String jsonLd;

	/**
	 * Inits the.
	 */
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

						locationResponse = LocationPlannerUtil.getLocationInfo(item);

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

	/**
	 * Gets the location response.
	 *
	 * @return the location response
	 */
	@Override
	public LocationResponse getLocationResponse() {
		return locationResponse;
	}

	/**
	 * Gets the heading.
	 *
	 * @return the heading
	 */
	@Override
	public String getHeading() {
		return heading;
	}

	/**
	 * Gets the zoom level.
	 *
	 * @return the zoomLevel
	 */
	@Override
	public int getZoomLevel() {
		return zoomLevel;
	}

	/**
	 * Checks if is empty.
	 *
	 * @return the isEmpty
	 */
	@Override
	public boolean isEmpty() {
		return isEmpty;
	}

	/**
	 * Gets the google direction path.
	 *
	 * @return the googleDirectionPath
	 */
	@Override
	public String getGoogleDirectionPath() {
		return googleDirectionPath;
	}

	/**
	 * Gets the direction button label.
	 *
	 * @return the directionButtonLabel
	 */
	@Override
	public String getDirectionButtonLabel() {
		return directionButtonLabel;
	}

	/**
	 * Gets the review question.
	 *
	 * @return the reviewQuestion
	 */
	@Override
	public String getReviewQuestion() {
		return reviewQuestion;
	}

	/**
	 * Gets the review link label.
	 *
	 * @return the reviewLinkLabel
	 */
	@Override
	public String getReviewLinkLabel() {
		return reviewLinkLabel;
	}

	/**
	 * Gets the map key.
	 *
	 * @return the mapKey
	 */
	@Override
	public String getMapKey() {
		return mapKey;
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

}