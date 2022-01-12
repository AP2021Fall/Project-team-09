package terminal_view;

import controller.BoardMenuController;
import controller.Response;
import exceptions.IllegalCommandException;
import model.Board;
import model.Team;
import utilities.ConsoleHelper;
import utilities.SharedPreferences;

public class BoardMenu implements TerminalView {

    private final String WELCOME_MESSAGE =
            "Welcome to board menu";

    private final String WARN_PERMISSION =
            "You don't have the permission to do this action!";
    private final String WARN_CREATION_INCOMPLETE =
            "Please finish creating the board first!";
    private final String WARN_404_BOARD =
            "There is no board with this name!";
    private String WARN_404_SELECTED_BOARD =
            "No board is selected!";
    private String WARN_INVALID_OPERATION =
            "Invalid Operation!";

    private final String NEW_BOARD_COMMAND =
            "board --new --name <board name>";
    private final String BOARD_REMOVE_COMMAND =
            "board --remove --name <board name>";

    private final String BOARD =
            "board";
    private final String NAME =
            "name";
    private final String NEW =
            "new";
    private final String REMOVE =
            "remove";
    private final String SELECT =
            "select";
    private final String DESELECT =
            "deselect";
    private final String CATEGORY =
            "category";
    private final String COLUMN =
            "column";
    private final String DONE =
            "done";
    private final String ADD =
            "add";
    private final String ASSIGN =
            "assign";
    private final String TASK =
            "task";
    private final String FORCE =
            "force";
    private final String SHOW =
            "show";
    private final String TEAM =
            "team";

    @Override
    public String text() {
        return WELCOME_MESSAGE;
    }

    @Override
    public void showHelp() {
        ConsoleHelper.getInstance()
                .join(NEW_BOARD_COMMAND)
                .join(BOARD_REMOVE_COMMAND)
                .printAll();
    }

    @Override
    public void parse(ArgumentManager input) {
        if (input.isCommandFollowArg(BOARD, NEW, NAME)) {
            createBoard(input);
        } else if (input.isCommandFollowArg(BOARD, REMOVE, NAME)) {
            removeBoard(input);
        } else if (input.isCommandFollowArg(BOARD, SELECT, NAME)) {
            selectBoard(input);
        } else if (input.isCommandFollowArg(BOARD, DESELECT)) {
            deselectBoard(input);
        } else if (input.isCommandFollowArg(BOARD, NEW, CATEGORY)) {
            createBoardCategory(input);
        } else if (input.isCommandFollowArg(BOARD, CATEGORY, COLUMN, NAME)) {
            createBoardCategoryAt(input);
        } else if (input.isCommandFollowArg(BOARD, DONE, NAME)) {
            setBoardStateToDone(input);
        } else if (input.isCommandFollowArg(BOARD, ADD, NAME)) {
            addTaskToBoard(input);
        } else if (input.isCommandFollowArg(BOARD, ASSIGN, TASK, NAME)) {
            assignTaskToMember(input);
        } else if (input.isCommandFollowArg(BOARD, FORCE, CATEGORY, TASK, NAME)) {
            forceMoveTaskToCategory(input);
        } else if (input.isCommandFollowArg(BOARD, CATEGORY, TASK, NAME)) {
            moveTaskToCategory(input);
        } else if (input.isCommandFollowArg(BOARD, SHOW, CATEGORY, BOARD)) {
            showCategoryTasks(input);
        }
    }

    private void createBoard(ArgumentManager input) {
        Team team = (Team) SharedPreferences.get(TEAM);
        Response response = BoardMenuController.getInstance()
                .createNewBoard(team, input.get(NAME));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void removeBoard(ArgumentManager input) {
        Team team = (Team) SharedPreferences.get(TEAM);
        Response response = BoardMenuController.getInstance()
                .removeBoard(team, input.get(NAME));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void selectBoard(ArgumentManager input) {
        Team team = (Team) SharedPreferences.get(TEAM);
        Response response = BoardMenuController.getInstance()
                .selectBoard(team, input.get(NAME));
        if (response.isSuccess()) {
            Board board = (Board) response.getObject();
            SharedPreferences.add(BOARD, board);
        }
    }

    private void deselectBoard(ArgumentManager input) {
        Response response = BoardMenuController.getInstance()
                .deselectBoard(input.get(NAME));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void createBoardCategory(ArgumentManager input) {
        Team team = (Team) SharedPreferences.get(TEAM);

        String categoryName = input.get(CATEGORY);
        String boardName = null;

        try {
            boardName = input.get(NAME);
        } catch (IllegalCommandException exception) {

        }

        Response response = BoardMenuController.getInstance()
                .createNewCategory(team, categoryName, boardName);

        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void createBoardCategoryAt(ArgumentManager input) {
        Team team = (Team) SharedPreferences.get(TEAM);

        String categoryName = input.get(CATEGORY);
        int column = Integer.parseInt(input.get(COLUMN));
        String boardName = null;

        try {
            boardName = input.get(NAME);
        } catch (IllegalCommandException exception) {

        }

        Response response = BoardMenuController.getInstance()
                .createNewCategoryAt(team, categoryName, column, boardName);
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void setBoardStateToDone(ArgumentManager input) {
        Team team = (Team) SharedPreferences.get(TEAM);

        String boardName = null;

        try {
            boardName = input.get(NAME);
        } catch (IllegalCommandException exception) {

        }

        Response response = BoardMenuController.getInstance()
                .setBoardToDone(team, boardName);
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void addTaskToBoard(ArgumentManager input) {
        Team team = (Team) SharedPreferences.get(TEAM);

        String boardName = null;

        try {
            boardName = input.get(NAME);
        } catch (IllegalCommandException exception) {

        }

        Response response = BoardMenuController.getInstance()
                .addTaskToBoard(team, input.get(ADD), boardName);
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void assignTaskToMember(ArgumentManager input) {
        Team team = (Team) SharedPreferences.get(TEAM);

        String boardName = null;

        try {
            boardName = input.get(NAME);
        } catch (IllegalCommandException exception) {

        }

        Response response = BoardMenuController.getInstance()
                .assignTaskToMember(team, input.get(ASSIGN), input.get(TASK), input.get(NAME));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void forceMoveTaskToCategory(ArgumentManager input) {
        Team team = (Team) SharedPreferences.get(TEAM);

        String boardName = null;

        try {
            boardName = input.get(NAME);
        } catch (IllegalCommandException exception) {

        }

        Response response = BoardMenuController.getInstance()
                .forceMoveTaskToCategory(team, input.get(CATEGORY), input.get(TASK), boardName);
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void moveTaskToCategory(ArgumentManager input) {
        Team team = (Team) SharedPreferences.get(TEAM);

        String boardName = null;

        try {
            boardName = input.get(NAME);
        } catch (IllegalCommandException exception) {

        }

        if(!input.get(CATEGORY).equalsIgnoreCase("next")){
            ConsoleHelper.getInstance().println(WARN_INVALID_OPERATION);
            return;
        }

        Response response = BoardMenuController.getInstance()
                .moveTaskToNextCategory(team, input.get(TASK), boardName);
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void showCategoryTasks(ArgumentManager input) {
        Team team = (Team) SharedPreferences.get(TEAM);

        Response response = BoardMenuController.getInstance()
                .showCategoryTasks(team, input.get(CATEGORY), input.get(BOARD));
        ConsoleHelper.getInstance().println(response.getMessage());
    }
}