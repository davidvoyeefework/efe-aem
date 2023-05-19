package com.efe.core.models.impl;

import com.efe.core.models.VerticalSeparator;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The Class VerticalSeparatorImplTest.
 */
@ExtendWith(AemContextExtension.class)
class VerticalSeparatorImplTest {

    /** The Constant RESOURCE_CONTENT. */
    private static final String RESOURCE_CONTENT = "/com/efe/core/models/verticalSeparator.json";

    /** The Constant RESOURCE_CONTENT. */
    private static final String RESOURCE_CONTENT_TWO = "/com/efe/core/models/verticalSeparatorTwo.json";

    /** The Constant TEST_CONTENT_ROOT. */
    private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

    /** The Constant RESOURCE. */
    private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/verticalSeparator";

    /** The model. */
    private VerticalSeparator verticalSeparator;

    /** The resource. */
    private Resource resource;

    /** The aem context. */
    private AemContext context = new AemContext();

    /**
     * Simple load and getter test.
     */
    @Test
    void testGetterOne() {
        context.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
        resource = context.currentResource(RESOURCE);
        Class<VerticalSeparator> modelClass = VerticalSeparator.class;
        context.addModelsForClasses(modelClass);
        verticalSeparator = resource.adaptTo(modelClass);

        assertEquals("verticalSeparator-553e037663", verticalSeparator.getId());
        assertEquals(3, verticalSeparator.getNoOfColumns());
        assertEquals("false", verticalSeparator.getHiddenSeparator());
        assertEquals("column_0", verticalSeparator.getColumnIndices()[0]);
        assertEquals("column_1", verticalSeparator.getColumnIndices()[1]);
        assertEquals("column_2", verticalSeparator.getColumnIndices()[2]);
        assertEquals("cmp-vertical-separator--one cmp-horizontal-separator", verticalSeparator.getUlClass());
        assertEquals("cmp-vertical-separator--one__item", verticalSeparator.getLiClass());
    }

    /**
     * Simple load and getter test.
     */
    @Test
    void testGetterTwo() {
        context.load().json(RESOURCE_CONTENT_TWO, TEST_CONTENT_ROOT);
        resource = context.currentResource(RESOURCE);
        Class<VerticalSeparator> modelClass = VerticalSeparator.class;
        context.addModelsForClasses(modelClass);
        verticalSeparator = resource.adaptTo(modelClass);

        assertEquals("verticalSeparator-553e037663", verticalSeparator.getId());
        assertEquals(2, verticalSeparator.getNoOfColumns());
        assertEquals("false", verticalSeparator.getHiddenSeparator());
        assertEquals("column_0", verticalSeparator.getColumnIndices()[0]);
        assertEquals("column_1", verticalSeparator.getColumnIndices()[1]);
        assertEquals("cmp-vertical-separator--two cmp-horizontal-separator", verticalSeparator.getUlClass());
        assertEquals("cmp-vertical-separator--two__item", verticalSeparator.getLiClass());
    }
}
