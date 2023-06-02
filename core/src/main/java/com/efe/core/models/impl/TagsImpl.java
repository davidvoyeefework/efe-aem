package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.efe.core.bean.LinkBean;
import com.efe.core.models.Tags;
import com.efe.core.utils.EFEUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * The Class TagsImpl.
 */
@Model(
    adaptables = { SlingHttpServletRequest.class },
    adapters = Tags.class,
    resourceType = { TagsImpl.RESOURCE_TYPE },
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class TagsImpl implements Tags {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "efe/components/tags";
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TagsImpl.class);
    /** The links. */
    private List<LinkBean> links;

    /** The current resource. */
    @SlingObject
    private Resource resource;

    @SlingObject
    private ResourceResolver resolver;
    /** The currentPage Object. */
    @ScriptVariable
    private Page currentPage;
    /** the id *. */
    @ValueMapValue
    private String id;

    /** the tags *. */
    @ValueMapValue
    private String[] tags;

    /** the mappedPage *. */
    @ValueMapValue
    private String mappedPage;

    @Self
    private SlingHttpServletRequest request;

    /**
     * Inits the model.
     */
    @PostConstruct
    public void init() {

        ResourceResolver resourceResolver = request.getResourceResolver();
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

        Tag[] pageTags = currentPage.getTags();
        if (null == pageTags || pageTags.length < 1) {
            return;
        }
        List<String> hideTagsList = new ArrayList<>();
        if (null != tags) {
            hideTagsList = Arrays.asList(tags);
        }

        links = new ArrayList<>();
        for (Tag pageTag : pageTags) {
            if (!hideTagsList.isEmpty() && hideTagsList.contains(pageTag.getTagID())) {
                continue;
            }
            LinkBean linkBean = new LinkBean();
            linkBean.setTagLabel(pageTag.getTitle());

            linkBean.setTagLink(EFEUtil.getTagLink(pageTag, pageManager, mappedPage, resolver));
            links.add(linkBean);
        }

    }

    /**
     * Gets the links.
     *
     * @return the links
     */
    @Override
    public List<LinkBean> getTagList() {

        if (Objects.nonNull(links)) {
            return new ArrayList<>(links);
        }
        return Collections.emptyList();

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

    /**
     * Checks if is empty.
     *
     * @return the empty
     */
    @Override
    public boolean isEmpty() {
        boolean isEmpty = true;
        if (currentPage.getTags().length > 0) {
            isEmpty = false;
        }
        return isEmpty;
    }

}
