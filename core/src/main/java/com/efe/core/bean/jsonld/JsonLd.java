
package com.efe.core.bean.jsonld;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonLd {

    @SerializedName("@context")
    @Expose
    private String context = "https://schema.org";
    
    @SerializedName("@type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("telePhone")
    @Expose
    private String telePhone;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("geo")
    @Expose
    private Geo geo;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

}
