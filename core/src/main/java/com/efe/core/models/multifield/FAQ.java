package com.efe.core.models.multifield;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.efe.core.utils.EFEUtil;

/**
 * The Class FAQMultifield.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FAQ {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FAQ.class);

	/** The Constant QUESTION_ELEMENNT_NAME. */
	private static final String QUESTION_ELEMENNT_NAME = "question";

	/** The Constant ANSWER_ELEMENNT_NAME. */
	private static final String ANSWER_ELEMENNT_NAME = "answer";

	/** The fragmentPath. */
	@ValueMapValue
	private String fragmentPath;

	/** The current resource. */
	private Resource currentResource;

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The question. */
	private String question = "";

	/** The answer. */
	private String answer = "";

	/** The id. */
	@ValueMapValue
	private String id;

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {

		LOG.debug("Fragment Path: " + fragmentPath);

		currentResource = resourceResolver.getResource(fragmentPath);

		Optional<ContentFragment> contentFragment = Optional.ofNullable(currentResource.adaptTo(ContentFragment.class));

		if (contentFragment == null) {
			LOG.error("Content Fragment can not be initialized because '{}' is not a content fragment.",
					currentResource.getPath());
		} else {
			question = contentFragment.map(cf -> cf.getElement(QUESTION_ELEMENNT_NAME)).map(ContentElement::getContent)
					.orElse(StringUtils.EMPTY);
			answer = contentFragment.map(cf -> cf.getElement(ANSWER_ELEMENNT_NAME)).map(ContentElement::getContent)
					.orElse(StringUtils.EMPTY);
		}

		if (!question.isEmpty()) {
			question = question.replaceAll("</?p>", "");
		}

		LOG.debug("Question: " + question);
		LOG.debug("Answer: " + answer);
	}

	/**
	 * Gets the fragment path.
	 *
	 * @return the fragmentPath
	 */
	public String getFragmentPath() {
		return fragmentPath;
	}

	/**
	 * Gets the question.
	 *
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Gets the answer.
	 *
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		if (id == null) {
			id = EFEUtil.getId(currentResource);
		}
		return id;
	}
}
