package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.FeFooter;
import com.efe.core.services.DynamicMediaService;
import com.efe.core.utils.EFEUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * The Class FeFooterImpl.
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = FeFooter.class, resourceType = {
        FeFooterImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class FeFooterImpl implements FeFooter {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/fe-components/structure/fe-footer";

    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * The current resource.
     */
    @SlingObject
    private Resource resource;

    /**
     * Injecting dynamicMediaService
     *
     */
    @OSGiService
    private DynamicMediaService dynamicMediaService;


    /** The file Rrference. */
    @ValueMapValue
    private String fileReference;

    /** The alt text. */
    @ValueMapValue
    private String altText;

    /** The id. */
    @ValueMapValue
    private String id;

    /**
     * Gets the file reference.
     *
     * @return the file reference
     */
    @Override
    public String getFileReference() {
        return dynamicMediaService.getDmImagePath(resourceResolver, fileReference);
    }


    /**
     * Gets the alt text.
     *
     * @return the alt text
     */
    @Override
    public String getAltText() {
        return altText;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */

    @Override
    public String getId() {
        if (id == null) {
            id = EFEUtil.getId(resource);
        }
        return id;
    }

}
