package org.western.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Saved Game Entries for players
 *
 * @author Xi Wang
 * @author Yuqian Sun
 */

public class SavedGameEntries {

    // add the @Expose annotation to the fields need to be serialized
    @Expose
    private int id;
    @Expose
    private int playerID;
    @Expose
    private int accumulatedScore;
    @Expose
    private int level;
    @Expose
    private ArrayList<Integer> progress;

    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    /**
     * Constructor: build new SavedGameEntries instance
     * Initialization: read data from saved_game_entries.json and initialize savedEntryList
     *
     * @param playerID player ID
     * @param accumulatedScore accumulatedScore
     * @param level level
     * @param progress progress
     */
    public SavedGameEntries(int playerID, int accumulatedScore, int level, ArrayList<Integer> progress) {
        this.id = getSavedGameEntryList().size() + 1;
        this.playerID = playerID;
        this.accumulatedScore = accumulatedScore;
        this.level = level;
        this.progress = progress;
    }

    /**
     * Get the id of the saved game entry.
     *
     * @return the id of the saved game entry
     */
    public int getID(){return id;}

    /**
     * Get the id of the player who made the saved game entry.
     *
     * @return the id of the player
     */
    public int getPlayerID(){return playerID;}

    /**
     * Set id for the saved game entry
     * @param id the id of the saved game entry
     */
    public void setId(int id){this.id = id;}

    /**
     * Get the accumulated score of the saved game entry.
     *
     * @return the accumulated score of the saved game entry
     */
    public int getAccumulatedScore() {
        return accumulatedScore;
    }

    /**
     * Get the level of the saved game entry.
     *
     * @return the level of the saved game entry
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get the progress list of the saved game entry.
     *
     * @return the progress Arraylist of the saved game entry
     */
    public ArrayList<Integer> getProgress() {
        return progress;
    }

    /**
     * Add a new saved game entry to the saved game entries list
     *
     * @param savedGamesEntry the new saved game entry
     */
    public static void addSavedGameEntry(SavedGameEntries savedGamesEntry){
        // ArrayList as a subclass of List can be treated as an instance of its superclass or interface.
        List<SavedGameEntries> savedEntryList = getSavedGameEntryList();
        savedEntryList.add(savedGamesEntry);
        try (FileWriter writer = new FileWriter("saved_game_entries.json")) {
            gson.toJson(savedEntryList, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a saved game entry from the saved game entries list
     *
     * @param id of the saved game entry need to be deleted
     */
    public static void deleteSavedGameEntry(int id){
        List<SavedGameEntries> savedEntryList = getSavedGameEntryList();

        for (SavedGameEntries entry: savedEntryList){
            if(entry.getID() == id){
                savedEntryList.remove(entry);
                break;
            }
        }

        // Update IDs of the saved game entries
        for (int i = 0; i < savedEntryList.size(); i++) {
            savedEntryList.get(i).setId(i + 1);
        }

        try (FileWriter writer = new FileWriter("saved_game_entries.json")) {
            gson.toJson(savedEntryList, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the saved game entries list
     *
     * @return the saved game entries list
     */
    public static List<SavedGameEntries> getSavedGameEntryList(){
        List<SavedGameEntries> savedEntryList;
        try (FileReader reader = new FileReader("saved_game_entries.json")) {
            Type listType = new TypeToken<List<SavedGameEntries>>() {}.getType();
            savedEntryList = gson.fromJson(reader, listType);
            return savedEntryList == null ? new ArrayList<>(): savedEntryList;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Get saved game entries for a certain player
     * @param playersID player's ID
     * @return savedEntryList
     */
    public static List<SavedGameEntries> getPlayerEntries(int playersID){
        List<SavedGameEntries> savedEntryList = getSavedGameEntryList();
        savedEntryList.removeIf(entry -> entry.getPlayerID() != playersID);
        return savedEntryList;
    }
}

