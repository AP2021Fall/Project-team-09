package controller;

import model.Notification;
import model.Team;
import model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class ProfileMenuController {

    private final String SUCCESS_PASS_CHANGED =
            "Password changed successfully!";

    private final String SUCCESS_USERNAME_CHANGED =
            "Username changed successfully!";
    private final String SUCCESS_PROFILE_UPDATED =
            "Profile updated successfully!";

    private final String WARN_WRONG_PASS =
            "Wrong old password!";
    private final String WARN_PASS_IN_HISTORY =
            "Please type a New Password!";
    private final String WARN_WEAK_PASS =
            "Please Choose A strong Password (Containing at least 8 characters including 1 digit " +
                    "and 1 Capital Letter)";
    private final String WARN_404_TEAMS =
            "No teams available!";
    private final String WARN_404_NOTIFICATIONS =
            "No notifications available!";
    private final String WARN_404_USER =
            "User not found!";
    private final String WARN_SAME_PASS =
            "Password is the same!";

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
    private final String WARN_404_FN =
            "First name cannot be empty!";
    private final String WARN_404_LN =
            "Last name cannot be empty!";
    private final String WARN_404_BD =
            "Birth date cannot be empty!";
    private final String WARN_NAME_INVALID =
            "Name is invalid";
    private final String WARN_TOO_MANY =
            "Too many tries, logging out!";

    private final String USERNAME_REGEXP =
            "[a-zA-Z0-9_]{4,}";
    private final String NAME_REGEXP =
            "[a-zA-Z ]{1,}";

    private static ProfileMenuController controller = null;
    private static int tries = 0;

    public static ProfileMenuController getInstance() {
        if (controller == null)
            controller = new ProfileMenuController();
        return controller;
    }

    public MResponse changePassword(String oldPassword, String newPassword) {
        User user = UserController.loggedUser;
        if (tries == 1) {
            LoginController.getInstance().logout();
            return new MResponse(WARN_TOO_MANY, false);
        }

        if (user.getPassword().equals(newPassword))
            return new MResponse(WARN_SAME_PASS, false);


        if (user.getPassword().equals(oldPassword)) {
            if (user.passwordIntHistory(newPassword)) {
                return new MResponse(WARN_PASS_IN_HISTORY, false);
            }

            if (!isHard(newPassword)) {
                return new MResponse(WARN_WEAK_PASS, false);
            }
        } else {
            tries++;
            return new MResponse(WARN_WRONG_PASS, false);
        }

        tries = 0;
        user.setPassword(newPassword);
        return new MResponse(SUCCESS_PASS_CHANGED, true);
    }

    public MResponse changeUsername(String newUsername) {
        tries = 0;
        if (newUsername.length() < 4) {
            return new MResponse(WARN_LENGTH, false);
        }
        if (User.usernameExists(newUsername)) {
            return new MResponse(WARN_USER_TAKEN, false);
        }
        if (!newUsername.matches(USERNAME_REGEXP)) {
            return new MResponse(WARN_SPECIAL_CHAR, false);
        }
        if (newUsername.equalsIgnoreCase(UserController.loggedUser.getUsername())) {
            return new MResponse(WARN_SAME_USERNAME, false);
        }

        UserController.loggedUser.setUsername(newUsername);
        return new MResponse(SUCCESS_USERNAME_CHANGED, true);
    }

    public MResponse showTeams() {
        tries = 0;

        User user = UserController.getLoggedUser();
        ArrayList<Team> teams;
        if (user.isTeamLeader()) {
            teams = user.getMyTeams();
        } else {
            teams = user.getTeams();
        }

        if (teams.isEmpty())
            return new MResponse(WARN_404_TEAMS, false);

        teams.sort(new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                int compare = o1.getTimeOfCreation().compareTo(o2.getTimeOfCreation());
                if (compare == 0)
                    return o1.getName().compareTo(o2.getName());
                return compare;
            }
        });

        StringBuilder response = new StringBuilder();
        int index = 1;

        for (Team team : teams) {
            response.append(String.format("%d- %s", index++, team.getName())).append("\n");
        }

        return new MResponse(response.toString(), true, teams);
    }

    public MResponse showTeam(String teamName) {
        tries = 0;

        Team team = UserController.getLoggedUser().getMyTeam(teamName);
        if (team == null) {
            return new MResponse(WARN_404_TEAM, false);
        }
        String response = "";
        response += team.getName() + "\n";
        response += team.getLeader().getUsername() + "\n";
        if (team.getLeader() != UserController.getLoggedUser()) {
            response += UserController.getLoggedUser().getUsername() + "\n";
        }

        ArrayList<User> members = team.getMembers();

        members.remove(UserController.getLoggedUser());

        members.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });

        for (User user : members) {
            response += user.getUsername() + "\n";
        }
        return new MResponse(response, true, team);
    }

    private boolean isHard(String newPassword) {
        return newPassword.length() >= 8 && newPassword.matches(".*[A-Z].*")
                && newPassword.matches(".*[a-z].*") && newPassword.matches(".*[0-9].*");
    }

    public MResponse getMyProfile() {
        User user = UserController.getLoggedUser();

        String answer = UserController.getLoggedUser().toString();

        int score = 0;

        ArrayList<Team> teams = user.getTeams();

        for (Team team : teams)
            if (team.hasMember(user))
                score += team.getMemberScore(user);
        answer += String.format("Score: %d\n", score);

        return new MResponse(answer, true, UserController.getLoggedUser());
    }

    public MResponse getLogs() {
        ArrayList<LocalDateTime> dates = UserController.getLoggedUser().getLogs();

        dates.sort(new Comparator<LocalDateTime>() {
            @Override
            public int compare(LocalDateTime o1, LocalDateTime o2) {
                return o2.compareTo(o1);
            }
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy|HH:mm:ss");
        String answer = "";
        for (LocalDateTime date : dates) {
            answer += date.format(formatter) + "\n";
        }
        return new MResponse(answer, true, dates);
    }

    public MResponse getNotifications() {
        ArrayList<Notification> notifications = UserController.getLoggedUser().getNotifications();
        if (notifications.isEmpty())
            return new MResponse(WARN_404_NOTIFICATIONS, false);

        StringBuilder answer = new StringBuilder();
        for (Notification notification : notifications)
            answer.append(notification).append("\n");
        return new MResponse(answer.toString(), true, notifications);
    }

    public MResponse updateProfile(String firstName, String lastName, LocalDate birthDate) {
        User user = UserController.getLoggedUser();

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (firstName.isEmpty())
            return new MResponse(WARN_404_FN, false);

        if (!firstName.matches(NAME_REGEXP) || !lastName.matches(NAME_REGEXP))
            return new MResponse(WARN_NAME_INVALID, false);

        if (lastName.isEmpty())
            return new MResponse(WARN_404_LN, false);

        if (birthDate == null)
            return new MResponse(WARN_404_BD, false);

        user.setFirstname(firstName);
        user.setLastName(lastName);
        user.setBirthday(birthDate);
        return new MResponse(SUCCESS_PROFILE_UPDATED, true);
    }

    public void resetTries() {
        tries = 0;
    }
}
