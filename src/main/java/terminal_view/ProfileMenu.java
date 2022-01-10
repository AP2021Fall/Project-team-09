package terminal_view;

import controller.LoginController;
import controller.ProfileMenuController;
import controller.Response;
import exceptions.IllegalCommandException;
import utilities.ConsoleHelper;

public class ProfileMenu implements TerminalView {

    private final String WELCOME_MESSAGE =
            "Welcome to profile menu";

    private final String CHANGE_PASS_COMMAND =
            "profile change --oldPassword [current_password] --newPassword [new_password]";
    private final String CHANGE_USER_COMMAND =
            "profile change --username [username]";
    private final String SHOW_TEAMS_COMMAND =
            "profile --showTeams";
    private final String SHOW_TEAM_COMMAND =
            "profile --showTeam [team_name]";
    private final String SHOW_PROFILE_COMMAND =
            "profile --show --myProfile";
    private final String SHOW_LOGS_COMMAND =
            "profile --show logs";
    private final String SHOW_NOTIFICATIONS =
            "profile --show notifications";

    private final String PROFILE = "profile";
    private final String CHANGE = "change";
    private final String USERNAME = "username";
    private final String SHOW = "show";
    private final String TEAM_NAME = "team name";
    private final String SHOW_TEAMS = "showteams";
    private final String MY_PROFILE = "myprofile";
    private final String LOGS = "logs";
    private final String NOTIFICATIONS = "notifications";

    private final String OLD_PASSWORD = "oldPassword";
    private final String NEW_PASSWORD = "newPassword";

    @Override
    public String text() {
        return WELCOME_MESSAGE;
    }

    @Override
    public void showHelp() {
        ConsoleHelper.getInstance()
                .join(CHANGE_PASS_COMMAND)
                .join(CHANGE_USER_COMMAND)
                .join(SHOW_TEAMS_COMMAND)
                .join(SHOW_TEAM_COMMAND)
                .join(SHOW_PROFILE_COMMAND)
                .join(SHOW_LOGS_COMMAND)
                .join(SHOW_NOTIFICATIONS)
                .printAll();
    }

    @Override
    public void parse(ArgumentManager input) {
        if (input.isCommandFollowedBy(PROFILE, CHANGE)) {
            changePassword(input);
        } else if (input.isCommandFollowedBy(PROFILE, CHANGE, USERNAME)) {
            changeUsername(input);
        } else if (input.isCommandFollowedBy(PROFILE, SHOW_TEAMS)) {
            showTeams();
        } else if (input.isCommand(SHOW_TEAM_COMMAND)) {
            showTeam(input);
        } else if (input.isCommandFollowedBy(PROFILE, SHOW, MY_PROFILE)) {
            showMyProfile();
        } else if (input.isCommandFollowedBy(PROFILE, SHOW, LOGS)) {
            showLogs();
        } else if (input.isCommandFollowedBy(PROFILE, SHOW, NOTIFICATIONS)) {
            showNotifications();
        }
    }

    private void changePassword(ArgumentManager input) {
        try {
            Response response = ProfileMenuController.getInstance()
                    .changePassword(input.get(OLD_PASSWORD), input.get(NEW_PASSWORD));
            ConsoleHelper.getInstance().println(response.getMessage());
            if (response.isSuccess())
                LoginController.getInstance().logout();
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void changeUsername(ArgumentManager input) {
        try {
            Response response = ProfileMenuController.getInstance()
                    .changeUsername(input.get(USERNAME));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showTeams() {
        Response response = ProfileMenuController.getInstance().showTeams();
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void showTeam(ArgumentManager input) {
        Response response = ProfileMenuController.getInstance()
                .showTeam(input.get(TEAM_NAME));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void showMyProfile() {
        Response response = ProfileMenuController.getInstance().getMyProfile();
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void showLogs() {
        Response response = ProfileMenuController.getInstance().getLogs();
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void showNotifications() {
        Response response = ProfileMenuController.getInstance().getNotifications();
        ConsoleHelper.getInstance().println(response.getMessage());
    }
}
