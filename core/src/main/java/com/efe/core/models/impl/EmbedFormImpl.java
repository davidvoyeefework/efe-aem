package com.efe.core.models.impl;

import com.day.cq.commons.JS;
import com.efe.core.models.EmbedForm;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * The Class EmbedFormImpl.
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = EmbedForm.class, resourceType = {
        EmbedFormImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EmbedFormImpl implements EmbedForm {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/fe-components/content/feform";

    /** The type. */
    @ValueMapValue
    String type;

    /** The initiationPointCode. */
    @ValueMapValue
    String initiationPointCode;

    /** The appointmentTypeId. */
    @ValueMapValue
    String appointmentTypeId;

    /** The description. */
    @ValueMapValue
    String description;

    /** The buttonText. */
    @ValueMapValue
    String buttonText;

    /** The scheduleAppointment. */
    @ValueMapValue
    Boolean scheduleAppointment;

    /** The dataOption. */
    private String dataOption;

    /**
     * Inits the model.
     */
    @PostConstruct
    protected void init() {
        if(Objects.isNull(appointmentTypeId)) {
            return ;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("initiationPoint", initiationPointCode);
        jsonObject.addProperty("desc", description);
        jsonObject.addProperty("buttonText", buttonText);
        jsonObject.addProperty("appointmentTypeId", appointmentTypeId);
        jsonObject.addProperty("hasAppointmentScheduler", scheduleAppointment);
        dataOption = jsonObject.toString();
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

    /**
     * Gets the dataOption.
     *
     * @return the dataOption
     */
    @Override
    public String getDataOptions() {
        String dataOptionJson = StringUtils.EMPTY;
        if(Objects.nonNull(dataOption)) {
            dataOptionJson = dataOption;
        }
        return dataOptionJson;
    }

}
