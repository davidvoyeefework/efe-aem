package com.efe.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.efe.core.models.Footer;
import com.efe.core.models.multifield.Link;
import com.efe.core.models.multifield.SocialLink;
import com.efe.core.models.multifield.VerticalList;
import com.efe.core.services.SeoService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;
import com.efe.core.utils.SeoUtil;

/**
 * The Class FooterImpl.
 */
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = Footer.class, resourceType = {
		FooterImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FooterImpl implements Footer {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/footer";

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;
	
	/** The request. */
	@SlingObject
	private SlingHttpServletRequest request;

	/** The externalizer. */
	@OSGiService
	private Externalizer externalizer;
	
	/** The seo service. */
	@OSGiService
	private SeoService seoService;
	
	/**
	 * The current resource.
	 */
	@SlingObject
	private Resource resource;
	
	/** The current page. */
	@Inject
	private Page currentPage;    

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
	@ChildResource
	private List<SocialLink> socialLinks;

	/** The link target. */
	@ValueMapValue
	private String linkTarget;

	/** The footer text list. */
	@ChildResource
	private List<Link> horizontalList;

	/** The vertical list. */
	@ChildResource
	private List<VerticalList> verticalList;
	
	/** The json ld. */
	private String jsonLd;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	protected void init() {
		
		if (null != currentPage) {
			boolean showSeo = currentPage.getProperties().get("showOrgSeoSchema", false);
			if (showSeo) {
				jsonLd = SeoUtil.getOrgSEO(externalizer, request, resourceResolver, seoService, socialLinks);
			}
		}
	}
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
	 * Gets the json ld.
	 *
	 * @return the json ld
	 */
	@Override
	public String getJsonLd() {
		return jsonLd;
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
