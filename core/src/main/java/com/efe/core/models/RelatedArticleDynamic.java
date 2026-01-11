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
     * Map of article page path -> hero image DAM path from the referenced Content Fragment.
     * Example key: /content/efe/us/en/education/tax/capital-gains-tax-iowa
     * Example value: /content/dam/efe/corporate-brand/.../AdobeStock_444752352.jpeg
     */
    Map<String, String> getTeaserHeroImages();
}
