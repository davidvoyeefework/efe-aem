package com.efe.core.services.impl.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Junit for BusinessHours Class
 *
 */
class BusinessHoursTest {
	
	BusinessHours businessHours = new BusinessHours();
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
    public void testSetAndGetDay() {
        
        businessHours.setDay("Monday");
       assertEquals("Monday", businessHours.getDay());
    }
    
    @Test
    public void testSetAndGetOpeningHours() {
        businessHours.setOpeningHours("09:00 AM");
       assertEquals("09:00 AM", businessHours.getOpeningHours());
    }
    
    @Test
    public void testSetAndGetClosingHours() {
        businessHours.setClosingHours("05:00 PM");
        assertEquals("05:00 PM", businessHours.getClosingHours());
    }
    
    @Test
    public void testSetAndGetIsClosed() {
        businessHours.setClosed(true);
        assertTrue(businessHours.isClosed());
    }

}
