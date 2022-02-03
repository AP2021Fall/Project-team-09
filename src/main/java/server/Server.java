package server;

import com.google.gson.Gson;
import model.MRequest;
import model.Team;
import server.controller.*;
import spark.ResponseTransformer;
import utilities.ConsoleHelper;

import java.time.LocalDate;
import java.util.ArrayList;

import static spark.Spark.*;

public class Server {

    // req

    private static final String JSON = "application/json";

    // args

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String PASSWORD1 = "password1";
    private static final String PASSWORD2 = "password2";
    private static final String EMAIL = "email";
    private static final String ROLE = "role";
    private static final String TEAM = "team";
    private static final String BOARD_NAME = "board_name";
    private static final String CATEGORY_NAME = "category_name";
    private static final String COLUMN = "column";
    private static final String TASK_ID = "task_id";
    private static final String TASK_TITLE = "task_title";
    private static final String CATEGORY = "category";
    private static final String DEADLINE = "deadline";
    private static final String TEAM_MATE = "task_mate";
    private static final String BODY = "body";
    private static final String TEAM_NAME = "team_name";
    private static final String OLD_PASS = "old_pass";
    private static final String NEW_PASS = "new_pass";
    private static final String NEW_USERNAME = "new_username";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String BIRTH_DATE = "birth_date";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String PRIORITY = "priority";
    private static final String START_TIME = "start_time";
    private static final String CALENDAR = "calendar";
    private static final String RATE = "rate";

    // auth

    private static final String LOGIN_PATH = "auth/signIn";
    private static final String SIGNUP_PATH = "auth/signUp";

    // admin

    private static final String GET_PROFILE_PATH = "admin/profile";
    private static final String BAN_USER_PATH = "admin/ban";
    private static final String CHANGE_ROLE_PATH = "admin/change-role";
    private static final String GET_PENDING_TEAMS = "admin/pending-teams";
    private static final String ACCEPT_PENDING_TEAMS = "admin/accept-pending";
    private static final String REJECT_PENDING_TEAMS = "admin/reject-pending";
    private static final String ADMIN_GET_ALL_USERS_PATH = "admin/get-all-users";

    // calendar

    private static final String GET_CALENDAR_PATH = "calendar";

    // board

    private static final String CREATE_BOARD_PATH = "board/create";
    private static final String REMOVE_BOARD_PATH = "board/remove";
    private static final String SELECT_BOARD_PATH = "board/select";
    private static final String DESELECT_BOARD_PATH = "board/deselect";
    private static final String CREATE_CATEGORY_PATH = "board/category";
    private static final String CREATE_CATEGORY_AT_PATH = "board/category-at";
    private static final String SET_BOARD_DONE_PATH = "board/set-done";
    private static final String ADD_TASK_PATH = "board/add-task";
    private static final String ASSIGN_TASK_PATH = "board/assign-task";
    private static final String FORCE_MOVE_PATH = "board/force-move";
    private static final String MOVE_TASK_PATH = "board/move-task";
    private static final String SHOW_CATEGORY_TASKS_PATH = "board/show-category-tasks";
    private static final String GET_SPECIFIED_CATEGORY_PATH = "board/get-specified-category";
    private static final String OPEN_FAILED_TASK_PATH = "board/open-failed-task";
    private static final String SHOW_BOARD_PATH = "board/show-board";
    private static final String GET_BOARDS_PATH = "board/get-boards";

    // notification

    private static final String NOTIFICATION_USER_PATH = "notification/user";
    private static final String NOTIFICATION_TEAM_PATH = "notification/team";
    private static final String NOTIFICATION_ALL_PATH = "notification/all";

    // profile

    private static final String CHANGE_PASS_PATH = "profile/change-pass";
    private static final String CHANGE_USER_PATH = "profile/change-user";
    private static final String SHOW_TEAMS_PATH = "profile/show-teams";
    private static final String SHOW_TEAM_PATH = "profile/show-team";
    private static final String MY_PROFILE_PATH = "profile";
    private static final String MY_LOGS_PATH = "profile/logs";
    private static final String GET_NOTIFICATIONS_PATH = "profile/get-notifications";
    private static final String UPDATE_PROFILE_PATH = "profile/update-profile";

    // task

    private static final String ALL_TASKS_PATH = "tasks/all";
    private static final String EDIT_TASK_TITLE_PATH = "tasks/edit-title";
    private static final String EDIT_TASK_DESCRIPTION_PATH = "tasks/edit-description";
    private static final String EDIT_TASK_PRIORITY_PATH = "tasks/edit-priority";
    private static final String EDIT_TASK_DEADLINE_PATH = "tasks/edit-priority";
    private static final String ASSIGN_USER_PATH = "tasks/assign-user";
    private static final String REMOVE_USER_PATH = "tasks/remove-user";
    private static final String EDIT_TASK_PATH = "tasks/edit-task";

    // team

    private static final String GET_SCOREBOARD_PATH = "team/scoreboard";
    private static final String GET_ROADMAP_FRM_PATH = "team/roadmap-frm";
    private static final String GET_ROADMAP_PATH = "team/roadmap";
    private static final String GET_MESSAGES_FRM_PATH = "team/messages-frm";
    private static final String GET_MESSAGES_PATH = "team/messages";
    private static final String GET_TEAM_PATH = "team/get-team";
    private static final String SEND_MESSAGE_PATH = "team/send-message";
    private static final String SHOW_TASKS_PATH = "team/show-tasks";
    private static final String SHOW_TASK_PATH = "team/show-task";
    private static final String GET_LEADER_TEAMS_PATH = "team/get-leader-teams";
    private static final String GET_LEADER_TEAM_PATH = "team/get-leader-team";
    private static final String CREATE_TEAM_PATH = "team/create-team";
    private static final String GET_ALL_TASKS_PATH = "team/get-all-tasks";
    private static final String CREATE_TASK_PATH = "team/create-task";
    private static final String CREATE_TASK_EXT_PATH = "team/create-task-ext";
    private static final String GET_MEMBERS_PATH = "team/get-members";
    private static final String ADD_MEMBER_PATH = "team/add-member";
    private static final String DELETE_MEMBER_PATH = "team/delete-member";
    private static final String SUSPEND_MEMBER_PATH = "team/suspend-member";
    private static final String PROMOTE_USER_PATH = "team/promote-user";
    private static final String ASSIGN_T0_TASK_PATH = "team/assign-to-task";
    private static final String GET_ALL_USERS_PATH = "team/get-all-users";


    private static final int PORT = 5678;
    private static final String BASE_URL = "http://localhost";

    public static void main(String[] args) {
        SaveAndLoadController.load();
        port(PORT);

        ConsoleHelper.getInstance().println(String.format("Server started at %s:%d", BASE_URL, PORT));

        before((request, response) -> {
            System.out.println(request.requestMethod());
            System.out.println(request.queryString());
            System.out.println(request.body());
//            if (!request.pathInfo().equalsIgnoreCase(LOGIN_PATH) &&
//                    !request.pathInfo().equalsIgnoreCase(SIGNUP_PATH)) {
//                halt(403);
//            }
        });

        // authentication

        post(LOGIN_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            System.out.println(requestBody);
            return LoginController.getInstance()
                    .userLogin((String) req.getArg(USERNAME), (String) req.getArg(PASSWORD));
        }, new JsonTransformer());

        post(SIGNUP_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return LoginController.getInstance()
                    .userCreate((String) req.getArg(USERNAME),
                            (String) req.getArg(PASSWORD1),
                            (String) req.getArg(PASSWORD2),
                            (String) req.getArg(EMAIL));
        }, new JsonTransformer());

        // admin

        get(GET_PROFILE_PATH, JSON, (request, response) -> {
            String username = request.queryParams(USERNAME);
            return AdminController.getInstance()
                    .getProfile(username);
        }, new JsonTransformer());

        patch(BAN_USER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return AdminController.getInstance()
                    .banUser((String) req.getArg(USERNAME));
        }, new JsonTransformer());

        patch(CHANGE_ROLE_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return AdminController.getInstance()
                    .changeRole((String) req.getArg(USERNAME), (String) req.getArg(ROLE));
        }, new JsonTransformer());

        get(GET_PENDING_TEAMS, JSON, (request, response) -> {
            return AdminController.getInstance()
                    .getPendingTeams();
        }, new JsonTransformer());

        patch(ACCEPT_PENDING_TEAMS, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            ArrayList<String> pendingTeams = (ArrayList<String>) req.getArg("pendingTeams");
            return AdminController.getInstance()
                    .acceptPendingTeams(pendingTeams.toArray(new String[0]));
        }, new JsonTransformer());

        patch(REJECT_PENDING_TEAMS, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            ArrayList<String> pendingTeams = (ArrayList<String>) req.getArg("pendingTeams");
            return AdminController.getInstance()
                    .rejectPendingTeams(pendingTeams.toArray(new String[0]));
        }, new JsonTransformer());

        get(ADMIN_GET_ALL_USERS_PATH, JSON, (request, response) -> {
            return AdminController.getInstance()
                    .getAllUsers();
        }, new JsonTransformer());

        // calendar

        get(GET_CALENDAR_PATH, JSON, (request, response) -> {
            String calendar = request.queryParams(CALENDAR);
            return controller.CalendarMenuController.getInstance()
                    .getCalendar(calendar);
        }, new JsonTransformer());

        // board

        put(CREATE_BOARD_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .createNewBoard(team, (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        delete(REMOVE_BOARD_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .removeBoard(team, (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        patch(SELECT_BOARD_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .selectBoard(team, (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        patch(DESELECT_BOARD_PATH, JSON, (request, response) -> {
            return BoardMenuController.getInstance()
                    .deselectBoard();
        }, new JsonTransformer());

        put(CREATE_CATEGORY_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .createNewCategory(team, (String) req.getArg(CATEGORY_NAME), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(CREATE_CATEGORY_AT_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .createNewCategoryAt(team, (String) req.getArg(CATEGORY_NAME),
                            Integer.parseInt((String) req.getArg(COLUMN)), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(SET_BOARD_DONE_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .setBoardToDone(team, (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(ADD_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .addTaskToBoard(team, (String) req.getArg(TASK_ID), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(ASSIGN_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .assignTaskToMember(team, (String) req.getArg(USERNAME),
                            (String) req.getArg(TASK_ID), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(FORCE_MOVE_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .forceMoveTaskToCategory(team, (String) req.getArg(CATEGORY_NAME),
                            (String) req.getArg(TASK_TITLE), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(MOVE_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .moveTaskToNextCategory(team, (String) req.getArg(TASK_TITLE), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(SHOW_CATEGORY_TASKS_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .showCategoryTasks(team, (String) req.getArg(CATEGORY_NAME), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        get(GET_SPECIFIED_CATEGORY_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .getSpecificCategoryTasks(team, (String) req.getArg(CATEGORY), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(OPEN_FAILED_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .openFailedTask(team, (String) req.getArg(TASK_TITLE), (String) req.getArg(DEADLINE),
                            (String) req.getArg(BOARD_NAME), (String) req.getArg(TEAM_MATE),
                            (String) req.getArg(CATEGORY));
        }, new JsonTransformer());

        get(SHOW_BOARD_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance()
                    .showBoard(team, (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        get(GET_BOARDS_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return BoardMenuController.getInstance().getBoards(team);
        }, new JsonTransformer());

        // notification

        put(NOTIFICATION_USER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return NotificationController.getInstance()
                    .sendNotificationToUser((String) req.getArg(BODY), (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        put(NOTIFICATION_TEAM_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return NotificationController.getInstance()
                    .sendNotificationToTeam((String) req.getArg(BODY), (String) req.getArg(TEAM_NAME));
        }, new JsonTransformer());

        put(NOTIFICATION_ALL_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return NotificationController.getInstance().sendNotificationToAll((String) req.getArg(BODY));
        }, new JsonTransformer());

        // profile

        post(CHANGE_PASS_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return ProfileMenuController.getInstance()
                    .changePassword((String) req.getArg(OLD_PASS), (String) req.getArg(NEW_PASS));
        }, new JsonTransformer());

        patch(CHANGE_USER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return ProfileMenuController.getInstance()
                    .changeUsername((String) req.getArg(NEW_USERNAME));
        }, new JsonTransformer());

        get(SHOW_TEAMS_PATH, JSON, (request, response) -> {
            return ProfileMenuController.getInstance().showTeams();
        }, new JsonTransformer());

        get(SHOW_TEAM_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return ProfileMenuController.getInstance().showTeam((String) req.getArg(TEAM_NAME));
        }, new JsonTransformer());

        get(MY_PROFILE_PATH, JSON, (request, response) -> {
            return ProfileMenuController.getInstance().getMyProfile();
        }, new JsonTransformer());

        get(MY_LOGS_PATH, JSON, (request, response) -> {
            return ProfileMenuController.getInstance().getLogs();
        }, new JsonTransformer());

        get(GET_NOTIFICATIONS_PATH, JSON, (request, response) -> {
            return ProfileMenuController.getInstance().getNotifications();
        }, new JsonTransformer());

        patch(UPDATE_PROFILE_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            LocalDate birthDate = new Gson().fromJson(req.getArg(BIRTH_DATE).toString(), LocalDate.class);
            return ProfileMenuController.getInstance()
                    .updateProfile((String) req.getArg(FIRST_NAME), (String) req.getArg(LAST_NAME),
                            birthDate);
        }, new JsonTransformer());

        // tasks

        get(ALL_TASKS_PATH, JSON, (request, response) -> {
            return TasksMenuController.getInstance().getAllTasks();
        }, new JsonTransformer());

        patch(EDIT_TASK_TITLE_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TasksMenuController.getInstance()
                    .editTaskTitle((String) req.getArg(ID), (String) req.getArg(TITLE));
        }, new JsonTransformer());

        patch(EDIT_TASK_DESCRIPTION_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TasksMenuController.getInstance()
                    .editTaskTitle((String) req.getArg(ID), (String) req.getArg(DESCRIPTION));
        }, new JsonTransformer());

        patch(EDIT_TASK_PRIORITY_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TasksMenuController.getInstance()
                    .editTaskTitle((String) req.getArg(ID), (String) req.getArg(PRIORITY));
        }, new JsonTransformer());

        patch(EDIT_TASK_DEADLINE_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TasksMenuController.getInstance()
                    .editTaskTitle((String) req.getArg(ID), (String) req.getArg(DEADLINE));
        }, new JsonTransformer());

        patch(ASSIGN_USER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TasksMenuController.getInstance()
                    .editTaskTitle((String) req.getArg(ID), (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        patch(REMOVE_USER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TasksMenuController.getInstance()
                    .editTaskTitle((String) req.getArg(ID), (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        patch(EDIT_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TasksMenuController.getInstance()
                    .editTask(team, (String) req.getArg(ID), (String) req.getArg(TITLE),
                            (String) req.getArg(PRIORITY), (String) req.getArg(START_TIME),
                            (String) req.getArg(DEADLINE), (String) req.getArg(DESCRIPTION));
        }, new JsonTransformer());

        // team

        get(GET_SCOREBOARD_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().getScoreboard(team);
        }, new JsonTransformer());

        get(GET_ROADMAP_FRM_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().getRoadmapFormatted(team);
        }, new JsonTransformer());

        get(GET_ROADMAP_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().getRoadmap(team);
        }, new JsonTransformer());

        get(GET_MESSAGES_FRM_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().getMessagesFormatted(team);
        }, new JsonTransformer());

        get(GET_MESSAGES_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().getMessages(team);
        }, new JsonTransformer());

        get(GET_TEAM_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TeamMenuController.getInstance().getTeam((String) req.getArg(TEAM_NAME));
        }, new JsonTransformer());

        put(SEND_MESSAGE_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().sendMessage(team, (String) req.getArg(BODY));
        }, new JsonTransformer());

        get(SHOW_TASKS_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().showTasks(team);
        }, new JsonTransformer());

        get(SHOW_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().showTask(team, (String) req.getArg(TASK_ID));
        }, new JsonTransformer());

        get(GET_LEADER_TEAMS_PATH, JSON, (request, response) -> {
            return TeamMenuController.getInstance().getLeaderTeams();
        }, new JsonTransformer());

        get(GET_LEADER_TEAM_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TeamMenuController.getInstance().getLeaderTeam((String) req.getArg(TEAM_NAME));
        }, new JsonTransformer());

        put(CREATE_TEAM_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TeamMenuController.getInstance().createTeam((String) req.getArg(TEAM_NAME));
        }, new JsonTransformer());

        get(GET_ALL_TASKS_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().getAllTasks(team);
        }, new JsonTransformer());

        put(CREATE_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance()
                    .createTask(team, (String) req.getArg(TITLE), (String) req.getArg(START_TIME),
                            (String) req.getArg(DEADLINE));
        }, new JsonTransformer());

        put(CREATE_TASK_EXT_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance()
                    .createTask(team, (String) req.getArg(TITLE), (String) req.getArg(PRIORITY),
                            (String) req.getArg(START_TIME), (String) req.getArg(DEADLINE),
                            (String) req.getArg(DESCRIPTION));
        }, new JsonTransformer());

        get(GET_MEMBERS_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().getMembers(team);
        }, new JsonTransformer());

        put(ADD_MEMBER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().addMemberToTeam(team, (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        delete(DELETE_MEMBER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().deleteMember(team, (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        patch(SUSPEND_MEMBER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().suspendMember(team, (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        patch(PROMOTE_USER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().promoteUser(team, (String) req.getArg(USERNAME),
                    (String) req.getArg(RATE));
        }, new JsonTransformer());

        patch(ASSIGN_T0_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().assignToTask(team, (String) req.getArg(TASK_ID),
                    (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        get(GET_ALL_USERS_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            Team team = new Gson().fromJson((String) req.getArg(TEAM), Team.class);
            return TeamMenuController.getInstance().getAllUsers(team);
        }, new JsonTransformer());
    }

    public static class JsonTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }
    }
}


