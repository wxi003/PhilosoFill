package org.western.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * LevelSession class is used to manage the session of a player in a level
 *
 * @author Chao Zhang
 */
public class LevelSession {
    private int levelDifficulty;
    private int accumulatedScore;
    private int passScore = 46;
    private ArrayList<Integer> progress;
    private Boolean debugStatus;

    /**
     * Constructor for LevelSession
     *
     * @param levelDifficulty  the difficulty of the level
     * @param accumulatedScore the accumulated score of the player
     * @param debugStatus      the debug status of the player
     */
    public LevelSession(int levelDifficulty, int accumulatedScore, Boolean debugStatus) {
        this.levelDifficulty = levelDifficulty;
        this.accumulatedScore = accumulatedScore;
        this.progress = new ArrayList<>();
        this.debugStatus = debugStatus;
    }

    /**
     * Constructor for LevelSession
     *
     * @param levelDifficulty  the difficulty of the level
     * @param accumulatedScore the accumulated score of the player
     * @param progress         the progress of the player
     * @param debugStatus      the debug status of the player
     */
    public LevelSession(int levelDifficulty, int accumulatedScore, ArrayList<Integer> progress, Boolean debugStatus) {
        this.levelDifficulty = levelDifficulty;
        this.accumulatedScore = accumulatedScore;
        this.progress = progress;
        this.debugStatus = debugStatus;
    }

    /**
     * Get the difficulty of the level
     *
     * @return the difficulty of the level
     */
    public int getLevelDifficulty() {
        return levelDifficulty;
    }

    /**
     * Get the name of the difficulty of the level
     *
     * @return the name of the difficulty of the level
     */
    public String getLevelDifficultyName() {
        if (levelDifficulty == 1) {
            return "Easy";
        } else if (levelDifficulty == 2) {
            return "Medium";
        } else {
            return "Hard";
        }
    }

    /**
     * Get the accumulated score of the player
     *
     * @return the accumulated score of the player
     */
    public int getAccumulatedScore() {
        return accumulatedScore;
    }

    /**
     * Get the pass score of the level
     *
     * @return the pass score of the level
     */
    public int getPassScore() {
        return passScore;
    }

    /**
     * Get the progress list of the player
     *
     * @return the progress Array list of the player
     */
    public ArrayList<Integer> getProgress() {
        return progress;
    }

    /**
     * Update the accumulated score of the player
     *
     * @param score the score to be updated
     */
    public void updateAccumulatedScore(int score) {
        this.accumulatedScore = score;
    }

    /**
     * Update the progress of the player
     *
     * @param puzzleId the puzzle id to be updated
     */
    public void updateProgress(int puzzleId) {
        progress.add(puzzleId);
    }

    /**
     * Generate the next puzzle id for the player
     *
     * @return the next puzzle id for the player
     */
    public int getNextPuzzleId() {
        int puzzleId;
        do {
            if (levelDifficulty == 1) {
                puzzleId = ThreadLocalRandom.current().nextInt(1, 16);
            } else if (levelDifficulty == 2) {
                puzzleId = ThreadLocalRandom.current().nextInt(16, 31);
            } else {
                puzzleId = ThreadLocalRandom.current().nextInt(31, 46);
            }
        } while (progress.contains(puzzleId));
        return puzzleId;
    }

    /**
     * Get the debug status of the player
     *
     * @return the debug status of the player
     */
    public Boolean getDebugStatus() {
        return debugStatus;
    }


}

