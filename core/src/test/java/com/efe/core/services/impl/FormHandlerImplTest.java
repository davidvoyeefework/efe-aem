package com.efe.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.osgi.services.HttpClientBuilderFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class FormHandlerImplTest.
 */
@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class FormHandlerImplTest {

	/** The form handler. */
	@InjectMocks
	private FormHandlerImpl formHandler;

	/** The client builder factory. */
	@Mock
	private HttpClientBuilderFactory clientBuilderFactory;

	/** The builder. */
	@Mock
	HttpClientBuilder builder;

	/** The client. */
	@Mock
	CloseableHttpClient client;

	/** The config. */
	@Mock
	FormHandlerImpl.Config config;

	/**
	 * Test forward form data success.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testForwardFormDataSuccess() throws Exception {
		lenient().when(clientBuilderFactory.newBuilder()).thenReturn(builder);
		lenient().when(builder.setDefaultRequestConfig(any())).thenReturn(builder);
		lenient().when(builder.build()).thenReturn(client);
		lenient().when(client.execute(any(HttpPost.class), any(BasicResponseHandler.class))).thenReturn("response");
		lenient().when(config.connectionTimeout()).thenReturn(2000);
		lenient().when(config.socketTimeout()).thenReturn(2000);

		formHandler.activate(config);
		assertTrue(formHandler.forwardFormData("testendpoint", "formdata"));
	}

	/**
	 * Test forward form data error.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testForwardFormDataError() throws Exception {

		lenient().when(clientBuilderFactory.newBuilder()).thenReturn(builder);
		lenient().when(builder.setDefaultRequestConfig(any())).thenReturn(builder);
		lenient().when(builder.build()).thenReturn(client);
		lenient().when(client.execute(any(HttpPost.class), any(BasicResponseHandler.class)))
				.thenThrow(new IOException());

		formHandler.activate(config);
		assertFalse(formHandler.forwardFormData("testendpoint", "formdata"));
	}
}