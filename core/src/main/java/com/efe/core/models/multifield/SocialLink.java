package com.efe.core.models.multifield;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.efe.core.utils.LinkUtil;

/**
 * The Class SocialLinksMultifield.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SocialLink {

	/** The icon. */
	@ValueMapValue
	private String icon;

	/** The link. */
	@ValueMapValue
	private String link;

	/** The alt. */
	@ValueMapValue
	private String alt;

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/**
	 * Gets the icon.
	 *
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink() {
		return LinkUtil.getFormattedLink(link, resourceResolver);
	}

	/**
	 * Gets the alt.
	 *
	 * @return the alt
	 */
	public String getAlt() {
		return alt;
	}

}
