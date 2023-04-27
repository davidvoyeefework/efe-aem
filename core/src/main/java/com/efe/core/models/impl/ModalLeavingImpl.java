package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.ModalLeaving;
import com.efe.core.utils.EFEUtil;
import com.google.gson.JsonObject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.Objects;

/**
 * The Class ModalLeaving Model Impl.
 */
@Model(adaptables = Resource.class, adapters = ModalLeaving.class, resourceType = {
        ModalLeavingImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ModalLeavingImpl implements ModalLeaving {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/components/modalleaving";

    private static final  String GENERIC_LIST_RESOURCE ="/etc/acs-commons/lists/efe/modal-exclusion/jcr:content/list";

    /**
     * The current resource.
     */
    @Self
    private Resource resource;

    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

    /** The logo. */
    @ValueMapValue
    private String logo;

    /** The logo alt text. */
    @ValueMapValue
    private String logoAltText;

    /** The modal title. */
    @ValueMapValue
    private String modalTitle;

    /** The modal description text. */
    @ValueMapValue
    private String modalDescriptionText;

    /** The modal leave text. */
    @ValueMapValue
    private String modalLeaveText;

    /** The modal cancel text. */
    @ValueMapValue
    private String modalCancelText;

    /** The id. */
    @ValueMapValue
    private String id;

    /** The modal list. */
    JsonObject modalList = new JsonObject();

    /**
     * Gets the logo.
     *
     * @return the logo
     */
    @Override
    public String getLogo() {
        return logo;
    }

    /**
     * Gets the logo alt text.
     *
     * @return the logo alt text
     */
    @Override
    public String getLogoAltText() {
        return logoAltText;
    }

    /**
     * Gets the modal title.
     *
     * @return the modal title
     */
    @Override
    public String getModalTitle() {
        return modalTitle;
    }

    /**
     * Gets the modal description text.
     *
     * @return the modal description text
     */
    @Override
    public String getModalDescriptionText() {
        return modalDescriptionText;
    }

    /**
     * Gets the modal leave text.
     *
     * @return the modal leave text
     */
    @Override
    public String getModalLeaveText() {
        return modalLeaveText;
    }

    /**
     * Gets the modal cancel text.
     *
     * @return the modal cancel text
     */
    @Override
    public String getModalCancelText() {
        return modalCancelText;
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

    /**
     * Gets the modal list.
     *
     * @return the modal list
     */
    @Override
    public JsonObject getModalList() {
        Resource resource = resourceResolver.getResource(GENERIC_LIST_RESOURCE);
        if (Objects.nonNull(resource)) {
            Iterable<Resource> children = resource.getChildren();
            for (Resource childResource : children) {
                String title = childResource.getValueMap().get("jcr:title", String.class);
                String nodevalue = childResource.getValueMap().get("value", String.class);
                modalList.addProperty(title,nodevalue);
            }
        }
        return modalList;
    }
}
