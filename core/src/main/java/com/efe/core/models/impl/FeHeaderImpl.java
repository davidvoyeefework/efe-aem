package com.efe.core.models.impl;

import com.adobe.acs.commons.genericlists.GenericList;
import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.FeHeader;
import com.efe.core.services.DynamicMediaService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;
import com.efe.core.utils.ResourceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

/**
 * The Class FeHeaderImpl.
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = FeHeader.class, resourceType = {
        FeHeaderImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class FeHeaderImpl implements FeHeader {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/fe-components/structure/fe-header";

    /** The request. */
    @SlingObject
    private SlingHttpServletRequest request;

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


    /** The resolver factory. */
    @OSGiService
    private ResourceResolverFactory resolverFactory;

    /** The primary logo. */
    @ValueMapValue
    private String primaryLogo;

    /** The secondary logo. */
    @ValueMapValue
    private String secondaryLogo;

    /** The primary logo link. */
    @ValueMapValue
    private String primaryLogoAltText;

    /** The secondary logo link. */
    @ValueMapValue
    private String secondaryLogoAltText;

    /** The id. */
    @ValueMapValue
    private String id;

    /** The sponsor details. */
    @ValueMapValue
    private String sponsorDetails;

    /** The type. */
    @ValueMapValue
    private String type;

    /** The dynamic variables. */
    private String dynamicVariables;

    /**
     * Inits the model.
     */
    @PostConstruct
    public void init() {
        if (null != request) {
            setDynamicVariablesField();
        }
    }

    /**
     * Sets the Dynamic Variables field.
     */
    private void setDynamicVariablesField() {
        try (ResourceResolver serviceResolver = ResourceUtil.getServiceResourceResolver(resolverFactory)) {
            GenericList list = EFEUtil.getGenericList(serviceResolver, "/etc/acs-commons/lists/fe/sponsor-details");

            if(null == list) {
                return;
            }

            JsonArray array = new JsonArray();
            for (GenericList.Item item : list.getItems()) {
                JsonObject dynamicVariable = new JsonObject();
                String[] values = item.getValue().split("\\|");
                if (values.length == 2) {
                    dynamicVariable.addProperty(values[0], values[1]);
                }
                array.add(dynamicVariable);
            }
            dynamicVariables = new Gson().toJson(array);
        }
    }

    /**
     * Gets the primary logo.
     *
     * @return the primary logo
     */
    @Override
    public String getPrimaryLogo() {
        return dynamicMediaService.getDmImagePath(resourceResolver, primaryLogo);
    }

    /**
     * Gets the secondary logo.
     *
     * @return the secondary logo
     */
    @Override
    public String getSecondaryLogo() {
        return dynamicMediaService.getDmImagePath(resourceResolver, secondaryLogo);
    }

    /**
     * Gets the primary logo alt text.
     *
     * @return the primary logo alt text
     */
    @Override
    public String getPrimaryLogoAltText() {
        return primaryLogoAltText;
    }

    /**
     * Gets the secondary logo alt text.
     *
     * @return the secondary logo alt text
     */
    @Override
    public String getSecondaryLogoAltText() {
        return secondaryLogoAltText;
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
     * Gets the Sponsor Details.
     *
     * @return the Sponsor Details
     */
    @Override
    public String getSponsorDetails() {
        return sponsorDetails;
    }

    /**
     * Gets the dynamic variables.
     *
     * @return the dynamicVariables
     */
    @Override
    public String getDynamicVariables() {
        return dynamicVariables;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public String getType() {
        return type;
    }
}
