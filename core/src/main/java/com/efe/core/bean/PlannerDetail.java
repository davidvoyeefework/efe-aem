package com.efe.core.bean;


/**
 * The PlannerDetail bean class.
 */
public class PlannerDetail {

    /** The firstName. */
    String firstName;

    /** The lastName. */
    String lastName;

    /** The desktopUrl. */
    String desktopUrl;

    /** The title. */
    String title;

    /** The buttonUrl. */
    String buttonUrl;
    
    /** The bioVideo URL */
    String bioVideo;

    String officeLocations;

    String bioVideo;

    /**
     * Gets the firstName.
     *
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the firstName.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the lastName.
     *
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the lastName.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the desktopUrl.
     *
     * @return the desktopUrl
     */
    public String getDesktopUrl() {
        return desktopUrl;
    }

    /**
     * Sets the desktopUrl.
     */
    public void setDesktopUrl(String desktopUrl) {
        this.desktopUrl = desktopUrl;
    }
    
        /**
     * Gets the bio video.
     *
     * @return the bio video
     */
    public String getBioVideo() {
        return bioVideo;
    }

    /**
     * Sets the bio video.
     */
    public void setBioVideo(String bioVideo) {
        this.bioVideo = bioVideo;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the buttonUrl.
     *
     * @return the buttonUrl
     */
    public String getButtonUrl() {
        return buttonUrl;
    }

    /**
     * Sets the buttonUrl.
     */
    public void setButtonUrl(String buttonUrl) {
        this.buttonUrl = buttonUrl;
    }

    // Getter
    public String getOfficeLocations () {
        return officeLocations;
    }    

    // Setter
    public void setOfficeLocations(String officeLocations) {
        this.officeLocations = officeLocations;
    }

}