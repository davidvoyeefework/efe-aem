package com.efe.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.efe.core.bean.datalayer.DataLayerObj;
import com.efe.core.bean.datalayer.Web;
import com.efe.core.bean.datalayer.WebDetails;
import com.efe.core.services.EfeService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The Class DataLayerUtils.
 */
public class DataLayerUtils {

	private static final String VALUE = "value";
	/** The Constant PAGE_LOADED_EVENT. */
	private static final String PAGE_LOADED_EVENT = "page_loaded";

	/**
	 * Instantiates a new data layer utils.
	 */
	private DataLayerUtils() {
	}

	/**
	 * Creates the page load event obj.
	 *
	 * @param efeService   the efe service
	 * @param currentPage  the current page
	 * @param request      the request
	 * @param resolver     the resolver
	 * @param externalizer the externalizer
	 * @return the data layer obj
	 */
	public static DataLayerObj createPageLoadEventObj(EfeService efeService, Page currentPage,
			SlingHttpServletRequest request, ResourceResolver resolver, Externalizer externalizer) {

		final DataLayerObj dataLayerObj = new DataLayerObj();
		dataLayerObj.setEvent(PAGE_LOADED_EVENT);

		final Web web = new Web();
		final WebDetails detail = new WebDetails();
		detail.setName(currentPage.getTitle());
		detail.setServer(externalizer.publishLink(resolver, "/"));
		detail.setUrl(externalizer.publishLink(resolver, request.getPathInfo()));

		if (currentPage.getPath().contains("errors")) {
			detail.setErrorPage(currentPage.getName());
		} else {
			final List<String> siteSections = new ArrayList<>();
			getSiteSections(siteSections, currentPage, efeService.getAnalyticsSiteRootLevel());

			Collections.reverse(siteSections);
			if (!siteSections.isEmpty()) {
				detail.setSiteSection(siteSections.get(0));
			}
			if (siteSections.size() >= 2) {
				detail.setSiteSectionLevel2(siteSections.get(1));
			}
		}
		web.setWebPageDetails(detail);
		dataLayerObj.setWeb(web);
		return dataLayerObj;
	}

	/**
	 * Gets the site sections.
	 *
	 * @param siteSections the site sections
	 * @param page         the page
	 * @param homeLevel    the home level
	 * @return the site sections
	 */
	private static void getSiteSections(List<String> siteSections, Page page, int homeLevel) {

		if (page == null) {
			return;
		}

		if (page.getDepth() <= homeLevel) {
			return;
		}

		if (page.getDepth() > homeLevel) {
			siteSections.add(page.getTitle());
			getSiteSections(siteSections, page.getParent(), homeLevel);
		}
	}

	/**
	 * Creates the video data object.
	 *
	 * @param videoTitle the video title
	 * @return the string
	 */
	public static String createVideoDataObject(String videoTitle) {

		JsonObject datalayerObj = new JsonObject();
		datalayerObj.addProperty("event", "video_action");

		JsonObject video = new JsonObject();
		JsonObject videoTimed = new JsonObject();
		videoTimed.addProperty("publisher", "youtube");

		JsonObject primaryAssetViewDetails = new JsonObject();
		primaryAssetViewDetails.addProperty("videoName", videoTitle);
		videoTimed.add("primaryAssetViewDetails", primaryAssetViewDetails);

		JsonObject starts = new JsonObject();
		starts.addProperty(VALUE, 0);
		videoTimed.add("starts", starts);

		JsonObject completes = new JsonObject();
		completes.addProperty(VALUE, 0);
		videoTimed.add("completes", completes);

		video.add("videoTimed", videoTimed);
		datalayerObj.add("video", video);

		return datalayerObj.toString();
	}

	/**
	 * Gets the tracking links list.
	 *
	 * @param resolverFactory the resolver factory
	 * @param efeService      the efe service
	 * @return the tracking links list
	 */
	public static String getTrackingLinksList(ResourceResolverFactory resolverFactory, EfeService efeService) {
		JsonArray array = new JsonArray();
		if (StringUtils.isNotEmpty(efeService.getLinkTrackingListPath())) {

			try (ResourceResolver resourceResolver = ResourceUtil.getServiceResourceResolver(resolverFactory)) {
				Resource resource = resourceResolver
						.getResource(efeService.getLinkTrackingListPath() + "/jcr:content/list");
				if (Objects.nonNull(resource)) {
					Iterable<Resource> children = resource.getChildren();
					for (Resource link : children) {
						final String linkLabel = link.getValueMap().get(VALUE, StringUtils.EMPTY);
						if (StringUtils.isNotEmpty(linkLabel)) {
							array.add(linkLabel.trim());
						}
					}
				}

			}
		}
		return array.toString();
	}
}
