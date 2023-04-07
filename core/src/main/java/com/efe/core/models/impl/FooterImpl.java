package com.efe.core.models.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

import com.efe.core.models.Footer;
import com.efe.core.models.multifield.Link;
import com.efe.core.models.multifield.SocialLink;
import com.efe.core.models.multifield.VerticalList;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;
import java.util.Objects;
import java.util.Collections;
import java.util.ArrayList;

/**
 * The Class FooterImpl.
 */
@Model(adaptables = Resource.class, adapters = Footer.class, resourceType = {
		FooterImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FooterImpl implements Footer {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/footer";

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/**
	 * The current resource.
	 */
	@Self
	private Resource resource;

	/** The id. */
	@ValueMapValue
	private String id;

	/** The file reference. */
	@ValueMapValue
	private String fileReference;

	/** The alt. */
	@ValueMapValue
	private String alt;

	/** The logo link. */
	@ValueMapValue
	private String logoLink;

	/** The logo target. */
	@ValueMapValue
	private String logoTarget;

	/** The social links. */
	@Inject
	private List<SocialLink> socialLinks;

	/** The link target. */
	@ValueMapValue
	private String linkTarget;

	/** The footer text list. */
	@Inject
	private List<Link> horizontalList;

	/** The vertical list. */
	@Inject
	private List<VerticalList> verticalList;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		if (id == null) {
			id = EFEUtil.getId(resource);
		}
		return id;
	}

	/**
	 * Gets the file reference.
	 *
	 * @return the file reference
	 */
	public String getFileReference() {
		return fileReference;
	}

	/**
	 * Gets the alt.
	 *
	 * @return the alt
	 */
	public String getAlt() {
		return alt;
	}

	/**
	 * Gets the logo link.
	 *
	 * @return the logo link
	 */
	public String getLogoLink() {
		return LinkUtil.getFormattedLink(logoLink, resourceResolver);
	}

	/**
	 * Gets the logo target.
	 *
	 * @return the logo target
	 */
	public String getLogoTarget() {
		return logoTarget;
	}

	/**
	 * Gets the social links.
	 *
	 * @return the social links
	 */
	public List<SocialLink> getSocialLinks() {
		if (Objects.nonNull(socialLinks)) {
			return new ArrayList<>(socialLinks);
		}
		return Collections.emptyList();
	}

	/**
	 * Gets the link target.
	 *
	 * @return the link target
	 */
	public String getLinkTarget() {
		return linkTarget;
	}

	/**
	 * Gets the horizontal list.
	 *
	 * @return the horizontal list
	 */
	public List<Link> getHorizontalList() {
		if (Objects.nonNull(horizontalList)) {
			return new ArrayList<>(horizontalList);
		}
		return Collections.emptyList();
	}

	/**
	 * Gets the vertical list.
	 *
	 * @return the vertical list
	 */
	public List<VerticalList> getVerticalList() {
		if (Objects.nonNull(verticalList)) {
			return new ArrayList<>(verticalList);
		}
		return Collections.emptyList();
	}

}
