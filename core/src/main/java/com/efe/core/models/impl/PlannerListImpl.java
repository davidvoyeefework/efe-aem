package com.efe.core.models.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

private static final Map<String, String> OFFICE_ID_TO_NAME = Map.ofEntries(
    // --- AL Alabama ---
    Map.entry("28", "Birmingham"),
    Map.entry("138", "Huntsville"),

    // --- AZ Arizona ---
    Map.entry("98", "Phoenix"),
    Map.entry("207", "Emerging Investors"),
    Map.entry("175", "Chandler"),

    // --- CA California ---
    Map.entry("15", "Walnut Creek"),
    Map.entry("222", "Woodland"),
    Map.entry("38", "Roseville"),
    Map.entry("64", "Fresno"),
    Map.entry("114", "Elk Grove"),
    Map.entry("128", "San Rafael"),
    Map.entry("135", "El Segundo"),
    Map.entry("137", "Anaheim"),
    Map.entry("159", "Orange County"),
    Map.entry("160", "Pasadena"),
    Map.entry("162", "Woodland Hills"),
    Map.entry("178", "San Diego"),
    Map.entry("179", "Santa Clara"),
    Map.entry("223", "Seal Beach"),
    Map.entry("224", "Danville"),

    // --- CO Colorado ---
    Map.entry("52", "Westminster"),
    Map.entry("53", "Englewood"),
    Map.entry("78", "Colorado Springs"),
    Map.entry("121", "Loveland"),

    // --- CT Connecticut ---
    Map.entry("157", "Farmington"),

    // --- FL Florida ---
    Map.entry("24", "Tampa-East"),
    Map.entry("35", "Melbourne"),
    Map.entry("123", "Tampa-Westshore"),
    Map.entry("163", "Boca Raton"),
    Map.entry("164", "Miami-Coral Gables"),
    Map.entry("172", "Orlando"),
    Map.entry("142", "Naples"),
    Map.entry("226", "Miami-Plantation"),
    Map.entry("237", "St. Petersburg"),

    // --- GA Georgia ---
    Map.entry("72", "Duluth"),
    Map.entry("84", "Vinings"),
    Map.entry("94", "Alpharetta"),
    Map.entry("129", "Atlanta"),

    // --- IL Illinois ---
    Map.entry("62", "Oak Brook"),
    Map.entry("79", "Mokena"),
    Map.entry("151", "Northbrook"),
    Map.entry("48", "Fairview Heights"),

    // --- IN Indiana ---
    Map.entry("2", "Indianapolis"),
    Map.entry("4", "Fort Wayne"),
    Map.entry("119", "Greenwood"),

    // --- IA Iowa ---
    Map.entry("75", "Des Moines"),

    // --- KS Kansas ---
    Map.entry("1", "Overland Park"),
    Map.entry("74", "Wichita"),

    // --- KY Kentucky ---
    Map.entry("19", "Louisville"),
    Map.entry("65", "Lexington"),
    Map.entry("90", "Florence"),

    // --- LA Louisiana ---
    Map.entry("11", "Metairie"),
    Map.entry("32", "Baton Rouge"),
    Map.entry("80", "Mandeville"),

    // --- MA Massachusetts ---
    Map.entry("22", "Springfield"),
    Map.entry("148", "Burlington"),
    Map.entry("238", "Quincy"),
    Map.entry("243", "Norwood"),

    // --- MD Maryland ---
    Map.entry("145", "Annapolis"),
    Map.entry("146", "Howard County"),
    Map.entry("147", "Towson"),
    Map.entry("185", "North Bethesda"),
    Map.entry("186", "Silver Spring"),

    // --- MI Michigan ---
    Map.entry("10", "Grand Rapids"),
    Map.entry("50", "Northville"),
    Map.entry("105", "Ann Arbor"),
    Map.entry("155", "Novi"),
    Map.entry("156", "Troy"),
    Map.entry("247", "Kalamazoo"),

    // --- MN Minnesota ---
    Map.entry("67", "Woodbury"),
    Map.entry("125", "Rochester"),
    Map.entry("31", "Minnetonka"),

    // --- MO Missouri ---
    Map.entry("6", "Chesterfield"),
    Map.entry("47", "Liberty"),
    Map.entry("81", "St. Louis"),
    Map.entry("83", "Kansas City"),

    // --- NE Nebraska ---
    Map.entry("3", "Omaha"),

    // --- NV Nevada ---
    Map.entry("7", "Las Vegas"),

    // --- NJ New Jersey ---
    Map.entry("165", "Paramus"),
    Map.entry("168", "Red Bank"),
    Map.entry("169", "Short Hills"),
    Map.entry("173", "Cherry Hill"),

    // --- NM New Mexico ---
    Map.entry("12", "Albuquerque"),

    // --- NY New York ---
    Map.entry("36", "Rochester"),
    Map.entry("39", "Syracuse"),
    Map.entry("40", "Albany"),
    Map.entry("99", "Buffalo"),
    Map.entry("166", "Grand Central"),
    Map.entry("167", "Long Island"),
    Map.entry("170", "Staten Island"),
    Map.entry("171", "White Plains"),

    // --- NC North Carolina ---
    Map.entry("13", "Charlotte"),
    Map.entry("44", "Greensboro"),
    Map.entry("60", "Raleigh"),

    // --- OH Ohio ---
    Map.entry("8", "Cincinnati"),
    Map.entry("16", "Westlake"),
    Map.entry("29", "Toledo"),
    Map.entry("57", "Dayton"),
    Map.entry("89", "Beachwood"),
    Map.entry("96", "Youngstown"),
    Map.entry("100", "Akron"),
    Map.entry("228", "Columbus"),

    // --- OK Oklahoma ---
    Map.entry("25", "Oklahoma City"),
    Map.entry("91", "Norman"),
    Map.entry("236", "Oklahoma City-North"),

    // --- OR Oregon ---
    Map.entry("37", "Portland"),

    // --- PA Pennsylvania ---
    Map.entry("34", "Sewickley"),
    Map.entry("58", "Allentown"),
    Map.entry("59", "Harrisburg"),
    Map.entry("92", "South Hills"),
    Map.entry("133", "Chadds Ford"),
    Map.entry("33", "Conshohocken"),

    // --- RI Rhode Island ---
    Map.entry("54", "East Greenwich"),

    // --- SC South Carolina ---
    Map.entry("55", "Charleston"),
    Map.entry("71", "Columbia"),

    // --- TN Tennessee ---
    Map.entry("14", "Brentwood"),
    Map.entry("41", "Memphis"),
    Map.entry("42", "Knoxville"),

    // --- TX Texas ---
    Map.entry("27", "San Antonio"),
    Map.entry("46", "Houston-Clear Lake/League City"),
    Map.entry("110", "The Woodlands"),
    Map.entry("118", "Sugar Land"),
    Map.entry("154", "Dallas"),
    Map.entry("209", "Austin"),
    Map.entry("158", "West Houston"),

    // --- UT Utah ---
    Map.entry("177", "Salt Lake City"),

    // --- VA Virginia ---
    Map.entry("20", "Virginia Beach"),
    Map.entry("68", "Newport News"),
    Map.entry("176", "Henrico"),
    Map.entry("182", "Alexandria"),
    Map.entry("183", "Fairfax"),
    Map.entry("184", "Loudoun County"),
    Map.entry("187", "Tysons Corner"),

    // --- WA Washington ---
    Map.entry("56", "Federal Way"),
    Map.entry("61", "Lynnwood"),
    Map.entry("76", "Bellevue"),
    Map.entry("77", "Vancouver"),
    Map.entry("241", "Kirkland"),

    // --- WI Wisconsin ---
    Map.entry("26", "Waukesha"),
    Map.entry("70", "Madison"),
    Map.entry("102", "Appleton"),
    Map.entry("124", "Glendale")
);

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
