package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.ContentCollage;
import com.efe.core.utils.EFEUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * The Class ContentCollageImpl.
 */
@Model(adaptables = Resource.class, adapters = ContentCollage.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ContentCollageImpl implements ContentCollage {

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

    /** The secondary image. */
    @ValueMapValue
    private String secondaryImage;

    /** The secondary image alt text. */
    @ValueMapValue
    private String secondaryImageAltText;

    /** The contentCard. */
    @ValueMapValue
    private String contentCard;

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
        return primaryImage;
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
     * Gets the secondary image.
     *
     * @return the secondary image
     */
    public String getSecondaryImage() {
        return secondaryImage;
    }

    /**
     * Gets the secondary image alt text.
     *
     * @return the secondary image alt text
     */
    public String getSecondaryImageAltText() {
        return secondaryImageAltText;
    }

    /**
     * Gets the content card.
     *
     * @return the content card
     */
    public String getContentCard() {
        return contentCard;
    }
}
