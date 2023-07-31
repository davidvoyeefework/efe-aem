package com.efe.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.efe.core.services.FeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import com.adobe.acs.commons.genericlists.GenericList;
import com.adobe.acs.commons.genericlists.GenericList.Item;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.EmptyDataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.commons.jcr.JcrConstants;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.ResourceUtil;

@Component(service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "efe/components/dropdown", methods = HttpConstants.METHOD_GET)
@ServiceDescription("EFE DropdownDataProviderServlet")
public class DropdownDataProviderServlet extends SlingSafeMethodsServlet {

	private static final String SPLIT_BY_PIPE = "\\|";

	private static final long serialVersionUID = 1L;

	@Reference
	private transient ResourceResolverFactory resolverFactory;

	@Reference
	private FeService feService;

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {

		try (ResourceResolver resourceResolver = ResourceUtil.getServiceResourceResolver(resolverFactory)) {
			Resource currentResource = req.getResource();
			String dropdownSelector = currentResource.getChild("datasource").getValueMap().get("type", String.class);
			getItemsList(req.getResourceResolver(), dropdownSelector, req);
		}
	}

	/**
	 * Gets the items list.
	 *
	 * @param resourceResolver the resource resolver
	 * @param selector         the selector
	 * @return the items list
	 */
	private void getItemsList(ResourceResolver resourceResolver, String selector, SlingHttpServletRequest req) {
		if ("dynamicvariables".equalsIgnoreCase(selector)) {
			String[] genericLists = feService.getDynamicVariableList();
			final List<Resource> fakeResourceList = new ArrayList<>();
			for (String genericList : genericLists) {
				if (StringUtils.isNotEmpty(genericList)) {
					String[] genericListArr = genericList.split(SPLIT_BY_PIPE);
					if(genericListArr.length == 3 && StringUtils.equalsIgnoreCase(genericListArr[2],"true")) {
						String genericListPath = genericListArr[0];
						req.setAttribute(DataSource.class.getName(), EmptyDataSource.instance());
						GenericList list = EFEUtil.getGenericList(resourceResolver, genericListPath);
						if (Objects.nonNull(list) && Objects.nonNull(list.getItems())) {
							getDynamicVariables(req.getResourceResolver(), list, fakeResourceList);
							DataSource ds = new SimpleDataSource(fakeResourceList.iterator());
							req.setAttribute(DataSource.class.getName(), ds);
						}
					}
				}
			}
		}
	}

	/**
	 * Gets the dynamic variables.
	 *
	 * @param resourceResolver the resource resolver
	 * @param list the list
	 * @param fakeResourceList the fake resource list
	 * @return the dynamic variables
	 */
	private void getDynamicVariables(ResourceResolver resourceResolver, GenericList list,
			final List<Resource> fakeResourceList) {
		for (Item item : list.getItems()) {
			ValueMap vm = new ValueMapDecorator(new HashMap<>());
			
			if(StringUtils.isEmpty(item.getValue())){
				return;
			}		
			vm.put("value", item.getValue().split(SPLIT_BY_PIPE)[0]);
			vm.put("text", item.getTitle());
			fakeResourceList.add(new ValueMapResource(resourceResolver, new ResourceMetadata(),
					JcrConstants.NT_UNSTRUCTURED, vm));
		}
	}
}
