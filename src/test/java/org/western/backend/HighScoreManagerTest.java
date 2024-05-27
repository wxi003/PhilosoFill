package org.western.backend;

import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the HighScoreManager class.
 * Tests are designed to assert the correctness of HighScoreManager's methods functionalities.
 *
 * @see HighScoreManager
 * @author Xi Wang
 */
class HighScoreManagerTest {

    private HighScoreManager highScoreManager;

    /**
     * Reset the score list for each test
     * This method clear the score_entries.json for test use.
     */
    @BeforeEach
    void setUp() {
        highScoreManager = HighScoreManager.getInstance();
        highScoreManager.resetScores();
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
     * Test of updateScores method, of class HighScoreManager.
     * Checks if the method updates the score entry correctly.
     */
    @Test
    void updateScores() {
        System.out.println("updateScores");
        ScoreEntry newScoreEntry = new ScoreEntry("testUser", 1, 10);
        highScoreManager.updateScores(newScoreEntry);
        int levelScore = highScoreManager.getLevelScore("testUser", 1);
        assertEquals(10, levelScore, "Score should be updated to 10");

        ScoreEntry scoreEntry = new ScoreEntry("testUser", 1, 9);
        highScoreManager.updateScores(scoreEntry);
        int newLevelScore = highScoreManager.getLevelScore("testUser", 1);
        assertEquals(10, newLevelScore, "Score should be still 10");

        ScoreEntry entryScore = new ScoreEntry("testUser", 1, 11);
        highScoreManager.updateScores(entryScore);
        int levelScoreNew = highScoreManager.getLevelScore("testUser", 1);
        assertEquals(11, levelScoreNew, "Score should be updated to 11");
    }

    /**
     * Test of getTopScores method, of class HighScoreManager.
     * Checks if the method returns the top score list correctly
     */
    @Test
    void getTopScores() {
        System.out.println("getTopScores");
        ScoreEntry scoreEntry1 = new ScoreEntry("user1", 1, 15);
        ScoreEntry scoreEntry2 = new ScoreEntry("user2", 1, 20);
        highScoreManager.updateScores(scoreEntry1);
        highScoreManager.updateScores(scoreEntry2);

        List<Map.Entry<String, Integer>> topScores = highScoreManager.getTopScores();
        assertEquals(2, topScores.size(), "There should be two entries in the top scores list");
        assertEquals(20, topScores.get(0).getValue().intValue(), "The top score should be 20");
    }

    /**
     * Test of getTopScores method, of class HighScoreManager.
     * Checks whether the method returns an empty list if there is no data in score_entries.json
     */
    @Test
    void getTopScoresNoEntries() {
        System.out.println("getTopScoresNoEntries");

        List<Map.Entry<String, Integer>> topScores = highScoreManager.getTopScores();
        assertTrue(topScores.isEmpty(), "The top score list should be empty");
    }

    /**
     * Test of getLevelScore method, of class HighScoreManager.
     * Checks if the method returns the level score correctly
     */
    @Test
    void getLevelScore() {
        System.out.println("getLevelScore");
        ScoreEntry scoreEntry = new ScoreEntry("testUser", 1, 5);
        highScoreManager.updateScores(scoreEntry);

        int levelScore = highScoreManager.getLevelScore("testUser", 1);
        assertEquals(5, levelScore, "Level score should be 5");

        int nonExistentScore = highScoreManager.getLevelScore("testUser", 2);
        assertEquals(-1, nonExistentScore, "Non-existent score should return -1");
    }

    /**
     * Test of getInstance method, of class HighScoreManager.
     * Checks if there are only one instance of HighScoreManager exists.
     */
    @Test
    void getInstance() {
        System.out.println("getInstance");
        HighScoreManager instance1 = HighScoreManager.getInstance();
        HighScoreManager instance2 = HighScoreManager.getInstance();
        assertSame(instance1, instance2, "getInstance should return the same instance");
    }
}
