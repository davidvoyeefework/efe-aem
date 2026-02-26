package com.efe.core.models;

import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.Resource;

public interface RelatedArticleDynamic {

    List<Resource> getRelatedTeasers();

    Map<String, String> getTeaserHeroImages();

    List<String> getRelatedArticlePagePaths();

    /**
     * 0 means unlimited.
     */
    int getMaxItems();
}