package com.efe.core.models.impl;

import com.efe.core.models.EmbedForm;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The Class EmbedFormImplTest.
 */
@ExtendWith(AemContextExtension.class)
 class EmbedFormImplTest {

    /** The Constant RESOURCE_CONTENT. */
    private static final String RESOURCE_CONTENT = "/com/efe/core/models/embedForm.json";

    /** The Constant TEST_CONTENT_ROOT. */
    private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

    /** The Constant RESOURCE. */
    private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/feForm";

    /** The Constant FORM_RESOURCE. */
    private static final String FORM_RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/feForm2";

    /** The model. */
    private EmbedForm embedForm;

    /** The resource. */
    private Resource resource;

    /** The aem context. */
    private AemContext aemContext = new AemContext();

    /**
     * Simple load and getter test.
     */
    @Test
    void modelGetterTest() {
        Class<EmbedForm> modelClass = EmbedForm.class;
        aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
        aemContext.addModelsForClasses(modelClass);
        resource = aemContext.currentResource(RESOURCE);
        embedForm = resource.adaptTo(modelClass);
        assertEquals("Enroll Form", embedForm.getType());
        assertEquals("{\"desc\":\"description\",\"buttonText\":\"Button Text\",\"initiationPoint\":\"Appointment Type Id\",\"appointmentTypeId\":\"Appointment Type Id\",\"hasAppointmentScheduler\":true}", embedForm.getDataOptions());
    }

    @Test
    void modelGetterTestNull() {
        Class<EmbedForm> modelClass = EmbedForm.class;
        aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
        aemContext.addModelsForClasses(modelClass);
        resource = aemContext.currentResource(FORM_RESOURCE);
        embedForm = resource.adaptTo(modelClass);
        assertNull(embedForm.getType());
        assertEquals(StringUtils.EMPTY, embedForm.getDataOptions());
    }

}
