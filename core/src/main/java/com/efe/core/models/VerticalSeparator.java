package com.efe.core.models;

import java.util.List;

/**
 * The Interface VerticalSeparator.
 */
public interface VerticalSeparator {

    /**
     * Gets the id.
     *
     * @return the id
     */
    String getId();

    /**
     * Gets the no Of Columns.
     *
     * @return the no Of Columns
     */
    int getNoOfColumns();

    /**
     * Gets the hidden separator.
     *
     * @return the hidden separator
     */
    String getHiddenSeparator();

    /**
     * Gets the column indices.
     *
     * @return the column indices
     */
    List<String> getColumnIndices();

    /**
     * Gets the ul class.
     *
     * @return the ul class
     */
    String getUlClass();

    /**
     * Gets the li class.
     *
     * @return the li class
     */
    String getLiClass();

}
