package com.efe.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.services.EfeService;

/**
 * The Class EfeServiceImpl.
 */
@Designate(ocd = EfeServiceImpl.Config.class)
@Component(service = EfeService.class)
public class EfeServiceImpl implements EfeService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EfeServiceImpl.class);

	/** Planner API Endpoint. */
	private String plannersAPIEndpoint;

	/** Location API Endpoint. */
	private String locationsAPIEndpoint;

	/** AuthHeader. */
	private String authHeader;
        
        private String printClientID;
        private String printClientSecret;
        private String partnerAPIAuthURL;
        private String partnerAPIAuthIssuer;
       
        private String partnerAPIAuthAudience;
        private String partnerAPIAuthSub;
        private String partnerAPIAuthKID;

	/** Planner Page Url. */
	private String plannerPageUrl;

	/** Planner Bio Page Url. */
	private String plannerBioPageUrl;

	/** Google Api Key *. */
	private String googlePublicApiKey;

	/** Google Direction Prefix Url *. */
	private String googleDirectionPrefixUrl;

	/** The onetrust script. */
	private String onetrustScript;

	/** The onetrust script id. */
	private String onetrustScriptId;

	/** The analytics site root level. */
	private int analyticsSiteRootLevel;

	/** The link tracking list path. */
	private String linkTrackingListPath;

	/** The form base url. */
	private String formBaseUrl;

	/** The form js url. */
	private String formJsUrl;

	/** The ga enabled. */
	private boolean gaEnabled;

	/** The ga tag value. */
	private String gaTagValue;

	/** The form auth header. */
	private String formAuthHeader;
	
	/** The omny playlist api. */
	private String omnyPlaylistApi;
	
	/** The omny episode api. */
	private String omnyEpisodeApi;
	
	/** The omny org id. */
	private String omnyOrgId;
	
	/** The jquery url. */
	private String jqueryUrl;
        
        private String FPIDLibraryURL;

	/** The external libraries. */
	private String externalLibraries;

	/** The national advisor center. */
	private String nationalAdvisorCenter;

	/**
	 * The Interface Config.
	 */
	@ObjectClassDefinition(name = "EFE Common Configurations", description = "EFE Common Configurations")
	public static @interface Config {

		/**
		 * Planners API endpoint.
		 *
		 * @return plannersAPIEndpoint
		 */
		@AttributeDefinition(name = "Planner API Endpoint URL")
		String plannersAPIEndpoint();

		/**
		 * Locations API endpoint.
		 *
		 * @return locationsAPIEndpoint
		 */
		@AttributeDefinition(name = "EFE Locations API End Point", description = "EFE Locations API End Point")
		String locationsAPIEndpoint();

		/**
		 * Auth header.
		 *
		 * @return authHeader
		 */
		@AttributeDefinition(name = "EFE UPLS authHeader", description = "EFE UPLS authHeader")
		String authHeader();

                /**
		 * Print Client ID.
		 *
		 * @return printClientID
		 */
                @AttributeDefinition(name = "EFE Print Client ID", description = "EFE Print Client ID")
                String printClientID();
                
                /**
		 * Print Client Secret.
		 *
		 * @return printClientSecret
		 */
                @AttributeDefinition(name = "EFE Print Client Secret", description = "EFE Print Client Secret")
                String printClientSecret();
                
                /**
		 * Partner API Auth URL.
		 *
		 * @return partnerAPIAuthURL
		 */
                @AttributeDefinition(name = "EFE Partner API Auth URL", description = "EFE Partner API Auth URL")
                String partnerAPIAuthURL();
                
                /**
		 * Partner API Auth Issuer.
		 *
		 * @return partnerAPIAuthIssuer
		 */
                @AttributeDefinition(name = "EFE Partner API Auth Issuer", description = "EFE Partner API Auth Issuer")
                String partnerAPIAuthIssuer();
                
                /**
		 * Partner API Auth Audience.
		 *
		 * @return partnerAPIAuthAudience
		 */
                @AttributeDefinition(name = "EFE Partner API Auth Audience", description = "EFE Partner API Auth Audience")
                String partnerAPIAuthAudience();
                
                /**
		 * Partner API Auth Subscriber.
		 *
		 * @return partnerAPIAuthSub
		 */
                @AttributeDefinition(name = "EFE Partner API Auth Subscriber", description = "EFE Partner API Auth Subscriber")
                String partnerAPIAuthSub();
                
                /**
		 * Partner API Auth KID.
		 *
		 * @return partnerAPIAuthKID
		 */
                @AttributeDefinition(name = "EFE Partner API Auth KID", description = "EFE Partner API Auth KID")
                String partnerAPIAuthKID();
		/**
		 * Planner page url.
		 *
		 * @return plannerPageUrl
		 */
		@AttributeDefinition(name = "Planner Page Url", description = "Planner Page Url")
		String plannerPageUrl();

		/**
		 * Planner bio page url.
		 *
		 * @return plannerBioPageUrl
		 */
		@AttributeDefinition(name = "Planner Bio Page Url", description = "Planner Bio Page Url")
		String plannerBioPageUrl();

		/**
		 * Google map public api.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Google Map API Key", description = "Google Map API Key for Map integration")
		String googleMapPublicApi();

		/**
		 * Google direction prefix url.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Google Map Direction Prefix Url", description = "Google Map Direction Prefix Url for forming office direction url")
		String googleDirectionPrefixUrl();

		/**
		 * Onetrust script.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "One Trust Script Url", description = "One Trust JS SDK Script URL")
		String onetrustScript();

		/**
		 * Onetrust script id.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "One Trust Script Domain Key", description = "One Trust JS SDK Domain Key")
		String onetrustScriptId();

		/**
		 * Analytics site root level.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Site Root level", description = "Site root level to form site sections", type = AttributeType.INTEGER)
		int analyticsSiteRootLevel();

		/**
		 * Link tracking list path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Link tracking Generic list path", description = "Analytics Link tracking Generic list path")
		String linkTrackingListPath();

		/**
		 * Form base url.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Wealth Management forms base Url", description = "Wealth Management forms base Url")
		String formBaseUrl();

		/**
		 * Form js url.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Wealth Management forms Javascript Url", description = "Wealth Management forms Javascript Url")
		String formJsUrl();

		/**
		 * Enable GA.
		 *
		 * @return true, if successful
		 */
		@AttributeDefinition(name = "Enable GA?", description = "Check to enable GA")
		boolean enableGA();

		/**
		 * Ga tag value.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "GA Tag value", description = "GA Tag Value for different environments")
		String gaTagValue();

		/**
		 * Form auth header.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Wealth Management forms Auth Header", description = "Wealth Management forms Auth Header")
		String formAuthHeader();
		
		/**
		 * Omny playlist api.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Omny Podcast Playlist API", description = "Omny Podcast Playlist API")
		String omnyPlaylistApi();
		
		/**
		 * Omny episode api.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Omny Podcast Episode API", description = "Omny Podcast Episode API")
		String omnyEpisodeApi();
		
		/**
		 * Omny org id.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Omny Podcast Org ID", description = "Omny Podcast Org ID")
		String omnyOrgId();
		
		
		/**
		 * Jquery hosted url.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Jquery Hosted URL", description = "Jquery Hosted URL")
		String jqueryHostedUrl();
		
                @AttributeDefinition(name = "FPID Library Hosted URL", description = "FPID Library Hosted URL")
                String FPIDLibraryHostedURL();
		/**
		 * External libraries.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "External Hosted Libraries", description = "External Hosted Libraries")
		String externalLibraries();

		/**
		 * National advisor center.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "National Advisor Center", description = "National Advisor Center")
		String nationalAdvisorCenter();
	}

	/**
	 * Activate method to initialize stuff.
	 *
	 * @param config the config
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
		this.googlePublicApiKey = config.googleMapPublicApi();
		this.googleDirectionPrefixUrl = config.googleDirectionPrefixUrl();
		this.onetrustScript = config.onetrustScript();
		this.onetrustScriptId = config.onetrustScriptId();
		this.analyticsSiteRootLevel = config.analyticsSiteRootLevel();
		this.linkTrackingListPath = config.linkTrackingListPath();
		this.formBaseUrl = config.formBaseUrl();
		this.formJsUrl = config.formJsUrl();
		this.formAuthHeader = config.formAuthHeader();
                
                this.printClientID = config.printClientID();
                this.printClientSecret = config.printClientSecret();
                this.partnerAPIAuthURL = config.partnerAPIAuthURL();
                this.partnerAPIAuthIssuer = config.partnerAPIAuthIssuer();
                this.partnerAPIAuthKID = config.partnerAPIAuthKID();
                this.partnerAPIAuthAudience = config.partnerAPIAuthAudience();
                this.partnerAPIAuthSub = config.partnerAPIAuthSub();
                
		this.gaEnabled = config.enableGA();
		this.gaTagValue = config.gaTagValue();
		this.omnyEpisodeApi = config.omnyEpisodeApi();
		this.omnyPlaylistApi = config.omnyPlaylistApi();
		this.omnyOrgId = config.omnyOrgId();
		this.jqueryUrl = config.jqueryHostedUrl();
                this.FPIDLibraryURL = config.FPIDLibraryHostedURL();
		this.externalLibraries = config.externalLibraries();
		this.nationalAdvisorCenter = config.nationalAdvisorCenter();
	}

	/**
	 * Gets the planners API endpoint.
	 *
	 * @return the plannersAPIEndpoint
	 */
	@Override
	public String getPlannersAPIEndpoint() {
		return plannersAPIEndpoint;
	}

	/**
	 * Gets the locations API endpoint.
	 *
	 * @return the locationsAPIEndpoint
	 */
	@Override
	public String getLocationsAPIEndpoint() {
		return locationsAPIEndpoint;
	}

	/**
	 * Gets the auth header.
	 *
	 * @return the authHeader
	 */
	@Override
	public String getAuthHeader() {
		return authHeader;
	}

	/**
	 * Gets the planner page url.
	 *
	 * @return the plannerPageUrl
	 */
	@Override
	public String getPlannerPageUrl() {
		return plannerPageUrl;
	}

	/**
	 * Gets the planner bio page url.
	 *
	 * @return the plannerBioPageUrl
	 */
	@Override
	public String getPlannerBioPageUrl() {
		return plannerBioPageUrl;
	}

	/**
	 * Gets the google public key.
	 *
	 * @return the google public key
	 */
	@Override
	public String getGooglePublicKey() {
		return googlePublicApiKey;
	}

	/**
	 * Gets the google direction prefix url.
	 *
	 * @return the google direction prefix url
	 */
	@Override
	public String getGoogleDirectionPrefixUrl() {
		return googleDirectionPrefixUrl;
	}

	/**
	 * Gets the one trust script.
	 *
	 * @return the one trust script
	 */
	@Override
	public String getOneTrustScript() {
		return onetrustScript;
	}

	/**
	 * Gets the one trust script id.
	 *
	 * @return the one trust script id
	 */
	@Override
	public String getOneTrustScriptId() {
		return onetrustScriptId;
	}

	/**
	 * Gets the analytics site root level.
	 *
	 * @return the analyticsSiteRootLevel
	 */
	@Override
	public int getAnalyticsSiteRootLevel() {
		return analyticsSiteRootLevel;
	}

	/**
	 * Gets the link tracking list path.
	 *
	 * @return the linkTrackingListPath
	 */
	@Override
	public String getLinkTrackingListPath() {
		return linkTrackingListPath;
	}

	/**
	 * Gets the form base url.
	 *
	 * @return the form base url
	 */
	@Override
	public String getFormBaseUrl() {
		return formBaseUrl;
	}

	/**
	 * Gets the form js url.
	 *
	 * @return the form js url
	 */
	@Override
	public String getFormJsUrl() {
		return formJsUrl;
	}

	/**
	 * Gets the form auth header.
	 *
	 * @return the form auth header
	 */
	@Override
	public String getFormAuthHeader() {
		return formAuthHeader;
	}
        
        @Override
	public String getPrintClientID() {
		return printClientID;
	}
        @Override
	public String getPrintClientSecret() {
		return printClientSecret;
	}
        @Override
	public String getPartnerAPIAuthURL() {
		return partnerAPIAuthURL;
	}
        @Override
	public String getPartnerAPIAuthIssuer() {
		return partnerAPIAuthIssuer;
	}
        @Override
	public String getPartnerAPIAuthAudience() {
		return partnerAPIAuthAudience;
	}
        @Override
	public String getPartnerAPIAuthSub() {
		return partnerAPIAuthSub;
	}
        @Override
	public String getPartnerAPIAuthKID() {
		return partnerAPIAuthKID;
	}



	/**
	 * Checks if is enabled GA.
	 *
	 * @return true, if is enabled GA
	 */
	@Override
	public boolean isEnabledGA() {
		return gaEnabled;
	}

	/**
	 * Gets the ga tag value.
	 *
	 * @return the ga tag value
	 */
	@Override
	public String getGaTagValue() {
		return gaTagValue;
	}

	/**
	 * Gets the omny playlist api.
	 *
	 * @return the omny playlist api
	 */
	@Override
	public String getOmnyPlaylistApi() {
		return omnyPlaylistApi;
	}

	/**
	 * Gets the omny episode api.
	 *
	 * @return the omny episode api
	 */
	@Override
	public String getOmnyEpisodeApi() {
		return omnyEpisodeApi;
	}

	/**
	 * Gets the omny org id.
	 *
	 * @return the omny org id
	 */
	@Override
	public String getOmnyOrgId() {
		return omnyOrgId;
	}

	/**
	 * Gets the jquery url.
	 *
	 * @return the jquery url
	 */
	@Override
	public String getJqueryUrl() {
		return jqueryUrl;
	}

        @Override
        public String getFPIDLibraryURL() {
            return FPIDLibraryURL;
        }
	/**
	 * Gets the external libraries.
	 *
	 * @return the externalLibraries
	 */
	@Override
	public String getExternalLibraries() {
		return externalLibraries;
	}

	/**
	 * Gets the nationalAdvisorCenter.
	 *
	 * @return the nationalAdvisorCenter
	 */
	@Override
	public String getNationalAdvisorCenter() {
		return nationalAdvisorCenter;
	}
}