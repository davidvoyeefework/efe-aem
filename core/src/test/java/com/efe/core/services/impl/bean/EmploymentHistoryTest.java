package com.efe.core.services.impl.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Junit for EmploymentHistory Class
 *
 */
class EmploymentHistoryTest {
	
	EmploymentHistory employmentHistory = new EmploymentHistory();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
    void testSetAndGetCompanyName() {
        String companyName = "ABC Inc.";
        employmentHistory.setCompanyName(companyName);
        assertEquals(companyName, employmentHistory.getCompanyName());
    }

    @Test
    void testSetAndGetCurrent() {
        boolean current = true;
        employmentHistory.setCurrent(current);
        assertTrue(employmentHistory.isCurrent());
    }

    @Test
    void testSetAndGetStartDate() {
        String startDate = "2021-01-01";
        employmentHistory.setStartDate(startDate);
        assertEquals(startDate, employmentHistory.getStartDate());
    }

    @Test
    void testSetAndGetEndDate() {
        String endDate = "2022-01-01";
        employmentHistory.setEndDate(endDate);
        assertEquals(endDate, employmentHistory.getEndDate());
    }

    @Test
    void testSetAndGetJobTitle() {
        String jobTitle = "Manager";
        employmentHistory.setJobTitle(jobTitle);
        assertEquals(jobTitle, employmentHistory.getJobTitle());
    }


}
