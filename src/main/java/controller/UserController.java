package controller;

public class UserController {
    private static UserController userController = null;

    public static UserController getInstance() {
        if(userController == null)
            userController = new UserController();
        return userController;
    }
}
