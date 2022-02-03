package server;

import com.google.gson.Gson;

import model.MRequest;
import model.Team;
import server.controller.*;
import spark.ResponseTransformer;
import utilities.ConsoleHelper;

import java.util.ArrayList;

import static spark.Spark.*;

public class Server {

    // req

    private static final String JSON = "application/json";

    // args

    private static final String USERNAME = "username";
    private static final String PASSWORD = "username";
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


    private static final int PORT = 5678;
    private static final String BASE_URL = "http://localhost";

    public static void main(String[] args) {
        SaveAndLoadController.load();
        port(PORT);

        ConsoleHelper.getInstance().println(String.format("Server started at %s:%d", BASE_URL, PORT));

        before((request, response) -> {
            System.out.println(request);
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
            String username = request.queryParams("username");
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

        // calendar

        get(GET_CALENDAR_PATH, JSON, (request, response) -> {
            String calendar = request.queryParams("calendar");
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


    }

    public static class JsonTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }
    }
}


