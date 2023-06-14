package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.CharacterLimitStyle;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import javax.annotation.PostConstruct;

/**
 * The Class CharacterLimitStyleImpl.
 */
@Model(adaptables = Resource.class, adapters = CharacterLimitStyle.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CharacterLimitStyleImpl implements CharacterLimitStyle {

    /** The characterLimit. */
    @ValueMapValue
    private int characterLimit;

    /** The styleValue. */
    private String styleValue = StringUtils.EMPTY;

    /**
     * Inits the model.
     */
    @PostConstruct
    protected void init() {
        if(characterLimit > 0) {
              styleValue = "max-width:"+ characterLimit +"ch; margin: 0 auto;";
        }
    }

    /**
     * Gets the Style Value.
     *
     * @return the Style Value
     */
    @Override
    public String getStyleValue() {
        return styleValue;
    }
}
