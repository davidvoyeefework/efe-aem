package com.efe.core.models.impl;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.adobe.aem.wcm.seo.SeoTags;
import com.day.cq.commons.Externalizer;
import com.efe.core.constants.Constants;
import com.efe.core.models.PageModel;
import com.efe.core.services.EfeService;
import com.day.cq.wcm.api.Page;
import com.efe.core.utils.LinkUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.Collections;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 * The Class PageImpl.
 */
@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = PageModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PageImpl implements PageModel {

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

	/** Thumbnail Image Path. */
	private String thumbnail;
	
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
		}
		
		return canonicalUrl;
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

}
