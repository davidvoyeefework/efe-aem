package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.efe.core.models.SundayskyModel;
import org.apache.sling.models.annotations.Exporter;


/**
 * The Class SundayskyImpl.clean up code
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class SundayskyImpl implements SundayskyModel {

    @ValueMapValue
    private final String baseUrl = "https://myvideo.sundaysky.com/embed/";

    @ValueMapValue
    private String programId;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String height;

    @ValueMapValue
    private String width;

    @Override
    public String getVideoUrl() {
        return this.baseUrl + "?programId=" + this.programId;
    }

    /**
     * @return programId
     */
    @Override
    public String getProgramId() {
            return programId;
    }

    /**
     * @return title
     */
    @Override
    public String getTitle() {
            return title;
    }

    /**
     * @return height
     */
    @Override
    public String getHeight() {
            return height;
    }

    /**
     * @return width
     */
    @Override
    public String getWidth() {
            return width;
    }
}
