package com.efe.core.services.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SeoServiceImplTest {
    private SeoServiceImpl seoServiceImpl = new SeoServiceImpl();

    @Mock
    private SeoServiceImpl.Config config;

    public final AemContext aemContext = new AemContext();

    @BeforeEach
    void setUp() {
        aemContext.registerService(SeoServiceImpl.class, seoServiceImpl);
        config = Mockito.mock(SeoServiceImpl.Config.class);
    }

    @Test
    void testActivate() {
        Mockito.lenient().when(config.contextUrl()).thenReturn("contextUrl");
        Mockito.lenient().when(config.siteName()).thenReturn("siteName");
        Mockito.lenient().when(config.orgType()).thenReturn("orgType");
        Mockito.lenient().when(config.contactType()).thenReturn("contactType");
        Mockito.lenient().when(config.contactTypeName()).thenReturn("contactTypeName");
        Mockito.lenient().when(config.contactOption()).thenReturn("contactOption");
        Mockito.lenient().when(config.areaServed()).thenReturn("areaServed");
        Mockito.lenient().when(config.addressType()).thenReturn("addressType");
        Mockito.lenient().when(config.geoType()).thenReturn("geoType");
        Mockito.lenient().when(config.businessType()).thenReturn("businessType");
        Mockito.lenient().when(config.faqType()).thenReturn("faqType");
        Mockito.lenient().when(config.answerType()).thenReturn("answerType");
        Mockito.lenient().when(config.questionType()).thenReturn("questionType");
        Mockito.lenient().when(config.breadCrumbType()).thenReturn("breadCrumbType");
        Mockito.lenient().when(config.breadCrumbItemType()).thenReturn("breadCrumbItemType");
        Mockito.lenient().when(config.youtubeAPIUrl()).thenReturn("youtubeAPIUrl");
        Mockito.lenient().when(config.videoType()).thenReturn("videoType");
        Mockito.lenient().when(config.podcastType()).thenReturn("podcastType");
        Mockito.lenient().when(config.podcastGenre()).thenReturn("podcastGenre");
        Mockito.lenient().when(config.podcastAwards()).thenReturn("podcastAwards");

        seoServiceImpl.activate(config);

        assertEquals("contextUrl", seoServiceImpl.getContextUrl());
        assertEquals("siteName", seoServiceImpl.getSiteName());
        assertEquals("orgType", seoServiceImpl.getOrgType());
        assertEquals("contactType", seoServiceImpl.getContactType());
        assertEquals("contactTypeName", seoServiceImpl.getContactTypeName());
        assertEquals("contactOption", seoServiceImpl.getContactOption());
        assertEquals("areaServed", seoServiceImpl.getAreaServed());
        assertEquals("addressType", seoServiceImpl.getAdressType());
        assertEquals("geoType", seoServiceImpl.getGeoType());
        assertEquals("businessType", seoServiceImpl.getBusinessType());
        assertEquals("faqType", seoServiceImpl.getFaqType());
        assertEquals("answerType", seoServiceImpl.getAnswerType());
        assertEquals("questionType", seoServiceImpl.getQuestionType());
        assertEquals("breadCrumbType", seoServiceImpl.getBreadCrumbType());
        assertEquals("breadCrumbItemType", seoServiceImpl.getBreadCrumbItemType());
        assertEquals("youtubeAPIUrl", seoServiceImpl.getYoutubeAPIUrl());
        assertEquals("videoType", seoServiceImpl.getVideoType());
        assertEquals("podcastType", seoServiceImpl.getPodcastType());
        assertEquals("podcastGenre", seoServiceImpl.getPodcastGenreValue());
        assertEquals("podcastAwards", seoServiceImpl.getPodcastAwardValue());
    }
}
