package com.efe.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.apache.sling.servlets.post.JSONResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		Resource formContainerResource = request.getResource();
		ValueMap valueMap = formContainerResource.adaptTo(ValueMap.class);
		if (valueMap != null) {
			String endPointUrl = valueMap.get(PN_FORM_ENDPOINT_URL, String.class);
			if (StringUtils.isNotEmpty(endPointUrl)) {
				String formJsonInput = getFormJsonInput(request);

				try (CloseableHttpClient client = HttpClients.createDefault()) {

					HttpPost post = new HttpPost(endPointUrl);
					post.setHeader("content-type", "application/json");
					post.setEntity(new StringEntity(formJsonInput,
							ContentType.create(JSONResponse.RESPONSE_CONTENT_TYPE, "UTF-8")));

					String resp = client.execute(post, new BasicResponseHandler());

					LOG.info("Response : {}", resp);

				} catch (IOException e) {
					LOG.info(e.getMessage());
				}

			}

		}
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
