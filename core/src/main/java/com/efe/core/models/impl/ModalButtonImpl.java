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
import com.adobe.cq.xf.ExperienceFragmentVariation;
import org.apache.sling.api.SlingHttpServletRequest;
import com.efe.core.constants.Constants;
import com.efe.core.models.FormsSelector;
import com.efe.core.services.EfeService;
import com.efe.core.utils.EFEUtil;
import javax.annotation.PostConstruct;

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
    
    @ValueMapValue
    private Resource xfragment;

    /** The button text. */
    @ValueMapValue
    private String buttonText;

    /** The button url. */
    @ValueMapValue
    private String embedFragment;

    /** The id. */
    @ValueMapValue
    private String id;
    
    /** The experience fragment. */
    @ValueMapValue
    private ExperienceFragmentVariation expFragment;

    /**
     * Injecting dynamicMediaService
     *
     */
    @OSGiService
    private DynamicMediaService dynamicMediaService;
    
    /**
     * Inits the Model.
     */
    @PostConstruct
    public void init() {
        xfragment = resourceResolver.getResource(embedFragment);
        expFragment = xfragment.adaptTo(ExperienceFragmentVariation.class);
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
    public String getEmbedFragment() {
        return xfragment.getPath();
    }
    
        /**
     * Gets the Experience Fragment.
     *
     * @return the Experience Fragment
     */
    @Override
    public ExperienceFragmentVariation getExpFragment() {
        return expFragment;
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
}
