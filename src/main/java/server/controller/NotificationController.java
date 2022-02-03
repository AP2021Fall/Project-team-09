package server.controller;

import model.Notification;
import model.Team;
import model.User;

public class NotificationController {

    private final String SUCCESS_NOTIFICATION_SENT =
            "Notification sent successfully!";

    private final String WARN_404_USER =
            "No user exists with this username!";
    private final String WARN_404_TEAM =
            "No team exists with this name!";
    private final String WARN_404_BODY =
            "Notification body cannot be empty!";

    private static NotificationController notificationController;

    public static NotificationController getInstance() {
        if (notificationController == null)
            notificationController = new NotificationController();
        return notificationController;
    }

    public MResponse sendNotificationToUser(String body, String username) {
        User user = User.getUser(username);

        if(user == null)
            return new MResponse(WARN_404_USER, false);

        if(body.isEmpty())
            return new MResponse(WARN_404_BODY, false);

        Notification notification = new Notification(UserController.getLoggedUser(), null, body);
        user.sendNotification(notification);
        return new MResponse(SUCCESS_NOTIFICATION_SENT, true);
    }

    public MResponse sendNotificationToTeam(String body, String teamName) {
        Team team = Team.getTeamByName(teamName);

        if(team == null)
            return new MResponse(WARN_404_TEAM, false);

        if(body.isEmpty())
            return new MResponse(WARN_404_BODY, false);

        Notification notification = new Notification(UserController.getLoggedUser(), team, body);
        team.sendNotification(notification);
        return new MResponse(SUCCESS_NOTIFICATION_SENT, true);
    }

    public MResponse sendNotificationToAll(String body) {
        Notification notification = new Notification(UserController.getLoggedUser(), null, body);
        for (User user : User.getAllUsers()) {
            user.sendNotification(notification);
        }
        return new MResponse(SUCCESS_NOTIFICATION_SENT, true);
    }
}