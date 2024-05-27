package org.western.backend;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player in the game.
 *
 * @author Chao Zhang
 */
public class Player {
    private final int id;
    private final String username;
    private final int role;
    private int unlock;
    private String collection;
    private static final int NUM_OF_PUZZLES = 45;

    /**
     * Constructor of the Player class.
     *
     * @param id         the id of the player
     * @param username   the username of the player
     * @param role       the role of the player
     * @param unlock     the number of puzzles the player has unlocked
     * @param collection the collection of the player
     */
    public Player(int id, String username, int role, int unlock, String collection) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.unlock = unlock;
        this.collection = collection;
    }

    /**
     * Get the id of the player.
     *
     * @return the id of the player
     */
    public int getId() {
        return id;
    }

    /**
     * Get the username of the player.
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the role of the player.
     *
     * @return the role of the player (1 for player, 2 for teacher, 3 for tester)
     */
    public int getRole() {
        return role;
    }

    /**
     * Get the number of levels the player has unlocked.
     *
     * @return the number of levels the player has unlocked
     */
    public int getUnlock() {
        return unlock;
    }

    /**
     * Get the collection list of the player.
     *
     * @return the collection Arraylist of the player
     */
    public ArrayList<Integer> getCollection() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < collection.length(); i++) {
            if (collection.charAt(i) == '1') {
                result.add(i + 1);
            }
        }
        return result;
    }

    /**
     * Set the number of levels the player has unlocked.
     *
     * @param unlock the number of levels the player has unlocked
     */
    public void setUnlock(int unlock) {
        this.unlock = unlock;
    }

    /**
     * Set the collection list of the player.
     *
     * @param collection the collection Arraylist of the player
     */
    public void setCollection(ArrayList<Integer> collection) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getNumOfPuzzles(); i++) {
            if (collection.contains(i + 1)) {
                sb.append("1");
            } else {
                sb.append("0");
            }
        }
        this.collection = sb.toString();
    }

    /**
     * Update the player's information in the database.
     */
    public void updatePlayer() {
        try {
            Gson gson = new Gson();
            List<Player> userList = readPlayers();
            userList.forEach(user -> {
                if (user.getId() == this.getId()) {
                    user.setUnlock(this.getUnlock());
                    user.setCollection(this.getCollection());
                }
            });
            String json = gson.toJson(userList);
            BufferedWriter out = new BufferedWriter(new FileWriter("user.json"));
            out.write(json);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Check if the player exists in the database.
     *
     * @param username the username of the player
     * @return the available id if the username doesn't exist, -1 otherwise
     */
    public static int checkPlayerExist(String username) {
        try {
            List<Player> userList = readPlayers();
            if (userList.isEmpty()) {
                return 1;
            }
            for (Player user : userList) {
                if (user.getUsername().equals(username)) {
                    return -1;
                }
            }
            return userList.get(userList.size() - 1).getId() + 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Add a new player to the json file.
     *
     * @param id         the id of the player
     * @param username   the username of the player
     * @param role       the role of the player
     * @param unlock     the number of puzzles the player has unlocked
     * @param collection the collection of the player
     */
    public static void addUser(int id, String username, int role, int unlock, String collection) {
        try {
            Gson gson = new Gson();
            List<Player> userList = readPlayers();
            userList.add(new Player(id, username, role, unlock, collection));
            String json = gson.toJson(userList);
            BufferedWriter out = new BufferedWriter(new FileWriter("user.json"));
            out.write(json);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Delete a player from the json file.
     *
     * @param id the id of the player
     */
    public static void deleteUser(int id) {
        try {
            Gson gson = new Gson();
            List<Player> userList = readPlayers();
            userList.removeIf(user -> user.getId() == id);
            String json = gson.toJson(userList);
            BufferedWriter out = new BufferedWriter(new FileWriter("user.json"));
            out.write(json);
            out.close();

            List<SavedGameEntries> savedEntryList = SavedGameEntries.getSavedGameEntryList();
            savedEntryList.removeIf(entry -> entry.getPlayerID() == id);
            // Update IDs of the saved game entries
            for (int i = 0; i < savedEntryList.size(); i++) {
                savedEntryList.get(i).setId(i + 1);
            }

            json = gson.toJson(savedEntryList);
            out = new BufferedWriter(new FileWriter("saved_game_entries.json"));
            out.write(json);
            out.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Read the players from the json file.
     *
     * @return the list of players
     */
    public static List<Player> readPlayers() {
//        String path = System.getProperty("user.dir");
//        System.out.println(path);
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("user.json");
            Type userListType = new TypeToken<List<Player>>() {
            }.getType();
            List<Player> userList = gson.fromJson(reader, userListType);
            reader.close();
            return userList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get the player by id.
     *
     * @param id the id of the player
     * @return the player class instance with the given id
     */
    public static Player getPlayerById(int id) {
        List<Player> userList = readPlayers();
        if (userList != null) {
            for (Player user : userList) {
                if (user.getId() == id) {
                    return user;
                }
            }
        }
        return null;
    }

    /**
     * Get the number of puzzles in the game.
     *
     * @return the number of puzzles in the game
     */
    public static int getNumOfPuzzles() {
        return NUM_OF_PUZZLES;
    }


}

