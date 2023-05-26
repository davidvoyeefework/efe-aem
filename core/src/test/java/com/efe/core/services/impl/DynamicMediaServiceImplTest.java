package com.efe.core.services.impl;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.scene7.api.constants.Scene7Constants;
import com.efe.core.constants.PlannerLocationConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * The Class DynamicMediaServiceImplTest.
 */
@ExtendWith(MockitoExtension.class)
class DynamicMediaServiceImplTest {

	/** The DynamicMediaServiceImpl. */
	private DynamicMediaServiceImpl dynamicMediaService = new DynamicMediaServiceImpl();

	/** The Constant METADATA. */
	public static final String METADATA = "metadata";

	/** The resourceResolver. */
	@Mock
	private ResourceResolver resourceResolver;

	/** The valueMap. */
	@Mock
	private ValueMap valueMap;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		dynamicMediaService = new DynamicMediaServiceImpl();
	}

	/**
	 * Test dynamic media image path.
	 */
	@Test
	void getDmImagePath() {
		String imagePath = "/content/dam/test1.png";
		Resource resource = mock(Resource.class);
		Mockito.when(resourceResolver.getResource(imagePath + PlannerLocationConstants.FORWARD_SLASH + JcrConstants.JCR_CONTENT + PlannerLocationConstants.FORWARD_SLASH + METADATA)).thenReturn(resource);
		Mockito.when(resource.getValueMap()).thenReturn(valueMap);
		Mockito.when(valueMap.get(Scene7Constants.PN_S7_FILE, StringUtils.EMPTY)).thenReturn("cem/test1");
		Mockito.when(valueMap.get(Scene7Constants.PN_S7_DOMAIN, StringUtils.EMPTY)).thenReturn("https://s7d9.scene7.com/");
		Mockito.when(valueMap.get(Scene7Constants.PN_S7_FILE_STATUS, StringUtils.EMPTY)).thenReturn(Scene7Constants.PV_S7_PUBLISH_COMPLETE);
		assertEquals("https://s7d9.scene7.com/is/image/cem/test1", dynamicMediaService.getDmImagePath(resourceResolver, imagePath));
	}

	/**
	 * Test dynamic media image path resource null.
	 */
	@Test
	void getDmImagePathResourceNull() {
		String imagePath = "/content/dam/test1.png";
		assertEquals(imagePath, dynamicMediaService.getDmImagePath(resourceResolver, imagePath));
	}

}
