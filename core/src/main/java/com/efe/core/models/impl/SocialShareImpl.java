package com.efe.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.SocialShare;
import com.efe.core.models.multifield.SocialMediaSharing;
import com.efe.core.utils.EFEUtil;

/**
 * The Class SocialShareImpl.
 */
@Model(adaptables = Resource.class, adapters = SocialShare.class, resourceType = {
		SocialShareImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class SocialShareImpl implements SocialShare {
	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/socialshare";

	/** The social share links. */
	@Inject
	private List<SocialMediaSharing> socialMediaSharing;

	/**
	 * The current resource.
	 */
	@Self
	private Resource resource;

	/** The link target. */
	@ValueMapValue
	private String linkTarget;

	/** The id. */
	@ValueMapValue
	private String id;

	/**
	 * Gets the social sharing links.
	 *
	 * @return the social sharing links
	 */
	public List<SocialMediaSharing> getSocialMediaSharing() {
		if (Objects.nonNull(socialMediaSharing)) {
			return new ArrayList<>(socialMediaSharing);
		}
		return Collections.emptyList();
	}

	/**
	 * Gets the link target.
	 *
	 * @return the link target
	 */
	@Override
	public String getLinkTarget() {
		return linkTarget;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public String getId() {
		if (id == null) {
			id = EFEUtil.getId(resource);
		}
		return id;
	}


}