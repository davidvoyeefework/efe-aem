package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.ImageCollage;
import com.efe.core.services.DynamicMediaService;
import com.efe.core.utils.EFEUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.efe.core.utils.ResourceUtil;

/**
 * The Class ImageCollageImpl.
 */
@Model(adaptables = Resource.class, adapters = ImageCollage.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ImageCollageImpl implements ImageCollage {

    /**
     * The current resource.
     */
    @Self
    private Resource resource;

    /** The id. */
    @ValueMapValue
    private String id;

    /** The primary image. */
    @ValueMapValue
    private String primaryImage;

    /** The primary image alt text. */
    @ValueMapValue
    private String primaryImageAltText;

    /** The first secondary image. */
    @ValueMapValue
    private String firstSecondaryImage;

    /** The first secondary image alt text. */
    @ValueMapValue
    private String firstSecondaryImageAltText;

    /** The second secondary image. */
    @ValueMapValue
    private String secondSecondaryImage;

    /** The second secondary image alt text. */
    @ValueMapValue
    private String secondSecondaryImageAltText;

    /** The primary image position. */
    @ValueMapValue
    private String primaryImagePosition;

    /** The first secondary image position. */
    @ValueMapValue
    private String firstSecondaryImagePosition;

    /** The second secondary image position. */
    @ValueMapValue
    private String secondSecondaryImagePosition;

    /**
     * Injecting dynamicMediaService
     *
     */
    @OSGiService
    private DynamicMediaService dynamicMediaService;

    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

    // Primary Image modifier
    String primaryImageModifier;

    // First Secondary Image modifier
    String firstSecondaryImageModifier;    

    // Second Secondary Image modifier
    String secondSecondaryImageModifier;     

    // Resource Path
    @ValueMapValue    
    String resourcePath;

    // Resource Property
    String resourceProperty;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        if (id == null) {
            id = EFEUtil.getId(resource);
        }
        return id;
    }  

    /**
     * Gets the primary image.
     *
     * @return the primary image
     */
    public String getPrimaryImage() {
        resourcePath = resource.getPath();
        resourceProperty = "primaryimgmodifier";
        String resourcePropertyStyleId = "cq:styleIds";
        primaryImageModifier = ResourceUtil.getProperty(resourceResolver, resourcePath, resourceProperty); 
        String basePath = dynamicMediaService.getDmImagePath(resourceResolver, primaryImage);  
        String [] styleRendition = ResourceUtil.getProperties(resourceResolver, resourcePath, resourcePropertyStyleId);     
        if (primaryImageModifier == null) {
            if (styleRendition[0].equals("primaryBottom-multi-corner-radius") || styleRendition[0].equals("primaryTop-multi-corner-radius")){
                String scene7path = basePath + "?wid=720&hei=310&op_sharpen=1&qlt=85&fmt=webp&fit=crop,1";
                return scene7path; 
            }
            String scene7path = basePath + "?op_sharpen=1&qlt=85&fmt=webp&hei=826&wid=480&fit=crop,1";
            return scene7path;  
            
        }
        else {
            if (styleRendition[0].equals("primaryBottom-multi-corner-radius") || styleRendition[0].equals("primaryTop-multi-corner-radius")){
                String scene7path = basePath + "?wid=720&hei=310&op_sharpen=1&qlt=85&fmt=webp&fit=crop,1" + primaryImageModifier;
                return scene7path; 
            }
            String scene7path = basePath + "?op_sharpen=1&qlt=85&fmt=webp&hei=826&wid=480&fit=crop,1" + primaryImageModifier;
            return scene7path;            
        }
    }

    /**
     * Gets the primary image alt text.
     *
     * @return the primary image alt text
     */
    public String getPrimaryImageAltText() {
        if (primaryImageAltText == null) {
            return " ";
        }
        else {
            return primaryImageAltText;
        }        
    }

    /**
     * Gets the first secondary image.
     *
     * @return the first secondary image
     */
    public String getFirstSecondaryImage() {
        resourcePath = resource.getPath();
        resourceProperty = "firstsecondaryimgmodifier";
        firstSecondaryImageModifier = ResourceUtil.getProperty(resourceResolver, resourcePath, resourceProperty);           
        String basePath = dynamicMediaService.getDmImagePath(resourceResolver, firstSecondaryImage);
        if (firstSecondaryImageModifier == null) {
            String scene7path = basePath + "?op_sharpen=1&qlt=85&fmt=webp&wid=480&hei=413&fit=crop,1";
            return scene7path;            
        }        
        else {
            String scene7path = basePath + "?op_sharpen=1&qlt=85&fmt=webp&wid=480&hei=413&fit=crop,1" + firstSecondaryImageModifier;
            return scene7path;
        }
    }

    /**
     * Gets the first secondary image alt text.
     *
     * @return the first secondary image alt text
     */
    public String getFirstSecondaryImageAltText() {
        if (firstSecondaryImageAltText == null) {
            return " ";
        }
        else {
            return firstSecondaryImageAltText;
        }
    }

    /**
     * Gets the second secondary image.
     *
     * @return the second secondary image
     */
    public String getSecondSecondaryImage() {
        resourcePath = resource.getPath();
        resourceProperty = "secondsecondaryimgmodifier";
        secondSecondaryImageModifier = ResourceUtil.getProperty(resourceResolver, resourcePath, resourceProperty);          
        String basePath = dynamicMediaService.getDmImagePath(resourceResolver, secondSecondaryImage);
        if (secondSecondaryImageModifier == null) {   
            String scene7path = basePath + "?op_sharpen=1&qlt=85&fmt=webp&wid=480&hei=413&fit=crop,1";  
            return scene7path;                          
        }
        else {
            String scene7path = basePath + "?op_sharpen=1&qlt=85&fmt=webp&wid=480&hei=413&fit=crop,1" + secondSecondaryImageModifier;
            return scene7path;
        } 

    }

    /**
     * Gets the second secondary image alt text.
     *
     * @return the second secondary image alt text
     */
    public String getSecondSecondaryImageAltText() {
        if (secondSecondaryImageAltText == null) {
            return " ";
        }
        else {
            return secondSecondaryImageAltText;
        }
    }

    /**
     * Gets the primary image position
     *
     * @return the primary image position
     */
    @Override
    public String getPrimaryImagePosition() {
        return primaryImagePosition;
    }

    /**
     * Gets the first secondary image position
     *
     * @return the first secondary image position
     */
    @Override
    public String getFirstSecondaryImagePosition() {
        return firstSecondaryImagePosition;
    }

    /**
     * Gets the second secondary image position
     *
     * @return the second secondary image position
     */
    @Override
    public String getSecondSecondaryImagePosition() {
        return secondSecondaryImagePosition;
    }
}
