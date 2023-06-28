package com.efe.core.models.impl;

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
     * Get StyleValue test.
     */
    @Test
    void testGetStyleValue() throws Exception {
        characterLimitStyle = new CharacterLimitStyleImpl();
        Field field = CharacterLimitStyleImpl.class.getDeclaredField("characterLimit");
        field.setAccessible(true);
        field.set(characterLimitStyle, 10);
        characterLimitStyle.init();
        String expectedStyleValue = "max-width:10ch;";
        String actualStyleValue = characterLimitStyle.getStyleValue();
        assertEquals(expectedStyleValue, actualStyleValue);
    }

    /**
     * Get StyleValue test for null.
     */
    @Test
    void testGetStyleValueNull() throws Exception{
        characterLimitStyle = new CharacterLimitStyleImpl();
        Field field = CharacterLimitStyleImpl.class.getDeclaredField("characterLimit");
        field.setAccessible(true);
        field.set(characterLimitStyle, 0);
        characterLimitStyle.init();
        String expectedStyleValue = "";
        String actualStyleValue = characterLimitStyle.getStyleValue();
        assertEquals(expectedStyleValue, actualStyleValue);
    }

}
