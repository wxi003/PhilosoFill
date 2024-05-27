package org.western.backend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * PuzzleRecord class represents a puzzle record.
 *
 * @author Chao Zhang
 */
public class PuzzleRecord {
    private final int id;
    private final int level;
    private final String puzzle;
    private final String solution;
    private final String source;
    private final String author;

    /**
     * Constructor of PuzzleRecord.
     *
     * @param id       the id of the puzzle
     * @param level    the level of the puzzle
     * @param puzzle   the missing characters of the puzzle
     * @param solution the complete sentences of the puzzle
     * @param source   the source of the puzzle
     * @param author   the author of the puzzle
     */
    public PuzzleRecord(int id, int level, String puzzle, String solution, String source, String author) {
        this.id = id;
        this.level = level;
        this.puzzle = puzzle;
        this.solution = solution;
        this.source = source;
        this.author = author;
    }

    /**
     * Get the id of the puzzle.
     *
     * @return the id of the puzzle
     */
    public int getId() {
        return id;
    }

    /**
     * Get the level of the puzzle.
     *
     * @return the level of the puzzle
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get the missing characters of the puzzle.
     *
     * @return the missing characters of the puzzle
     */
    public String getPuzzle() {
        return puzzle;
    }

    /**
     * Get the complete sentences of the puzzle.
     *
     * @return the complete sentences of the puzzle
     */
    public String getSolution() {
        return solution;
    }

    /**
     * Get the source of the puzzle.
     *
     * @return the source of the puzzle
     */
    public String getSource() {
        return source;
    }

    /**
     * Get the author of the puzzle.
     *
     * @return the author of the puzzle
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Find the puzzle by id.
     *
     * @param puzzleId the id of the puzzle
     * @return the puzzleRecord class instance of the puzzle with the given id
     */
    public static PuzzleRecord findPuzzle(int puzzleId) {
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

