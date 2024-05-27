package org.western.backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the Tester class.
 *
 * @see Tester
 * @author Chao Zhang
 */
class TesterTest {

    /**
     * Tests the static method verifyPassword to make sure it returns true for the correct password.
     */
    @Test
    void testVerifyPasswordSuccess() {
        assertTrue(Tester.verifyPassword("987654"));
        assertFalse(Teacher.verifyPassword("12345"));
    }

}