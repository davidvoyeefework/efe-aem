package com.efe.core.models.impl;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.adobe.aem.wcm.seo.SeoTags;
import com.day.cq.commons.Externalizer;
import com.efe.core.constants.Constants;
import com.efe.core.models.PageModel;
import com.efe.core.services.EfeService;
import com.day.cq.wcm.api.Page;
import com.efe.core.bean.PlannerResponse;
import com.efe.core.utils.ArticleDetailUtil;
import com.efe.core.utils.LinkUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import com.efe.core.utils.LocationPlannerUtil;
import java.util.Collections;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 * The Class PageImpl.
 */
@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = PageModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PageImpl implements PageModel {
    	/** The SlingHttpServletRequest. */
	@Self
	private SlingHttpServletRequest request;

	/** Resource. */
	@ScriptVariable
	private Resource resource;

	/** Externalizer service. */
	@OSGiService
	private Externalizer externalizer;
	
	/** The efe service. */
	@OSGiService
	private EfeService efeService;

	/** Current Page. */
	@Inject
	private Page currentPage;

	/** Resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	private String socialSharingImage;

	/** Thumbnail Image Path. */
	private String thumbnail;
	
        /** The planner response. */
	private PlannerResponse plannerResponse;
        
        /**
	 * Gets the description or a version of the description for locations/planners.
	 *
	 * @return the modified description
	 */
        @Override
        public String getModifiedDescription() {
            String canonicalUrl = null;
            if (!getRobotsTags(resource).contains(Constants.NOINDEX)) {
                SeoTags seoTags = resource.adaptTo(SeoTags.class);
                canonicalUrl = seoTags != null ? seoTags.getCanonicalUrl() : null;
                canonicalUrl = canonicalUrl != null ? externalizer.publishLink(resourceResolver, canonicalUrl)
                                : LinkUtil.getAbsoluteUrl(currentPage, resourceResolver, externalizer);
            } else {
                return currentPage.getDescription();
            }
            String[] selectors = request.getRequestPathInfo().getSelectors();
            if(selectors.length > 0 && canonicalUrl != null) {
                if(canonicalUrl.endsWith("/location/") && selectors.length == 2) {
                    String stateSelector = selectors[0].toLowerCase();
                    String citySelector = selectors[1].toLowerCase();
                    Resource resourceLocation = LocationPlannerUtil.getLocationResource(resourceResolver, stateSelector, citySelector);
                    String city = LocationPlannerUtil.getLocationProperty(resourceResolver, resourceLocation, "city");
                    return "Connect with financial advisors in " + city + ", " + selectors[0].toUpperCase() + ", at Edelman Financial Engines, and discover our approach to financial planning.";
                } else if (canonicalUrl.endsWith("/financial-planner/") && selectors.length == 3) {
                    String nameSelector = selectors[0];
                    String idSelector = selectors[2];
                    Resource resourcePlanner = LocationPlannerUtil.getPlannerResource(resourceResolver, nameSelector, idSelector);
                    plannerResponse = ArticleDetailUtil.getPlannerDetails(resourceResolver, resourcePlanner, efeService);
                    String firstNameAlias = plannerResponse.getFirstNameAlias();
                    if(StringUtils.isNotEmpty(firstNameAlias)) {
                            plannerResponse.setFirstName(firstNameAlias);
                    }
					return plannerResponse.getFirstName() + " " + plannerResponse.getLastName() + " is a financial planner in the " + plannerResponse.getPrimaryOffice().getCity() + ", " + plannerResponse.getPrimaryOffice().getState() + " area. Request an appointment or call us to schedule one.";
                }
            } 
            return currentPage.getDescription();
        }
        
         /**
	 * Gets the title or a version of the title for locations/planners.
	 *
	 * @return the modified title
	 */
        @Override
        public String getModifiedTitle() {
            String canonicalUrl = null;
            if (!getRobotsTags(resource).contains(Constants.NOINDEX)) {
                SeoTags seoTags = resource.adaptTo(SeoTags.class);
                canonicalUrl = seoTags != null ? seoTags.getCanonicalUrl() : null;
                canonicalUrl = canonicalUrl != null ? externalizer.publishLink(resourceResolver, canonicalUrl)
                                : LinkUtil.getAbsoluteUrl(currentPage, resourceResolver, externalizer);
            } else {
                return currentPage.getTitle();
            }
            String[] selectors = request.getRequestPathInfo().getSelectors();
            if(selectors.length > 0 && canonicalUrl != null) {
                if(canonicalUrl.endsWith("/location/") && selectors.length == 2) {
                    String stateSelector = selectors[0].toLowerCase();
                    String citySelector = selectors[1].toLowerCase();
                    Resource resourceLocation = LocationPlannerUtil.getLocationResource(resourceResolver, stateSelector, citySelector);
                    String city = LocationPlannerUtil.getLocationProperty(resourceResolver, resourceLocation, "city");
                    return "Financial Advisors in " + city +", " + selectors[0].toUpperCase();
                } else if (canonicalUrl.endsWith("/financial-planner/") && selectors.length == 3) {
                    String nameSelector = selectors[0];
                    String idSelector = selectors[2];
                    Resource resourcePlanner = LocationPlannerUtil.getPlannerResource(resourceResolver, nameSelector, idSelector);
                    plannerResponse = ArticleDetailUtil.getPlannerDetails(resourceResolver, resourcePlanner, efeService);
                    String firstNameAlias = plannerResponse.getFirstNameAlias();
                    if(StringUtils.isNotEmpty(firstNameAlias)) {
                            plannerResponse.setFirstName(firstNameAlias);
                    }
					return "Financial Planner in " + plannerResponse.getPrimaryOffice().getCity() + ", " + plannerResponse.getPrimaryOffice().getState() + " | Edelman Financial Engines";
                }
            }
            return currentPage.getTitle();
        }
        
        
	/**
	 * Gets the canonical link.
	 *
	 * @return the canonical link
	 */
	public String getCanonicalLink() {
		String canonicalUrl = null;
		if (!getRobotsTags(resource).contains(Constants.NOINDEX)) {
			SeoTags seoTags = resource.adaptTo(SeoTags.class);
			canonicalUrl = seoTags != null ? seoTags.getCanonicalUrl() : null;
			canonicalUrl = canonicalUrl != null ? externalizer.publishLink(resourceResolver, canonicalUrl)
					: LinkUtil.getAbsoluteUrl(currentPage, resourceResolver, externalizer);
		} else {
                    return canonicalUrl;
                }
		String[] selectors = request.getRequestPathInfo().getSelectors();
                if(selectors.length > 0 && canonicalUrl != null) {
                    if(canonicalUrl.endsWith("/location/") && selectors.length == 2) {
                        return (canonicalUrl.substring(0,canonicalUrl.length() - 1)) + "." + selectors[0] + "." + selectors[1] + "/";
                    } else if (canonicalUrl.endsWith("/financial-planner/") && selectors.length == 3) {
                        return (canonicalUrl.substring(0,canonicalUrl.length() - 1)) + "." + selectors[0] + "." + selectors[1] + "." + selectors[2] + "/";
                    }
                } 
		return canonicalUrl;
	}

	public String getSocialSharingImage() {
		if (Objects.nonNull(resourceResolver.getResource(resource.getPath() + Constants.FEATURED_IMAGE))) {
			socialSharingImage = getThumbnailUrl(currentPage, 800, 480);
		}
		if (StringUtils.isNotBlank(socialSharingImage)) {
			socialSharingImage = externalizer.publishLink(resourceResolver, socialSharingImage);
		} else if (Objects.nonNull(resourceResolver.getResource(resource.getPath() + Constants.IMAGE))) {
			socialSharingImage = getThumbnailUrl(currentPage, 800, 480);
			if (StringUtils.isNotBlank(socialSharingImage)) {
				socialSharingImage = externalizer.publishLink(resourceResolver, socialSharingImage);
			}
		}
		return socialSharingImage;
	}

	/**
	 * Gets the robots tags.
	 *
	 * @param resource the resource
	 * @return the robots tags
	 */
	public List<String> getRobotsTags(Resource resource) {
		SeoTags seoTags = resource.adaptTo(SeoTags.class);
		List<String> robotsTags = seoTags != null && !seoTags.getRobotsTags().isEmpty()
				? Collections.unmodifiableList(seoTags.getRobotsTags())
				: Collections.emptyList();
		return robotsTags != null ? new ArrayList<>(robotsTags) : Collections.emptyList();
	}

	/**
	 * Gets the thumbnail url.
	 *
	 * @param page   the page
	 * @param width  the width
	 * @param height the height
	 * @return the thumbnail url
	 */
	public static String getThumbnailUrl(Page page, int width, int height) {
		return page.getPath() + Constants.THUMB + width + Constants.DOT_STRING + height + Constants.PNG;
	}

	/**
	 * Getter for Thumbnail Image.
	 *
	 * @return Thumbnail Image.
	 */
	public String getThumbnail() {
		if (Objects.nonNull(resourceResolver.getResource(resource.getPath() + Constants.IMAGE))) {
			thumbnail = getThumbnailUrl(currentPage, 800, 480);
		}
		if (StringUtils.isNotBlank(thumbnail)) {
			thumbnail = externalizer.publishLink(resourceResolver, thumbnail);
		}
		return thumbnail;
	}

	/**
	 * Gets the jquery url.
	 *
	 * @return the jquery url
	 */
	@Override
	public String getJqueryUrl() {
		String jQueryUrl = null;
		if(Objects.nonNull(efeService)) {
			jQueryUrl = efeService.getJqueryUrl();
		}
		return jQueryUrl;
	}
	
        @Override
        public String getFPIDLibURL() {
            String FPIDLib = null;
            if(Objects.nonNull(efeService)) {
                FPIDLib = efeService.getFPIDLibraryURL();
            }
            return FPIDLib;
        }
	/**
	 * Gets the external libraries.
	 *
	 * @return the external libraries
	 */
	@Override
	public List<String> getExternalLibraries(){
		List<String> externalLibraries = new ArrayList<>();	
		if(Objects.nonNull(efeService) && StringUtils.isNotEmpty(efeService.getExternalLibraries())) {
			String []libraries = efeService.getExternalLibraries().split(",");	
			for(String library: libraries) {
				externalLibraries.add(library.trim());
			}
		}
		return Collections.unmodifiableList(externalLibraries);
	}

}
