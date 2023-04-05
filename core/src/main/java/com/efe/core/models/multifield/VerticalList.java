package com.efe.core.models.multifield;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * The Class VerticalList.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class VerticalList {

	/** The heading. */
	@ValueMapValue
	private String heading;

	/** The vertical links. */
	@Inject
	private List<Link> verticalLinks;

	/**
	 * Gets the heading.
	 *
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * Gets the vertical links.
	 *
	 * @return the vertical links
	 */
	public List<Link> getVerticalLinks() {
		return verticalLinks;
	}

}
