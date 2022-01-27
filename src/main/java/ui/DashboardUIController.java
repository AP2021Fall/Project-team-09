package ui;

import controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import ui.list_item.*;
import utilities.SharedPreferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DashboardUIController implements Initializable, GUI {

    private final String SELECTED_TEAM =
            "team";

    private final int PROFILE = 0;
    private final int PROFILE_INFO = 0;
    private final int PROFILE_TEAMS = 1;
    private final int PROFILE_AUTH = 2;

    private final int TEAM = 1;
    private final int TEAMS_PAGE = 1;
    private final int MEMBERS = 0;
    private final int ROADMAP = 1;
    private final int CHATROOM = 2;
    private final int BOARDS = 3;

    private final int TASKS = 2;
    private final int NOTIFICATION = 3;
    private final int REQUESTS = 4;
    private final int CALENDAR = 5;

    private final int CREATE_REQUEST = 6;
    private final int ADD_MEMBER = 7;
    private final int CREATE_BOARD = 8;
    private final int CREATE_TASK = 9;

    private int TAB = 0;
    private int SUB_TAB = 0;

    @FXML
    private TabPane mainTabPane;

    // profile

    @FXML
    private TabPane profileTabPane;

    // personal info

    @FXML
    private TextField PIUsername;

    @FXML
    private TextField PIFirstName;

    @FXML
    private TextField PILastName;

    @FXML
    private DatePicker PIBirthDate;

    @FXML
    private TextField PIEmail;


    // auth

    @FXML
    private PasswordField PAOldPass;

    @FXML
    private PasswordField PANewPass;

    @FXML
    private VBox PTeamsItemHolder;

    @FXML
    private TextField PTSearchInput;


    private double xOffset, yOffset;

    public static final String DASH_PAGE = "src/main/resources/ui/pages/dash.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPaneHandler(null, PROFILE, PROFILE_INFO);
    }

    // main menu listeners

    @FXML
    private void onProfile() {
        tabPaneHandler(null, PROFILE, 0);
    }

    @FXML
    private void onTeam() {
        tabPaneHandler(null, TEAM, 0);
    }

    @FXML
    private void onTasks() {
        tabPaneHandler(null, TASKS, 0);
    }

    @FXML
    private void onNotification() {
        tabPaneHandler(null, NOTIFICATION, 0);
    }

    @FXML
    private void onRequests() {
        tabPaneHandler(null, REQUESTS, 0);
    }


    // profile sub section menu listeners

    @FXML
    private void onProfileInfoSection() {
        tabPaneHandler(profileTabPane, PROFILE, PROFILE_INFO);
    }

    @FXML
    private void onProfileTeamsSection() {
        tabPaneHandler(profileTabPane, PROFILE, PROFILE_TEAMS);
    }

    @FXML
    private void onProfileAuthSection() {
        tabPaneHandler(profileTabPane, PROFILE, PROFILE_AUTH);
    }

    private void tabPaneHandler(TabPane subTabPane, int tab, int subTab) {

        if (tab != TAB) {
            mainTabPane.getSelectionModel().select(tab);
            TAB = tab;
        }

        if (subTabPane == null)
            return;

        subTabPane.getSelectionModel().select(subTab);
        SUB_TAB = subTab;

        if (tab == PROFILE) {
            switch (subTab) {
                case PROFILE_INFO:
                    setProfile();
                    break;
                case PROFILE_TEAMS:
                    setTeams();
                    break;
            }
        }
    }


    private void showPage(String pathName) {
        try {
            Stage currentStage =
                    (Stage) PIFirstName.getScene()
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

    private void logout() {
        showPage(AuthenticationUIController.AUTH_PAGE);
    }


    // profile > info
    private void setProfile() {
//        Response response = ProfileMenuController.getInstance().getMyProfile();
//        if (!response.isSuccess())
//            return;

//        User user = (User) response.getObject();
        User user = UserController.getLoggedUser();

        if (user == null)
            return;

        setText(PIUsername, user.getUsername());
        setText(PIFirstName, user.getFirstname());
        setText(PILastName, user.getLastName());
        setDate(PIBirthDate, user.getBirthday());
        setText(PIEmail, user.getEmail());
    }

    @FXML
    private void onPISaveChanges() {
        String firstName = getValue(PIFirstName);
        String lastName = getValue(PILastName);
        LocalDate birthDate = PIBirthDate.getValue();

        Response response =
                ProfileMenuController.getInstance().updateProfile(firstName, lastName, birthDate);
        showResponse(response);
        save();
    }


    // profile > teams

    private void setTeams() {
        ArrayList<Team> teams = null;

        Response response =
                ProfileMenuController.getInstance().showTeams();

//        showResponse(response);

        if (response.isSuccess()) {
            teams = (ArrayList<Team>) response.getObject();
        }

        PTeamsItemHolder.getChildren().clear();
        if (teams != null) {
            for (Team team : teams) {
                TeamItem teamItem = new TeamItem(team);
                teamItem.setOnItemClickListener(new TeamItem.OnItemClickListener() {
                    @Override
                    public void onClick(Team team) {

                    }
                });
                HBox teamBox = teamItem.draw();
                PTeamsItemHolder.getChildren().add(teamBox);
            }
            onTeamSearchListener(teams);
        }
    }

    private void onTeamSearchListener(ArrayList<Team> teams) {
        if (!PTeamsItemHolder.getChildren().isEmpty()) {
            ArrayList<Team> filteredTeams = new ArrayList<>();
            PTSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                for (Team team : teams) {
                    if (team.getName().contains(newValue)) {
                        filteredTeams.add(team);
                    }
                }
            });
            for (Team team : filteredTeams) {
                TeamItem teamItem = new TeamItem(team);
                teamItem.setOnItemClickListener(new TeamItem.OnItemClickListener() {
                    @Override
                    public void onClick(Team team) {

                    }
                });
                HBox teamBox = teamItem.draw();
                PTeamsItemHolder.getChildren().add(teamBox);
            }
        }
    }


    // profile > auth

    @FXML
    private void onPASaveChanges() {
        String oldPassword = getValue(PAOldPass);
        String newPassword = getValue(PANewPass);

        Response response =
                ProfileMenuController.getInstance().changePassword(oldPassword, newPassword);
        showResponse(response);

        if (response.isSuccess()) {
            logout();
            ProfileMenuController.getInstance().resetTries();
        }
        if (UserController.getLoggedUser() == null) {
            logout();
            ProfileMenuController.getInstance().resetTries();
            return;
        }
        save();
    }
}
