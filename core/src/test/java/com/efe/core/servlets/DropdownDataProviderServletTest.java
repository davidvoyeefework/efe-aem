package com.efe.core.servlets;

import com.adobe.acs.commons.genericlists.GenericList;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.efe.core.services.FeService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The Class DropdownDataProviderServletTest.
 */
@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
 class DropdownDataProviderServletTest {

    @InjectMocks
    private DropdownDataProviderServlet dropdownDataProviderServlet;

    /** The request. */
    @Mock
    private SlingHttpServletRequest request;

    /** The response. */
    @Mock
    private SlingHttpServletResponse response;

    /** The resolver factory. */
    @Mock
    ResourceResolverFactory resolverFactory;

    /** The feService. */
    @Mock
    private FeService feService;

    /** The aemContext. */
    public final AemContext aemContext = new AemContext();

    /**
     * Inits the class.
     */
    @BeforeEach
    void init() {
        aemContext.registerService(FeService.class, feService);
    }

    /**
     * Do get test.
     *
     */
    @Test
    void doGetTest() {
        Resource currentResource = mock(Resource.class);
        Resource childResource = mock(Resource.class);
        when(request.getResource()).thenReturn(currentResource);
        when(currentResource.getChild("datasource")).thenReturn(childResource);
        ValueMap valueMap = mock(ValueMap.class);
        when(childResource.getValueMap()).thenReturn(valueMap);
        when(valueMap.get("type", String.class)).thenReturn("dynamicvariables");
        ResourceResolver resourceResolver = mock(ResourceResolver.class);
        when(request.getResourceResolver()).thenReturn(resourceResolver);
        String[] genericLists = new String[]{"/etc/acs-commons/lists/fe/sponsor-details|true"};
        when(feService.getDynamicVariableList()).thenReturn(genericLists);
        PageManager pageManager = mock(PageManager.class);
        Page listPage = mock(Page.class);
        when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        when(pageManager.getPage("/etc/acs-commons/lists/fe/sponsor-details")).thenReturn(listPage);
        GenericList list = mock(GenericList.class);
        when(listPage.adaptTo(GenericList.class)).thenReturn(list);

        List<GenericList.Item> itemList = new ArrayList<>();
        GenericList.Item item1 = mock(GenericList.Item.class);
        GenericList.Item item2 = mock(GenericList.Item.class);
        itemList.add(item1);
        itemList.add(item2);
        when(list.getItems()).thenReturn(itemList);
        when(item1.getValue()).thenReturn("sponsorName|header.sponsorName");
        when(item2.getValue()).thenReturn("supportHour|header.supportHour");
        when(item1.getTitle()).thenReturn("Sponsor Name");
        when(item2.getTitle()).thenReturn("Support Hour");
        assertNotNull(resourceResolver);
        dropdownDataProviderServlet.doGet(request, response);
    }
}
