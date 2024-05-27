package org.western.frontend;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * High Scores used to display High Score Table
 *
 * @author Xi Wang
 */
public class HighScores {
    private final SimpleIntegerProperty rank;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty score;

    /**
     * Constructor for HighScores
     * @param rank rank of the player
     * @param score score of the player
     * @param name name of the player
     */
    public HighScores(int rank, int score, String name) {
        this.rank = new SimpleIntegerProperty(rank);
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleIntegerProperty(score);
    }

    /**
     * Get the rank of the player
     * @return rank of the player
     */
    public int getRank() { return rank.get(); }

    /**
     * Get the name of the player
     * @return name of the player
     */
    public String getName() { return name.get(); }

    /**
     * Get the score of the player
     * @return score of the player
     */
    public int getScore() { return score.get(); }
}
