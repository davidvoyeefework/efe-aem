
package com.efe.core.bean.jsonld;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class Geo.
 */
public class Geo {

    /** The type. */
    @SerializedName("@type")
    @Expose
    private String type;
    
    /** The latitude. */
    @Expose
    private String latitude;
    
    /** The longitude. */
    @Expose
    private String longitude;

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the latitude.
     *
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude.
     *
     * @param latitude the new latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitude.
     *
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude.
     *
     * @param longitude the new longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
