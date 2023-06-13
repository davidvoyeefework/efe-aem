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
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        Page page = mock(Page.class);
        aemContext.currentPage(page);
        Tag tag1 = mock(Tag.class);
        Tag tag2 = mock(Tag.class);
        Tag[] tagsArr = {tag1,tag2};
        when(page.getTags()).thenReturn(tagsArr);
        resolver = aemContext.resourceResolver();
        tags = aemContext.request().adaptTo(Tags.class);
        assertNotNull(tags.getTagList());
        tags.isEmpty();
    }

    @Test
    void testNoTagsList() {
        aemContext.currentResource("/content/efe/tags");
        resolver = aemContext.resourceResolver();
        tags = aemContext.request().adaptTo(Tags.class);
        assertEquals(0, tags.getTagList().size());
    }

    @Test
    void getIdtest() {
        aemContext.currentResource("/content/efe/tags");
        resolver = aemContext.resourceResolver();
        tags = aemContext.request().adaptTo(Tags.class);
        assertNotNull(tags.getId());
    }
}
