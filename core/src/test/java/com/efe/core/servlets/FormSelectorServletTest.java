package com.efe.core.servlets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.efe.core.models.FormsSelector;
import com.efe.core.services.EfeService;
import com.efe.core.services.RestService;
import com.efe.core.services.impl.EfeServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
public class FormSelectorServletTest {
	
	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/formselector/content.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/corp/home";

	/** The Constant RESOURCE. */
	private static final String RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/root/container/container/formselector";

	/** The efe form servlet. */
	@InjectMocks
	private FormSelectorServlet formSelectorServlet = new FormSelectorServlet();;

	@Mock
	private EfeService efeService;

	@Mock
	private RestService restService;
	
	
	private FormsSelector formsSelector ;
	
	/** The resource. */
	private Resource resource;
	
	@Mock
	/** The configuration. */
	private EfeServiceImpl.Config configuration;

	@Test
	public void setup(AemContext aemContext) throws ServletException, IOException {
		Class<FormsSelector> modelClass = FormsSelector.class;
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.addModelsForClasses(modelClass);
		resource = aemContext.currentResource(RESOURCE);
		formsSelector = aemContext.request().adaptTo(modelClass);
		MockSlingHttpServletRequest request = aemContext.request();
		MockSlingHttpServletResponse response = aemContext.response();
		aemContext.registerService(FormSelectorServlet.class, formSelectorServlet);
		aemContext.getService(FormSelectorServlet.class);
		Mockito.lenient().when(efeService.getFormAuthHeader()).thenReturn("formAuthHeader");
		Mockito.lenient().when(efeService.getFormBaseUrl()).thenReturn("FormBaseUrl");
		aemContext.registerService(EfeService.class, efeService);
		Mockito.lenient().when(restService.getData(Mockito.anyString(), Mockito.anyString())).thenReturn("response");
		aemContext.registerService(RestService.class, restService);		
		formSelectorServlet.doGet(request, response);
	}

}
