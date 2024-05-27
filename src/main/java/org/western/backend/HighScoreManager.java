
package org.western.backend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * High score table manager using Singleton design pattern
 *
 * @author Xi Wang
 */
public class HighScoreManager {
    private List<ScoreEntry> scoreList;

    /** only one instance of HighScoreManager */
    private static HighScoreManager instance;

    /**
     * Initialization: read data from score_entries.json
     */
    private HighScoreManager() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("score_entries.json")) {
            Type listType = new TypeToken<List<ScoreEntry>>() {}.getType();
            scoreList = gson.fromJson(reader, listType);
            if (scoreList == null) {
                scoreList = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            scoreList = new ArrayList<>();
        }
    }

    /**
     * Update the score list with a new entry
     *
     * @param newEntry the new score entry
     */
    public void updateScores(ScoreEntry newEntry) {
        boolean updated = false;
        for (ScoreEntry entry : scoreList) {
            // Update for same username and level only when the new score is higher than the original
            // No update for equal or smaller new scores
            if (entry.getUsername().equals(newEntry.getUsername()) && entry.getLevel() == newEntry.getLevel()) {
                if (entry.getScore() < newEntry.getScore()) {
                    entry.setScore(newEntry.getScore());
                }
                updated = true;
                break;
            }
        }
        // Add a new entry if it is not an existed score entry
        if (!updated) {
            scoreList.add(newEntry);
        }
        saveScores();
    }

    /**
     * Get the top score list of all players
     *
     * @return top score list
     */
    public List<Map.Entry<String, Integer>> getTopScores() {
        // This map stores the scores for each player.
        // The key is the player's username (String), and
        // the value is another map, where the key is the level (Integer), and the value is the highest score for that level (Integer).
        Map<String, Map<Integer, Integer>> playerScores = new HashMap<>();

        // Group scores by player and level, keeping the highest score for each level
        for (ScoreEntry entry : scoreList) {
            // If the player doesn't have a map entry yet, it creates a new empty map for them.
            playerScores.putIfAbsent(entry.getUsername(), new HashMap<>());
            // a map where the key is the level (e.g., 1 for easy, 2 for medium, 3 for hard)
            // and the value is the highest score the player has achieved at that level.
            Map<Integer, Integer> levelScores = playerScores.get(entry.getUsername());

            levelScores.put(entry.getLevel(), entry.getScore());
        }

        // Sum up the highest scores for each level for each player
        Map<String, Integer> totalScores = new HashMap<>();
        for (Map.Entry<String, Map<Integer, Integer>> playerEntry : playerScores.entrySet()) {
            int totalScore = playerEntry.getValue().values().stream().mapToInt(Integer::intValue).sum();
            totalScores.put(playerEntry.getKey(), totalScore);
        }

        // Sort the total scores in descending order and return the list
        return totalScores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());
    }

    /**
     * Get the score of a player for a specific level
     *
     * @param username username of the player
     * @param levelDifficulty level of the game
     * @return score of the player for the level
     */
    public int getLevelScore(String username, int levelDifficulty ){
        for(ScoreEntry entry: scoreList){
            if(entry.getUsername().equals(username) && entry.getLevel() == levelDifficulty){
                return entry.getScore();
            }
        }
        // No score was founded for the player and level
        return -1;
    }

    /**
     * Save the score list to score_entries.json
     */
    private void saveScores() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("score_entries.json")) {
            gson.toJson(scoreList, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * provide a global point of access to the HighScoreManager object.
     *
     * @return instance of HighScoreManager class
     */
    public static HighScoreManager getInstance() {
        if (instance == null) {
            instance = new HighScoreManager();
        }
        return instance;
    }

    /**
     * Unit test usage: Reset the high score table for each test
     */
    public void resetScores() {
        scoreList.clear();
    }
}

