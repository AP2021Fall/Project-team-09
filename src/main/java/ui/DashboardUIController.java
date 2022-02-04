package ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import ui.list_item.*;
import utilities.AlertHandler;
import utilities.SharedPreferences;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class DashboardUIController implements Initializable, GUI {

    private final String SELECTED_TEAM =
            "team";
    private final String BOARD = "board";
    private final String TASK = "task";
    private final String ONLY_MINE = "only_mine";
    private final String SORTED_TEAM = "sorted_team";
    private final String SORTED_PRIORITY = "sorted_priority";
    private final String SORTED_DEADLINE = "sorted_deadline";
    private final String SORTED_A_USERS = "sorted_a_users";
    private final String SORTED_S_USERS = "sorted_s_users";
    private final String SELECTED_USER = "selected_user";
    private final String SELECTED_PROFILE = "selected_profile";

    private final int PROFILE = 0;
    private final int PROFILE_INFO = 0;
    private final int PROFILE_TEAMS = 1;
    private final int PROFILE_AUTH = 2;
    private final int PROFILE_USERNAME = 3;

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

    private final int USERS = 6;
    private final int TEAMS = 7;
    private final int STATISTICS = 8;
    private final int A_NOTIFICATION = 17;

    private final int CREATE_REQUEST = 9;
    private final int ADD_MEMBER = 10;
    private final int CREATE_BOARD = 11;
    private final int CREATE_TASK = 12;
    private final int BOARD_PAGE = 13;
    private final int CREATE_CATEGORY = 14;
    private final int NEW_NOTIFICATION = 15;
    private final int USER_NOTIFICATION = 16;
    private final int USER_PROFILE = 18;

    private int TAB = 0;
    private int SUB_TAB = 0;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private Label day;

    @FXML
    private Label date;

    @FXML
    private Label username;

    @FXML
    private Label role;

    @FXML
    private Button ProfileMenu;

    @FXML
    private Button TeamMenu;

    @FXML
    private Button TasksMenu;

    @FXML
    private Button NotificationMenu;

    @FXML
    private Button RequestsMenu;

    @FXML
    private Button CalendarMenu;

    @FXML
    private Button UsersMenu;

    @FXML
    private Button TeamsMenu;

    @FXML
    private Button StatisticsMenu;

    @FXML
    private Button ANotificationMenu;


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

    // username

    @FXML
    private TextField PUUsername;

    // team

    @FXML
    private VBox TTeamsItemHolder;

    @FXML
    private TextField TTSearchInput;

    // teams

    @FXML
    private TabPane teamTabPane;

    @FXML
    private VBox PTeamsItemHolder;

    @FXML
    private TextField PTSearchInput;

    @FXML
    private Button TCTMenuButton;

    // teams > team page

    @FXML
    private TabPane TPTabPane;

    // teams > members

    @FXML
    private VBox TMemberItemHolder;

    @FXML
    private TextField TMSearchInput;

    @FXML
    private Button TAMButton;

    // teams > add member

    @FXML
    private VBox AMMembersItemHolder;

    @FXML
    private TextField AMSearchInput;

    // teams > roadmap

    @FXML
    private VBox TRTasksHolder;

    @FXML
    private TextField TRSearchInput;

    @FXML
    private PieChart TRChart;

    @FXML
    private Label TRAll;

    @FXML
    private Label TRInProgress;

    @FXML
    private Label TRDone;

    @FXML
    private Label TRFailed;

    // teams > chatroom

    @FXML
    private VBox TCChatItemHolder;

    @FXML
    private TextField TCMessageInput;

    // boards > boards

    @FXML
    private VBox TBBoardItemHolder;

    @FXML
    private TextField TBSearchInput;

    @FXML
    private Button TCBButton;

    // boards > board > create board

    @FXML
    private TextField CBNameInput;

    // boards > board page

    @FXML
    private HBox BCategoryHolder;

    @FXML
    private CheckBox BMyTasksCheck;

    // board > create category

    @FXML
    private TextField CCNameInput;

    // tasks

    @FXML
    private VBox TTaskItemHolder;

    @FXML
    private TextField TSearchInput;

    @FXML
    private ComboBox<String> TTeamCombo;

    @FXML
    private ComboBox<String> TPriorityCombo;

    @FXML
    private ComboBox<String> TDeadlineCombo;

    // tasks > create task

    @FXML
    private TextField TTNameInput;

    @FXML
    private ComboBox<String> TTPriorityCombo;

    @FXML
    private DatePicker TTStartDate;

    @FXML
    private TextField TTStartTime;

    @FXML
    private DatePicker TTDeadlineDate;

    @FXML
    private TextField TTDeadlineTime;

    @FXML
    private TextArea TTDescription;

    @FXML
    private VBox TTMemberItemHolder;

    @FXML
    private Button TTCreateTask;

    @FXML
    private Button TTSaveChanges;

    @FXML
    private Button TTClear;

    @FXML
    private TextField TTMSearchInput;

    // notifications

    @FXML
    private VBox NNotificationItemHolder;

    @FXML
    private Button NNNButton;

    // new notification

    @FXML
    private ComboBox<String> NNTeamCombo;

    @FXML
    private TextArea NNBody;

    // new user notification

    @FXML
    private Label NNUser;

    @FXML
    private TextArea NNUBody;


    // requests

    @FXML
    private VBox RTeamsItemHolder;

    @FXML
    private TextField RSearchInput;

    // create request

    @FXML
    private TextField CTTeamName;

    // calendar

    @FXML
    private VBox CTasksItemHolder;

    @FXML
    private TextField CTSearchInput;

    @FXML
    private ComboBox<String> CDCombo;

    // admin

    // users

    @FXML
    private TextField AUSearchInput;

    @FXML
    private ComboBox<String> AUNameCombo;

    @FXML
    private ComboBox<String> AUScoreCombo;

    @FXML
    private VBox AUItemHolder;

    // teams

    @FXML
    private TextField ATSearchInput;

    @FXML
    private VBox ATItemHolder;

    // statistics

    @FXML
    private Label ASUTotal;

    @FXML
    private Label ASTTotal;

    @FXML
    private Label ASTDone;

    @FXML
    private Label ASTFailed;

    @FXML
    private VBox ASTUItemHolder;

    // admin notification

    @FXML
    private TextArea NNABody;

    // user profile

    @FXML
    private TextField UPUsername;

    @FXML
    private TextField UPFirstName;

    @FXML
    private TextField UPLastName;

    @FXML
    private DatePicker UPBirthdate;

    @FXML
    private TextField UPEmail;

    @FXML
    private TextField UPRole;


    private double xOffset, yOffset;

    public static final String DASH_PAGE = "src/main/resources/ui/pages/dash.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = UserController.getLoggedUser();

        day.setText(LocalDate.now().getDayOfWeek().name());
        date.setText(LocalDate.now().toString());

        if (user != null) {
            username.setText(user.getUsername());
            role.setText(user.getType().name());

            if (user.isAdmin()) {
                tabPaneHandler(null, USERS, 0);
                ProfileMenu.setVisible(false);
                ProfileMenu.setManaged(false);
                TeamMenu.setVisible(false);
                TeamMenu.setManaged(false);
                TasksMenu.setVisible(false);
                TasksMenu.setManaged(false);
                NotificationMenu.setVisible(false);
                NotificationMenu.setManaged(false);
                RequestsMenu.setVisible(false);
                RequestsMenu.setManaged(false);
                CalendarMenu.setVisible(false);
                CalendarMenu.setManaged(false);
            } else {
                tabPaneHandler(profileTabPane, PROFILE, PROFILE_INFO);
                UsersMenu.setVisible(false);
                UsersMenu.setManaged(false);
                TeamsMenu.setVisible(false);
                TeamsMenu.setManaged(false);
                StatisticsMenu.setVisible(false);
                StatisticsMenu.setManaged(false);
                ANotificationMenu.setVisible(false);
                ANotificationMenu.setManaged(false);

                if (!user.isTeamLeader()) {
                    RequestsMenu.setVisible(false);
                    RequestsMenu.setManaged(false);

                    TAMButton.setVisible(false);
                    TAMButton.setManaged(false);
                    TCBButton.setVisible(false);
                    TCBButton.setManaged(false);
                    TCTMenuButton.setVisible(false);
                    TCTMenuButton.setManaged(false);
                    NNNButton.setVisible(false);
                    NNNButton.setManaged(false);
                    TTCreateTask.setVisible(false);
                    TTSaveChanges.setVisible(false);
                    TTSaveChanges.setManaged(false);
                    TTClear.setVisible(false);
                    TTClear.setManaged(false);
                }
            }
        }
    }

    // main menu listeners

    @FXML
    private void onProfile() {
        tabPaneHandler(profileTabPane, PROFILE, PROFILE_INFO);
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

    @FXML
    private void onCalendar() {
        tabPaneHandler(null, CALENDAR, 0);
    }

    @FXML
    private void onAUsers() {
        tabPaneHandler(null, USERS, 0);
    }

    @FXML
    private void onATeams() {
        tabPaneHandler(null, TEAMS, 0);
    }

    @FXML
    private void onAStatistics() {
        tabPaneHandler(null, STATISTICS, 0);
    }

    @FXML
    private void onANotification() {
        tabPaneHandler(null, A_NOTIFICATION, 0);
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

    @FXML
    private void onProfileUserSection() {
        tabPaneHandler(profileTabPane, PROFILE, PROFILE_USERNAME);
    }

    private void tabPaneHandler(TabPane subTabPane, int tab, int subTab) {
        clearFields(RSearchInput, AMSearchInput, PTSearchInput, TBSearchInput, RSearchInput, TSearchInput, TTSearchInput,
                TTMSearchInput, TMSearchInput, TRSearchInput);

        clearFields(CBNameInput, CCNameInput);

        if (tab == TEAM && tab != TAB) {
            teamsSetTeams();
            teamTabPane.getSelectionModel().select(0);
        } else if (tab == REQUESTS && tab != TAB) {
            requestsSetTeams();
        } else if (tab == ADD_MEMBER && tab != TAB) {
            setAMMembers();
        } else if (tab == CREATE_TASK) {
            setUpTaskCreationPage();
            setTaskMembers();
        } else if (tab == TASKS) {
            setUpTasksPage();
            setTTasks();
        } else if (tab == NOTIFICATION) {
            setNotifications();
        } else if (tab == BOARD_PAGE) {
            setBoard();
        } else if (tab == CALENDAR) {
            setUpCalendar();
        } else if (tab == USERS) {
            setAUsers();
            setUpAUCombos();
        } else if (tab == TEAMS) {
            setATeams();
        } else if (tab == STATISTICS) {
            setAStatistics();
        } else if (tab == NEW_NOTIFICATION) {
            setUpNewNotification();
        } else if (tab == USER_NOTIFICATION) {
            setUpNewUserNotification();
        } else if (tab == USER_PROFILE) {
            setUpUserProfile();
        }

        if (tab != TAB) {
            mainTabPane.getSelectionModel().select(tab);
            TAB = tab;
        }

        if (subTabPane == null)
            return;

//        if (subTab != SUB_TAB) {
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
                case PROFILE_USERNAME:
                    setUsername();
            }
        }
//        else if (tab == TEAM && tab != TAB) {
//            switch (subTab) {
//                case MEMBERS:
//                    setMembers();
//                    break;
//                case ROADMAP:
//                    break;
//                case CHATROOM:
//                    break;
//                case BOARDS:
//                    break;
//            }
//        }
//        }
    }

    private void teamPageTabPaneHandler(int subTab) {
        if (TAB != TEAM) {
            mainTabPane.getSelectionModel().select(TEAM);
        }

        teamTabPane.getSelectionModel().select(TEAMS_PAGE);

        TPTabPane.getSelectionModel().select(subTab);

        switch (subTab) {
            case MEMBERS:
                setMembers();
                break;
            case ROADMAP:
                setRoadMap();
                setUpChart();
                break;
            case CHATROOM:
                setChatroom();
                break;
            case BOARDS:
                setBoards();
                break;
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
        MResponse response = ProfileMenuController.getInstance().getMyProfile();
        if (!response.isSuccess())
            return;

        Type typeMyType = new TypeToken<User>() {
        }.getType();

        User user = new Gson().fromJson((String) response.getObject(), typeMyType);

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

        MResponse MResponse =
                ProfileMenuController.getInstance().updateProfile(firstName, lastName, birthDate);
        showResponse(MResponse);
        save();
    }


    // profile > teams
    private void setTeams() {
        ArrayList<Team> teams = null;

        MResponse MResponse =
                ProfileMenuController.getInstance().showTeams();

//        showResponse(response);

        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<ArrayList<Team>>() {
            }.getType();

            teams = new Gson().fromJson((String) MResponse.getObject(), typeMyType);
        }

        PTeamsItemHolder.getChildren().clear();
        if (teams != null) {
            for (Team team : teams) {
                TeamItem teamItem = new TeamItem(team);
                teamItem.setOnItemClickListener(new TeamItem.OnItemClickListener() {
                    @Override
                    public void onClick(Team team) {
                        if (!team.isSuspended(UserController.getLoggedUser())) {
                            if (!team.isPending()) {
                                SharedPreferences.add(SELECTED_TEAM, team);
                                teamPageTabPaneHandler(MEMBERS);
                            } else {
                                new AlertHandler(Alert.AlertType.ERROR,
                                        "Team is waiting for admin confirmation!").ShowAlert();
                            }
                        } else {
                            new AlertHandler(Alert.AlertType.ERROR,
                                    "You have been suspended from this team!").ShowAlert();
                        }
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
            PTSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                PTeamsItemHolder.getChildren().clear();
                for (Team team : teams) {
                    if (team.getName().contains(newValue)) {
                        TeamItem teamItem = new TeamItem(team);
                        teamItem.setOnItemClickListener(new TeamItem.OnItemClickListener() {
                            @Override
                            public void onClick(Team team) {
                                if (!team.isSuspended(UserController.getLoggedUser())) {
                                    if (!team.isPending()) {
                                        SharedPreferences.add(SELECTED_TEAM, team);
                                        teamPageTabPaneHandler(MEMBERS);
                                    } else {
                                        new AlertHandler(Alert.AlertType.ERROR,
                                                "Team is waiting for admin confirmation!").ShowAlert();
                                    }
                                } else {
                                    new AlertHandler(Alert.AlertType.ERROR,
                                            "You have been suspended from this team!").ShowAlert();
                                }
                            }
                        });
                        HBox teamBox = teamItem.draw();
                        PTeamsItemHolder.getChildren().add(teamBox);
                    }
                }
            });
        }
    }

    private void setUsername() {
        User user = UserController.getLoggedUser();

        PUUsername.setText(user.getUsername());
    }

    @FXML
    private void onPUUpdate() {
        String username = getValue(PUUsername);

        MResponse MResponse =
                ProfileMenuController.getInstance().changeUsername(username);
        showResponse(MResponse);
        if (MResponse.isSuccess()) {
            UserController.logout();
            logout();
        }
        save();
    }


    // profile > auth
    @FXML
    private void onPASaveChanges() {
        String oldPassword = getValue(PAOldPass);
        String newPassword = getValue(PANewPass);

        MResponse MResponse =
                ProfileMenuController.getInstance().changePassword(oldPassword, newPassword);
        showResponse(MResponse);

        if (MResponse.isSuccess()) {
            logout();
//            ProfileMenuController.getInstance().resetTries();
        }
        if (UserController.getLoggedUser() == null) {
            logout();
//            ProfileMenuController.getInstance().resetTries();
            return;
        }
        save();
    }

    // teams

    private void teamsSetTeams() {
        ArrayList<Team> teams = null;

        MResponse MResponse =
                ProfileMenuController.getInstance().showTeams();

//        showResponse(response);
        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<ArrayList<Team>>() {
            }.getType();

            teams = new Gson().fromJson((String) MResponse.getObject(), typeMyType);
        }


        TTeamsItemHolder.getChildren().clear();
        if (teams != null) {
            for (Team team : teams) {
                TeamItem teamItem = new TeamItem(team);
                teamItem.setOnItemClickListener(new TeamItem.OnItemClickListener() {
                    @Override
                    public void onClick(Team team) {
                        if (!team.isSuspended(UserController.getLoggedUser())) {
                            if (!team.isPending()) {
                                SharedPreferences.add(SELECTED_TEAM, team);
                                teamPageTabPaneHandler(MEMBERS);
                            } else {
                                new AlertHandler(Alert.AlertType.ERROR,
                                        "Team is waiting for admin confirmation!").ShowAlert();
                            }
                        } else {
                            new AlertHandler(Alert.AlertType.ERROR,
                                    "You have been suspended from this team!").ShowAlert();
                        }
                    }
                });
                HBox teamBox = teamItem.draw();
                TTeamsItemHolder.getChildren().add(teamBox);
            }
            onTeamTeamsSearchListener(teams);
        }
    }

    private void onTeamTeamsSearchListener(ArrayList<Team> teams) {
        if (!TTeamsItemHolder.getChildren().isEmpty()) {
            TTSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                TTeamsItemHolder.getChildren().clear();
                for (Team team : teams) {
                    if (team.getName().contains(newValue)) {
                        TeamItem teamItem = new TeamItem(team);
                        teamItem.setOnItemClickListener(new TeamItem.OnItemClickListener() {
                            @Override
                            public void onClick(Team team) {
                                if (!team.isSuspended(UserController.getLoggedUser())) {
                                    if (!team.isPending()) {
                                        SharedPreferences.add(SELECTED_TEAM, team);
                                        teamPageTabPaneHandler(MEMBERS);
                                    } else {
                                        new AlertHandler(Alert.AlertType.ERROR,
                                                "Team is waiting for admin confirmation!").ShowAlert();
                                    }
                                } else {
                                    new AlertHandler(Alert.AlertType.ERROR,
                                            "You have been suspended from this team!").ShowAlert();
                                }
                            }
                        });
                        HBox teamBox = teamItem.draw();
                        TTeamsItemHolder.getChildren().add(teamBox);
                    }
                }
            });
        }
    }

    // teams page

    @FXML
    private void onMembersMenu() {
        teamPageTabPaneHandler(MEMBERS);
    }

    @FXML
    private void onRoadmapMenu() {
        teamPageTabPaneHandler(ROADMAP);
    }

    @FXML
    private void onChatroomMenu() {
        teamPageTabPaneHandler(CHATROOM);
    }

    @FXML
    private void onBoardsMenu() {
        teamPageTabPaneHandler(BOARDS);
    }


    // team > members

    private void setMembers() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);
        if (team == null)
            return;

        ArrayList<User> members = team.getMembers();

        if (members == null)
            return;

        TMemberItemHolder.getChildren().clear();
        for (User user : members) {
            TeamMemberItem teamMemberItem = new TeamMemberItem(team, user);
            teamMemberItem.setOnItemClickListener(new TeamMemberItem.OnItemClickListener() {
                @Override
                public void onClick(User member) {
                    SharedPreferences.add(SELECTED_PROFILE, member);
                    tabPaneHandler(null, USER_PROFILE, 0);
                }

                @Override
                public void onRemove(User member) {
                    removeMember(team, member);
                    save();
                    setMembers();
                }

                @Override
                public void onMessage(User member) {
                    SharedPreferences.add(SELECTED_USER, member);
                    tabPaneHandler(null, USER_NOTIFICATION, 0);
                }

                @Override
                public void onPromote(User member) {
                    promoteMember(team, member);
                }

                @Override
                public void onSuspend(User member) {
                    suspendUser(team, member);
                }

                @Override
                public void onActivate(User member) {
                    activateUser(team, member);
                }
            });
            HBox teamMemberBox = teamMemberItem.draw();
            TMemberItemHolder.getChildren().add(teamMemberBox);
        }
        onTeamMemberSearchListener(members);
    }

    private void onTeamMemberSearchListener(ArrayList<User> members) {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);
        if (team == null)
            return;

        if (!TMemberItemHolder.getChildren().isEmpty()) {
            TMSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                TMemberItemHolder.getChildren().clear();
                for (User member : members) {
                    if (member.getUsername().contains(newValue)) {
                        TeamMemberItem teamMemberItem = new TeamMemberItem(team, member);
                        teamMemberItem.setOnItemClickListener(new TeamMemberItem.OnItemClickListener() {
                            @Override
                            public void onClick(User member) {
                                SharedPreferences.add(SELECTED_PROFILE, member);
                                tabPaneHandler(null, USER_PROFILE, 0);
                            }

                            @Override
                            public void onRemove(User member) {
                                removeMember(team, member);
                            }

                            @Override
                            public void onMessage(User member) {
                                SharedPreferences.add(SELECTED_USER, member);
                                tabPaneHandler(null, USER_NOTIFICATION, 0);
                            }

                            @Override
                            public void onPromote(User member) {
                                promoteMember(team, member);
                            }

                            @Override
                            public void onSuspend(User member) {
                                suspendUser(team, member);
                            }

                            @Override
                            public void onActivate(User member) {
                                activateUser(team, member);
                            }
                        });
                        HBox teamMemberBox = teamMemberItem.draw();
                        TMemberItemHolder.getChildren().add(teamMemberBox);
                    }
                }
            });
        }
    }

    private void suspendUser(Team team, User member) {
        team.suspendMember(member);
        save();
        setMembers();
    }

    private void activateUser(Team team, User member) {
        team.activateMember(member);
        save();
        setMembers();
    }

    private void removeMember(Team team, User member) {
        MResponse MResponse =
                TeamMenuController.getInstance().deleteMember(team, member.getUsername());
        showResponse(MResponse);
        save();
        setMembers();
    }

    private void promoteMember(Team team, User member) {
        MResponse MResponse =
                TeamMenuController.getInstance().promoteUser(team, member.getUsername(), "teamLeader");
        showResponse(MResponse);
        save();
        setMembers();
    }

    @FXML
    private void onAddMember() {
        tabPaneHandler(null, ADD_MEMBER, 0);
    }

    // team > add member

    private void setAMMembers() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        MResponse MResponse =
                TeamMenuController.getInstance().getAllUsers(team);
        AMMembersItemHolder.getChildren().clear();
        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<ArrayList<User>>() {
            }.getType();

            ArrayList<User> users = new Gson().fromJson((String) MResponse.getObject(), typeMyType);

            for (User user : users) {
                AssignMemberItem assignMemberItem = new AssignMemberItem(user);
                assignMemberItem.setOnItemClickListener(new AssignMemberItem.OnItemClickListener() {
                    @Override
                    public void onClick(User member) {
                        addMemberToTeam(member);
                    }
                });
                HBox assignMemberBox = assignMemberItem.draw();
                AMMembersItemHolder.getChildren().add(assignMemberBox);
            }
            onAMMembersSearchListener(users);
        }
    }

    private void onAMMembersSearchListener(ArrayList<User> users) {
        if (!AMMembersItemHolder.getChildren().isEmpty()) {
            AMSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                AMMembersItemHolder.getChildren().clear();
                for (User user : users) {
                    if (user.getUsername().contains(newValue)) {
                        AssignMemberItem assignMemberItem = new AssignMemberItem(user);
                        assignMemberItem.setOnItemClickListener(new AssignMemberItem.OnItemClickListener() {
                            @Override
                            public void onClick(User member) {
                                addMemberToTeam(member);
                            }
                        });
                        HBox assignMemberBox = assignMemberItem.draw();
                        AMMembersItemHolder.getChildren().add(assignMemberBox);
                    }
                }
            });
        }
    }

    private void addMemberToTeam(User member) {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        MResponse MResponse
                = TeamMenuController.getInstance().addMemberToTeam(team, member.getUsername());
        showResponse(MResponse);
        save();
        setAMMembers();
    }

    // team > roadmap

    private void setRoadMap() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        MResponse MResponse =
                TeamMenuController.getInstance().getRoadmap(team);

        TRTasksHolder.getChildren().clear();
        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<ArrayList<Task>>() {
            }.getType();

            ArrayList<Task> tasks = new Gson().fromJson((String) MResponse.getObject(), typeMyType);

            for (Task task : tasks) {
                RoadmapItem roadmapItem = new RoadmapItem(task);
                roadmapItem.setOnItemClickListener(new RoadmapItem.OnItemClickListener() {
                    @Override
                    public void onClick(Task task) {
                        SharedPreferences.add(TASK, task);
                        tabPaneHandler(null, CREATE_TASK, 0);
                    }
                });
                HBox roadmapBox = roadmapItem.draw();
                TRTasksHolder.getChildren().add(roadmapBox);
            }
            setTRTaskSearchListener(tasks);
        }
    }

    private void setTRTaskSearchListener(ArrayList<Task> tasks) {
        if (!TRTasksHolder.getChildren().isEmpty()) {
            TRSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                TRTasksHolder.getChildren().clear();
                for (Task task : tasks) {
                    if (task.getTitle().contains(newValue)) {
                        RoadmapItem roadmapItem = new RoadmapItem(task);
                        roadmapItem.setOnItemClickListener(new RoadmapItem.OnItemClickListener() {
                            @Override
                            public void onClick(Task task) {
                                SharedPreferences.add(TASK, task);
                                tabPaneHandler(null, CREATE_TASK, 0);
                            }
                        });
                        HBox roadmapBox = roadmapItem.draw();
                        TRTasksHolder.getChildren().add(roadmapBox);
                    }
                }
            });
        }
    }

    private void setUpChart() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        ArrayList<Task> tasks = team.getTasks();

        int totalTasks = team.getTasks().size();
        int totalInProgress = 0;
        int totalDone = 0;
        int totalFailed = 0;

        for (Task task : tasks) {
            if (task.isDone()) {
                totalDone++;
                continue;
            }
            if (task.isFailed()) {
                totalFailed++;
                continue;
            }
            totalInProgress++;
        }

        TRChart.getData().clear();

        TRChart.getData().add(new PieChart.Data("All", totalTasks));
        TRChart.getData().add(new PieChart.Data("In progress", totalInProgress));
        TRChart.getData().add(new PieChart.Data("Done", totalDone));
        TRChart.getData().add(new PieChart.Data("Failed", totalFailed));

        TRAll.setText(String.valueOf(totalTasks));
        TRInProgress.setText(String.valueOf(totalInProgress));
        TRDone.setText(String.valueOf(totalDone));
        TRFailed.setText(String.valueOf(totalFailed));
    }

    // team > chatroom

    private void setChatroom() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        MResponse MResponse =
                TeamMenuController.getInstance().getMessages(team);
        TCChatItemHolder.getChildren().clear();

        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<ArrayList<Message>>() {
            }.getType();
            ArrayList<Message> messages = new Gson().fromJson((String) MResponse.getObject(), typeMyType);

            for (Message message : messages) {
                ChatItem chatItem = new ChatItem(message);
                chatItem.setOnItemClickListener(new ChatItem.OnItemClickListener() {
                    @Override
                    public void onClick(Message message) {
                        SharedPreferences.add(SELECTED_PROFILE, message.getSender());
                        tabPaneHandler(null, USER_PROFILE, 0);
                    }
                });
                VBox chatBox = chatItem.draw();
                TCChatItemHolder.getChildren().add(chatBox);
            }
        }
    }

    @FXML
    private void onSendMessage() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        String message = getValue(TCMessageInput);

        MResponse MResponse =
                TeamMenuController.getInstance().sendMessage(team, message);
        if (MResponse.isSuccess()) {
            setChatroom();
        }
        clearFields(TCMessageInput);
        save();
    }


    // team > board

    private void setBoards() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        MResponse MResponse =
                BoardMenuController.getInstance().getBoards(team);

        TBBoardItemHolder.getChildren().clear();

        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<ArrayList<Board>>() {
            }.getType();

            ArrayList<Board> boards = new Gson().fromJson((String) MResponse.getObject(), typeMyType);

            for (Board board : boards) {
                BoardItem boardItem = new BoardItem(board);
                boardItem.setOnItemClickListener(new BoardItem.OnItemClickListener() {
                    @Override
                    public void onClick(Board board) {
                        SharedPreferences.add(BOARD, board);
                        tabPaneHandler(null, BOARD_PAGE, 0);
                    }

                    @Override
                    public void onRemove(Board board) {
                        removeBoard(board);
                    }
                });
                HBox boardBox = boardItem.draw();
                TBBoardItemHolder.getChildren().add(boardBox);
            }
            onTBBoardSearchListener(boards);
        }
    }

    private void onTBBoardSearchListener(ArrayList<Board> boards) {
        if (!TBBoardItemHolder.getChildren().isEmpty()) {
            TBSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                TBBoardItemHolder.getChildren().clear();
                for (Board board : boards) {
                    if (board.getName().contains(newValue)) {
                        BoardItem boardItem = new BoardItem(board);
                        boardItem.setOnItemClickListener(new BoardItem.OnItemClickListener() {
                            @Override
                            public void onClick(Board board) {
                                SharedPreferences.add(BOARD, board);
                                tabPaneHandler(null, BOARD_PAGE, 0);
                            }

                            @Override
                            public void onRemove(Board board) {
                                removeBoard(board);
                            }
                        });
                        HBox boardBox = boardItem.draw();
                        TBBoardItemHolder.getChildren().add(boardBox);
                    }
                }
            });
        }
    }

    private void removeBoard(Board board) {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        MResponse MResponse =
                BoardMenuController.getInstance().removeBoard(team, board.getName());
        showResponse(MResponse);
        save();
        setBoards();
    }

    @FXML
    private void onTCBoard() {
        tabPaneHandler(null, CREATE_BOARD, 0);
    }


    // team > board > create board
    @FXML
    private void onCreateBoard() {
        String boardName = getValue(CBNameInput);
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        MResponse MResponse =
                BoardMenuController.getInstance().createNewBoard(team, boardName);
        showResponse(MResponse);
        save();
    }

    // board > board page

    private void setBoard() {
        Board board = (Board) SharedPreferences.get(BOARD);
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (board == null)
            return;

        if (team == null)
            return;

        BCategoryHolder.getChildren().clear();
        BoardCategoryItem boardCategoryItem =
                new BoardCategoryItem(team, board, "All tasks", team.getNoCategoryTasks());
        boardCategoryItem.setOnItemClickListener(new BoardCategoryItem.OnItemClickListener() {
            @Override
            public void onClick(Task task) {
                SharedPreferences.add(TASK, task);
                tabPaneHandler(null, CREATE_TASK, 0);
            }

            @Override
            public void onDone(Task task) {
                if (task.getBoard() == null) {
                    MResponse MResponse =
                            BoardMenuController.getInstance()
                                    .addTaskToBoard(team, String.valueOf(task.getId()), board.getName());
                    showResponse(MResponse);
                }
                if (task.getBoard() != null) {
                    MResponse res = BoardMenuController.getInstance()
                            .forceMoveTaskToCategory(team, "done", task.getTitle(), board.getName());
                    showResponse(res);
                }
                save();
                setBoard();
            }

            @Override
            public void onNext(Task task) {
                if (task.getBoard() == null) {
                    MResponse MResponse =
                            BoardMenuController.getInstance()
                                    .addTaskToBoard(team, String.valueOf(task.getId()), board.getName());
                    showResponse(MResponse);
                }
                if (task.getBoard() != null) {
                    MResponse res = BoardMenuController.getInstance()
                            .moveTaskToNextCategory(team, task.getTitle(), board.getName());
                    showResponse(res);
                }
                save();
                setBoard();
            }

            @Override
            public void addToBoard(Task task) {
                if (!board.isDone()) {
                    MResponse res =
                            BoardMenuController.getInstance().setBoardToDone(team, board.getName());
                    showResponse(res);
                } else if (task.getBoard() == null) {
                    MResponse MResponse =
                            BoardMenuController.getInstance()
                                    .addTaskToBoard(team, String.valueOf(task.getId()), board.getName());
                    showResponse(MResponse);
                }
                save();
                setBoard();
            }
        });
        BCategoryHolder.getChildren().add(boardCategoryItem.draw());

        ArrayList<String> categories = new ArrayList<>(board.getCategories());
        System.out.println(categories);
        categories.add("done");
        categories.add("failed");

        for (String category : categories) {
            System.out.println(category);
            MResponse MResponse = null;
            if (category.equalsIgnoreCase("done") || category.equalsIgnoreCase("failed"))
                MResponse = BoardMenuController.getInstance().getSpecificCategoryTasks(team, category, board.getName());
            else
                MResponse = BoardMenuController.getInstance().showCategoryTasks(team, category, board.getName());

            ArrayList<Task> tasks = new ArrayList<>();
            if (MResponse.isSuccess()) {
                Type typeMyType = new TypeToken<ArrayList<Task>>() {
                }.getType();

                tasks = new Gson().fromJson((String) MResponse.getObject(), typeMyType);
            }
            System.out.println(tasks);
            BoardCategoryItem bciBox = new BoardCategoryItem(team, board, category, tasks);
            bciBox.setOnItemClickListener(new BoardCategoryItem.OnItemClickListener() {
                @Override
                public void onClick(Task task) {
                    SharedPreferences.add(TASK, task);
                    tabPaneHandler(null, CREATE_TASK, 0);
                }

                @Override
                public void onDone(Task task) {
                    if (task.getBoard() == null) {
                        MResponse MResponse =
                                BoardMenuController.getInstance()
                                        .addTaskToBoard(team, String.valueOf(task.getId()), board.getName());
                        showResponse(MResponse);
                    }
                    if (task.getBoard() != null) {
                        MResponse res = BoardMenuController.getInstance()
                                .forceMoveTaskToCategory(team, "done", task.getTitle(), board.getName());
                        showResponse(res);
                    }
                    save();
                    setBoard();
                }

                @Override
                public void onNext(Task task) {
                    if (task.getBoard() == null) {
                        MResponse MResponse =
                                BoardMenuController.getInstance()
                                        .addTaskToBoard(team, String.valueOf(task.getId()), board.getName());
                        showResponse(MResponse);
                    }
                    if (task.getBoard() != null) {
                        MResponse res = BoardMenuController.getInstance()
                                .moveTaskToNextCategory(team, task.getTitle(), board.getName());
                        showResponse(res);
                    }
                    save();
                    setBoard();
                }

                @Override
                public void addToBoard(Task task) {
                    if (!board.isDone()) {
                        MResponse res =
                                BoardMenuController.getInstance().setBoardToDone(team, board.getName());
                        showResponse(res);
                    } else if (task.getBoard() == null) {
                        MResponse MResponse =
                                BoardMenuController.getInstance()
                                        .addTaskToBoard(team, String.valueOf(task.getId()), board.getName());
                        showResponse(MResponse);
                    }
                    save();
                    setBoard();
                }
            });
            VBox boardVBox = bciBox.draw();
            BCategoryHolder.getChildren().add(boardVBox);
        }
    }

    @FXML
    private void onBCCategory() {
        tabPaneHandler(null, CREATE_CATEGORY, 0);
    }

    @FXML
    private void onCreateCategory() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);
        Board board = (Board) SharedPreferences.get(BOARD);
        String categoryName = getValue(CCNameInput);

        if (team == null)
            return;

        MResponse MResponse =
                BoardMenuController.getInstance().createNewCategory(team, categoryName, board.getName());
        showResponse(MResponse);
        save();
    }

    @FXML
    private void showMyTasks() {
        boolean isCheck = BMyTasksCheck.isSelected();

        SharedPreferences.add(ONLY_MINE, isCheck);
        setBoard();
    }

    // tasks
    private void setTTasks() {

        MResponse MResponse =
                TasksMenuController.getInstance().getAllTasks();
//
//        showResponse(response);

        TTaskItemHolder.getChildren().clear();

        String sortedTeam = (String) SharedPreferences.get(SORTED_TEAM);
        String sortedPriority = (String) SharedPreferences.get(SORTED_PRIORITY);
        String sortedDeadline = (String) SharedPreferences.get(SORTED_DEADLINE);


        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<HashMap<Team, ArrayList<Task>>>() {
            }.getType();
            HashMap<Team, ArrayList<Task>> teamTasks = new Gson().fromJson((String) MResponse.getObject(), typeMyType);

            for (Team team : teamTasks.keySet()) {
                if (sortedTeam != null)
                    if (!team.getName().equalsIgnoreCase(sortedTeam))
                        continue;
                ArrayList<Task> tasks = new ArrayList<>(team.getTasks());
                if (sortedPriority != null)
                    sortPriority(sortedPriority, tasks);
                if (sortedDeadline != null)
                    sortDeadline(sortedDeadline, tasks);
                for (Task task : tasks) {
                    TaskItem taskItem = new TaskItem(team, task);
                    taskItem.setOnItemClickListener(new TaskItem.OnItemClickListener() {
                        @Override
                        public void onClick(Team team, Task task) {
                            if (!team.isSuspended(UserController.getLoggedUser())) {
                                if (!team.isPending()) {
                                    SharedPreferences.add(SELECTED_TEAM, team);
                                    SharedPreferences.add(TASK, task);
                                    tabPaneHandler(null, CREATE_TASK, 0);
                                } else {
                                    new AlertHandler(Alert.AlertType.ERROR,
                                            "Team is waiting for admin confirmation!").ShowAlert();
                                }
                            } else {
                                new AlertHandler(Alert.AlertType.ERROR,
                                        "You have been suspended from this team!").ShowAlert();
                            }
                        }
                    });
                    HBox taskItemBox = taskItem.draw();
                    TTaskItemHolder.getChildren().add(taskItemBox);
                }
            }
            onTTaskSearchListener(teamTasks);
        }
    }

    private void sortPriority(String sortedPriority, ArrayList<Task> tasks) {

        if (sortedPriority.equalsIgnoreCase("low->high") ||
                sortedPriority.equalsIgnoreCase("high->low")) {


            tasks.sort(new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    boolean asc = sortedPriority.equalsIgnoreCase("low->high");
                    System.out.println(asc);
                    if (asc)
                        return Integer.compare(o1.getPriorityNumeric(), o2.getPriorityNumeric());
                    return Integer.compare(o2.getPriorityNumeric(), o1.getPriorityNumeric());
                }
            });
        } else {
            ArrayList<Task> toBeRemoved = new ArrayList<>();
            for (Task task : tasks)
                if (!task.getPriority().equalsIgnoreCase(sortedPriority))
                    toBeRemoved.add(task);

            tasks.removeAll(toBeRemoved);
        }
    }

    private void sortDeadline(String sortedDeadline, ArrayList<Task> tasks) {
        if (sortedDeadline.equalsIgnoreCase("oldest->newest") ||
                sortedDeadline.equalsIgnoreCase("newest->oldest")) {

            tasks.sort(new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    boolean asc = sortedDeadline.equalsIgnoreCase("oldest->newest");
                    if (asc)
                        return o1.getTimeOfDeadline().compareTo(o2.getTimeOfDeadline());
                    return o2.getTimeOfDeadline().compareTo(o1.getTimeOfDeadline());
                }
            });
        }
    }

    private void onTTaskSearchListener(HashMap<Team, ArrayList<Task>> teamTasks) {
        if (!TTaskItemHolder.getChildren().isEmpty()) {
            TSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                TTaskItemHolder.getChildren().clear();
                for (Team team : teamTasks.keySet()) {
                    for (Task task : team.getTasks()) {
                        if (task.getTitle().contains(newValue)) {
                            TaskItem taskItem = new TaskItem(team, task);
                            taskItem.setOnItemClickListener(new TaskItem.OnItemClickListener() {
                                @Override
                                public void onClick(Team team, Task task) {
                                    if (!team.isSuspended(UserController.getLoggedUser())) {
                                        if (!team.isPending()) {
                                            SharedPreferences.add(SELECTED_TEAM, team);
                                            SharedPreferences.add(TASK, task);
                                            tabPaneHandler(null, CREATE_TASK, 0);
                                        } else {
                                            new AlertHandler(Alert.AlertType.ERROR,
                                                    "Team is waiting for admin confirmation!").ShowAlert();
                                        }
                                    } else {
                                        new AlertHandler(Alert.AlertType.ERROR,
                                                "You have been suspended from this team!").ShowAlert();
                                    }
                                }
                            });
                            HBox taskItemBox = taskItem.draw();
                            TTaskItemHolder.getChildren().add(taskItemBox);
                        }
                    }
                }
            });
        }
    }

    private void setUpTasksPage() {
        // teams
        ObservableList<String> teams = FXCollections.observableArrayList();
        teams.addAll("All");
        MResponse MResponse =
                ProfileMenuController.getInstance().showTeams();
        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<Team>() {
            }.getType();
            ArrayList<Team> allTeams = new Gson().fromJson((String) MResponse.getObject(), typeMyType);

            for (Team team : allTeams) {
                teams.add(team.getName());
            }
        }
        TTeamCombo.setItems(teams);
        TTeamCombo.getSelectionModel().select(0);

        // priorities
        ObservableList<String> priorities = FXCollections.observableArrayList();
        priorities.addAll("All", "Low->High", "High->Low", "Lowest", "Low", "High", "Highest");
        TPriorityCombo.setItems(priorities);
        TPriorityCombo.getSelectionModel().select(0);

        // deadlines
        ObservableList<String> deadlines = FXCollections.observableArrayList();
        deadlines.addAll("All", "Oldest->Newest", "Newest->Oldest");
        TDeadlineCombo.setItems(deadlines);
        TDeadlineCombo.getSelectionModel().select(0);

        setUpComboListeners();
    }

    private void setUpComboListeners() {
        TTeamCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equalsIgnoreCase("all"))
                    SharedPreferences.add(SORTED_TEAM, null);
                else
                    SharedPreferences.add(SORTED_TEAM, newValue);
                setTTasks();
            }
        });

        TPriorityCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equalsIgnoreCase("all"))
                    SharedPreferences.add(SORTED_PRIORITY, null);
                else
                    SharedPreferences.add(SORTED_PRIORITY, newValue);
                setTTasks();
            }
        });

        TDeadlineCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equalsIgnoreCase("all"))
                    SharedPreferences.add(SORTED_DEADLINE, null);
                else
                    SharedPreferences.add(SORTED_DEADLINE, newValue);
                setTTasks();
            }
        });
    }

    @FXML
    private void onTCTask() {
        SharedPreferences.remove(TASK);
        tabPaneHandler(null, CREATE_TASK, 0);
    }

    // tasks > create task

    private void setTaskMembers() {
        Task task = (Task) SharedPreferences.get(TASK);

        TTMemberItemHolder.setDisable(task == null);

        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        MResponse MResponse =
                TeamMenuController.getInstance().getMembers(team);

        TTMemberItemHolder.getChildren().clear();
        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<ArrayList<User>>() {
            }.getType();
            ArrayList<User> members = new Gson().fromJson((String) MResponse.getObject(), typeMyType);
            for (User user : members) {
                TaskMemberItem taskMemberItem = new TaskMemberItem(user);
                taskMemberItem.setOnItemClickListener(new TaskMemberItem.OnItemClickListener() {
                    @Override
                    public void onAdd(User member) {
                        addMemberToTask(member);
                        save();
                        setTaskMembers();
                    }

                    @Override
                    public void onRemove(User member) {
                        removeMemberFromTask(member);
                        save();
                        setTaskMembers();
                    }
                });
                HBox taskMember = taskMemberItem.draw();
                TTMemberItemHolder.getChildren().add(taskMember);
            }
            onTTMemberSearchListener(members);
        }
    }

    private void onTTMemberSearchListener(ArrayList<User> members) {
        if (!TTMemberItemHolder.getChildren().isEmpty()) {
            TTMSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                TTMemberItemHolder.getChildren().clear();
                for (User member : members) {
                    if (member.getUsername().contains(newValue)) {
                        TaskMemberItem taskMemberItem = new TaskMemberItem(member);
                        taskMemberItem.setOnItemClickListener(new TaskMemberItem.OnItemClickListener() {
                            @Override
                            public void onAdd(User member) {
                                addMemberToTask(member);
                                save();
                                setTaskMembers();
                            }

                            @Override
                            public void onRemove(User member) {
                                removeMemberFromTask(member);
                                save();
                                setTaskMembers();
                            }
                        });
                        HBox taskMember = taskMemberItem.draw();
                        TTMemberItemHolder.getChildren().add(taskMember);
                    }
                }
            });
        }
    }

    private void addMemberToTask(User member) {
        Task task = (Task) SharedPreferences.get(TASK);

        MResponse MResponse
                = TasksMenuController.getInstance()
                .addToAssignedUsers(String.valueOf(task.getId()), member.getUsername());
        showResponse(MResponse);
    }

    private void removeMemberFromTask(User member) {
        Task task = (Task) SharedPreferences.get(TASK);

        MResponse MResponse
                = TasksMenuController.getInstance()
                .removeAssignedUsers(String.valueOf(task.getId()), member.getUsername());
        showResponse(MResponse);
    }

    @FXML
    private void onCreateTask() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        String name = getValue(TTNameInput);
        String priority = getComboValue(TTPriorityCombo);
        LocalDate startDate = getDate(TTStartDate);
        String startTime = getValue(TTStartTime);
        LocalDate deadlineDate = getDate(TTDeadlineDate);
        String deadlineTime = getValue(TTDeadlineTime);
        String description = getValue(TTDescription);


        String startDateTime = "";
        String deadDateTime = "";

        try {
            startDateTime = String.format("%s|%s", startDate.toString(), startTime);
            deadDateTime = String.format("%s|%s", deadlineDate.toString(), deadlineTime);
        } catch (Exception e) {

        }
        MResponse MResponse =
                TeamMenuController.getInstance().createTask(team, name, priority, startDateTime, deadDateTime, description);
        showResponse(MResponse);
        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<Task>() {
            }.getType();
            Task task = new Gson().fromJson((String) MResponse.getObject(), typeMyType);
            SharedPreferences.add(TASK, task);
            setTaskMembers();
        }
        save();
    }

    @FXML
    private void onCTSaveChanges() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        Task task = (Task) SharedPreferences.get(TASK);

        if (task == null)
            return;

        String name = getValue(TTNameInput);
        String priority = getComboValue(TTPriorityCombo);
        LocalDate startDate = getDate(TTStartDate);
        String startTime = getValue(TTStartTime);
        LocalDate deadlineDate = getDate(TTDeadlineDate);
        String deadlineTime = getValue(TTDeadlineTime);
        String description = getValue(TTDescription);


        String startDateTime = "";
        String deadDateTime = "";

        try {
            startDateTime = String.format("%s|%s", startDate.toString(), startTime);
            deadDateTime = String.format("%s|%s", deadlineDate.toString(), deadlineTime);
        } catch (Exception e) {

        }

        MResponse MResponse =
                TasksMenuController.getInstance().editTask(team, String.valueOf(task.getId()),
                        name, priority, startDateTime, deadDateTime, description);
        showResponse(MResponse);
        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<Task>() {
            }.getType();
            task = new Gson().fromJson((String) MResponse.getObject(), typeMyType);
            SharedPreferences.add(TASK, task);
            setTaskMembers();
        }
        save();
    }

    @FXML
    private void onCreateTaskClear() {
        SharedPreferences.remove(TASK);
        tabPaneHandler(null, CREATE_TASK, 0);
    }

    private void setUpTaskCreationPage() {
        ObservableList<String> priorities = FXCollections.observableArrayList();
        priorities.addAll("Lowest", "Low", "High", "Highest");
        TTPriorityCombo.setItems(priorities);
        TTPriorityCombo.getSelectionModel().select(0);

        TTNameInput.clear();
        TTStartDate.setValue(LocalDate.now());
        TTStartTime.clear();
        TTDeadlineDate.setValue(LocalDate.now());
        TTDeadlineTime.clear();
        TTDescription.clear();

        Task task = (Task) SharedPreferences.get(TASK);
        if (task != null) {
            TTNameInput.setText(task.getTitle());
            TTPriorityCombo.getSelectionModel().select(task.getPriorityNumeric() - 1);
            TTStartDate.setValue(task.getStartTime().toLocalDate());
            TTStartTime.setText(String.format("%d:%d", task.getStartTime().getHour(),
                    task.getStartTime().getMinute()));
            TTDeadlineDate.setValue(task.getTimeOfDeadline().toLocalDate());
            TTDeadlineTime.setText(String.format("%d:%d", task.getTimeOfDeadline().getHour(),
                    task.getTimeOfDeadline().getMinute()));
            TTDescription.setText(task.getDescription());

            if (!task.isDone()) {
                TTSaveChanges.setDisable(false);
                TTCreateTask.setDisable(true);
            } else {
                TTSaveChanges.setDisable(true);
                TTCreateTask.setDisable(true);
            }
        } else {
            TTSaveChanges.setDisable(true);
            TTCreateTask.setDisable(false);
        }


    }

    //  notifications

    private void setNotifications() {
        User user = UserController.getLoggedUser();

        ArrayList<Notification> notifications =
                user.getNotifications();
        NNotificationItemHolder.getChildren().clear();
        for (Notification notification : notifications) {
            NotificationItem notificationItem = new NotificationItem(notification);
            notificationItem.setOnItemClickListener(new NotificationItem.OnItemClickListener() {
                @Override
                public void onClick(Notification notification) {

                }
            });
            VBox itemBox = notificationItem.draw();
            NNotificationItemHolder.getChildren().add(itemBox);
        }
    }

    @FXML
    private void onNewNotification() {
        tabPaneHandler(null, NEW_NOTIFICATION, 0);
    }

    // new notification

    private void setUpNewNotification() {
        ObservableList<String> teams = FXCollections.observableArrayList();
        teams.addAll("All");
        MResponse MResponse =
                ProfileMenuController.getInstance().showTeams();
        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<ArrayList<Team>>() {
            }.getType();
            ArrayList<Team> allTeams = new Gson().fromJson((String) MResponse.getObject(), typeMyType);

            for (Team team : allTeams) {
                teams.add(team.getName());
            }
        }
        NNTeamCombo.setItems(teams);
        NNTeamCombo.getSelectionModel().select(0);
    }

    @FXML
    private void onSendNotification() {
        String team = getComboValue(NNTeamCombo);
        String body = getValue(NNBody);

        if (body.isEmpty()) {
            new AlertHandler(Alert.AlertType.ERROR, "body cannot be empty!").ShowAlert();
            return;
        }

        if (team.equalsIgnoreCase("all")) {
            MResponse MResponse =
                    ProfileMenuController.getInstance().showTeams();
            if (MResponse.isSuccess()) {
                Type typeMyType = new TypeToken<ArrayList<Team>>() {
                }.getType();
                ArrayList<Team> allTeams = new Gson().fromJson((String) MResponse.getObject(), typeMyType);

                for (Team t : allTeams) {
                    t.sendNotification(new Notification(UserController.getLoggedUser(), t, body));
                }
            }
            new AlertHandler(Alert.AlertType.INFORMATION, "Notification sent successfully!").ShowAlert();
        } else {
            MResponse MResponse =
                    NotificationController.getInstance().sendNotificationToTeam(body, team);
            showResponse(MResponse);
            save();
        }
        clearFields(NNBody);
        save();
    }

    private void setUpNewUserNotification() {
        User user = (User) SharedPreferences.get(SELECTED_USER);

        if (user == null)
            return;

        NNUser.setText(user.getUsername());
    }

    @FXML
    private void onUSendNotification() {
        User user = (User) SharedPreferences.get(SELECTED_USER);

        if (user == null)
            return;

        String body = getValue(NNUBody);

        if (body.isEmpty()) {
            new AlertHandler(Alert.AlertType.ERROR, "body cannot be empty!").ShowAlert();
            return;
        }

        MResponse MResponse =
                NotificationController.getInstance().sendNotificationToUser(body, user.getUsername());
        showResponse(MResponse);
        clearFields(NNUBody);
        save();
    }


    // requests

    private void requestsSetTeams() {
        ArrayList<Team> teams = null;

        MResponse MResponse =
                ProfileMenuController.getInstance().showTeams();

        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<ArrayList<Team>>() {
            }.getType();
            teams = new Gson().fromJson((String) MResponse.getObject(), typeMyType);
        }

        RTeamsItemHolder.getChildren().clear();
        if (teams != null) {
            for (Team team : teams) {
                RequestTeamItem teamItem = new RequestTeamItem(team);
                teamItem.setOnItemClickListener(new TeamItem.OnItemClickListener() {
                    @Override
                    public void onClick(Team team) {

                    }
                });
                HBox teamBox = teamItem.draw();
                RTeamsItemHolder.getChildren().add(teamBox);
            }
            onRequestTeamsSearchListener(teams);
        }
    }

    private void onRequestTeamsSearchListener(ArrayList<Team> teams) {
        if (!RTeamsItemHolder.getChildren().isEmpty()) {
            RSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                RTeamsItemHolder.getChildren().clear();
                for (Team team : teams) {
                    if (team.getName().contains(newValue)) {
                        RequestTeamItem teamItem = new RequestTeamItem(team);
                        teamItem.setOnItemClickListener(new TeamItem.OnItemClickListener() {
                            @Override
                            public void onClick(Team team) {

                            }
                        });
                        HBox teamBox = teamItem.draw();
                        RTeamsItemHolder.getChildren().add(teamBox);
                    }
                }
            });
        }
    }

    @FXML
    private void onCreateTeamRequest() {
        tabPaneHandler(null, CREATE_REQUEST, 0);
    }

    // create team request

    @FXML
    private void onCTSubmitRequest() {
        String teamName = getValue(CTTeamName);

        MResponse MResponse =
                TeamMenuController.getInstance().createTeam(teamName);
        showResponse(MResponse);
        save();
    }

    // Calendar

    private void setUpCalendar() {

        MResponse MResponse =
                CalendarMenuController.getInstance().getCalendar("deadlines");

        CTasksItemHolder.getChildren().clear();
        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<ArrayList<Task>>() {
            }.getType();
            ArrayList<Task> tasks = new Gson().fromJson((String) MResponse.getObject(), typeMyType);

            for (Task task : tasks) {
                CalendarTaskItem calendarTaskItem = new CalendarTaskItem(task);
                calendarTaskItem.setOnItemClickListener(new CalendarTaskItem.OnItemClickListener() {
                    @Override
                    public void onClick(Task task) {

                    }
                });
                HBox taskItemBox = calendarTaskItem.draw();
                CTasksItemHolder.getChildren().add(taskItemBox);
            }
        }
    }

    // admin

    // users

    private void setAUsers() {
        MResponse MResponse =
                AdminController.getInstance().getAllUsers();

        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<ArrayList<User>>() {
            }.getType();

            ArrayList<User> users = new Gson().fromJson((String) MResponse.getObject(), typeMyType);

            String sortedUsers = (String) SharedPreferences.get(SORTED_A_USERS);
            String sortedSUsers = (String) SharedPreferences.get(SORTED_S_USERS);

            if (sortedUsers != null) {
                users.sort(new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        boolean asc = sortedUsers.equalsIgnoreCase("a-z");

                        if (asc)
                            return o1.getUsername().compareTo(o2.getUsername());
                        return o2.getUsername().compareTo(o1.getUsername());
                    }
                });
            }

            if (sortedSUsers != null) {
                users.sort(new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        boolean asc = sortedSUsers.equalsIgnoreCase("asc");

                        if (asc)
                            return Integer.compare(User.getUserPoints(o1), User.getUserPoints(o2));

                        return Integer.compare(User.getUserPoints(o2), User.getUserPoints(o1));
                    }
                });
            }

            AUItemHolder.getChildren().clear();
            System.out.println(users);
            for (User user : users) {
                AUserItem aUserItem = new AUserItem(user);
                aUserItem.setOnItemClickListener(new AUserItem.OnItemClickListener() {
                    @Override
                    public void onClick(User user) {
                        SharedPreferences.add(SELECTED_PROFILE, user);
                        tabPaneHandler(null, USER_PROFILE, 0);
                    }

                    @Override
                    public void ban(User user) {
                        banUser(user);
                    }

                    @Override
                    public void setMember(User user) {
                        changeRoleToMember(user);
                    }

                    @Override
                    public void setLeader(User user) {
                        changeRoleToLeader(user);
                    }
                });
                HBox userBox = aUserItem.draw();
                AUItemHolder.getChildren().add(userBox);
            }

            onAdminUserSearchListener(users);
        }
    }

    private void onAdminUserSearchListener(ArrayList<User> users) {
        if (!AUItemHolder.getChildren().isEmpty()) {
            AUSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                AUItemHolder.getChildren().clear();
                for (User user : users) {
                    if (user.getUsername().contains(newValue)) {
                        AUserItem aUserItem = new AUserItem(user);
                        aUserItem.setOnItemClickListener(new AUserItem.OnItemClickListener() {
                            @Override
                            public void onClick(User user) {
                                SharedPreferences.add(SELECTED_PROFILE, user);
                                tabPaneHandler(null, USER_PROFILE, 0);
                            }

                            @Override
                            public void ban(User user) {
                                banUser(user);
                            }

                            @Override
                            public void setMember(User user) {
                                changeRoleToMember(user);
                            }

                            @Override
                            public void setLeader(User user) {
                                changeRoleToLeader(user);
                            }
                        });
                        HBox userBox = aUserItem.draw();
                        AUItemHolder.getChildren().add(userBox);
                    }
                }
            });
        }
    }

    private void setUpAUCombos() {
        ObservableList<String> name = FXCollections.observableArrayList();
        name.addAll("A-Z", "Z-A");
        AUNameCombo.setItems(name);
        AUNameCombo.getSelectionModel().select(0);

        ObservableList<String> score = FXCollections.observableArrayList();
        score.addAll("ASC", "DSC");
        AUScoreCombo.setItems(score);
        AUScoreCombo.getSelectionModel().select(0);

        onAUChangeListener();
    }

    private void onAUChangeListener() {
        AUNameCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                SharedPreferences.add(SORTED_A_USERS, newValue);
                setAUsers();
            }
        });

        AUScoreCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                SharedPreferences.add(SORTED_S_USERS, newValue);
                setAUsers();
            }
        });
    }

    private void banUser(User user) {
        MResponse MResponse =
                AdminController.getInstance().banUser(user.getUsername());
        showResponse(MResponse);
        setAUsers();
        save();
    }

    private void changeRoleToMember(User user) {
        MResponse MResponse =
                AdminController.getInstance().changeRole(user.getUsername(), "teamMember");
        showResponse(MResponse);
        setAUsers();
        save();
    }

    private void changeRoleToLeader(User user) {
        MResponse MResponse =
                AdminController.getInstance().changeRole(user.getUsername(), "teamLeader");
        showResponse(MResponse);
        setAUsers();
        save();
    }

    // teams

    private void setATeams() {
        MResponse MResponse =
                AdminController.getInstance().getPendingTeams();

        ATItemHolder.getChildren().clear();
        if (MResponse.isSuccess()) {
            Type typeMyType = new TypeToken<ArrayList<Team>>() {
            }.getType();

            ArrayList<Team> teams = new Gson().fromJson(MResponse.getObject().toString(), typeMyType);

            for (Team team : teams) {
                ATeamItem aTeamItem = new ATeamItem(team);
                aTeamItem.setOnItemClickListener(new ATeamItem.OnItemClickListener() {
                    @Override
                    public void accept(Team team) {
                        acceptTeam(team);
                    }

                    @Override
                    public void reject(Team team) {
                        rejectTeam(team);
                    }
                });
                HBox teamBox = aTeamItem.draw();
                ATItemHolder.getChildren().add(teamBox);
            }
            onATeamsSearchListener(teams);
        }
    }

    private void onATeamsSearchListener(ArrayList<Team> teams) {
        if (!ATItemHolder.getChildren().isEmpty()) {
            ATSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                ATItemHolder.getChildren().clear();
                for (Team team : teams) {
                    if (team.getName().contains(newValue)) {
                        ATeamItem aTeamItem = new ATeamItem(team);
                        aTeamItem.setOnItemClickListener(new ATeamItem.OnItemClickListener() {
                            @Override
                            public void accept(Team team) {
                                acceptTeam(team);
                            }

                            @Override
                            public void reject(Team team) {
                                rejectTeam(team);
                            }
                        });
                        HBox teamBox = aTeamItem.draw();
                        ATItemHolder.getChildren().add(teamBox);
                    }
                }
            });
        }
    }

    private void acceptTeam(Team team) {
        MResponse MResponse =
                AdminController.getInstance().acceptPendingTeams(new String[]{team.getName()});
        showResponse(MResponse);
        save();
        setATeams();
    }

    private void rejectTeam(Team team) {
        MResponse MResponse =
                AdminController.getInstance().rejectPendingTeams(new String[]{team.getName()});
        showResponse(MResponse);
        save();
        setATeams();
    }

    // statistics

    private void setAStatistics() {
        int totalUsers = User.getAllUsers().size();
        int totalTeams = Team.getTeams().size();
        int doneTasks = 0;
        int failedTasks = 0;

        for (Task task : Task.getAllTask())
            if (task.isDone())
                doneTasks++;
            else if (task.isFailed())
                failedTasks++;

        ASUTotal.setText(String.valueOf(totalUsers));
        ASTTotal.setText(String.valueOf(totalTeams));
        ASTDone.setText(String.valueOf(doneTasks));
        ASTFailed.setText(String.valueOf(failedTasks));

        setUpASUItemHolder();
    }

    private void setUpASUItemHolder() {
        HashMap<Integer, User> userPoints = new LinkedHashMap<>();
        for (User user : User.getAllUsers()) {
            userPoints.put(User.getUserPoints(user), user);
        }

        Iterator<Integer> iterator = userPoints.keySet().iterator();
        int index = 0;

        ASTUItemHolder.getChildren().clear();

        while (iterator.hasNext() && index < 3) {
            int key = iterator.next();
            TopUserItem topUserItem = new TopUserItem(userPoints.get(key));
            topUserItem.setOnItemClickListener(new TopUserItem.OnItemClickListener() {
                @Override
                public void onClick(User user) {
                    SharedPreferences.add(SELECTED_PROFILE, user);
                    tabPaneHandler(null, USER_PROFILE, 0);
                }
            });
            HBox topBox = topUserItem.draw();
            ASTUItemHolder.getChildren().add(topBox);
            index++;
        }
    }

    // notification

    @FXML
    private void onASendNotification() {
        String body = getValue(NNABody);

        if (body.isEmpty()) {
            new AlertHandler(Alert.AlertType.ERROR, "body cannot be empty!").ShowAlert();
            return;
        }

        MResponse MResponse =
                NotificationController.getInstance().sendNotificationToAll(body);
        showResponse(MResponse);
        clearFields(NNABody);
        save();
    }

    private void setUpUserProfile() {
        User user = (User) SharedPreferences.get(SELECTED_PROFILE);

        if (user == null)
            return;

        UPUsername.setText(user.getUsername());
        UPFirstName.setText(user.getFirstname());
        UPLastName.setText(user.getLastName());
        if (user.getBirthday() != null)
            UPBirthdate.setValue(user.getBirthday());
        UPEmail.setText(user.getEmail());
        UPRole.setText(user.getType().name());
    }

    @FXML
    private void onClose() {
        SharedPreferences.clear();
        System.exit(0);
    }

    @FXML
    private void onSignOut() {
        SharedPreferences.clear();
        UserController.logout();
        logout();
    }
}
