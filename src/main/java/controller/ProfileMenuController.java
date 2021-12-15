package controller;

import model.Team;
import model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfileMenuController {
    private static ProfileMenuController controller = null;

    public static ProfileMenuController getInstance() {
        if(controller == null)
            controller = new ProfileMenuController();
        return controller;
    }

    public Response changePassword(String oldPassword,String newPassword){
        User user = UserController.logonUser;
        if(user.getPassword().equals(oldPassword)){
            if(user.passwordIntHistory(newPassword)){
                return new Response("Please type a New Password !",false);
            }
            else if (!isHard(newPassword)){
                return new Response("Please Choose A strong Password (Containing at least 8 characters including 1 digit " +
                        "and 1 Capital Letter)",false);
            }
        }
        else{
            return new Response("Wrong old password!",false);
        }

        user.setPassword(newPassword);
        return new Response("Password changed successfully!" , true);
    }
    
    public Response changeUsername(String newUsername){
        if(newUsername.length() < 4){
            return new Response("Your new username must include at least 4 characters!",false,null);
        }
        if(User.usernameExists(newUsername)){
            return new Response("Username already taken",false,null);
        }
        if(!newUsername.matches("[a-zA-Z0-9_]")){
            return new Response("New username contains Special Characters! Please remove them and try again!",false,null);
        }
        if(newUsername.equalsIgnoreCase(UserController.logonUser.getUsername())){
            return new Response("you already have this username!",false);
        }
        UserController.logonUser.setUsername(newUsername);
        return new Response("Username changed successfully!",true);
    }

    public Response showTeams(){
        Team[] teams = UserController.logonUser.getTeams();
        StringBuilder response = new StringBuilder();
        String [] teamNames = new String[teams.length];
        for (int i = 0; i < teams.length; i++) {
            Team team = teams[i];
            response.append(team.getName()).append(",");
            teamNames[i] = team.getName();
        }
        if(response.length() > 0)
            response = new StringBuilder(response.substring(0, response.length() - 1));
        return new Response(response.toString(),true,teams);
    }

    public Response showTeam(String teamName){
        Team team = Team.getTeamByName(teamName);
        if(team == null){
            return new Response("Team not found!",false);
        }
        String response = "";
        response += team.getName()+ "\n";
        response += team.getLeader().getUsername()+ "\n";
        if(team.getLeader() != UserController.logonUser){
            response += team.getLeader()+ "\n";
        }
        User [] members = team.getMembers();
        Arrays.sort(members);
        for(User user : members){
            response += user.getUsername() + "\n";
        }
        return new Response(response,true,team);
    }

    private boolean isHard(String newPassword) {
        return newPassword.length() >= 8 && newPassword.matches(".*[A-Z].*")
                && newPassword.matches(".*[a-z].*")&&newPassword.matches(".*[0-9].*");
    }

    public Response getMyProfile() {
        String answer = UserController.logonUser.toString();

        return new Response(answer,true,UserController.logonUser);
    }

    public Response getLogs() {
        ArrayList<LocalDate> dates = UserController.logonUser.getLogs();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String answer = "";
        for(LocalDate date : dates){
            answer +=  date.format(formatter) + "\n";
        }
        return new Response(answer,true,dates);
    }
}
