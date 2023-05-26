package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.ImageCollage;
import com.efe.core.services.DynamicMediaService;
import com.efe.core.utils.EFEUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * The Class ImageCollageImpl.
 */
@Model(adaptables = Resource.class, adapters = ImageCollage.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ImageCollageImpl implements ImageCollage {

    /**
     * The current resource.
     */
    @Self
    private Resource resource;

    /** The id. */
    @ValueMapValue
    private String id;

    /** The primary image. */
    @ValueMapValue
    private String primaryImage;

    /** The primary image alt text. */
    @ValueMapValue
    private String primaryImageAltText;

    /** The first secondary image. */
    @ValueMapValue
    private String firstSecondaryImage;

    /** The first secondary image alt text. */
    @ValueMapValue
    private String firstSecondaryImageAltText;

    /** The second secondary image. */
    @ValueMapValue
    private String secondSecondaryImage;

    /** The second secondary image alt text. */
    @ValueMapValue
    private String secondSecondaryImageAltText;

    /**
     * Injecting dynamicMediaService
     *
     */
    @OSGiService
    private DynamicMediaService dynamicMediaService;

    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        if (id == null) {
            id = EFEUtil.getId(resource);
        }
        return id;
    }

    /**
     * Gets the primary image.
     *
     * @return the primary image
     */
    public String getPrimaryImage() {
        return dynamicMediaService.getDmImagePath(resourceResolver, primaryImage);
    }

    /**
     * Gets the primary image alt text.
     *
     * @return the primary image alt text
     */
    public String getPrimaryImageAltText() {
        return primaryImageAltText;
    }

    /**
     * Gets the first secondary image.
     *
     * @return the first secondary image
     */
    public String getFirstSecondaryImage() {
        return dynamicMediaService.getDmImagePath(resourceResolver, firstSecondaryImage);
    }

    /**
     * Gets the first secondary image alt text.
     *
     * @return the first secondary image alt text
     */
    public String getFirstSecondaryImageAltText() {
        return firstSecondaryImageAltText;
    }

    /**
     * Gets the second secondary image.
     *
     * @return the second secondary image
     */
    public String getSecondSecondaryImage() {
        return dynamicMediaService.getDmImagePath(resourceResolver, secondSecondaryImage);
    }

    /**
     * Gets the second secondary image alt text.
     *
     * @return the second secondary image alt text
     */
    public String getSecondSecondaryImageAltText() {
        return secondSecondaryImageAltText;
    }
}
