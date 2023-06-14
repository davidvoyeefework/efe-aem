package com.efe.core.servlets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The Class AuthcheckerServletTest.
 */
@ExtendWith(MockitoExtension.class)
class AuthcheckerServletTest {

	/** The authchecker servlet. */
	@InjectMocks
	private AuthcheckerServlet authcheckerServlet;

	/** The request. */
	@Mock
	private SlingHttpServletRequest request;

	/** The response. */
	@Mock
	private SlingHttpServletResponse response;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource. */
	@Mock
	Resource resource;

	/** The children itr. */
	@Mock
	Iterator<Resource> childrenItr;

	/** The value map. */
	@Mock
	ValueMap valueMap;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The session. */
	@Mock
	Session session;

	/**
	 * Inits the.
	 *
	 * @throws LoginException the login exception
	 */
	@BeforeEach
	void init() throws LoginException {
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(resolverFactory.getServiceResourceResolver(any())).thenReturn(resourceResolver);
		when(resourceResolver.getResource(anyString())).thenReturn(resource);
		when(resource.listChildren()).thenReturn(childrenItr);
		when(resource.hasChildren()).thenReturn(true);
		when(childrenItr.hasNext()).thenReturn(true).thenReturn(false);
	}

	/**
	 * Do head test.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void doHeadTest() throws LoginException {
		when(request.getParameter("uri")).thenReturn("/graphql/efe/all-category-cards");
		when(valueMap.get(anyString(), anyString())).thenReturn("Employee-Category-Cards");
		when(childrenItr.next()).thenReturn(resource);
		when(resource.getValueMap()).thenReturn(valueMap);
	
		authcheckerServlet.doHead(request, response);
		assertNotNull(session);

	}

	/**
	 * Do head test headers.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void doHeadTestHeaders() throws LoginException {
		when(request.getParameter("uri")).thenReturn("/graphql/efe/all-header-details");
		when(valueMap.get(anyString(), anyString())).thenReturn("Employee-Header-Details");
		when(childrenItr.next()).thenReturn(resource);
		when(resource.getValueMap()).thenReturn(valueMap);
	
		authcheckerServlet.doHead(request, response);
		assertNotNull(response);
	}

	/**
	 * Do head test headers.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void doHeadTestWithInvalidInp() throws LoginException {
		when(request.getParameter("uri")).thenReturn("/graphql/all-header-details");
		when(valueMap.get(anyString(), anyString())).thenReturn("Employee-Header-Details");
		when(childrenItr.next()).thenReturn(resource);
		when(resource.getValueMap()).thenReturn(valueMap);
	
		authcheckerServlet.doHead(request, response);
		assertNotNull(session);
	}

	/**
	 * Do head test headers.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void doHeadTestWithNoAcsList() throws LoginException {
		when(resource.hasChildren()).thenReturn(true);
		when(childrenItr.hasNext()).thenReturn(false);
		authcheckerServlet.doHead(request, response);
		assertNotNull(session);
	}

}
