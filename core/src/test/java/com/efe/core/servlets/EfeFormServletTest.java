package com.efe.core.servlets;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.servlethelpers.MockRequestDispatcherFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.efe.core.services.FormHandler;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class EfeFormServletTest.
 */
@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class EfeFormServletTest {

	/** The form handler. */
	@Mock
	private FormHandler formHandler;

	/** The efe form servlet. */
	@InjectMocks
	private EfeFormServlet efeFormServlet;

	/** The dispatcher factory. */
	@Mock
	private MockRequestDispatcherFactory dispatcherFactory;

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/**
	 * Inits the.
	 */
	@BeforeEach
	void init() {
		Map<String, Object> formComponentProperties = new HashMap<>();
		formComponentProperties.put("efeExternalServiceEndPointUrl", "https://testendpoint");
		formComponentProperties.put("efeErrorMessage", "Error Message");
		formComponentProperties.put("redirect", "/content/forms");
		formComponentProperties.put("name", "Form name");
		
		Resource formResource = aemContext.create().resource("/content/forms/get-intouch", formComponentProperties);
		aemContext.currentResource(formResource);

		aemContext.request().addRequestParameter("firstName", "Test Name");
		aemContext.request().addRequestParameter("lastName", "Test Last Name");
		aemContext.request().addRequestParameter("arrayKey", "item1");
		aemContext.request().addRequestParameter("arrayKey", "item2");
		aemContext.request().addRequestParameter(":cq_csrf_token", "token");

	}

	/**
	 * Test success.
	 *
	 * @throws IOException      Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	@Test
	void testSuccess() throws IOException, ServletException {
		when(formHandler.forwardFormData(anyString(), anyString())).thenReturn(true);
		aemContext.request().setRequestDispatcherFactory(dispatcherFactory);
		efeFormServlet.doPost(aemContext.request(), aemContext.response());
		verify(formHandler, times(1)).forwardFormData("https://testendpoint",
				"{\"firstName\":\"Test Name\",\"lastName\":\"Test Last Name\",\"arrayKey\":[\"item1\",\"item2\"]}");
	}

	/**
	 * Test error.
	 *
	 * @throws IOException      Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	@Test
	void testError() throws IOException, ServletException {
		when(formHandler.forwardFormData(anyString(), anyString())).thenReturn(false);
		aemContext.request().setRequestDispatcherFactory(dispatcherFactory);
		efeFormServlet.doPost(aemContext.request(), aemContext.response());
		verify(formHandler, times(1)).forwardFormData("https://testendpoint",
				"{\"firstName\":\"Test Name\",\"lastName\":\"Test Last Name\",\"arrayKey\":[\"item1\",\"item2\"]}");

	}

}
