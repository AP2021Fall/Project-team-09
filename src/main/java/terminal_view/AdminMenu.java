package terminal_view;

import controller.AdminController;
import controller.NotificationController;
import controller.Response;
import exceptions.IllegalCommandException;
import utilities.ConsoleHelper;

public class AdminMenu implements TerminalView {

    private final String WELCOME_MESSAGE =
            "Welcome to admin page";

    private final String COMMAND_SHOW_PROFILE =
            "show profile --username [username]";
    private final String COMMAND_BAN_USER =
            "ban user --username [username]";
    private final String COMMAND_NOTIFICATION_ALL =
            "send --notification [notification] --all";
    private final String COMMAND_NOTIFICATION_USER =
            "send --notification [notification] --user [username]";
    private final String COMMAND_NOTIFICATION_TEAM =
            "send --notification [notification] --team [team name]";
    private final String COMMAND_CHANGE_ROLE =
            "change role --username [username] --role [role]";
    private final String COMMAND_SHOW_PENDING =
            "show --pendingTeams";
    private final String COMMAND_ACCEPT_TEAMS =
            "accept --teams [team names]";
    private final String COMMAND_REJECT_TEAMS =
            "reject --teams [team names]";

    private final String SHOW_PROFILE =
            "show profile";
    private final String BAN_USER =
            "ban user";
    private final String CHANGE_ROLE =
            "change role";
    private final String USERNAME =
            "username";
    private final String ROLE =
            "role";
    private final String SEND =
            "send";
    private final String NOTIFICATION =
            "notification";
    private final String ALL =
            "all";
    private final String USER =
            "user";
    private final String TEAM =
            "team";
    private final String TEAMS =
            "teams";
    private final String SHOW =
            "show";
    private final String PENDING_TEAMS =
            "pendingTeams";
    private final String ACCEPT =
            "accept";
    private final String REJECT =
            "reject";

    @Override
    public String text() {
        return WELCOME_MESSAGE;
    }

    @Override
    public void showHelp() {
        ConsoleHelper.getInstance()
                .join(COMMAND_SHOW_PROFILE)
                .join(COMMAND_BAN_USER)
                .join(COMMAND_NOTIFICATION_ALL)
                .join(COMMAND_NOTIFICATION_USER)
                .join(COMMAND_NOTIFICATION_TEAM)
                .join(COMMAND_CHANGE_ROLE)
                .join(COMMAND_SHOW_PENDING)
                .join(COMMAND_ACCEPT_TEAMS)
                .join(COMMAND_REJECT_TEAMS)
                .printAll();
    }

    @Override
    public void parse(ArgumentManager input) {
        if (input.isCommandFollowArg(SHOW_PROFILE)) {
            showUserProfile(input);
        } else if (input.isCommandFollowArg(BAN_USER)) {
            banUser(input);
        } else if (input.isCommandFollowArg(SEND, NOTIFICATION, ALL)) {
            sendNotificationAll(input);
        } else if (input.isCommandFollowArg(SEND, NOTIFICATION, USER)) {
            sendNotification(input);
        } else if (input.isCommandFollowArg(SEND, NOTIFICATION, TEAM)) {
            sendNotificationToTeam(input);
        } else if (input.isCommandFollowArg(CHANGE_ROLE, USERNAME, ROLE)) {
            changeRole(input);
        } else if (input.isCommandFollowArg(SHOW, PENDING_TEAMS)) {
            showPendingTeams();
        } else if (input.isCommandFollowArg(ACCEPT, TEAMS)) {
            acceptPendingTeams(input);
        } else if (input.isCommandFollowArg(REJECT, PENDING_TEAMS)) {
            rejectPendingTeams(input);
        }
    }

    private void sendNotification(ArgumentManager input) {
        try {
            Response response = NotificationController.getInstance()
                    .sendNotificationToUser(input.get(NOTIFICATION), input.get(USER));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void sendNotificationAll(ArgumentManager input) {
        try {
            Response response = NotificationController.getInstance()
                    .sendNotificationToAll(input.get(NOTIFICATION));
            System.out.println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void sendNotificationToTeam(ArgumentManager input) {
        try {
            Response response = NotificationController.getInstance()
                    .sendNotificationToTeam(input.get(NOTIFICATION), input.get(TEAM));
            System.out.println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void banUser(ArgumentManager input) {
        try {
            Response response = AdminController.getInstance()
                    .banUser(input.get(USERNAME));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showUserProfile(ArgumentManager input) {
        try {
            Response response = AdminController.getInstance().getProfile(input.get(USERNAME));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void changeRole(ArgumentManager input) {
        try {
            Response response = AdminController.getInstance()
                    .changeRole(input.get(USERNAME), input.get(ROLE));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showPendingTeams() {
        try {
            Response response = AdminController.getInstance()
                    .getPendingTeams();
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void acceptPendingTeams(ArgumentManager input) {
        try {
            Response response = AdminController.getInstance()
                    .acceptPendingTeams(input.get(TEAMS).split(" "));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void rejectPendingTeams(ArgumentManager input) {
        try {
            Response response = AdminController.getInstance()
                    .rejectPendingTeams(input.get(TEAMS).split(" "));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }
}
