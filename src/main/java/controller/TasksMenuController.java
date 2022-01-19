package controller;

import model.Task;
import model.Team;
import model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class TasksMenuController {

    private final String SUCCESS_TITLE_UPDATE = "Title updated successfully!";
    private final String SUCCESS_DES_UPDATE = "Description updated successfully!";
    private final String SUCCESS_PRIO_UPDATE = "Priority updated successfully!";
    private final String SUCCESS_DEAD_UPDATE = "Deadline updated successfully!";
    private final String SUCCESS_USER_REMOVE = "User %s removed successfully!";
    private final String SUCCESS_USER_ADD = "User %s added successfully!";
    private final String SUCCESS = "Success!";

    private final String WARN_DEAD_INVALID = "New deadline is invalid!";
    private final String WARN_404_USER_LIST =
            "There is not any user with this username %s in list";
    private final String WARN_404_USER =
            "There is not any user with this username %s!";
    private final String WARN_404_TASK =
            "Task with id %d not found!";

    private static TasksMenuController tasksMenuController = null;

    public static TasksMenuController getInstance() {
        if (tasksMenuController == null)
            tasksMenuController = new TasksMenuController();
        return tasksMenuController;
    }

    public Response getAllTasks() {
        User user = UserController.getLoggedUser();

        ArrayList<Team> teams = user.getTeams();

        HashMap<Team, ArrayList<Task>> teamTasks = new HashMap<>();

        for (Team team : teams) {
            teamTasks.put(team, team.getTasks());
        }

        return new Response(SUCCESS, true, teamTasks);
    }

    public Response editTaskTitle(String id, String title) {
        int taskId = getInt(id);

        if (taskId == -1)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        Task task = Task.getTask(taskId);

        if (task == null)
            return new Response(String.format(WARN_404_TASK, taskId), true);

        task.setTitle(title);
        return new Response(SUCCESS_TITLE_UPDATE, false);
    }

    public Response editTaskDescription(String id, String description) {
        int taskId = getInt(id);

        if (taskId == -1)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        Task task = Task.getTask(taskId);

        if (task == null)
            return new Response(String.format(WARN_404_TASK, taskId), true);

        task.setDescription(description);
        return new Response(SUCCESS_DES_UPDATE, false);
    }

    public Response editTaskPriority(String id, String priority) {
        int taskId = getInt(id);

        if (taskId == -1)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        Task task = Task.getTask(taskId);

        if (task == null)
            return new Response(String.format(WARN_404_TASK, taskId), true);

        task.setPriority(priority);
        return new Response(SUCCESS_PRIO_UPDATE, true);
    }

    public Response editTaskDeadline(String id, String deadline) {
        int taskId = getInt(id);

        if (taskId == -1)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        Task task = Task.getTask(taskId);

        if (task == null)
            return new Response(String.format(WARN_404_TASK, taskId), true);


        LocalDateTime deadDateTime =
                isTimeValid(task.getTimeOfCreation(), deadline);

        if (deadDateTime == null)
            return new Response(WARN_DEAD_INVALID, false);

        task.setTimeOfDeadline(deadDateTime);
        return new Response(SUCCESS_DEAD_UPDATE, true);
    }

    public Response addToAssignedUsers(String id, String username) {
        int taskId = getInt(id);

        if (taskId == -1)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        Task task = Task.getTask(taskId);

        if (task == null)
            return new Response(String.format(WARN_404_TASK, taskId), true);

        User user = User.getUser(username);
        if (user == null)
            return new Response(String.format(WARN_404_USER, username), false);

        task.assignUser(user);
        return new Response(String.format(SUCCESS_USER_ADD, username), true);
    }

    public Response removeAssignedUsers(String id, String username) {
        int taskId = getInt(id);

        if (taskId == -1)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        Task task = Task.getTask(taskId);

        if (task == null)
            return new Response(String.format(WARN_404_TASK, taskId), true);

        User user = task.isInAssignedUsers(username);
        if (user == null)
            return new Response(String.format(WARN_404_USER_LIST, username), false);
        task.removeFromAssignedUsers(user);
        return new Response(String.format(SUCCESS_USER_REMOVE, username), true);
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

    private int getInt(String input) {
        int out = -1;
        try {
            out = Integer.parseInt(input);
        } catch (Exception e) {
            return out;
        }
        return out;
    }
}
