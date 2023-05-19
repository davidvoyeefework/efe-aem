package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.efe.core.bean.Articles;
import com.efe.core.models.ArticleDetails;
import com.efe.core.services.EfeService;
import com.efe.core.services.SeoService;
import com.efe.core.utils.ArticleDetailUtil;
import com.efe.core.utils.SeoUtil;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import javax.annotation.PostConstruct;

/**
 * The Class ArticleDetailsImpl.
 */
@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = com.efe.core.models.ArticleDetails.class, resourceType = {
				ArticleDetailsImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ArticleDetailsImpl implements ArticleDetails {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/components/articledetails";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDetailsImpl.class);

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The current resource. */
	@SlingObject
	private Resource resource;

	@OSGiService
	private SeoService seoService;

	@OSGiService
	private EfeService efeService;
	
	@OSGiService
	private Externalizer externalizer;

	@ScriptVariable
	private Page currentPage;

	/** The article fragment path. */
	@ValueMapValue
	private String articleFragmentPath;

	/** the id *. */
	@ValueMapValue
	private String id;

	/** ArticleDetail. */
	private Articles articleDetails;

	/** The json ld. */
	private String jsonLd;

	/**
	 * Inits the Model.
	 */
	@PostConstruct
	public void init() {
		articleDetails = new Articles();
		if (null != articleFragmentPath) {
			Resource articlesFragmentResource = resourceResolver.getResource(articleFragmentPath);
			if (null != articlesFragmentResource) {
				articleDetails = ArticleDetailUtil.getArticleDetails(articlesFragmentResource, resourceResolver);
				if (Objects.nonNull(articleDetails)) {
					jsonLd = SeoUtil.getArticleSchema(seoService, efeService, externalizer, resourceResolver, currentPage,
							articleDetails);
				}
			}
		} else {
			LOGGER.error("Article Content fragment not found : {}", articleFragmentPath);
		}

	}

	/**
	 * Gets the ArticleFragmentPath.
	 *
	 * @return the articleDetails
	 */
	@Override
	public Articles getArticleFragmentPath() {
		return articleDetails;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * Gets the json ld.
	 *
	 * @return the jsonLd
	 */
	@Override
	public String getJsonLd() {
		return jsonLd;
	}

}
