package com.efe.core.services.impl;

import java.io.IOException;
import java.util.Objects;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.servlets.post.JSONResponse;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efe.core.services.RestService;

/**
 * The Class RestServiceImpl.
 */
@Component(service = RestService.class)
public class RestServiceImpl implements RestService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceImpl.class);

	/**
	 * Gets the data.
	 *
	 * @param apiUrl     the token
	 * @param authHeader the authHeader
	 * @return the data
	 */
	@Override
	public String getData(String apiUrl, String authHeader) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(apiUrl);
			String authorization = "Basic " + authHeader;
			httpGet.addHeader("Authorization", authorization);
			httpGet.addHeader("Content-Type", JSONResponse.RESPONSE_CONTENT_TYPE);
			CloseableHttpResponse httpResponse = null;
			try {
				httpResponse = httpClient.execute(httpGet);
			} catch (IOException ioException) {
				LOGGER.error("IOException  occured method:", ioException);

			}
			if (Objects.nonNull(httpResponse)) {
				String jsonOutput = EntityUtils.toString(httpResponse.getEntity());
				return jsonOutput;
			}
		} catch (ParseException e) {
			LOGGER.error("ParseException occured method:", e);
		} catch (IOException e) {
			LOGGER.error("IOException occured method:", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				LOGGER.error("IOException  occured method: getLocationData cause ", e);

			}
		}
		return null;

	}
}
