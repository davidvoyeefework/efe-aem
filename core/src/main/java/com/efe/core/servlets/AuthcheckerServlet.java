package com.efe.core.servlets;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = { Servlet.class })
@SlingServletPaths(value = "/bin/permissioncheck")
@ServiceDescription("EFE Authchecker Servlet")
public class AuthcheckerServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = -5239715848179225012L;

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(AuthcheckerServlet.class);

	@Override
	public void doHead(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		// retrieve the requested URL
		String uri = request.getParameter("uri");
		
		LOG.info("authchecker called : URI : {}", uri);
		
		// obtain the session from the request
		Session session = request.getResourceResolver().adaptTo(Session.class);
		// perform the permissions check
		try {
			
			if(uri.contains("efe/all-category-cards")) {
				session.checkPermission("/content/dam/efe/cf/wealth-planning/disclosures", Session.ACTION_READ);
				session.checkPermission("/content/dam/efe/cf/employee-planning/disclosures", Session.ACTION_READ);
			}else {
				session.checkPermission(uri, Session.ACTION_READ);
			}
			LOG.info("authchecker says OK");
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			LOG.info("authchecker says READ access DENIED!");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}

	}
}
