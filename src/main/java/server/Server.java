package server;

import com.google.gson.Gson;

import model.MRequest;
import server.controller.*;
import spark.ResponseTransformer;
import utilities.ConsoleHelper;

import static spark.Spark.*;

public class Server {

    // req

    private static final String JSON = "application/json";

    // args

    private static final String USERNAME = "username";
    private static final String PASSWORD = "username";
    private static final String PASSWORD1 = "password1";
    private static final String PASSWORD2 = "password2";
    private static final String EMAIL = "EMAIL";

    // auth

    private static final String LOGIN_PATH = "auth/signIn";
    private static final String SIGNUP_PATH = "auth/signUp";

    private static final int PORT = 5678;
    private static final String BASE_URL = "http://localhost";

    public static void main(String[] args) {
        SaveAndLoadController.load();
        port(PORT);

        ConsoleHelper.getInstance().println(String.format("Server started at %s:%d", BASE_URL, PORT));

        before((request, response) -> {
            if (!request.pathInfo().equalsIgnoreCase(LOGIN_PATH) &&
                    !request.pathInfo().equalsIgnoreCase(SIGNUP_PATH)) {
                halt(403);
            }
        });

        // authentication

        post(LOGIN_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return LoginController.getInstance()
                    .userLogin(req.getArg(USERNAME), req.getArg(PASSWORD));
        }, new JsonTransformer());

        post(SIGNUP_PATH, JSON, (request, response) -> {
            String requestBody = request.body();
            MRequest req = new Gson().fromJson(requestBody, MRequest.class);
            return LoginController.getInstance()
                    .userCreate(req.getArg(USERNAME),
                            req.getArg(PASSWORD1),
                            req.getArg(PASSWORD2),
                            req.getArg(EMAIL));
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


