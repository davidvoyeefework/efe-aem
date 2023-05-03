package com.efe.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.efe.core.services.SeoService;

@Designate(ocd = SeoServiceImpl.Config.class)
@Component(service = SeoService.class)
public class SeoServiceImpl implements SeoService{
	
	private String contextUrl;
	
	private String siteName;
	
	private String orgType;
	
	private String contactType;
	
	private String contactTypeName;
	
	private String contactOption;
	
	private String areaServed;
	
	private String addressType;
	
	private String geoType;
	
	private String businessType;
	
	@ObjectClassDefinition(name = "EFE SEO Configurations", description = "EFE SEO Configurations for storing the SEO static values")
	public static @interface Config {

			@AttributeDefinition(name = "Context URL")
			String contextUrl();
			
			@AttributeDefinition(name = "Site Name")
			String siteName();
			
			@AttributeDefinition(name = "Org Type")
			String orgType();
			
			@AttributeDefinition(name = "Contact Type")
			String contactType();
			
			@AttributeDefinition(name = "Contact Type Name")
			String contactTypeName();
			
			@AttributeDefinition(name = "Contact Option")
			String contactOption();
			
			@AttributeDefinition(name = "Area Server")
			String areaServed();
			
			@AttributeDefinition(name = "Business Type")
			String businessType();
			
			@AttributeDefinition(name = "Address Type")
			String addressType();
			
			@AttributeDefinition(name = "Geo Type")
			String geoType();		
	}
	
	/**
	 * Activate method to initialize stuff
	 * 
	 * @param config
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

	@Override
	public String getContextUrl() {
		return contextUrl;
	}

	@Override
	public String getSiteName() {
		return siteName;
	}

	@Override
	public String getOrgType() {
		return orgType;
	}

	@Override
	public String getContactType() {
		return contactType;
	}

	@Override
	public String getContactTypeName() {
		return contactTypeName;
	}

	@Override
	public String getContactOption() {
		return contactOption;
	}

	@Override
	public String getAreaServed() {
		return areaServed;
	}

	@Override
	public String getAdressType() {
		return addressType;
	}

	@Override
	public String getGeoType() {
		return geoType;
	}

	@Override
	public String getBusinessType() {
		return businessType;
	}

}
