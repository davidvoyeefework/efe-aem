package com.efe.core.models.impl;

import com.efe.core.models.ImageCollage;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AemContextExtension.class)
public class ImageCollageImplTest {

    private static final String RESOURCE_CONTENT = "/com/efe/core/models/footer/imageCollage.json";

    private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

    private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/imageCollage";

    private ImageCollage imageCollageModel;

    private Resource resource;

    private AemContext aemContext = new AemContext();

    @BeforeEach
    public void setup() {
        Class<ImageCollage> modelClass = ImageCollage.class;
        aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
        aemContext.addModelsForClasses(modelClass);
        resource = aemContext.currentResource(RESOURCE);
        imageCollageModel = resource.adaptTo(modelClass);
    }

    @Test
    public void modelGetterTest() {
        assertEquals("cmp_test", imageCollageModel.getId());
        assertEquals("/content/dam/test1.png", imageCollageModel.getPrimaryImage());
        assertEquals("/content/dam/test2.png", imageCollageModel.getFirstSecondaryImage());
        assertEquals("/content/dam/test3.png", imageCollageModel.getSecondSecondaryImage());
        assertEquals("alt text", imageCollageModel.getPrimaryImageAltText());
        assertEquals("alt text", imageCollageModel.getFirstSecondaryImageAltText());
        assertEquals("alt text", imageCollageModel.getSecondSecondaryImageAltText());
    }
}
