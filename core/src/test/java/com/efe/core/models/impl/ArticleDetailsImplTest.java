package com.efe.core.models.impl;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.commons.Externalizer;
import com.efe.core.bean.Articles;
import com.efe.core.models.ArticleDetails;
import com.efe.core.services.EfeService;
import com.efe.core.services.SeoService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class ArticleDetailsImplTest {
    /** The aem context. */
    private AemContext aemContext = new AemContext();

    /** The Resource resolver. */
    @Mock
    ResourceResolver resolver;

    /** The resource. */
    @Mock
    Resource resource;
    
    @Mock
    Externalizer externalizer;
    
    @Mock
    SeoService seoService;
    
    @Mock
    EfeService efeService;
    
    ArticleDetails articleDetails;
    
    ContentFragment contentFragment;

    Articles articles;

    @BeforeEach
    void setUp() {
    	aemContext.addModelsForClasses(ContentFragment.class);
    	aemContext.addModelsForClasses(ArticleDetailsImpl.class);	
    	aemContext.registerService(Externalizer.class, externalizer);
		aemContext.registerService(EfeService.class, efeService);
		aemContext.registerService(SeoService.class, seoService);
		
    	aemContext.load().json("/com/efe/core/models/articleDetails/articleDetails.json", "/content");
        aemContext.load().json("/com/efe/core/models/articleDetails/articleFragment.json", "/content/dam/efe/test-article-cf/master");
        aemContext.load().json("/com/efe/core/models/articleDetails/regularAuthor.json", "/content/dam/efe/author1");
        aemContext.load().json("/com/efe/core/models/articleDetails/planner.json", "/content/dam/efe/cf/plan1");
        aemContext.load().json("/com/efe/core/models/articleDetails/education.json", "/content/dam/efe/test-education-cf");
        aemContext.load().json("/com/efe/core/models/articleDetails/primaryaddress.json", "/content/dam/efe/primaryoffice");
        aemContext.load().json("/com/efe/core/models/articleDetails/certification.json", "/content/dam/efe/test-certification");
        aemContext.load().json("/com/efe/core/models/articleDetails/tags.json", "/content/cq:tags");

        aemContext.currentResource("/content/efe/jcr:content/articleDetails");
        resolver = aemContext.resourceResolver();
        articleDetails = aemContext.request().adaptTo(ArticleDetails.class);

        Resource res = aemContext.currentResource("/content/dam/efe/test-article-cf/master");
        contentFragment = res.adaptTo(ContentFragment.class);

        Resource authorFragment = aemContext.currentResource("/content/dam/efe/author1");
        contentFragment = authorFragment.adaptTo(ContentFragment.class);

    }

    @Test
    void getArticleCertification() {
        assertEquals("CERTIFIED FINANCIAL PLANNER", articleDetails.getArticleFragmentPath().getPlannerResponse().get(0).getCertifications().get(0).getName());
        assertEquals("CFP", articleDetails.getArticleFragmentPath().getPlannerResponse().get(0).getCertifications().get(0).getAbbreviation());
        assertEquals("Legal Compliance Disclosure", articleDetails.getArticleFragmentPath().getPlannerResponse().get(0).getCertifications().get(0).getLegalComplianceDisclosure());
        assertEquals("Marketing Disclosure", articleDetails.getArticleFragmentPath().getPlannerResponse().get(0).getCertifications().get(0).getMarketingDisclosure());
    }

    @Test
    void getArticles() {
        assertNotNull(articleDetails.getArticleFragmentPath());
        assertEquals("<p style=\"text-align: center;\">TItLE that may have many words in it and go on two lines</p>\n", articleDetails.getArticleFragmentPath().getTitle());
        assertEquals("<p style=\"text-align: center;\">Subtitle ante egestas amet in aliquam nam porttitor tincidunt gravida uit purus. Massa id magna quisque semper euismod.</p>\n", articleDetails.getArticleFragmentPath().getSubtitle());
        assertNotNull(articleDetails.getArticleFragmentPath().getDateUpdated());
        assertNotNull(articleDetails.getArticleFragmentPath().getDatePublished());
        assertNotNull(articleDetails.getArticleFragmentPath().getArticleSummary());
        assertEquals("/content/efe/us/en/corp", articleDetails.getMappedPage());
        assertNotNull(articleDetails.getArticleFragmentPath().getRegularAuthor());
        assertNotNull(articleDetails.getArticleFragmentPath().getAuthorType());
        assertNotNull(articleDetails.getJsonLd());
        assertEquals("/content/dam/efe/rebalance-in-a-down-market.jpg", articleDetails.getArticleFragmentPath().getHeroImage());
        assertEquals("/content/experience-fragments/efe/us/en/site/testArticleXF/master", articleDetails.getArticleFragmentPath().getBody());
    }

    @Test
    void getArticlesAuthor() {
        assertEquals("Content Writter", articleDetails.getArticleFragmentPath().getArticleAuthors().get(0).getTitle());
        assertEquals("<p style=\"text-align: center;\">Testing the Content Fragment for Author CF Model.</p>\n", articleDetails.getArticleFragmentPath().getArticleAuthors().get(0).getBiography());
        assertEquals("Author Test", articleDetails.getArticleFragmentPath().getArticleAuthors().get(0).getName());
        assertEquals("/content/dam/efe/image5.png", articleDetails.getArticleFragmentPath().getArticleAuthors().get(0).getPhoto());
    }

    @Test
    void getArticlesPlannerResponse() {
        assertEquals("Johnathan", articleDetails.getArticleFragmentPath().getPlannerResponse().get(0).getFirstName());
        assertEquals("Aaron", articleDetails.getArticleFragmentPath().getPlannerResponse().get(0).getMiddleName());
        assertNotNull(articleDetails.getArticleFragmentPath().getPlannerResponse().get(0).getLastName());
        assertNotNull(articleDetails.getArticleFragmentPath().getPlannerResponse().get(0).getDesktopImageUrl());
        assertEquals("As a financial planner since 2006, I enjoy serving families and business owners in the areas of investments, financial planning, estate planning, income distribution strategies, insurance planning and multi-generational wealth accumulation and transfer. Outside of work I enjoy spending time with my family, the outdoors and reading.", articleDetails.getArticleFragmentPath().getPlannerResponse().get(0).getBio());
    }

    @Test
    void getArticlesEducation() {
        assertNotNull(articleDetails.getArticleFragmentPath().getPlannerResponse().get(0).getEducation().get(0).getDegree());
        assertNotNull(articleDetails.getArticleFragmentPath().getPlannerResponse().get(0).getEducation().get(0).getMajor());
        assertNotNull(articleDetails.getArticleFragmentPath().getPlannerResponse().get(0).getEducation().get(0).getUniversity());
    }

    @Test
    void getId() {
        assertNotNull(articleDetails.getId());

    }
}
