package com.myproject.core.listeners;

import com.myproject.core.services.DataPointsCacheService;
import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component(service = ResourceChangeListener.class, property = {
        ResourceChangeListener.PATHS + "=/content/dam/myproject/data",
        ResourceChangeListener.CHANGES + "=ADDED",
        ResourceChangeListener.CHANGES + "=CHANGED",
        ResourceChangeListener.CHANGES + "=REMOVED"
})
public class DataPointAssetListener implements ResourceChangeListener {

    private static final Logger LOG = LoggerFactory.getLogger(DataPointAssetListener.class);

    @Reference
    private DataPointsCacheService cacheService;

    @Override
    public void onChange(List<ResourceChange> changes) {
        for (ResourceChange change : changes) {
            if (change.getPath().endsWith("datapoints.json")) {
                LOG.info("DataPoints asset updated, invalidating cache");
                cacheService.invalidateCache();
                break;
            }
        }
    }
}
