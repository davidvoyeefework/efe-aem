package com.efe.core.models;

import java.util.List;

import com.efe.core.bean.LocationResponse;
import com.efe.core.bean.PlannerResponse;

/**
 * The Planner Bio Model.
 */
public interface PlannerBio {
	
	/**
	 * Gets the planner response.
	 *
	 * @return the planner response
	 */
	PlannerResponse getPlannerResponse();

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();

	/**
	 * Checks if is empty.
	 *
	 * @return the empty
	 */
	boolean isEmpty();

	/**
	 * Gets the json ld.
	 *
	 * @return the json ld
	 */
	String getJsonLd();

	/**
	 * Gets the office locations.
	 *
	 * @return the officeLocations
	 */
	List<LocationResponse> getOfficeLocations();

	/**
	 * Gets the location section heading.
	 *
	 * @return the locationSectionHeading
	 */
	String getLocationSectionHeading();

	/**
	 * Gets the education heading.
	 *
	 * @return the educationHeading
	 */
	String getEducationHeading();

	/**
	 * Gets the certification heading.
	 *
	 * @return the certificationHeading
	 */
	String getCertificationHeading();

	/**
	 * Gets the about me heading.
	 *
	 * @return the aboutMeHeading
	 */
	String getAboutMeHeading();

	/**
	 * Gets the about section heading.
	 *
	 * @return the aboutSectionHeading
	 */
	String getAboutSectionHeading();

	/**
	 * Gets the appointment cta.
	 *
	 * @return the appointmentCta
	 */
	String getAppointmentCta();

	/**
	 * Gets the appointment cta label.
	 *
	 * @return the appointmentCtaLabel
	 */
	String getAppointmentCtaLabel();

	/**
	 * Gets the explore link label.
	 *
	 * @return the exploreLinkLabel
	 */
	String getExploreLinkLabel();

	/**
	 * Gets the file reference.
	 *
	 * @return the fileReference
	 */
	String getFileReference();

    String getDefaultRedirectPagePath();
}
