package com.efe.core.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The Articles bean class.
 */
public class Articles {

    /** The articleSummary. */
    private String articleSummary;

    /** The authorType. */
    private String[] authorType;

    /** The body. */
    private  String body;

    /** The tags. */
    private List<String> tags;

    /** The datePublished. */
    private String datePublished;

    /** The dateUpdated. */
    private String dateUpdated;

    /** The subtitle. */
    private String subtitle;

    /** The title. */
    private String title;

    /** The heroImage. */
    private String heroImage;

    /** The planner. */
    private String[] planner;

    /** The regularAuthor. */
    private String[] regularAuthor;

    /** The articleAuthors. */
    private List<ArticleAuthor> articleAuthors;

    /** The plannerResponse. */
    private List<PlannerResponse> plannerResponse;

    /**
     * Gets the plannerResponse.
     *
     * @return the plannerResponse
     */

    public List<PlannerResponse> getPlannerResponse() {
        if (Objects.nonNull(plannerResponse)) {
            return new ArrayList<>(plannerResponse);
        }
        return Collections.emptyList();
    }

    /**
     * Sets the plannerResponse.
     *
     * @return the plannerResponse
     */
    public void setPlannerResponse(List<PlannerResponse> plannerResponse) {
        this.plannerResponse = plannerResponse;
    }

    /**
     * Gets the articleAuthors.
     *
     * @return the articleAuthors
     */
    public List<ArticleAuthor> getArticleAuthors() {
        if (Objects.nonNull(articleAuthors)) {
            return new ArrayList<>(articleAuthors);
        }
        return Collections.emptyList();
    }

    /**
     * Sets the articleAuthors.
     *
     * @return the articleAuthors
     */
    public void setArticleAuthors(List<ArticleAuthor> articleAuthors) {
        this.articleAuthors = articleAuthors;
    }

    /**
     * Gets the articleSummary.
     *
     * @return the articleSummary
     */
    public String getArticleSummary() {
        return articleSummary;
    }

    /**
     * Sets the articleSummary.
     *
     * @return the articleSummary
     */
    public void setArticleSummary(String articleSummary) {
        this.articleSummary = articleSummary;
    }

    /**
     * Gets the authorType.
     *
     * @return the authorType
     */
    public String[] getAuthorType() {
        return authorType;
    }

    /**
     * Sets the authorType.
     *
     * @return the authorType
     */
    public void setAuthorType(String[] authorType) {
        this.authorType = authorType;
    }

    /**
     * Gets the body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body.
     *
     * @return the body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets the tags.
     *
     * @return the tags
     */
    public List<String> getTags() {
        if (Objects.nonNull(tags)) {
            return new ArrayList<>(tags);
        }
        return Collections.emptyList();
    }

    /**
     * Sets the tags.
     *
     * @return the tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Gets the datePublished.
     *
     * @return the datePublished
     */
    public String getDatePublished() {
        return datePublished;
    }

    /**
     * Sets the datePublished.
     *
     * @return the datePublished
     */
    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    /**
     * Gets the dateUpdated.
     *
     * @return the dateUpdated
     */
    public String getDateUpdated() {
        return dateUpdated;
    }

    /**
     * Sets the dateUpdated.
     *
     * @return the dateUpdated
     */
    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    /**
     * Gets the subtitle.
     *
     * @return the subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Sets the subtitle.
     *
     * @return the subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @return the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the heroImage.
     *
     * @return the heroImage
     */
    public String getHeroImage() {
        return heroImage;
    }

    /**
     * Sets the heroImage.
     *
     * @return the heroImage
     */
    public void setHeroImage(String heroImage) {
        this.heroImage = heroImage;
    }

    /**
     * Gets the planner.
     *
     * @return the planner
     */
    public String[] getPlanner() {
        return planner;
    }

    /**
     * Sets the planner.
     *
     * @return the planner
     */
    public void setPlanner(String[] planner) {
        this.planner = planner;
    }

    /**
     * Gets the RegularAuthor.
     *
     * @return the RegularAuthor
     */
    public String[] getRegularAuthor() {
        return regularAuthor;
    }

    /**
     * Sets the RegularAuthor.
     *
     * @return the RegularAuthor
     */
    public void setRegularAuthor(String[] regularAuthor) {
        this.regularAuthor = regularAuthor;
    }

}
