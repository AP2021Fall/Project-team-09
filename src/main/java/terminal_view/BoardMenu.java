package terminal_view;

import controller.BoardMenuController;
import controller.MResponse;
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
    private String WARN_INVALID_OPERATION =
            "Invalid Operation!";

    private final String NEW_BOARD_COMMAND =
            "board --new --name <board name>";
    private final String BOARD_REMOVE_COMMAND =
            "board --remove --name <board name>";
    private final String BOARD_SELECT_COMMAND =
            "board --select --name <board name>";
    private final String BOARD_DESELECT_COMMAND =
            "board --deselect";
    private final String BOARD_NEW_CAT_COMMAND =
            "board --new --category [category name] --name [board name]";
    private final String BOARD_NEW_CAT_AT_COMMAND =
            "board --category [category name] --column [column] --name [board name]";
    private final String BOARD_DONE_COMMAND =
            "board --done --name [board name]";
    private final String BOARD_ADD_TASK_COMMAND =
            "board --add [task id] --name [board name]";
    private final String BOARD_ASSIGN_TASK_COMMAND =
            "board --assign [team member] --task [task id] --name [board name]";
    private final String BOARD_FORCE_CAT_COMMAND =
            "board --force --category [category name] --task [task title] --name [board name]";
    private final String BOARD_CAT_NEXT_COMMAND =
            "board --category next --task [task] --name [board name]";
    private final String BOARD_SHOW_COMMAND =
            "board --show [done/failed] --name --board [board name]";
    private final String BOARD_OPEN_COMMAND =
            "board --open --task [task title] (--assign[teammate])? --deadline [deadline]" +
                    " (--category [category name])? --name [board name]";
    private final String BOARD_SHOW_NAME_COMMAND =
            "board --show --name [board name]";

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
    private final String OPEN =
            "open";
    private final String DEADLINE =
            "deadline";

    @Override
    public String text() {
        return WELCOME_MESSAGE;
    }

    @Override
    public void showHelp() {
        ConsoleHelper.getInstance()
                .join(NEW_BOARD_COMMAND)
                .join(BOARD_REMOVE_COMMAND)
                .join(BOARD_SELECT_COMMAND)
                .join(BOARD_DESELECT_COMMAND)
                .join(BOARD_NEW_CAT_COMMAND)
                .join(BOARD_NEW_CAT_AT_COMMAND)
                .join(BOARD_DONE_COMMAND)
                .join(BOARD_ADD_TASK_COMMAND)
                .join(BOARD_ASSIGN_TASK_COMMAND)
                .join(BOARD_FORCE_CAT_COMMAND)
                .join(BOARD_CAT_NEXT_COMMAND)
                .join(BOARD_SHOW_COMMAND)
                .join(BOARD_OPEN_COMMAND)
                .join(BOARD_SHOW_NAME_COMMAND)
                .printAll();
    }

    @Override
    public void parse(ArgumentManager input) {

        if (input.isCommandFollowArg(BOARD, REMOVE, NAME)) {
            removeBoard(input);
        } else if (input.isCommandFollowArg(BOARD, SELECT, NAME)) {
            selectBoard(input);
        } else if (input.isCommandFollowArg(BOARD, DESELECT)) {
            deselectBoard();
        } else if (input.isCommandFollowArg(BOARD, NEW, CATEGORY)
                || input.isCommandFollowArg(BOARD, NEW, CATEGORY, NAME)) {
            createBoardCategory(input);
        } else if (input.isCommandFollowArg(BOARD, CATEGORY, COLUMN)
                || input.isCommandFollowArg(BOARD, CATEGORY, COLUMN, NAME)) {
            createBoardCategoryAt(input);
        } else if (input.isCommandFollowArg(BOARD, DONE)
                || input.isCommandFollowArg(BOARD, DONE, NAME)) {
            setBoardStateToDone(input);
        } else if (input.isCommandFollowArg(BOARD, ADD)
                || input.isCommandFollowArg(BOARD, ADD, NAME)) {
            addTaskToBoard(input);
        } else if (input.isCommandFollowArg(BOARD, ASSIGN, TASK)
                || input.isCommandFollowArg(BOARD, ASSIGN, TASK, NAME)) {
            assignTaskToMember(input);
        } else if (input.isCommandFollowArg(BOARD, FORCE, CATEGORY, TASK)
                || input.isCommandFollowArg(BOARD, FORCE, CATEGORY, TASK, NAME)) {
            forceMoveTaskToCategory(input);
        } else if (input.isCommandFollowArg(BOARD, CATEGORY, TASK)
                || input.isCommandFollowArg(BOARD, CATEGORY, TASK, NAME)) {
            moveTaskToCategory(input);
        } else if (input.isCommandFollowArg(BOARD, SHOW, CATEGORY, BOARD)) {
            showCategoryTasks(input);
        } else if (input.isCommandFollowArg(BOARD, SHOW, NAME, BOARD)) {
            showDoneFailed(input);
        } else if (input.isCommandFollowArg(BOARD, OPEN, TASK, DEADLINE, NAME)
                || input.isCommandFollowArg(BOARD, OPEN, TASK, ASSIGN, DEADLINE, CATEGORY, NAME)
                || input.isCommandFollowArg(BOARD, OPEN, TASK, ASSIGN, DEADLINE, NAME)
                || input.isCommandFollowArg(BOARD, OPEN, TASK, DEADLINE, CATEGORY, NAME)) {
            openTask(input);
        } else if (input.isCommandFollowArg(BOARD, NEW, NAME)) {
            createBoard(input);
        }else if (input.isCommandFollowArg(BOARD, SHOW, NAME)) {
            showBoard(input);
        }
    }

    private void showBoard(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);

            String boardName = null;

            try {
                boardName = input.get(NAME);
            } catch (IllegalCommandException exception) {

            }

            MResponse MResponse = BoardMenuController.getInstance()
                    .showBoard(team, boardName);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showDoneFailed(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);
            MResponse MResponse = BoardMenuController.getInstance()
                    .getSpecificCategoryTasks(team, input.get(SHOW), input.get(BOARD));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void openTask(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);
            String teamMate = null;
            String category = null;
            try {
                teamMate = input.get(ASSIGN);
            } catch (IllegalCommandException e) {
            }

            try {
                category = input.get(CATEGORY);
            } catch (IllegalCommandException e) {
            }
            MResponse MResponse = BoardMenuController.getInstance()
                    .openFailedTask(team, input.get(TASK),
                            input.get(DEADLINE),
                            input.get(NAME), teamMate, category);

            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void createBoard(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);
            MResponse MResponse = BoardMenuController.getInstance()
                    .createNewBoard(team, input.get(NAME));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void removeBoard(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);
            MResponse MResponse = BoardMenuController.getInstance()
                    .removeBoard(team, input.get(NAME));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void selectBoard(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);
            MResponse MResponse = BoardMenuController.getInstance()
                    .selectBoard(team, input.get(NAME));
            if (MResponse.isSuccess()) {
                Board board = (Board) MResponse.getObject();
                SharedPreferences.add(BOARD, board);
                ConsoleHelper.getInstance().println(MResponse.getMessage());
            }
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void deselectBoard() {
        try {
            MResponse MResponse = BoardMenuController.getInstance()
                    .deselectBoard();
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void createBoardCategory(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);

            String categoryName = input.get(CATEGORY);
            String boardName = null;

            try {
                boardName = input.get(NAME);
            } catch (IllegalCommandException exception) {

            }

            MResponse MResponse = BoardMenuController.getInstance()
                    .createNewCategory(team, categoryName, boardName);

            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void createBoardCategoryAt(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);

            String categoryName = input.get(CATEGORY);
            int column = Integer.parseInt(input.get(COLUMN));
            String boardName = null;

            try {
                boardName = input.get(NAME);
            } catch (IllegalCommandException exception) {

            }

            MResponse MResponse = BoardMenuController.getInstance()
                    .createNewCategoryAt(team, categoryName, column, boardName);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void setBoardStateToDone(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);

            String boardName = null;

            try {
                boardName = input.get(NAME);
            } catch (IllegalCommandException exception) {

            }

            MResponse MResponse = BoardMenuController.getInstance()
                    .setBoardToDone(team, boardName);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void addTaskToBoard(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);

            String boardName = null;

            try {
                boardName = input.get(NAME);
            } catch (IllegalCommandException exception) {

            }

            MResponse MResponse = BoardMenuController.getInstance()
                    .addTaskToBoard(team, input.get(ADD), boardName);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void assignTaskToMember(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);

            String boardName = null;

            try {
                boardName = input.get(NAME);
            } catch (IllegalCommandException exception) {

            }

            MResponse MResponse = BoardMenuController.getInstance()
                    .assignTaskToMember(team, input.get(ASSIGN), input.get(TASK), boardName);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void forceMoveTaskToCategory(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);

            String boardName = null;

            try {
                boardName = input.get(NAME);
            } catch (IllegalCommandException exception) {

            }

            MResponse MResponse = BoardMenuController.getInstance()
                    .forceMoveTaskToCategory(team, input.get(CATEGORY), input.get(TASK), boardName);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void moveTaskToCategory(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);

            String boardName = null;

            try {
                boardName = input.get(NAME);
            } catch (IllegalCommandException exception) {

            }

            if (!input.get(CATEGORY).equalsIgnoreCase("next")) {
                ConsoleHelper.getInstance().println(WARN_INVALID_OPERATION);
                return;
            }

            MResponse MResponse = BoardMenuController.getInstance()
                    .moveTaskToNextCategory(team, input.get(TASK), boardName);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showCategoryTasks(ArgumentManager input) {
        try {
            Team team = (Team) SharedPreferences.get(TEAM);

            MResponse MResponse = BoardMenuController.getInstance()
                    .showCategoryTasks(team, input.get(CATEGORY), input.get(BOARD));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }
}