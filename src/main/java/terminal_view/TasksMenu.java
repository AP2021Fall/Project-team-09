package terminal_view;

import controller.Response;
import controller.TasksMenuController;
import controller.UserController;
import exceptions.IllegalCommandException;
import utilities.ConsoleHelper;

public class TasksMenu implements TerminalView {

    private final String WELCOME_MESSAGE =
            "Welcome to Tasks Menu";

    private final String WARN_ACCESS =
            "You don't have access to do this action!";
    private final String WARN_COMMAND_INVALID =
            "Invalid command!";

    private final String EDIT_TITLE_COMMAND =
            "edit --task --id [task id] --title [new title]";
    private final String EDIT_DES_COMMAND =
            "edit --task --id [task id] --descriptions [new description]";
    private final String EDIT_PRIORITY_COMMAND =
            "edit --task --id [task id] --priority [new priority]";
    private final String EDIT_DEAD_COMMAND =
            "edit --task --id [task id] --deadline [new deadline]";
    private final String REMOVE_USER_COMMAND =
            "edit --task --id [task id] --assignedUsers [username] --remove";
    private final String ADD_USER_COMMAND =
            "edit --task --id [task id] --assignedUsers [username] --add";

    private final String EDIT = "edit";
    private final String TASK = "task";
    private final String TITLE = "title";
    private final String DESCRIPTION = "descriptions";
    private final String PRIORITY = "priority";
    private final String DEADLINE = "deadline";
    private final String ASSIGNED_USERS = "assignedUsers";
    private final String ADD = "add";
    private final String REMOVE = "remove";

    private final String ID = "id";

    @Override
    public String text() {
        return WELCOME_MESSAGE;
    }

    @Override
    public void showHelp() {
        ConsoleHelper.getInstance()
                .join(EDIT_TITLE_COMMAND)
                .join(EDIT_DES_COMMAND)
                .join(EDIT_PRIORITY_COMMAND)
                .join(EDIT_DEAD_COMMAND)
                .join(REMOVE_USER_COMMAND)
                .join(ADD_USER_COMMAND)
                .printAll();
    }

    @Override
    public void parse(ArgumentManager input) {
        if (!UserController.getLoggedUser().isTeamLeader())
            showWarning();
        if (input.isCommandFollowArg(EDIT, TITLE)) {
            editTaskTitle(input);
        } else if (input.isCommandFollowArg(EDIT, DESCRIPTION)) {
            editTaskDescription(input);
        } else if (input.isCommandFollowArg(EDIT, PRIORITY)) {
            editTaskPriority(input);
        } else if (input.isCommandFollowArg(EDIT, DEADLINE)) {
            editTaskDeadline(input);
        } else if (input.isCommandFollowArg(EDIT, ASSIGNED_USERS)
                && input.isCommandFollowCommand(EDIT, ADD)) {
            addToAssignedUsers(input);
        } else if (input.isCommandFollowArg(EDIT, ASSIGNED_USERS)
                && input.isCommandFollowCommand(EDIT, REMOVE)) {
            removeFromAssignedUsers(input);
        } else {
            ConsoleHelper.getInstance().println(WARN_COMMAND_INVALID);
        }
    }

    private void showWarning() {
        ConsoleHelper.getInstance().println(WARN_ACCESS);
    }

    private void editTaskTitle(ArgumentManager input) {
        try {
            Response response = TasksMenuController.getInstance()
                    .editTaskTitle(input.get(ID), input.get(TITLE));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void editTaskDescription(ArgumentManager input) {
        try {
            Response response = TasksMenuController.getInstance()
                    .editTaskDescription(input.get(ID),
                            input.get(DESCRIPTION));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void editTaskPriority(ArgumentManager input) {
        try {
            Response response = TasksMenuController.getInstance()
                    .editTaskPriority(input.get(ID),
                            input.get(PRIORITY));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void editTaskDeadline(ArgumentManager input) {
        try {
            Response response = TasksMenuController.getInstance()
                    .editTaskDeadline(input.get(ID),
                            input.get(DEADLINE));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void addToAssignedUsers(ArgumentManager input) {
        try {
            Response response = TasksMenuController.getInstance()
                    .addToAssignedUsers(input.get(ID),
                            input.get(ASSIGNED_USERS));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void removeFromAssignedUsers(ArgumentManager input) {
        try {
            Response response = TasksMenuController.getInstance()
                    .removeAssignedUsers(input.get(ID),
                            input.get(ASSIGNED_USERS));
            ConsoleHelper.getInstance().println(response.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }
}
