package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.efe.core.services.impl.DynamicMediaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.commons.Externalizer;
import com.efe.core.models.Video;
import com.efe.core.services.RestService;
import com.efe.core.services.SeoService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class VideoImplTest {

	private AemContext aemContext = new AemContext();

	@Mock
	private Externalizer externalizer;

	@Mock
	private RestService restService;

	@Mock
	private SeoService seoService;

	/** The DynamicMediaServiceImpl. */
	private DynamicMediaServiceImpl dynamicMediaService = new DynamicMediaServiceImpl();

	private Video video;

	@BeforeEach
	void init() {
		aemContext.registerService(Externalizer.class, externalizer);
		aemContext.registerService(RestService.class, restService);
		aemContext.registerService(SeoService.class, seoService);
		aemContext.load().json("/com/efe/core/models/video.json", "/content");
		aemContext.registerInjectActivateService(dynamicMediaService);
		aemContext.currentResource("/content/efe/jcr:content/video");
	}

	@Test
	void testWithoutYoutubeApi() {
		video = aemContext.request().adaptTo(Video.class);
		assertEquals("1", video.getId());
		assertEquals("12345", video.getVideoId());
		assertEquals("/thumbnail.png?fmt=png-alpha&bfc=on", video.getVideoThumbnail());
		assertEquals("/btn-icon.png?fmt=png-alpha&bfc=on", video.getButtonIcon());
		
	}

	@Test
	void testWithYoutubeApi() {
		when(seoService.getYoutubeAPIUrl()).thenReturn("http://test-api");
		when(seoService.getVideoType()).thenReturn("video");
		when(seoService.getContextUrl()).thenReturn("http://test-context");
		when(restService.getData("http://test-api", null)).thenReturn(
				"{\"items\":[{\"id\":\"UvegGOwSGBI\",\"snippet\":{\"publishedAt\":\"2023-05-05T13:31:03Z\",\"title\":\"Test Title\",\"description\":\"Test Desc\"},\"contentDetails\":{\"duration\":\"PT1M\"}}]}");

		video = aemContext.request().adaptTo(Video.class);
		assertNotNull(video.getJsonLd());
		assertEquals("Test Title", video.getVideoTitle());
		assertEquals("{\"event\":\"video_action\",\"video\":{\"videoTimed\":{\"publisher\":\"youtube\",\"primaryAssetViewDetails\":{\"videoName\":\"Test Title\"},\"starts\":{\"value\":0},\"completes\":{\"value\":0}}}}", video.getDataLayer() );
		assertEquals(false, video.isEmpty());
	}
	
	@Test
	void testIdAttribute() {
		video = aemContext.currentResource("/content/efe/jcr:content/video2")
				.adaptTo(Video.class);
		assertEquals("video2-63cd0bd9a1", video.getId());
	}

}
