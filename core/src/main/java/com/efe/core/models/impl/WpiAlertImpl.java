package com.efe.core.models.impl;

import javax.annotation.PostConstruct;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.efe.core.models.WpiAlert;
import com.efe.core.utils.ResourceUtil;

/**
 * The Class WpiAlertImpl
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = WpiAlert.class, resourceType = {
		WpiAlertImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class WpiAlertImpl implements WpiAlert {
    
    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/fe-components/content/wpi-alert";

    /** The Headline content fragment path */
    @ValueMapValue
    private String headlineFragmentPath;

    /** The page resource path. */
	String resourcePath;

    @ScriptVariable
    protected Page currentPage;

    /** The resource property. */
	String resourceProperty;

    /** The headline CF */
	String headlineContentFragment;

    /** The resource CF path */
	String resourceCFPath;

    /** The resource CF path property */
	String resourceCFProperty;

    /** The resource CF path property value */
	String headlineCFValue;

    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * Inits the Model.
     */
    @PostConstruct
    public void init() {

        resourcePath = currentPage.getPath() + "/jcr:content/root/container/container/wpi_alert";
        resourceProperty = "Headline";
		headlineContentFragment = ResourceUtil.getProperty( resourceResolver, resourcePath, resourceProperty );
        if (headlineContentFragment != null) {
            resourceCFPath = headlineContentFragment + "/jcr:content";
            resourceCFProperty = "jcr:title";
            headlineCFValue = ResourceUtil.getProperty(resourceResolver, resourceCFPath, resourceCFProperty);
        }
        else {
            headlineCFValue = "";
        }
    
    }




    /**
     * Gets the Headline Fragment Path.
     *
     * @return the Headline
     */

    @Override
    public String getHeadline() {
        return headlineCFValue;
    }
}
