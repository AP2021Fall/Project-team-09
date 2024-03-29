package server.controller;

import server.model.*;
import utilities.SharedPreferences;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class BoardMenuController {

    private String SUCCESS_BOARD_CREATED =
            "Board created successfully!";
    private String SUCCESS_BOARD_REMOVED =
            "Board removed successfully!";
    private String SUCCESS_BOARD_SELECTED =
            "Board \"%s\" selected!";
    private String SUCCESS_BOARD_DESELECTED =
            "Board \"%s\" deselected!";
    private String SUCCESS_CATEGORY_CREATED =
            "Category added to column \"%d\" in board \"%s\"";
    private String SUCCESS_BOARD_DONE =
            "Board \"%s\" is set to done!";
    private String SUCCESS_TASK_ADDED =
            "Task added to board \"%s\" successfully!";
    private String SUCCESS_TASK_ASSIGNED =
            "Task \"%s\" assigned to user \"%s\"";
    private String SUCCESS_SET_CATEGORY =
            "Task \"%s\" is moved to category \"%s\"";
    private String SUCCESS_TASK_REOPENED =
            "Task reopened successfully!";
    private final String SUCCESS =
            "Success!";

    private String WARN_BOARD_EXISTS =
            "There is already a board with this name!";
    private String WARN_404_BOARD =
            "There is no board with this name!";
    private String WARN_404_SELECTED_BOARD =
            "No board is selected!";
    private String WARN_WRONG_COL =
            "wrong column!";
    private String WARN_NO_CATEGORY =
            "Please make a category first!";
    private String WARN_TASK_ALREADY_ADDED =
            "This task has already been added to this board!";
    private String WARN_INVALID_TASK_ID =
            "Invalid task id!";
    private String WARN_DEADLINE_PASSED =
            "The deadline of this task has already passed!";
    private String WARN_NOT_ASSIGNED =
            "Please assign this task to someone first!";
    private String WARN_INVALID_TEAMMATE =
            "Invalid teammate!";
    private String WARN_TASK_ALREADY_DONE =
            "This task has already finished!";
    private String WARN_TASK_FAILED =
            "This task has failed!";
    private String WARN_404_TASK =
            "There is no task with given information!";
    private String WARN_INVALID_CATEGORY =
            "Invalid category!";
    private String WARN_NOT_YOURS =
            "This task is not assigned to you!";
    private String WARN_TAKEN_CATEGORY =
            "Name is already taken for a category!";
    private String WARN_NOT_FAILED =
            "Task is not in the failed section";
    private String WARN_INVALID_DEADLINE =
            "Invalid deadline!";
    private String WARN_404_USER =
            "User with this username does not exist!";
    private final String WARN_PERMISSION =
            "You don't have the permission to do this action!";

    private String BOARD =
            "board";

    private static BoardMenuController boardMenuController = null;

    public static BoardMenuController getInstance() {
        if (boardMenuController == null)
            boardMenuController = new BoardMenuController();
        return boardMenuController;
    }

    public MResponse createNewBoard(Team team, String boardName) {

        if (!UserController.getLoggedUser().isTeamLeader())
            return new MResponse(WARN_PERMISSION, false);

        Board board = team.getBoardByName(boardName);

        if (board != null)
            return new MResponse(WARN_BOARD_EXISTS, false);

        team.addBoard(new Board(boardName));
        return new MResponse(SUCCESS_BOARD_CREATED, true);
    }

    public MResponse removeBoard(Team team, String boardName) {

        if (!UserController.getLoggedUser().isTeamLeader())
            return new MResponse(WARN_PERMISSION, false);

        Board board = team.getBoardByName(boardName);

        if (board == null)
            return new MResponse(WARN_404_BOARD, false);
        team.removeBoard(board);
        return new MResponse(SUCCESS_BOARD_REMOVED, true);
    }

    public MResponse selectBoard(Team team, String boardName) {
        Board board = team.getBoardByName(boardName);

        if (board == null)
            return new MResponse(WARN_404_BOARD, false);

        return new MResponse(String.format(SUCCESS_BOARD_SELECTED, board.getName()),
                true, board);
    }

    public MResponse deselectBoard() {
        Board board = (Board) SharedPreferences.get(BOARD);
        if (board == null)
            return new MResponse(WARN_404_SELECTED_BOARD, false);
        String boardName = board.getName();

        SharedPreferences.remove(BOARD);
        return new MResponse(String.format(SUCCESS_BOARD_DESELECTED, boardName), true);
    }

    public MResponse createNewCategory(Team team, String categoryName, String boardName) {

        if (!UserController.getLoggedUser().isTeamLeader())
            return new MResponse(WARN_PERMISSION, false);

        Board board = getBoard(team, boardName);

        if (board == null)
            return new MResponse(WARN_404_SELECTED_BOARD, false);

        if (categoryName.isEmpty())
            return new MResponse(WARN_INVALID_CATEGORY, false);

        if (board.hasCategory(categoryName))
            return new MResponse(WARN_TAKEN_CATEGORY, false);

        int column = board.addCategory(categoryName);
        return new MResponse(String.format(SUCCESS_CATEGORY_CREATED, column, board.getName()),
                true);
    }

    public MResponse createNewCategoryAt(Team team, String categoryName, int column, String boardName) {

        if (!UserController.getLoggedUser().isTeamLeader())
            return new MResponse(WARN_PERMISSION, false);

        Board board = getBoard(team, boardName);

        if (board == null)
            return new MResponse(WARN_404_SELECTED_BOARD, false);

        int col = board.addCategory(column, categoryName);

        if (col > -1)
            return new MResponse(String.format(SUCCESS_CATEGORY_CREATED, column, boardName),
                    true);

        return new MResponse(WARN_WRONG_COL, false);
    }

    public MResponse setBoardToDone(Team team, String boardName) {

        if (!UserController.getLoggedUser().isTeamLeader())
            return new MResponse(WARN_PERMISSION, false);

        Board board = getBoard(team, boardName);

        if (board == null)
            return new MResponse(WARN_404_SELECTED_BOARD, false);

        if (board.getCategories().isEmpty())
            return new MResponse(WARN_NO_CATEGORY, false);

        board.setDone();
        return new MResponse(String.format(SUCCESS_BOARD_DONE, boardName), true);
    }

    public MResponse addTaskToBoard(Team team, String taskId, String boardName) {

        if (!UserController.getLoggedUser().isTeamLeader())
            return new MResponse(WARN_PERMISSION, false);

        Board board = getBoard(team, boardName);

        if (board == null)
            return new MResponse(WARN_404_SELECTED_BOARD, false);

        int tId;
        try {
            tId = Integer.parseInt(taskId);
        } catch (NumberFormatException exception) {
            return new MResponse(WARN_INVALID_TASK_ID, false);
        }

        Task task = team.getTaskById(tId);

        if (task == null)
            return new MResponse(WARN_INVALID_TASK_ID, false);

        if (task.isAddedToBoard(board))
            return new MResponse(WARN_TASK_ALREADY_ADDED, false);

        if (task.hasPassedDeadline())
            return new MResponse(WARN_DEADLINE_PASSED, false);

        if (task.getAssignedUsers().isEmpty())
            return new MResponse(WARN_NOT_ASSIGNED, false);

        task.setBoard(board);
        return new MResponse(String.format(SUCCESS_TASK_ADDED, boardName), true);
    }

    public MResponse assignTaskToMember(Team team, String username, String taskId, String boardName) {

        if (!UserController.getLoggedUser().isTeamLeader())
            return new MResponse(WARN_PERMISSION, false);

        Board board = getBoard(team, boardName);

        if (board == null)
            return new MResponse(WARN_404_SELECTED_BOARD, false);

        int tId;
        try {
            tId = Integer.parseInt(taskId);
        } catch (NumberFormatException exception) {
            return new MResponse(WARN_INVALID_TASK_ID, false);
        }

        Task task = team.getTaskById(tId);

        if (task == null)
            return new MResponse(WARN_INVALID_TASK_ID, false);

        if (!task.isAddedToBoard(board))
            return new MResponse(WARN_INVALID_TASK_ID, false);

        User user = User.getUser(username);
        if (team.hasMember(user))
            return new MResponse(WARN_INVALID_TEAMMATE, false);

        if (task.isDone())
            return new MResponse(WARN_TASK_ALREADY_DONE, false);

        task.assignUser(user);
        return new MResponse(String.format(SUCCESS_TASK_ASSIGNED, task.getTitle(),
                username), true);
    }

    public MResponse forceMoveTaskToCategory(Team team, String categoryName,
                                             String taskTitle, String boardName) {

        Board board = getBoard(team, boardName);

        if (board == null)
            return new MResponse(WARN_404_SELECTED_BOARD, false);

        Task task = team.getTaskByTitle(taskTitle);

        if (task == null)
            return new MResponse(WARN_404_TASK, false);

        User loggedUser = UserController.getLoggedUser();

        if (task.isInAssignedUsers(loggedUser.getUsername()) == null)
            return new MResponse(WARN_NOT_YOURS, false);

        if (!task.isAddedToBoard(board))
            return new MResponse(WARN_404_TASK, false);

        if (!categoryName.equalsIgnoreCase("done"))
            if (!board.hasCategory(categoryName))
                return new MResponse(WARN_INVALID_CATEGORY, false);

        task.setCategory(categoryName);
        if (task.isDone()) {
            team.givePoint(task);
        }
        team.sendNotification(new Notification(team.getLeader(), team,
                String.format("\"%s\" has made progress to \"%s\" task!", loggedUser.getUsername(), task.getTitle())));
        return new MResponse(String.format(SUCCESS_SET_CATEGORY, taskTitle, categoryName), true);
    }


    public MResponse moveTaskToNextCategory(Team team, String taskTitle, String boardName) {

        Board board = getBoard(team, boardName);

        if (board == null)
            return new MResponse(WARN_404_SELECTED_BOARD, false);

        Task task = team.getTaskByTitle(taskTitle);

        if (task == null)
            return new MResponse(WARN_404_TASK, false);

        if (!task.isAddedToBoard(board))
            return new MResponse(WARN_404_TASK, false);

        User loggedUser = UserController.getLoggedUser();

        if (task.isInAssignedUsers(loggedUser.getUsername()) == null)
            return new MResponse(WARN_NOT_YOURS, false);

        boolean result = task.moveToNextCategory();

        if (result) {
            if (task.isDone()) {
                team.givePoint(task);
            }
            team.sendNotification(new Notification(team.getLeader(), team,
                    String.format("\"%s\" has made progress to \"%s\" task!", loggedUser.getUsername(), task.getTitle())));
            return new MResponse(String.format(SUCCESS_SET_CATEGORY, task.getTitle(),
                    task.getCategory()), true);
        } else {
            if (task.isDone())
                return new MResponse(WARN_TASK_ALREADY_DONE, false);
            else
                return new MResponse(WARN_TASK_FAILED, false);
        }
    }

    public MResponse showCategoryTasks(Team team, String categoryName, String boardName) {
        Board board = getBoard(team, boardName);

        if (!board.hasCategory(categoryName))
            return new MResponse(WARN_INVALID_CATEGORY, false);
        return new MResponse(board.getCategoryTasksFormatted(categoryName), true, board.getCategoryTasks(categoryName));
    }

    public MResponse getSpecificCategoryTasks(Team team, String category, String boardName) {
        Board board = getBoard(team, boardName);

        if (board == null)
            return new MResponse(WARN_404_SELECTED_BOARD, false);

        if (category.equalsIgnoreCase("done") || category.equalsIgnoreCase("failed")) {
            return new MResponse(team.getTasksByCategory(category), true, board.getCategoryTasks(category));
        }
        return new MResponse(WARN_INVALID_CATEGORY, false);
    }

    public MResponse openFailedTask(Team team, String taskTitle, String deadline, String boardName,
                                    String teamMate, String category) {

        if (!UserController.getLoggedUser().isTeamLeader())
            return new MResponse(WARN_PERMISSION, false);

        Board board = getBoard(team, boardName);

        boolean categorySet = false;
        boolean teammateSet = false;

        if (board == null)
            return new MResponse(WARN_404_SELECTED_BOARD, false);

        Task task = team.getTaskByTitle(taskTitle);

        if (task == null)
            return new MResponse(WARN_404_TASK, false);

        if (!task.isAddedToBoard(board))
            return new MResponse(WARN_404_TASK, false);

        if (!task.isFailed())
            return new MResponse(WARN_NOT_FAILED, false);

        if (category != null) {
            if (!board.hasCategory(category))
                return new MResponse(WARN_INVALID_CATEGORY, false);
            categorySet = true;
        }
        User user = null;
        if (teamMate != null) {
            user = User.getUser(teamMate);

            if (user == null)
                return new MResponse(WARN_404_USER, false);

            if (!team.hasMember(user))
                return new MResponse(WARN_INVALID_TEAMMATE, false);

            if (task.isInAssignedUsers(user.getUsername()) == null)
                teammateSet = true;
        }

        LocalDateTime deadlineTime = isTimeValid(task.getTimeOfCreation(), deadline);
        if (deadlineTime == null)
            return new MResponse(WARN_INVALID_DEADLINE, false);

        if (!categorySet)
            task.setCategory(board.getFirstCategory());
        else task.setCategory(category);

        if (teammateSet)
            task.assignUser(user);
        task.setTimeOfDeadline(deadlineTime);
        return new MResponse(SUCCESS_TASK_REOPENED, true);
    }

    public MResponse showBoard(Team team, String boardName) {
        Board board = getBoard(team, boardName);

        if (board == null)
            return new MResponse(WARN_404_SELECTED_BOARD, false);

        return new MResponse(board.getInfo(team.getLeader().getUsername(),
                team, board), true);
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

    public MResponse getBoards(Team team) {
        ArrayList<Board> boards = team.getBoards();

        return new MResponse(SUCCESS, true, boards);
    }

    private Board getBoard(Team team, String boardName) {
        Board board = null;
        if (boardName == null)
            board = (Board) SharedPreferences.get(BOARD);
        else
            board = team.getBoardByName(boardName);
        return board;
    }
}
