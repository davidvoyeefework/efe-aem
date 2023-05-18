package com.efe.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.efe.core.services.SeoService;

/**
 * The Class SeoServiceImpl.
 */
@Designate(ocd = SeoServiceImpl.Config.class)
@Component(service = SeoService.class)
public class SeoServiceImpl implements SeoService{
	
	/** The context url. */
	private String contextUrl;
	
	/** The site name. */
	private String siteName;
	
	/** The org type. */
	private String orgType;
	
	/** The contact type. */
	private String contactType;
	
	/** The contact type name. */
	private String contactTypeName;
	
	/** The contact option. */
	private String contactOption;
	
	/** The area served. */
	private String areaServed;
	
	/** The address type. */
	private String addressType;
	
	/** The geo type. */
	private String geoType;
	
	/** The business type. */
	private String businessType;
	
	/** The faq type. */
	private String faqType;
	
	/** The question type. */
	private String questionType;
	
	/** The answer type. */
	private String answerType;
	
	/** The breadcrumb item type. */
	private String breadcrumbItemType;
	
	/** The breadcrumb type. */
	private String breadcrumbType;
	
	/** The video type. */
	private String videoType;
	
	/** The youtube API url. */
	private String youtubeAPIUrl;
	
	/**
	 * The Interface Config.
	 */
	@ObjectClassDefinition(name = "EFE SEO Configurations", description = "EFE SEO Configurations for storing the SEO static values")
	public static @interface Config {

			/**
			 * Context url.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Context URL")
			String contextUrl();
			
			/**
			 * Site name.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Site Name")
			String siteName();
			
			/**
			 * Org type.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Org Type")
			String orgType();
			
			/**
			 * Contact type.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Contact Type")
			String contactType();
			
			/**
			 * Contact type name.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Contact Type Name")
			String contactTypeName();
			
			/**
			 * Contact option.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Contact Option")
			String contactOption();
			
			/**
			 * Area served.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Area Server")
			String areaServed();
			
			/**
			 * Business type.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Business Type")
			String businessType();
			
			/**
			 * Address type.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Address Type")
			String addressType();
			
			/**
			 * Geo type.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Geo Type")
			String geoType();
			
			/**
			 * Faq type.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Faq Type")
			String faqType();
			
			/**
			 * Question type.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Faq Question Type")
			String questionType();
			
			/**
			 * Answer type.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Faq Answer Type")
			String answerType();
			
			/**
			 * Bread crumb type.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Breadcrumb Type")
			String breadCrumbType();
			
			/**
			 * Bread crumb item type.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Breadcrumb Item Type")
			String breadCrumbItemType();
			
			/**
			 * Youtube API url.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Youtube API")
			String youtubeAPIUrl();
			
			/**
			 * Video type.
			 *
			 * @return the string
			 */
			@AttributeDefinition(name = "Video Type")
			String videoType();
	}
	
	/**
	 * Activate method to initialize stuff.
	 *
	 * @param config the config
	 */
	@Activate
	@Modified
	public void activate(final SeoServiceImpl.Config config) {		
		contextUrl = config.contextUrl();
		siteName = config.siteName();
		orgType = config.orgType();
		contactType = config.contactType();
		contactTypeName = config.contactTypeName();
		contactOption = config.contactOption();
		areaServed = config.areaServed();
		addressType = config.addressType();
		geoType = config.geoType();
		businessType = config.businessType();
		faqType = config.faqType();
		answerType = config.answerType();
		questionType = config.questionType();
		breadcrumbType = config.breadCrumbType();
		breadcrumbItemType = config.breadCrumbItemType();
		youtubeAPIUrl = config.youtubeAPIUrl();
		videoType = config.videoType();
	}

	/**
	 * Gets the context url.
	 *
	 * @return the context url
	 */
	@Override
	public String getContextUrl() {
		return contextUrl;
	}

	/**
	 * Gets the site name.
	 *
	 * @return the site name
	 */
	@Override
	public String getSiteName() {
		return siteName;
	}

	/**
	 * Gets the org type.
	 *
	 * @return the org type
	 */
	@Override
	public String getOrgType() {
		return orgType;
	}

	/**
	 * Gets the contact type.
	 *
	 * @return the contact type
	 */
	@Override
	public String getContactType() {
		return contactType;
	}

	/**
	 * Gets the contact type name.
	 *
	 * @return the contact type name
	 */
	@Override
	public String getContactTypeName() {
		return contactTypeName;
	}

	/**
	 * Gets the contact option.
	 *
	 * @return the contact option
	 */
	@Override
	public String getContactOption() {
		return contactOption;
	}

	/**
	 * Gets the area served.
	 *
	 * @return the area served
	 */
	@Override
	public String getAreaServed() {
		return areaServed;
	}

	/**
	 * Gets the adress type.
	 *
	 * @return the adress type
	 */
	@Override
	public String getAdressType() {
		return addressType;
	}

	/**
	 * Gets the geo type.
	 *
	 * @return the geo type
	 */
	@Override
	public String getGeoType() {
		return geoType;
	}

	/**
	 * Gets the business type.
	 *
	 * @return the business type
	 */
	@Override
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * Gets the faq type.
	 *
	 * @return the faq type
	 */
	@Override
	public String getFaqType() {	
		return faqType;
	}

	/**
	 * Gets the question type.
	 *
	 * @return the question type
	 */
	@Override
	public String getQuestionType() {
		return questionType;
	}

	/**
	 * Gets the answer type.
	 *
	 * @return the answer type
	 */
	@Override
	public String getAnswerType() {
		return answerType;
	}

	/**
	 * Gets the bread crumb type.
	 *
	 * @return the bread crumb type
	 */
	@Override
	public String getBreadCrumbType() {
		return breadcrumbType;
	}

	/**
	 * Gets the bread crumb item type.
	 *
	 * @return the bread crumb item type
	 */
	@Override
	public String getBreadCrumbItemType() {
		return breadcrumbItemType;
	}

	/**
	 * Gets the youtube API url.
	 *
	 * @return the youtube API url
	 */
	@Override
	public String getYoutubeAPIUrl() {
		return youtubeAPIUrl;
	}

	/**
	 * Gets the video type.
	 *
	 * @return the video type
	 */
	@Override
	public String getVideoType() {
		return videoType;
	}

}
