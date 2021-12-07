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
        return new Response("Passowrd changed succesfully!" , true);
    }

    private boolean isHard(String newPassword) {
        return newPassword.length() >= 8 && newPassword.matches(".*[A-Z].*")
                && newPassword.matches(".*[a-z].*")&&newPassword.matches(".*[0-9].*");
    }
}
