package com.efe.core.services.impl.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Junit for HonorAward Class
 *
 */
class HonorAwardTest {
	HonorAward honorAward = new HonorAward();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	public void testSetAndGetDateOfAward() {

		honorAward.setDateOfAward("2022-01-01");
		assertEquals("2022-01-01", honorAward.getDateOfAward());
	}

	@Test
	public void testSetAndGetDisclosure() {

		honorAward.setDisclosure("Some disclosure information");
		assertEquals("Some disclosure information", honorAward.getDisclosure());
	}

	@Test
	public void testSetAndGetHonorAwardName() {

		honorAward.setHonorAwardName("Some honor/award name");
		assertEquals("Some honor/award name", honorAward.getHonorAwardName());
	}

	@Test
	public void testSetAndGetOrganization() {

		honorAward.setOrganization("Some organization name");
		assertEquals("Some organization name", honorAward.getOrganization());
	}

}
