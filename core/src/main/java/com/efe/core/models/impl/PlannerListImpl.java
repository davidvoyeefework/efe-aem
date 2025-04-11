package com.efe.core.models.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.annotation.PostConstruct;

import com.efe.core.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.gfx.Plan;
import com.day.cq.dam.api.DamConstants;
import com.efe.core.bean.OfficesLocations;
import com.efe.core.bean.PlannerDetail;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.models.PlannerList;
import com.efe.core.services.EfeService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;
import com.efe.core.utils.LocationPlannerUtil;
import com.efe.core.utils.OfficeLocationsUtil;
import com.efe.core.utils.ResourceUtil;

/**
 * The Class PlannerListImpl.
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = PlannerList.class, resourceType = {
		PlannerListImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PlannerListImpl implements PlannerList {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/plannerlist";

	/** The Constant SELECTOR_PLACEHOLDER_1. */
	private static final String SELECTOR_PLACEHOLDER_1 = "{1}";

	/** The Constant SELECTOR_PLACEHOLDER_0. */
	private static final String SELECTOR_PLACEHOLDER_0 = "{0}";

	/** The SlingHttpServletRequest. */
	@SlingObject
	private SlingHttpServletRequest request;

	@SlingObject
	private SlingHttpServletResponse response;

	@ValueMapValue
	private String defaultRedirectPagePath;

	/** The PlannerDetails. */
	private List<PlannerDetail> plannerDetails = new ArrayList<>();

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/**
	 * Injecting efeService
	 *
	 */
	@OSGiService
	private EfeService efeService;

	/** The current resource. */
	@SlingObject
	private Resource resource;

	/** The id. */
	@ValueMapValue
	private String id;

	/** The Planner target. */
	@ValueMapValue
	private String plannerTarget;

	/** The CTA Label. */
	@ValueMapValue
	private String ctaLabel;

	/** The Planner title. */
	@ValueMapValue
	private String plannerTitle;

	@ValueMapValue
	private String stateValueMap;

	/** The national planner title. */
	@ValueMapValue
	private String nationalPlannerTitle;

	/** The State. */
	private String state;

	/** The City. */
	private String city;

	public String[] officeLocationsEncoded;

	public ArrayList<String> officeLocationsEncodedSubstring = new ArrayList<String>();


	/**
	 * This method sets the planner values in bean class according to selectors
	 * value.
	 */
	@PostConstruct
	protected void init() {

		String[] selectors = request.getRequestPathInfo().getSelectors();
		if (selectors.length == 2) {
			List<String> cfList = new ArrayList<>();
			state = selectors[0].toLowerCase();
			String citySelector = selectors[1].toLowerCase();
			Resource resourceLocation = LocationPlannerUtil.getLocationResource(resourceResolver, state, citySelector);
			if (Objects.nonNull(resourceLocation)) {
				for (Resource item : resourceLocation.getChildren()) {
					setCfList(cfList, item);
				}
			} else {
				try {
					response.sendRedirect(getDefaultRedirectPagePath());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			setPlannerDetails(cfList);
			if (plannerTitle != null) {
				setPlannerTitle(citySelector);
			}

	
		}

		if (selectors.length == 0) {
			state="az";
			if(stateValueMap != null) {
				state=stateValueMap;
			}
			List<String> cfList = new ArrayList<>();
			List <String> finalPlannerCFList = new ArrayList<>();
			List <String> PlannerCFTagsArray = new ArrayList<>();
			Resource resourceLocation1 = LocationPlannerUtil.getLocationResourceStateDirectory(resourceResolver, state);
			Iterator<Resource> children = resourceLocation1.listChildren();
			while (children.hasNext()) {
				final Resource childState = children.next();
				if (Objects.nonNull(childState)) {
					for (Resource item : childState.getChildren()) {
						if (item.getPath().contains("national-advisor-center") ) {
							break;
						}
						else {
							setCfList(cfList, item);
						}
					}
				}
				else {
					try {
						response.sendRedirect(getDefaultRedirectPagePath());
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
			
			// Removing duplicate planners that are associated with more than 1 city
			List <String> listWithoutDuplicates = removeDuplicates(cfList);
			cfList = listWithoutDuplicates;

			// Sort cfList by first name
			for (String plannerCF : cfList) {
				Integer lastIndexSlash = plannerCF.lastIndexOf("/");
				if (lastIndexSlash != -1) {
					String plannerCFTag = plannerCF.substring(lastIndexSlash + 1);
					PlannerCFTagsArray.add(plannerCFTag);
				}
			}
			 Collections.sort(PlannerCFTagsArray);

			// Build Sorted Cleaned Planner List Path for Specific State
			 for (String plannerCFSlice: PlannerCFTagsArray) {
				Integer lastUnderscore = plannerCFSlice.lastIndexOf("_");
				if (lastUnderscore != -1) { 
					String plannerId = plannerCFSlice.substring(lastUnderscore + 1);
					String plannerPathBuilder = "/content/dam/efe/cf/plannerlocation/planners";
					plannerPathBuilder = plannerPathBuilder + "/" + plannerId + "/" + plannerCFSlice;
					finalPlannerCFList.add(plannerPathBuilder);
				}
			 }
			setPlannerDetails(finalPlannerCFList);

		}
		
	}

	public static List<String> removeDuplicates(List<String> listWithDuplicates) {
        Set<String> set = new LinkedHashSet<>(listWithDuplicates);
        return new ArrayList<>(set);
    }

	private void setPlannerTitle(String citySelector) {
		plannerTitle = plannerTitle.replace(SELECTOR_PLACEHOLDER_0, citySelector);
		plannerTitle = plannerTitle.replace(SELECTOR_PLACEHOLDER_1, state);
		if(StringUtils.equalsIgnoreCase(citySelector, efeService.getNationalAdvisorCenter())) {
			plannerTitle = nationalPlannerTitle;
		}
	}

	/**
	 * Sets the planner details.
	 *
	 * @param cfList the new planner details
	 */
	private void setPlannerDetails(List<String> cfList) {
		for (String item : cfList) {
			ArrayList<String> officeLocationsDecoded = new ArrayList<String>();
			officeLocationsEncodedSubstring.clear();
			PlannerDetail plannerObj = new PlannerDetail();
			Resource planner = resourceResolver.getResource(item);
			if (Objects.nonNull(planner)) {
				Resource plannerMaster = resourceResolver
						.getResource(planner.getPath() + PlannerLocationConstants.MASTER_NODE);
				
				if(plannerMaster == null) {
					continue;
				}
				String firstName = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(), "firstName");
				String firstNameAlias = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(), "firstNameAlias");
				String lastName = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(), "lastName");
				String title = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(), "title");
				String imageUrl = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(),
						"desktopImageurl");
				String plannerId = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(), "id");

				// Get all office locations folder names associated with Planner and isolate just array of folder names not whole path
				officeLocationsEncoded = ResourceUtil.getProperties(resourceResolver, plannerMaster.getPath(), "officeslocations");
				for(String office: officeLocationsEncoded) {
					Integer lastIndexSlash = office.lastIndexOf("/");
					if (lastIndexSlash != -1) { 
						String plannerOfficeFolderString = office.substring(lastIndexSlash + 1);
						officeLocationsEncodedSubstring.add(plannerOfficeFolderString);
					}
				}

				// Alter planner path folder directory to root and iterate over all locations associated and add the correlated city to String array
				String badPlannerPath = planner.getPath();
				Integer lastIndexofSlash1 = badPlannerPath.lastIndexOf("/");
				if (lastIndexofSlash1 != -1) { 
					String goodPlannerPath = badPlannerPath.substring(0, lastIndexofSlash1 + 1);
					for (String officeFolderLocation : officeLocationsEncodedSubstring) {
						String pathBuilder = goodPlannerPath + "officeslocations/" + officeFolderLocation + "/jcr:content/data/master";		
						String cityProperty = ResourceUtil.getProperty(resourceResolver, pathBuilder, "city" );
						officeLocationsDecoded.add(cityProperty);
					}
				}

				// Clean the list, sort and remove duplicates and array brackets
				Collections.sort(officeLocationsDecoded);
				List<String> officeLocationsDecodedDuplicatesRemoved = removeDuplicates(officeLocationsDecoded);
				plannerObj.setOfficeLocations(officeLocationsDecodedDuplicatesRemoved.toString().replace("[", "").replace("]", ""));

				if(StringUtils.isNotEmpty(firstNameAlias)) {
					plannerObj.setFirstName(firstNameAlias);
				} else {
					plannerObj.setFirstName(firstName);
				}
				plannerObj.setLastName(lastName);
				plannerObj.setTitle(title);
				plannerObj.setDesktopUrl(imageUrl);
				plannerObj.setButtonUrl(LinkUtil.getFormattedLink(efeService.getPlannerBioPageUrl()+ PlannerLocationConstants.DOT + firstName
								+ PlannerLocationConstants.DOT + lastName + PlannerLocationConstants.DOT + plannerId,
						resourceResolver));					
			}
		
			plannerDetails.add(plannerObj);
		}
	}

	/**
	 * Sets the cf list.
	 *
	 * @param cfList the cf list
	 * @param item   the item
	 */
	private void setCfList(List<String> cfList, Resource item) {
		if (item.isResourceType(DamConstants.NT_DAM_ASSET)) {
			Resource masterResource = resourceResolver.getResource(item.getPath() + PlannerLocationConstants.MASTER_NODE);
			if(Objects.nonNull(masterResource)) {
				city = ResourceUtil.getProperty(resourceResolver, masterResource.getPath(), "city");
				String[] plannerList = ResourceUtil.getProperties(resourceResolver, masterResource.getPath(), "planners");
				for (String list : plannerList) {
					cfList.add(list);
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
	 * Gets the planner target.
	 *
	 * @return the planner target
	 */

	@Override
	public String getPlannerTarget() {
		return plannerTarget;
	}

	/**
	 * Gets the CTA label.
	 *
	 * @return the CTA label
	 */

	@Override
	public String getCtaLabel() {
		return ctaLabel;
	}

	/**
	 * Gets the Planner title.
	 *
	 * @return the Planner title
	 */

	@Override
	public String getPlannerTitle() {
		return plannerTitle;
	}

	/**
	 * Gets the State.
	 *
	 * @return the State
	 */
	@Override
	public String getState() {
		return state.toUpperCase();
	}

	/**
	 * Gets the City.
	 *
	 * @return the City
	 */
	@Override
	public String getCity() {
		return city.toUpperCase();
	}

	/**
	 * Gets the PlannerList.
	 *
	 * @return the PlannerList
	 */
	@Override
	public List<PlannerDetail> getPlannerList() {
		if (Objects.nonNull(plannerDetails)) {
			return new ArrayList<>(plannerDetails);
		}
		return Collections.emptyList();
	}

	@Override
	public String getDefaultRedirectPagePath() {
		if (StringUtils.isBlank(defaultRedirectPagePath))
			defaultRedirectPagePath = "/locations";
		return defaultRedirectPagePath.concat(Constants.HTML_SUFFIX);
	}	

}
