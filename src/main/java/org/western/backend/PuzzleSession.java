package org.western.backend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * PuzzleSession class is used to store the score-related information of the current puzzle session.
 *
 * @author Chao Zhang
 */
public class PuzzleSession {
    private int puzzleId;
    private int hint = 0;
    private int puzzleScore = 10;
    private int attempt = 0;

    /**
     * Constructor of PuzzleSession class.
     *
     * @param puzzleId the id of the puzzle
     */
    public PuzzleSession(int puzzleId) {
        this.puzzleId = puzzleId;
    }

    /**
     * Get the id of the puzzle.
     *
     * @return the id of the puzzle
     */
    public int getPuzzleId() {
        return puzzleId;
    }

    /**
     * Get the scores of the puzzle session.
     *
     * @return the scores of the puzzle session
     */
    public int getPuzzleScore() {
        return puzzleScore;
    }

    /**
     * Get the failed attempts of the puzzle session.
     *
     * @return the failed attempts of the puzzle session
     */
    public int getAttempt() {
        return attempt;
    }

    /**
     * Update the scores of the puzzle session.
     *
     */
    public void updatePuzzleScore() {
        int minus = (hint) * 1 + attempt * 1;
        this.puzzleScore = 10 - minus;
        if (this.puzzleScore < 6) {
            this.puzzleScore = 6;
        }
    }

    /**
     * Update the number of failed attempts of the puzzle session.
     *
     */
    public void updateAttempt() {
        this.attempt++;
    }

    /**
     * Update the hints used in the puzzle session.
     *
     */
    public void useHint() {
        this.hint = 1;
    }

    /**
     * reset score-related information of the puzzle session.
     *
     */
    public void reset() {
        this.hint = 0;
        this.puzzleScore = 10;
        this.attempt = 0;
    }

    /**
     * Find the puzzle of the puzzle session.
     *
     * @return the puzzleRecord class instance with the puzzle id in the puzzle session
     */
    public PuzzleRecord findPuzzle() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("puzzle.json");
            Type puzzleListType = new TypeToken<List<PuzzleRecord>>() {}.getType();
            List<PuzzleRecord> puzzleList = gson.fromJson(reader, puzzleListType);
            reader.close();
            for (PuzzleRecord puzzle : puzzleList) {
                if (puzzle.getId() == puzzleId) {
                    return puzzle;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}

