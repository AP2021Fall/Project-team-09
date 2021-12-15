package controller;

import model.User;

public class UserController {
    static User logonUser = null;

    public static User getLogonUser() {
        return logonUser;
    }

    public static void logout() {
        logonUser = null;
    }



    public static Response clearNotifications() {
        logonUser.clearNotifications();
        return new Response("Notifications cleared!",true);
    }

    public static Response getNotifications() {
        String answer = "";
        for(String notification : logonUser.getNotifications()){
            answer += notification + "\n";
        }
        return new Response(answer,true,logonUser.getNotifications());
    }
}
