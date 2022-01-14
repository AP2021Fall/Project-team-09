package terminal_view;


import controller.LoginController;
import controller.SaveAndLoadController;
import controller.UserController;
import utilities.ConsoleHelper;

import java.util.Calendar;

public interface TerminalView {

    String BACK = "back";
    String HELP = "help";
    String LOGOUT = "logout";

    String HELP_TITLE = "help and enter menu command are available from anywhere.";

    String ENTER_PROFILE = "enter menu profile";
    String ENTER_TEAM = "enter menu team";
    String ENTER_TASKS = "enter menu tasks";
    String ENTER_NOTIFICATIONS = "enter menu notifications";
    String ENTER_CALENDAR = "enter menu calendar";

    String SHOW = "show";
    String TEAMS = "teams";
    String TEAM = "team";
    String CREATE = "create";

    String text();

    default void show() {
        ConsoleHelper consoleHelper = ConsoleHelper.getInstance();

        consoleHelper.println(text());

        while (true) {
            if (forceExit()) {
                return;
            }
            ArgumentManager argumentManager = ArgumentManager.readInput();
            System.out.println(argumentManager);
            if (argumentManager.isCommand(BACK))
                return;
            else if (argumentManager.isCommand(HELP)) {
                consoleHelper.println(HELP_TITLE);
                showHelp();
            } else if (argumentManager.isCommand(LOGOUT)) {
                LoginController.getInstance().logout();
            } else if (UserController.getLoggedUser() != null) {
                if (UserController.getLoggedUser().isTeamLeader()) {
                    if (argumentManager.isCommandFollowArg(SHOW, TEAMS)) {
                        showMyTeams();
                    } else if (argumentManager.isCommandFollowArg(SHOW, TEAM)) {
                        showTeam(argumentManager.get(TEAM));
                    } else if (argumentManager.isCommandFollowArg(CREATE, TEAM)) {
                        createTeam(argumentManager.get(TEAM));
                    }
                }
                if (argumentManager.isCommand(ENTER_PROFILE)) {
                    enterProfileMenu();
                } else if (argumentManager.isCommand(ENTER_TEAM)) {
                    enterTeamMenu();
                } else if (argumentManager.isCommand(ENTER_NOTIFICATIONS)) {
                    enterNotificationsMenu();
                } else if (argumentManager.isCommand(ENTER_TASKS)) {
                    enterTasksMenu();
                } else if (argumentManager.isCommand(ENTER_CALENDAR)) {
                    enterCalendarMenu();
                } else if (argumentManager.isCommand("enter menu admin")) {
                    if (UserController.getLoggedUser().isAdmin()) {
                        enterAdminMenu();
                    }
                } else {
                    parse(argumentManager);
                }
            } else {
                parse(argumentManager);
            }
            SaveAndLoadController.save();
        }
    }

    void showHelp();

    default void enterTasksMenu() {
        new TasksMenu().show();
    }

    default void enterNotificationsMenu() {
        new NotificationsMenu().show();
    }

    default boolean forceExit() {
        return UserController.getLoggedUser() == null;
    }

    default void enterTeamMenu() {
        new TeamMenu().show();
    }

    default void enterProfileMenu() {
        new ProfileMenu().show();
    }

    default void enterCalendarMenu() {
        new CalendarMenu().show();
    }

    default void enterAdminMenu() {
        new AdminMenu().show();
    }

    default void showMyTeams() {
        new TeamMenu().showLeaderTeams();
    }

    default void showTeam(String teamName) {
        new TeamMenu().showTheTeam(teamName);
    }

    default void createTeam(String teamName) {
        new TeamMenu().createTeam(teamName);
    }

    void parse(ArgumentManager input);
}
