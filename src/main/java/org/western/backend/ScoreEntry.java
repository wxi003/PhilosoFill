package org.western.backend;

/**
 * Score Entry for players
 *
 * @author Xi Wang
 */
public class ScoreEntry {
    private final String username;
    private final int level;
    private int score;

    /**
     * Constructor: build new ScoreEntry instance
     *
     * @param playerUsername username of the player
     * @param playerLevel level of the score record
     * @param playerScore score of the player got
     */
    public ScoreEntry(String playerUsername, int playerLevel, int playerScore){
        username = playerUsername;
        level = playerLevel;
        score = playerScore;
    }

    /**
     * Get the username of the player who made the score entry.
     *
     * @return the username of the player
     */
    public String getUsername(){
        return username;
    }

    /**
     * Get the level of the score entry.
     *
     * @return the level of the score entry
     */
    public int getLevel(){
        return level;
    }

    /**
     * Get the score of the score entry.
     *
     * @return the score of the score entry
     */
    public int getScore(){
        return score;
    }


    /**
     * Set the score of the score entry.
     *
     * @param newScore the new score of the score entry
     */
    public void setScore(int newScore){
        score = newScore;
    }

}

