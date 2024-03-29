package controller;

import model.MRequest;
import model.User;

import java.util.ArrayList;
import java.util.Arrays;

public class AdminController {

    private static final String USERNAME = "username";
    private static final String OLD_USERNAME = "old_username";
    private static final String NEW_USERNAME = "new_username";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String OLD_EMAIL = "old_email";
    private static final String NEW_EMAIL = "new_email";
    private static final String ROLE = "role";
    private static final String PENDING_TEAMS = "pending-teams";

    private static final String GET_PROFILE_PATH = "/admin/profile";
    private static final String BAN_USER_PATH = "/admin/ban";
    private static final String CHANGE_ROLE_PATH = "/admin/change-role";
    private static final String GET_PENDING_TEAMS = "/admin/pending-teams";
    private static final String ACCEPT_PENDING_TEAMS = "/admin/accept-pending";
    private static final String REJECT_PENDING_TEAMS = "/admin/reject-pending";
    private static final String GET_ALL_USERS_PATH = "/admin/get-all-users";
    private static final String CHANGE_USERNAME = "/admin/change-username";
    private static final String CHANGE_PASSWORD = "/admin/change-password";
    private static final String CHANGE_EMAIL = "/admin/change-email";
    private static final String UPDATE_PROFILE = "/admin/change-profile";
    private static final String CHANGE_TO_KNOWN = "/admin/change-known";
    private static final String CHANGE_TO_UNKNOWN = "/admin/change-unknown";

    private static AdminController controller = null;

    public static AdminController getInstance() {
        if (controller == null)
            controller = new AdminController();
        return controller;
    }

    public MResponse getProfile(String username) {
        return new MRequest()
                .setPath(GET_PROFILE_PATH)
                .addArg(USERNAME, username)
                .get();
    }

    public MResponse banUser(String username) {
        return new MRequest()
                .setPath(BAN_USER_PATH)
                .addArg(USERNAME, username)
                .patch();
    }

    public MResponse changeRole(String username, String role) {
        return new MRequest()
                .setPath(CHANGE_ROLE_PATH)
                .addArg(USERNAME, username)
                .addArg(ROLE, role)
                .patch();
    }

    public MResponse getPendingTeams() {
        return new MRequest()
                .setPath(GET_PENDING_TEAMS)
                .get();
    }


    public MResponse acceptPendingTeams(String[] teams) {
        ArrayList<String> pendingTeams = new ArrayList<>(Arrays.asList(teams));
        return new MRequest()
                .setPath(ACCEPT_PENDING_TEAMS)
                .addArg(PENDING_TEAMS, pendingTeams)
                .patch();
    }

    public MResponse rejectPendingTeams(String[] teams) {
        ArrayList<String> pendingTeams = new ArrayList<>(Arrays.asList(teams));
        return new MRequest()
                .setPath(REJECT_PENDING_TEAMS)
                .addArg(PENDING_TEAMS, pendingTeams)
                .patch();
    }

    public MResponse getAllUsers() {
        return new MRequest()
                .setPath(GET_ALL_USERS_PATH)
                .get();
    }

    public MResponse changeUsername(String oldUsername, String newUsername) {
        return new MRequest()
                .setPath(CHANGE_USERNAME)
                .addArg(OLD_USERNAME, oldUsername)
                .addArg(NEW_USERNAME, newUsername)
                .patch();
    }

    public MResponse changePassword(String username, String password) {
        return new MRequest()
                .setPath(CHANGE_PASSWORD)
                .addArg(USERNAME, username)
                .addArg(PASSWORD, password)
                .post();
    }

    public MResponse changeEmail(String username, String newEmail) {
        return new MRequest()
                .setPath(CHANGE_EMAIL)
                .addArg(USERNAME, username)
                .addArg(NEW_EMAIL, newEmail)
                .patch();
    }

    public MResponse updateProfile(String username, String firstName, String lastName) {
        return new MRequest()
                .setPath(UPDATE_PROFILE)
                .addArg(USERNAME, username)
                .addArg(FIRST_NAME, firstName)
                .addArg(LAST_NAME, lastName)
                .patch();
    }

    public MResponse changeToKnown(User user) {
        return new MRequest()
                .setPath(CHANGE_TO_KNOWN)
                .addArg(USERNAME, user.getUsername())
                .patch();
    }

    public MResponse changeToUnknown(User user) {
        return new MRequest()
                .setPath(CHANGE_TO_UNKNOWN)
                .addArg(USERNAME, user.getUsername())
                .patch();
    }
}
