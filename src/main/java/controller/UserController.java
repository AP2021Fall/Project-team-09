package controller;

import model.User;

public class UserController {
    static User logonUser = null;

    public static User getLogonUser() {
        return logonUser;
    }
}
