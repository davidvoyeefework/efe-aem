package com.efe.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.services.EfeService;

/**
 * The Class EfeServiceImpl
 *
 */
@Designate(ocd = EfeServiceImpl.Config.class)
@Component(service = EfeService.class)
public class EfeServiceImpl implements EfeService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EfeServiceImpl.class);

	/**
	 * Planner API Endpoint
	 */
	private String plannersAPIEndpoint;

	/**
	 * Location API Endpoint
	 */
	private String locationsAPIEndpoint;

	/**
	 * AuthHeader
	 */
	private String authHeader;
	
	/**
	 * Planner Page Url
	 */
	private String plannerPageUrl;
	
	/**
	 * Planner Bio Page Url
	 */
	private String plannerBioPageUrl;

	/**
	 * The Interface Config
	 *
	 */
	@ObjectClassDefinition(name = "EFE Common Configurations", description = "EFE Common Configurations")
	public static @interface Config {

		/**
		 * @return plannersAPIEndpoint
		 */
		@AttributeDefinition(name = "Planner API Endpoint URL")
		String plannersAPIEndpoint();

		/**
		 * @return locationsAPIEndpoint
		 */
		@AttributeDefinition(name = "EFE Locations API End Point", description = "EFE Locations API End Point")
		String locationsAPIEndpoint();

		/**
		 * @return authHeader
		 */
		@AttributeDefinition(name = "EFE UPLS authHeader", description = "EFE UPLS authHeader")
		String authHeader();
		
		/**
		 * @return plannerPageUrl
		 */
		@AttributeDefinition(name = "Planner Page Url", description = "Planner Page Url")
		String plannerPageUrl();
		
		/**
		 * @return plannerBioPageUrl
		 */
		@AttributeDefinition(name = "Planner Bio Page Url", description = "Planner Bio Page Url")
		String plannerBioPageUrl();
	}

	/**
	 * Activate method to initialize stuff
	 * 
	 * @param config
	 */
	@Activate
	@Modified
	public void activate(final EfeServiceImpl.Config config) {
		LOGGER.debug("EfeServiceImpl.activate method called {}", "{}");
		this.plannersAPIEndpoint = config.plannersAPIEndpoint();
		this.locationsAPIEndpoint = config.locationsAPIEndpoint();
		this.authHeader = config.authHeader();
		this.plannerPageUrl = config.plannerPageUrl();
		this.plannerBioPageUrl = config.plannerBioPageUrl();

	}

	/**
	 * @return the plannersAPIEndpoint
	 */
	@Override
	public String getPlannersAPIEndpoint() {
		return plannersAPIEndpoint;
	}

	/**
	 * @return the locationsAPIEndpoint
	 */
	@Override
	public String getLocationsAPIEndpoint() {
		return locationsAPIEndpoint;
	}

	/**
	 * @return the authHeader
	 */
	@Override
	public String getAuthHeader() {
		return authHeader;
	}

	/**
	 * @return the plannerPageUrl
	 */
	@Override
	public String getPlannerPageUrl() {
		return plannerPageUrl;
	}

	/**
	 * @return the plannerBioPageUrl
	 */
	@Override
	public String getPlannerBioPageUrl() {
		return plannerBioPageUrl;
	}
}