package com.efe.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.PodcastArchiveList;
import com.efe.core.models.bean.Podcast;
import com.efe.core.services.EfeService;
import com.efe.core.services.RestService;
import com.efe.core.utils.EFEUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class PodcastArchiveListImpl.
 */
@Model(adaptables = { Resource.class }, adapters = PodcastArchiveList.class, resourceType = {
		PodcastArchiveListImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PodcastArchiveListImpl implements PodcastArchiveList {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/podcastarchivelist";
	
	/** The efe service. */
	@OSGiService
	private EfeService efeService;

	/** The rest service. */
	@OSGiService
	private RestService restService;

	/** The current resource. */
	@SlingObject
	private Resource resource;

	/** The id. */
	@ValueMapValue
	private String id;

	private List<Podcast> podcasts;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	public void init() {

		podcasts = new ArrayList<>();

		String response = restService.getData(
				"https://api.omny.fm/orgs/28f40a00-40de-4bda-94f0-ade200730f75/playlists/a61156d9-5032-4334-ae50-ae0c015b375a/clips/v2",
				null);

		if (StringUtils.isNotEmpty(response)) {

			JsonElement rootElement = JsonParser.parseString(response);
			if (rootElement != null && rootElement.isJsonObject()) {
				JsonObject rootJsonObject = rootElement.getAsJsonObject();

				if (rootJsonObject.has("Clips") && rootJsonObject.get("Clips").isJsonArray()) {

					JsonArray clips = rootJsonObject.get("Clips").getAsJsonArray();
					if (null != clips && !clips.isEmpty()) {

						for (JsonElement clip : clips) {
							Podcast podcast = new Podcast();
							if (clip.isJsonObject()) {
								JsonObject clipObj = clip.getAsJsonObject();
								podcast.setTitle(clipObj.get("Title").getAsString());
								podcast.setDescriptionHtml(clipObj.get("DescriptionHtml").getAsString());
								podcasts.add(podcast);
							}
						}
					}
				}
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
		if (!podcasts.isEmpty()) {
			isEmpty = false;
		}
		return isEmpty;
	}

	@Override
	public List<Podcast> getPodcasts() {
		return podcasts;
	}

}