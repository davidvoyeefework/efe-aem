package com.efe.core.models;

import com.adobe.cq.wcm.core.components.models.Breadcrumb;
import com.adobe.cq.wcm.core.components.models.NavigationItem;


import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface BreadcrumbModel extends Breadcrumb {

    public List<NavigationItem> createItemsWithSelector();

}