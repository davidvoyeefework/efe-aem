package com.efe.core.models.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;


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
			state="al";
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
						if ((item.getPath().contains("national-advisor-center"))) {
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
		plannerTitle = plannerTitle.replace(SELECTOR_PLACEHOLDER_0, city);
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
                String bioVideo = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(), "bioVideo");
				String youtubeID = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(), "youTubeid");

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
						String cityProperty = ResourceUtil.getProperty(resourceResolver, pathBuilder, "id" );
						officeLocationsDecoded.add(cityProperty);
					}
				}

				// Clean the list, sort and remove duplicates and array brackets
				Collections.sort(officeLocationsDecoded);
				List<String> officeLocationsDecodedDuplicatesRemoved = removeDuplicates(officeLocationsDecoded);
				String stringToRemove = "143"; // 143 is reference to national planner center in AZ.
				officeLocationsDecodedDuplicatesRemoved.remove(stringToRemove);
				List<String> externalOfficeNames = officeIDtoExternalName(officeLocationsDecodedDuplicatesRemoved);
				plannerObj.setOfficeLocations(externalOfficeNames.toString().replace("[", "").replace("]", ""));

				if(StringUtils.isNotEmpty(firstNameAlias)) {
					plannerObj.setFirstName(firstNameAlias);
				} else {
					plannerObj.setFirstName(firstName);
				}
				if(StringUtils.isNotEmpty(bioVideo)) {
					plannerObj.setBioVideo(bioVideo);
				}
				if(StringUtils.isNotEmpty(youtubeID)) {
					plannerObj.setYoutubeID(youtubeID);
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

    // --- Static map of office IDs to names ---
    private static final Map<String, String> OFFICE_ID_TO_NAME = createOfficeMap();

	private static Map<String, String> createOfficeMap() {
    	Map<String, String> map = new HashMap<>();

        // --- AL Alabama ---
        map.put("28", "Birmingham");
        map.put("138", "Huntsville");

        // --- AZ Arizona ---
        map.put("98", "Phoenix");
        map.put("207", "Emerging Investors");
        map.put("175", "Chandler");

        // --- CA California ---
        map.put("15", "Walnut Creek");
        map.put("222", "Woodland");
        map.put("38", "Roseville");
        map.put("64", "Fresno");
        map.put("114", "Elk Grove");
        map.put("128", "San Rafael");
        map.put("135", "El Segundo");
        map.put("137", "Anaheim");
        map.put("159", "Costa Mesa");
        map.put("160", "Pasadena");
        map.put("162", "Woodland Hills");
        map.put("178", "San Diego");
        map.put("179", "Silicon Valley");
        map.put("223", "Seal Beach");
        map.put("224", "Danville");

        // --- CO Colorado ---
        map.put("52", "Westminster");
        map.put("53", "Englewood");
        map.put("78", "Colorado Springs");
        map.put("121", "Loveland");

        // --- CT Connecticut ---
        map.put("157", "Farmington");

        // --- FL Florida ---
        map.put("24", "Tampa-East");
        map.put("35", "Melbourne");
        map.put("123", "Tampa-Westshore");
        map.put("163", "Boca Raton");
        map.put("164", "Miami-Coral Gables");
        map.put("172", "Orlando");
        map.put("142", "Naples");
        map.put("226", "Miami-Plantation");
        map.put("237", "St. Petersburg");

        // --- GA Georgia ---
        map.put("72", "Duluth");
        map.put("84", "Vinings");
        map.put("94", "Alpharetta");
        map.put("129", "Atlanta");

        // --- IL Illinois ---
        map.put("62", "Oak Brook");
        map.put("79", "Mokena");
        map.put("151", "Northbrook");
        map.put("48", "Fairview Heights");

        // --- IN Indiana ---
        map.put("2", "Indianapolis");
        map.put("4", "Fort Wayne");
        map.put("119", "Greenwood");

        // --- IA Iowa ---
        map.put("75", "Des Moines");

        // --- KS Kansas ---
        map.put("1", "Overland Park");
        map.put("74", "Wichita");
		

        // --- KY Kentucky ---
        map.put("19", "Louisville");
        map.put("65", "Lexington");
        map.put("90", "Florence");

        // --- LA Louisiana ---
        map.put("11", "Metairie");
        map.put("32", "Baton Rouge");
        map.put("80", "Mandeville");

        // --- MA Massachusetts ---
        map.put("22", "Springfield");
        map.put("148", "Burlington");
        map.put("238", "Quincy");
        map.put("243", "Norwood");

        // --- MD Maryland ---
        map.put("145", "Annapolis");
        map.put("146", "Howard County");
        map.put("147", "Towson");
        map.put("185", "North Bethesda");
        map.put("186", "Silver Spring");

        // --- MI Michigan ---
        map.put("10", "Grand Rapids");
        map.put("50", "Northville");
        map.put("105", "Ann Arbor");
        map.put("155", "Novi");
        map.put("156", "Troy");
        map.put("247", "Kalamazoo");

        // --- MN Minnesota ---
        map.put("67", "Woodbury");
        map.put("125", "Rochester");
        map.put("31", "Minnetonka");

        // --- MO Missouri ---
        map.put("6", "Chesterfield");
        map.put("47", "Liberty");
        map.put("81", "St. Louis");
        map.put("83", "Lee's Summit");

        // --- NE Nebraska ---
        map.put("3", "Omaha");

        // --- NV Nevada ---
        map.put("7", "Las Vegas");

        // --- NJ New Jersey ---
        map.put("165", "Paramus");
        map.put("168", "Red Bank");
        map.put("169", "Short Hills");
        map.put("173", "Cherry Hill");

        // --- NM New Mexico ---
        map.put("12", "Albuquerque");

        // --- NY New York ---
        map.put("36", "Rochester");
        map.put("39", "Syracuse");
        map.put("40", "Albany");
        map.put("99", "Amherst");
        map.put("166", "Grand Central");
        map.put("167", "Long Island");
        map.put("170", "Staten Island");
        map.put("171", "White Plains");

        // --- NC North Carolina ---
        map.put("13", "Charlotte");
        map.put("44", "Greensboro");
        map.put("60", "Raleigh");

        // --- OH Ohio ---
        map.put("8", "Cincinnati");
        map.put("16", "Westlake");
        map.put("29", "Toledo");
        map.put("57", "Dayton");
        map.put("89", "Beachwood");
        map.put("96", "Youngstown");
        map.put("100", "Akron");
        map.put("228", "Columbus");

        // --- OK Oklahoma ---
        map.put("25", "Oklahoma City");
        map.put("91", "Norman");
        map.put("236", "Oklahoma City-North");

        // --- OR Oregon ---
        map.put("37", "Portland");

        // --- PA Pennsylvania ---
        map.put("34", "Sewickley");
        map.put("58", "Allentown");
        map.put("59", "Harrisburg");
        map.put("92", "South Hills");
        map.put("133", "Chadds Ford");
        map.put("33", "Conshohocken");

        // --- RI Rhode Island ---
        map.put("54", "East Greenwich");

        // --- SC South Carolina ---
        map.put("55", "Charleston");
        map.put("71", "Columbia");

        // --- TN Tennessee ---
        map.put("14", "Brentwood");
        map.put("41", "Memphis");
        map.put("42", "Knoxville");

        // --- TX Texas ---
        map.put("27", "San Antonio");
        map.put("46", "Houston-Clear Lake/League City");
        map.put("110", "The Woodlands");
        map.put("118", "Sugar Land");
        map.put("154", "Dallas");
        map.put("209", "Austin");
        map.put("158", "West Houston");

        // --- UT Utah ---
        map.put("177", "Salt Lake City");

        // --- VA Virginia ---
        map.put("20", "Virginia Beach");
        map.put("68", "Newport News");
        map.put("176", "Henrico");
        map.put("182", "Alexandria");
        map.put("183", "Fairfax");
        map.put("184", "Loudoun County");
        map.put("187", "Tysons Corner");

        // --- WA Washington ---
        map.put("56", "Federal Way");
        map.put("61", "Lynnwood");
        map.put("76", "Bellevue");
        map.put("77", "Vancouver");
        map.put("241", "Kirkland");

        // --- WI Wisconsin ---
        map.put("26", "Waukesha");
        map.put("70", "Madison");
        map.put("102", "Appleton");
        map.put("124", "Glendale");

        return map;
    }

	private List<String> officeIDtoExternalName(List<String> officeIDlist) {
    return officeIDlist.stream()
            .map(OFFICE_ID_TO_NAME::get)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .collect(Collectors.toList()); 
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
