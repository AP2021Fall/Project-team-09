import controller.LoginController;
import controller.MResponse;
import controller.SaveAndLoadController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserAuthenticationTest {

    @Test
    void alreadyExistingUser() {
        String username = "test";
        String password = "Secret761*";
        String email = "test@gmail.com";
        LoginController.getInstance().userCreate(username, password, password, email);

        String newEmail = "test1@gmail.com";
        MResponse MResponse =
                LoginController.getInstance().userCreate(username, password, password,
                        newEmail);

        String WARN_USER_EXISTS =
                "user with username %s already exists!";
        assertEquals(MResponse.getMessage(), String.format(WARN_USER_EXISTS, username));
    }

    @Test
    void invalidEmail() {
        String username = "test1";
        String password = "Secret761*";
        String email = "test@invalid.com";

        MResponse MResponse = LoginController.getInstance()
                .userCreate(username, password, password, email);
        String WARN_EMAIL_INVALID =
                "Email address is invalid";
        assertEquals(MResponse.getMessage(), WARN_EMAIL_INVALID);
    }

    @Test
    void weakPassword() {
        String username = "test2";
        String password = "123";
        String email = "test@gmail.com";

        MResponse MResponse = LoginController.getInstance()
                .userCreate(username, password, password, email);
        String WARN_WEAK_PASS =
                "Please Choose A strong Password (Containing at least 8 characters including 1 digit " +
                        "and 1 Capital Letter)";
        assertEquals(MResponse.getMessage(), WARN_WEAK_PASS);
    }

    @Test
    void passwordsNotSame() {
        String username = "test3";
        String password1 = "123";
        String password2 = "1234";
        String email = "test@gmail.com";

        MResponse MResponse = LoginController.getInstance()
                .userCreate(username, password1, password2, email);
        String WARN_PASS_NOT_MATCH =
                "Your passwords are not the same!";
        assertEquals(MResponse.getMessage(), WARN_PASS_NOT_MATCH);
    }

    @Test
    void userPassNotMatch() {
        SaveAndLoadController.load();
        String username = "test";
        String password = "Secret761*";
        String email = "test@gmail.com";
        LoginController.getInstance().userCreate(username, password, password, email);

        String user = "test";
        String pass = "Secret761";

        MResponse MResponse = LoginController.getInstance()
                .userLogin(user, pass);

        String WARN_UP_NOT_MATCH =
                "Username and password didn’t match!";
        assertEquals(MResponse.getMessage(), WARN_UP_NOT_MATCH);
    }

    @Test
    void userNotExists() {
        SaveAndLoadController.load();
        String username = "test4";
        String password = "123";

        MResponse MResponse = LoginController.getInstance()
                .userLogin(username, password);

        String WARN_USER_NOT_EXIST =
                "There is not any user with username: %s!";
        assertEquals(MResponse.getMessage(), String.format(WARN_USER_NOT_EXIST, username));
    }
}
