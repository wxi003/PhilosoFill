package org.western.backend;

/**
 * Tester class extends Teacher class. It has the password related to level selection.
 *
 * @author Chao Zhang
 */
public class Tester extends Teacher{
    private static final int role = 3;

    private static final String password = "987654";

    /**
     * Constructor for Tester class
     * @param id the id of the tester
     * @param username the username of the tester
     * @param role the role of the tester
     * @param unlock the unlock status of the tester
     * @param collection the collection of the tester
     */
    public Tester(int id, String username, int role, int unlock, String collection) {
        super(id, username, role, unlock, collection);
    }

    /**
     * Verify the password
     * @param input the input string
     * @return the matching result of the input and the password
     */
    public static Boolean verifyPassword(String input){
        return input.equals(password);
    }
}

