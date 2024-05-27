package org.western.backend;

/**
 * Teacher class extends Player class. It has the password related to tracking players' progress.
 *
 * @author Chao Zhang
 */
public class Teacher extends Player{
    private static final int role = 2;
    private static final String password = "123456";

    /**
     * Constructor for Teacher class
     * @param id the id of the teacher
     * @param username the username of the teacher
     * @param role the role of the teacher
     * @param unlock the unlock status of the teacher
     * @param collection the collection of the teacher
     */
    public Teacher(int id, String username, int role, int unlock, String collection) {
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

