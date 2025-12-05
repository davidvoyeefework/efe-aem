package com.efe.core.servlets;

import org.apache.commons.io.IOUtils;
import org.osgi.service.component.annotations.Component;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Component(service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/statichtmlfile",
                "sling.servlet.methods=GET"
        })
public class CustomDownloadServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String assetPath = "/content/dam/efe/employer/sureroute-test-object.html"; // Adjust the path as needed
        assetPath = assetPath + "/jcr:content/renditions/original/jcr:content";
        Resource resource = request.getResourceResolver().getResource(assetPath);

        if (resource != null) {
            Node svgNode = resource.adaptTo(Node.class);

            InputStream svgInputStream = null;
            try {
                svgInputStream = svgNode.getProperty("jcr:data").getBinary().getStream();
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }

            BufferedInputStream bufInputStream = new BufferedInputStream(svgInputStream);
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

            int readInt = bufInputStream.read();
            while (readInt != -1) {
                byte byteData = (byte) readInt;
                byteOutputStream.write(byteData);
                readInt = bufInputStream.read();
            }

            String svgJcrData = byteOutputStream.toString();

            response.setHeader("Content-Disposition", "inline; filename=\"yourfile.html\"");
            response.setContentType("text/html");
            response.getWriter().println(svgJcrData);

        }


        // Set Content-Disposition header


    }
}

