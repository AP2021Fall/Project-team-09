package controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.MRequest;
import model.User;

public class LoginController {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String PASSWORD1 = "password1";
    private static final String PASSWORD2 = "password2";
    private static final String EMAIL = "email";

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
                .addArg(USERNAME, username)
                .addArg(PASSWORD1, password1)
                .addArg(PASSWORD2, password2)
                .addArg(EMAIL, email)
                .post();
    }

    public MResponse userLogin(String username, String password) {
        MResponse response = new MRequest()
                .setPath(LOGIN_PATH)
                .addArg(USERNAME, username)
                .addArg(PASSWORD, password)
                .post();
        if (response.isSuccess()) {
            User user = new Gson().fromJson((String) response.getObject(), User.class);
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
                .addArg(USERNAME, username)
                .addArg(PASSWORD, password)
                .post();
    }
}
