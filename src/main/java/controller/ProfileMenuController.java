package controller;

import model.User;

public class ProfileMenuController {
    private static ProfileMenuController controller = null;

    public static ProfileMenuController getInstance() {
        if(controller == null)
            controller = new ProfileMenuController();
        return controller;
    }

    public Response changePassword(String oldPassword,String newPassword){
        User user = UserController.getInstance().getLogonUser();
        if(user.getPassword().equals(oldPassword)){
            if(user.passwordInHistory(newPassword)){
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
        if(newUsername.equalsIgnoreCase(UserController.getInstance().getLogonUser().getUsername())){
            return new Response("you already have this username!",false);
        }
        UserController.getInstance().getLogonUser().setUsername(newUsername);
        return new Response("Username changed successfully!",true);
    }

    private boolean isHard(String newPassword) {
        return newPassword.length() >= 8 && newPassword.matches(".*[A-Z].*")
                && newPassword.matches(".*[a-z].*")&&newPassword.matches(".*[0-9].*");
    }
}
