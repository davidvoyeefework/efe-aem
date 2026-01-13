package com.efe.core.models;

import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.Resource;

public interface RelatedArticleDynamic {

    String getLinkText();

    String getRequestText();

    List<String> getRelatedArticlePagePaths();

    List<Resource> getRelatedTeasers();

    Map<String, String> getTeaserHeroImages();

    /**
     * Max items configured in dialog (./maxItems).
     * Returns 0 when unlimited (blank or 0 in dialog).
     */
    int getMaxItems();
}
