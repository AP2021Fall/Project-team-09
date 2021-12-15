package terminal_view;

import controller.ProfileMenuController;
import controller.Response;
import exceptions.IllegalCommandException;

public class ProfileMenu implements TerminalView{
    @Override
    public String text() {
        return "Welcome to profile menu";
    }

    @Override
    public void showHelp() {
        System.out.println("profile change --oldPassword [current_password] --newPassword [new_password]");
        System.out.println("profile change --username [username]");
        System.out.println("profile --showTeams");
        System.out.println("profile --showTeam [team_name]");
        System.out.println("profile --show --myProfile");
        System.out.println("profile --show logs");
        System.out.println("profile --show notifications");
    }

    @Override
    public void parse(ArgumentManager input) {
        if(input.getCommand().equals("profile") &&  input.contains("change")){
            changePassword(input);
        }
        else if(input.getCommand().equals("profile") &&  input.contains("change") && input.contains("username")){
            changeUsername(input);
        }
        else if(input.getCommand().equals("profile") && input.contains("showteams")){
            showTeams();
        }
        else if(input.getCommand().equals("profile") && input.contains("show") && input.contains("myprofile")){
            showMyProfile();
        }
        else if(input.getCommand().equals("profile") && input.contains("show") && input.contains("logs")){
            showLogs();
        }
    }

    private void showLogs() {
        Response response = ProfileMenuController.getInstance().getLogs();
        System.out.println(response.getMessage());
    }

    private void showMyProfile() {
        Response response = ProfileMenuController.getInstance().getMyProfile();
        System.out.println(response.getMessage());
    }

    private void showTeams() {
        Response response = ProfileMenuController.getInstance().showTeams();
        System.out.println(response.getMessage());
    }

    private void changeUsername(ArgumentManager input) {
        try {
            Response response = ProfileMenuController.getInstance().changeUsername("username");
            System.out.println(response.getMessage());
        }
        catch (IllegalCommandException e){
            System.out.println(e.getMessage());
        }
    }

    private void changePassword(ArgumentManager input) {
        try{
            Response response = ProfileMenuController.getInstance().changePassword(input.get("oldpassword") , input.get("newpassword"));
            System.out.println(response.getMessage());
        }
        catch (IllegalCommandException e){
            System.out.println(e.getMessage());
        }
    }
}
