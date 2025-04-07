package com.efe.core.models.impl;

import java.util.Objects;

import javax.annotation.PostConstruct;

import com.day.cq.dam.commons.util.DamUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.dam.api.DamConstants;
import com.efe.core.bean.LocationResponse;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.constants.StatesEnum;
import com.efe.core.models.MapStateLocations;
import com.efe.core.services.EfeService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;
import com.efe.core.utils.LocationPlannerUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The Class MapSearchResultsImpl.
 */
@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = MapStateLocations.class, resourceType = {
				MapStateLocationsImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class MapStateLocationsImpl implements MapStateLocations {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/mapstatelocations";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MapStateLocationsImpl.class);

	/** The SlingHttpServletRequest. */
	@SlingObject
	private SlingHttpServletRequest request;

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The current resource. */
	@SlingObject
	private Resource resource;

	/** The efe service. */
	@OSGiService
	private EfeService efeService;

	/** The id. */
	@ValueMapValue
	private String id;

	/** The plannerSearchLabel. */
	@ValueMapValue
	private String plannerSearchLabel;

	/** The plannerSearchPlaceHolder. */
	@ValueMapValue
	private String plannerSearchPlaceHolder;

	/** The plannerButtonLabel. */
	@ValueMapValue
	private String plannerButtonLabel;

	/** The plannerButtonLabel. */
	@ValueMapValue
	private String searchButtonLabel;
	
	/** The exploreLinkLabel. */
	@ValueMapValue
	private String exploreLinkLabel;

	/** The zoom level. */
	@ValueMapValue
	private int zoomLevel;
	
	/** The national advisor title. */
	@ValueMapValue
	private String nationalTitle;
	
	/** The national advisor callback label. */
	@ValueMapValue
	private String nationalCallbackNumberLabel;
	
	/** The national advisor callback number. */
	@ValueMapValue
	private String nationalCallbackNumber;
	
	/** The national advisor details. */
	@ValueMapValue
	private String nationalDetails;
	
	/** The national advisor link. */
	@ValueMapValue
	private String nationalLink;
        
        /** The state to display */
        @ValueMapValue
        private String mapState;

	/** The map key. */
	private String mapKey;

	/** The office location json. */
	private String officeLocationJson;

	/**
	 * Inits the Model.
	 */
	@PostConstruct
	public void init() {

		mapKey = efeService.getGooglePublicKey();

		JsonArray officeLocationArr = new JsonArray();
                if(mapState.isEmpty()) {
                    mapState = "AK";
                }
                Resource stateResource = resourceResolver.getResource(PlannerLocationConstants.LOCATION_PATH + "/" + mapState);

                if (Objects.nonNull(stateResource) && stateResource.isResourceType(JcrResourceConstants.NT_SLING_ORDERED_FOLDER)) {
                    createNAddOfficeObject(officeLocationArr, stateResource);
                } else {
                    LOGGER.debug("Locations CF Node not found.");
                }

		officeLocationJson = officeLocationArr.toString();
	}

	/**
	 * Method to create Office Json Object.
	 *
	 * @param officeLocationArr array in which object is added
	 * @param stateResource     state resource node
	 */
	private void createNAddOfficeObject(JsonArray officeLocationArr, Resource stateResource) {
		for (Resource cityResource : stateResource.getChildren()) {
			for (Resource item : cityResource.getChildren()) {
				if (item.isResourceType(DamConstants.NT_DAM_ASSET)) {
					String cityExternalName = LocationPlannerUtil.getLocationProperty(resourceResolver, cityResource, "externalName");
					if(Objects.nonNull(cityExternalName)) {
						JsonObject officeObject = new JsonObject();
						LocationResponse locationBean = LocationPlannerUtil.getLocationInfo(item);
						officeObject.addProperty(PlannerLocationConstants.EXTERNAL_NAME, locationBean.getExternalName());
						officeObject.addProperty(PlannerLocationConstants.ADDRESS_1, locationBean.getAddress1());
						officeObject.addProperty(PlannerLocationConstants.ADDRESS_2, locationBean.getAddress2());
						officeObject.addProperty(PlannerLocationConstants.CITY, locationBean.getCity());
						officeObject.addProperty(PlannerLocationConstants.LATITUDE, locationBean.getLatitude());
						officeObject.addProperty(PlannerLocationConstants.LONGITUTE, locationBean.getLongitude());
						officeObject.addProperty(PlannerLocationConstants.ZIP, locationBean.getZip());

						StatesEnum stateEnum = StatesEnum.valueOf(stateResource.getName().toUpperCase());
						officeObject.addProperty(PlannerLocationConstants.STATE, stateEnum.getStateName());
						officeObject.addProperty("stateCode", locationBean.getState());
					
						final String cityUrl = LinkUtil.getFormattedLink(
							efeService.getPlannerPageUrl() + PlannerLocationConstants.DOT
									+ stateResource.getName().toUpperCase() + PlannerLocationConstants.DOT
									+ DamUtil.getSanitizedFolderName(cityExternalName, false),
							resourceResolver);

						officeObject.addProperty("link", cityUrl);
						officeLocationArr.add(officeObject);	
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
	 * Gets the offices json.
	 *
	 * @return the offices json
	 */
	@Override
	public String getOfficesJson() {
		return officeLocationJson;
	}

	/**
	 * Gets the map key.
	 *
	 * @return the map key
	 */
	@Override
	public String getMapKey() {
		return mapKey;
	}

	/**
	 * Gets the planner search label.
	 *
	 * @return the planner search label
	 */
	@Override
	public String getPlannerSearchLabel() {
		return plannerSearchLabel;
	}

	/**
	 * Gets the planner search place holder.
	 *
	 * @return the planner search place holder
	 */
	@Override
	public String getPlannerSearchPlaceHolder() {
		return plannerSearchPlaceHolder;
	}

	/**
	 * Gets the planner button label.
	 *
	 * @return the planner button label
	 */
	@Override
	public String getPlannerButtonLabel() {
		return plannerButtonLabel;
	}

	/**
	 * Gets the search button label.
	 *
	 * @return the search button label
	 */
	@Override
	public String getSearchButtonLabel() {
		return searchButtonLabel;
	}

	/**
	 * Gets the explore link label.
	 *
	 * @return the explore link label
	 */
	@Override
	public String getExploreLinkLabel() {
		return exploreLinkLabel;
	}

	/**
	 * Gets the national title.
	 *
	 * @return the national title
	 */
	@Override
	public String getNationalTitle() {
		return nationalTitle;
	}

	/**
	 * Gets the national callback number.
	 *
	 * @return the national callback number
	 */
	@Override
	public String getNationalCallbackNumber() {
		return nationalCallbackNumber;
	}

	/**
	 * Gets the national link.
	 *
	 * @return the national link
	 */
	@Override
	public String getNationalLink() {
		String nationalLinkVal = null;
		if(StringUtils.isNotEmpty(nationalLink)) {
			nationalLinkVal =  LinkUtil.getFormattedLink(nationalLink, resourceResolver);
		}
		return nationalLinkVal;
	}
        
        /**
	 * Method to return the state to display.
	 *
	 * @return the mapState
	 */
        @Override
        public String getMapState() {
            return mapState;
        }

	/**
	 * Gets the national details.
	 *
	 * @return the national details
	 */
	@Override
	public String getNationalDetails() {
		return nationalDetails;
	}

	@Override
	public String getNationalCallbackLabel() {
		return nationalCallbackNumberLabel;
	}

}
