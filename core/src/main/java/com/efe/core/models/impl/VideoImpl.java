package com.efe.core.models.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.Externalizer;
import com.efe.core.models.Video;
import com.efe.core.services.RestService;
import com.efe.core.services.SeoService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.SeoUtil;
import com.google.gson.JsonObject;

/**
 * The Class VideoImpl.
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = Video.class, resourceType = {
		VideoImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class VideoImpl implements Video {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/video";

	/** The SlingHttpServletRequest. */
	@SlingObject
	private SlingHttpServletRequest request;
	
	/** The externalizer. */
	@OSGiService
	private Externalizer externalizer;

	/** The seo service. */
	@OSGiService
	private SeoService seoService;
	
	/** The rest service. */
	@OSGiService
	private RestService restService;

	/** The current resource. */
	@SlingObject
	private Resource resource;

	/** The id. */
	@ValueMapValue
	private String id;

	/** The button icon reference. */
	@ValueMapValue
	private String buttonIconReference;

	/** The file reference. */
	@ValueMapValue
	private String fileReference;

	/** The video id. */
	@ValueMapValue
	private String videoId;

	/** The json ld. */
	private String jsonLd;

	/** The video title. */
	private String videoTitle;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	public void init() {
		if (StringUtils.isNotEmpty(videoId)) {
			JsonObject videoDetails = SeoUtil.getVideoSeo(restService, seoService, externalizer, request, videoId, fileReference);
			if(videoDetails.has(SeoUtil.TITLE)) {
				videoTitle = videoDetails.get(SeoUtil.TITLE).getAsString();
			}
			
			if(videoDetails.has(SeoUtil.JSON_LD)) {
				jsonLd = videoDetails.get(SeoUtil.JSON_LD).getAsString();
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
	 * Gets the json ld.
	 *
	 * @return the json ld
	 */
	@Override
	public String getJsonLd() {
		return jsonLd;
	}

	/**
	 * Gets the video id.
	 *
	 * @return the video id
	 */
	@Override
	public String getVideoId() {
		return videoId;
	}

	/**
	 * Gets the video thumbnail.
	 *
	 * @return the video thumbnail
	 */
	@Override
	public String getVideoThumbnail() {
		return fileReference;
	}

	/**
	 * Gets the button icon.
	 *
	 * @return the button icon
	 */
	@Override
	public String getButtonIcon() {
		return buttonIconReference;
	}

	/**
	 * Gets the video title.
	 *
	 * @return the video title
	 */
	@Override
	public String getVideoTitle() {
		return videoTitle;
	}

	/**
	 * Checks if is empty.
	 *
	 * @return the empty
	 */
	@Override
	public boolean isEmpty() {
		boolean isEmpty = true;
		if (StringUtils.isNotEmpty(videoId)) {
			isEmpty = false;
		}
		return isEmpty;
	}

}