package terminal_view;

import controller.EnvironmentVariables;
import controller.LoginController;
import model.Response;
import exceptions.IllegalCommandException;

public class LoginMenu implements TerminalView{

    @Override
    public String text() {
        return "Welcome to Jira " + EnvironmentVariables.getInstance().getString("VERSION") + "!";
    }

    @Override
    public void parse(ArgumentManager input) {
        if(input.getCommand().equals("user create")){
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
}
