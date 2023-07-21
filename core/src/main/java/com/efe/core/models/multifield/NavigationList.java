package com.efe.core.models.multifield;

import com.efe.core.utils.LinkUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The Class NavigationList.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NavigationList {

	/** The Primary heading. */
	@ValueMapValue
	private String heading;

	/** The Primary heading links. */
	@ValueMapValue
	private String headingLink;

	/** The  Primary heading target. */
	@ValueMapValue
	private String primaryTarget;

	/** The Primary links. */
	@Inject
	private List<Link> secondaryLinks;

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/**
	 * Gets the heading.
	 *
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * Gets the primary heading links.
	 *
	 * @return the primary heading links
	 */
	public String getHeadingLink() {
		return LinkUtil.getFormattedLink(headingLink, resourceResolver);
	}

	/**
	 * Gets the primary heading Target.
	 *
	 * @return the primary heading Target
	 */
	public String getHeadingTarget() { return primaryTarget; }


	/**
	 * Gets the Secondary links.
	 *
	 * @return the Secondary links
	 */
	public List<Link> getSecondaryLinks() {
		if (Objects.nonNull(secondaryLinks)) {
			return new ArrayList<>(secondaryLinks);
		}
		return Collections.emptyList();
	}

}
