package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.efe.core.models.PodcastArchiveList;
import com.efe.core.models.bean.Podcast;
import com.efe.core.services.EfeService;
import com.efe.core.services.RestService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class PodcastArchiveListImplTest {

	private AemContext aemContext = new AemContext();

	@Mock
	private RestService restService;

	@Mock
	private EfeService efeService;

	@BeforeEach
	void init() {
		aemContext.registerService(RestService.class, restService);
		aemContext.registerService(EfeService.class, efeService);

		aemContext.load().json("/com/efe/core/models/podcast/podcastlist.json", "/content/efe");
		aemContext.load().json("/com/efe/core/models/podcast/tags.json", "/content/cq:tags");

		when(efeService.getOmnyOrgId()).thenReturn("orgId");
		lenient().when(efeService.getOmnyPlaylistApi()).thenReturn("playlistApi");
		lenient().when(restService.getData("playlistApi", null)).thenReturn(
				"{\"Clips\":[{\"Id\":\"1\",\"Title\":\"Title\",\"DescriptionHtml\":\"<p>Description</p>\",\"Season\":2,\"Episode\":21,\"EpisodeType\":\"Full\",\"EmbedUrl\":\"embedurl\",\"PublishedUtc\":\"2023-05-25T16:26:22.327Z\"}]}");

	}

	@Test
	void testPodcastsList() {
		Resource resource = aemContext.currentResource("/content/efe/podcastlistpage/jcr:content/root/podcastlist");
		PodcastArchiveList archiveList = resource.adaptTo(PodcastArchiveList.class);
		assertNotNull(archiveList.getPodcasts());

		Podcast podcast = archiveList.getPodcasts().get(0);

		assertEquals("Title", podcast.getTitle());
		assertEquals("Description", podcast.getShortDescriptionHtml());
		assertEquals("<p>Description</p>", podcast.getDescriptionHtml());
		assertEquals("embedurl", podcast.getEmbedUrl());
		assertEquals(21, podcast.getEpisode());
		assertEquals(2, podcast.getSeason());
		assertEquals("1", archiveList.getId());
		assertEquals("/content/efe/podcastlistpage/podcast.html", podcast.getPageLink());
		assertFalse(archiveList.isEmpty());
		assertFalse(archiveList.isApiError());
	}
	
	@Test
	void testPodcastsListNoConfigs() {
		when(efeService.getOmnyOrgId()).thenReturn(null);
		Resource resource = aemContext.currentResource("/content/efe/podcastlistpage/jcr:content/root/podcastlist");
		PodcastArchiveList archiveList = resource.adaptTo(PodcastArchiveList.class);
		assertEquals(0, archiveList.getPodcasts().size());
	}
	
	@Test
	void testPodcastsListEmpty() {
		lenient().when(efeService.getOmnyOrgId()).thenReturn(null);
		Resource resource = aemContext.currentResource("/content/efe/podcastlistpage/jcr:content/root/podcastlistempty");
		PodcastArchiveList archiveList = resource.adaptTo(PodcastArchiveList.class);
		assertNotNull(archiveList.getId());
	}
	
	
	
	
	@Test
	void testPodcastsListApiError() {
		when(restService.getData("playlistApi", null)).thenReturn(
				"{\"Clipss\":[{\"Id\":\"1\",\"Title\":\"Title\",\"DescriptionHtml\":\"<p>Description</p>\",\"Season\":2,\"Episode\":21,\"EpisodeType\":\"Full\",\"EmbedUrl\":\"embedurl\",\"PublishedUtc\":\"2023-05-25T16:26:22.327Z\"}]}");

		Resource resource = aemContext.currentResource("/content/efe/podcastlistpage/jcr:content/root/podcastlist");
		PodcastArchiveList archiveList = resource.adaptTo(PodcastArchiveList.class);
		assertEquals(0, archiveList.getPodcasts().size());
		assertTrue(archiveList.isApiError());
	}


}
