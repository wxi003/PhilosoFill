package org.western.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the LevelSession class.
 * Tests are designed to assert the correctness of LevelSession's methods functionalities.
 *
 * @see LevelSession
 * @author Chao Zhang
 */
class LevelSessionTest {

    private LevelSession easySession;
    private LevelSession mediumSession;
    private LevelSession hardSession;
    private ArrayList<Integer> progress;

    /**
     * Sets up test data for each test method. This method initializes instances
     * of the LevelSession class with different parameters.
     */
    @BeforeEach
    void setUp() {
        progress = new ArrayList<>(List.of(1, 2, 3));
        easySession = new LevelSession(1, 10, false);
        mediumSession = new LevelSession(2, 0, progress, true);
        hardSession = new LevelSession(3, 30, new ArrayList<>(), false);
    }

    /**
     * Tests the getLevelDifficulty method.
     * Verifies if the method returns the correct level difficulty.
     */
    @Test
    void testGetLevelDifficulty() {
        assertEquals(1, easySession.getLevelDifficulty());
        assertEquals(2, mediumSession.getLevelDifficulty());
        assertEquals(3, hardSession.getLevelDifficulty());
    }

    /**
     * Tests the getLevelDifficultyName method.
     * Verifies if the method returns the correct difficulty name based on the level.
     */
    @Test
    void testGetLevelDifficultyName() {
        assertEquals("Easy", easySession.getLevelDifficultyName());
        assertEquals("Medium", mediumSession.getLevelDifficultyName());
        assertEquals("Hard", hardSession.getLevelDifficultyName());
    }

    /**
     * Tests the getAccumulatedScore method.
     * Checks if the method returns the correct accumulated score for the session.
     */
    @Test
    void testGetAccumulatedScore() {
        assertEquals(10, easySession.getAccumulatedScore());
        assertEquals(0, mediumSession.getAccumulatedScore());
        assertEquals(30, hardSession.getAccumulatedScore());
    }

    /**
     * Tests the getPassScore method.
     * Asserts if the method returns the correct pass score that is set for passing a level.
     */
    @Test
    void testGetPassScore() {
        assertEquals(46, easySession.getPassScore());
        assertEquals(46, mediumSession.getPassScore());
        assertEquals(46, hardSession.getPassScore());
    }

    /**
     * Tests the getProgress method.
     * Validates if the method returns the correct progress list for each session.
     */
    @Test
    void testGetProgress() {
        assertEquals(new ArrayList<>(), easySession.getProgress());
        assertEquals(progress, mediumSession.getProgress());
        assertEquals(new ArrayList<>(), hardSession.getProgress());
    }

    /**
     * Tests the updateAccumulatedScore method.
     * Confirms if the method correctly updates the player's accumulated score.
     */
    @Test
    void testUpdateAccumulatedScore() {
        easySession.updateAccumulatedScore(15);
        assertEquals(15, easySession.getAccumulatedScore());
    }

    /**
     * Tests the updateProgress method.
     * Ensures if the method correctly updates the list of completed puzzles.
     */
    @Test
    void testUpdateProgress() {
        int puzzleId = 4;
        easySession.updateProgress(puzzleId);
        assertTrue(easySession.getProgress().contains(puzzleId));
    }

    /**
     * Tests the getNextPuzzleId method.
     * Checks whether the method generates a valid next puzzle ID which is not already in the progress list.
     */
    @Test
    void testGetNextPuzzleId() {
        int puzzleId = easySession.getNextPuzzleId();
        assertFalse(easySession.getProgress().contains(puzzleId));
        assertTrue(puzzleId >= 1 && puzzleId < 16);
    }

    /**
     * Tests the getDebugStatus method.
     * Verifies whether the method accurately reflects the debug status of the session.
     */
    @Test
    void testGetDebugStatus() {
        assertFalse(easySession.getDebugStatus());
        assertTrue(mediumSession.getDebugStatus());
        assertFalse(hardSession.getDebugStatus());
    }
}