
package com.efe.core.bean.jsonld;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class KnowsAbout.
 */
public class KnowsAbout {

    /** The type. */
    @SerializedName("@type")
    @Expose
    public String type = "DefinedTermSet";
    
    /** The name. */
    @Expose
    public List<String> name;
    
	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(List<String> name) {
		this.name = new ArrayList<>(name);
	}

}
