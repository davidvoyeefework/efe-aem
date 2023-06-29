package com.efe.core.models.multifield;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.efe.core.utils.LinkUtil;

/**
 * The Class Link.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Link {

	/** The label. */
	@ValueMapValue
	private String label;

	/** The link. */
	@ValueMapValue
	private String link;

	/** The target. */
	@ValueMapValue
	private String target;
	
	/** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
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
	 * Gets the target.
	 *
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

}
