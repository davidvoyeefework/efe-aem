package com.efe.core.utils;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.PageManager;
import com.efe.core.bean.*;
import com.efe.core.constants.ArticleDetailsConstants;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.services.DynamicMediaService;
import com.efe.core.services.EfeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ArticleDetailUtil
 */
public class ArticleDetailUtil {

    private ArticleDetailUtil() {
    }

    /**
     * @param articleDetailsCFResource
     * @param resolver
     * @param tags
     * @param mappedPage
     * @param pageManager
     * @param dynamicMediaService 
     * @return list of articleDetails
     */
    public static Articles getArticleDetails(Resource articleDetailsCFResource, ResourceResolver resolver, String[] tags,
        String mappedPage, PageManager pageManager, EfeService efeService, DynamicMediaService dynamicMediaService) {
        Articles articleDetails = new Articles();
        List<LinkBean> links = null;

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

        String heroImage = articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.HERO_IMAGE))
        .map(ContentElement::getContent).orElse(StringUtils.EMPTY);
        
        if(StringUtils.isNotEmpty(heroImage)) {
        	articleDetails.setHeroImage(dynamicMediaService.getDmImagePath(resolver, heroImage));
        }

        articleDetails.setAuthorType(new String[] { articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.AUTHOR_TYPE))
            .map(ContentElement::getContent).orElse(StringUtils.EMPTY) });

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

        List<String> hideTagsList = new ArrayList<>();
        if (null != tags) {
            hideTagsList = Arrays.asList(tags);
        }

        if (null != authorTags) {
            links = new ArrayList<>();
            for (String pageTag : authorTags) {
                TagManager tagManager = resolver.adaptTo(TagManager.class);
                Tag efeTag = tagManager.resolve(pageTag);
                if (!hideTagsList.isEmpty() && hideTagsList.contains(efeTag.getTagID())) {
                    continue;
                }
                LinkBean linkBean = new LinkBean();
                linkBean.setTagLabel(efeTag.getTitle());

                linkBean.setTagLink(EFEUtil.getTagLink(efeTag, pageManager, mappedPage, resolver));
                links.add(linkBean);

            }
        }

        articleDetails.setLinks(links);
        String plannerAuthorsCF = articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.PLANNER))
            .map(ContentElement::getContent).orElse(StringUtils.EMPTY);
        articleDetails.setPlanner(plannerAuthorsCF.split("\n"));
        String disclosureCF = articleDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.DISCLOSURE))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY);
        articleDetails.setDisclosure(disclosureCF.split("\n"));

        articleDetails.setArticleAuthors(setArticleAuthorDetails(articleDetails, resolver));

        articleDetails.setPlannerResponse(setArticlePlannerDetails(articleDetails, resolver, efeService));

        articleDetails.setDisclosures(setArticleDisclosureDetails(articleDetails, resolver));

        return articleDetails;
    }

    /**
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
                if (null != articleAuthorResource && null != articleAuthorResource.adaptTo(ContentFragment.class)) {
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

                    String biography = articleAuthor.getBiography();
                    if (biography.length() > 400) {
                        biography = biography.substring(0, 400) + "...";
                    }
                    articleAuthor.setBiography(biography);

                    articleAuthor.setLiveURL(authorDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.LIVEURL))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

                    articleAuthors.add(articleAuthor);
                }
            }
        }
        return articleAuthors;
    }

    /**
     * @param articleDetails
     * @param resourceResolver
     * @return plannerResponseList
     */
    public static List<PlannerResponse> setArticlePlannerDetails(Articles articleDetails, ResourceResolver resourceResolver, EfeService efeService) {
        String[] planners = articleDetails.getPlanner();

        List<PlannerResponse> plannerResponseList = new ArrayList<>();
        if (planners != null) {
            for (String plannersFragmentPath : planners) {

                Resource articlePlannerResource = resourceResolver.getResource(plannersFragmentPath);
                if (null != articlePlannerResource && null != articlePlannerResource.adaptTo(ContentFragment.class)) {
                    PlannerResponse plannerResponse = getPlannerDetails(resourceResolver, articlePlannerResource, efeService);
                    plannerResponseList.add(plannerResponse);
                }
            }
        }
        return plannerResponseList;
    }

    /**
     * Gets the planner details.
     *
     * @param resourceResolver the resource resolver
     * @param articlePlannerResource the article planner resource
     * @return the planner details
     */
    public static PlannerResponse getPlannerDetails(ResourceResolver resourceResolver,
        Resource articlePlannerResource, EfeService efeService) {

        PlannerResponse plannerResponse = new PlannerResponse();

        Optional<ContentFragment> plannerDetailsCF = Optional.ofNullable(articlePlannerResource.adaptTo(ContentFragment.class));

        String id = plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.ID))
            .map(ContentElement::getContent).orElse(StringUtils.EMPTY);

        if (StringUtils.isNotEmpty(id)) {
            plannerResponse.setId(Integer.parseInt(id));
        }

        plannerResponse.setTitle(plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.TITLE))
            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

        String firstName = plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.FIRST_NAME))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY);

        String firstNameAlias = plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.FIRST_NAME_ALIAS))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY);

        if(StringUtils.isEmpty(firstNameAlias)) {
            firstNameAlias = firstName;
        }
        plannerResponse.setFirstName(firstName);

        plannerResponse.setFirstNameAlias(firstNameAlias);

        plannerResponse.setMiddleName(plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.MIDDLE_NAME))
            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

        String lastName = plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.LAST_NAME))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY);

        plannerResponse.setLastName(lastName);

        String plannerId = plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.ID))
                .map(ContentElement::getContent).orElse(StringUtils.EMPTY);

        String url = LinkUtil.getFormattedLink(efeService.getPlannerBioPageUrl() + PlannerLocationConstants.DOT
                + firstName + PlannerLocationConstants.DOT + lastName
                + PlannerLocationConstants.DOT + plannerId, resourceResolver);

        plannerResponse.setUrl(url);


        plannerResponse.setBio(plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.BIO))
            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

        plannerResponse.setDesktopImageUrl(plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.DESKTOP_IMAGE_URL))
            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

        String certifications = plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.CERTIFICATIONS))
            .map(ContentElement::getContent).orElse(StringUtils.EMPTY);

        if (StringUtils.isNotEmpty(certifications)) {
            String[] certificationsList = certifications.split("\n");
            plannerResponse.setCertifications(setPlannerCertificationDetails(certificationsList, resourceResolver));

            if (!plannerResponse.getCertifications().isEmpty()) {
                List<String> certificationAbbrevations = plannerResponse.getCertifications().stream()
                    .map(Certifications::getAbbreviation)
                    .collect(Collectors.toList());
                plannerResponse.setCertificationsAbbrevations(certificationAbbrevations);
            }
        }

        String educations = plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.EDUCATION))
            .map(ContentElement::getContent).orElse(StringUtils.EMPTY);

        if (StringUtils.isNotEmpty(educations)) {
            String[] educationsList = educations.split("\n");
            plannerResponse.setEducation(setPlannerEducationDetails(educationsList, resourceResolver));
        }
        
        String supportstaffs = plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.SUPPORT_STAFF))
            .map(ContentElement::getContent).orElse(StringUtils.EMPTY);

        if (StringUtils.isNotEmpty(supportstaffs)) {
            String[] staffList = supportstaffs.split("\n");
            plannerResponse.setSupportStaff(setPlannerSupportStaffDetails(staffList, resourceResolver));
        }

        String primaryOfficeCF = plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.PRIMARY_OFFICE))
            .map(ContentElement::getContent).orElse(StringUtils.EMPTY);
        plannerResponse.setPrimaryOffice(setPrimaryOfficeDetails(primaryOfficeCF, resourceResolver));
        return plannerResponse;
    }

    /**
     * Sets the primary office details.
     *
     * @param primaryOfficeCF the primary office CF
     * @param resourceResolver the resource resolver
     * @return the primary office
     */
    private static PrimaryOffice setPrimaryOfficeDetails(String primaryOfficeCF, ResourceResolver resourceResolver) {
        PrimaryOffice primaryOffice = null;
        if (null != primaryOfficeCF) {
            Resource primaryOfficeResource = resourceResolver.getResource(primaryOfficeCF);
            if (null != primaryOfficeResource) {
                Optional<ContentFragment> plannerDetailsCF = Optional.ofNullable(primaryOfficeResource.adaptTo(ContentFragment.class));
                primaryOffice = new PrimaryOffice();
                primaryOffice.setName(
                    plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.NAME)).map(ContentElement::getContent)
                        .orElse(StringUtils.EMPTY));
                primaryOffice.setCity(
                    plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.CITY)).map(ContentElement::getContent)
                        .orElse(StringUtils.EMPTY));
                primaryOffice.setZip(plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.ZIP)).map(ContentElement::getContent)
                    .orElse(StringUtils.EMPTY));
                primaryOffice.setState(
                    plannerDetailsCF.map(cf -> cf.getElement(PlannerLocationConstants.STATE)).map(ContentElement::getContent)
                        .orElse(StringUtils.EMPTY));
            }
        }
        return primaryOffice;

    }

    /**
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

                    certificationsBean.setName(
                        plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.NAME)).map(ContentElement::getContent)
                            .orElse(StringUtils.EMPTY));
                    certificationsBean.setAbbreviation(
                        plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.ABBREVIATION)).map(ContentElement::getContent)
                            .orElse(StringUtils.EMPTY));
                    certificationsBean.setMarketingDisclosure(
                        plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.MARKETING_DISCLOSURE))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));
                    certificationsBean.setLegalComplianceDisclosure(
                        plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.LEGAL_COMPLIANCE_DISCLOSURE))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));
                    certificationsList.add(certificationsBean);
                }
            }
        }
        return certificationsList;
    }

    /**
     * Sets the planner education details.
     *
     * @param education the education
     * @param resourceResolver the resource resolver
     * @return educationList
     */
    public static List<Education> setPlannerEducationDetails(String[] education, ResourceResolver resourceResolver) {
        List<Education> educationList = new ArrayList<>();
        if (null != education) {
            for (String educations : education) {
                Resource educationResource = resourceResolver.getResource(educations);
                if (null != educationResource) {
                    Education educationBean = new Education();
                    Optional<ContentFragment> plannerDetailsCF = Optional
                        .ofNullable(educationResource.adaptTo(ContentFragment.class));

                    educationBean.setMajor(plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.MAJOR))
                        .map(ContentElement::getContent).orElse(StringUtils.EMPTY));
                    educationBean
                        .setUniversity(plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.UNIVERSITY))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));
                    educationBean.setDegree(plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.DEGREE))
                        .map(ContentElement::getContent).orElse(StringUtils.EMPTY));
                    educationList.add(educationBean);
                }
            }

        }
        return educationList;
    }
    
        public static List<SupportStaff> setPlannerSupportStaffDetails(String[] supportstaff, ResourceResolver resourceResolver) {
        List<SupportStaff> suportStaffList = new ArrayList<>();
        if (null != supportstaff) {
            for (String supportstaffs : supportstaff) {
                Resource supportStaffResource = resourceResolver.getResource(supportstaffs);
                if (null != supportStaffResource) {
                    SupportStaff supportStaffBean = new SupportStaff();
                    Optional<ContentFragment> plannerDetailsCF = Optional
                        .ofNullable(supportStaffResource.adaptTo(ContentFragment.class));
                    
                    supportStaffBean.setFirstName(plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.STAFF_FIRST_NAME))
                        .map(ContentElement::getContent).orElse(StringUtils.EMPTY));
                    supportStaffBean.setLastName(plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.STAFF_LAST_NAME))
                        .map(ContentElement::getContent).orElse(StringUtils.EMPTY));
                    supportStaffBean.setPhoto(plannerDetailsCF.map(cf -> cf.getElement(ArticleDetailsConstants.STAFF_PHOTO))
                        .map(ContentElement::getContent).orElse(StringUtils.EMPTY));

                    suportStaffList.add(supportStaffBean);
                }
            }

        }
        return suportStaffList;
    }

    /**
     *
     * @param articleDetails
     * @param resourceResolver
     * @return
     */
    public static List<Disclosures> setArticleDisclosureDetails(Articles articleDetails, ResourceResolver resourceResolver) {
        List<Disclosures> disclosuresList = new ArrayList<>();
        String[] disclosure = articleDetails.getDisclosure();
        if (null != disclosure) {
            for (String disclosures : disclosure) {
                Resource disclosureResource = resourceResolver.getResource(disclosures);
                if (null != disclosureResource) {
                    Disclosures disclosuresBean = new Disclosures();
                    Optional<ContentFragment> disclosureCF = Optional.ofNullable(disclosureResource.adaptTo(ContentFragment.class));

                    disclosuresBean.setDisclosureText(disclosureCF.map(cf -> cf.getElement(ArticleDetailsConstants.DISCLOSURE_TEXT))
                            .map(ContentElement::getContent).orElse(StringUtils.EMPTY));
                    disclosuresList.add(disclosuresBean);
                }
            }
        }
        return disclosuresList;
    }
}
