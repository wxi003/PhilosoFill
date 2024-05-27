package org.western.backend;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the Player class.
 * It's assumed that a "user.json" file exists with the appropriate data for testing.
 *
 * @see Player
 * @author Chao Zhang
 */
class PlayerTest {

    /**
     * Sets up test data for each test method.
     * This method initializes the "user.json" file with some test data.
     */
    @BeforeEach
    void setUp() {
        // Create a user.json file with some test data
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(new Player(1, "user1", 1, 10, "000100010001000"));
        testPlayers.add(new Player(2, "user2", 2, 20, "001010001000100"));
        savePlayers(testPlayers);
    }

    /**
     * Tears down test data after each test method.
     * This method clears the "user.json" file after each test.
     */
    @AfterEach
    void tearDown() throws IOException {
        // Cleanup the user.json file after tests
        new BufferedWriter(new FileWriter("user.json")).close(); // This effectively clears the file
    }

    /**
     * Test the getId method of the Player class.
     */
    @Test
    void testGetId() {
        Player player = new Player(1, "testUser", 1, 1, "");
        assertEquals(1, player.getId());
    }

    /**
     * Test the getUsername method of the Player class.
     */
    @Test
    void testGetUsername() {
        Player player = new Player(1, "testUser", 1, 1, "");
        assertEquals("testUser", player.getUsername());
    }

    /**
     * Test the getRole method of the Player class.
     */
    @Test
    void testGetRole() {
        Player player = new Player(1, "testUser", 1, 1, "");
        assertEquals(1, player.getRole());
    }

    /**
     * Test the getUnlock method of the Player class.
     */
    @Test
    void testGetUnlock() {
        Player player = new Player(1, "testUser", 1, 1, "");
        assertEquals(1, player.getUnlock());
    }

    /**
     * Test the getCollection method of the Player class.
     */
    @Test
    void testGetCollection() {
        Player player = new Player(1, "testUser", 1, 1, "10001");
        ArrayList<Integer> expResult = new ArrayList<>();
        expResult.add(1);
        expResult.add(5);
        assertEquals(expResult, player.getCollection());
    }

    /**
     * Test the setUnlock method of the Player class.
     */
    @Test
    void testSetUnlock() {
        Player player = new Player(1, "testUser", 1, 1, "");
        player.setUnlock(2);
        assertEquals(2, player.getUnlock());
    }

    /**
     * Test the setCollection method of the Player class.
     */
    @Test
    void testSetCollection() {
        Player player = new Player(1, "testUser", 1, 1, "");
        ArrayList<Integer> collection = new ArrayList<>();
        collection.add(1);
        collection.add(3);
        collection.add(5);
        player.setCollection(collection);
        assertEquals(collection, player.getCollection());
    }

    /**
     * Test the updatePlayer method of the Player class.
     */
    @Test
    void testUpdatePlayer() {
        Player player = Player.getPlayerById(1);
        assertNotNull(player);
        player.setUnlock(3);
        player.updatePlayer();

        Player updatedPlayer = Player.getPlayerById(1);
        assertEquals(3, updatedPlayer.getUnlock());
    }

    /**
     * Test the checkPlayerExist method of the Player class.
     */
    @Test
    void testCheckPlayerExist() {
        String testUsername = "newUser";
        int newId = Player.checkPlayerExist(testUsername);
        assertNotEquals(-1, newId);

        Player.addUser(newId, testUsername, 1, 0, "000000000000000");
        int shouldBeNegativeOne = Player.checkPlayerExist(testUsername);
        assertEquals(-1, shouldBeNegativeOne);
    }

    /**
     * Test the addUser method of the Player class.
     */
    @Test
    void testAddUser() {
        String testUsername = "newUser111";
        int newId = Player.checkPlayerExist(testUsername);
        assertNotEquals(-1, newId);

        Player.addUser(newId, testUsername, 1, 1, "000000000000000");
        Player newUser = Player.getPlayerById(newId);
        assertNotNull(newUser);
        assertEquals(testUsername, newUser.getUsername());
    }

    /**
     * Test the deleteUser method of the Player class.
     */
    @Test
    void testDeleteUser() {
        int testId = 1;
        Player.deleteUser(testId);
        Player deletedPlayer = Player.getPlayerById(testId);
        assertNull(deletedPlayer);
    }

    /**
     * Test the readPlayers method of the Player class.
     */
    @Test
    void testReadPlayers() {
        List<Player> players = Player.readPlayers();
        assertNotNull(players);
        assertFalse(players.isEmpty());
    }

    /**
     * Test the getPlayerById method of the Player class.
     */
    @Test
    void testGetPlayerById() {
        Player player = Player.getPlayerById(1);
        assertNotNull(player);
        assertEquals(1, player.getId());
    }

    /**
     * Test the getNumOfPuzzles method of the Player class.
     */
    @Test
    void testNumOfPuzzles() {
        assertEquals(45, Player.getNumOfPuzzles());
    }

    private static void savePlayers(List<Player> players) {
        // Helper method to save players list to the file
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("user.json"));
            Gson gson = new Gson();
            out.write(gson.toJson(players));
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}