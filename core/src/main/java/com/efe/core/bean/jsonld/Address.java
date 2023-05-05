
package com.efe.core.bean.jsonld;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class Address.
 */
public class Address {

    /** The type. */
    @SerializedName("@type")
    @Expose
    private String type;
   
    /** The street address. */
    @Expose
    private String streetAddress;
    
    /** The address locality. */
    @Expose
    private String addressLocality;
    
    /** The address region. */
    @Expose
    private String addressRegion;
   
    /** The postal code. */
    @Expose
    private String postalCode;

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
     * Gets the street address.
     *
     * @return the street address
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets the street address.
     *
     * @param streetAddress the new street address
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * Gets the address locality.
     *
     * @return the address locality
     */
    public String getAddressLocality() {
        return addressLocality;
    }

    /**
     * Sets the address locality.
     *
     * @param addressLocality the new address locality
     */
    public void setAddressLocality(String addressLocality) {
        this.addressLocality = addressLocality;
    }

    /**
     * Gets the address region.
     *
     * @return the address region
     */
    public String getAddressRegion() {
        return addressRegion;
    }

    /**
     * Sets the address region.
     *
     * @param addressRegion the new address region
     */
    public void setAddressRegion(String addressRegion) {
        this.addressRegion = addressRegion;
    }

    /**
     * Gets the postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code.
     *
     * @param postalCode the new postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
