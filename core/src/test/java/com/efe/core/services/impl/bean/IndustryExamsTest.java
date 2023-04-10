package com.efe.core.services.impl.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Junit for IndustryExams Class
 *
 */
class IndustryExamsTest {
	IndustryExams industryExams = new IndustryExams();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
    void testGetExamNameShort() {
        
        industryExams.setExamNameShort("ABC");
        assertEquals("ABC", industryExams.getExamNameShort());
    }
    
    @Test
    void testGetExamNameLong() {
        industryExams.setExamNameLong("Advanced Business Certification");
        assertEquals("Advanced Business Certification", industryExams.getExamNameLong());
    }
}
