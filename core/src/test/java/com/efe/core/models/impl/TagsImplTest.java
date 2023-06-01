package com.efe.core.models.impl;

import com.efe.core.models.Tags;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class TagsImplTest {

    private final AemContext aemContext = new AemContext();
    @Mock
    ResourceResolver resolver;
    Tags tags;

    @BeforeEach
    void init() {
        aemContext.load().json("/com/efe/core/models/tags/tags.json", "/content/efe");
    }

    @Test
    void testTagsList() {
        aemContext.currentResource("/content/efe/tags");
        resolver = aemContext.resourceResolver();
        tags = aemContext.request().adaptTo(Tags.class);
        assertEquals("5657775354464", tags.getId());
    }
}
