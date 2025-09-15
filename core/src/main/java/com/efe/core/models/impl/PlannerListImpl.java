package com.efe.core.models.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.HashSet;

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
				System.out.println(externalOfficeNames);
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


	private List<String> officeIDtoExternalName(List<String> officeIDlist) {

		List<String> externalOfficeNames = new ArrayList<>();
		for (String item: officeIDlist) {
			switch(item) {
				// --- AL Alabama ---
				case "28":
					externalOfficeNames.add("Birmingham");
					break;
				case "138":
					externalOfficeNames.add("Huntsville");
					break;

				// --- AZ Arizona ---
				case "98":
					externalOfficeNames.add("Phoenix");
					break;
				case "207":
					externalOfficeNames.add("Emerging Investors");
					break;
				case "175":
					externalOfficeNames.add("Chandler");
					break;

				// --- CA California ---
				case "15":
					externalOfficeNames.add("Walnut Creek");
					break;
				case "222":
					externalOfficeNames.add("Woodland");
					break;
				case "38":
					externalOfficeNames.add("Roseville");
					break;
				case "64":
					externalOfficeNames.add("Fresno");
					break;
				case "114":
					externalOfficeNames.add("Elk Grove");
					break;
				case "128":
					externalOfficeNames.add("San Rafael");
					break;
				case "135":
					externalOfficeNames.add("El Segundo");
					break;
				case "137":
					externalOfficeNames.add("Anaheim");
					break;
				case "159":
					externalOfficeNames.add("Orange County");
					break;
				case "160":
					externalOfficeNames.add("Pasadena");
					break;
				case "162":
					externalOfficeNames.add("Woodland Hills");
					break;
				case "178":
					externalOfficeNames.add("San Diego");
					break;
				case "179":
					externalOfficeNames.add("Santa Clara");
					break;
				case "223":
					externalOfficeNames.add("Seal Beach");
					break;
				case "224":
					externalOfficeNames.add("Danville");
					break;

				// --- CO Colorado ---
				case "52":
					externalOfficeNames.add("Westminster");
					break;
				case "53":
					externalOfficeNames.add("Englewood"); // duplicate IDs
					break;
				case "78":
					externalOfficeNames.add("Colorado Springs");
					break;
				case "121":
					externalOfficeNames.add("Loveland");
					break;

				// --- CT Connecticut ---
				case "157":
					externalOfficeNames.add("Farmington");
					break;

				// --- FL Florida ---
				case "24":
					externalOfficeNames.add("Tampa-East");
					break;
				case "35":
					externalOfficeNames.add("Melbourne");
					break;
				case "123":
					externalOfficeNames.add("Tampa-Westshore");
					break;
				case "163":
					externalOfficeNames.add("Boca Raton");
					break;
				case "164":
					externalOfficeNames.add("Miami-Coral Gables");
					break;
				case "172":
					externalOfficeNames.add("Orlando");
					break;
				case "142":
					externalOfficeNames.add("Naples");
					break;
				case "226":
					externalOfficeNames.add("Miami-Plantation");
					break;
				case "237":
					externalOfficeNames.add("St. Petersburg");
					break;

				// --- GA Georgia ---
				case "72":
					externalOfficeNames.add("Duluth");
					break;
				case "84":
					externalOfficeNames.add("Vinings");
					break;
				case "94":
					externalOfficeNames.add("Alpharetta");
					break;
				case "129":
					externalOfficeNames.add("Atlanta");
					break;

				// --- IL Illinois ---
				case "62":
					externalOfficeNames.add("Oak Brook");
					break;
				case "79":
					externalOfficeNames.add("Mokena");
					break;
				case "151":
					externalOfficeNames.add("Northbrook");
					break;
				case "48":
					externalOfficeNames.add("Fairview Heights");
					break;

				// --- IN Indiana ---
				case "2":
					externalOfficeNames.add("Indianapolis");
					break;
				case "4":
					externalOfficeNames.add("Fort Wayne");
					break;
				case "119":
					externalOfficeNames.add("Greenwood");
					break;

				// --- IA Iowa ---
				case "75":
					externalOfficeNames.add("Des Moines");
					break;					

				// --- KS Kansas ---
				case "1":
					externalOfficeNames.add("Overland Park");
					break;
				case "74":
					externalOfficeNames.add("Wichita");
					break;

				// --- KY Kentucky ---
				case "19":
					externalOfficeNames.add("Louisville");
					break;
				case "65":
					externalOfficeNames.add("Lexington");
					break;
				case "90":
					externalOfficeNames.add("Florence");
					break;

				// --- LA Louisiana ---
				case "11":
					externalOfficeNames.add("Metairie");
					break;
				case "32":
					externalOfficeNames.add("Baton Rouge");
					break;
				case "80":
					externalOfficeNames.add("Mandeville");
					break;	

				// MA Massachusetts
				case "22":
					externalOfficeNames.add("Springfield");
					break;
				case "148":
					externalOfficeNames.add("Burlington");
					break;
				case "238":
					externalOfficeNames.add("Quincy");
					break;
				case "243":
					externalOfficeNames.add("Norwood");
					break;

				// MD Maryland
				case "145":
					externalOfficeNames.add("Annapolis");
					break;
				case "146":
					externalOfficeNames.add("Howard County");
					break;
				case "147":
					externalOfficeNames.add("Towson");
					break;
				case "185":
					externalOfficeNames.add("North Bethesda");
					break;
				case "186":
					externalOfficeNames.add("Silver Spring");
					break;

				// MI Michigan
				case "10":
					externalOfficeNames.add("Grand Rapids");
					break;
				case "50":
					externalOfficeNames.add("Northville");
					break;
				case "105":
					externalOfficeNames.add("Ann Arbor");
					break;
				case "155":
					externalOfficeNames.add("Novi");
					break;
				case "156":
					externalOfficeNames.add("Troy");
					break;
				case "247":
					externalOfficeNames.add("Kalamazoo");
					// externalOfficeNames.add("Grand Rapids II - Portage");
					break;

				// MN Minnesota
				case "67":
					externalOfficeNames.add("Woodbury");
					break;
				case "125":
					externalOfficeNames.add("Rochester");
					break;
				case "31":
					externalOfficeNames.add("Minnetonka");
					break;

				// MO Missouri
				case "6":
					externalOfficeNames.add("Chesterfield");
					break;
				case "47":
					externalOfficeNames.add("Liberty");
					break;
				case "81":
					externalOfficeNames.add("St. Louis");
					break;
				case "83":
					externalOfficeNames.add("Lee's Summit");
					break;

				// NE Nebraska
				case "3":
					externalOfficeNames.add("Omaha");
					break;

				// NV Nevada
				case "7":
					externalOfficeNames.add("Las Vegas");
					break;					

				// NJ New Jersey
				case "165":
					externalOfficeNames.add("Paramus");
					break;
				case "168":
					externalOfficeNames.add("Red Bank");
					break;
				case "169":
					externalOfficeNames.add("Short Hills");
					break;
				case "173":
					externalOfficeNames.add("Cherry Hill");
					break;

				// NM New Mexico
				case "12":
					externalOfficeNames.add("Albuquerque");
					break;

				// NY New York
				case "36":
					externalOfficeNames.add("Rochester");
					break;
				case "39":
					externalOfficeNames.add("Syracuse");
					break;
				case "40":
					externalOfficeNames.add("Albany");
					break;
				case "99":
					externalOfficeNames.add("Buffalo");
					break;
				case "166":
					externalOfficeNames.add("Grand Central");
					break;
				case "167":
					externalOfficeNames.add("Long Island");
					break;
				case "170":
					externalOfficeNames.add("Staten Island");
					break;
				case "171":
					externalOfficeNames.add("White Plains");
					break;

				// NC North Carolina
				case "13":
					externalOfficeNames.add("Charlotte");
					break;
				case "44":
					externalOfficeNames.add("Greensboro");
					break;
				case "60":
					externalOfficeNames.add("Raleigh");
					break;					

				// OH Ohio
				case "8":
					externalOfficeNames.add("Cincinnati");
					break;
				case "16":
					externalOfficeNames.add("Westlake");
					break;
				case "29":
					externalOfficeNames.add("Toledo");
					break;
				case "57":
					externalOfficeNames.add("Dayton");
					break;
				case "89":
					externalOfficeNames.add("Beachwood");
					break;
				case "96":
					externalOfficeNames.add("Youngstown");
					break;
				case "100":
					externalOfficeNames.add("Akron");
					break;
				case "228":
					externalOfficeNames.add("Columbus");
					break;

				// OK Oklahoma
				case "25":
					externalOfficeNames.add("Oklahoma City");
					break;
				case "91":
					externalOfficeNames.add("Norman");
					break;
				case "236":
					externalOfficeNames.add("Oklahoma City-North");
					break;

				// OR Oregon
				case "37":
					externalOfficeNames.add("Portland");
					break;

				// PA Pennsylvania
				case "34":
					externalOfficeNames.add("Sewickley");
					break;
				case "58":
					externalOfficeNames.add("Allentown");
					break;
				case "59":
					externalOfficeNames.add("Harrisburg");
					break;
				case "92":
					externalOfficeNames.add("South Hills");
					break;
				case "133":
					externalOfficeNames.add("Chadds Ford");
					break;
				case "33":
					externalOfficeNames.add("Conshohocken");
					break;

				// RI Rhode Island
				case "54":
					externalOfficeNames.add("East Greenwich");
					break;

				// SC South Carolina
				case "55":
					externalOfficeNames.add("Charleston");
					break;
				case "71":
					externalOfficeNames.add("Columbia");
					break;

				// TN Tennessee
				case "14":
					externalOfficeNames.add("Brentwood");
					break;
				case "41":
					externalOfficeNames.add("Memphis");
					break;
				case "42":
					externalOfficeNames.add("Knoxville");
					break;

				// TX Texas
				case "27":
					externalOfficeNames.add("San Antonio");
					break;
				case "46":
					externalOfficeNames.add("Houston-Clear Lake/League City");
					break;
				case "110":
					externalOfficeNames.add("The Woodlands");
					break;
				case "118":
					externalOfficeNames.add("Sugar Land");
					break;
				case "154":
					externalOfficeNames.add("Dallas");
					break;
				case "209":
					externalOfficeNames.add("Austin");
					break;
				case "158":
					externalOfficeNames.add("West Houston");
					break;

				// UT Utah
				case "177":
					externalOfficeNames.add("Salt Lake City");
					break;

				// VA Virginia
				case "20":
					externalOfficeNames.add("Virginia Beach");
					break;
				case "68":
					externalOfficeNames.add("Newport News");
					break;
				case "176":
					externalOfficeNames.add("Henrico");
					break;
				case "182":
					externalOfficeNames.add("Alexandria");
					break;
				case "183":
					externalOfficeNames.add("Fairfax");
					break;
				case "184":
					externalOfficeNames.add("Loudoun County");
					break;
				case "187":
					externalOfficeNames.add("Tysons Corner");
					break;
				// case "246":
				// 	externalOfficeNames.add("Washington DC - Client Development");
				// 	break;

				// WA Washington
				case "56":
					externalOfficeNames.add("Federal Way");
					break;
				case "61":
					externalOfficeNames.add("Lynnwood");
					break;
				case "76":
					externalOfficeNames.add("Bellevue");
					break;
				case "77":
					externalOfficeNames.add("Vancouver");
					break;
				case "241":
					externalOfficeNames.add("Kirkland");
					break;

				// WI Wisconsin
				case "26":
					externalOfficeNames.add("Waukesha");
					break;
				case "70":
					externalOfficeNames.add("Madison");
					break;
				case "102":
					externalOfficeNames.add("Appleton");
					break;
				case "124":
					externalOfficeNames.add("Glendale");
					break;					
			}

		}
		Collections.sort(externalOfficeNames);
		Set<String> externalOfficeNamesWithoutDuplicates = new HashSet<>(externalOfficeNames);
		List<String> cleanedOfficeNames = new ArrayList<>(externalOfficeNamesWithoutDuplicates);
		return cleanedOfficeNames;
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
