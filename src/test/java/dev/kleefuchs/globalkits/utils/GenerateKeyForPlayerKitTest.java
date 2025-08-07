package dev.kleefuchs.globalkits.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GenerateKeyForPlayerKitTest {

    @Test
    public void testGenerateKeyForPlayerKitTest() {
        assertEquals(GenerateKeyForPlayerKit.generate("Test", "uwu"), "Test/uwu");
    }
}
