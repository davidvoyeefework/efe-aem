package com.efe.core.models;

public interface WpiAlert {

    //  Method to return Headline content fragment value
    public String getHeadline();

    //  Method to return Subheadline content fragment value
    public String getSubheadline();

    //  Method to return record keeper theme class
    public String getRecordKeeper();

    //  Method to return Viewcode
    public String getViewcode();   

    //  Method to return CTA text
    public String getCta(); 

    //  Method to return Icon img path
    public String getIcon();     
}
