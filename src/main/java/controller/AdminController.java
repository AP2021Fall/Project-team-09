package controller;

import model.User;

public class AdminController {
    private static AdminController controller = null;

    public static AdminController getInstance() {
        if(controller == null)
            controller = new AdminController();
        return controller;
    }

    public Response getProfile(String username) {
        if(User.usernameExists(username)){
            User user = User.getUser(username);
            return new Response(user.toString(),true,user);
        }
        else{
            return new Response("There is no user with this username!",false);
        }
    }

    public Response banUser(String username) {
        if(User.usernameExists(username)){
            User.removeUser(username);
            return new Response("User removed successfully",true);
        }
        else{
            return new Response("There is no user with this username!",false);
        }
    }

    public Response changeRole(String username, String role) {
        if(User.usernameExists(username)){
            User user = User.getUser(username);
            System.out.println(role);
            if(user.setType(role)){
                return new Response("User role changed to " + user.getType() + " successfully",true);
            }
            else{
                return new Response("Wrong role name!",false);
            }
        }
        else{
            return new Response("There is no user with this username!",false);
        }
    }

    public Response sendNotificationToAll(String notification) {
        for(User user : User.getAllUsers()){
            user.addNotification(notification);
        }
        return new Response("Notification sent successfully!" , true);
    }

    public Response sendNotification(String username,String notification) {
        if(User.usernameExists(username)){
            User user = User.getUser(username);
            user.addNotification(notification);
            return new Response("Notification sent successfully!" , true);
        }
        else{
            return new Response("There is no user with this username!",false);
        }
    }
}
