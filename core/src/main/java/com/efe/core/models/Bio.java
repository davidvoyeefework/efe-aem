package com.efe.core.models;

public interface Bio {

    //  Method to return Bio name from Content Fragment
    public String getName();

    //  Method to return Bio title from Content Fragment
    public String getTitle();    

    //  Method to return Bio certifications from Content Fragment
    public String [] getCerts();    

    //  Method to return Education details from Content Fragment
    public String [] getEducation();     
    
    //  Method to return Bio body copy from Content Fragment
    public String getBodyCopy();       

    //  Method to return Thumbnail from Content Fragment
    public String getThumbnail();      

    //  Method to return boolean string if author wants to include SEO Schema metadata
    public String getSeoSchema();
    
    
}
