package com.efe.core.models;

import com.efe.core.bean.Articles;

/**
 * The Interface ArticleDetails.
 */
public interface ArticleDetails {

    /**
     * Method to return article fragment path.
     *
     * @return the articleFragmentPath
     */
    public Articles getArticleFragmentPath();

    /**
     * Gets the id.
     *
     * @return the id.
     */
    public String getId();

    /**
     * Gets the json ld.
     *
     * @return the jsonLd
     */
    String getJsonLd();

    // Value for showing inline section CTA
    String getShowCta();

    // Get CTA Headline
    String getCtaHeading();

    // Get CTA BodyCopy
    String getBodyCopy();

    // Get CTA Text Label
    String getCtaText();

    // Get CTA Label
    String getCtaLink();

}
