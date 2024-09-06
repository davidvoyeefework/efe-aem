package com.efe.core.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.engine.EngineConstants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.*;
import java.io.IOException;
import java.io.StringWriter;

@Component(
        service = javax.servlet.Filter.class,
        property = {
                EngineConstants.SLING_FILTER_SCOPE + "=" + "request",
                "sling.filter.pattern=/.*\\.model.json"
        }
)
public class CustomJsonFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomJsonFilter.class);
    public static final String POWERED_BY_IMAGE = "poweredByImage";
    public static final String POWERED_BY_TEXT = "poweredByText";
    public static final String POWERED_BY_ALT_TEXT = "poweredByAltText";
    public static final String PASS_THROUGH_STATE = "passThroughState";
    public static final String DESTINATION = "destination";
    public static final String LABEL = "label";
    public static final String ALT_TEXT = "altText";
    public static final String ALERT_IMAGE = "alertImage";
    public static final String IMAGE_CODE = "imageCode";
    public static final String IMAGE_URL = "imageUrl";
    public static final String ROOT = "root";
    public static final String TYPE = ":type";
    public static final String RECORD = "record";
    public static final String ITEMS = ":items";
    public static final String WPI_JSON_ALERT = "wpi_json_alert";
    public static final String CALL_TO_ACTION = "callToAction";
    public static final String POWERED_BY = "poweredBy";
    public static final String CONTENT = "content";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialize the filter if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        StringWriter stringWriter = new StringWriter();
        // Intercept the response and wrap it
        SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
        SlingHttpServletResponse slingResponse = (SlingHttpServletResponse) response;
        // if page has "wpi-json-alert" component then only go further and change model.json
        if (containsWpiJsonAlertComponent(slingRequest)) {
            LOGGER.info("wpi json alert component is present on this page");
            CustomResponseWrapper responseWrapper = new CustomResponseWrapper(slingResponse, stringWriter);

            // Continue with the rest of the filter chain
            chain.doFilter(slingRequest, responseWrapper);

            // Get the JSON string from the response
            String originalJson = stringWriter.toString();

            // Modify the JSON
            String modifiedJson = modifyJsonString(originalJson, slingRequest);

            // Write the modified JSON back to the response
            response.getWriter().write(modifiedJson);
        } else {
            LOGGER.info("wpi json alert component is not present on this page");
            // If the component is not found, continue without modifying the response
            chain.doFilter(slingRequest, slingResponse);
        }
    }

    private boolean containsWpiJsonAlertComponent(SlingHttpServletRequest request) {

        Resource resource = request.getResource();

        if (resource == null) {
            return false;
        }

        String queryString = "SELECT * FROM [nt:unstructured] AS s WHERE ISDESCENDANTNODE([" + resource.getPath() + "]) " +
                "AND s.[sling:resourceType] = 'efe/fe-components/content/wpi-json-alert'";
        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);
            if (session != null) {
                QueryManager queryManager = session.getWorkspace().getQueryManager();
                Query query = queryManager.createQuery(queryString, Query.JCR_SQL2);
                QueryResult result = query.execute();

                // Check if any nodes are returned by the query
                return result.getNodes().hasNext();
            }
        } catch (Exception e) {
            // Log the exception (use appropriate logger)
            LOGGER.error("error is fetching results via query: {}", e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void destroy() {
        // Clean up the filter if needed
    }

    // Utility method to modify the JSON string
    private String modifyJsonString(String json, SlingHttpServletRequest slingRequest) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);

        // Recursively modify the JSON tree
        return modifyTypeKey(rootNode, slingRequest);
    }

    private String modifyTypeKey(JsonNode node, SlingHttpServletRequest slingRequest) throws JsonProcessingException {
        if (node.isObject()) {
            JsonNode itemsNode = node.path(ITEMS);
            if (itemsNode.isMissingNode() || !itemsNode.has(ROOT)) {
                // If ":items" or "root" is missing, return an empty JSON object
                return "{}";
            }
            JsonNode rootNodeItems = itemsNode.path(ROOT);
            ObjectNode rootNodeItemsObject = (ObjectNode) rootNodeItems.path(ITEMS);
            JsonNode wpiAlertNode = rootNodeItemsObject.path(WPI_JSON_ALERT);
            if (wpiAlertNode.isObject()) {
                return appendEverythingToContentNode(wpiAlertNode, rootNodeItemsObject, slingRequest);
            }

            // Return an empty JSON object if "wpi_json_alert" was not found
            return "{}";
        }
        return "{}";
    }

    private String appendEverythingToContentNode(JsonNode wpiAlertNode, ObjectNode rootNodeItemsObject, SlingHttpServletRequest slingRequest) throws JsonProcessingException {
        // Create a new root with just "wpi_json_alert"
        ObjectNode newRootNode = objectMapper.createObjectNode();

        // Create a new object for "content"
        ObjectNode contentNode = objectMapper.createObjectNode();
        contentNode.setAll((ObjectNode) wpiAlertNode);

        ObjectNode alertImageNode = objectMapper.createObjectNode();
        if (wpiAlertNode.has(IMAGE_URL)) {
            alertImageNode.set(IMAGE_URL, wpiAlertNode.get(IMAGE_URL));
            contentNode.remove(IMAGE_URL);
        }
        if (wpiAlertNode.has(IMAGE_CODE)) {
            alertImageNode.set(IMAGE_CODE, wpiAlertNode.get(IMAGE_CODE));
            contentNode.remove(IMAGE_CODE);
        }
        if (wpiAlertNode.has(ALT_TEXT)) {
            alertImageNode.set(ALT_TEXT, wpiAlertNode.get(ALT_TEXT));
            contentNode.remove(ALT_TEXT);
        }
        contentNode.set(ALERT_IMAGE, alertImageNode);

        ObjectNode callToActionNode = objectMapper.createObjectNode();
        if (wpiAlertNode.has(LABEL)) {
            callToActionNode.set(LABEL, wpiAlertNode.get(LABEL));
            contentNode.remove(LABEL);
        }
        if (wpiAlertNode.has(DESTINATION)) {
            callToActionNode.set(PASS_THROUGH_STATE, wpiAlertNode.get(DESTINATION));
            contentNode.remove(DESTINATION);
        }
        contentNode.set(CALL_TO_ACTION, callToActionNode);

        ObjectNode poweredByNode = objectMapper.createObjectNode();
        if (wpiAlertNode.has(POWERED_BY_TEXT)) {
            poweredByNode.set(POWERED_BY_TEXT, wpiAlertNode.get(POWERED_BY_TEXT));
            contentNode.remove(POWERED_BY_TEXT);
        }
        if (wpiAlertNode.has(POWERED_BY_IMAGE) || wpiAlertNode.has(ALT_TEXT)) {
            ObjectNode poweredByImage = objectMapper.createObjectNode();
            if (wpiAlertNode.has(POWERED_BY_IMAGE))
                poweredByImage.set(IMAGE_URL, wpiAlertNode.get(POWERED_BY_IMAGE));
            if (wpiAlertNode.has(ALT_TEXT)) {
                poweredByImage.set(ALT_TEXT, wpiAlertNode.get(POWERED_BY_ALT_TEXT));
                contentNode.remove(POWERED_BY_ALT_TEXT);
            }
            poweredByNode.set(POWERED_BY_IMAGE, poweredByImage);
            contentNode.remove(POWERED_BY_IMAGE);
        }
        contentNode.set(POWERED_BY, poweredByNode);

        if (wpiAlertNode.has(RECORD)) {
            contentNode.remove(RECORD);
        }
        if (wpiAlertNode.has(TYPE)) {
            contentNode.remove(TYPE);
        }

        ResourceResolver resolver = slingRequest.getResourceResolver();
        fetchCFValues(wpiAlertNode, "title", "titleCFVariations", resolver, contentNode);
        fetchCFValues(wpiAlertNode, "subtitle", "subtitleCFVariations", resolver, contentNode);
        fetchCFValues(wpiAlertNode, "header1", "header1CFVariations", resolver, contentNode);
        fetchCFValues(wpiAlertNode, "header2", "header2CFVariations", resolver, contentNode);
        fetchCFValues(wpiAlertNode, "body1", "body1CFVariations", resolver, contentNode);
        fetchCFValues(wpiAlertNode, "body2", "body2CFVariations", resolver, contentNode);
        fetchCFValues(wpiAlertNode, "footer", "footerCFVariations", resolver, contentNode);

        // Set "content" object in the new root node
        newRootNode.set(CONTENT, contentNode);
        // Remove "wpi_json_alert" node from its parent
        rootNodeItemsObject.remove(WPI_JSON_ALERT);
        return objectMapper.writeValueAsString(newRootNode);
    }

    private static void fetchCFValues(JsonNode wpiAlertNode, String contentFragmentPath, String contentFragmentVariationName, ResourceResolver resolver, ObjectNode contentNode) {
        if (wpiAlertNode.has(contentFragmentPath) && wpiAlertNode.has(contentFragmentVariationName)) {
            Resource contentFragmentResource = resolver.getResource(wpiAlertNode.get(contentFragmentPath).asText() + "/jcr:content/data/" + wpiAlertNode.get(contentFragmentVariationName).asText());
            if (contentFragmentResource != null) {
                ValueMap resourceMap = contentFragmentResource.getValueMap();
                if (resourceMap.containsKey(CONTENT)) {
                    String cfValue = resourceMap.get(CONTENT, String.class);
                    contentNode.set(contentFragmentPath, TextNode.valueOf(html2text(cfValue)));
                }
            }
            contentNode.remove(contentFragmentVariationName);
        }
    }

    public static String html2text(String html) {
        return html.replaceAll("\\<.*?>", "").replaceAll("\n", "").replaceAll("\t", "");
    }
}