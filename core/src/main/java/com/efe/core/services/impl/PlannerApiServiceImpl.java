package com.efe.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.services.PlannerApiService;

/**
 * The Class PlannerApiServiceImpl
 *
 */
@Designate(ocd = PlannerApiServiceImpl.Config.class)
@Component(service = PlannerApiService.class)
public class PlannerApiServiceImpl implements PlannerApiService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerApiServiceImpl.class);

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
	 * The Interface Config
	 *
	 */
	@ObjectClassDefinition(name = "EFE Planner API End Point", description = "EFE Planner API End Point")
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
	}

	/**
	 * Activate method to initialize stuff
	 * 
	 * @param config
	 */
	@Activate
	@Modified
	protected void activate(final PlannerApiServiceImpl.Config config) {
		LOGGER.debug("PlannerApiServiceImpl.activate method called {}", "{}");
		this.plannersAPIEndpoint = config.plannersAPIEndpoint();
		this.locationsAPIEndpoint = config.locationsAPIEndpoint();
		this.authHeader = config.authHeader();

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
}