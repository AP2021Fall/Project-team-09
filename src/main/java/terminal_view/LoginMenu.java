package terminal_view;

import controller.EnvironmentVariables;
import controller.LoginController;
import controller.Response;
import exceptions.IllegalCommandException;
import model.User;
import utilities.ConsoleHelper;

public class LoginMenu implements TerminalView {

    private final String WELCOME_MESSAGE = "Welcome to Jira %s!" +
            "\nType 'help' if you need help!";

    private final String VERSION = "VERSION";

    public static final String USER_CREATE = "user create";
    public static final String USER_LOGIN = "user login";
    public static final String ADMIN_LOGIN = "admin login";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String PASSWORD_1 = "password1";
    public static final String PASSWORD_2 = "password2";

    private final String COMMANDS = "Commands in LoginMenu";
    private final String USER_CREATE_COMMAND =
            "user create --username <username> --password1 <pass1>" +
                    " --password2 <pass2> --email <email>";
    private final String USER_LOGIN_COMMAND =
            "user login --username <username> --password <password>";
    private final String ADMIN_LOGIN_COMMAND =
            "admin login --username <username> --password <password>";

    @Override
    public String text() {
        return String.format(WELCOME_MESSAGE,
                EnvironmentVariables.getInstance().getString(VERSION));
    }

    @Override
    public void parse(ArgumentManager input) {
        if (input.isCommand(USER_CREATE)) {
            createUser(input);
        } else if (input.isCommand(USER_LOGIN)) {
            userLogin(input);
        } else if (input.isCommand(ADMIN_LOGIN)) {
            adminLogin(input);
        }
    }

    private void adminLogin(ArgumentManager input) {
        try {
            Response response = LoginController.getInstance().adminLogin(
                    input.get(USERNAME),
                    input.get(PASSWORD)
            );

            if (response.isSuccess()) {
                enterAdminMenu();
            }
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void userLogin(ArgumentManager input) {
        try {
            Response response = LoginController.getInstance().userLogin(
                    input.get(USERNAME),
                    input.get(PASSWORD)
            );
            ConsoleHelper.getInstance().println(response.getMessage());
            if (response.isSuccess()) {
                User user = (User) response.getObject();
                if (user.isAdmin()) {
                    enterAdminMenu();
                } else {
                    enterProfileMenu();
                }
            }
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    public void showHelp() {
        ConsoleHelper.getInstance()
                .join(COMMANDS)
                .join(USER_CREATE_COMMAND)
                .join(USER_LOGIN_COMMAND)
                .join(ADMIN_LOGIN_COMMAND)
                .printAll();
    }

    private void createUser(ArgumentManager input) {
        try {
            Response response = LoginController.getInstance().userCreate(
                    input.get(USERNAME),
                    input.get(PASSWORD_1),
                    input.get(PASSWORD_2),
                    input.get(EMAIL)
            );
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    @Override
    public boolean forceExit() {
        return false;
    }
}
