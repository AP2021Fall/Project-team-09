package terminal_view;

import controller.AdminController;
import controller.Response;
import exceptions.IllegalCommandException;

public class AdminMenu implements TerminalView {
    @Override
    public String text() {
        return "Admin page";
    }

    @Override
    public void showHelp() {
        System.out.println("show profile --username [username]");
        System.out.println("ban user --username [username]");
        System.out.println("send --notification [notification] --all");
        System.out.println("send --notification [notification] --user [username]");
        System.out.println("change role --user [username] --role [role]");
    }

    @Override
    public void parse(ArgumentManager input) {
        if (input.getCommand().equals("show profile")) {
            showUserProfile(input);
        } else if (input.getCommand().equals("ban user")) {
            banUser(input);
        } else if (input.getCommand().equals("send") && input.contains("notification") && input.contains("--all")) {
            sendNotificationAll(input);
        } else if (input.getCommand().equals("send") && input.contains("notification")) {
            sendNotification(input);
        } else if (input.getCommand().equals("change role")) {
            changeRole(input);
        }
    }

    private void sendNotification(ArgumentManager input) {
        try {
            Response response = AdminController.getInstance().sendNotification(input.get("user"), input.get("notification"));
            System.out.println(response.getMessage());
        } catch (IllegalCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendNotificationAll(ArgumentManager input) {
        try {
            Response response = AdminController.getInstance().sendNotificationToAll(input.get("notification"));
            System.out.println(response.getMessage());
        } catch (IllegalCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    private void banUser(ArgumentManager input) {
        try {
            Response response = AdminController.getInstance().banUser(input.get("username"));
            System.out.println(response.getMessage());
        } catch (IllegalCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showUserProfile(ArgumentManager input) {
        try {
            Response response = AdminController.getInstance().getProfile(input.get("username"));
            System.out.println(response.getMessage());
        } catch (IllegalCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    private void changeRole(ArgumentManager input) {
        try {
            System.out.println(input);
            Response response = AdminController.getInstance()
                    .changeRole(input.get("username"), input.get("role"));
            System.out.println(response.getMessage());
        } catch (IllegalCommandException e) {
            System.out.println(e.getMessage());
        }
    }
}
