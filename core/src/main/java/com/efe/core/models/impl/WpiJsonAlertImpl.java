package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.efe.core.models.WpiAlert;
import com.efe.core.models.WpiJsonAlert;
import com.efe.core.services.DynamicMediaService;
import com.efe.core.utils.FragmentUtil;
import com.efe.core.utils.ResourceUtil;
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

import javax.annotation.PostConstruct;

/**
 * The Class WpiJsonAlertImpl
 */
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = WpiJsonAlert.class, resourceType = {
        WpiJsonAlertImpl.RESOURCE_TYPE}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class WpiJsonAlertImpl implements WpiJsonAlert {

    /**
     * The Constant RESOURCE_TYPE.
     */
    public static final String RESOURCE_TYPE = "efe/fe-components/content/wpi-json-alert";
    String imageUrl;

    String altText;

    String imageCode;

    String label;

    String passThroughState;

    String poweredByText;

    String poweredByImage;

    String titleCFContent;

    String subtitleCFContent;

    String header1CFContent;

    String header2CFContent;

    String body1CFContent;

    String body2CFContent;

    String footerCFContent;

    /**
     * The page resource path.
     */
    String resourcePath;

    @ScriptVariable
    protected Page currentPage;

    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    private DynamicMediaService dynamicMediaService;

    /**
     * Inits the Model.
     */
    @PostConstruct
    public void init() {

        resourcePath = currentPage.getPath() + "/jcr:content/root/wpi_json_alert";
        titleCFContent = FragmentUtil.populateCFContent(resourcePath, resourceResolver, "title", "titleCFVariations");
        subtitleCFContent = FragmentUtil.populateCFContent(resourcePath, resourceResolver, "subtitle", "subtitleCFVariations");
        header1CFContent = FragmentUtil.populateCFContent(resourcePath, resourceResolver, "header1", "header1CFVariations");
        header2CFContent = FragmentUtil.populateCFContent(resourcePath, resourceResolver, "header2", "header2CFVariations");
        body1CFContent = FragmentUtil.populateCFContent(resourcePath, resourceResolver, "body1", "body1CFVariations");
        body2CFContent = FragmentUtil.populateCFContent(resourcePath, resourceResolver, "body2", "body2CFVariations");
        footerCFContent = FragmentUtil.populateCFContent(resourcePath, resourceResolver, "footer", "footerCFVariations");

        imageUrl = ResourceUtil.getProperty(resourceResolver, resourcePath, "imageUrl");
        altText = ResourceUtil.getProperty(resourceResolver, resourcePath, "altText");
        imageCode = ResourceUtil.getProperty(resourceResolver, resourcePath, "imageCode");
        label = ResourceUtil.getProperty(resourceResolver, resourcePath, "label");
        passThroughState = ResourceUtil.getProperty(resourceResolver, resourcePath, "passThroughState");
        poweredByText = ResourceUtil.getProperty(resourceResolver, resourcePath, "poweredByText");
        poweredByImage = ResourceUtil.getProperty(resourceResolver, resourcePath, "poweredByImage");

    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public String getImageCode() {
        return imageCode;
    }

    public String getLabel() {
        return label;
    }

    public String getPassThroughState() {
        return passThroughState;
    }

    public String getPoweredByText() {
        return poweredByText;
    }

    public String getPoweredByImage() {
        return poweredByImage;
    }

    public String getTitleCFContent() {
        return titleCFContent;
    }

    public String getSubtitleCFContent() {
        return subtitleCFContent;
    }

    public String getHeader1CFContent() {
        return header1CFContent;
    }

    public String getHeader2CFContent() {
        return header2CFContent;
    }

    public String getBody1CFContent() {
        return body1CFContent;
    }

    public String getBody2CFContent() {
        return body2CFContent;
    }

    public String getFooterCFContent() {
        return footerCFContent;
    }
}