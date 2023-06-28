package com.efe.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.NonExistingResource;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.components.ComponentContext;
import com.day.cq.wcm.foundation.forms.FormsHandlingRequest;
import com.day.cq.wcm.foundation.forms.FormsHandlingServletHelper;
import com.day.cq.wcm.foundation.forms.ValidationInfo;
import com.efe.core.services.FormHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The Class EfeFormServlet.
 */
@Component(service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "efe/components/form/actions/rpc", methods = HttpConstants.METHOD_POST, selectors = "post")
@ServiceDescription("EFE Forms Servlet")
public class EfeFormServlet extends SlingAllMethodsServlet {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(EfeFormServlet.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant PN_FORM_ENDPOINT_URL. */
	private static final String PN_FORM_ENDPOINT_URL = "efeExternalServiceEndPointUrl";

	/** The Constant IGNORE_KEYS. */
	private static final List<String> IGNORE_KEYS = new ArrayList<>(
			Arrays.asList(":formstart", "_charset_", ":redirect", ":cq_csrf_token"));

	/** The Constant ATTR_RESOURCE. */
	private static final String ATTR_RESOURCE = FormsHandlingServletHelper.class.getName() + "/resource";
	
	/** The Constant HTML_SUFFIX. */
	private static final String HTML_SUFFIX = ".html";

	
	/** The form handler. */
	@Reference
	private transient FormHandler formHandler;

	/**
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {
		boolean processFormApiSuccess = false;
		Resource formContainerResource = request.getResource();
		ValueMap valueMap = formContainerResource.adaptTo(ValueMap.class);
		if (valueMap != null) {
			String endPointUrl = valueMap.get(PN_FORM_ENDPOINT_URL, String.class);
			if (StringUtils.isNotEmpty(endPointUrl)) {
				String formJsonInput = getFormJsonInput(request);
				processFormApiSuccess = formHandler.forwardFormData(endPointUrl, formJsonInput);
			}
			sendRedirect(valueMap, request, response, processFormApiSuccess);
		}
	}

	/**
	 * Send redirect.
	 *
	 * @param valueMap the value map
	 * @param request the request
	 * @param response the response
	 * @param processFormApiSuccess the process form api success
	 * @throws ServletException the servlet exception
	 */
	private void sendRedirect(ValueMap valueMap, SlingHttpServletRequest request, SlingHttpServletResponse response,
			boolean processFormApiSuccess) throws ServletException {
		
		String formName = valueMap.get("name", valueMap.get("id", String.class));
		String redirect = getMappedRedirect(formName, valueMap.get("redirect", String.class), request.getResourceResolver());
		String errorMessage = valueMap.get("errorMessage", String.class);
		FormsHandlingRequest formRequest = new FormsHandlingRequest(request);
		try {
			if (StringUtils.isNotEmpty(redirect) && processFormApiSuccess) {
				response.sendRedirect(redirect);
			} else {
				if (!processFormApiSuccess && StringUtils.isNotEmpty(errorMessage)) {
					ValidationInfo validationInfo = ValidationInfo.createValidationInfo(request);
					validationInfo.addErrorMessage(null, errorMessage);
				}
				final Resource formResource = (Resource) request.getAttribute(ATTR_RESOURCE);
				request.removeAttribute(ATTR_RESOURCE);
				request.removeAttribute(ComponentContext.BYPASS_COMPONENT_HANDLING_ON_INCLUDE_ATTRIBUTE);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(formResource);
				if (requestDispatcher != null) {
					requestDispatcher.forward(formRequest, response);
				} else {
					throw new IOException("can't get request dispatcher to forward the response");
				}
			}
		} catch (IOException var13) {
			LOG.error("Error redirecting to {}", redirect);
		}
	}
	
    /**
     * Gets the mapped redirect.
     *
     * @param redirect the redirect
     * @param resourceResolver the resource resolver
     * @return the mapped redirect
     */
    private String getMappedRedirect(String formName, String redirect, ResourceResolver resourceResolver) {
        String mappedRedirect = null;
        if (StringUtils.isNotEmpty(redirect)) {
            if (StringUtils.endsWith(redirect, HTML_SUFFIX)) {
                Resource resource = resourceResolver.resolve(redirect);
                if (!(resource instanceof NonExistingResource)) {
                    mappedRedirect = redirect;
                }
            } else {
                Resource resource = resourceResolver.getResource(redirect);
                if (resource != null) {
                    redirect = redirect.concat(".formevent.").concat(formName).concat(HTML_SUFFIX);
                    mappedRedirect = resourceResolver.map(redirect);
                }
            }
        }
        return mappedRedirect;
    }

	/**
	 * Gets the form json input.
	 *
	 * @param request the request
	 * @return the form json input
	 */
	private String getFormJsonInput(final SlingHttpServletRequest request) {
		Gson gson = new GsonBuilder().create();
		// Convert the Java Map to a Google JSON object
		JsonObject jsonObject = new JsonObject();
		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {

			if (!IGNORE_KEYS.contains(entry.getKey())) {
				String[] values = entry.getValue();

				if (values.length > 1) {
					JsonArray jsonArray = new JsonArray();
					for (String item : values) {
						jsonArray.add(item);
					}
					jsonObject.add(entry.getKey(), jsonArray);
				} else if (values.length == 1) {
					jsonObject.addProperty(entry.getKey(), entry.getValue()[0]);
				}
			}
		}
		return gson.toJsonTree(jsonObject).toString();
	}
}
