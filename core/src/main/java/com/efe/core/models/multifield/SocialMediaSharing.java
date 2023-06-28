package com.efe.core.models.multifield;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.efe.core.utils.LinkUtil;

/**
 * The Class SocialMediaSharingModel.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SocialMediaSharing {

	/**
	 * Icon image.
	 */
	private String icon;

	/**
	 * Social share URL.
	 */
	@ValueMapValue
	private String link;

	/** Current Resource. */
	@Self
	private Resource resource;

	/** Externalizer service. */
	@OSGiService
	private Externalizer externalizer;

	/** Resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	public void initModel() {
		if (StringUtils.isNotBlank(link)) {
			String[] arrOfStr = link.split(";", 2);
			icon = arrOfStr[0];
			link = arrOfStr[1];
		}
	}

	/**
	 * Getter for fileReference.
	 * 
	 * @return icon image.
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Getter for social share link.
	 * 
	 * @return social share link.
	 */
	public String getLink() {
		return getSharingUrl(link);
	}

	/**
	 * Gets the sharing url.
	 *
	 * @param link the link
	 * @return the sharing url
	 */
	private String getSharingUrl(String link) {
		final PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		if (pageManager != null) {
			final Page currentPage = pageManager.getContainingPage(resource);
			final Map<String, String> tokenMap = new HashMap<>();
			tokenMap.put("pageUrl", LinkUtil.getAbsoluteUrl(currentPage, resourceResolver, externalizer));
			tokenMap.put("pageTitle", currentPage.getTitle());
			final Pattern pattern = Pattern.compile("\\$\\[\\[(.+?)\\]\\]");
			final Matcher matcher = pattern.matcher(link);
			final StringBuffer buffer = new StringBuffer();
			while (matcher.find()) {
				if (tokenMap.containsKey(matcher.group(1))) {
					String replacement = tokenMap.get(matcher.group(1));
					matcher.appendReplacement(buffer,
							replacement != null ? Matcher.quoteReplacement(replacement) : "null");
				}
			}
			matcher.appendTail(buffer);
			return buffer.toString();
		}
		return StringUtils.EMPTY;
	}

}