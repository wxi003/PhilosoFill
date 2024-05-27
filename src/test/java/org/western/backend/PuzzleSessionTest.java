package org.western.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for PuzzleSession.
 * This class contains unit tests for the PuzzleSession class methods.
 * It's assumed that a "puzzle.json" file exists with the appropriate data for testing.
 *
 * @see PuzzleSession
 * @author Chao Zhang
 */
class PuzzleSessionTest {

    private PuzzleSession puzzleSession;

    /**
     * Sets up the environment for testing, creates an instance of PuzzleSession
     * with a sample puzzle ID.
     */
    @BeforeEach
    void setUp() {
        puzzleSession = new PuzzleSession(1);
    }

    /**
     * Tests the getPuzzleId method of the PuzzleSession class.
     * This test verifies if the method returns the correct puzzle's id.
     */
    @Test
    void testGetPuzzleId() {
        assertEquals(1, puzzleSession.getPuzzleId());
    }

    /**
     * Tests the getPuzzleScore method of the PuzzleSession class.
     * This test ensures that the method returns the correct initial score.
     */
    @Test
    void testGetPuzzleScore() {
        assertEquals(10, puzzleSession.getPuzzleScore());
    }

    /**
     * Tests the getAttempt method of the PuzzleSession class.
     * This test checks if the method returns the correct number of failed attempts.
     */
    @Test
    void testGetAttempt() {
        assertEquals(0, puzzleSession.getAttempt());
    }

    /**
     * Tests the updatePuzzleScore method of the PuzzleSession class.
     * This test asserts the updated puzzle score based on hints used and failed attempts.
     */
    @Test
    void testUpdatePuzzleScore() {
        puzzleSession.useHint();
        puzzleSession.updateAttempt();
        puzzleSession.updatePuzzleScore();
        assertEquals(8, puzzleSession.getPuzzleScore());
    }

    /**
     * Tests the updateAttempt method of the PuzzleSession class.
     * This test confirms the attempt count is incremented correctly.
     */
    @Test
    void testUpdateAttempt() {
        puzzleSession.updateAttempt();
        assertEquals(1, puzzleSession.getAttempt());
    }

    /**
     * Tests the useHint method of the PuzzleSession class.
     * This test verifies that the hint usage is recorded correctly.
     */
    @Test
    void testUseHint() {
        puzzleSession.useHint();
        puzzleSession.updatePuzzleScore(); // Score should reduce by hint penalty
        assertEquals(9, puzzleSession.getPuzzleScore());
    }

    /**
     * Tests the reset method of the PuzzleSession class.
     * This test ensures all score-related information is reset to default values.
     */
    @Test
    void testReset() {
        puzzleSession.useHint();
        puzzleSession.updateAttempt();
        puzzleSession.reset();
        assertEquals(10, puzzleSession.getPuzzleScore());
        assertEquals(0, puzzleSession.getAttempt());
        // Assuming hint is private and there is no getter for it, so it's not tested directly.
    }

    /**
     * Tests the findPuzzle method of the PuzzleSession class.
     * This test ensures that the method returns the correct PuzzleRecord instance for the puzzle ID.
     */
    @Test
    void testFindPuzzle() {
        assertNotNull(puzzleSession.findPuzzle());
    }
}