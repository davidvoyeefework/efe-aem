package com.efe.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import com.efe.core.utils.ResourceUtil;


import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.factory.ModelFactory;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.commons.link.Link;
import com.adobe.cq.wcm.core.components.models.ListItem;
import com.adobe.cq.wcm.core.components.models.Teaser;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;
import com.adobe.xfa.ut.Resolver;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.efe.core.bean.LinkBean;
import com.efe.core.models.EFETeaser;

/**
 * The Class EFETeaserImpl.
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = EFETeaser.class, resourceType = {
		EFETeaserImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class EFETeaserImpl implements EFETeaser {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "efe/components/relatedteaser";

	/** The model factory. */
	@OSGiService
	private ModelFactory modelFactory;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	/** The core teaser. */
	private Teaser teaser;

	/** The article page tags. */
	List<LinkBean> articlePageTags;

	/** The article page subtitle. */
	String articleSubtitle;

	/** The article resource path. */
	String resourcePath;

	/** The article resource property. */
	String resourceProperty;

	/** The article CF subtitle */
	String resourceCFPath;

	/** The resourceCF Property subtitle */
	String resourceCFProperty;

	/** The resourceCF Property subtitle */
	String articleCFSubtitle;

	    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

	/**
	 * Inits the Model.
	 */
	@PostConstruct
	protected void init() {
		
		articlePageTags = new ArrayList<>();
		teaser = modelFactory.getModelFromWrappedRequest(request, request.getResource(), Teaser.class);
		Page page = (Page) teaser.getLink().getReference();

		if (null != page && page.getTags() != null) {
			for (Tag pageTag : page.getTags()) {
				LinkBean tagObj = new LinkBean();
				tagObj.setTagLabel(pageTag.getTitle());
				articlePageTags.add(tagObj);
			}
		}

			resourcePath =  page.getPath() + "/jcr:content/root/container_1735562516/container/articledetails";
			resourceProperty = "articleFragmentPath";
			articleSubtitle = ResourceUtil.getProperty( resourceResolver, resourcePath, resourceProperty );
			if (articleSubtitle != null) {
				resourceCFPath = articleSubtitle + "/jcr:content/data/master";
				resourceCFProperty = "subtitle";
				articleCFSubtitle = ResourceUtil.getProperty(resourceResolver, resourceCFPath, resourceCFProperty).replaceAll("<[^>]*>", "");
			}
			else {
				articleCFSubtitle = "";
			}
	}

	/**
	 * Gets the subtitle.
	 *
	 * @return the subtitle
	 */
	@Override
	public String getSubtitle() {
		return articleCFSubtitle;
	}

	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	@Override
	public List<LinkBean> getTags() {
		return Collections.unmodifiableList(articlePageTags);
	}

	/**
	 * Checks if is actions enabled.
	 *
	 * @return true, if is actions enabled
	 */
	@Override
	public boolean isActionsEnabled() {
		return teaser.isActionsEnabled();
	}

	/**
	 * Gets the actions.
	 *
	 * @return the actions
	 */
	@Override
	public List<ListItem> getActions() {

		return teaser.getActions();
	}

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	@Override
	public Link getLink() {
		return teaser.getLink();
	}

	/**
	 * Gets the image resource.
	 *
	 * @return the image resource
	 */
	@Override
	public Resource getImageResource() {
		return teaser.getImageResource();
	}

	/**
	 * Checks if is image link hidden.
	 *
	 * @return true, if is image link hidden
	 */
	@Override
	public boolean isImageLinkHidden() {
		return teaser.isImageLinkHidden();
	}

	/**
	 * Gets the pretitle.
	 *
	 * @return the pretitle
	 */
	@Override
	public String getPretitle() {
		return teaser.getPretitle();
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	@Override
	public String getTitle() {
		return teaser.getTitle();
	}

	/**
	 * Checks if is title link hidden.
	 *
	 * @return true, if is title link hidden
	 */
	@Override
	public boolean isTitleLinkHidden() {
		return teaser.isTitleLinkHidden();
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	@Override
	public String getDescription() {
		return teaser.getDescription();
	}

	/**
	 * Gets the title type.
	 *
	 * @return the title type
	 */
	@Override
	public String getTitleType() {
		return teaser.getTitleType();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public String getId() {
		return teaser.getId();
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	@Override
	public ComponentData getData() {
		return teaser.getData();
	}

	/**
	 * Gets the applied css classes.
	 *
	 * @return the applied css classes
	 */
	@Override
	public String getAppliedCssClasses() {
		return teaser.getAppliedCssClasses();
	}

	/**
	 * Gets the exported type.
	 *
	 * @return the exported type
	 */
	@Override
	public String getExportedType() {
		return teaser.getExportedType();
	}

}
