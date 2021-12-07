package model;

import java.util.ArrayList;

public class User {
    private ArrayList<User> users = new ArrayList<>();


    public static boolean usernameExists(String username) {
        return false;
    }

    public static boolean emailExists(String email) {
        return false;
    }

    public static User createUser(String username, String password, String email) {

        return null;
    }

    /**
     * @return found user or null if not found
     */
    public static User getUser(String username, String password) {
        return null;
    }
}
