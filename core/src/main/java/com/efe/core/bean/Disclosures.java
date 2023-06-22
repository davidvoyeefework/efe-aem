package com.efe.core.bean;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * The Disclosures class.
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Disclosures {

    /* disclosureText */
    private String disclosureText;

    /**
     * Returns the Disclosure Text.
     *
     * @return the Disclosure Text
     */
    public String getDisclosureText() {
        return disclosureText;
    }

    /**
     *
     * sets the Disclosure Text
     */
    public void setDisclosureText(String disclosureText) {
        this.disclosureText = disclosureText;
    }

}