package model;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Serializable {
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static User admin;
    private ArrayList<String> oldPasswords = new ArrayList<>();
    private ArrayList<Team> teams = new ArrayList<>();
    private String lastName;
    private String firstname;
    private String birthday = "Not Entered Yet";
    private String username;
    private String password;
    private String email;
    private int score = 0;
    private ArrayList<LocalDate> logs = new ArrayList<>();
    private Type type;
    private ArrayList<String> notifications;


    public User(String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.password = password;
        type = Type.teamMember;
    }


    public static User getUser(String username, String password) {
        if (admin.getUsername().equalsIgnoreCase(username) && admin.getPassword().equals(password))
            return admin;
        for (User user : allUsers) {
            if (user.username.equalsIgnoreCase(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static User getUser(String username) {
        for (User user : allUsers) {
            if (user.username.equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public static void setUsers(ArrayList<User> loadingUsers) {
        User.allUsers = loadingUsers;
    }

    public static void removeUser(String username) {
        for (User user : allUsers) {
            if (user.username.equalsIgnoreCase(username)) {
                allUsers.remove(user);
                return;
            }
        }
    }

    public static boolean checkAdmin(String username, String password) {
        return admin.getUsername().equalsIgnoreCase(username) && admin.getPassword().equalsIgnoreCase(password);
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }


    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


    public String getLastName() {
        return lastName;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static boolean usernameExists(String username) {
        for (User user : User.getAllUsers()) {
            if (user.getUsername().equalsIgnoreCase(username))
                return true;
        }

        return false;
    }

    public static boolean emailExists(String email) {
        for (User user : User.getAllUsers()) {
            if (email.equals(user.getEmail()))
                return true;
        }
        return false;
    }


    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches())
            return true;

        return false;

    }

    public boolean passwordIntHistory(String newPassword) {
        return oldPasswords.contains(newPassword);
    }


    public static User createUser(String username, String password, String email) {
        User user = new User(username, password, email);
        allUsers.add(user);
        return user;
    }

    public Team[] getTeams() {
        return teams.toArray(new Team[0]);
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public void logLogin() {
        logs.add(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Name: " + firstname + " " + lastName + "\n" +
                "Username: " + username + "\n" +
                "Email: " + email + "\n" +
                "Birthday: " + birthday + "\n" +
                "Role: " + type.toString() + "\n" +
                "Score: " + score;
    }

    public ArrayList<LocalDate> getLogs() {
        return logs;
    }

    public static void setAdmin(User admin) {
        User.admin = admin;
        admin.type = Type.systemAdministrator;
    }

    public static User getAdmin() {
        return admin;
    }

    public static boolean isAdmin(String username) {
        return username.equalsIgnoreCase(admin.getUsername());
    }

    public boolean isAdmin() {
        return type == Type.systemAdministrator;
    }

    public boolean setType(String role) {
        if (Type.get(role) != null) {
            type = Type.get(role);
            return true;
        }
        return false;
    }

    public Type getType() {
        return type;
    }

    public void addNotification(String notification) {
        notifications.add(notification);
    }

    public void clearNotifications() {
        notifications.clear();
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    enum Type {
        teamMember, teamLeader, systemAdministrator;

        public static Type get(String role) {
            role = role.toLowerCase().replace(" ", "").replace("_", "");
            if (role.equals("teammember"))
                return Type.teamMember;
            else if (role.equals("teamleader"))
                return Type.teamLeader;
            else if (role.equals("systemadministrator"))
                return Type.systemAdministrator;
            else
                return null;
        }
    }
}
