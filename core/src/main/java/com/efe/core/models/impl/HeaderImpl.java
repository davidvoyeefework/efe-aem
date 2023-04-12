package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.Header;
import com.efe.core.models.multifield.Link;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.LinkUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The Class HeaderImpl.
 */
@Model(adaptables = Resource.class, adapters = Header.class, resourceType = {
        HeaderImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class HeaderImpl implements Header {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE="efe/components/header";

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

    /** The CTA title. */
    @ValueMapValue
    private String ctaTitle;

    /** The CTA link. */
    @ValueMapValue
    private String ctaLink;

    /** The CTA target. */
    @ValueMapValue
    private String ctaTarget;

    /** The Search button. */
    @ValueMapValue
    private String searchBtn;

    /** The Search Label. */
    @ValueMapValue
    private String searchLabel;

    /** The Login title. */
    @ValueMapValue
    private String loginTitle;

    /** The Login link. */
    @ValueMapValue
    private String loginLink;

    /** The Login target. */
    @ValueMapValue
    private String loginTarget;

    /** The Contact title. */
    @ValueMapValue
    private String contactTitle;

    /** The Contact Number. */
    @ValueMapValue
    private String contactNumber;

    /** The header list. */
    @Inject
    private List<Link> headerList;

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
     * Gets the file reference.
     *
     * @return the file reference
     */
    @Override
    public String getFileReference() {
        return fileReference;
    }

    /**
     * Gets the alt.
     *
     * @return the alt
     */
    @Override
    public String getAlt() {
        return alt;
    }

    /**
     * Gets the logo link.
     *
     * @return the logo link
     */
    @Override
    public String getLogoLink() {
        return LinkUtil.getFormattedLink(logoLink, resourceResolver);
    }

    /**
     * Gets the logo target.
     *
     * @return the logo target
     */
    @Override
    public String getLogoTarget() {
        return logoTarget;
    }

    /**
     * Gets the CTA title.
     *
     * @return the CTA title
     */
    @Override
    public String getCtaTitle() {
        return ctaTitle;
    }

    /**
     * Gets the CTA link.
     *
     * @return the CTA link
     */
    @Override
    public String getCtaLink() {
        return LinkUtil.getFormattedLink(ctaLink, resourceResolver);
    }

    /**
     * Gets the CTA target.
     *
     * @return the CTA target
     */
    @Override
    public String getCtaTarget() {
        return ctaTarget;
    }

    /**
     * Gets the Search label.
     *
     * @return the Search label
     */
    @Override
    public String getSearchLabel() {
        return searchLabel;
    }

    /**
     * Gets the Search button.
     *
     * @return the Search button
     */
    @Override
    public String getSearchBtn() {
        return searchBtn;
    }

    /**
     * Gets the Login title.
     *
     * @return the Login title
     */
    @Override
    public String getLoginTitle() {
        return loginTitle;
    }

    /**
     * Gets the Login link.
     *
     * @return the Login link
     */
    @Override
    public String getLoginLink() {
        return LinkUtil.getFormattedLink(loginLink, resourceResolver);
    }

    /**
     * Gets the Login target.
     *
     * @return the Login target
     */
    @Override
    public String getLoginTarget() {
        return loginTarget;
    }

    /**
     * Gets the Contact Title.
     *
     * @return the Contact Title
     */
    @Override
    public String getContactTitle() {
        return contactTitle;
    }

    /**
     * Gets the Contact Number.
     *
     * @return the Contact Number
     */
    @Override
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * Gets the header list.
     *
     * @return the header list
     */

    @Override
    public List<Link> getHeaderList() {
        if (Objects.nonNull(headerList)) {
            return new ArrayList<>(headerList);
        }
        return Collections.emptyList();
    }
}
