package com.efe.core.models.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.genericlists.GenericList;
import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.FeProperties;
import com.efe.core.services.FeService;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.ResourceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The Class FePropertiesImpl.
 */
@Model(adaptables = SlingHttpServletRequest.class, adapters = FeProperties.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FePropertiesImpl implements FeProperties {

	/** The constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FePropertiesImpl.class);

	/** The unbounce service. */
	@OSGiService
	private FeService feService;

	/** The resolver factory. */
	@OSGiService
	private ResourceResolverFactory resolverFactory;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	/** The theme. */
	private String theme;

	/** The dynamic variables. */
	private Map<String, String> dynamicVariables;

	/** The sponsor id. */
	private String sponsorId;

	/** The record keeper. */
	private String recordKeeper;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	protected void init() {
		JsonObject cookieJsonObject = getCookie("daVars");
		if (Objects.isNull(cookieJsonObject)) {
			return;
		}
		setUnbounceField();
		recordKeeper = getCookieValue(cookieJsonObject, "providerId");
		if(StringUtils.isNotEmpty(recordKeeper)) {
			recordKeeper = "theme-" + recordKeeper;
		}
		sponsorId = getCookieValue(cookieJsonObject, "sponsorId");
	}

	/**
	 * Gets the cookie.
	 *
	 * @param cookieValue the cookie value
	 * @return the cookie
	 */
	private JsonObject getCookie(String cookieValue) {
		Cookie cookie = request.getCookie(cookieValue);
		JsonObject jsonObject = null;
		if (Objects.nonNull(cookie)) {
			String value = cookie.getValue();
			LOGGER.info("Cookie value :{}", value);
			try {
				String decodedValue = URLDecoder.decode(value, "utf8");
				jsonObject = new Gson().fromJson(decodedValue, JsonObject.class);

			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Error while decoding ", e);
			}
		}
		return jsonObject;
	}

	/**
	 * Gets the cookie value.
	 *
	 * @param cookieJsonObject the cookie json object
	 * @param cookieKey        the cookie key
	 * @return the cookie value
	 */
	private String getCookieValue(JsonObject cookieJsonObject, String cookieKey) {
		String cookieValue = StringUtils.EMPTY;
		if (Objects.nonNull(cookieJsonObject) && cookieJsonObject.has(cookieKey) && !cookieJsonObject.get(cookieKey).isJsonNull()) {
			cookieValue = cookieJsonObject.get(cookieKey).getAsString();
		}
		return cookieValue;
	}

	/**
	 * Sets the unbounce field.
	 */
	private void setUnbounceField() {
		dynamicVariables = new HashMap<>();
		try (ResourceResolver serviceResolver = ResourceUtil.getServiceResourceResolver(resolverFactory)) {
			String[] genericLists = feService.getDynamicVariableList();

			if (null == genericLists) {
				return;
			}

			for (String genericList : genericLists) {
				if (StringUtils.isNotEmpty(genericList)) {
					String[] genericListArr = genericList.split("\\|");
					if (genericListArr.length == 3) {
						String dataVariableName = genericListArr[1];
						String genericListPath = genericListArr[0];
						GenericList list = EFEUtil.getGenericList(serviceResolver, genericListPath);

						if (null == list) {
							return;
						}
						createDynamicVariableMap(dataVariableName, list);
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("Error :",e);
		}
	}

	/**
	 * Creates the dynamic variable map.
	 *
	 * @param dataVariableName the data variable name
	 * @param list             the list
	 */
	private void createDynamicVariableMap(String dataVariableName, GenericList list) {
		JsonArray array = new JsonArray();
		for (GenericList.Item item : list.getItems()) {
			JsonObject dynamicVariable = new JsonObject();
			String[] values = item.getValue().split("\\|");
			if (values.length == 2) {
				dynamicVariable.addProperty(values[0], values[1]);
			}
			array.add(dynamicVariable);
		}
		String dataVariableValue = new Gson().toJson(array);
		dynamicVariables.put(dataVariableName, dataVariableValue);
	}

	/**
	 * Gets the PageFrameApi.
	 *
	 * @return the PageFrameApi
	 */
	@Override
	public String getPageFrameApi() {
		return feService.getPageFrameApi();
	}

	/**
	 * Gets the AggregateApi.
	 *
	 * @return the AggregateApi
	 */
	@Override
	public String getAggregateApi() {
		return feService.getAggregateApi();
	}

	/**
	 * Gets the ForKeyApi.
	 *
	 * @return the ForKeyApi
	 */
	@Override
	public String getForKeyApi() {
		return feService.getForKeyApi();
	}

	/**
	 * Gets the SoftAuthApi.
	 *
	 * @return the SoftAuthApi
	 */
	@Override
	public String getSoftAuthApi() {
		return feService.getSoftAuthApi();
	}

	/**
	 * Gets the SignupApi.
	 *
	 * @return the SignupApi
	 */
	@Override
	public String getSignupApi() {
		return feService.getSignupApi();
	}

	/**
	 * Gets the ScheduleApi.
	 *
	 * @return the ScheduleApi
	 */
	@Override
	public String getScheduleApi() {
		return feService.getScheduleApi();
	}

	/**
	 * Gets the AuthenticateApi.
	 *
	 * @return the AuthenticateApi
	 */
	@Override
	public String getAuthenticateApi() {
		return feService.getAuthenticateApi();
	}

	/**
	 * Gets the CallBackApi.
	 *
	 * @return the CallBackApi
	 */
	@Override
	public String getCallBackApi() {
		return feService.getCallBackApi();
	}

	/**
	 * Gets the dynamic variables.
	 *
	 * @return the dynamicVariables
	 */
	@Override
	public Map<String, String> getDynamicVariables() {
		if(Objects.nonNull(dynamicVariables)) {
			return new HashMap<>(dynamicVariables);
		}
		return Collections.emptyMap();
	}

	/**
	 * Gets the sponsor id.
	 *
	 * @return the sponsorId
	 */
	@Override
	public String getSponsorId() {
		return sponsorId;
	}

	/**
	 * Gets the record keeper.
	 *
	 * @return the recordKeeper
	 */
	@Override
	public String getRecordKeeper() {
		return recordKeeper;
	}

}
