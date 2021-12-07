package terminal_view;


import controller.UserController;

public interface TerminalView {
    String text();

    default void show(){
        System.out.println(text());
        while(true){
            ArgumentManager argumentManager = ArgumentManager.readInput();
            if(argumentManager.getCommand().equalsIgnoreCase("back"))
                return;
            else if(argumentManager.getCommand().equals("help")){
                showHelp();
            }
            else if(UserController.getInstance().getLogonUser() != null){
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
