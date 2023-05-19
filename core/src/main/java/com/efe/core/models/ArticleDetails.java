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
}
