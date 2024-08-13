package com.efe.core.servlets;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.models.factory.ModelFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/contentfragment/variations",
                "sling.servlet.methods=GET"
        })
public class ContentFragmentVariationsServlet extends SlingAllMethodsServlet {

    @Reference
    private ModelFactory modelFactory;

    private List<String> variations;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String path = request.getParameter("path");
        variations = new ArrayList<>();
        Resource resource = request.getResourceResolver().getResource(path+ "/jcr:content/data");

        if (resource != null) {
            Iterable<Resource> variationsIterable = resource.getChildren();
            // Fetch all variations of the Content Fragment
            for (Resource variation : variationsIterable) {
                variations.add(variation.getName());
            }
            Map<String, Object> result = new HashMap<>();
            result.put("variations", variations);

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(result));
        } else {
            response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid content fragment path or no variations found.\"}");
        }
    }
}