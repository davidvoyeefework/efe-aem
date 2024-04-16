package com.efe.core.models.impl;
import com.efe.core.models.Image;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.efe.core.services.DynamicMediaService;
import com.efe.core.utils.ResourceUtil;
import com.adobe.cq.export.json.ExporterConstants;

@Model(adaptables = Resource.class, adapters = Image.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ImageImpl implements Image {

    /**
     * The current resource.
     */
    @Self
    private Resource resource;    

    /**
     * Injecting dynamicMediaService
     *
     */
    @OSGiService
    private DynamicMediaService dynamicMediaService;   
    
    // Resource Path
    @ValueMapValue    
    String resourcePath;   

    /** The primary image. */
    @ValueMapValue
    private String fileName;    
    
    // Resource Property
    String resourceProperty;    

    // Primary Image modifier
    String ImageModifier;

    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;    


    public String getImageModifier () {
        resourcePath = resource.getPath();
        resourceProperty = "scene7imagemodifier";        
        ImageModifier = ResourceUtil.getProperty(resourceResolver, resourcePath, resourceProperty);  
        // String basePath = dynamicMediaService.getDmImagePath(resourceResolver, fileName);                   
        return ImageModifier;
    }
    
}


