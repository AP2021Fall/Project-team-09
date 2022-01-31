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
import utilities.AlertHandler;
import utilities.SharedPreferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DashboardUIController implements Initializable, GUI {

    private final String SELECTED_TEAM =
            "team";
    private final String BOARD = "board";
    private final String TASK = "task";
    private final String ONLY_MINE = "only_mine";
    private static final String SORTED_TEAM = "sorted_team";
    private static final String SORTED_PRIORITY = "sorted_priority";
    private static final String SORTED_DEADLINE = "sorted_deadline";
    private static final String SORTED_A_USERS = "sorted_a_users";

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

    private final int USERS = 6;
    private final int TEAMS = 7;
    private final int STATISTICS = 8;

    private final int CREATE_REQUEST = 9;
    private final int ADD_MEMBER = 10;
    private final int CREATE_BOARD = 11;
    private final int CREATE_TASK = 12;
    private final int BOARD_PAGE = 13;
    private final int CREATE_CATEGORY = 14;
    private final int NEW_NOTIFICATION = 15;

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

    // teams > team page

    @FXML
    private TabPane TPTabPane;

    // teams > members

    @FXML
    private VBox TMemberItemHolder;

    @FXML
    private TextField TMSearchInput;

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

    // new notification

    @FXML
    private ComboBox<String> NNTeamCombo;

    @FXML
    private TextArea NNBody;

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

                if (!user.isTeamLeader()) {
                    RequestsMenu.setVisible(false);
                    RequestsMenu.setManaged(false);
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
                        SharedPreferences.add(SELECTED_TEAM, team);
                        teamPageTabPaneHandler(MEMBERS);
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
                                SharedPreferences.add(SELECTED_TEAM, team);
                                teamPageTabPaneHandler(MEMBERS);
                            }
                        });
                        HBox teamBox = teamItem.draw();
                        PTeamsItemHolder.getChildren().add(teamBox);
                    }
                }
            });
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


    // teams

    private void teamsSetTeams() {
        ArrayList<Team> teams = null;

        Response response =
                ProfileMenuController.getInstance().showTeams();

//        showResponse(response);
        if (response.isSuccess()) {
            teams = (ArrayList<Team>) response.getObject();
        }

        TTeamsItemHolder.getChildren().clear();
        if (teams != null) {
            for (Team team : teams) {
                TeamItem teamItem = new TeamItem(team);
                teamItem.setOnItemClickListener(new TeamItem.OnItemClickListener() {
                    @Override
                    public void onClick(Team team) {
                        SharedPreferences.add(SELECTED_TEAM, team);
                        teamPageTabPaneHandler(MEMBERS);
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
                                SharedPreferences.add(SELECTED_TEAM, team);
                                teamPageTabPaneHandler(MEMBERS);
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
                public void onRemove(User member) {
                    removeMember(team, member);
                    save();
                    setMembers();
                }

                @Override
                public void onMessage(User member) {

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
                            public void onRemove(User member) {
                                removeMember(team, member);
                            }

                            @Override
                            public void onMessage(User member) {

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
        Response response =
                TeamMenuController.getInstance().deleteMember(team, member.getUsername());
        showResponse(response);
        save();
        setMembers();
    }

    private void promoteMember(Team team, User member) {
        Response response =
                TeamMenuController.getInstance().promoteUser(team, member.getUsername(), "teamLeader");
        showResponse(response);
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

        Response response =
                TeamMenuController.getInstance().getAllUsers(team);
        AMMembersItemHolder.getChildren().clear();
        if (response.isSuccess()) {
            ArrayList<User> users = (ArrayList<User>) response.getObject();

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

        Response response
                = TeamMenuController.getInstance().addMemberToTeam(team, member.getUsername());
        showResponse(response);
        save();
        setAMMembers();
    }

    // team > roadmap

    private void setRoadMap() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        Response response =
                TeamMenuController.getInstance().getRoadmap(team);

        TRTasksHolder.getChildren().clear();
        if (response.isSuccess()) {
            ArrayList<Task> tasks = (ArrayList<Task>) response.getObject();

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

    // team > chatroom

    private void setChatroom() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        Response response =
                TeamMenuController.getInstance().getMessages(team);
        TCChatItemHolder.getChildren().clear();

        if (response.isSuccess()) {
            ArrayList<Message> messages = (ArrayList<Message>) response.getObject();

            for (Message message : messages) {
                ChatItem chatItem = new ChatItem(message);
                chatItem.setOnItemClickListener(new ChatItem.OnItemClickListener() {
                    @Override
                    public void onClick(Message message) {

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

        Response response =
                TeamMenuController.getInstance().sendMessage(team, message);
        if (response.isSuccess()) {
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

        Response response =
                BoardMenuController.getInstance().getBoards(team);

        TBBoardItemHolder.getChildren().clear();

        if (response.isSuccess()) {
            ArrayList<Board> boards = (ArrayList<Board>) response.getObject();

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

        Response response =
                BoardMenuController.getInstance().removeBoard(team, board.getName());
        showResponse(response);
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

        Response response =
                BoardMenuController.getInstance().createNewBoard(team, boardName);
        showResponse(response);
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
                    Response response =
                            BoardMenuController.getInstance()
                                    .addTaskToBoard(team, String.valueOf(task.getId()), board.getName());
                    showResponse(response);
                }
                if (task.getBoard() != null) {
                    Response res = BoardMenuController.getInstance()
                            .forceMoveTaskToCategory(team, "done", task.getTitle(), board.getName());
                    showResponse(res);
                }
                save();
                setBoard();
            }

            @Override
            public void onNext(Task task) {
                if (task.getBoard() == null) {
                    Response response =
                            BoardMenuController.getInstance()
                                    .addTaskToBoard(team, String.valueOf(task.getId()), board.getName());
                    showResponse(response);
                }
                if (task.getBoard() != null) {
                    Response res = BoardMenuController.getInstance()
                            .moveTaskToNextCategory(team, task.getTitle(), board.getName());
                    showResponse(res);
                }
                save();
                setBoard();
            }

            @Override
            public void addToBoard(Task task) {
                if (!board.isDone()) {
                    Response res =
                            BoardMenuController.getInstance().setBoardToDone(team, board.getName());
                    showResponse(res);
                } else if (task.getBoard() == null) {
                    Response response =
                            BoardMenuController.getInstance()
                                    .addTaskToBoard(team, String.valueOf(task.getId()), board.getName());
                    showResponse(response);
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
            Response response = null;
            if (category.equalsIgnoreCase("done") || category.equalsIgnoreCase("failed"))
                response = BoardMenuController.getInstance().getSpecificCategoryTasks(team, category, board.getName());
            else
                response = BoardMenuController.getInstance().showCategoryTasks(team, category, board.getName());

            ArrayList<Task> tasks = new ArrayList<>();
            if (response.isSuccess())
                tasks = (ArrayList<Task>) response.getObject();
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
                        Response response =
                                BoardMenuController.getInstance()
                                        .addTaskToBoard(team, String.valueOf(task.getId()), board.getName());
                        showResponse(response);
                    }
                    if (task.getBoard() != null) {
                        Response res = BoardMenuController.getInstance()
                                .forceMoveTaskToCategory(team, "done", task.getTitle(), board.getName());
                        showResponse(res);
                    }
                    save();
                    setBoard();
                }

                @Override
                public void onNext(Task task) {
                    if (task.getBoard() == null) {
                        Response response =
                                BoardMenuController.getInstance()
                                        .addTaskToBoard(team, String.valueOf(task.getId()), board.getName());
                        showResponse(response);
                    }
                    if (task.getBoard() != null) {
                        Response res = BoardMenuController.getInstance()
                                .moveTaskToNextCategory(team, task.getTitle(), board.getName());
                        showResponse(res);
                    }
                    save();
                    setBoard();
                }

                @Override
                public void addToBoard(Task task) {
                    if (!board.isDone()) {
                        Response res =
                                BoardMenuController.getInstance().setBoardToDone(team, board.getName());
                        showResponse(res);
                    } else if (task.getBoard() == null) {
                        Response response =
                                BoardMenuController.getInstance()
                                        .addTaskToBoard(team, String.valueOf(task.getId()), board.getName());
                        showResponse(response);
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

        Response response =
                BoardMenuController.getInstance().createNewCategory(team, categoryName, board.getName());
        showResponse(response);
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

        Response response =
                TasksMenuController.getInstance().getAllTasks();
//
//        showResponse(response);

        TTaskItemHolder.getChildren().clear();

        String sortedTeam = (String) SharedPreferences.get(SORTED_TEAM);
        String sortedPriority = (String) SharedPreferences.get(SORTED_PRIORITY);
        String sortedDeadline = (String) SharedPreferences.get(SORTED_DEADLINE);


        if (response.isSuccess()) {
            HashMap<Team, ArrayList<Task>> teamTasks = (HashMap<Team, ArrayList<Task>>) response.getObject();

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
                            SharedPreferences.add(SELECTED_TEAM, team);
                            SharedPreferences.add(TASK, task);
                            tabPaneHandler(null, CREATE_TASK, 0);
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
                                    SharedPreferences.add(SELECTED_TEAM, team);
                                    SharedPreferences.add(TASK, task);
                                    tabPaneHandler(null, CREATE_TASK, 0);
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
        Response response =
                ProfileMenuController.getInstance().showTeams();
        if (response.isSuccess()) {
            ArrayList<Team> allTeams = (ArrayList<Team>) response.getObject();

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

        Response response =
                TeamMenuController.getInstance().getMembers(team);

        TTMemberItemHolder.getChildren().clear();
        if (response.isSuccess()) {
            ArrayList<User> members = (ArrayList<User>) response.getObject();
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

        Response response
                = TasksMenuController.getInstance()
                .addToAssignedUsers(String.valueOf(task.getId()), member.getUsername());
        showResponse(response);
    }

    private void removeMemberFromTask(User member) {
        Task task = (Task) SharedPreferences.get(TASK);

        Response response
                = TasksMenuController.getInstance()
                .removeAssignedUsers(String.valueOf(task.getId()), member.getUsername());
        showResponse(response);
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
        Response response =
                TeamMenuController.getInstance().createTask(team, name, priority, startDateTime, deadDateTime, description);
        showResponse(response);
        if (response.isSuccess()) {
            Task task = (Task) response.getObject();
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

        Response response =
                TasksMenuController.getInstance().editTask(team, String.valueOf(task.getId()),
                        name, priority, startDateTime, deadDateTime, description);
        showResponse(response);
        if (response.isSuccess()) {
            task = (Task) response.getObject();
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
        Response response =
                ProfileMenuController.getInstance().showTeams();
        if (response.isSuccess()) {
            ArrayList<Team> allTeams = (ArrayList<Team>) response.getObject();

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
            Response response =
                    ProfileMenuController.getInstance().showTeams();
            if (response.isSuccess()) {
                ArrayList<Team> allTeams = (ArrayList<Team>) response.getObject();

                for (Team t : allTeams) {
                    t.sendNotification(new Notification(UserController.getLoggedUser(), t, body));
                }
            }
            new AlertHandler(Alert.AlertType.INFORMATION, "Notification sent successfully!").ShowAlert();
        } else {
            Response response =
                    NotificationController.getInstance().sendNotificationToTeam(body, team);
            showResponse(response);
            save();
        }
        clearFields(NNBody);
        save();
    }


    // requests

    private void requestsSetTeams() {
        ArrayList<Team> teams = null;

        Response response =
                ProfileMenuController.getInstance().showTeams();

        if (response.isSuccess()) {
            teams = (ArrayList<Team>) response.getObject();
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

        Response response =
                TeamMenuController.getInstance().createTeam(teamName);
        showResponse(response);
        save();
    }

    // Calendar

    private void setUpCalendar() {

        Response response =
                CalendarMenuController.getInstance().getCalendar("deadlines");

        CTasksItemHolder.getChildren().clear();
        if (response.isSuccess()) {
            ArrayList<Task> tasks = (ArrayList<Task>) response.getObject();

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
        ArrayList<User> users =
                User.getAllUsers();

        String sortedUsers = (String) SharedPreferences.get(SORTED_A_USERS);

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

        AUItemHolder.getChildren().clear();
        for (User user : users) {
            AUserItem aUserItem = new AUserItem(user);
            aUserItem.setOnItemClickListener(new AUserItem.OnItemClickListener() {
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

    private void onAdminUserSearchListener(ArrayList<User> users) {
        if (!AUItemHolder.getChildren().isEmpty()) {
            AUSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                AUItemHolder.getChildren().clear();
                for (User user : users) {
                    if (user.getUsername().contains(newValue)) {
                        AUserItem aUserItem = new AUserItem(user);
                        aUserItem.setOnItemClickListener(new AUserItem.OnItemClickListener() {
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

        onAUChangeListener();
    }

    private void onAUChangeListener() {
        AUNameCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                SharedPreferences.add(SORTED_A_USERS, newValue);
                setAUsers();
            }
        });
    }

    private void banUser(User user) {
        Response response =
                AdminController.getInstance().banUser(user.getUsername());
        showResponse(response);
        setAUsers();
        save();
    }

    private void changeRoleToMember(User user) {
        Response response =
                AdminController.getInstance().changeRole(user.getUsername(), "teamMember");
        showResponse(response);
        setAUsers();
        save();
    }

    private void changeRoleToLeader(User user) {
        Response response =
                AdminController.getInstance().changeRole(user.getUsername(), "teamLeader");
        showResponse(response);
        setAUsers();
        save();
    }

    // teams

    private void setATeams() {
        Response response =
                AdminController.getInstance().getPendingTeams();

        ATItemHolder.getChildren().clear();
        if (response.isSuccess()) {
            ArrayList<Team> teams = (ArrayList<Team>) response.getObject();

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
        Response response =
                AdminController.getInstance().acceptPendingTeams(new String[]{team.getName()});
        showResponse(response);
        save();
        setATeams();
    }

    private void rejectTeam(Team team) {
        Response response =
                AdminController.getInstance().rejectPendingTeams(new String[]{team.getName()});
        showResponse(response);
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
    }
}
