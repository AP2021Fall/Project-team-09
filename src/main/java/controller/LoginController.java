package controller;

import model.User;

public class LoginController {

    private final String SUCCESS_USER_CREATED =
            "user created successfully!";
    private final String SUCCESS_LOGIN =
            "user logged in successfully!";
    private final String SUCCESS_LOGOUT =
            "user logged out successfully!";

    private final String WARN_USER_EXISTS =
            "user with username %s already exists!";
    private final String WARN_PASS_NOT_MATCH =
            "Your passwords are not the same!";
    private final String WARN_EMAIL_EXISTS =
            "User with this email already exists!";
    private final String WARN_EMAIL_INVALID =
            "Email address is invalid";
    private final String WARN_USER_NOT_EXIST =
            "There is not any user with username: %s!";
    private final String WARN_UP_NOT_MATCH =
            "Username and password didn’t match!";
    private final String WARN_WEAK_PASS =
            "Please Choose A strong Password (Containing at least 8 characters including 1 digit " +
                    "and 1 Capital Letter)";

    private final String EMAIL_REGEXP =
            "[a-zA-Z0-9]+@(yahoo.com|gmail.com)";

    private static LoginController loginController = null;

    public static LoginController getInstance() {
        if (loginController == null)
            loginController = new LoginController();
        return loginController;
    }

    public Response userCreate(String username, String password1, String password2, String email) {
        if (User.usernameExists(username))
            return new Response(String.format(WARN_USER_EXISTS, username), false);

        if (!password1.equals(password2))
            return new Response(WARN_PASS_NOT_MATCH, false);

        if (!isHard(password1))
            return new Response(WARN_WEAK_PASS, false);

        if (User.emailExists(email))
            return new Response(WARN_EMAIL_EXISTS, false);

        if (!email.matches(EMAIL_REGEXP))
            return new Response(WARN_EMAIL_INVALID, false);

        User user = User.createUser(username, password1, email);

        return new Response(SUCCESS_USER_CREATED, true, user);
    }

    public Response userLogin(String username, String password) {
        if (!User.usernameExists(username) && !User.isAdmin(username)) {
            return new Response(String.format(WARN_USER_NOT_EXIST, username), false);
        }
        User user = User.getUser(username, password);
        if (user == null)
            return new Response(WARN_UP_NOT_MATCH, false);

        UserController.loggedUser = user;
        user.logLogin();
        return new Response(SUCCESS_LOGIN, true, user);
    }

    public Response logout() {
        UserController.logout();
        return new Response(SUCCESS_LOGOUT, true);
    }

    public Response adminLogin(String username, String password) {
        boolean isAdmin = User.checkAdmin(username, password);
        if (isAdmin) {
            UserController.loggedUser = User.getAdmin();
            return new Response("Admin logon successfully", true);
        } else {
            return new Response("Username of password is incorrect", false);
        }
    }

    private boolean isHard(String newPassword) {
        return newPassword.length() >= 8 && newPassword.matches(".*[A-Z].*")
                && newPassword.matches(".*[a-z].*") && newPassword.matches(".*[0-9].*");
    }
}
