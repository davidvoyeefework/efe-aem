package com.efe.core.models.impl;

import com.efe.core.models.EmbedForm;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

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
     * Gets the initiation point code.
     *
     * @return the initiation point code
     */
    @Override
    public String getInitiationPointCode() {
        return initiationPointCode;
    }

    /**
     * Gets the appointment type id.
     *
     * @return the appointment type id
     */
    @Override
    public String getAppointmentTypeId() {
        return appointmentTypeId;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Gets the button text.
     *
     * @return the button text
     */
    @Override
    public String getButtonText() {
        return buttonText;
    }

    /**
     * Gets the schedule appointment.
     *
     * @return the schedule appointment
     */
    @Override
    public Boolean getScheduleAppointment() {
        return scheduleAppointment;
    }
}
