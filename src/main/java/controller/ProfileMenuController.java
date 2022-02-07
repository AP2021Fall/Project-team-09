package controller;

import model.MRequest;

import java.time.LocalDate;

public class ProfileMenuController {

    private static final String CHANGE_PASS_PATH = "/profile/change-pass";
    private static final String CHANGE_USER_PATH = "/profile/change-user";
    private static final String SHOW_TEAMS_PATH = "/profile/show-teams";
    private static final String SHOW_TEAM_PATH = "/profile/show-team";
    private static final String MY_PROFILE_PATH = "/profile/my-profile";
    private static final String MY_LOGS_PATH = "/profile/logs";
    private static final String GET_NOTIFICATIONS_PATH = "/profile/get-notifications";
    private static final String UPDATE_PROFILE_PATH = "/profile/update-profile";

    private static final String OLD_PASS = "old_pass";
    private static final String NEW_PASS = "new_pass";
    private static final String NEW_USERNAME = "new_username";
    private static final String TEAM_NAME = "team_name";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String BIRTH_DATE = "birth_date";

    private static ProfileMenuController controller = null;

    public static ProfileMenuController getInstance() {
        if (controller == null)
            controller = new ProfileMenuController();
        return controller;
    }

    public MResponse changePassword(String oldPassword, String newPassword) {
        return new MRequest()
                .setPath(CHANGE_PASS_PATH)
                .addArg(OLD_PASS, oldPassword)
                .addArg(NEW_PASS, newPassword)
                .post();
    }

    public MResponse changeUsername(String newUsername) {
        return new MRequest()
                .setPath(CHANGE_USER_PATH)
                .addArg(NEW_USERNAME, newUsername)
                .patch();
    }

    public MResponse showTeams() {
        return new MRequest()
                .setPath(SHOW_TEAMS_PATH)
                .get();
    }

    public MResponse showTeam(String teamName) {
        return new MRequest()
                .setPath(SHOW_TEAM_PATH)
                .addArg(TEAM_NAME, teamName)
                .get();
    }

    public MResponse getMyProfile() {
        return new MRequest()
                .setPath(MY_PROFILE_PATH)
                .get();
    }

    public MResponse getLogs() {
        return new MRequest()
                .setPath(MY_LOGS_PATH)
                .get();
    }

    public MResponse getNotifications() {
        return new MRequest()
                .setPath(GET_NOTIFICATIONS_PATH)
                .get();
    }

    public MResponse updateProfile(String firstName, String lastName, LocalDate birthDate) {
        return new MRequest()
                .setPath(UPDATE_PROFILE_PATH)
                .addArg(FIRST_NAME, firstName)
                .addArg(LAST_NAME, lastName)
                .addArg(BIRTH_DATE, birthDate)
                .patch();
    }
}
