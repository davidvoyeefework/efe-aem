package com.efe.core.services.impl.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Junit for PrimaryOfficeTest Class
 *
 */
class PrimaryOfficeTest {

	@BeforeEach
	void setUp() throws Exception {
	}


    @Test
    void testSettersAndGetters() {
        PrimaryOffice office = new PrimaryOffice();
        office.setId("123");
        office.setName("Main Office");
        office.setCity("New York");
        office.setState("NY");
        office.setZip("10001");

        assertEquals("123", office.getId());
        assertEquals("Main Office", office.getName());
        assertEquals("New York", office.getCity());
        assertEquals("NY", office.getState());
        assertEquals("10001", office.getZip());
    }
}
