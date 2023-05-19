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
 * The Class VerticalSeparatorImpl.
 */
@Model(adaptables = Resource.class, adapters = VerticalSeparator.class, resourceType = {
        VerticalSeparatorImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class VerticalSeparatorImpl implements VerticalSeparator {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/components/verticalseparator";

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

    private String ulClass = StringUtils.EMPTY;

    private String liClass = StringUtils.EMPTY;

    /**
     * Inits the model.
     */
    @PostConstruct
    protected void init() {
        int arrayLength = getNoOfColumns();
        if (arrayLength > 0) {
            columnIndices = new String[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                columnIndices[i] = COLUMN + "_" + i;
            }
            setClasses();
        }
    }

    private void setClasses() {
        if (getNoOfColumns() == 3) {
            ulClass += VERTICAL_SEPARATOR_ONE;
            liClass = VERTICAL_SEPARATOR_ONE_ITEM;
        } else {
            ulClass += VERTICAL_SEPARATOR_TWO;
            liClass = VERTICAL_SEPARATOR_TWO_ITEM;
        }
        if(StringUtils.equalsIgnoreCase(getHiddenSeparator(),"false")) {
            ulClass += HORIZONTAL_SEPARATOR;
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
     * Gets the no Of Columns.
     *
     * @return the no Of Columns
     */
    @Override
    public int getNoOfColumns() {
        return noOfColumns;
    }

    /**
     * Gets the hidden separator.
     *
     * @return the hidden separator
     */
    @Override
    public String getHiddenSeparator() {
        return hiddenSeparator;
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
        return ulClass;
    }

    /**
     * Gets the li class.
     *
     * @return the li class
     */
    @Override
    public String getLiClass() {
        return liClass;
    }
}
