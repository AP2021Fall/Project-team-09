package controller;

import model.Team;
import model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfileMenuController {

    private final String SUCCESS_PASS_CHANGED =
            "Password changed successfully!";

    private final String SUCCESS_USERNAME_CHANGED =
            "Username changed successfully!";

    private final String WARN_WRONG_PASS =
            "Wrong old password!";
    private final String WARN_PASS_IN_HISTORY =
            "Please type a New Password!";
    private final String WARN_WEAK_PASS =
            "Please Choose A strong Password (Containing at least 8 characters including 1 digit " +
                    "and 1 Capital Letter)";

    private final String WARN_LENGTH =
            "Your new username must include at least 4 characters!";
    private final String WARN_USER_TAKEN =
            "Username already taken";
    private final String WARN_SPECIAL_CHAR =
            "New username contains Special Characters! Please remove them and try again!";
    private final String WARN_SAME_USERNAME =
            "you already have this username!";
    private final String WARN_404_TEAM =
            "Team not found!";

    private final String USERNAME_REGEXP =
            "[a-zA-Z0-9_]";

    private static ProfileMenuController controller = null;
    private static int tries = 0;

    public static ProfileMenuController getInstance() {
        if (controller == null)
            controller = new ProfileMenuController();
        return controller;
    }

    public Response changePassword(String oldPassword, String newPassword) {
        User user = UserController.loggedUser;
        if (tries == 2)
            LoginController.getInstance().logout();
        if (user.getPassword().equals(oldPassword)) {
            if (user.passwordIntHistory(newPassword)) {
                return new Response(WARN_PASS_IN_HISTORY, false);
            }

            if (!isHard(newPassword)) {
                return new Response(WARN_WEAK_PASS, false);
            }
        } else {
            tries++;
            return new Response(WARN_WRONG_PASS, false);
        }

        tries = 0;
        user.setPassword(newPassword);
        return new Response(SUCCESS_PASS_CHANGED, true);
    }

    public Response changeUsername(String newUsername) {
        tries = 0;
        if (newUsername.length() < 4) {
            return new Response(WARN_LENGTH, false);
        }
        if (User.usernameExists(newUsername)) {
            return new Response(WARN_USER_TAKEN, false);
        }
        if (!newUsername.matches(USERNAME_REGEXP)) {
            return new Response(WARN_SPECIAL_CHAR, false);
        }
        if (newUsername.equalsIgnoreCase(UserController.loggedUser.getUsername())) {
            return new Response(WARN_SAME_USERNAME, false);
        }

        UserController.loggedUser.setUsername(newUsername);
        return new Response(SUCCESS_USERNAME_CHANGED, true);
    }

    public Response showTeams() {
        tries = 0;
        // Todo respond according to user type [MEMBER/LEADER]
        Team[] teams = UserController.loggedUser.getTeams();
        StringBuilder response = new StringBuilder();
        String[] teamNames = new String[teams.length];
        for (int i = 0; i < teams.length; i++) {
            Team team = teams[i];
            response.append(team.getName()).append(",");
            teamNames[i] = team.getName();
        }
        if (response.length() > 0)
            response = new StringBuilder(response.substring(0, response.length() - 1));
        return new Response(response.toString(), true, teams);
    }

    public Response showTeam(String teamName) {
        tries = 0;

        Team team = Team.getTeamByName(teamName);
        if (team == null) {
            return new Response(WARN_404_TEAM, false);
        }
        String response = "";
        response += team.getName() + "\n";
        response += team.getLeader().getUsername() + "\n";
        if (team.getLeader() != UserController.loggedUser) {
            response += UserController.getLoggedUser().getUsername() + "\n";
        }
        User[] members = team.getMembers();
        Arrays.sort(members);
        for (User user : members) {
            response += user.getUsername() + "\n";
        }
        return new Response(response, true, team);
    }

    private boolean isHard(String newPassword) {
        return newPassword.length() >= 8 && newPassword.matches(".*[A-Z].*")
                && newPassword.matches(".*[a-z].*") && newPassword.matches(".*[0-9].*");
    }

    public Response getMyProfile() {
        String answer = UserController.loggedUser.toString();

        return new Response(answer, true, UserController.loggedUser);
    }

    public Response getLogs() {
        ArrayList<LocalDate> dates = UserController.loggedUser.getLogs();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String answer = "";
        for (LocalDate date : dates) {
            answer += date.format(formatter) + "\n";
        }
        return new Response(answer, true, dates);
    }

    public Response getNotifications() {
        ArrayList<String> notifications = UserController.getLoggedUser().getNotifications();

        StringBuilder answer = new StringBuilder();
        for (String notification : notifications)
            answer.append(notification).append("\n");
        return new Response(answer.toString(), true, notifications);
    }
}
