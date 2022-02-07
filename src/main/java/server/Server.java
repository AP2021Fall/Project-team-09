package server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import server.model.*;
import server.controller.*;
import spark.ResponseTransformer;
import utilities.ConsoleHelper;

import java.lang.reflect.Type;
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
    private static final String PENDING_TEAMS = "pending-teams";
    private static final String OLD_USERNAME = "old_username";
    private static final String OLD_EMAIL = "old_email";
    private static final String NEW_EMAIL = "new_email";

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

    private static final String CHANGE_USERNAME = "admin/change-username";
    private static final String CHANGE_PASSWORD = "admin/change-password";
    private static final String CHANGE_EMAIL = "admin/change-email";
    private static final String UPDATE_PROFILE = "admin/change-profile";

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
    private static final String GET_ALL_NOTIFICATIONS = "notification/get-all";

    // profile

    private static final String CHANGE_PASS_PATH = "profile/change-pass";
    private static final String CHANGE_USER_PATH = "profile/change-user";
    private static final String SHOW_TEAMS_PATH = "profile/show-teams";
    private static final String SHOW_TEAM_PATH = "profile/show-team";
    private static final String MY_PROFILE_PATH = "profile/my-profile";
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
    private static final String ACTIVATE_MEMBER_PATH = "/team/activate-member";
    private static final String PROMOTE_USER_PATH = "team/promote-user";
    private static final String ASSIGN_T0_TASK_PATH = "team/assign-to-task";
    private static final String GET_ALL_USERS_PATH = "team/get-all-users";

    // general

    private static final String GET_AUTH_USER = "auth/get-user";
    private static final String GET_USER = "user/get-user";
    private static final String GET_ALL_USERS = "user/get-all-users";
    private static final String GET_ALL_TEAM = "team/get-all";
    private static final String GET_ALL_TASK = "task/get-all";


    private static final int PORT = 5678;
    private static final String BASE_URL = "http://localhost";

    public static void main(String[] args) {
        SaveAndLoadController.load();
        SaveAndLoadController.save();
        port(PORT);

        ConsoleHelper.getInstance().println(String.format("Server started at %s:%d", BASE_URL, PORT));

        before((request, response) -> {
            System.out.println(request.pathInfo());
            System.out.println(request.requestMethod());
            System.out.println(request.queryString());
            System.out.println(request.body());
            System.out.println(request);
            if (!request.pathInfo().equalsIgnoreCase(String.format("/%s", LOGIN_PATH)) &&
                    !request.pathInfo().equalsIgnoreCase(String.format("/%s", SIGNUP_PATH))) {
                System.out.println("not authentication");
                if (request.headers("token") == null) {
                    halt(403);
                } else {
                    System.out.println("has token");
                    String token = request.headers("token");
                    User user = UserController.getUser(token);
                    System.out.println(user);
                    System.out.println(token);
                    if (user != null) {
                        UserController.setLoggedUser(user);
                    } else {
                        System.out.println("stopped here");
                        halt(403);
                    }
                }
            }
            System.out.println("-----------");
        });

        // authentication

        post(LOGIN_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
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
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get(PENDING_TEAMS).toString();
                req.setObject(object);
            }
            Type typeMyType = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> pendingTeams = new Gson().fromJson((String) req.getObject(), typeMyType);
            return AdminController.getInstance()
                    .acceptPendingTeams(pendingTeams.toArray(new String[0]));
        }, new JsonTransformer());

        patch(REJECT_PENDING_TEAMS, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get(PENDING_TEAMS).toString();
                req.setObject(object);
            }
            Type typeMyType = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> pendingTeams = new Gson().fromJson((String) req.getObject(), typeMyType);
            return AdminController.getInstance()
                    .rejectPendingTeams(pendingTeams.toArray(new String[0]));
        }, new JsonTransformer());

        get(ADMIN_GET_ALL_USERS_PATH, JSON, (request, response) -> {
            return AdminController.getInstance()
                    .getAllUsers();
        }, new JsonTransformer());

        patch(CHANGE_USERNAME, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return AdminController.getInstance()
                    .changeUsername((String) req.getArg(OLD_USERNAME), (String) req.getArg(NEW_USERNAME));
        }, new JsonTransformer());

        post(CHANGE_PASSWORD, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return AdminController.getInstance()
                    .changePassword((String) req.getArg(USERNAME), (String) req.getArg(PASSWORD));
        }, new JsonTransformer());

        patch(CHANGE_EMAIL, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return AdminController.getInstance()
                    .changeEmail((String) req.getArg(USERNAME), (String) req.getArg(NEW_EMAIL));
        }, new JsonTransformer());

        patch(UPDATE_PROFILE, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return AdminController.getInstance()
                    .updateProfile((String) req.getArg(USERNAME),
                            (String) req.getArg(FIRST_NAME), (String) req.getArg(LAST_NAME));
        }, new JsonTransformer());

        // calendar

        get(GET_CALENDAR_PATH, JSON, (request, response) -> {
            String calendar = request.queryParams(CALENDAR);
            return CalendarMenuController.getInstance()
                    .getCalendar(calendar);
        }, new JsonTransformer());

        // board

        put(CREATE_BOARD_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return BoardMenuController.getInstance()
                    .createNewBoard(t, (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        delete(REMOVE_BOARD_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return BoardMenuController.getInstance()
                    .removeBoard(t, (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        patch(SELECT_BOARD_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return BoardMenuController.getInstance()
                    .selectBoard(t, (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        patch(DESELECT_BOARD_PATH, JSON, (request, response) -> {
            return BoardMenuController.getInstance()
                    .deselectBoard();
        }, new JsonTransformer());

        put(CREATE_CATEGORY_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return BoardMenuController.getInstance()
                    .createNewCategory(t, (String) req.getArg(CATEGORY_NAME), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(CREATE_CATEGORY_AT_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return BoardMenuController.getInstance()
                    .createNewCategoryAt(t, (String) req.getArg(CATEGORY_NAME),
                            Integer.parseInt((String) req.getArg(COLUMN)), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(SET_BOARD_DONE_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return BoardMenuController.getInstance()
                    .setBoardToDone(t, (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(ADD_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return BoardMenuController.getInstance()
                    .addTaskToBoard(t, (String) req.getArg(TASK_ID), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(ASSIGN_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return BoardMenuController.getInstance()
                    .assignTaskToMember(t, (String) req.getArg(USERNAME),
                            (String) req.getArg(TASK_ID), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(FORCE_MOVE_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return BoardMenuController.getInstance()
                    .forceMoveTaskToCategory(t, (String) req.getArg(CATEGORY_NAME),
                            (String) req.getArg(TASK_TITLE), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(MOVE_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return BoardMenuController.getInstance()
                    .moveTaskToNextCategory(t, (String) req.getArg(TASK_TITLE), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        put(SHOW_CATEGORY_TASKS_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return BoardMenuController.getInstance()
                    .showCategoryTasks(t, (String) req.getArg(CATEGORY_NAME), (String) req.getArg(BOARD_NAME));
        }, new JsonTransformer());

        get(GET_SPECIFIED_CATEGORY_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            String category = request.queryParams(CATEGORY);
            String boardName = request.queryParams(BOARD_NAME);
            return BoardMenuController.getInstance()
                    .getSpecificCategoryTasks(Team.getTeamByName(team), category, boardName);
        }, new JsonTransformer());

        put(OPEN_FAILED_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return BoardMenuController.getInstance()
                    .openFailedTask(t, (String) req.getArg(TASK_TITLE), (String) req.getArg(DEADLINE),
                            (String) req.getArg(BOARD_NAME), (String) req.getArg(TEAM_MATE),
                            (String) req.getArg(CATEGORY));
        }, new JsonTransformer());

        get(SHOW_BOARD_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            String boardName = request.queryParams(BOARD_NAME);
            return BoardMenuController.getInstance()
                    .showBoard(Team.getTeamByName(team), boardName);
        }, new JsonTransformer());

        get(GET_BOARDS_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            return BoardMenuController.getInstance().getBoards(Team.getTeamByName(team));
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
            System.out.println("body");
            System.out.println((String) req.getArg(BODY));
            return NotificationController.getInstance().sendNotificationToAll((String) req.getArg(BODY));
        }, new JsonTransformer());

        get(GET_ALL_NOTIFICATIONS, JSON, (request, response) -> {
            return new MResponse("Success", true, UserController.getLoggedUser().getNotifications());
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
            String teamName = request.queryParams(TEAM_NAME);
            return ProfileMenuController.getInstance().showTeam(teamName);
        }, new JsonTransformer());

        get(MY_PROFILE_PATH, JSON, (request, response) -> {
            System.out.println("finally");
            System.out.println(UserController.getLoggedUser());
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
                    .editTaskDescription((String) req.getArg(ID), (String) req.getArg(DESCRIPTION));
        }, new JsonTransformer());

        patch(EDIT_TASK_PRIORITY_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TasksMenuController.getInstance()
                    .editTaskPriority((String) req.getArg(ID), (String) req.getArg(PRIORITY));
        }, new JsonTransformer());

        patch(EDIT_TASK_DEADLINE_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TasksMenuController.getInstance()
                    .editTaskDeadline((String) req.getArg(ID), (String) req.getArg(DEADLINE));
        }, new JsonTransformer());

        patch(ASSIGN_USER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TasksMenuController.getInstance()
                    .addToAssignedUsers((String) req.getArg(ID), (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        patch(REMOVE_USER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TasksMenuController.getInstance()
                    .removeAssignedUsers((String) req.getArg(ID), (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        patch(EDIT_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return TasksMenuController.getInstance()
                    .editTask(t, (String) req.getArg(ID), (String) req.getArg(TITLE),
                            (String) req.getArg(PRIORITY), (String) req.getArg(START_TIME),
                            (String) req.getArg(DEADLINE), (String) req.getArg(DESCRIPTION));
        }, new JsonTransformer());

        // team

        get(GET_SCOREBOARD_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            return TeamMenuController.getInstance().getScoreboard(Team.getTeamByName(team));
        }, new JsonTransformer());

        get(GET_ROADMAP_FRM_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            return TeamMenuController.getInstance().getRoadmapFormatted(Team.getTeamByName(team));
        }, new JsonTransformer());

        get(GET_ROADMAP_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            return TeamMenuController.getInstance().getRoadmap(Team.getTeamByName(team));
        }, new JsonTransformer());

        get(GET_MESSAGES_FRM_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            return TeamMenuController.getInstance().getMessagesFormatted(Team.getTeamByName(team));
        }, new JsonTransformer());

        get(GET_MESSAGES_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            return TeamMenuController.getInstance().getMessages(Team.getTeamByName(team));
        }, new JsonTransformer());

        get(GET_TEAM_PATH, JSON, (request, response) -> {
            String teamName = request.queryParams(TEAM_NAME);
            return TeamMenuController.getInstance().getTeam(teamName);
        }, new JsonTransformer());

        put(SEND_MESSAGE_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return TeamMenuController.getInstance().sendMessage(t, (String) req.getArg(BODY));
        }, new JsonTransformer());

        get(SHOW_TASKS_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            return TeamMenuController.getInstance().showTasks(Team.getTeamByName(team));
        }, new JsonTransformer());

        get(SHOW_TASK_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            String taskId = request.queryParams(TASK_ID);
            return TeamMenuController.getInstance().showTask(Team.getTeamByName(team), taskId);
        }, new JsonTransformer());

        get(GET_LEADER_TEAMS_PATH, JSON, (request, response) -> {
            return TeamMenuController.getInstance().getLeaderTeams();
        }, new JsonTransformer());

        get(GET_LEADER_TEAM_PATH, JSON, (request, response) -> {
            String teamName = request.queryParams(TEAM_NAME);
            return TeamMenuController.getInstance().getLeaderTeam(teamName);
        }, new JsonTransformer());

        put(CREATE_TEAM_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return TeamMenuController.getInstance().createTeam((String) req.getArg(TEAM_NAME));
        }, new JsonTransformer());

        get(GET_ALL_TASKS_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            return TeamMenuController.getInstance().getAllTasks(Team.getTeamByName(team));
        }, new JsonTransformer());

        put(CREATE_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return TeamMenuController.getInstance()
                    .createTask(t, (String) req.getArg(TITLE), (String) req.getArg(START_TIME),
                            (String) req.getArg(DEADLINE));
        }, new JsonTransformer());

        put(CREATE_TASK_EXT_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return TeamMenuController.getInstance()
                    .createTask(t, (String) req.getArg(TITLE), (String) req.getArg(PRIORITY),
                            (String) req.getArg(START_TIME), (String) req.getArg(DEADLINE),
                            (String) req.getArg(DESCRIPTION));
        }, new JsonTransformer());

        get(GET_MEMBERS_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            return TeamMenuController.getInstance().getMembers(Team.getTeamByName(team));
        }, new JsonTransformer());

        put(ADD_MEMBER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return TeamMenuController.getInstance().addMemberToTeam(t, (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        delete(DELETE_MEMBER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return TeamMenuController.getInstance().deleteMember(t, (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        patch(SUSPEND_MEMBER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return TeamMenuController.getInstance().suspendMember(t, (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        patch(ACTIVATE_MEMBER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return TeamMenuController.getInstance().activateMember(t, (String) req.getArg(USERNAME));
        }, new JsonTransformer());


        patch(PROMOTE_USER_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return TeamMenuController.getInstance().promoteUser(t, (String) req.getArg(USERNAME),
                    (String) req.getArg(RATE));
        }, new JsonTransformer());

        patch(ASSIGN_T0_TASK_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            JsonElement jsonElement = new JsonParser().parse(requestBody);
            if (jsonElement.getAsJsonObject().get("arguments") != null) {
                String object = jsonElement.getAsJsonObject()
                        .get("arguments").getAsJsonObject()
                        .get("team").toString();
                req.setObject(object);
            }
            Team team = new Gson().fromJson((String) req.getObject(), Team.class);
            Team t = Team.getTeamByName(team.getName());
            return TeamMenuController.getInstance().assignToTask(t, (String) req.getArg(TASK_ID),
                    (String) req.getArg(USERNAME));
        }, new JsonTransformer());

        get(GET_ALL_USERS_PATH, JSON, (request, response) -> {
            String team = request.queryParams(TEAM);
            return TeamMenuController.getInstance().getAllUsers(Team.getTeamByName(team));
        }, new JsonTransformer());

        // general

        get(GET_ALL_USERS, JSON, (request, response) -> {
            return new MResponse("Success", true, User.getAllUsers());
        }, new JsonTransformer());

        get(GET_AUTH_USER, JSON, (request, response) -> {
            String username = request.queryParams(USERNAME);
            String password = request.queryParams(PASSWORD);
            return new MResponse("Success", true, User.getUser(username, password));
        }, new JsonTransformer());

        get(GET_USER, JSON, (request, response) -> {
            String username = request.queryParams(USERNAME);
            return new MResponse("success", true, User.getUser(username));
        }, new JsonTransformer());

        get(GET_ALL_TEAM, JSON, (request, response) -> {
            return new MResponse("Success", true, Team.getTeams());
        }, new JsonTransformer());

        get(GET_ALL_TASK, JSON, (request, response) -> {
            return new MResponse("Success", true, Task.getAllTask());
        }, new JsonTransformer());

        afterAfter((request, response) -> SaveAndLoadController.save());


        // test

        get("/test/get-users", JSON, (request, response) -> {
            return AdminController.getInstance()
                    .getAllUsers();
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


