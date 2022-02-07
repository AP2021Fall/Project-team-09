package ui;

import controller.LoginController;
import controller.MResponse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthenticationUIController implements Initializable, GUI {

    private final int SIGN_IN = 0;
    private final int SIGN_UP = 1;

    private int TAB = 0;

    // sign in

    @FXML
    private TextField SIUsername;

    @FXML
    private PasswordField SIPassword;

    // sign up

    @FXML
    private TextField SUUsername;

    @FXML
    private PasswordField SUPassword;

    @FXML
    private PasswordField SUCPassword;

    @FXML
    private TextField SUEmail;

    @FXML
    private TabPane mainTabPane;

    private double xOffset, yOffset;

    public static final String AUTH_PAGE = "src/main/resources/ui/pages/auth.fxml";


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onSignInMenu() {
        tabPaneHandler(SIGN_IN);
        clearFields(SIUsername, SIPassword, SUUsername, SUPassword,
                SUCPassword, SUEmail);
    }

    @FXML
    private void onSignUpMenu() {
        tabPaneHandler(SIGN_UP);
        clearFields(SIUsername, SIPassword, SUUsername, SUPassword,
                SUCPassword, SUEmail);
    }

    private void tabPaneHandler(int tab) {
        if (tab != TAB) {
            mainTabPane.getSelectionModel().select(tab);
            TAB = tab;
        }
    }

    private void showPage(String pathName) {
        try {
            Stage currentStage =
                    (Stage) SIUsername.getScene()
                            .getWindow();

            currentStage.close();
            startStage(pathName);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void startStage(String pathName) throws IOException {
        URL url = new File(pathName).toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("Hello World");
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    // sign in

    @FXML
    private void onSignIn() {
        String username = getValue(SIUsername);
        String password = getValue(SIPassword);

        MResponse MResponse =
                LoginController.getInstance().userLogin(username, password);

        if (MResponse.isSuccess())
            showPage(DashboardUIController.DASH_PAGE);
        else showResponse(MResponse);
    }

    // sign up

    @FXML
    private void onSignUp() {
        String username = getValue(SUUsername);
        String password = getValue(SUPassword);
        String confirmPass = getValue(SUCPassword);
        String email = getValue(SUEmail);

        MResponse MResponse =
                LoginController.getInstance()
                        .userCreate(username, password, confirmPass, email);
        if (MResponse.isSuccess())
            tabPaneHandler(SIGN_IN);
        else showResponse(MResponse);
        save();
    }
}
