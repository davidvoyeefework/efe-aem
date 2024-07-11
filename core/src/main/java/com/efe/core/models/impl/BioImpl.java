package com.efe.core.models.impl;
import javax.annotation.PostConstruct;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.Bio;
import com.efe.core.utils.ResourceUtil;

// The class BioImpl
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = Bio.class, resourceType = {
    BioImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class BioImpl implements Bio {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/components/bio";         

	// The current resource
	@SlingObject
	private Resource resource;    

    // Resource Path
    @ValueMapValue    
    String resourcePath;
    
    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;    

    String thumbnail;
    String name;
    String title;
    String bodyCopy;
    String seoSchema;
    String liveUrl;
    String [] certs;
    String [] education;

    /**
     * Inits the Model.
     */
    @PostConstruct
        public void init() {

        resourcePath = resource.getPath();

        // Fetch CF for Author Details
        String resourceProperty = "AuthorContentFragment";
		String authorContentFragment = ResourceUtil.getProperty( resourceResolver, resourcePath, resourceProperty );
        if (authorContentFragment != null) {
            String resourceCFPath = authorContentFragment + "/jcr:content/data/master";

            // Fetch Name
            String resourceCFProperty = "name";
            name = ResourceUtil.getProperty(resourceResolver, resourceCFPath, resourceCFProperty);

            // Fetch Title
            String resourceTitleCFProperty = "title";
            title = ResourceUtil.getProperty(resourceResolver, resourceCFPath, resourceTitleCFProperty);   
            
            // Fetch Certs
            String resourceCertCFProperty = "certifications";
            certs = ResourceUtil.getProperties(resourceResolver, resourceCFPath, resourceCertCFProperty);  
            
            // // Fetch Education
            String resourceEducationCFProperty = "education";
            education = ResourceUtil.getProperties(resourceResolver, resourceCFPath, resourceEducationCFProperty); 

            // Fetch Body Copy
            String resourceCopyCFProperty = "biography";
            bodyCopy = ResourceUtil.getProperty(resourceResolver, resourceCFPath, resourceCopyCFProperty);       

            // Fetch Thumbnail
            String resourceImageCFProperty = "photo";
            thumbnail = ResourceUtil.getProperty(resourceResolver, resourceCFPath, resourceImageCFProperty);  
            
            // Fetch SEO Schema boolean
            String resourceSeoCFProperty = "seoSchema";
            seoSchema = ResourceUtil.getProperty(resourceResolver, resourcePath, resourceSeoCFProperty);    
            
            // Fetch Author Bio link
            String resourceliveUrlCFProperty = "liveUrl";
            liveUrl = ResourceUtil.getProperty(resourceResolver, resourceCFPath, resourceliveUrlCFProperty);             
        }
    }
        

        // Gets the Name from Author Content Fragment Model
        public String getName() {
            return name;
        }

        // Gets the Title from Author Content Fragment Model
        public String getTitle() {
            return title;
        }     
        
        // Gets the Title from Author Content Fragment Model
        public String [] getCerts() {
            return certs;
        }     
        
        
        // Gets the Education from Author Content Fragment Model
        public String [] getEducation() {
            return education;
        }         
        
        // Gets the Body Copy from Author Content Fragment Model
        public String getBodyCopy() {
            return bodyCopy;
        }     
        
        // Gets the Body Copy from Author Content Fragment Model
        public String getThumbnail() {
            return thumbnail;
        }          

        // Gets boolean toogle if author wants to include SEO Schema metadata
        public String getSeoSchema() {
            return seoSchema;
        }          

        // Gets author bio link from Author Content Fragment Model
        public String getliveUrl() {
            return liveUrl;
        }           
    
}
