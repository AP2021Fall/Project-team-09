package controller;

import com.google.gson.Gson;
import model.MRequest;
import model.User;

public class LoginController {

    private final String LOGIN_PATH = "/auth/signIn";
    private final String SIGNUP_PATH = "/auth/signUp";

    private final String SUCCESS_LOGOUT =
            "user logged out successfully!";

    private static LoginController loginController = null;

    public static LoginController getInstance() {
        if (loginController == null)
            loginController = new LoginController();
        return loginController;
    }

    public MResponse userCreate(String username, String password1, String password2, String email) {
        return new MRequest()
                .setPath(SIGNUP_PATH)
                .addArg("username", username)
                .addArg("password1", password1)
                .addArg("password2", password2)
                .addArg("email", email)
                .post();
    }

    public MResponse userLogin(String username, String password) {
        MResponse response = new MRequest()
                .setPath(LOGIN_PATH)
                .addArg("username", username)
                .addArg("password", password)
                .post();
        if (response.isSuccess()) {
            System.out.println(response);
            User user = new Gson().fromJson(response.getObject().toString(), User.class);
            UserController.loggedUser = user;
        }
        return response;
    }

    public MResponse logout() {
        UserController.logout();
        return new MResponse(SUCCESS_LOGOUT, true);
    }

    public MResponse adminLogin(String username, String password) {
        return new MRequest()
                .setPath(LOGIN_PATH)
                .addArg("username", username)
                .addArg("password", password)
                .post();
    }
}
