package com.efe.core.models.impl;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.Breadcrumb;
import com.adobe.cq.wcm.core.components.models.NavigationItem;
import com.efe.core.models.BreadcrumbModel;

import org.apache.sling.models.annotations.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

@Model(
    adaptables = SlingHttpServletRequest.class,
    adapters = { BreadcrumbModel.class,ComponentExporter.class},
    resourceType = BreadcrumbModelImpl.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class BreadcrumbModelImpl implements BreadcrumbModel {

        // points to the the component resource path in ui.apps
        static final String RESOURCE_TYPE = "efe/components/breadcrumb";

        @Self
        private SlingHttpServletRequest request;

        // With sling inheritance (sling:resourceSuperType) we can adapt the current resource to the Image class
        // this allows us to re-use all of the functionality of the Image class, without having to implement it ourself
        // see https://github.com/adobe/aem-core-wcm-components/wiki/Delegation-Pattern-for-Sling-Models
        @Self
        @Via(type = ResourceSuperType.class)
        private Breadcrumb breadcrumb;
    
        public String getExportedType() {
            return BreadcrumbModelImpl.RESOURCE_TYPE;
        }

        public Collection<NavigationItem> getItems() {
            return breadcrumb.getItems();
        }

        public List<NavigationItem> createItemsWithSelector() {
            return null;
        }
}
