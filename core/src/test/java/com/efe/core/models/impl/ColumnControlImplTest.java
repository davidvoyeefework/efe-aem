package com.efe.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.efe.core.models.ColumnControl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class ColumnControlImplTest.
 */
@ExtendWith(AemContextExtension.class)
class ColumnControlImplTest {

	/** The Constant RESOURCE_CONTENT. */
	private static final String RESOURCE_CONTENT = "/com/efe/core/models/columncontrol.json";

	/** The Constant TEST_CONTENT_ROOT. */
	private static final String TEST_CONTENT_ROOT = "/content/efe/us/en/columncontrol";

	/** The aem context. */
	private AemContext aemContext = new AemContext();

	/** The column control. */
	ColumnControl columnControl;

	/**
	 * Test column two split one.
	 */
	@Test
	void testColumnTwoSplitOne() {
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/columncontrol_var1");
		columnControl = aemContext.request().adaptTo(ColumnControl.class);
		assertEquals(2, columnControl.getColumnsList().size());
		assertEquals("aem-GridColumn--phone--12 aem-GridColumn--default--8",
				columnControl.getColumnsList().get(0).get(ColumnControlImpl.CLASSVALUE));
		assertEquals("aem-GridColumn--phone--12 aem-GridColumn--default--4",
				columnControl.getColumnsList().get(1).get(ColumnControlImpl.CLASSVALUE));
	}

	/**
	 * Test column two split two.
	 */
	@Test
	void testColumnTwoSplitTwo() {
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/columncontrol_var2");
		columnControl = aemContext.request().adaptTo(ColumnControl.class);
		assertEquals(2, columnControl.getColumnsList().size());
		assertEquals("aem-GridColumn--phone--12 aem-GridColumn--default--4",
				columnControl.getColumnsList().get(0).get(ColumnControlImpl.CLASSVALUE));
		assertEquals("aem-GridColumn--phone--12 aem-GridColumn--default--8",
				columnControl.getColumnsList().get(1).get(ColumnControlImpl.CLASSVALUE));
	}

	/**
	 * Test column two split default.
	 */
	@Test
	void testColumnTwoSplitDefault() {
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/columncontrol_var3");
		columnControl = aemContext.request().adaptTo(ColumnControl.class);
		assertEquals(2, columnControl.getColumnsList().size());
		assertEquals("aem-GridColumn--phone--12 aem-GridColumn--default--6",
				columnControl.getColumnsList().get(0).get(ColumnControlImpl.CLASSVALUE));
		assertEquals("aem-GridColumn--phone--12 aem-GridColumn--default--6",
				columnControl.getColumnsList().get(1).get(ColumnControlImpl.CLASSVALUE));
	}

	/**
	 * Test column three.
	 */
	@Test
	void testColumnThree() {
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/columncontrol_var4");
		columnControl = aemContext.request().adaptTo(ColumnControl.class);
		assertEquals(3, columnControl.getColumnsList().size());
		assertEquals("aem-GridColumn--phone--12 aem-GridColumn--default--4",
				columnControl.getColumnsList().get(0).get(ColumnControlImpl.CLASSVALUE));
		assertEquals("aem-GridColumn--phone--12 aem-GridColumn--default--4",
				columnControl.getColumnsList().get(1).get(ColumnControlImpl.CLASSVALUE));
		assertEquals("aem-GridColumn--phone--12 aem-GridColumn--default--4",
				columnControl.getColumnsList().get(2).get(ColumnControlImpl.CLASSVALUE));
	}

	/**
	 * Test empty.
	 */
	@Test
	void testEmpty() {
		aemContext.load().json(RESOURCE_CONTENT, TEST_CONTENT_ROOT);
		aemContext.currentResource(TEST_CONTENT_ROOT + "/jcr:content/root/container/columncontrol_empty");
		columnControl = aemContext.request().adaptTo(ColumnControl.class);
		assertEquals(0, columnControl.getColumnsList().size());
	}

}
