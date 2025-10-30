package com.myproject.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component(service = Servlet.class, property = {
        "sling.servlet.resourceTypes=myproject/datasources/datapoints",
        "sling.servlet.methods=GET"
})
@ServiceDescription("DataPoints DataSource Servlet - Reads from DAM Asset")
public class DataPointsDataSourceServlet extends SlingSafeMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(DataPointsDataSourceServlet.class);

    // Configuration - this could be made configurable via OSGi
    private static final String DAM_ASSET_PATH = "/content/dam/myproject/data/datapoints.json";

    @Reference
    private DataPointsCacheService cacheService; // Optional caching layer

    @Override
    protected void doGet(SlingHttpServletRequest request,
            SlingHttpServletResponse response) {

        ResourceResolver resolver = request.getResourceResolver();
        List<Resource> resourceList = new ArrayList<>();

        try {
            // Check cache first (optional)
            List<DataPoint> dataPoints = cacheService.getCachedDataPoints();

            if (dataPoints == null || dataPoints.isEmpty()) {
                // Read from DAM
                dataPoints = fetchDataPointsFromDAM(resolver);

                // Update cache
                if (!dataPoints.isEmpty()) {
                    cacheService.cacheDataPoints(dataPoints);
                }
            }

            // Build dropdown options
            for (DataPoint dp : dataPoints) {
                ValueMap vm = new ValueMapDecorator(new HashMap<>());
                vm.put("value", dp.getId());
                vm.put("text", dp.getLabel());

                // Optional: Add a description tooltip
                if (dp.getMetadata() != null) {
                    vm.put("description", dp.getMetadata());
                }

                resourceList.add(new ValueMapResource(
                        resolver, new Resource() {
                            @Override
                            public String getPath() {
                                return "";
                            }

                            @Override
                            public String getName() {
                                return dp.getId();
                            }

                            @Override
                            public Resource getParent() {
                                return null;
                            }

                            @Override
                            public ResourceResolver getResourceResolver() {
                                return resolver;
                            }

                            @Override
                            public String getResourceType() {
                                return "";
                            }

                            @Override
                            public String getResourceSuperType() {
                                return null;
                            }

                            @Override
                            public boolean hasChildren() {
                                return false;
                            }

                            @Override
                            public boolean isResourceType(String resourceType) {
                                return false;
                            }

                            @Override
                            public ResourceMetadata getResourceMetadata() {
                                return new ResourceMetadata();
                            }

                            @Override
                            public ValueMap getValueMap() {
                                return vm;
                            }

                            @Override
                            public <T> T adaptTo(Class<T> type) {
                                return null;
                            }

                            @Override
                            public Iterable<Resource> getChildren() {
                                return null;
                            }

                            @Override
                            public Resource getChild(String relPath) {
                                return null;
                            }

                            @Override
                            public Iterator<Resource> listChildren() {
                                return null;
                            }
                        }, "", vm));
            }

        } catch (Exception e) {
            LOG.error("Error fetching data points from DAM", e);

            // Provide a fallback option
            ValueMap fallback = new ValueMapDecorator(new HashMap<>());
            fallback.put("value", "");
            fallback.put("text", "-- No data available --");
            resourceList.add(new ValueMapResource(resolver, "", "", fallback));
        }

        DataSource dataSource = new SimpleDataSource(resourceList.iterator());
        request.setAttribute(DataSource.class.getName(), dataSource);
    }

    private List<DataPoint> fetchDataPointsFromDAM(ResourceResolver resolver) {
        List<DataPoint> dataPoints = new ArrayList<>();

        try {
            Resource assetResource = resolver.getResource(DAM_ASSET_PATH);
            if (assetResource != null) {
                Asset asset = assetResource.adaptTo(Asset.class);
                if (asset != null) {
                    // Get the original rendition (the JSON file itself)
                    Rendition original = asset.getOriginal();
                    if (original != null) {
                        try (InputStream is = original.getStream();
                                InputStreamReader reader = new InputStreamReader(is)) {

                            Gson gson = new Gson();
                            JsonObject root = gson.fromJson(reader, JsonObject.class);

                            // Parse the JSON structure
                            if (root.has("datapoints")) {
                                JsonArray datapointsArray = root.getAsJsonArray("datapoints");

                                for (int i = 0; i < datapointsArray.size(); i++) {
                                    JsonObject dp = datapointsArray.get(i).getAsJsonObject();

                                    dataPoints.add(new DataPoint(
                                            dp.get("id").getAsString(),
                                            dp.get("label").getAsString(),
                                            dp.has("metadata") ? dp.get("metadata").getAsString() : null,
                                            dp.has("value") ? dp.get("value").getAsString() : null));
                                }
                            }

                            LOG.debug("Successfully loaded {} data points from DAM", dataPoints.size());
                        }
                    }
                } else {
                    LOG.warn("Asset not found or invalid at path: {}", DAM_ASSET_PATH);
                }
            } else {
                LOG.warn("No resource found at DAM path: {}", DAM_ASSET_PATH);
            }
        } catch (Exception e) {
            LOG.error("Error reading JSON from DAM asset", e);
        }

        return dataPoints;
    }
}
