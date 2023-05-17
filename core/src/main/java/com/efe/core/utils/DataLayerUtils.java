package com.efe.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.efe.core.bean.datalayer.DataLayerObj;
import com.efe.core.bean.datalayer.Web;
import com.efe.core.bean.datalayer.WebDetails;
import com.google.gson.JsonObject;

/**
 * The Class DataLayerUtils.
 */
public class DataLayerUtils {

	/** The Constant PAGE_LOADED_EVENT. */
	private static final String PAGE_LOADED_EVENT = "page_loaded";
	/** The Constant HOME_LEVEL. */
	private static final int HOME_LEVEL = 3;

	/**
	 * Instantiates a new data layer utils.
	 */
	private DataLayerUtils() {
	}

	/**
	 * Creates the page load event obj.
	 *
	 * @param currentPage  the current page
	 * @param request the request
	 * @param resolver     the resolver
	 * @param externalizer the externalizer
	 * @return the data layer obj
	 */
	public static DataLayerObj createPageLoadEventObj(Page currentPage, SlingHttpServletRequest request,
			ResourceResolver resolver, Externalizer externalizer) {

		final DataLayerObj dataLayerObj = new DataLayerObj();
		dataLayerObj.setEvent(PAGE_LOADED_EVENT);

		final Web web = new Web();
		final WebDetails detail = new WebDetails();
		detail.setName(currentPage.getTitle());
		detail.setServer(externalizer.publishLink(resolver, "/"));
		detail.setUrl(externalizer.publishLink(resolver, request.getPathInfo()));

		final List<String> siteSections = new ArrayList<>();
		getSiteSections(siteSections, currentPage);

		Collections.reverse(siteSections);
		if (!siteSections.isEmpty()) {
			detail.setSiteSection(siteSections.get(0));
		}
		if (siteSections.size() >= 2) {
			detail.setSiteSectionLevel2(siteSections.get(1));
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
	 * @return the site sections
	 */
	private static void getSiteSections(List<String> siteSections, Page page) {

		if (page == null) {
			return;
		}

		if (page.getDepth() <= HOME_LEVEL) {
			return;
		}

		if (page.getDepth() > HOME_LEVEL) {
			siteSections.add(page.getTitle());
			getSiteSections(siteSections, page.getParent());
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
		
		JsonObject video = new  JsonObject();
		JsonObject videoTimed = new JsonObject();
		videoTimed.addProperty("publisher", "youtube");
		
		JsonObject primaryAssetViewDetails = new JsonObject();
		primaryAssetViewDetails.addProperty("videoName", videoTitle);
		videoTimed.add("primaryAssetViewDetails", primaryAssetViewDetails);
		
		JsonObject starts = new JsonObject();
		starts.addProperty("starts", 0);
		videoTimed.add("starts", starts);
		
		JsonObject completes = new JsonObject();
		completes.addProperty("value", 0);
		videoTimed.add("completes", completes);
		
		video.add("videoTimed", videoTimed);
		datalayerObj.add("video", video);
	
		return datalayerObj.toString();
	}
}
