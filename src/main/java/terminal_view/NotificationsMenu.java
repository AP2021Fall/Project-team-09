package terminal_view;

import controller.Response;
import controller.UserController;

public class NotificationsMenu implements TerminalView{
    @Override
    public String text() {
        return "Notification menu!";
    }

    @Override
    public void showHelp() {
        System.out.println("notifications --read");
        System.out.println("notifications --clear");
    }

    @Override
    public void parse(ArgumentManager input) {
        if(input.getCommand().equals("notifications")){
            if(input.contains("read")){
                readNotifications();
            }
            else if(input.contains("clear")){
                clearNotifications();
            }
        }
    }

    private void clearNotifications() {
        Response response = UserController.clearNotifications();
        System.out.println(response.getMessage());
    }

    private void readNotifications() {
        Response response = UserController.getNotifications();
        System.out.println(response.getMessage());
    }
}
