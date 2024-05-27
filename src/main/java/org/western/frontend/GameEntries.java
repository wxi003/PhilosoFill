package org.western.frontend;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * This class is used to create a GameEntries object that contains the record number, level, progress, and score of a game.
 *
 * @author Xi Wang
 */
public class GameEntries {
    private final SimpleIntegerProperty recordNum;
    private final SimpleStringProperty level;
    private final SimpleStringProperty progress;
    private final SimpleIntegerProperty score;

    private final int id;
    private final ArrayList<Integer> savedGameList;

    /**
     * Constructor for GameEntries
     * @param recordNum record number
     * @param level level of the saved game
     * @param progress progress of the saved game
     * @param score score of the saved game
     * @param list list of saved games
     */
    public GameEntries(int recordNum, String level, String progress, int score, ArrayList<Integer> list,int id) {
        this.recordNum = new SimpleIntegerProperty(recordNum);
        this.level = new SimpleStringProperty(level);
        this.progress = new SimpleStringProperty(progress);
        this.score = new SimpleIntegerProperty(score);
        this.savedGameList = list;
        this.id = id;
    }

    /**
     * Get Record Number of the saved game.
     * @return record number of the saved game.
     */
    public int getRecordNum() { return recordNum.get(); }

    /**
     * Get level of the saved game.
     * @return level of the saved game.
     */
    public String getLevel() { return level.get(); }

    /**
     * Get progress of the saved game.
     * @return progress of the saved game.
     */
    public String getProgress() { return progress.get(); }

    /**
     * Get score of the saved game.
     * @return score of the saved game
     */
    public int getScore(){return score.get();}

    /**
     * Get saved game list.
     * @return saved game list
     */
    public ArrayList<Integer> getSavedGameList(){return savedGameList;}

    /**
     * Get saved game id.
     * @return saved game id
     */
    public int getId(){return id;}
}
