package controller;

import model.User;

public class AdminController {

    private final String SUCCESS_USER_BANED =
            "User removed successfully";
    private final String SUCCESS_ROLE_CHANGED =
            "User role changed to %s successfully";
    private final String SUCCESS_NOTIFICATION_SENT =
            "Notification sent successfully!";

    private final String WARN_404_USER =
            "There is no user with this username!";
    private final String WARN_WRONG_ROLE =
            "Wrong role name!";

    private static AdminController controller = null;

    public static AdminController getInstance() {
        if (controller == null)
            controller = new AdminController();
        return controller;
    }

    public Response getProfile(String username) {
        User user = User.getUser(username);

        if (user == null)
            return new Response(WARN_404_USER, false);

        return new Response(user.toString(), true, user);
    }

    public Response banUser(String username) {
        User user = User.getUser(username);

        if (user == null)
            return new Response(WARN_404_USER, false);

        User.removeUser(user);
        return new Response(SUCCESS_USER_BANED, true);
    }

    public Response changeRole(String username, String role) {
        User user = User.getUser(username);

        if (user == null)
            return new Response(WARN_404_USER, false);

        if (user.setType(role)) {
            return new Response(SUCCESS_ROLE_CHANGED, true);
        } else {
            return new Response(WARN_WRONG_ROLE, false);
        }
    }

    public Response sendNotificationToAll(String notification) {
        for (User user : User.getAllUsers()) {
            user.sendNotification(notification);
        }
        return new Response(SUCCESS_NOTIFICATION_SENT, true);
    }

    public Response sendNotification(String username, String notification) {
        User user = User.getUser(username);

        if (user == null)
            return new Response(WARN_404_USER, false);

        user.sendNotification(notification);
        return new Response(SUCCESS_NOTIFICATION_SENT, true);
    }
}
