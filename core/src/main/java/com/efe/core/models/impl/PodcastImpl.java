package com.efe.core.models.impl;

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
import com.efe.core.models.PodcastModel;
import com.efe.core.models.bean.Podcast;
import com.efe.core.services.EfeService;
import com.efe.core.services.RestService;
import com.efe.core.utils.EFEUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * The Class PodcastArchiveListImpl.
 */
@Model(adaptables = { Resource.class }, adapters = PodcastModel.class, resourceType = {
		PodcastImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PodcastImpl implements PodcastModel {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PodcastImpl.class);

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/podcast";

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
	private String episodeId;

	/** The podcasts. */
	private Podcast podcast;

	/** The is api error. */
	private boolean isApiError;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	public void init() {

		final String orgId = efeService.getOmnyOrgId();
		final String episodeApi = efeService.getOmnyEpisodeApi();

		if (StringUtils.isEmpty(episodeId) || StringUtils.isEmpty(orgId) || StringUtils.isEmpty(episodeApi)) {
			LOGGER.debug("Skipping get episode details.");
			return;
		}

		if (StringUtils.isNotBlank(orgId) && StringUtils.isNotBlank(episodeApi)) {
			final String endpoint = episodeApi.replace("{orgid}", orgId).replace("{episodeid}", episodeId);
			final String episodeAPIResponse = restService.getData(endpoint, null);

			if (StringUtils.isNotEmpty(episodeAPIResponse)) {
				try {
					handleApiResponse(episodeAPIResponse);
				} catch (JsonParseException e) {
					LOGGER.error("Invalid JSON Response", e);
				}
			}

		}

	}

	/**
	 * Handle API response.
	 *
	 * @param episodeAPIResponse the episode API response
	 */
	private void handleApiResponse(final String episodeAPIResponse) {
		JsonElement rootElement = JsonParser.parseString(episodeAPIResponse);
		if (rootElement != null && rootElement.isJsonObject()) {
			JsonObject rootJsonObject = rootElement.getAsJsonObject();
			podcast = EFEUtil.getPodCastObj(rootJsonObject);

			if (null != podcast && StringUtils.isBlank(podcast.getId())) {
				isApiError = true;
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
	 * Checks if is empty.
	 *
	 * @return the empty
	 */
	@Override
	public boolean isEmpty() {
		boolean isEmpty = true;
		if (StringUtils.isNotBlank(episodeId)) {
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
	public Podcast getPodcast() {
		return podcast;
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