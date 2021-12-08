package model;

import java.util.ArrayList;


public class User {

    private static ArrayList<User> allUsers = new ArrayList<>();
    private String lastName;
    private String birthday;
    private String username;
    private String password1;
    private String password2;
    private String email;
    private ArrayList<Task> taskOfUser;

    private class teamMember {

    }
    private class teamLeader{

    }
    private class systemAdministrator{

    }

    public User(String username,String password1,String password2, String email ) {
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
        this.email = email;
        this.taskOfUser = new ArrayList<>();
        allUsers.add(this);
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }

    public String getEmail() {
        return email;
    }

    public static boolean usernameExists(String username) {
        return false;
    }

    public static boolean emailExists(String email) {
        return false;
    }

    public static User createUser(String username, String password, String email) {

        return null;
    }
}
