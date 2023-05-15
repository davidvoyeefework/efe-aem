package com.efe.core.utils;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.tagging.TagManager;
import com.day.cq.tagging.Tag;
import com.efe.core.bean.*;
import com.efe.core.constants.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ArticleDetailUtil
 */
public class ArticleDetailUtil {


    private ArticleDetailUtil() {
    }

    /**
     *
     * @param articleDetailsCFResource
     * @param resolver
     * @return list of articleDetails
     */
    public static Articles getArticleDetails(Resource articleDetailsCFResource, ResourceResolver resolver) {
        Articles articleDetails = new Articles();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy");


        Optional<ContentFragment> articleDetailsCF = Optional.ofNullable(articleDetailsCFResource.adaptTo(ContentFragment.class));
        articleDetails.setTitle(articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.TITLE))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

        articleDetails.setSubtitle(articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.SUBTITLE))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

        articleDetails.setDateUpdated(articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.DATE_UPDATED))
                .map(ContentElement::getContent).map(dateStr -> {
                    try {
                        Date date = inputFormat.parse(dateStr);
                        return outputFormat.format(date);
                    } catch (ParseException e) {
                        return null;
                    }
                }).orElse(StringUtils.EMPTY));

        articleDetails.setDatePublished(articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.DATE_PUBLISHED))
                .map(ContentElement::getContent).map(dateStr -> {
                    try {
                        Date date = inputFormat.parse(dateStr);
                        return outputFormat.format(date);
                    } catch (ParseException e) {
                        return null;
                    }
                }).orElse(StringUtils.EMPTY));

        articleDetails.setHeroImage(articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.HERO_IMAGE))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

        articleDetails.setAuthorType(new String[]{articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.AUTHOR_TYPE))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY)});

        articleDetails.setArticleSummary(articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.ARTICLE_SUMMARY))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

        articleDetails.setBody(articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.BODY))
                .map(ContentElement::getContent).map(body -> body.concat("/master")).orElse(StringUtils.EMPTY));

        String regularAuthorsCF = articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.REGULAR_AUTHOR))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY);
        articleDetails.setRegularAuthor(regularAuthorsCF.split("\n"));
        String tagsCF = articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.TAGS))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY);
        String[] authorTags = tagsCF.split("\n");
        TagManager tagManager = resolver.adaptTo(TagManager.class);
        List<String> tagTitles = new ArrayList<>();
        for (String tag : authorTags) {
            Tag efeTag = tagManager.resolve(tag);
            if (null != efeTag) {
                String tagTitle = efeTag.getTitle();
                tagTitles.add(tagTitle);
            }
        }
        articleDetails.setTags(tagTitles);

        String plannerAuthorsCF = articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.PLANNER))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY);
        articleDetails.setPlanner(plannerAuthorsCF.split("\n"));

        articleDetails.setArticleAuthors(setArticleAuthorDetails(articleDetails, resolver));

        articleDetails.setPlannerResponse(setArticlePlannerDetails(articleDetails, resolver));

        return articleDetails;
    }

    /**
     *
     * @param articleDetails
     * @param resourceResolver
     * @return articleAuthors
     */
    public static List<ArticleAuthor> setArticleAuthorDetails(Articles articleDetails, ResourceResolver resourceResolver) {
        String[] authors = articleDetails.getRegularAuthor();

        List<ArticleAuthor> articleAuthors = new ArrayList<>();
        if (authors != null) {
            for (String authorFragmentPath : authors) {
            	 
                Resource articleAuthorResource = resourceResolver.getResource(authorFragmentPath);
                if (null != articleAuthorResource) {
                    ArticleAuthor articleAuthor = new ArticleAuthor();
                    Optional<ContentFragment> authorDetailsCF = Optional.ofNullable(articleAuthorResource.adaptTo(ContentFragment.class));

                    articleAuthor.setTitle(authorDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.TITLE))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

                    articleAuthor.setName(authorDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.NAME))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

                    articleAuthor.setPhoto(authorDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.PHOTO))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

                    articleAuthor.setBiography(authorDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.BIOGRAPHY))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

                    articleAuthors.add(articleAuthor);
                }
            }
        }
        return articleAuthors;
    }

    /**
     *
     * @param articleDetails
     * @param resourceResolver
     * @return plannerResponseList
     */
    public static List<PlannerResponse> setArticlePlannerDetails(Articles articleDetails, ResourceResolver resourceResolver) {
        String[] planners = articleDetails.getPlanner();

        List<PlannerResponse> plannerResponseList = new ArrayList<>();
        if (planners != null) {
            for (String plannersFragmentPath : planners) {
                PlannerResponse plannerResponse = new PlannerResponse();
                Resource articlePlannerResource = resourceResolver.getResource(plannersFragmentPath);
                if (null != articlePlannerResource) {
                    Optional<ContentFragment> plannerDetailsCF = Optional.ofNullable(articlePlannerResource.adaptTo(ContentFragment.class));

                    plannerResponse.setTitle(plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.TITLE))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

                    plannerResponse.setFirstName(plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.FIRST_NAME))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

                    plannerResponse.setLastName(plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.LAST_NAME))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

                    plannerResponse.setBio(plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.BIO))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

                    String certifications = plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.CERTIFICATIONS))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY);
                    String[] certificationsList = certifications.split("\n");
                    plannerResponse.setCertifications(setPlannerCertificationDetails(certificationsList, resourceResolver));

                    String educations = plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.EDUCATION))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY);
                    String[] educationsList = educations.split("\n");
                    plannerResponse.setEducation(setPlannerEducationDetails(educationsList, resourceResolver));

                    plannerResponseList.add(plannerResponse);
                }
            }
        }
        return plannerResponseList;
    }

    /**
     *
     * @param certifications
     * @param resourceResolver
     * @return certificationsList
     */
    public static List<Certifications> setPlannerCertificationDetails(String[] certifications, ResourceResolver resourceResolver) {
        List<Certifications> certificationsList = new ArrayList<>();
        if (null != certifications) {
            for (String certification : certifications) {
                Resource certificationResource = resourceResolver.getResource(certification);
                if (null != certificationResource) {
                    Certifications certificationsBean = new Certifications();
                    Optional<ContentFragment> plannerDetailsCF = Optional.ofNullable(certificationResource.adaptTo(ContentFragment.class));

                    certificationsBean.setName(plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.NAME)).map(ContentElement::getContent).orElse(StringUtils.EMPTY));
                    certificationsBean.setAbbreviation(plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.ABBREVIATION)).map(ContentElement::getContent).orElse(StringUtils.EMPTY));
                    certificationsBean.setMarketingDisclosure(plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.MARKETING_DISCLOSURE)).map(ContentElement::getContent).orElse(StringUtils.EMPTY));
                    certificationsBean.setLegalComplianceDisclosure(plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.LEGAL_COMPLIANCE_DISCLOSURE)).map(ContentElement::getContent).orElse(StringUtils.EMPTY));
                    certificationsList.add(certificationsBean);
                }
            }
        }
        return certificationsList;
    }

    /**
     *
     * @param education
     * @param resourceResolver
     * @return educationList
     */
        public static List<Education> setPlannerEducationDetails (String[] education, ResourceResolver resourceResolver) {
        List<Education> educationList = new ArrayList<>();
        if(null != education) {
            for (String educations : education){
        Resource educationResource=resourceResolver.getResource(educations);
        if(null!=educationResource){
            Education educationBean=new Education();
        Optional<ContentFragment> plannerDetailsCF=Optional.ofNullable(educationResource.adaptTo(ContentFragment.class));

            educationBean.setMajor(plannerDetailsCF.map(cf->cf.getElement(ArticleDetailsConstants.MAJOR)).map(ContentElement::getContent).orElse(StringUtils.EMPTY));
            educationBean.setUniversity(plannerDetailsCF.map(cf->cf.getElement(ArticleDetailsConstants.UNIVERSITY)).map(ContentElement::getContent).orElse(StringUtils.EMPTY));
            educationBean.setDegree(plannerDetailsCF.map(cf->cf.getElement(ArticleDetailsConstants.DEGREE)).map(ContentElement::getContent).orElse(StringUtils.EMPTY));
            educationList.add(educationBean);
        }
        }

        }
            return educationList;
    }
        }
