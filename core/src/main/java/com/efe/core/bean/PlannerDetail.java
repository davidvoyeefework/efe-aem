package com.efe.core.bean;

public class PlannerDetail {

    String firstName;
    String lastName;
    String desktopurl;
    String title;
    String buttonurl;


  /*public PlannerDetail(String firstName, String lastName, String desktopurl, String title) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.desktopurl = desktopurl;
        this.title = title;
    }*/
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesktopurl() {
        return desktopurl;
    }

    public void setDesktopurl(String desktopurl) {
        this.desktopurl = desktopurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getButtonurl() {
        return buttonurl;
    }

    public void setButtonurl(String buttonurl) {
        this.buttonurl = buttonurl;
    }

}