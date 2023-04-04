package com.efe.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FAQModel {
    
    private static final Logger LOG = LoggerFactory.getLogger(FAQModel.class);

    /**
     * The name of the master variation.
     */
    private static final String MASTER_VARIATION = "master";

    private static final String QUESTION_ELEMENNT_NAME = "question";

    private static final String ANSWER_ELEMENNT_NAME = "answer";

    private Resource contentFragmentResource;

    private final String resourceType;

    private String question;

    private String answer;


    public FAQModel(Resource contentFragmentResource) {

        this.resourceType = contentFragmentResource.getResourceType();

        Optional<ContentFragment> contentFragment = Optional.ofNullable(contentFragmentResource.adaptTo(ContentFragment.class));

        if (contentFragment == null) {
            LOG.error("Content Fragment can not be initialized because '{}' is not a content fragment.",
                    contentFragmentResource.getPath());
        } else {
            question = contentFragment.map(cf -> cf.getElement(QUESTION_ELEMENNT_NAME)).map(ContentElement::getContent).orElse(StringUtils.EMPTY);
            answer = contentFragment.map(cf -> cf.getElement(ANSWER_ELEMENNT_NAME)).map(ContentElement::getContent).orElse(StringUtils.EMPTY);            
        }

        LOG.debug("Question: " + question);
        LOG.debug("Answer: " + answer);
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
