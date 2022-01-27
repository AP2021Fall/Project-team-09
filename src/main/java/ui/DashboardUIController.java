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

    // teams > add member

    @FXML
    private ComboBox<String> AMTeamCombo;

    @FXML
    private VBox AMMembersItemHolder;

    // teams > roadmap

    @FXML
    private VBox TRTasksHolder;

    // teams > chatroom

    @FXML
    private VBox TCChatItemHolder;

    @FXML
    private TextField TCMessageInput;

    // boards > boards

    @FXML
    private VBox TBBoardItemHolder;

    // boards > board > create board

    @FXML
    private TextField CBNameInput;


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

    // notifications

    @FXML
    private VBox NNotificationItemHolder;


    // requests

    @FXML
    private VBox RTeamsItemHolder;

    @FXML
    private TextField RSearchInput;

    // create request

    @FXML
    private TextField CTTeamName;


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

        if (tab == TEAM && tab != TAB) {
            teamsSetTeams();
            teamTabPane.getSelectionModel().select(0);
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
            ArrayList<Team> filteredTeams = new ArrayList<>();
            TTSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
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
                TTeamsItemHolder.getChildren().add(teamBox);
            }
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
            TeamMemberItem teamMemberItem = new TeamMemberItem(user);
            teamMemberItem.setOnItemClickListener(new TeamMemberItem.OnItemClickListener() {
                @Override
                public void onClick(User member) {

                }
            });
            HBox teamMemberBox = teamMemberItem.draw();
            TMemberItemHolder.getChildren().add(teamMemberBox);
        }
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

                    }
                });
                HBox assignMemberBox = assignMemberItem.draw();
                AMMembersItemHolder.getChildren().add(assignMemberBox);
            }
        }
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

                    }
                });
                HBox roadmapBox = roadmapItem.draw();
                TRTasksHolder.getChildren().add(roadmapBox);
            }
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

                    }
                });
                HBox boardBox = boardItem.draw();
                TBBoardItemHolder.getChildren().add(boardBox);
            }
        }
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

    // tasks
    private void setTTasks() {

        Response response =
                TasksMenuController.getInstance().getAllTasks();
//
//        showResponse(response);

        TTaskItemHolder.getChildren().clear();
        if (response.isSuccess()) {
            HashMap<Team, ArrayList<Task>> teamTasks = (HashMap<Team, ArrayList<Task>>) response.getObject();
            for (Team team : teamTasks.keySet()) {
                for (Task task : team.getTasks()) {
                    TaskItem taskItem = new TaskItem(team, task);
                    taskItem.setOnItemClickListener(new TaskItem.OnItemClickListener() {
                        @Override
                        public void onClick(Task task) {

                        }
                    });
                    HBox taskItemBox = taskItem.draw();
                    TTaskItemHolder.getChildren().add(taskItemBox);
                }
            }
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
    }

    @FXML
    private void onTCTask() {
        tabPaneHandler(null, CREATE_TASK, 0);
    }

    // tasks > create task

    private void setTaskMembers() {
        Team team = (Team) SharedPreferences.get(SELECTED_TEAM);

        if (team == null)
            return;

        Response response =
                TeamMenuController.getInstance().getAllUsers(team);

        TTMemberItemHolder.getChildren().clear();
        if (response.isSuccess()) {
            ArrayList<User> members = (ArrayList<User>) response.getObject();
            for (User user : members) {
                TaskMemberItem taskMemberItem = new TaskMemberItem(user);
                HBox taskMember = taskMemberItem.draw();
                TTMemberItemHolder.getChildren().add(taskMember);
            }
        }
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
        save();
    }

    private void setUpTaskCreationPage() {
        ObservableList<String> priorities = FXCollections.observableArrayList();
        priorities.addAll("Lowest", "Low", "High", "Highest");
        TTPriorityCombo.setItems(priorities);
        TTPriorityCombo.getSelectionModel().select(0);
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
}
