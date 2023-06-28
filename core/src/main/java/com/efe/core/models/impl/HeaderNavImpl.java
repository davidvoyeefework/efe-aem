package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.HeaderNav;
import com.efe.core.models.multifield.NavigationList;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The Class Header Nav Model Impl.
 */
@Model(adaptables = Resource.class, adapters = HeaderNav.class, resourceType = {
		HeaderNavImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class HeaderNavImpl implements HeaderNav {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/header-nav";

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/**
	 * The current resource.
	 */
	@Self
	private Resource resource;

	/** The navigation list. */
	@Inject
	private List<NavigationList> primaryList;
	/**
	 * Gets the Primary navigation list.
	 *
	 * @return the Primary navigation list
	 */
	public List<NavigationList> getPrimaryList() {
		if (Objects.nonNull(primaryList)) {
			return new ArrayList<>(primaryList);
		}
		return Collections.emptyList();
	}

}
