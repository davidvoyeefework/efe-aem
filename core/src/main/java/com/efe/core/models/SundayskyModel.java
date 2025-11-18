package com.efe.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SundayskyModel {

	@ValueMapValue
	private String title;

	@ValueMapValue
	private String programId;

	@ValueMapValue
	private String minHeight;

        private final String baseUrl = "https://myvideo.sundaysky.com/embed/";

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return path
	 */
	public String getProgramId() {
		return programId;
	}

	/**
	 * @return height
	 */
	public String getMinHeight() {
		return minHeight;
	}

	/**
	 * @return width
	 */
	public String getVideoUrl() {
		return baseUrl + "?programId=" + programId;
	}
}