package terminal_view;


import controller.LoginController;
import controller.SaveAndLoadController;
import controller.UserController;
import utilities.ConsoleHelper;

public interface TerminalView {

    String BACK = "back";
    String HELP = "help";
    String LOGOUT = "logout";

    String HELP_TITLE = "help and enter menu command are available from anywhere.";

    String ENTER_PROFILE = "enter menu profile";
    String ENTER_TEAM = "enter menu team";
    String ENTER_TASKS = "enter menu tasks";
    String ENTER_NOTIFICATIONS = "enter menu notifications";

    String text();

    default void show(){
        ConsoleHelper consoleHelper = ConsoleHelper.getInstance();

        consoleHelper.println(text());

        while(true){
            if(forceExit())
                return;
            ArgumentManager argumentManager = ArgumentManager.readInput();
            if(argumentManager.isCommand(BACK))
                return;
            else if(argumentManager.isCommand(HELP)){
                consoleHelper.println(HELP_TITLE);
                showHelp();
            }
            else if(argumentManager.isCommand(LOGOUT)){
                LoginController.getInstance().logout();
            }
            else if(UserController.getLoggedUser() != null){
                if(argumentManager.isCommand(ENTER_PROFILE)){
                    enterProfileMenu();
                }
                else if(argumentManager.isCommand(ENTER_TEAM)){
                    enterTeamMenu();
                }
                else if(argumentManager.isCommand(ENTER_NOTIFICATIONS)){
                    enterNotificationsMenu();
                }
                else if(argumentManager.isCommand("enter menu admin")){
                    if(UserController.getLoggedUser().isAdmin()){
                        enterAdminMenu();
                    }
                }
                else{
                    parse(argumentManager);
                }
            }
            else{
                parse(argumentManager);
            }
            SaveAndLoadController.save();
        }
    }

    default void enterNotificationsMenu(){
        new NotificationsMenu().show();
    }

    default boolean forceExit(){
        return UserController.getLoggedUser() == null;
    }

    void showHelp();

    default void enterTeamMenu(){
        new TeamMenu().show();
    }

    default void enterProfileMenu(){
        new ProfileMenu().show();
    }

    default void enterAdminMenu(){
        new AdminMenu().show();
    }

    void parse(ArgumentManager input);
}
