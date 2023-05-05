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

}
