package com.efe.core.services.impl;

import java.io.IOException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.osgi.services.HttpClientBuilderFactory;
import org.apache.sling.servlets.post.JSONResponse;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.rest.Constants;
import com.efe.core.services.FormHandler;

/**
 * The Class FormHandlerImpl.
 */
@Component(service = { FormHandler.class })
@Designate(ocd = FormHandlerImpl.Config.class)
public class FormHandlerImpl implements FormHandler {

	/** The Constant DEFAULT_CONNECTION_TIMEOUT. */
	private static final int DEFAULT_CONNECTION_TIMEOUT = 6000;

	/** The Constant DEFAULT_SOCKET_TIMEOUT. */
	private static final int DEFAULT_SOCKET_TIMEOUT = 6000;

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FormHandlerImpl.class);

	/** The Constant CHARSET. */
	private static final String CHARSET = "UTF-8";

	/** The client. */
	private CloseableHttpClient client;

	/** The client builder factory. */
	@Reference
	private HttpClientBuilderFactory clientBuilderFactory;

	/**
	 * Forward form data.
	 *
	 * @param formData    the form data
	 * @param endPointUrl the end point url
	 * @return true, if successful
	 */
	@Override
	public boolean forwardFormData(String endPointUrl, String formData) {
		boolean isSuccess = false;

		HttpPost post = new HttpPost(endPointUrl);
		post.setHeader("content-type", Constants.CT_JSON);
		post.setEntity(new StringEntity(formData, ContentType.create(JSONResponse.RESPONSE_CONTENT_TYPE, CHARSET)));
		try {
			String apiResponse = client.execute(post, new BasicResponseHandler());
			LOG.debug("POSTing form data to '{}' succeeded: response: {}", endPointUrl, apiResponse);
			isSuccess = true;
		} catch (IOException e) {
			LOG.error("POSTing form data to '{}' failed: {}", endPointUrl, e.getMessage(), e);
		}
		return isSuccess;
	}

	/**
	 * Activate.
	 *
	 * @param config the config
	 */
	@Activate
	protected void activate(Config config) {
		int connectionTimeout = config.connectionTimeout();
		if (connectionTimeout < 0) {
			throw new IllegalArgumentException("Connection timeout value cannot be less than 0");
		}
		int socketTimeout = config.socketTimeout();
		if (socketTimeout < 0) {
			throw new IllegalArgumentException("Socket timeout value cannot be less than 0");
		}

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectionTimeout)
				.setConnectionRequestTimeout(connectionTimeout).setSocketTimeout(socketTimeout).build();

		client = clientBuilderFactory.newBuilder().setDefaultRequestConfig(requestConfig).build();
	}

	/**
	 * Deactivate.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Deactivate
	protected void deactivate() throws IOException {
		client.close();
	}

	/**
	 * The Interface Config.
	 */
	@ObjectClassDefinition(name = "EFE Form API Client", description = "A HTTP Client wrapper for Form API requests")
	public @interface Config {

		/**
		 * Connection timeout.
		 *
		 * @return the int
		 */
		@AttributeDefinition(name = "Connection timeout", description = "Timeout in milliseconds until a connection is established. A timeout value of zero is interpreted as an "
				+ "infinite timeout. Default is 6000ms", defaultValue = { "" + DEFAULT_CONNECTION_TIMEOUT })
		int connectionTimeout() default DEFAULT_CONNECTION_TIMEOUT;

		/**
		 * Socket timeout.
		 *
		 * @return the int
		 */
		@AttributeDefinition(name = "Socket timeout", description = "Timeout in milliseconds for waiting for data or a maximum period of inactivity between two consecutive "
				+ "data packets. Default is 6000ms", defaultValue = { "" + DEFAULT_SOCKET_TIMEOUT })
		int socketTimeout() default DEFAULT_SOCKET_TIMEOUT;
	}

}
