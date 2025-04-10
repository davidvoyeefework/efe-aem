package com.efe.core.models;

import com.adobe.xfa.ArrayNodeList;
import java.util.ArrayList;
import com.efe.core.bean.PlannerDetail;

import java.util.List;

/**
 * The Interface PlannerList Model.
 */

public interface PlannerList {


    /**
     * Gets the id.
     *
     * @return the id
     */
    String getId();


    /**
     * Gets the planner target.
     *
     * @return the planner target
     */
    String getPlannerTarget();

    /**
     * Gets the CTA Label.
     *
     * @return the CTA Label
     */
    String getCtaLabel();

    /**
     * Gets the Planner title.
     *
     * @return the Planner title
     */
    String getPlannerTitle();

    /**
     * Gets the  State.
     *
     * @return the State
     */
    String getState();

    /**
     * Gets the  City.
     *
     * @return the City
     */
    String getCity();
    /**
     * Gets the planner list.
     *
     * @return the planner list
     */
    List<PlannerDetail> getPlannerList();

    String getDefaultRedirectPagePath();

}
