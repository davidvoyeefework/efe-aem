package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.ModalPromotion;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


/**
 * The Class ModalPromotionImpl.
 */
@Model(adaptables = Resource.class, adapters = ModalPromotion.class, resourceType = {
        ModalPromotionImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ModalPromotionImpl implements ModalPromotion {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/components/modalpromotion";

    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * The current resource.
     */
    @Self
    private Resource resource;

    /** The pretitle. */
    @ValueMapValue
    private String pretitle;

    /** The title. */
    @ValueMapValue
    private String title;

    /** The description. */
    @ValueMapValue
    private String description;

    /** The button text. */
    @ValueMapValue
    private String buttonText;

    /** The button url. */
    @ValueMapValue
    private String buttonUrl;

    /** The promotion image. */
    @ValueMapValue
    private String promotionImage;

    /** The promotion image alt text. */
    @ValueMapValue
    private String promotionImageAltText;

    /** The close button color. */
    @ValueMapValue
    private String closeButtonColor;

    /** The close button alt text. */
    @ValueMapValue
    private String closeButtonAltText;

    /** The start date. */
    @ValueMapValue
    private String startDate;

    /** The end date. */
    @ValueMapValue
    private String endDate;

    /** The id. */
    @ValueMapValue
    private String id;

    /**
     * Gets the Pre-title.
     *
     * @return the Pre-title
     */
    @Override
    public String getPretitle() {
        return pretitle;
    }

    /**
     * Gets the Title.
     *
     * @return the Title
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Gets the Description.
     *
     * @return the Description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Gets the Button Text.
     *
     * @return the Button Text
     */
    @Override
    public String getButtonText() {
        return buttonText;
    }

    /**
     * Gets the Button Url.
     *
     * @return the Button Url
     */
    @Override
    public String getButtonUrl() {
        return LinkUtil.getFormattedLink(buttonUrl, resourceResolver);
    }

    /**
     * Gets the Promotion Image.
     *
     * @return the Promotion Image
     */
    @Override
    public String getPromotionImage() {
        return promotionImage;
    }

    /**
     * Gets the Promotion Image Alt Text.
     *
     * @return the Promotion Image Alt Text
     */
    @Override
    public String getPromotionImageAltText() {
        return promotionImageAltText;
    }

    /**
     * Gets the Close Button Color.
     *
     * @return the Close Button Color
     */
    @Override
    public String getCloseButtonColor() {
        return closeButtonColor;
    }

    /**
     * Gets the Close Button Alt Text.
     *
     * @return the Close Button Alt Text
     */
    @Override
    public String getCloseButtonAltText() {
        return closeButtonAltText;
    }

    /**
     * Gets the Start Date.
     *
     * @return the Start Date
     */
    @Override
    public String getStartDate() {
        return startDate;
    }

    /**
     * Gets the End Date.
     *
     * @return the End Date
     */
    @Override
    public String getEndDate() {
        return endDate;
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
