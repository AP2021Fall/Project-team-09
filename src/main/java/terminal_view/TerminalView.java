package terminal_view;


import controller.LoginController;
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
                //todo other Menus
            }
            else{
                parse(argumentManager);
            }
        }
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

    //todo other menus

    void parse(ArgumentManager input);
}
