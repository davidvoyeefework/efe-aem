package com.efe.core.services.impl.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Junit for Education Class
 *
 */
class EducationTest {

	Education education = new Education();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetSetDegree() {

		education.setDegree("Bachelor's");
		assertEquals("Bachelor's", education.getDegree());
	}

	@Test
	void testGetSetMajor() {

		education.setMajor("Computer Science");
		assertEquals("Computer Science", education.getMajor());
	}

	@Test
	void testGetSetUniversity() {

		education.setUniversity("MIT");
		assertEquals("MIT", education.getUniversity());
	}

}
