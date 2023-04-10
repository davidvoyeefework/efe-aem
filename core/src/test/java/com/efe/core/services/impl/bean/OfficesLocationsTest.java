package com.efe.core.services.impl.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Junit for OfficesLocations Class
 *
 */
class OfficesLocationsTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
    void testGettersAndSetters() {
        OfficesLocations officesLocations = new OfficesLocations();
        officesLocations.setId("1");
        officesLocations.setName("New York Office");
        officesLocations.setCity("New York");
        officesLocations.setState("NY");
        officesLocations.setZip("10001");

        assertEquals("1", officesLocations.getId());
        assertEquals("New York Office", officesLocations.getName());
        assertEquals("New York", officesLocations.getCity());
        assertEquals("NY", officesLocations.getState());
        assertEquals("10001", officesLocations.getZip());
    }
}
