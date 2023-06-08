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
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.dam.api.DamConstants;
import com.efe.core.bean.PlannerDetail;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.models.PlannerList;
import com.efe.core.services.EfeService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;
import com.efe.core.utils.LocationPlannerUtil;
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

	/** The SlingHttpServletRequest. */
	@SlingObject
	private SlingHttpServletRequest request;

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

	/** The State. */
	private String state;

	/** The City. */
	private String city;

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
			city = selectors[1].toLowerCase().replaceAll(PlannerLocationConstants.HYPHEN,PlannerLocationConstants.SPACE);
			
			Resource resourceLocation = LocationPlannerUtil.getLocationResource(resourceResolver, state, city);
			if (Objects.nonNull(resourceLocation)) {
				for (Resource item : resourceLocation.getChildren()) {
					setCfList(cfList, item);
				}
			}

			setPlannerDetails(cfList);
		}
	}

	/**
	 * Sets the planner details.
	 *
	 * @param cfList the new planner details
	 */
	private void setPlannerDetails(List<String> cfList) {
		for (String item : cfList) {
			PlannerDetail plannerObj = new PlannerDetail();
			Resource planner = resourceResolver.getResource(item);
			if (Objects.nonNull(planner)) {
				Resource plannerMaster = resourceResolver
						.getResource(planner.getPath() + PlannerLocationConstants.MASTER_NODE);
				
				if(plannerMaster == null) {
					
					continue;
				}
				String firstName = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(), "firstName");
				String lastName = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(), "lastName");
				String title = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(), "title");
				String imageUrl = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(),
						"desktopImageurl");
				String plannerId = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(), "id");

				plannerObj.setFirstName(firstName);
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
			Resource masterResource = resourceResolver
					.getResource(item.getPath() + PlannerLocationConstants.MASTER_NODE);
			String[] plannerList = ResourceUtil.getProperties(resourceResolver, masterResource.getPath(), "planners");
			for (String list : plannerList) {
				cfList.add(list);
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
}
