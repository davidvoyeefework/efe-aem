package com.efe.core.models.impl;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.Calculator;


/**
 * The Class WpiAlertImpl
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = Calculator.class, resourceType = {
		CalculatorImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class CalculatorImpl implements Calculator {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/components/calculator";

    // Calc Type
    @ValueMapValue    
    String calcType;

    /**
     * Inits the Model.
     */
    @PostConstruct
    public void init() {
    }

    public String getCalc() {
        return calcType;
    }

}
