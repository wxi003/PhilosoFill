package org.western.backend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for PuzzleRecord.
 * This class contains unit tests for the PuzzleRecord class methods.
 * It's assumed that a "puzzle.json" file exists with the appropriate data for testing.
 *
 * @author Chao Zhang
 */
class PuzzleRecordTest {
    /**
     * Test for getId method.
     * This test should verify that the getId method returns the correct id of the puzzle.
     *
     * @see PuzzleRecord
     * @author Chao Zhang
     */
    @Test
    void testGetId() {
        PuzzleRecord puzzleRecord = new PuzzleRecord(1, 1, "tp", "test puzzle", "source1", "author1");
        assertEquals(1, puzzleRecord.getId());
    }

    /**
     * Test for getLevel method.
     * This test should verify that the getLevel method returns the correct level of the puzzle.
     */
    @Test
    void testGetLevel() {
        PuzzleRecord puzzleRecord = new PuzzleRecord(1, 1, "tp", "test puzzle", "source1", "author1");
        assertEquals(1, puzzleRecord.getLevel());
    }

    /**
     * Test for getPuzzle method.
     * This test should verify that the getPuzzle method returns the correct puzzle string with missing characters.
     */
    @Test
    void testGetPuzzle() {
        PuzzleRecord puzzleRecord = new PuzzleRecord(1, 1, "tp", "test puzzle", "source1", "author1");
        assertEquals("tp", puzzleRecord.getPuzzle());
    }

    /**
     * Test for getSolution method.
     * This test should verify that the getSolution method returns the correct complete sentences of the puzzle.
     */
    @Test
    void testGetSolution() {
        PuzzleRecord puzzleRecord = new PuzzleRecord(1, 1, "tp", "test puzzle", "source1", "author1");
        assertEquals("test puzzle", puzzleRecord.getSolution());
    }

    /**
     * Test for getSource method.
     * This test should verify that the getSource method returns the correct source of the puzzle.
     */
    @Test
    void testGetSource() {
        PuzzleRecord puzzleRecord = new PuzzleRecord(1, 1, "tp", "test puzzle", "source1", "author1");
        assertEquals("source1", puzzleRecord.getSource());
    }

    /**
     * Test for getAuthor method.
     * This test should verify that the getAuthor method returns the correct author of the puzzle.
     */
    @Test
    void testGetAuthor() {
        PuzzleRecord puzzleRecord = new PuzzleRecord(1, 1, "tp", "test puzzle", "source1", "author1");
        assertEquals("author1", puzzleRecord.getAuthor());
    }

    /**
     * Test for findPuzzle method.
     * This test should verify that the findPuzzle static method returns the correct PuzzleRecord instance for a given id.
     */
    @Test
    void testFindPuzzle() {
        PuzzleRecord foundPuzzle = PuzzleRecord.findPuzzle(1);
        assertNotNull(foundPuzzle);
        assertEquals(1, foundPuzzle.getId());
    }
}