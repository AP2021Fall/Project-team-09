package controller;

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

    public Response userLogin(String username, String password){
        if(!User.usernameExists(username)){
            return new Response("There is not any user with username: " + username + "!",false);
        }
        User user = User.getUser(username,password);
        if(user == null){
            return new Response("Username and password didn’t match!",false);
        }
        UserController.logonUser = user;
        user.logLogin();
        return new Response("user logged in successfully!",true,user);
    }

    public Response logout(){
        UserController.logout();
        return new Response("user logged out successfully!",true);
    }
}
