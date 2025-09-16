package com.efe.core.models.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.efe.core.models.WpiAlert;
import com.efe.core.utils.ResourceUtil;
import com.efe.core.services.DynamicMediaService;

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

    /** The resource property for headline */
	String resourceProperty;

    /** The resource property for subheadline */
	String resourcePropertySubheadline;

    /** The headline CF */
	String headlineContentFragment;

    /** The subheadline CF */
	String subheadlineContentFragment;

    /** The resource CF path for headline */
	String resourceCFPath;

    /** The resource CF path for subheadline */
	String resourceCFPathSubheadline;






    /** The resource CF path for disclosures */
	String disclosureContentFragment;    

    /** The resource CF path property for disclosure */
	String resourcePropertyDisclosure;   
    
    /** The resource CF path property for disclosure */
	String resourceCFPropertyDisclosure;    
    
    /** The resource value for disclosure */
    String resourceCFPathDisclosure; 

    /** The resource value for disclosure */
    String disclosureCFValue;





    /** The resource CF path property for headline */
	String resourceCFProperty;

    /** The resource CF path property for subheadline */
	String resourceCFPropertySubheadline;

    /** The resource CF path property value for headline */
	String headlineCFValue;

    /** The resource CF path property value for subheadline */
	String subheadlineCFValue;

    /** The record keeper theme util class */
	String recordKeeper;   

    // The viewcode for specific record keeper
	String viewcode;   

    // The CTA text
	String cta;    

    // The Icon IMG path
	String icon;

    // Destination
	String destination;   

    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    private DynamicMediaService dynamicMediaService;    

    /**
     * Inits the Model.
     */
    @PostConstruct
    public void init() {

        resourcePath = currentPage.getPath() + "/jcr:content/root/wpi_alert";

        // Fetch CF for Headline
        resourceProperty = "Headline";
		headlineContentFragment = ResourceUtil.getProperty( resourceResolver, resourcePath, resourceProperty );
        String headlineVariationCFPrperty = "HeadlineVariations";
        String headlineContentFragmentVariation = ResourceUtil.getProperty( resourceResolver, resourcePath, headlineVariationCFPrperty);


        if (headlineContentFragment != null) {
            resourceCFPath = headlineContentFragment + "/jcr:content/data/master";
            if (StringUtils.isNotEmpty(headlineContentFragmentVariation)) {
                resourceCFPath = headlineContentFragment + "/jcr:content/data/"+headlineContentFragmentVariation;
            }

            resourceCFProperty = "content";
            headlineCFValue = ResourceUtil.getProperty(resourceResolver, resourceCFPath, resourceCFProperty);
        }
        else {
            headlineCFValue = "";
        }

        // Fetch CF for Subheadline
        resourcePropertySubheadline = "Subheadline";
        subheadlineContentFragment = ResourceUtil.getProperty( resourceResolver, resourcePath, resourcePropertySubheadline);
        String subHeadlineVariationCFPrperty = "SubheadlineVariations";
        String subHeadlineContentFragmentVariation = ResourceUtil.getProperty( resourceResolver, resourcePath, subHeadlineVariationCFPrperty);

        if (subheadlineContentFragment != null) {
            resourceCFPathSubheadline = subheadlineContentFragment + "/jcr:content/data/master";
            if (StringUtils.isNotEmpty(subHeadlineContentFragmentVariation)) {
                resourceCFPathSubheadline = subheadlineContentFragment + "/jcr:content/data/"+subHeadlineContentFragmentVariation;
            }

            resourceCFPropertySubheadline = "content";
            subheadlineCFValue = ResourceUtil.getProperty(resourceResolver, resourceCFPathSubheadline, resourceCFPropertySubheadline);
        }

        else {
            subheadlineCFValue = "";
        }

        // Fetch CF for Disclosures
        resourcePropertyDisclosure = "Disclosure";
        disclosureContentFragment = ResourceUtil.getProperty( resourceResolver, resourcePath, resourcePropertyDisclosure);
        String disclosureVariationCFPrperty = "DisclosureVariations";
        String disclosureContentFragmentVariation = ResourceUtil.getProperty( resourceResolver, resourcePath, disclosureVariationCFPrperty);

        if (disclosureContentFragment != null) {
            resourceCFPathDisclosure = disclosureContentFragment + "/jcr:content/data/master";
            if (StringUtils.isNotEmpty(disclosureContentFragmentVariation)) {
                resourceCFPathDisclosure = disclosureContentFragment + "/jcr:content/data/"+disclosureContentFragmentVariation;
            }
            resourceCFPropertyDisclosure = "content";
            disclosureCFValue = ResourceUtil.getProperty(resourceResolver, resourceCFPathDisclosure, resourceCFPropertyDisclosure);
        }

        else {
            disclosureCFValue = "";
        }        



        // Fetch Record Keeper theme util class
        recordKeeper = ResourceUtil.getProperty( resourceResolver, resourcePath, "record");

        // Fetch viewcode
        viewcode = ResourceUtil.getProperty( resourceResolver, resourcePath, "viewcode");       
        
        // Fetch CTA text
        cta = ResourceUtil.getProperty( resourceResolver, resourcePath, "ctatext");     
        
        // Fetch Icon IMG path
        icon = ResourceUtil.getProperty( resourceResolver, resourcePath, "icon");       
        
        // Fetch CTA Destination token
        destination = ResourceUtil.getProperty( resourceResolver, resourcePath, "destination");           

    }

    // Gets the Headline Content Fragment Path Value
    public String getHeadline() {
        return headlineCFValue;
    }

    // Gets the Subheadline Content Fragment Path Value
    public String getSubheadline() {
        return subheadlineCFValue;
    }

    // Gets the Disclosure Content Fragment Path Value
    public String getDisclosures() {
        return disclosureCFValue;
    }    

    // Gets Record keeper theme class
    public String getRecordKeeper() {
        return recordKeeper;
    }

    // Gets Record keeper theme class
    public String getViewcode() {
        return viewcode;
    }    

    // Gets CTA copy
    public String getCta() {
        return cta;
    }     

    // Gets Icon IMG
    public String getIcon() {
        return dynamicMediaService.getDmImagePath(resourceResolver, icon);
    }

    // Gets destination cta token
    public String getDestination() {
        return destination;
    }      
}
