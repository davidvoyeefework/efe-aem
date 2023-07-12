package com.efe.core.services.impl;

import com.efe.core.services.UnbounceService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Designate(ocd = UnbounceServiceImpl.Config.class)
@Component(service = UnbounceService.class)
public class UnbounceServiceImpl implements UnbounceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnbounceServiceImpl.class);

    private String pageFrameApi;

    private String aggregateApi;

    private String forKeyApi;

    private String softAuthApi;

    private String signupApi;

    private String scheduleApi;

    private String authenticateApi;

    private String callBackApi;

    private String[] dynamicVariableList;

    @ObjectClassDefinition(name = "EFE Sponsor Details Api", description = "EFE Sponsor Details Api")
    public static @interface Config {

        @AttributeDefinition(name="PageFrame Api", description="PageFrame Api")
        String pageFrameApi();

        @AttributeDefinition(name="Aggregate Api", description="Aggregate Api")
        String aggregateApi();

        @AttributeDefinition(name="For Key Api", description="For Key Api")
        String forKeyApi();

        @AttributeDefinition(name="Soft Auth Api", description="Soft Auth Api")
        String softAuthApi();

        @AttributeDefinition(name="Signup Api", description="Signup Api")
        String signupApi();

        @AttributeDefinition(name="Schedule Api", description="Schedule Api")
        String scheduleApi();

        @AttributeDefinition(name="Authenticate Api", description="Authenticate Api")
        String authenticateApi();

        @AttributeDefinition(name = "CallBack Api", description = "CallBack Api")
        String callBackApi();

        @AttributeDefinition(name = "Unbounce Dynamic Variables", description = "Unbounce Dynamic Variables")
        String[] dynamicVariableList();
    }

    @Activate
    @Modified
    public void activate(final UnbounceServiceImpl.Config config) {
        LOGGER.debug("DynamicVariableServiceImpl.activate method called {}", "{}");
        this.pageFrameApi = config.pageFrameApi();
        this.aggregateApi = config.aggregateApi();
        this.forKeyApi = config.forKeyApi();
        this.callBackApi = config.callBackApi();
        this.authenticateApi = config.authenticateApi();
        this.scheduleApi = config.scheduleApi();
        this.signupApi = config.signupApi();
        this.softAuthApi = config.softAuthApi();
        this.dynamicVariableList = config.dynamicVariableList();
    }

    @Override
    public String getPageFrameApi() {
        return pageFrameApi;
    }

    @Override
    public String getAggregateApi() {
        return aggregateApi;
    }

    @Override
    public String getForKeyApi() {
        return forKeyApi;
    }

    @Override
    public String getSoftAuthApi() {
        return softAuthApi;
    }

    @Override
    public String getSignupApi() {
        return signupApi;
    }

    @Override
    public String getScheduleApi() {
        return scheduleApi;
    }

    @Override
    public String getAuthenticateApi() {
        return authenticateApi;
    }

    @Override
    public String getCallBackApi() {
        return callBackApi;
    }

    @Override
    public String[] getDynamicVariableList() {
        return dynamicVariableList;
    }
}
