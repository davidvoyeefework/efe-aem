package com.efe.core.models.impl;

import com.adobe.acs.commons.genericlists.GenericList;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.efe.core.models.FeHeader;
import com.efe.core.services.impl.DynamicMediaServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The Class FeHeaderImplTest.
 */
@ExtendWith(AemContextExtension.class)
 class FeHeaderImplTest {

    /** The Constant RESOURCE_CONTENT. */
    private static final String RESOURCE_CONTENT = "/com/efe/core/models/feHeader.json";

    /** The Constant TEST_CONTENT_ROOT. */
    private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

    /** The Constant RESOURCE. */
    private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/feHeader";

    /** The model. */
    private FeHeader feHeaderModel;

    /** The resource. */
    private Resource resource;

    /** The aem context. */
    private AemContext aemContext = new AemContext();

    /** The DynamicMediaServiceImpl. */
    private DynamicMediaServiceImpl dynamicMediaService;

    /** The resolverFactory. */
    @Mock
    ResourceResolverFactory resolverFactory;

    /** The feHeader. */
    @InjectMocks
    private FeHeaderImpl feHeader;

    /**
     * Sets the up.
     */
    @BeforeEach
    public void setup() {
        Class<FeHeader> modelClass = FeHeader.class;
        aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
        dynamicMediaService = new DynamicMediaServiceImpl();
        aemContext.registerInjectActivateService(dynamicMediaService);
        aemContext.addModelsForClasses(modelClass);
        resource = aemContext.currentResource(RESOURCE);
        feHeaderModel = resource.adaptTo(modelClass);
        resolverFactory = mock(ResourceResolverFactory.class);
    }

    /**
     * Simple load and getter test.
     */
    @Test
    void modelGetterTest() {
        assertEquals("/content/dam/test1.svg", feHeaderModel.getPrimaryLogo());
        assertEquals("/content/dam/test2.svg", feHeaderModel.getSecondaryLogo());
        assertEquals("alt text1", feHeaderModel.getPrimaryLogoAltText());
        assertEquals("alt text2", feHeaderModel.getSecondaryLogoAltText());
        assertEquals("test quote", feHeaderModel.getSponsorDetails());
        assertEquals("feHeader-71334e5ba6", feHeaderModel.getId());
        assertEquals("subadvised", feHeaderModel.getType());
    }
}
