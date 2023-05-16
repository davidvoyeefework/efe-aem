package com.efe.core.services;

/**
 * The Interface SeoService.
 */
public interface SeoService {
	
	/**
	 * Get the Context URL Value.
	 *
	 * @return context URL
	 */
	String getContextUrl();
	
	/**
	 * Get the Site Name Value.
	 *
	 * @return siteName
	 */
	String getSiteName();
	
	/**
	 * Get the Org Type Value.
	 *
	 * @return orgType
	 */
	String getOrgType();
	
	/**
	 * Get the Contact Type Value.
	 *
	 * @return contactType
	 */
	String getContactType();
	
	/**
	 * Get the Contact Type Name Value.
	 *
	 * @return contactTypeName
	 */
	String getContactTypeName();
	
	/**
	 * Get the Contact Option Value.
	 *
	 * @return contactOption
	 */
	String getContactOption();
	
	/**
	 * Get the Area Served Value.
	 *
	 * @return areaServed
	 */
	String getAreaServed();
	
	/**
	 * Get the Address Type Value.
	 *
	 * @return addressType
	 */
	String getAdressType();
	
	/**
	 * Get the Geo Type value.
	 *
	 * @return geoType
	 */
	String getGeoType();

	/**
	 * Get the business type Value.
	 *
	 * @return the businessType
	 */
	String getBusinessType();
	
	/**
	 * Gets the faq type.
	 *
	 * @return the faq type
	 */
	String getFaqType();
	
	/**
	 * Gets the question type.
	 *
	 * @return the question type
	 */
	String getQuestionType();
	
	/**
	 * Gets the answer type.
	 *
	 * @return the answer type
	 */
	String getAnswerType();
	
	/**
	 * Gets the bread crumb type.
	 *
	 * @return the bread crumb type
	 */
	String getBreadCrumbType();
	
	/**
	 * Gets the bread crumb item type.
	 *
	 * @return the bread crumb item type
	 */
	String getBreadCrumbItemType();
	
	
	/**
	 * Gets the youtube API url.
	 *
	 * @return the youtube API url
	 */
	String getYoutubeAPIUrl();
	
	/**
	 * Gets the video type.
	 *
	 * @return the video type
	 */
	String getVideoType();
	
}
