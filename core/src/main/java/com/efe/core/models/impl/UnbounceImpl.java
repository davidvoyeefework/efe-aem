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
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Model(adaptables = Resource.class , adapters = Unbounce.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class UnbounceImpl implements Unbounce {
    @OSGiService
    private UnbounceService unbounceService;

    /** The resolver factory. */
    @OSGiService
    private ResourceResolverFactory resolverFactory;

    private final Map<String, String> dynamicVariables = new HashMap<>();

    /**
     * Inits the model.
     */
    @PostConstruct
    protected void init() {
        setUnbounceField();
    }

    /**
     * Sets the unbounce field.
     */
    private void setUnbounceField() {
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

    @Override
    public String getPageFrameApi() {
        return unbounceService.getPageFrameApi();
    }

    @Override
    public String getAggregateApi() {
        return unbounceService.getAggregateApi();
    }

    @Override
    public String getForKeyApi() {
        return unbounceService.getForKeyApi();
    }

    @Override
    public String getSoftAuthApi() {
        return unbounceService.getSoftAuthApi();
    }

    @Override
    public String getSignupApi() {
        return unbounceService.getSignupApi();
    }

    @Override
    public String getScheduleApi() {
        return unbounceService.getScheduleApi();
    }

    @Override
    public String getAuthenticateApi() {
        return unbounceService.getAuthenticateApi();
    }

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

}
