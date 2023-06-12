package com.efe.core.sitemap;

import com.adobe.aem.wcm.seo.sitemap.PageTreeSitemapGenerator;
import com.day.cq.commons.Externalizer;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.constants.NameConstants;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.services.EfeService;
import com.efe.core.utils.ResourceUtil;
import io.wcm.testing.mock.aem.junit5.AemContext;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.sling.sitemap.SitemapException;
import org.apache.sling.sitemap.SitemapService;
import org.apache.sling.sitemap.builder.Sitemap;
import org.apache.sling.sitemap.builder.Url;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The Class EFESitemapGeneratorTest.
 */
@ExtendWith(MockitoExtension.class)
class EFESitemapGeneratorTest {

    /** The efeSitemapGenerator. */
    private EFESitemapGenerator efeSitemapGenerator = new EFESitemapGenerator();

    /** The aem context. */
    private final AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);

    /** The Sitemap. */
    @Mock
    private Sitemap sitemap;

    /** The resource. */
    @Mock
    private Resource resource;

    /** The Page. */
    @Mock
    private Page page;

    /** The ResourceResolverFactory. */
    @Mock
    private ResourceResolverFactory resolverFactory;

    /** The resource resolver. */
    @Mock
    private ResourceResolver resourceResolver;

    /** The EfeService. */
    @Mock
    private EfeService efeService;

    /** The Externalizer. */
    @Mock
    private Externalizer externalizer;

    /**
     * The defaultGenerator
     */
    @Mock
    private PageTreeSitemapGenerator defaultGenerator;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Field field = efeSitemapGenerator.getClass().getDeclaredField("resolverFactory");
        field.setAccessible(true);
        field.set(efeSitemapGenerator, resolverFactory);

        Field efeServiceField = efeSitemapGenerator.getClass().getDeclaredField("efeService");
        efeServiceField.setAccessible(true);
        efeServiceField.set(efeSitemapGenerator, efeService);

        Field externalizerField = efeSitemapGenerator.getClass().getDeclaredField("externalizer");
        externalizerField.setAccessible(true);
        externalizerField.set(efeSitemapGenerator, externalizer);

        Field defaultGeneratorField = efeSitemapGenerator.getClass().getDeclaredField("defaultGenerator");
        defaultGeneratorField.setAccessible(true);
        defaultGeneratorField.set(efeSitemapGenerator, defaultGenerator);
    }

    @Test
    void addResourceTest() throws SitemapException {
        when(resource.adaptTo(Page.class)).thenReturn(page);
        when(ResourceUtil.getServiceResourceResolver(resolverFactory)).thenReturn(resourceResolver);
        when(resource.getPath()).thenReturn("/content/efe/us/en/locations");
        Resource locationResource = mock(Resource.class);
        when(resourceResolver.getResource(PlannerLocationConstants.LOCATION_PATH)).thenReturn(locationResource);
        Resource stateResource = mock(Resource.class);
        Resource cityResource = mock(Resource.class);
        when(locationResource.getChildren()).thenReturn(Collections.singletonList(stateResource));
        when(stateResource.isResourceType("sling:OrderedFolder")).thenReturn(true);
        when(stateResource.getChildren()).thenReturn(Collections.singletonList(cityResource));
        when(efeService.getPlannerPageUrl()).thenReturn("/planner");
        when(stateResource.getName()).thenReturn("state");
        when(cityResource.getName()).thenReturn("city");
        when(externalizer.publishLink(resourceResolver, "/planner.STATE.City")).thenReturn("publish/planner.STATE.City");
        Url url = mock(Url.class);
        when(sitemap.addUrl("publish/planner.STATE.City")).thenReturn(url);
        ValueMap vm = mock(ValueMap.class);
        when(cityResource.getValueMap()).thenReturn(vm);
        Calendar calendar = mock(Calendar.class);
        when(vm.get(JcrConstants.JCR_CREATED, Calendar.class)).thenReturn(calendar);

        Resource plannerFolderResource = mock(Resource.class);
        when(resourceResolver.getResource(PlannerLocationConstants.PLANNER_PATH)).thenReturn(plannerFolderResource);
        when(plannerFolderResource.hasChildren()).thenReturn(true);
        Resource plannerFolder = mock(Resource.class);
        when(plannerFolderResource.getChildren()).thenReturn(Collections.singletonList(plannerFolder));
        when(plannerFolder.isResourceType(JcrResourceConstants.NT_SLING_ORDERED_FOLDER)).thenReturn(true);
        when(plannerFolder.getName()).thenReturn("plannerName");
        when(plannerFolder.getValueMap()).thenReturn(vm);
        when(vm.get(JcrConstants.JCR_TITLE, String.class)).thenReturn("plannerFolder");
        when(plannerFolder.getPath()).thenReturn("/content/dam/test");
        Resource plannerResource = mock(Resource.class);
        when(resourceResolver.getResource("/content/dam/test/fragment_plannerFolder")).thenReturn(plannerResource);
        Resource plannerMaster = mock(Resource.class);
        when(plannerResource.getChild("/content/dam/test/fragment_plannerFolder/jcr:content/data/master")).thenReturn(plannerMaster);
        when(plannerMaster.getPath()).thenReturn("/content/dam/test/fragment_plannerFolder/jcr:content/data/master");
        when(resourceResolver.getResource("/content/dam/test/fragment_plannerFolder/jcr:content/data/master")).thenReturn(plannerMaster);
        ValueMap property = mock(ValueMap.class);
        when(plannerMaster.adaptTo(ValueMap.class)).thenReturn(property);
        when(property.get("firstName", String.class)).thenReturn("firstName");
        when(property.get("lastName", String.class)).thenReturn("lastName");
        when(efeService.getPlannerBioPageUrl()).thenReturn("/plannerBio");
        when(externalizer.publishLink(resourceResolver, "/plannerBio.firstName.lastName.plannerName")).thenReturn("publish/plannerBio.firstName.lastName.plannerName");
        Url url1 = mock(Url.class);
        when(sitemap.addUrl("publish/plannerBio.firstName.lastName.plannerName")).thenReturn(url1);
        when(plannerResource.getValueMap()).thenReturn(vm);
        when(vm.get(JcrConstants.JCR_CREATED, Calendar.class)).thenReturn(calendar);
        efeSitemapGenerator.addResource("sitemap", sitemap, resource);
        assertNotNull(resource.adaptTo(Page.class));


        lenient().when(externalizer.publishLink(resourceResolver, "/plannerBio.firstName.lastName.plannerName")).thenReturn("publish/plannerBio.firstName.lastName.plannerName");
        lenient().when(sitemap.addUrl("publish/plannerBio.firstName.lastName.plannerName")).thenThrow(SitemapException.class);
        efeSitemapGenerator.addResource("sitemap", sitemap, resource);

        when(plannerResource.getChild("/content/dam/test/fragment_plannerFolder/jcr:content/data/master")).thenReturn(null);
        efeSitemapGenerator.addResource("sitemap", sitemap, resource);

        when(resourceResolver.getResource("/content/dam/test/fragment_plannerFolder")).thenReturn(null);
        efeSitemapGenerator.addResource("sitemap", sitemap, resource);

        when(resource.adaptTo(Page.class)).thenReturn(null);
        efeSitemapGenerator.addResource("sitemap", sitemap, resource);

    }

    @Test
    void addResourceElseTest() throws SitemapException {
        when(resource.adaptTo(Page.class)).thenReturn(page);
        when(ResourceUtil.getServiceResourceResolver(resolverFactory)).thenReturn(resourceResolver);
        when(resource.getPath()).thenReturn("/content/efe/us/en/locations1");
        when(resourceResolver.map("/content/efe/us/en/locations1.html")).thenReturn("/content/efe/us/en/locations1.html");
        when(externalizer.publishLink(resourceResolver, "/content/efe/us/en/locations1.html")).thenReturn("publish/content/efe/us/en/locations1.html");
        Url url = mock(Url.class);
        when(sitemap.addUrl("publish/content/efe/us/en/locations1.html")).thenReturn(url);
        Calendar calendar = mock(Calendar.class);
        when(page.getLastModified()).thenReturn(calendar);
        ValueMap vm = mock(ValueMap.class);
        when(page.getContentResource()).thenReturn(resource);
        when(resource.getValueMap()).thenReturn(vm);
        when(vm.get(JcrConstants.JCR_CREATED, Calendar.class)).thenReturn(calendar);
        efeSitemapGenerator.addResource("sitemap", sitemap, resource);
        assertNotNull(resource.adaptTo(Page.class));
    }

    @Test
    void shouldIncludeTest() {
        Page page = mock(Page.class);
        when(resource.adaptTo(Page.class)).thenReturn(page);
        when(resource.getChild(JcrConstants.JCR_CONTENT)).thenReturn(resource);
        when(defaultGenerator.shouldInclude(resource)).thenReturn(true);
        ValueMap prop = mock(ValueMap.class);
        lenient().when(page.getProperties()).thenReturn(prop);
        lenient().when(prop.get(NameConstants.PN_REDIRECT_TARGET, String.class)).thenReturn("/content");
        efeSitemapGenerator.shouldInclude(resource);
        lenient().when(prop.get(NameConstants.PN_REDIRECT_TARGET, String.class)).thenReturn(null);
        efeSitemapGenerator.shouldInclude(resource);
        assertNotNull(page);
    }

    @Test
    void shouldFollowTest() {
        ValueMap props = mock(ValueMap.class);
        when(resource.getValueMap()).thenReturn(props);
        //efeSitemapGenerator.shouldFollow(resource);
        lenient().when(resource.getChild(JcrConstants.JCR_CONTENT)).thenReturn(resource);
        lenient().when(props.get(SitemapService.PROPERTY_SITEMAP_ROOT, Boolean.class)).thenReturn(false);
        lenient().when(defaultGenerator.shouldFollow(resource)).thenReturn(true);
        efeSitemapGenerator.shouldFollow(resource);

        when(resource.getName()).thenReturn("resource");
        lenient().when(props.get(SitemapService.PROPERTY_SITEMAP_ROOT, Boolean.class)).thenReturn(true);
        lenient().when(defaultGenerator.shouldFollow(resource)).thenReturn(false);
        efeSitemapGenerator.shouldFollow(resource);

        lenient().when(props.get(SitemapService.PROPERTY_SITEMAP_ROOT, Boolean.class)).thenReturn(false);
        efeSitemapGenerator.shouldFollow(resource);

    }

}