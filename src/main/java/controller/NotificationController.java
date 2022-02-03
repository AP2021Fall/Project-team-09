package controller;

import model.MRequest;
import model.Notification;
import model.Team;
import model.User;

public class NotificationController {

    private static final String NOTIFICATION_USER_PATH = "/notification/user";
    private static final String NOTIFICATION_TEAM_PATH = "/notification/team";
    private static final String NOTIFICATION_ALL_PATH = "/notification/all";

    private static final String BODY = "body";
    private static final String USERNAME = "username";
    private static final String TEAM_NAME = "team_name";

    private static NotificationController notificationController;

    public static NotificationController getInstance() {
        if (notificationController == null)
            notificationController = new NotificationController();
        return notificationController;
    }

    public MResponse sendNotificationToUser(String body, String username) {
        return new MRequest()
                .setPath(NOTIFICATION_USER_PATH)
                .addArg(BODY, body)
                .addArg(USERNAME, username)
                .put();
    }

    public MResponse sendNotificationToTeam(String body, String teamName) {
        return new MRequest()
                .setPath(NOTIFICATION_TEAM_PATH)
                .addArg(BODY, body)
                .addArg(TEAM_NAME, teamName)
                .put();
    }

    public MResponse sendNotificationToAll(String body) {
        return new MRequest()
                .setPath(NOTIFICATION_ALL_PATH)
                .addArg(BODY, body)
                .put();
    }
}
