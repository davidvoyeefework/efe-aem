package com.efe.core.models.impl;
import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.ComparisonCard;
import com.efe.core.utils.ResourceUtil;
import org.apache.sling.api.resource.ResourceResolver;
import com.day.cq.wcm.api.Page;

/**
 * The Class WpiAlertImpl
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = ComparisonCard.class, resourceType = {
    ComparisonCardImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class ComparisonCardImpl implements ComparisonCard {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/fe-components/content/comparisoncard";

    // Body Copy Content Fragment
	String bodyContentFragment;    

    @ScriptVariable
    protected Page currentPage;    
    
    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;    

    // Resource Path
    @ValueMapValue    
    String resourcePath;    

	// The current resource
	@SlingObject
	private Resource resource;        

    String bodyCFValue;

    @PostConstruct
    public void init() {

        resourcePath = resource.getPath();

        // Fetch CF for Body Content
        String resourceProperty = "bodycontent";
		bodyContentFragment= ResourceUtil.getProperty( resourceResolver, resourcePath, resourceProperty );

        if (bodyContentFragment!= null) {
            String resourceCFPath = bodyContentFragment+ "/jcr:content/data/master";
            String resourceCFProperty = "content";
            bodyCFValue = ResourceUtil.getProperty(resourceResolver, resourceCFPath, resourceCFProperty);
        }
        else {
            bodyCFValue = "";
        }        
    }

    // Gets the Title
    public String getTitle() {
        return "test";
    }

    // Gets the Body Content
    public String getContent() {
        return bodyCFValue;
    }
    
}
