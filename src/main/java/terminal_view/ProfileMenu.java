package terminal_view;

import controller.LoginController;
import controller.ProfileMenuController;
import controller.MResponse;
import exceptions.IllegalCommandException;
import utilities.ConsoleHelper;

public class ProfileMenu implements TerminalView {

    private final String WELCOME_MESSAGE =
            "Welcome to profile menu";

    private final String SUCCESS_LOG_OUT =
            "User logged out successfully!";


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
    private final String PROFILE_CHANGE =
            "profile change";
    private final String USERNAME = "username";
    private final String SHOW = "show";
    private final String SHOW_TEAMS = "showteams";
    private final String SHOW_TEAM = "showteam";
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
        if (input.isCommandFollowArg(PROFILE_CHANGE, OLD_PASSWORD, NEW_PASSWORD)) {
            changePassword(input);
        } else if (input.isCommandFollowArg(PROFILE_CHANGE, USERNAME)) {
            changeUsername(input);
        } else if (input.isCommandFollowArg(PROFILE, SHOW_TEAMS)) {
            showTeams();
        } else if (input.isCommandFollowArg(PROFILE, SHOW_TEAM)) {
            showTeam(input);
        } else if (input.isCommandFollowArg(PROFILE, SHOW, MY_PROFILE)) {
            showMyProfile();
        } else if (input.isCommandFollowArg(PROFILE, SHOW)) {
            show(input);
        }
    }

    private void changePassword(ArgumentManager input) {
        try {
            MResponse MResponse = ProfileMenuController.getInstance()
                    .changePassword(input.get(OLD_PASSWORD), input.get(NEW_PASSWORD));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
            if (MResponse.isSuccess()) {
                LoginController.getInstance().logout();
                ConsoleHelper.getInstance().println(SUCCESS_LOG_OUT);
            }
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void changeUsername(ArgumentManager input) {
        try {
            MResponse MResponse = ProfileMenuController.getInstance()
                    .changeUsername(input.get(USERNAME));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showTeams() {
        try {
            MResponse MResponse = ProfileMenuController.getInstance().showTeams();
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
            ;
        }
    }

    private void showTeam(ArgumentManager input) {
        try {
            MResponse MResponse = ProfileMenuController.getInstance()
                    .showTeam(input.get(SHOW_TEAM));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showMyProfile() {
        try {
            MResponse MResponse = ProfileMenuController.getInstance().getMyProfile();
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
            ;
        }
    }

    private void showLogs() {
        try {
            MResponse MResponse = ProfileMenuController.getInstance().getLogs();
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
            ;
        }
    }

    private void showNotifications() {
        try {
            MResponse MResponse = ProfileMenuController.getInstance().getNotifications();
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
            ;
        }
    }

    private void show(ArgumentManager input) {
        try {
            switch (input.get(SHOW)) {
                case LOGS:
                    showLogs();
                    break;
                case NOTIFICATIONS:
                    showNotifications();
                    break;
            }
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }
}
