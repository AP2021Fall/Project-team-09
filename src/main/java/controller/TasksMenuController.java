package controller;

import model.MRequest;
import model.Team;

public class TasksMenuController {

    private static final String ALL_TASKS_PATH = "/tasks/all";
    private static final String EDIT_TASK_TITLE_PATH = "/tasks/edit-title";
    private static final String EDIT_TASK_DESCRIPTION_PATH = "/tasks/edit-description";
    private static final String EDIT_TASK_PRIORITY_PATH = "/tasks/edit-priority";
    private static final String EDIT_TASK_DEADLINE_PATH = "/tasks/edit-priority";
    private static final String ASSIGN_USER_PATH = "/tasks/assign-user";
    private static final String REMOVE_USER_PATH = "/tasks/remove-user";
    private static final String EDIT_TASK_PATH = "/tasks/edit-task";

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String PRIORITY = "priority";
    private static final String DEADLINE = "deadline";
    private static final String USERNAME = "username";
    private static final String START_TIME = "start_time";
    private static final String TEAM = "team";

    private static TasksMenuController tasksMenuController = null;

    public static TasksMenuController getInstance() {
        if (tasksMenuController == null)
            tasksMenuController = new TasksMenuController();
        return tasksMenuController;
    }

    public MResponse getAllTasks() {
        return new MRequest()
                .setPath(ALL_TASKS_PATH)
                .get();
    }

    public MResponse editTaskTitle(String id, String title) {
        return new MRequest()
                .setPath(EDIT_TASK_TITLE_PATH)
                .addArg(ID, id)
                .addArg(TITLE, title)
                .patch();
    }

    public MResponse editTaskDescription(String id, String description) {
        return new MRequest()
                .setPath(EDIT_TASK_DESCRIPTION_PATH)
                .addArg(ID, id)
                .addArg(DESCRIPTION, description)
                .patch();
    }

    public MResponse editTaskPriority(String id, String priority) {
        return new MRequest()
                .setPath(EDIT_TASK_PRIORITY_PATH)
                .addArg(ID, id)
                .addArg(PRIORITY, priority)
                .patch();
    }

    public MResponse editTaskDeadline(String id, String deadline) {
        return new MRequest()
                .setPath(EDIT_TASK_DEADLINE_PATH)
                .addArg(ID, id)
                .addArg(DEADLINE, deadline)
                .patch();
    }

    public MResponse addToAssignedUsers(String id, String username) {
        return new MRequest()
                .setPath(ASSIGN_USER_PATH)
                .addArg(ID, id)
                .addArg(USERNAME, username)
                .patch();
    }

    public MResponse removeAssignedUsers(String id, String username) {
        return new MRequest()
                .setPath(REMOVE_USER_PATH)
                .addArg(ID, id)
                .addArg(USERNAME, username)
                .patch();
    }

    public MResponse editTask(Team team, String id, String title,
                              String priority, String startTime, String deadline, String description) {
        return new MRequest()
                .setPath(EDIT_TASK_PATH)
                .addArg(TEAM, team)
                .addArg(ID, id)
                .addArg(TITLE, title)
                .addArg(PRIORITY, priority)
                .addArg(START_TIME, startTime)
                .addArg(DEADLINE, deadline)
                .addArg(DESCRIPTION, description)
                .patch();
    }
}
