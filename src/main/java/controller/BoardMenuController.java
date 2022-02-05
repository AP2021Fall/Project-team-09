package controller;

import model.MRequest;
import model.Team;

public class BoardMenuController {

    private static final String CREATE_BOARD_PATH = "/board/create";
    private static final String REMOVE_BOARD_PATH = "/board/remove";
    private static final String SELECT_BOARD_PATH = "/board/select";
    private static final String DESELECT_BOARD_PATH = "/board/deselect";
    private static final String CREATE_CATEGORY_PATH = "/board/category";
    private static final String CREATE_CATEGORY_AT_PATH = "/board/category-at";
    private static final String SET_BOARD_DONE_PATH = "/board/set-done";
    private static final String ADD_TASK_PATH = "/board/add-task";
    private static final String ASSIGN_TASK_PATH = "/board/assign-task";
    private static final String FORCE_MOVE_PATH = "/board/force-move";
    private static final String MOVE_TASK_PATH = "/board/move-task";
    private static final String SHOW_CATEGORY_TASKS_PATH = "/board/show-category-tasks";
    private static final String GET_SPECIFIED_CATEGORY_PATH = "/board/get-specified-category";
    private static final String OPEN_FAILED_TASK_PATH = "/board/open-failed-task";
    private static final String SHOW_BOARD_PATH = "/board/show-board";
    private static final String GET_BOARDS_PATH = "/board/get-boards";

    private static final String TEAM = "team";
    private static final String BOARD_NAME = "board_name";
    private static final String CATEGORY_NAME = "category_name";
    private static final String COLUMN = "column";
    private static final String TASK_ID = "task_id";
    private static final String USERNAME = "username";
    private static final String TASK_TITLE = "task_title";
    private static final String CATEGORY = "category";
    private static final String DEADLINE = "deadline";
    private static final String TEAM_MATE = "task_mate";


    private static BoardMenuController boardMenuController = null;

    public static BoardMenuController getInstance() {
        if (boardMenuController == null)
            boardMenuController = new BoardMenuController();
        return boardMenuController;
    }

    public MResponse createNewBoard(Team team, String boardName) {
        return new MRequest()
                .setPath(CREATE_BOARD_PATH)
                .addArg(TEAM, team)
                .addArg(BOARD_NAME, boardName)
                .put();
    }

    public MResponse removeBoard(Team team, String boardName) {
        return new MRequest()
                .setPath(REMOVE_BOARD_PATH)
                .addArg(TEAM, team)
                .addArg(BOARD_NAME, boardName)
                .delete();
    }

    public MResponse selectBoard(Team team, String boardName) {
        return new MRequest()
                .setPath(SELECT_BOARD_PATH)
                .addArg(TEAM, team)
                .addArg(BOARD_NAME, boardName)
                .patch();
    }

    public MResponse deselectBoard() {
        return new MRequest()
                .setPath(DESELECT_BOARD_PATH)
                .patch();
    }

    public MResponse createNewCategory(Team team, String categoryName, String boardName) {
        return new MRequest()
                .setPath(CREATE_CATEGORY_PATH)
                .addArg(TEAM, team)
                .addArg(CATEGORY_NAME, categoryName)
                .addArg(BOARD_NAME, boardName)
                .put();
    }

    public MResponse createNewCategoryAt(Team team, String categoryName, int column, String boardName) {
        return new MRequest()
                .setPath(CREATE_CATEGORY_AT_PATH)
                .addArg(TEAM, team)
                .addArg(CATEGORY_NAME, categoryName)
                .addArg(COLUMN, column)
                .addArg(BOARD_NAME, boardName)
                .put();
    }

    public MResponse setBoardToDone(Team team, String boardName) {
        return new MRequest()
                .setPath(SET_BOARD_DONE_PATH)
                .addArg(TEAM, team)
                .addArg(BOARD_NAME, boardName)
                .put();
    }

    public MResponse addTaskToBoard(Team team, String taskId, String boardName) {
        return new MRequest()
                .setPath(ADD_TASK_PATH)
                .addArg(TEAM, team)
                .addArg(TASK_ID, taskId)
                .addArg(BOARD_NAME, boardName)
                .put();
    }

    public MResponse assignTaskToMember(Team team, String username, String taskId, String boardName) {
        return new MRequest()
                .setPath(ASSIGN_TASK_PATH)
                .addArg(TEAM, team)
                .addArg(USERNAME, username)
                .addArg(TASK_ID, taskId)
                .addArg(BOARD_NAME, boardName)
                .put();
    }

    public MResponse forceMoveTaskToCategory(Team team, String categoryName,
                                             String taskTitle, String boardName) {
        return new MRequest()
                .setPath(FORCE_MOVE_PATH)
                .addArg(TEAM, team)
                .addArg(CATEGORY_NAME, categoryName)
                .addArg(TASK_TITLE, taskTitle)
                .addArg(BOARD_NAME, boardName)
                .put();
    }

    public MResponse moveTaskToNextCategory(Team team, String taskTitle, String boardName) {
        return new MRequest()
                .setPath(MOVE_TASK_PATH)
                .addArg(TEAM, team)
                .addArg(TASK_TITLE, taskTitle)
                .addArg(BOARD_NAME, boardName)
                .put();
    }

    public MResponse showCategoryTasks(Team team, String categoryName, String boardName) {
        return new MRequest()
                .setPath(SHOW_CATEGORY_TASKS_PATH)
                .addArg(TEAM, team)
                .addArg(CATEGORY_NAME, categoryName)
                .addArg(BOARD_NAME, boardName)
                .put();
    }

    public MResponse getSpecificCategoryTasks(Team team, String category, String boardName) {
        return new MRequest()
                .setPath(GET_SPECIFIED_CATEGORY_PATH)
                .addArg(TEAM, team.getName())
                .addArg(CATEGORY, category)
                .addArg(BOARD_NAME, boardName)
                .get();
    }

    public MResponse openFailedTask(Team team, String taskTitle, String deadline, String boardName,
                                    String teamMate, String category) {
        return new MRequest()
                .setPath(OPEN_FAILED_TASK_PATH)
                .addArg(TEAM, team)
                .addArg(TASK_TITLE, taskTitle)
                .addArg(DEADLINE, deadline)
                .addArg(BOARD_NAME, boardName)
                .addArg(TEAM_MATE, teamMate)
                .addArg(CATEGORY, category)
                .put();
    }

    public MResponse showBoard(Team team, String boardName) {
        return new MRequest()
                .setPath(SHOW_BOARD_PATH)
                .addArg(TEAM, team.getName())
                .addArg(BOARD_NAME, boardName)
                .get();
    }

    public MResponse getBoards(Team team) {
        return new MRequest()
                .setPath(GET_BOARDS_PATH)
                .addArg(TEAM, team.getName())
                .get();
    }
}
