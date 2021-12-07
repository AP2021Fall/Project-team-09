package controller;

import model.User;

public class UserController {
    private static UserController userController = null;
    private User logonUser = null;

    public static UserController getInstance() {
        if(userController == null)
            userController = new UserController();
        return userController;
    }

    public User getLogonUser() {
        return logonUser;
    }

    public void setLogonUser(User logonUser) {
        this.logonUser = logonUser;
    }
}
