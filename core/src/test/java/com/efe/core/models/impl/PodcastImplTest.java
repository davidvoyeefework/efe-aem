package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.efe.core.models.PodcastModel;
import com.efe.core.services.DynamicMediaService;
import com.efe.core.services.EfeService;
import com.efe.core.services.RestService;
import com.efe.core.services.SeoService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class PodcastImplTest.
 */
@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class PodcastImplTest {

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/** The rest service. */
	@Mock
	private RestService restService;

	/** The efe service. */
	@Mock
	private EfeService efeService;

	@Mock
	private SeoService seoService;
	@Mock
	private DynamicMediaService dynamicMediaService;

	/**
	 * Inits the.
	 */
	@BeforeEach
	void init() {
		aemContext.registerService(RestService.class, restService);
		aemContext.registerService(EfeService.class, efeService);
		aemContext.registerService(SeoService.class, seoService);
		aemContext.registerService(DynamicMediaService.class, dynamicMediaService);
		aemContext.load().json("/com/efe/core/models/podcast/podcastlist.json", "/content/efe");
		aemContext.load().json("/com/efe/core/models/dam.json", "/content/dam");
		aemContext.load().json("/com/efe/core/models/podcast/tags.json", "/content/cq:tags");

		when(efeService.getOmnyOrgId()).thenReturn("orgId");
		lenient().when(dynamicMediaService.getDmImagePath(any(), anyString())).thenReturn("/content/dam/efe/test.png");
		lenient().when(efeService.getOmnyEpisodeApi()).thenReturn("episodeApi");
		lenient().when(restService.getData("episodeApi", null)).thenReturn(
				"{\"Id\":\"1\",\"Title\":\"Title\",\"DescriptionHtml\":\"<p>Description</p>\",\"Season\":2,\"Episode\":21,\"EpisodeType\":\"Full\",\"EmbedUrl\":\"embedurl\",\"PublishedUtc\":\"2023-05-25T16:26:22.327Z\"}");

	}

	/**
	 * Podcast with valid no episode id.
	 */
	@Test
	void podcastWithValidNoEpisodeId() {

		Resource resource = aemContext.currentResource("/content/efe/podcast/jcr:content/root/emptypodcast");
		PodcastModel podcast = resource.adaptTo(PodcastModel.class);

		assertNull(podcast.getFileReference());
		assertNotNull(podcast.getId());
		assertTrue(podcast.isEmpty());
		assertFalse(podcast.isApiError());

	}

	/**
	 * Podcast with valid episode id.
	 */
	@Test
	void podcastWithValidEpisodeId() {

		Resource resource = aemContext.currentResource("/content/efe/podcast/jcr:content/root/podcast");
		PodcastModel podcast = resource.adaptTo(PodcastModel.class);

		assertEquals("/content/dam/efe/test.png", podcast.getFileReference());
		assertNotNull(podcast.getPodcast());
		assertEquals("Title", podcast.getPodcast().getTitle());
		assertEquals("<p>Description</p>", podcast.getPodcast().getDescriptionHtml());
		assertEquals(2, podcast.getPodcast().getSeason());
		assertEquals(21, podcast.getPodcast().getEpisode());
		assertEquals("embedurl", podcast.getPodcast().getEmbedUrl());

		assertFalse(podcast.isEmpty());
		assertFalse(podcast.isApiError());
		assertNotNull(podcast.getJsonLd());
	}

	/**
	 * Podcast with in valid episode id.
	 */
	@Test
	void podcastWithInValidEpisodeId() {
		lenient().when(restService.getData("episodeApi", null)).thenReturn("{}");

		Resource resource = aemContext.currentResource("/content/efe/podcast/jcr:content/root/wrongpodcastid");
		PodcastModel podcast = resource.adaptTo(PodcastModel.class);
		assertNotNull(podcast.getPodcast());
		assertFalse(podcast.isEmpty());
		assertTrue(podcast.isApiError());
	}
}
