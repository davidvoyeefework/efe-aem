package com.efe.core.models;

import com.efe.core.bean.Articles;

public interface ArticleDetails {

    /**
     * Method to return article fragment path
     *
     * @return the articleFragmentPath
     */
    public Articles getArticleFragmentPath();

    /**
     *
     * @return the id.
     */
    public String getId();
}
