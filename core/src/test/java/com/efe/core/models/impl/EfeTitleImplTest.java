package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.cq.wcm.core.components.models.Title;

/**
 * The Class EfeTitleImplTest.
 */
@ExtendWith({ MockitoExtension.class })
class EfeTitleImplTest {

	/** The title. */
	@Mock
	Title title;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The request path info. */
	@Mock
	RequestPathInfo requestPathInfo;

	/** The efe title impl. */
	@InjectMocks
	EfeTitleImpl efeTitleImpl;

	/**
	 * Test with selector.
	 *
	 * @throws IllegalAccessException the illegal access exception
	 */
	@Test
	void testWithSelector() throws IllegalAccessException {

		FieldUtils.writeField(efeTitleImpl, "includeLocationInTitle", true, true);

		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		when(requestPathInfo.getSelectors()).thenReturn(new String[] { "ks", "wichita" });
		when(title.getText()).thenReturn("Finanical Planner In {0}, {1}");

		when(title.getExportedType()).thenReturn("type");
		when(title.getAppliedCssClasses()).thenReturn("css");
		when(title.getType()).thenReturn("h2");
		when(title.getId()).thenReturn("id");
		when(title.isLinkDisabled()).thenReturn(true);

		efeTitleImpl.init();
		assertEquals("Finanical Planner In ks, wichita", efeTitleImpl.getText());
		assertEquals("h2", efeTitleImpl.getType());
		assertEquals("type", efeTitleImpl.getExportedType());
		assertEquals("css", efeTitleImpl.getAppliedCssClasses());
		assertEquals("id", efeTitleImpl.getId());
		assertNull(efeTitleImpl.getData());
		assertNull(efeTitleImpl.getLink());
		assertTrue(efeTitleImpl.isLinkDisabled());

	}

	/**
	 * Test without selector.
	 *
	 * @throws IllegalAccessException the illegal access exception
	 */
	@Test
	void testWithoutSelector() throws IllegalAccessException {
		FieldUtils.writeField(efeTitleImpl, "includeLocationInTitle", true, true);
		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		when(requestPathInfo.getSelectors()).thenReturn(new String[] {});
		when(title.getText()).thenReturn("Finanical Planner In");
		efeTitleImpl.init();
		assertEquals("Finanical Planner In", efeTitleImpl.getText());
	}
	
	/**
	 * Test without text.
	 */
	@Test
	void testWithoutText() {
		when(title.getText()).thenReturn(null);
		efeTitleImpl.init();
		assertNull(efeTitleImpl.getText());
	}

}
