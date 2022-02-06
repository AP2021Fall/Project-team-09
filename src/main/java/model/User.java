package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.MResponse;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Serializable {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String GET_AUTH_USER = "/auth/get-user";
    private static final String GET_USER = "/user/get-user";
    private static final String GET_ALL_USERS = "/user/get-all-users";

    private static User admin;

    private String firstname;
    private String lastName;
    private LocalDate birthday;
    private String username;
    private String password;
    private String email;
    private Type type;

    private static ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<LocalDateTime> logs = new ArrayList<>();
    private ArrayList<String> oldPasswords = new ArrayList<>();
    private ArrayList<Notification> notifications;

    public User(String lastName, String firstname, LocalDate birthday, String username, String password,
                String email, Type type, ArrayList<LocalDateTime> logs, ArrayList<String> oldPasswords,
                ArrayList<Notification> notifications) {
        this.lastName = lastName;
        this.firstname = firstname;
        this.birthday = birthday;
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
        this.logs = logs;
        this.oldPasswords = oldPasswords;
        this.notifications = notifications;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.type = Type.teamMember;
        this.notifications = new ArrayList<>();
        this.oldPasswords = new ArrayList<>();
        this.logs = new ArrayList<>();

        this.lastName = null;
        this.firstname = null;
        this.birthday = null;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public static void setUsers(ArrayList<User> loadingUsers) {
        User.allUsers = loadingUsers;
    }

    public static ArrayList<User> getAllUsers() {
        MResponse MResponse = new MRequest()
                .setPath(GET_ALL_USERS)
                .get();

        java.lang.reflect.Type typeMyType = new TypeToken<ArrayList<User>>() {
        }.getType();

        return new Gson().fromJson((String) MResponse.getObject(), typeMyType);
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        User.allUsers = allUsers;
    }

    public void setLogs(ArrayList<LocalDateTime> logs) {
        this.logs = logs;
    }

    public ArrayList<String> getOldPasswords() {
        return oldPasswords;
    }

    public void setOldPasswords(ArrayList<String> oldPasswords) {
        this.oldPasswords = oldPasswords;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public ArrayList<LocalDateTime> getLogs() {
        return logs;
    }

    public void sendNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public static User getAdmin() {
        return admin;
    }

    public static User getUser(String username, String password) {
        MResponse MResponse = new MRequest()
                .setPath(GET_AUTH_USER)
                .addArg(USERNAME, username)
                .addArg(PASSWORD, password)
                .get();

        java.lang.reflect.Type typeMyType = new TypeToken<User>() {
        }.getType();

        return new Gson().fromJson((String) MResponse.getObject(), typeMyType);
    }

    public static User getUser(String username) {
        MResponse MResponse = new MRequest()
                .setPath(GET_USER)
                .addArg(USERNAME, username)
                .get();

        java.lang.reflect.Type typeMyType = new TypeToken<User>() {
        }.getType();

        return new Gson().fromJson((String) MResponse.getObject(), typeMyType);
    }


    public static void removeUser(User user) {
        allUsers.remove(user);
        for (Team team : Team.getTeams())
            team.deleteMember(user);

        for (Task task : Task.getAllTask())
            task.removeFromAssignedUsers(user);
    }

    public static boolean checkAdmin(String username, String password) {
        return admin.getUsername().equalsIgnoreCase(username) && admin.getPassword().equalsIgnoreCase(password);
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

    public static int getUserPoints(User user) {
        int total = 0;

        for (Team team : user.getTeams())
            total += team.getMemberScore(user);
        return total;
    }

    public boolean passwordIntHistory(String newPassword) {
        return oldPasswords.contains(newPassword);
    }

    public static User createUser(String username, String password, String email) {
        User user = new User(username, password, email);
        allUsers.add(user);
        return user;
    }

    public void logLogin() {
        logs.add(LocalDateTime.now());
    }

    public static void setAdmin(User admin) {
        User.admin = admin;
        admin.type = Type.systemAdministrator;
    }

    public static boolean isAdmin(String username) {
        return username.equalsIgnoreCase(admin.getUsername());
    }

    public boolean isTeamLeader() {
        return this.type == Type.teamLeader;
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

    public void clearNotifications() {
        notifications.clear();
    }

    public ArrayList<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        for (Team team : Team.getTeams()) {
            if (team.hasMember(this))
                teams.add(team);
        }
        return teams;
    }

    public ArrayList<Team> getMyTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        for (Team team : Team.getTeams())
            if (team.getLeader().getUsername().equalsIgnoreCase(this.username))
                teams.add(team);
        return teams;
    }

    public Team getMyTeam(String teamName) {
        for (Team team : Team.getTeams())
            if (team.getName().equalsIgnoreCase(teamName))
                return team;
        return null;
    }

    @Override
    public String toString() {
        return "Name: " + firstname + " " + lastName + "\n" +
                "Username: " + username + "\n" +
                "Email: " + email + "\n" +
                "Birthday: " + (birthday == null ? "" : birthday.toString()) + "\n" +
                "Role: " + type.toString() + "\n";
    }

    public enum Type {
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
