package controller;

import model.Response;
import model.User;

public class LoginController {
    private static LoginController loginController = null;

    public static LoginController getInstance() {
        if(loginController == null)
            loginController = new LoginController();
        return loginController;
    }

    public Response userCreate(String username, String password1, String password2, String email) {
        if (User.usernameExists(username)){
            return new Response("user with username " + username + " already exists!",false);
        }
        else if(!password1.equals(password2)){
            return new Response("Your passwords are not the same!",false);
        }
        else if(User.emailExists(email)){
            return new Response("User with this email already exists!",false);
        }
        else if(!email.matches("[a-zA-Z0-9]+@(yahoo.com|gmail.com)")){
            return new Response("Email address is invalid!",false);
        }

        User user = User.createUser(username,password1,email);

        return new Response("user created successfully!",true,user);
    }
}
