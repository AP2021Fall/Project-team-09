package controller;

import model.Notification;
import model.User;

public class UserController {
    static User loggedUser = null;
    static String token = null;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    public static void setToken(String t) {
        token = t;
    }

    public static String getToken() {
        return token;
    }

    public static void logout() {
        loggedUser = null;
    }

    public static MResponse clearNotifications() {
        loggedUser.clearNotifications();
        return new MResponse("Notifications cleared!",true);
    }

    public static MResponse getNotifications() {
        String answer = "";
        for(Notification notification : loggedUser.getNotifications()){
            answer += notification + "\n";
        }
        return new MResponse(answer,true, loggedUser.getNotifications());
    }
}
