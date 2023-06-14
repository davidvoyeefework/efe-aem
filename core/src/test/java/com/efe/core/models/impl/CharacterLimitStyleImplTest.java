package com.efe.core.models.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class CharacterLimitStyleImplTest.
 */
class CharacterLimitStyleImplTest {

    /** The CharacterLimitStyleImpl. */
    @InjectMocks
    private CharacterLimitStyleImpl characterLimitStyle;

    /**
     * Sets the up.
     */
    @BeforeEach
    void setup() throws Exception{
        characterLimitStyle = new CharacterLimitStyleImpl();
        Field field = CharacterLimitStyleImpl.class.getDeclaredField("characterLimit");
        field.setAccessible(true);
        field.set(characterLimitStyle, 10);
    }

    /**
     * Get StyleValue test.
     */
    @Test
    void testGetStyleValue() {
        characterLimitStyle.init();
        String expectedStyleValue = "max-width:10ch; margin: 0 auto;";
        String actualStyleValue = characterLimitStyle.getStyleValue();
        assertEquals(expectedStyleValue, actualStyleValue);
    }

}
