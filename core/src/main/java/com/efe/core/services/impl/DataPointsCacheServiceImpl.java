// /core/src/main/java/com/myproject/core/services/impl/DataPointsCacheServiceImpl.java
package com.myproject.core.services.impl;

import com.myproject.core.models.DataPoint;
import com.myproject.core.services.DataPointsCacheService;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component(service = DataPointsCacheService.class, configurationPolicy = ConfigurationPolicy.OPTIONAL)
@Designate(ocd = DataPointsCacheServiceImpl.Config.class)
public class DataPointsCacheServiceImpl implements DataPointsCacheService {

    private static final Logger LOG = LoggerFactory.getLogger(DataPointsCacheServiceImpl.class);

    private final ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private static final String CACHE_KEY = "datapoints";

    private long cacheTTL;

    @ObjectClassDefinition(name = "Data Points Cache Configuration")
    public @interface Config {
        @AttributeDefinition(name = "Cache TTL (minutes)", description = "Time to live for cached data in minutes")
        long cache_ttl() default 60; // 1 hour default
    }

    @Activate
    protected void activate(Config config) {
        this.cacheTTL = TimeUnit.MINUTES.toMillis(config.cache_ttl());
        LOG.info("DataPoints Cache Service activated with TTL: {} minutes", config.cache_ttl());
    }

    @Override
    public List<DataPoint> getCachedDataPoints() {
        CacheEntry entry = cache.get(CACHE_KEY);

        if (entry != null && !entry.isExpired()) {
            LOG.debug("Returning cached data points");
            return entry.getData();
        }

        LOG.debug("Cache miss or expired");
        return null;
    }

    @Override
    public void cacheDataPoints(List<DataPoint> dataPoints) {
        cache.put(CACHE_KEY, new CacheEntry(dataPoints, cacheTTL));
        LOG.debug("Cached {} data points", dataPoints.size());
    }

    @Override
    public void invalidateCache() {
        cache.clear();
        LOG.info("Cache invalidated");
    }

    private static class CacheEntry {
        private final List<DataPoint> data;
        private final long expiryTime;

        CacheEntry(List<DataPoint> data, long ttl) {
            this.data = data;
            this.expiryTime = System.currentTimeMillis() + ttl;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }

        List<DataPoint> getData() {
            return data;
        }
    }
}
