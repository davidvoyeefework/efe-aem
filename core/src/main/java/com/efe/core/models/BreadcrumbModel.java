package com.efe.core.models;

import com.adobe.cq.wcm.core.components.models.BreadcrumbModel;
import com.adobe.cq.wcm.core.components.models.NavigationItem;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface BreadcrumbModel extends BreadcrumbModel {

    private List<NavigationItem> createItemsWithSelector();

}