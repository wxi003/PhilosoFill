package org.western.backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the Teacher class.
 *
 * @see Teacher
 * @author Chao Zhang
 */
class TeacherTest {

    /**
     * Tests the static method verifyPassword to make sure it returns true for the correct password.
     */
    @Test
    void testVerifyPasswordSuccess() {
        assertTrue(Teacher.verifyPassword("123456"));
        assertFalse(Teacher.verifyPassword("12345"));
    }

}