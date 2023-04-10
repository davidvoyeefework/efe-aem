package com.efe.core.services.impl.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Junit for Certifications Class
 *
 */
class CertificationsTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	Certifications certifications = new Certifications();

	@Test
	void testGetAbbreviation() {

		certifications.setAbbreviation("ABC");
		assertEquals("ABC", certifications.getAbbreviation());
	}

	@Test
	void testGetLegalComplianceDisclosure() {

		certifications.setLegalComplianceDisclosure("Some disclosure");
		assertEquals("Some disclosure", certifications.getLegalComplianceDisclosure());
	}

	@Test
	void testGetMarketingDisclosure() {

		certifications.setMarketingDisclosure("Some disclosure");
		assertEquals("Some disclosure", certifications.getMarketingDisclosure());
	}

	@Test
	void testGetName() {

		certifications.setName("Certification name");
		assertEquals("Certification name", certifications.getName());
	}

}
