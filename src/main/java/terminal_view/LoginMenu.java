package terminal_view;

import controller.EnvironmentVariables;
import controller.LoginController;
import model.Response;
import exceptions.IllegalCommandException;

public class LoginMenu implements TerminalView{

    @Override
    public String text() {
        return "Welcome to Jira " + EnvironmentVariables.getInstance().getString("VERSION") + "!" +
                "\nType 'help' if you need help!";
    }

    @Override
    public void parse(ArgumentManager input) {
        if(input.getCommand().equals("help")){
            showHelp();
        }
        else if(input.getCommand().equals("user create")){
            createUser(input);
        }
    }

    private void showHelp() {
        System.out.println("Commands:");
        System.out.println("user create --username <username> --password1 <pass1> --password2 <pass2> --email <email>");
    }

    private void createUser(ArgumentManager input) {
        try{
            Response response = LoginController.getInstance().userCreate(
                    input.get("username"),
                    input.get("password1"),
                    input.get("password2"),
                    input.get("email")
            );
            System.out.println(response.getMessage());
        }
        catch (IllegalCommandException e){
            System.out.println(e.getMessage());
        }
    }
}
