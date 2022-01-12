package controller;

import model.User;

public class UserController {
    static User loggedUser = null;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void logout() {
        loggedUser = null;
    }

    public static Response clearNotifications() {
        loggedUser.clearNotifications();
        return new Response("Notifications cleared!",true);
    }

    public static Response getNotifications() {
        String answer = "";
        for(String notification : loggedUser.getNotifications()){
            answer += notification + "\n";
        }
        return new Response(answer,true, loggedUser.getNotifications());
    }
}
