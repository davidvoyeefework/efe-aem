package com.efe.core.models;

/**
 * The Interface EmbedForm.
 */
public interface EmbedForm {

    /**
     * Gets the type.
     *
     * @return the type
     */
    String getType();

    /**
     * Gets the initiation point code.
     *
     * @return the initiation point code
     */
    String getInitiationPointCode();

    /**
     * Gets the appointment type id.
     *
     * @return the appointment type id
     */
    String getAppointmentTypeId();

    /**
     * Gets the description.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Gets the button text.
     *
     * @return the button text
     */
    String getButtonText();

    /**
     * Gets the schedule appointment.
     *
     * @return the schedule appointment
     */
    Boolean getScheduleAppointment();
}
