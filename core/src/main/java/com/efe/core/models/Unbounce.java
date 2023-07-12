package com.efe.core.models;

import java.util.Map;

public interface Unbounce {

    String getPageFrameApi();

    String getAggregateApi();

    String getForKeyApi();

    String getSoftAuthApi();

    String getSignupApi();

    String getScheduleApi();

    String getAuthenticateApi();

    String getCallBackApi();

    /**
     * Gets the dynamic variables.
     *
     * @return the dynamicVariables
     */
    Map<String, String> getDynamicVariables();
}
