package terminal_view;

import controller.NotificationController;
import controller.Response;
import controller.UserController;
import model.User;
import utilities.ConsoleHelper;

public class NotificationsMenu implements TerminalView {

    private final String WELCOME_MESSAGE =
            "Notification menu!";

    private final String COMMAND_NOTIFICATIONS_READ =
            "notifications --read";
    private final String COMMAND_NOTIFICATIONS_CLEAR =
            "notifications --clear";

    private final String NOTIFICATIONS =
            "notifications";
    private final String READ =
            "read";
    private final String CLEAR =
            "clear";
    private final String SEND =
            "send";
    private final String NOTIFICATION =
            "notification";
    private final String USERNAME =
            "username";
    private final String TEAM =
            "team";

    @Override
    public String text() {
        return WELCOME_MESSAGE;
    }

    @Override
    public void showHelp() {
        ConsoleHelper.getInstance()
                .join(COMMAND_NOTIFICATIONS_READ)
                .join(COMMAND_NOTIFICATIONS_CLEAR)
                .printAll();
    }

    @Override
    public void parse(ArgumentManager input) {
        User user = UserController.getLoggedUser();
        if (user.isTeamLeader()) {
            if (input.isCommandFollowArg(SEND, NOTIFICATION, USERNAME)) {
                sendNotificationToUser(input);
            } else if (input.isCommandFollowArg(SEND, NOTIFICATION, TEAM)) {
                sendNotificationToTeam(input);
            }
        }
        if (input.isCommandFollowArg(NOTIFICATIONS, READ)) {
            readNotifications();
        } else if (input.isCommandFollowArg(NOTIFICATIONS, CLEAR)) {
            clearNotifications();
        }
    }

    private void sendNotificationToUser(ArgumentManager input) {
        Response response = NotificationController.getInstance()
                .sendNotificationToUser(input.get(NOTIFICATION), input.get(USERNAME));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void sendNotificationToTeam(ArgumentManager input) {
        Response response = NotificationController.getInstance()
                .sendNotificationToTeam(input.get(NOTIFICATION), input.get(TEAM));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void clearNotifications() {
        Response response = UserController.clearNotifications();
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void readNotifications() {
        Response response = UserController.getNotifications();
        ConsoleHelper.getInstance().println(response.getMessage());
    }
}
