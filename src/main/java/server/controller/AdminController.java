package server.controller;

import server.model.Team;
import server.model.User;

import java.util.ArrayList;
import java.util.Comparator;

public class AdminController {

    private final String SUCCESS_USER_BANED =
            "User removed successfully";
    private final String SUCCESS_ROLE_CHANGED =
            "User role changed to %s successfully";
    private final String SUCCESS_SET_ACCEPTED =
            "All teams status set to accepted!";
    private final String SUCCESS =
            "Success!";

    private final String WARN_404_USER =
            "There is no user with this username!";
    private final String WARN_WRONG_ROLE =
            "Wrong role name!";
    private final String WARN_404_PENDING =
            "There are no team in pending status!";
    private final String WARN_404_TEAM =
            "%s team is not available!";
    private final String WARN_NOT_PENDING =
            "Some teams are not in pending status!";
    private final String WARN_ALREADY_KNOWN =
            "User is already set to known!";
    private final String WARN_ALREADY_UNKNOWN =
            "User is already set to unknown!";

    private final String SUCCESS_PASS_CHANGED =
            "Password changed successfully!";

    private final String SUCCESS_USERNAME_CHANGED =
            "Username changed successfully!";
    private final String SUCCESS_EMAIL_CHANGED =
            "Email changed successfully!";
    private final String SUCCESS_PROFILE_UPDATED =
            "Profile updated successfully!";
    private final String SUCCESS_UNKNOWN =
            "User set to unknown successfully!";
    private final String SUCCESS_KNOWN =
            "User set to known successfully!";

    private final String WARN_WRONG_PASS =
            "Wrong old password!";
    private final String WARN_PASS_IN_HISTORY =
            "Please type a New Password!";
    private final String WARN_WEAK_PASS =
            "Please Choose A strong Password (Containing at least 8 characters including 1 digit " +
                    "and 1 Capital Letter)";
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
    private final String WARN_404_FN =
            "First name cannot be empty!";
    private final String WARN_404_LN =
            "Last name cannot be empty!";
    private final String WARN_NAME_INVALID =
            "Name is invalid";

    private final String WARN_EMAIL_EXISTS =
            "User with this email already exists!";
    private final String WARN_EMAIL_INVALID =
            "Email address is invalid";

    private final String USERNAME_REGEXP =
            "[a-zA-Z0-9_]{4,}";
    private final String NAME_REGEXP =
            "[a-zA-Z ]{1,}";
    private final String EMAIL_REGEXP =
            "[a-zA-Z0-9]+@(yahoo.com|gmail.com)";


    private static AdminController controller = null;

    public static AdminController getInstance() {
        if (controller == null)
            controller = new AdminController();
        return controller;
    }

    public MResponse getProfile(String username) {
        User user = User.getUser(username);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        return new MResponse(user.toString(), true, user);
    }

    public MResponse banUser(String username) {
        User user = User.getUser(username);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        User.removeUser(user);
        return new MResponse(SUCCESS_USER_BANED, true);
    }

    public MResponse changeRole(String username, String role) {
        User user = User.getUser(username);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (user.setType(role)) {
            return new MResponse(String.format(SUCCESS_ROLE_CHANGED, role), true);
        } else {
            return new MResponse(WARN_WRONG_ROLE, false);
        }
    }

    public MResponse getPendingTeams() {
        ArrayList<Team> pending = Team.getPendingTeams();

        if (pending.isEmpty())
            return new MResponse(WARN_404_PENDING, false);

        pending.sort(new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                return o1.getTimeOfCreation().compareTo(o2.getTimeOfCreation());
            }
        });

        StringBuilder result = new StringBuilder();

        for (Team team : pending)
            result.append(team.getName()).append("\n");
        return new MResponse(result.toString(), true, pending);
    }


    public MResponse acceptPendingTeams(String[] teams) {
        ArrayList<Team> acceptedTeams = new ArrayList<>();
        for (String team : teams) {
            Team theTeam = Team.getTeamByName(team);
            if (theTeam == null)
                return new MResponse(String.format(WARN_404_TEAM, team), false);
            if (!theTeam.isPending())
                return new MResponse(WARN_NOT_PENDING, false);
            acceptedTeams.add(theTeam);
        }
        for (Team team : acceptedTeams)
            team.accept();
        return new MResponse(SUCCESS_SET_ACCEPTED, true);
    }

    public MResponse rejectPendingTeams(String[] teams) {
        ArrayList<Team> rejectedTeams = new ArrayList<>();
        for (String team : teams) {
            Team theTeam = Team.getTeamByName(team);
            if (theTeam == null)
                return new MResponse(String.format(WARN_404_TEAM, team), false);
            if (!theTeam.isPending())
                return new MResponse(WARN_NOT_PENDING, false);
            rejectedTeams.add(theTeam);
        }
        for (Team team : rejectedTeams)
            team.reject();
        return new MResponse(SUCCESS_SET_ACCEPTED, true);
    }

    public MResponse getAllUsers() {
        return new MResponse(SUCCESS, true, User.getAllUsers());
    }

    public MResponse changeUsername(String oldUsername, String newUsername) {
        User user = User.getUser(oldUsername);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (newUsername.length() < 4) {
            return new MResponse(WARN_LENGTH, false);
        }
        if (User.usernameExists(newUsername)) {
            return new MResponse(WARN_USER_TAKEN, false);
        }
        if (!newUsername.matches(USERNAME_REGEXP)) {
            return new MResponse(WARN_SPECIAL_CHAR, false);
        }
        if (newUsername.equalsIgnoreCase(UserController.getLoggedUser().getUsername())) {
            return new MResponse(WARN_SAME_USERNAME, false);
        }

        user.setUsername(newUsername);
        return new MResponse(SUCCESS_USERNAME_CHANGED, true);
    }

    public MResponse changePassword(String username, String newPassword) {
        User user = User.getUser(username);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (user.getPassword().equals(newPassword))
            return new MResponse(WARN_SAME_PASS, false);

        String oldPassword = user.getPassword();

        if (user.getPassword().equals(oldPassword)) {
            if (user.passwordIntHistory(newPassword)) {
                return new MResponse(WARN_PASS_IN_HISTORY, false);
            }

            if (!isHard(newPassword)) {
                return new MResponse(WARN_WEAK_PASS, false);
            }
        } else {
            return new MResponse(WARN_WRONG_PASS, false);
        }

        user.setPassword(newPassword);
        return new MResponse(SUCCESS_PASS_CHANGED, true);
    }

    public MResponse changeEmail(String username, String email) {
        User user = User.getUser(username);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (User.emailExists(email))
            return new MResponse(WARN_EMAIL_EXISTS, false);

        if (!email.matches(EMAIL_REGEXP))
            return new MResponse(WARN_EMAIL_INVALID, false);

        user.setEmail(email);
        return new MResponse(SUCCESS_EMAIL_CHANGED, true);
    }

    public MResponse updateProfile(String username, String firstName, String lastName) {
        User user = User.getUser(username);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (firstName.isEmpty())
            return new MResponse(WARN_404_FN, false);

        if (!firstName.matches(NAME_REGEXP) || !lastName.matches(NAME_REGEXP))
            return new MResponse(WARN_NAME_INVALID, false);

        if (lastName.isEmpty())
            return new MResponse(WARN_404_LN, false);

        user.setFirstname(firstName);
        user.setLastName(lastName);
        return new MResponse(SUCCESS_PROFILE_UPDATED, true);
    }

    public MResponse changeToKnown(String username) {
        User user = User.getUser(username);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (!user.isUnknown())
            return new MResponse(WARN_ALREADY_KNOWN, false);

        user.setUnknown(false);
        return new MResponse(SUCCESS_UNKNOWN, true);
    }

    public MResponse changeToUnknown(String username) {
        User user = User.getUser(username);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (user.isUnknown())
            return new MResponse(WARN_ALREADY_UNKNOWN, false);

        user.setUnknown(true);
        return new MResponse(SUCCESS_UNKNOWN, true);
    }

    private boolean isHard(String newPassword) {
        return newPassword.length() >= 8 && newPassword.matches(".*[A-Z].*")
                && newPassword.matches(".*[a-z].*") && newPassword.matches(".*[0-9].*");
    }
}
