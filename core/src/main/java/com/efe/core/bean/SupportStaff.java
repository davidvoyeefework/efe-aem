package com.efe.core.bean;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collections;
import java.util.Objects;

/**
 * The Support Staff class.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupportStaff {

	/* first name */
	private String firstName;

	/* last name */
	private String lastName;

	/* photo */
	private String photo;

    private int supportStaffId;

    private List<Integer> planners;

    /**
     * Returns the First Name.
     *
     * @return the First Name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns the Last Name.
     *
     * @return the Last Name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Returns the Photo.
     *
     * @return the Photo
     */
    public String getPhoto() {
        return this.photo;
    }

    public int getSupportStaffId() {
        return this.supportStaffId;
    }

    public List<Integer> getPlanners() {
        if (Objects.nonNull(planners)) {
			return new ArrayList<>(planners);
		}
		return Collections.emptyList();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setSupportStaffId(int supportStaffId) {
        this.supportStaffId = supportStaffId;
    }

    public void setPlanners(List<Integer> planners) {
        
        this.planners = new ArrayList<>(planners);
    }
}
