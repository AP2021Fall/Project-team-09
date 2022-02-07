package server.controller;

import server.model.Notification;
import server.model.User;

import java.util.Base64;
import java.util.HashMap;
import java.util.Random;

public class UserController {
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    static HashMap<String, User> userToken = new HashMap<>();
    static User loggedUser = null;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    public static User getUser(String token) {
        return userToken.get(token);
    }

    public static String generateToken(User user) {
        String time = String.valueOf(System.currentTimeMillis());
        String username = user.getUsername();

        int nonce = new Random().nextInt(9999);

        String token = hash(String.format("%s%s%d", username, time, nonce));
        userToken.put(token, user);
        return token;
    }

    public static void logout() {
        loggedUser = null;
    }

    public static MResponse clearNotifications() {
        loggedUser.clearNotifications();
        return new MResponse("Notifications cleared!", true);
    }

    public static MResponse getNotifications() {
        String answer = "";
        for (Notification notification : loggedUser.getNotifications()) {
            answer += notification + "\n";
        }
        return new MResponse(answer, true, loggedUser.getNotifications());
    }

    private static String hash(String input) {
        return base64Encoder.encodeToString(input.getBytes());
    }
}
