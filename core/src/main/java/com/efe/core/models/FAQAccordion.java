package com.efe.core.models;

import java.util.List;

import com.efe.core.models.multifield.FAQ;

/**
 * The Interface FAQAccordion.
 */
public interface FAQAccordion {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();

	/**
	 * Gets the FAQ list.
	 *
	 * @return the FAQ list
	 */
	List<FAQ> getFaqList();

}
