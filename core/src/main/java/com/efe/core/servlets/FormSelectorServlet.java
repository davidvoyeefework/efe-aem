package com.efe.core.servlets;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.efe.core.models.FormsSelector;
import com.efe.core.services.EfeService;
import com.efe.core.services.RestService;
import com.google.gson.JsonObject;

/**
 * The Class EfeFormServlet.
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=EFE - Forms selector servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.resourceTypes=" + "efe/components/formselector", "sling.servlet.extensions=" + "json" })
public class FormSelectorServlet extends SlingAllMethodsServlet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1076866032440054729L;

	/** The rest service. */
	@Reference
	private transient RestService restService;

	/** The efe service. */
	@Reference
	private transient EfeService efeService;

	/**
	 * Do get.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		FormsSelector formsSelector = request.adaptTo(FormsSelector.class);
		if (Objects.nonNull(formsSelector)) {
			response.setContentType("application/json");
			String formAuthHeader = efeService.getFormAuthHeader();
			String formUrl = formsSelector.getFormUrl();
			JsonObject responseObj = new JsonObject();
			
			if (Objects.nonNull(formAuthHeader) && Objects.nonNull(formUrl)) {
				String formResponse = restService.getData(formUrl, formAuthHeader);
				responseObj.addProperty("status", HttpServletResponse.SC_ACCEPTED);
				responseObj.addProperty("htmlResponse", formResponse);
				responseObj.addProperty("scriptUrl", formsSelector.getFormJsUrl());
				response.getWriter().write(responseObj.toString());
			}else {
				responseObj.addProperty("status", HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write(responseObj.toString());
			}
		}
	}
}
