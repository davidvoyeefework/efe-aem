package com.efe.core.models.impl;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.Breadcrumb;
import com.adobe.cq.wcm.core.components.models.Image;
import com.adobe.cq.wcm.core.components.models.NavigationItem;

import org.apache.sling.models.annotations.*;

import java.util.Collection;
import java.util.Collections;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

@Model(
    adaptables = SlingHttpServletRequest.class,
    adapters = { BreadcrumbModelImpl.class,ComponentExporter.class},
    resourceType = BreadcrumbModelImpl.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class BreadcrumbModelImpl {

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
    
        // method required by `ComponentExporter` interface
        // exposes a JSON property named `:type` with a value of `wknd-spa-react/components/banner`
        // required to map the JSON export to the SPA component props via the `MapTo`
        @Override
        public String getExportedType() {
            return BreadcrumbModelImpl.RESOURCE_TYPE;
        }

        @Override
        public Collection<NavigationItem> getItems() {
            return breadcrumb.getItems();
        }
}
