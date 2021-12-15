package terminal_view;


import controller.LoginController;
import controller.SaveAndLoadController;
import controller.UserController;
import model.User;

public interface TerminalView {
    String text();

    default void show(){
        System.out.println(text());
        while(true){
            if(forceExit())
                return;
            ArgumentManager argumentManager = ArgumentManager.readInput();
            if(argumentManager.getCommand().equalsIgnoreCase("back"))
                return;
            else if(argumentManager.getCommand().equals("help")){
                System.out.println("help and enter menu command are available from anywhere.");
                showHelp();
            }
            else if(argumentManager.getCommand().equals("logout")){
                LoginController.getInstance().logout();
            }
            else if(UserController.getLogonUser() != null){
                if(argumentManager.getCommand().toLowerCase().startsWith("enter menu profile")){
                    enterProfileMenu();
                }
                else if(argumentManager.getCommand().toLowerCase().startsWith("enter menu team")){
                    enterTeamMenu();
                }
                else if(argumentManager.getCommand().toLowerCase().startsWith("enter menu notifications")){
                    enterNotificationsMenu();
                }
                else if(argumentManager.getCommand().toLowerCase().startsWith("enter menu admin")){
                    if(UserController.getLogonUser().isAdmin()){
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
        return UserController.getLogonUser() == null;
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
