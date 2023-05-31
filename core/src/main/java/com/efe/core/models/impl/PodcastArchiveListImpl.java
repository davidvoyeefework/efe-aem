package com.efe.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.RangeIterator;
import com.day.cq.tagging.TagManager;
import com.efe.core.constants.Constants;
import com.efe.core.models.PodcastArchiveList;
import com.efe.core.models.bean.Podcast;
import com.efe.core.services.EfeService;
import com.efe.core.services.RestService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * The Class PodcastArchiveListImpl.
 */
@Model(adaptables = { Resource.class }, adapters = PodcastArchiveList.class, resourceType = {
		PodcastArchiveListImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PodcastArchiveListImpl implements PodcastArchiveList {

	/** The Constant DEFAULT_SEARCH_PATH. */
	private static final String DEFAULT_SEARCH_PATH = "content/efe";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PodcastArchiveListImpl.class);

	/** The Constant EPISODE_KEY. */
	private static final String EPISODE_KEY = "Clips";

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/podcastarchivelist";


	/** The efe service. */
	@OSGiService
	private EfeService efeService;

	/** The rest service. */
	@OSGiService
	private RestService restService;

	/** The resolver. */
	@SlingObject
	private ResourceResolver resolver;

	/** The current resource. */
	@SlingObject
	private Resource resource;

	/** The id. */
	@ValueMapValue
	private String id;

	/** The playlist id. */
	@ValueMapValue
	private String playlistId;

	/** The tags. */
	@ValueMapValue
	private String[] tags;

	@ValueMapValue
	private String searchPath;

	/** The podcasts. */
	private List<Podcast> podcasts;

	/** The is api error. */
	private boolean isApiError;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	public void init() {

		if (null == tags || tags.length < 1) {
			return;
		}
		
		if(StringUtils.isBlank(searchPath)) {
			searchPath = Constants.FORWARD_SLASH + DEFAULT_SEARCH_PATH;
		}

		final String orgId = efeService.getOmnyOrgId();
		final String playListApi = efeService.getOmnyPlaylistApi();

		if (StringUtils.isNotBlank(orgId) && StringUtils.isNotBlank(playlistId)
				&& StringUtils.isNotBlank(playListApi)) {
			podcasts = new ArrayList<>();
			final String endpoint = playListApi.replace("{orgid}", orgId).replace("{playlistid}", playlistId);
			final String playlistAPIResponse = restService.getData(endpoint, null);
			if (StringUtils.isNotEmpty(playlistAPIResponse)) {
				try {
					handleApiResponse(playlistAPIResponse);
				} catch (JsonParseException e) {
					LOGGER.error("Invalid JSON Response", e);
				}
			}
		}
	}

	/**
	 * Find archived episodes.
	 *
	 * @param tags     the tags
	 * @param resolver the resolver
	 * @return the list
	 */
	private Map<String, String> findArchivedEpisodes(String[] tags, ResourceResolver resolver) {

		Map<String, String> episodesMap = new HashMap<>();

		TagManager manager = resolver.adaptTo(TagManager.class);

		RangeIterator<Resource> resoucesItr = manager.find(searchPath, tags);

		while (resoucesItr.hasNext()) {
			Resource pageResource = resoucesItr.next();
			Resource podcastResource = EFEUtil.traverseResourceHierarchy(pageResource, PodcastImpl.RESOURCE_TYPE);
			if (null != podcastResource) {
				String episodeId = podcastResource.getValueMap().get("episodeId", null);
				if (StringUtils.isNotBlank(episodeId)) {
					episodesMap.put(episodeId,
							LinkUtil.getFormattedLink(pageResource.getPath().replace("/jcr:content", ""), resolver));
				}
			}
		}
		return episodesMap;
	}

	/**
	 * Handle API response.
	 *
	 * @param archivedEpisodes    the archived episodes
	 * @param playlistAPIResponse the playlist API response
	 */
	private void handleApiResponse(final String playlistAPIResponse) {

		JsonElement rootElement = JsonParser.parseString(playlistAPIResponse);
		if (rootElement != null && rootElement.isJsonObject()) {
			JsonObject rootJsonObject = rootElement.getAsJsonObject();
			if (rootJsonObject.has(EPISODE_KEY) && rootJsonObject.get(EPISODE_KEY).isJsonArray()) {
				Map<String, String> archivedEpisodes = findArchivedEpisodes(tags, resolver);
			
				JsonArray clips = rootJsonObject.get(EPISODE_KEY).getAsJsonArray();
				for (JsonElement clip : clips) {
					Podcast podcast = EFEUtil.getPodCastObj(clip);
					if (archivedEpisodes.containsKey(podcast.getId())) {
						podcast.setPageLink(archivedEpisodes.get(podcast.getId()));
						podcasts.add(podcast);
					}
				}
			} else {
				LOGGER.debug("Required property clips not found. {}", playlistAPIResponse);
				isApiError = true;
			}
		}
		
		if(podcasts.isEmpty()) {
			isApiError = true;
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
	 * Checks if is empty.
	 *
	 * @return the empty
	 */
	@Override
	public boolean isEmpty() {
		boolean isEmpty = true;
		if (StringUtils.isNotBlank(playlistId) || null != tags) {
			isEmpty = false;
		}
		return isEmpty;
	}

	/**
	 * Gets the podcasts.
	 *
	 * @return the podcasts
	 */
	@Override
	public List<Podcast> getPodcasts() {
		if (Objects.nonNull(podcasts)) {
			return new ArrayList<>(podcasts);
		}
		return Collections.emptyList();
	}

	/**
	 * Checks if is api error.
	 *
	 * @return the isApiError
	 */
	@Override
	public boolean isApiError() {
		return isApiError;
	}

}