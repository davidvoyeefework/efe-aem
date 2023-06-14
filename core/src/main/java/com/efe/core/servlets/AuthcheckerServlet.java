package com.efe.core.servlets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.efe.core.utils.ResourceUtil;

/**
 * The Class AuthcheckerServlet.
 */
@Component(service = { Servlet.class })
@SlingServletPaths(value = "/bin/permissioncheck")
@ServiceDescription("EFE Authchecker Servlet")
public class AuthcheckerServlet extends SlingSafeMethodsServlet {

	/** The Constant EMPLOYEE_HEADER_DETAILS. */
	private static final String EMPLOYEE_HEADER_DETAILS = "Employee-Header-Details";

	/** The Constant EMPLOYEE_CATEGORY_CARDS. */
	private static final String EMPLOYEE_CATEGORY_CARDS = "Employee-Category-Cards";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5239715848179225012L;

	/** The resolver factory. */
	@Reference
	private transient ResourceResolverFactory resolverFactory;

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(AuthcheckerServlet.class);

	/**
	 * Do head.
	 *
	 * @param request  the request
	 * @param response the response
	 */
	@Override
	public void doHead(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		// retrieve the requested URL
		String uri = request.getParameter("uri");

		Map<String, String> pathMap = new HashMap<>();

		try (ResourceResolver resourceResolver = ResourceUtil.getServiceResourceResolver(resolverFactory)) {
			Resource genericList = resourceResolver
					.getResource("/etc/acs-commons/lists/efe/momemtum-paths-list/jcr:content/list");

			if (Objects.nonNull(genericList) && genericList.hasChildren()) {
				Iterator<Resource> childrenItr = genericList.listChildren();
				while (childrenItr.hasNext()) {
					final Resource link = childrenItr.next();
					final String paths = link.getValueMap().get("value", StringUtils.EMPTY);
					final String type = link.getValueMap().get(JcrConstants.JCR_TITLE, StringUtils.EMPTY);
					if (StringUtils.isNotEmpty(paths) && StringUtils.isNotEmpty(type)) {
						pathMap.put(type, paths);
					}

				}
			}
		}
		
		if (pathMap.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		checkAndHandlePermissions(request, response, uri, pathMap);
	}

	/**
	 * Check and handle permissions.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param uri      the uri
	 * @param pathMap  the path map
	 */
	private void checkAndHandlePermissions(SlingHttpServletRequest request, SlingHttpServletResponse response,
			String uri, Map<String, String> pathMap) {
		LOG.info("authchecker called : URI : {}", uri);

		// obtain the session from the request
		Session session = request.getResourceResolver().adaptTo(Session.class);
		// perform the permissions check
		try {
			if (uri.contains("efe/all-category-cards") && pathMap.containsKey(EMPLOYEE_CATEGORY_CARDS)) {
				checkAccess(pathMap, session, EMPLOYEE_CATEGORY_CARDS);
				response.setStatus(HttpServletResponse.SC_OK);
				LOG.info("authchecker says OK");
			} else if (uri.contains("efe/all-header-details") && pathMap.containsKey(EMPLOYEE_HEADER_DETAILS)) {
				checkAccess(pathMap, session, EMPLOYEE_HEADER_DETAILS);
				response.setStatus(HttpServletResponse.SC_OK);
				LOG.info("authchecker says OK");
			} else {
				LOG.info("authchecker says READ access DENIED!");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}

		} catch (RepositoryException e) {
			LOG.info("authchecker says READ access DENIED!");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	/**
	 * Check access.
	 *
	 * @param pathMap the path map
	 * @param session the session
	 * @param key     the key
	 * @throws RepositoryException the repository exception
	 */
	private void checkAccess(Map<String, String> pathMap, Session session, String key) throws RepositoryException {
		String paths = pathMap.get(key);
		if (StringUtils.isNotEmpty(paths)) {
			for (String path : paths.split((";"))) {
				session.checkPermission(path.trim(), Session.ACTION_READ);
			}
		}
	}
}
