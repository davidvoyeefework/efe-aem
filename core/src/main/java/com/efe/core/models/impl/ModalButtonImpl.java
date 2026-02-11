package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.ModalButton;
import com.efe.core.services.DynamicMediaService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import com.efe.core.constants.Constants;
import com.efe.core.models.FormsSelector;
import com.efe.core.services.EfeService;
import com.efe.core.utils.EFEUtil;

/**
 *
 * @author jrodriquez
 */
@Model(adaptables = Resource.class, adapters = ModalButton.class, resourceType = {
        ModalButtonImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ModalButtonImpl implements ModalButton {
        /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/components/modalbutton";
    
    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * The current resource.
     */
    @Self
    private Resource resource;

    /** The button text. */
    @ValueMapValue
    private String buttonText;

    /** The button url. */
    @ValueMapValue
    private String embedFragment;

    /** The id. */
    @ValueMapValue
    private String id;

    /** The OPEN Button */
    @ValueMapValue
    private String openModalButton;

    /** The CLOSE Button */
    @ValueMapValue
    private String closeModalButton;

    /**
     * Injecting dynamicMediaService
     *
     */
    @OSGiService
    private DynamicMediaService dynamicMediaService;

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
    public String getEmbedFragment() {
        return embedFragment;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Override
    public String getId() {
        return EFEUtil.getId(resource);
    }

    @Override
    public String getOpenModalButton() {
        return openModalButton;
    }

    @Override
    public String getCloseModalButton() {
        return closeModalButton;
    }

    
    @Override
    public String getOpenModalEvent() {
        // empty/blank/whitespace → null
        return StringUtils.trimToNull(openModalButton);
    }

    @Override
    public String getCloseModalEvent() {
        return StringUtils.trimToNull(closeModalButton);
    }

}
