package terminal_view;

import controller.ProfileMenuController;
import controller.Response;
import controller.TeamMenuController;
import model.Team;
import utilities.ConsoleHelper;
import utilities.SharedPreferences;

public class TeamMenu implements TerminalView {

    private final String WELCOME_MESSAGE =
            "Welcome to team menu";

    private final String ENTER_TEAM_COMMAND =
            "enter team <teamName>";
    private final String SHOW_SCORE_COMMAND =
            "scoreboard --show";

    private final String ENTER_TEAM =
            "enter team";
    private final String SCOREBOARD =
            "scoreboard";
    private final String ROADMAP =
            "roadmap";
    private final String CHATROOM =
            "chatroom";
    private final String SEND =
            "send";
    private final String MESSAGE =
            "message";
    private final String ID =
            "id";
    private final String SHOW_TASKS =
            "show tasks";
    private final String SHOW_TASK =
            "show task";
    private final String SHOW = "show";
    private final String TEAM = "team";
    private final String ENTER_BOARD =
            "enter menu board";

    private Team team = null;

    @Override
    public String text() {
        showTeams();
        return WELCOME_MESSAGE;
    }

    @Override
    public void showHelp() {
        ConsoleHelper.getInstance()
                .join(ENTER_TEAM_COMMAND)
                .printAll();
    }

    @Override
    public void parse(ArgumentManager input) {
        if (input.isCommandIgnoreFlag(ENTER_TEAM)) {
            enterTeamPage(input.extractValueIgnoreFlag(ENTER_TEAM));
        }

        if (team != null) {
            if (input.isCommandFollowCommand(SCOREBOARD, SHOW)) {
                showScoreboard();
            } else if (input.isCommandFollowCommand(ROADMAP, SHOW)) {
                showRoadMap();
            } else if (input.isCommandFollowCommand(CHATROOM, SHOW)) {
                showChatRoom();
            } else if (input.isCommandFollowArg(SEND, MESSAGE)) {
                sendMessage(input);
            } else if (input.isCommand(SHOW_TASKS)) {
                showTasks();
            } else if (input.isCommandFollowArg(SHOW_TASK, ID)) {
                // Todo
            } else if (input.isCommand(ENTER_BOARD)) {
                SharedPreferences.add(TEAM, team);
                new BoardMenu().show();
            }
        }
    }


    private void enterTeamPage(String teamName) {
        Response response = TeamMenuController.getInstance().getTeam(teamName);
        ConsoleHelper.getInstance().println(response.getMessage());

        if (response.isSuccess())
            this.team = (Team) response.getObject();
    }

    private void showScoreboard() {
        Response response = TeamMenuController.getInstance()
                .getScoreboard(team);
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void showRoadMap() {
        Response response = TeamMenuController.getInstance()
                .getRoadmap(team);
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void showChatRoom() {
        Response response = TeamMenuController.getInstance()
                .getMessages(team);
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void sendMessage(ArgumentManager input) {
        TeamMenuController.getInstance()
                .sendMessage(team, input.get(MESSAGE));
    }

    private void showTasks() {
        Response response = TeamMenuController.getInstance()
                .showTasks(team);
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void showTeams() {
        Response response = ProfileMenuController.getInstance()
                .showTeams();
        ConsoleHelper.getInstance().println(response.getMessage());
    }
}
