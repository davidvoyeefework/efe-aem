package com.efe.core.models.impl;

import com.adobe.acs.commons.genericlists.GenericList;
import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.Unbounce;
import com.efe.core.services.UnbounceService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.ResourceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The Class UnbounceImpl.
 */
@Model(adaptables = SlingHttpServletRequest.class , adapters = Unbounce.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class UnbounceImpl implements Unbounce {

    /** The constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UnbounceImpl.class);

    /** The unbounce service. */
    @OSGiService
    private UnbounceService unbounceService;

    /** The resolver factory. */
    @OSGiService
    private ResourceResolverFactory resolverFactory;

    /** The request. */
    @Self
    private SlingHttpServletRequest request;

    /** The theme. */
    private String theme;

    /** The dynamic variables. */
    private Map<String, String> dynamicVariables;

    /**
     * Inits the model.
     */
    @PostConstruct
    protected void init() {
        setUnbounceField();
        setTheme();
    }

    /**
     * Sets the theme.
     */
    private void setTheme() {
        Cookie cookie = request.getCookie("daVars");
        if(Objects.nonNull(cookie)) {
            String value = cookie.getValue();
            LOGGER.info("Cookie value :{}", value);
            try {
                String decodedValue =  URLDecoder.decode(value, "utf8");
                JsonObject jsonObject =  new Gson().fromJson(decodedValue, JsonObject.class);

                String recordKeeper = StringUtils.EMPTY;
                if(jsonObject.has("providerId")) {
                    recordKeeper = jsonObject.get("providerId").getAsString();
                    LOGGER.info("Record keeper  :{}", recordKeeper);
                }
                if("hewitt".equals(recordKeeper)) {
                    theme = "theme-primary";
                }else if("FMR".equals(recordKeeper)) {
                    theme = "theme-secondary";
                }
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("Error while decoding ", e);
            }


        }else {
            LOGGER.info("Cookie value is null");
        }
    }

    /**
     * Sets the unbounce field.
     */
    private void setUnbounceField() {
        dynamicVariables = new HashMap<>();
        try (ResourceResolver serviceResolver = ResourceUtil.getServiceResourceResolver(resolverFactory)) {
            String[] genericLists = unbounceService.getDynamicVariableList();
            for (String genericList : genericLists) {
                if(StringUtils.isNotEmpty(genericList)) {
                    String[] genericListArr = genericList.split("\\|");
                    if(genericListArr.length == 3) {
                        String dataVariableName = genericListArr[1];
                        String genericListPath = genericListArr[0];
                        GenericList list = EFEUtil.getGenericList(serviceResolver, genericListPath);

                        if(null == list) {
                            return;
                        }
                        JsonArray array = new JsonArray();
                        for (GenericList.Item item : list.getItems()) {
                            JsonObject dynamicVariable = new JsonObject();
                            String[] values = item.getValue().split("\\|");
                            if (values.length == 2) {
                                dynamicVariable.addProperty(values[0], values[1]);
                            }
                            array.add(dynamicVariable);
                        }
                        String dataVariableValue = new Gson().toJson(array);
                        dynamicVariables.put(dataVariableName, dataVariableValue);
                    }
                }
            }
        }
    }

    /**
     * Gets the PageFrameApi.
     *
     * @return the PageFrameApi
     */
    @Override
    public String getPageFrameApi() {
        return unbounceService.getPageFrameApi();
    }

    /**
     * Gets the AggregateApi.
     *
     * @return the AggregateApi
     */
    @Override
    public String getAggregateApi() {
        return unbounceService.getAggregateApi();
    }

    /**
     * Gets the ForKeyApi.
     *
     * @return the ForKeyApi
     */
    @Override
    public String getForKeyApi() {
        return unbounceService.getForKeyApi();
    }

    /**
     * Gets the SoftAuthApi.
     *
     * @return the SoftAuthApi
     */
    @Override
    public String getSoftAuthApi() {
        return unbounceService.getSoftAuthApi();
    }

    /**
     * Gets the SignupApi.
     *
     * @return the SignupApi
     */
    @Override
    public String getSignupApi() {
        return unbounceService.getSignupApi();
    }

    /**
     * Gets the ScheduleApi.
     *
     * @return the ScheduleApi
     */
    @Override
    public String getScheduleApi() {
        return unbounceService.getScheduleApi();
    }

    /**
     * Gets the AuthenticateApi.
     *
     * @return the AuthenticateApi
     */
    @Override
    public String getAuthenticateApi() {
        return unbounceService.getAuthenticateApi();
    }

    /**
     * Gets the CallBackApi.
     *
     * @return the CallBackApi
     */
    @Override
    public String getCallBackApi() {
        return unbounceService.getCallBackApi();
    }

    /**
     * Gets the dynamic variables.
     *
     * @return the dynamicVariables
     */
    @Override
    public Map<String, String> getDynamicVariables() {
        return dynamicVariables;
    }

    /**
     * Gets the theme.
     *
     * @return the theme
     */
    @Override
    public String getTheme() {
        return theme;
    }
}
