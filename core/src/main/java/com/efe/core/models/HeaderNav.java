package com.efe.core.models;


import com.efe.core.models.multifield.NavigationList;

import java.util.List;

/**
 * The Interface Header Model.
 */

public interface HeaderNav {
    /**
     * Gets the Header Nav list.
     *
     * @return the Header Nav list
     */
    List<NavigationList> getPrimaryList();
}
