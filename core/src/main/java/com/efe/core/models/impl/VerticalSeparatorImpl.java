package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.VerticalSeparator;
import com.efe.core.utils.EFEUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

/**
 * The Class ContentCollageImpl.
 */
@Model(adaptables = Resource.class, adapters = VerticalSeparator.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class VerticalSeparatorImpl implements VerticalSeparator {

    /** The Constant COLUMN. */
    public static final String COLUMN = "column";

    /** The Constant VERTICAL_SEPARATOR_ONE. */
    public static final String VERTICAL_SEPARATOR_ONE = "cmp-vertical-separator--one";

    /** The Constant VERTICAL_SEPARATOR_TWO. */
    public static final String VERTICAL_SEPARATOR_TWO = "cmp-vertical-separator--two";

    /** The Constant VERTICAL_SEPARATOR_ONE_ITEM. */
    public static final String VERTICAL_SEPARATOR_ONE_ITEM = "cmp-vertical-separator--one__item";

    /** The Constant VERTICAL_SEPARATOR_TWO_ITEM. */
    public static final String VERTICAL_SEPARATOR_TWO_ITEM = "cmp-vertical-separator--two__item";

    /** The Constant HORIZONTAL_SEPARATOR. */
    public static final String HORIZONTAL_SEPARATOR = " cmp-horizontal-separator";

    /**
     * The current resource.
     */
    @Self
    private Resource resource;

    /** The id. */
    @ValueMapValue
    private String id;

    /** The noOfColumns. */
    @ValueMapValue
    private int noOfColumns;

    /** The hiddenSeparator. */
    @ValueMapValue
    private String hiddenSeparator;

    /** The columnIndices. */
    private String[] columnIndices;

    /**
     * Inits the model.
     */
    @PostConstruct
    protected void init() {
        columnIndices = new String[noOfColumns];
        for (int i = 0; i < noOfColumns; i++) {
            columnIndices[i] = COLUMN + "_" + i;
        }
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Override
    public String getId() {
        if (id == null) {
            id = EFEUtil.getId(resource);
        }
        return id;
    }

    /**
     * Gets the column indices.
     *
     * @return the column indices
     */
    @Override
    public String[] getColumnIndices() {
        return columnIndices;
    }

    /**
     * Gets the ul class.
     *
     * @return the ul class
     */
    @Override
    public String getUlClass() {
        String ulClass = StringUtils.EMPTY;
        if (noOfColumns == 3) {
            ulClass += VERTICAL_SEPARATOR_ONE;
        } else {
            ulClass += VERTICAL_SEPARATOR_TWO;
        }
        if(StringUtils.equalsIgnoreCase(hiddenSeparator,"false")) {
            ulClass += HORIZONTAL_SEPARATOR;
        }
        return ulClass;
    }

    /**
     * Gets the li class.
     *
     * @return the li class
     */
    @Override
    public String getLiClass() {
        if (noOfColumns == 3) {
            return VERTICAL_SEPARATOR_ONE_ITEM;
        } else {
            return VERTICAL_SEPARATOR_TWO_ITEM;
        }
    }
}
