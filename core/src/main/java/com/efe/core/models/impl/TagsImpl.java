package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.efe.core.bean.LinkBean;
import com.efe.core.models.Tags;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private final List<LinkBean> linkBeanList = new ArrayList<>();

    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

    @ScriptVariable
    private Page currentPage;
    /** the id *. */
    @ValueMapValue
    private String id;

    /** the tags *. */
    @ValueMapValue
    private String[] tags;

    /**
     * @return linkBeanList
     */
    @Override
    public List<LinkBean> getTagList() {
        Tag[] pageTags = currentPage.getTags();
        List<String> hideTagsList = Arrays.asList(tags);
        List<String> links = new ArrayList<>();

        if (pageTags != null) {
            for (Tag pageTag : pageTags) {
                if (hideTagsList.contains(pageTag.getTagID())) {
                    continue;
                }
                links.add(pageTag.getTitle());
            }

            for (String tag : links) {
                LinkBean linkBean = new LinkBean();
                if (tag != null) {
                    linkBean.setTagLabel(tag);
                    linkBeanList.add(linkBean);
                }
            }
        } else {
            LOGGER.error("Tags are not available : {}", pageTags.length);
        }

        return linkBeanList;
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
     * Gets the tags
     *
     * @return tags
     */
    public String[] getTags() {
        return tags;
    }
}
