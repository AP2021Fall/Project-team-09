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
    private final String WARN_TITLE_INVALID =
            "Title is invalid!";
    private final String WARN_DUPLICATE_TITLE =
            "There is another task with this name!";
    private final String WARN_START_TIME_INVALID =
            "Invalid start time!";
    private final String WARN_DEADLINE_INVALID =
            "Invalid deadline!";

    private static TasksMenuController tasksMenuController = null;

    public static TasksMenuController getInstance() {
        if (tasksMenuController == null)
            tasksMenuController = new TasksMenuController();
        return tasksMenuController;
    }

    public Response getAllTasks() {
        User user = UserController.getLoggedUser();

        ArrayList<Team> teams = new ArrayList<>();

        if (user.isTeamLeader()) {
            teams = user.getMyTeams();
        } else {
            teams = user.getTeams();
        }

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
            return new Response(String.format(WARN_404_TASK, taskId), false);

        task.setTitle(title);
        return new Response(SUCCESS_TITLE_UPDATE, true);
    }

    public Response editTaskDescription(String id, String description) {
        int taskId = getInt(id);

        if (taskId == -1)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        Task task = Task.getTask(taskId);

        if (task == null)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        task.setDescription(description);
        return new Response(SUCCESS_DES_UPDATE, true);
    }

    public Response editTaskPriority(String id, String priority) {
        int taskId = getInt(id);

        if (taskId == -1)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        Task task = Task.getTask(taskId);

        if (task == null)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        task.setPriority(priority);
        return new Response(SUCCESS_PRIO_UPDATE, true);
    }

    public Response editTaskDeadline(String id, String deadline) {
        int taskId = getInt(id);

        if (taskId == -1)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        Task task = Task.getTask(taskId);

        if (task == null)
            return new Response(String.format(WARN_404_TASK, taskId), false);


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
            return new Response(String.format(WARN_404_TASK, taskId), false);

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
            return new Response(String.format(WARN_404_TASK, taskId), false);

        User user = task.isInAssignedUsers(username);
        if (user == null)
            return new Response(String.format(WARN_404_USER_LIST, username), false);
        task.removeFromAssignedUsers(user);
        return new Response(String.format(SUCCESS_USER_REMOVE, username), true);
    }

    public Response editTask(Team team, String id, String title,
                             String priority, String startTime, String deadline, String description) {


        int taskId = getInt(id);

        if (taskId == -1)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        Task task = Task.getTask(taskId);

        if (task == null)
            return new Response(String.format(WARN_404_TASK, taskId), false);

        Task t = team.getTaskByTitle(title);

        if (t != null)
            return new Response(WARN_DUPLICATE_TITLE, false);

        if (title.isEmpty())
            return new Response(WARN_TITLE_INVALID, false);

        LocalDateTime start = isTimeValid(startTime);
        LocalDateTime dead = isTimeValid(deadline);

        if (start == null)
            return new Response(WARN_START_TIME_INVALID, false);

        if (dead == null)
            return new Response(WARN_DEADLINE_INVALID, false);

        if (dead.isBefore(start))
            return new Response(WARN_DEADLINE_INVALID, false);

        task.setTitle(title);
        task.setPriority(priority);
        task.setStartTime(start);
        task.setTimeOfDeadline(dead);
        task.setDescription(description);
        return new Response(SUCCESS, true, task);
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

    private LocalDateTime isTimeValid(String time) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");

        try {
            LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
            if (dateTime.isBefore(now))
                return null;

            return dateTime;
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
