package terminal_view;

import controller.EnvironmentVariables;
import controller.LoginController;
import controller.Response;
import exceptions.IllegalCommandException;
import model.User;

public class LoginMenu implements TerminalView{

    @Override
    public String text() {
        return "Welcome to Jira " + EnvironmentVariables.getInstance().getString("VERSION") + "!" +
                "\nType 'help' if you need help!";
    }

    @Override
    public void parse(ArgumentManager input) {
        if(input.getCommand().equals("user create")){
            createUser(input);
        }
        else if(input.getCommand().equals("user login")){
            userLogin(input);
        }
        else if(input.getCommand().equals("admin login")){
            adminLogin(input);
        }
    }

    private void adminLogin(ArgumentManager input) {
        try{
            Response response = LoginController.getInstance().adminLogin(
                    input.get("username"),
                    input.get("password")
            );
            System.out.println(response.getMessage());
            if(response.isSuccess()){
                enterAdminMenu();
            }
        }
        catch (IllegalCommandException e){
            System.out.println(e.getMessage());
        }
    }

    private void userLogin(ArgumentManager input) {
        try{
            Response response = LoginController.getInstance().userLogin(
                    input.get("username"),
                    input.get("password")
            );
            System.out.println(response.getMessage());
            if(response.isSuccess()){
                User user = (User) response.getObject();
                if(user.isAdmin()){
                    enterAdminMenu();
                }
                else{
                    enterProfileMenu();
                }
            }
        }
        catch (IllegalCommandException e){
            System.out.println(e.getMessage());
        }
    }

    public void showHelp() {
        System.out.println("Commands in LoginMenu:");
        System.out.println("user create --username <username> --password1 <pass1> --password2 <pass2> --email <email>");
        System.out.println("user login --username <username> --password <password>");
        System.out.println("admin login --username <username> --password <password>");

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

    @Override
    public boolean forceExit() {
        return false;
    }
}
