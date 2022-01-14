package terminal_view;

import controller.AdminController;
import controller.Response;
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
    private final String COMMAND_CHANGE_ROLE =
            "change role --user [username] --role [role]";

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
                .join(COMMAND_CHANGE_ROLE)
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
        } else if (input.isCommandFollowArg(CHANGE_ROLE, USER, ROLE)) {
            changeRole(input);
        }
    }

    private void sendNotification(ArgumentManager input) {
        Response response = AdminController.getInstance()
                .sendNotification(input.get(USER), input.get(NOTIFICATION));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void sendNotificationAll(ArgumentManager input) {
        Response response = AdminController.getInstance()
                .sendNotificationToAll(input.get(NOTIFICATION));
        System.out.println(response.getMessage());
    }

    private void banUser(ArgumentManager input) {
        Response response = AdminController.getInstance()
                .banUser(input.get(USERNAME));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void showUserProfile(ArgumentManager input) {
        Response response = AdminController.getInstance().getProfile(input.get(USERNAME));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void changeRole(ArgumentManager input) {
        Response response = AdminController.getInstance()
                .changeRole(input.get(USERNAME), input.get(ROLE));
        ConsoleHelper.getInstance().println(response.getMessage());
    }
}
