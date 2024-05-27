package org.western.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the SavedGameEntries class.
 * It's assumed that a "saved_game_entries.json" file exists with the appropriate data for testing.
 *
 * @see SavedGameEntries
 * @author Xi Wang
 */
class SavedGameEntriesTest {

    private SavedGameEntries savedGameEntry;

    /**
     * Sets up test data for each test method.
     * This method initializes the "saved_game_entries.json" file with some test data.
     */
    @BeforeEach
    void setUp() {
        ArrayList<Integer> progress = new ArrayList<>();
        progress.add(1);
        progress.add(2);
        savedGameEntry = new SavedGameEntries(200, 80, 1, progress);
        savedGameEntry.setId(100);
    }

    /**
     * Tests the getID method, of class SavedGameEntries.
     * Verifies if the method returns the correct id of saved game entry.
     */
    @Test
    void getID() {
        System.out.println("getID");
        assertEquals(100, savedGameEntry.getID(), "ID should be 100");
    }

    /**
     * Tests the getPlayerID method, of class SavedGameEntries.
     * Verifies if the method returns the correct player ID of saved game entry.
     */
    @Test
    void getPlayerID() {
        System.out.println("getPlayerID");
        assertEquals(200, savedGameEntry.getPlayerID(), "Player ID should be 200");
    }

    /**
     * Tests the setId method, of class SavedGameEntries.
     * Verifies if the method set the id of saved game entry correctly.
     */
    @Test
    void setId() {
        System.out.println("setId");
        savedGameEntry.setId(101);
        assertEquals(101, savedGameEntry.getID(), "ID should be 201");
    }

    /**
     * Tests the getAccumulatedScore method, of class SavedGameEntries.
     * Verifies if the method returns the correct accumulated score of saved game entry.
     */
    @Test
    void getAccumulatedScore() {
        System.out.println("getAccumulatedScore");
        assertEquals(80, savedGameEntry.getAccumulatedScore(), "Accumulated score should be 80");
    }

    /**
     * Tests the getLevel method, of class SavedGameEntries.
     * Verifies if the method returns the correct level of saved game entry.
     */
    @Test
    void getLevel() {
        System.out.println("getLevel");
        assertEquals(1, savedGameEntry.getLevel(), "Level should be 1");
    }

    /**
     * Tests the getProgress method, of class SavedGameEntries.
     * Verifies if the method returns the correct progress of saved game entry.
     */
    @Test
    void getProgress() {
        System.out.println("getProgress");
        assertEquals(2, savedGameEntry.getProgress().size(), "Progress should have 2 elements");
        assertEquals(1,savedGameEntry.getProgress().get(0),"Saved progress list should have puzzle id 1 at index 0");
        assertEquals(2,savedGameEntry.getProgress().get(1),"Saved progress list should have puzzle id 2 at index 1");
    }

    /**
     * Tests the addSavedGameEntry and deleteSavedGameEntry method, of class SavedGameEntries.
     * Verifies if these two methods add and delete the test data in saved_game_entries.json correctly.
     */
    @Test
    void addAndDeleteSavedGameEntry() {
        System.out.println("addAndDeleteSavedGameEntry");
        SavedGameEntries.addSavedGameEntry(savedGameEntry);
        SavedGameEntries.deleteSavedGameEntry(savedGameEntry.getID());
        List<SavedGameEntries> savedEntryList = SavedGameEntries.getSavedGameEntryList();
        assertFalse(savedEntryList.contains(savedGameEntry),"Saved Game Entry with id 101 should already be deleted");
    }

    /**
     * Tests the getSavedGameEntryList method, of class SavedGameEntries.
     * Verifies if the method returns the correct saved game entry list.
     */
    @Test
    void getSavedGameEntryList() {
        System.out.println("getSavedGameEntryList");
        List<SavedGameEntries> savedEntryList = SavedGameEntries.getSavedGameEntryList();
        assertNotEquals(0,savedEntryList.size(),"Saved entry list should not be empty");
    }

    /**
     * Tests the getPlayerEntries method, of class SavedGameEntries.
     * Verifies if the method returns the correct saved game entries list of the selected player.
     */
    @Test
    void getPlayerEntries() {
        System.out.println("getPlayerEntries");
        List<SavedGameEntries> playerEntries = SavedGameEntries.getPlayerEntries(200);
        assertTrue(playerEntries.isEmpty(), "Player entries list should be empty for the player after deletion");
    }
}