package com.efe.core.models;

import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.Resource;

public interface RelatedArticleDynamic {

    String getLinkText();

    String getRequestText();

    List<String> getRelatedArticlePagePaths();

    List<Resource> getRelatedTeasers();

    /**
     * Map of linkURL/page-path variants -> hero image src (mapped for the current env)
     */
    Map<String, String> getTeaserHeroImages();
}
