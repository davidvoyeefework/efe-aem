package com.efe.core.sitemap;

import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;

import com.day.cq.dam.commons.util.DamUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.sling.sitemap.SitemapException;
import org.apache.sling.sitemap.builder.Sitemap;
import org.apache.sling.sitemap.builder.Url;
import org.apache.sling.sitemap.spi.generator.ResourceTreeSitemapGenerator;
import org.apache.sling.sitemap.spi.generator.SitemapGenerator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.wcm.seo.sitemap.PageTreeSitemapGenerator;
import com.day.cq.commons.Externalizer;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.constants.NameConstants;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.services.EfeService;
import com.efe.core.utils.LinkUtil;
import com.efe.core.utils.LocationPlannerUtil;
import com.efe.core.utils.ResourceUtil;

/**
 * The Class EFESitemapGenerator.
 */
@Component(service = SitemapGenerator.class, immediate = true)
@ServiceRanking(value = 20)
public class EFESitemapGenerator extends ResourceTreeSitemapGenerator {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(EFESitemapGenerator.class);

	/** The resolver factory. */
	@Reference
	private ResourceResolverFactory resolverFactory;

	/** The externalizer. */
	@Reference
	private Externalizer externalizer;

	/** The default generator. */
	@Reference
	private PageTreeSitemapGenerator defaultGenerator;

	/** The efe service. */
	@Reference
	private EfeService efeService;

	/**
	 * Adds the resource.
	 *
	 * @param name the name
	 * @param sitemap the sitemap
	 * @param resource the resource
	 * @throws SitemapException the sitemap exception
	 */
	@Override
	protected void addResource(String name, Sitemap sitemap, Resource resource) throws SitemapException {
		Page page = resource.adaptTo(Page.class);
		if (page == null) {
			LOG.debug("Skipping resource at {}: not a page", resource.getPath());
			return;
		}

		try (ResourceResolver resourceResolver = ResourceUtil.getServiceResourceResolver(resolverFactory)) {
			if (resource.getPath().equals("/content/efe/us/en/locations")) {
				getLocationsNPlanners(sitemap, resourceResolver);
			} else {

				String location = externalizer.publishLink(resourceResolver, LinkUtil.getFormattedLink(resource.getPath(), resourceResolver));

				Url url = sitemap.addUrl(location);

				final Calendar lastmod = Optional.ofNullable(page.getLastModified())
						.orElse(page.getContentResource().getValueMap().get(JcrConstants.JCR_CREATED, Calendar.class));
				if (lastmod != null) {
					url.setLastModified(lastmod.toInstant());
				}
			}
		}

	}

	/**
	 * Gets the locations N planners.
	 *
	 * @param sitemap the sitemap
	 * @param resourceResolver the resource resolver
	 * @return the locations N planners
	 * @throws SitemapException the sitemap exception
	 */
	private void getLocationsNPlanners(Sitemap sitemap, ResourceResolver resourceResolver) throws SitemapException {
		Resource locationResource = resourceResolver.getResource(PlannerLocationConstants.LOCATION_PATH);
		if (Objects.nonNull(locationResource)) {
			for (Resource stateResource : locationResource.getChildren()) {
				if (stateResource.isResourceType(JcrResourceConstants.NT_SLING_ORDERED_FOLDER)) {

					for (Resource cityResource : stateResource.getChildren()) {
						String city = LocationPlannerUtil.getLocationProperty(resourceResolver, cityResource, "externalName");
						String cityUrl = LinkUtil.getFormattedLink(efeService.getPlannerPageUrl()
								+ PlannerLocationConstants.DOT + stateResource.getName().toUpperCase()
								+ PlannerLocationConstants.DOT
								+ DamUtil.getSanitizedFolderName(city, false), resourceResolver);

						cityUrl = externalizer.publishLink(resourceResolver, cityUrl);
						Url url = sitemap.addUrl(cityUrl);
						
						final Calendar lastmod = cityResource.getValueMap().get(JcrConstants.JCR_CREATED,
										Calendar.class);
						if (lastmod != null) {
							url.setLastModified(lastmod.toInstant());
						}

					}
				}
			}

			getPlannera(sitemap, resourceResolver);

		}
	}

	/**
	 * Gets the plannera.
	 *
	 * @param sitemap the sitemap
	 * @param resourceResolver the resource resolver
	 * @return the plannera
	 * @throws SitemapException the sitemap exception
	 */
	private void getPlannera(Sitemap sitemap, ResourceResolver resourceResolver) throws SitemapException {
		Resource plannerFolderResource = resourceResolver
				.getResource(PlannerLocationConstants.PLANNER_PATH);
		if (Objects.nonNull(plannerFolderResource) && plannerFolderResource.hasChildren()) {
			for (Resource plannerFolder : plannerFolderResource.getChildren()) {
				if (plannerFolder.isResourceType(JcrResourceConstants.NT_SLING_ORDERED_FOLDER)) {

					try {
					String plannerId = plannerFolder.getName();
					String plannerNameId = plannerFolder.getValueMap().get(JcrConstants.JCR_TITLE,
							String.class);
					String plannerFragmentPath = plannerFolder.getPath() + "/fragment_" + plannerNameId;
					
					Resource plannerResource = resourceResolver.getResource(plannerFragmentPath);
					
					if(null == plannerResource) {
						continue;
					}
								
					Resource plannerMaster = plannerResource.getChild(plannerFragmentPath + PlannerLocationConstants.MASTER_NODE);

					if(plannerMaster == null) {
						continue;
					}
					
					String firstName = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(),
							"firstName");
					String lastName = ResourceUtil.getProperty(resourceResolver, plannerMaster.getPath(),
							"lastName");
					
					if(firstName == null) {
						continue;
					}

					String plannerUrl = LinkUtil.getFormattedLink(efeService.getPlannerBioPageUrl()
							+ PlannerLocationConstants.DOT + firstName + PlannerLocationConstants.DOT
							+ lastName + PlannerLocationConstants.DOT + plannerId, resourceResolver);

					Url url = sitemap.addUrl(externalizer.publishLink(resourceResolver, plannerUrl));

					final Calendar lastmod = plannerResource.getValueMap().get(JcrConstants.JCR_CREATED,
									Calendar.class);
					if (lastmod != null) {
						url.setLastModified(lastmod.toInstant());
					}
					}catch (Exception e) {
						LOG.error("Error:", e);
					}
				}
			}
		}
	}

	/**
	 * Should follow.
	 *
	 * @param resource the resource
	 * @return true, if successful
	 */
	@Override
	protected final boolean shouldFollow(Resource resource) {
		return super.shouldFollow(resource) && defaultGenerator.shouldFollow(resource);
	}

	/**
	 * Should include.
	 *
	 * @param resource the resource
	 * @return true, if successful
	 */
	@Override
	protected final boolean shouldInclude(Resource resource) {
		Page page = resource.adaptTo(Page.class);
		return super.shouldInclude(resource) && defaultGenerator.shouldInclude(resource) && !isRedirect(page);
	}

	/**
	 * Checks if is redirect.
	 *
	 * @param page the page
	 * @return true, if is redirect
	 */
	private boolean isRedirect(Page page) {
		return page.getProperties().get(NameConstants.PN_REDIRECT_TARGET, String.class) != null;
	}

}