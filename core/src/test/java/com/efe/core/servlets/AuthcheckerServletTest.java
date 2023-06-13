package com.efe.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import javax.jcr.Session;

@ExtendWith(MockitoExtension.class)
class AuthcheckerServletTest {

    @InjectMocks
    private AuthcheckerServlet authcheckerServlet;

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private SlingHttpServletResponse response;

    @Test
    void doHeadTest() {
        when(request.getParameter("uri")).thenReturn("uri");
        ResourceResolver resourceResolver = mock(ResourceResolver.class);
        when(request.getResourceResolver()).thenReturn(resourceResolver);
        Session session = mock(Session.class);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        authcheckerServlet.doHead(request, response);
        assertNotNull(session);
    }
}
