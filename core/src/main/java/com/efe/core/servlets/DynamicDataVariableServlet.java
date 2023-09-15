package com.efe.core.servlets;

import com.adobe.acs.commons.genericlists.GenericList;
import com.efe.core.services.FeService;
import com.efe.core.utils.EFEUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import java.io.IOException;

/**
 * The Class DynamicDataVariableServlet.
 */
@Component(service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "efe/fe-components/structure/fe-page", methods = HttpConstants.METHOD_GET, extensions="json", selectors="dynamic")
@ServiceDescription("FE DynamicDataVariableServlet")
public class DynamicDataVariableServlet extends SlingSafeMethodsServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The resolver factory. */
	@Reference
	private transient ResourceResolverFactory resolverFactory;

	/** The feService. */
	@Reference
	private transient FeService feService;

	/**
	 * The array.
	 */
	private JsonArray array;

	/**
	 * The dynamic variables.
	 */
	private String dynamicVariables;

	/**
	 * Do get.
	 *
	 * @param req  the request
	 * @param resp the response
	 */
	@Override
	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException {
		setDynamicVariablesField(resp);
	}

	/**
	 * Sets the dynamic variables field.
	 *
	 * @param resp the response
	 */
	private void setDynamicVariablesField(SlingHttpServletResponse resp) throws IOException {
		String[] genericLists = feService.getDynamicVariableList();

		if (null == genericLists) {
			return;
		}
		array = new JsonArray();
		for (String genericList : genericLists) {
			if (StringUtils.isNotEmpty(genericList)) {
				String[] genericListArr = genericList.split("\\|");
				if (genericListArr.length == 2) {
					String genericListPath = genericListArr[0];
					GenericList list = EFEUtil.getGenericList(resolverFactory, genericListPath);

					if (null == list) {
						return;
					}
					setDynamicVariable(list, resp);
				}
			}
		}
	}

	/**
	 * Sets the dynamic variable.
	 *
	 * @param list the list
	 * @param resp the response
	 */
	private void setDynamicVariable(GenericList list, SlingHttpServletResponse resp) throws IOException {
		for (GenericList.Item item : list.getItems()) {
			JsonObject dynamicVariable = new JsonObject();
			String[] values = item.getValue().split("\\|");
			if (values.length == 2) {
				dynamicVariable.addProperty(values[0], values[1]);
			}
			array.add(dynamicVariable);
		}
		dynamicVariables = new Gson().toJson(array);
		resp.setContentType("application/json");
		resp.getWriter().write(dynamicVariables);
	}
}
