package controller;

import model.MRequest;

import java.util.ArrayList;
import java.util.Arrays;

public class AdminController {

    private static final String USERNAME = "username";
    private static final String ROLE = "role";
    private static final String PENDING_TEAMS = "pending-teams";

    private static final String GET_PROFILE_PATH = "/admin/profile";
    private static final String BAN_USER_PATH = "/admin/ban";
    private static final String CHANGE_ROLE_PATH = "/admin/change-role";
    private static final String GET_PENDING_TEAMS = "/admin/pending-teams";
    private static final String ACCEPT_PENDING_TEAMS = "/admin/accept-pending";
    private static final String REJECT_PENDING_TEAMS = "/admin/reject-pending";
    private static final String GET_ALL_USERS_PATH = "/admin/get-all-users";

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
}
