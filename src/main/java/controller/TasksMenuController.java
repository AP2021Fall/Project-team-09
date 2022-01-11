package controller;

import model.Task;
import model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TasksMenuController {

    private final String SUCCESS_TITLE_UPDATE = "Title updated successfully!";
    private final String SUCCESS_DES_UPDATE = "Description updated successfully!";
    private final String SUCCESS_PRIO_UPDATE = "Priority updated successfully!";
    private final String SUCCESS_DEAD_UPDATE = "Deadline updated successfully!";
    private final String SUCCESS_USER_REMOVE = "User %s removed successfully!";
    private final String SUCCESS_USER_ADD = "User %s added successfully!";

    private final String WARN_DEAD_INVALID = "New deadline is invalid!";
    private final String WARN_404_USER_LIST =
            "There is not any user with this username %s in list";
    private final String WARN_404_USER =
            "There is not any user with this username %s!";
    private final String WARN_FAILED =
            "Operation failed!";

    private static TasksMenuController tasksMenuController = null;

    public static TasksMenuController getInstance() {
        if (tasksMenuController == null)
            tasksMenuController = new TasksMenuController();
        return tasksMenuController;
    }

    public Response editTaskTitle(int id, String title) {
        Task task = Task.getTask(id);

        if (task != null) {
            task.setTitle(title);
            return new Response(SUCCESS_TITLE_UPDATE, true);
        }
        return new Response(WARN_FAILED, false);
    }

    public Response editTaskDescription(int id, String description) {
        Task task = Task.getTask(id);

        if (task != null) {
            task.setDescription(description);
            return new Response(SUCCESS_DES_UPDATE, true);
        }
        return new Response(WARN_FAILED, false);
    }

    public Response editTaskPriority(int id, String priority) {
        Task task = Task.getTask(id);

        if (task != null) {
            task.setPriority(priority);
            return new Response(SUCCESS_PRIO_UPDATE, true);
        }
        return new Response(WARN_FAILED, false);
    }

    public Response editTaskDeadline(int id, String deadline) {
        Task task = Task.getTask(id);

        if (task != null) {
            LocalDateTime deadDateTime =
                    isTimeValid(task.getTimeOfCreation(), deadline);
            if (deadDateTime == null)
                return new Response(WARN_DEAD_INVALID, false);

            task.setTimeOfDeadline(deadDateTime);
            return new Response(SUCCESS_DEAD_UPDATE, true);
        }
        return new Response(WARN_FAILED, false);
    }

    public Response addToAssignedUsers(int id, String username) {
        Task task = Task.getTask(id);

        if (task != null) {
            User user = User.getUser(username);
            if(user == null)
                return new Response(WARN_404_USER, false);
            task.assignUser(user);
            return new Response(SUCCESS_USER_ADD, true);
        }
        return new Response(WARN_FAILED, false);
    }

    public Response removeAssignedUsers(int id, String username) {
        Task task = Task.getTask(id);

        if (task != null) {
            User user = task.isInAssignedUsers(username);
            if(user == null)
                return new Response(WARN_404_USER_LIST, false);
            task.removeFromAssignedUsers(user);
            return new Response(SUCCESS_USER_REMOVE, true);
        }
        return new Response(WARN_FAILED, false);
    }

    private LocalDateTime isTimeValid(LocalDateTime creationDateTime, String deadline) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");

        try {
            LocalDateTime dead = LocalDateTime.parse(deadline, formatter);
            if (dead.isBefore(now))
                return null;
            if (dead.isBefore(creationDateTime))
                return null;

            return dead;
        } catch (DateTimeParseException exception) {
            return null;
        }
    }
}
