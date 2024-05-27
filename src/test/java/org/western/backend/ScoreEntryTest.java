package org.western.backend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the ScoreEntry class.
 * It's assumed that a "score_entries.json" file exists with the appropriate data for testing.
 *
 * @see ScoreEntry
 * @author Xi Wang
 */
class ScoreEntryTest {
    private ScoreEntry scoreEntry;

    /**
     * Sets up test data for each test method.
     * This method initializes the "score_entries.json" file with some test data.
     */
    @BeforeEach
    void setUp() {
        scoreEntry = new ScoreEntry("testUser", 1, 50);
    }

    /**
     * Tears down test data after each test method.
     * This method clears the "score_entries.json" file after each test.
     */
    @AfterEach
    void tearDown() throws IOException {
        new BufferedWriter(new FileWriter("score_entries.json")).close();
    }

    /**
     * Test of getUsername method, of class ScoreEntryTest.
     * Verifies if the method returns the correct username.
     */
    @Test
    void getUsername() {
        System.out.println("getUsername");
        assertEquals("testUser", scoreEntry.getUsername(), "Username should be 'testUser'");
    }

    /**
     * Test of getLevel method, of class ScoreEntryTest.
     * Verifies if the method returns the correct level.
     */
    @Test
    void getLevel() {
        System.out.println("getLevel");
        assertEquals(1, scoreEntry.getLevel(), "Level should be 1");
    }

    /**
     * Test of getScore method, of class ScoreEntryTest.
     * Verifies if the method returns the correct score.
     */
    @Test
    void getScore() {
        System.out.println("getScore");
        assertEquals(1, scoreEntry.getLevel(), "Score should be 50");
    }

    /**
     * Test of setScore method, of class ScoreEntryTest.
     * Verifies if the method set the score correctly.
     */
    @Test
    void setScore() {
        System.out.println("setScore");
        scoreEntry.setScore(60);
        assertEquals(60, scoreEntry.getScore(), "Score should be updated to 60");
    }
}