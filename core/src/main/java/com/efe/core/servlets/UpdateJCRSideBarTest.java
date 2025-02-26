package com.efe.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import java.io.IOException;
import javax.servlet.Servlet;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.methods=GET",
                "sling.servlet.paths=/bin/updateAccessibilityLabel"
        }
)
public class UpdateJCRSideBarTest extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        ResourceResolver resourceResolver = request.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);

        if (session == null) {
            response.getWriter().write("Unable to obtain JCR session.");
            return;
        }

        // Corrected SQL2 Query String
        String queryString =
                "SELECT * FROM [nt:base] AS node " +
                        "WHERE ISDESCENDANTNODE(node, '/content/efe/us/en/education/') " +
                        "AND NAME(node) = 'articledetails' " +
                        "AND node.sidebar IS NULL";

        try {
            QueryManager queryManager = session.getWorkspace().getQueryManager();
            Query query = queryManager.createQuery(queryString, Query.JCR_SQL2);
            QueryResult result = query.execute();

            javax.jcr.NodeIterator nodes = result.getNodes();
            int updatedCount = 0;

            while (nodes.hasNext()) {
                Node node = nodes.nextNode();
                node.setProperty("sidebar", "no");
                updatedCount++;

            }

            session.save();
            response.getWriter().write("Updated " + updatedCount + " nodes successfully.");
        } catch (RepositoryException e) {
            response.getWriter().write("Error updating nodes: " + e.getMessage());
        }
    }
}
 