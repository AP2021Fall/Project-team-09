package server;

import com.google.gson.Gson;

import model.MRequest;
import server.controller.*;
import spark.ResponseTransformer;
import utilities.ConsoleHelper;

import static spark.Spark.*;

public class Server {

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
    }

    public static class JsonTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }
    }
}


