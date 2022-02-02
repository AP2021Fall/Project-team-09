package server;

import com.google.gson.Gson;

import model.MRequest;
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
    private static final String PENDING_TEAMS = "pending-teams";

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


    }

    public static class JsonTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }
    }
}


