package terminal_view;

import controller.ProfileMenuController;
import controller.Response;
import controller.TeamMenuController;
import controller.UserController;
import model.Team;
import model.User;
import utilities.ConsoleHelper;
import utilities.SharedPreferences;

public class TeamMenu implements TerminalView {

    private final String WELCOME_MESSAGE =
            "Welcome to team menu";

    private final String ENTER_TEAM_COMMAND =
            "enter team <teamName>";
    private final String SHOW_SCORE_COMMAND =
            "scoreboard --show";

    private final String SUCCESS_TASK_CREATED =
            "Task created successfully!";

    private final String WARN_DUPLICATE_TITLE =
            "There is another task with this title!";
    private final String WARN_INVALID_START_DATE =
            "Invalid start date!";
    private final String WARN_INVALID_DEADLINE =
            "Invalid deadline!";

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
    private final String SHOW =
            "show";
    private final String TEAM =
            "team";
    private final String SUDO_SHOW =
            "sudo show";
    private final String TASKS =
            "tasks";
    private final String TASK =
            "task";
    private final String ALL =
            "all";
    private final String CREATE_TASK =
            "create task";
    private final String TITLE =
            "title";
    private final String START_TIME =
            "startTime";
    private final String DEADLINE =
            "deadline";
    private final String MEMBERS =
            "members";
    private final String ADD_MEMBER =
            "add member";
    private final String USERNAME =
            "username";
    private final String DELETE_MEMBER =
            "delete member";
    private final String SUSPEND_MEMBER =
            "suspend member";
    private final String ASSIGN_MEMBER =
            "assign member";
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
            User user = UserController.getLoggedUser();
            if (user.isTeamLeader()) {
                if (input.isCommandFollowArg(SUDO_SHOW, ALL, TASKS)) {
                    showAllTasks();
                } else if (input.isCommandFollowArg(CREATE_TASK, TITLE, START_TIME, DEADLINE)) {
                    createTask(input);
                } else if (input.isCommandFollowArg(SHOW, MEMBERS)) {
                    showMembers();
                } else if (input.isCommandFollowArg(ADD_MEMBER, USERNAME)) {
                    addMemberToTeam(input);
                } else if (input.isCommandFollowArg(DELETE_MEMBER, USERNAME)) {
                    deleteMember(input);
                } else if (input.isCommandFollowArg(SUSPEND_MEMBER, USERNAME)) {
                    suspendMember(input);
                } else if (input.isCommandFollowArg(ASSIGN_MEMBER, TASK, USERNAME)) {
                    assignUserToTask(input);
                } else if (input.isCommandFollowArg(SHOW, SCOREBOARD)) {
                    showScoreboard();
                }
            }
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

    private void showAllTasks() {
        Response response = TeamMenuController.getInstance()
                .getAllTasks(team);
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void createTask(ArgumentManager input) {
        Response response = TeamMenuController.getInstance()
                .createTask(team, input.get(TITLE), input.get(START_TIME), input.get(DEADLINE));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void showMembers() {
        Response response = TeamMenuController.getInstance()
                .getMembers(team);
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void addMemberToTeam(ArgumentManager input) {
        Response response = TeamMenuController.getInstance()
                .addMemberToTeam(team, input.get(USERNAME));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void deleteMember(ArgumentManager input) {
        Response response = TeamMenuController.getInstance()
                .deleteMember(team, input.get(USERNAME));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void suspendMember(ArgumentManager input) {
        Response response = TeamMenuController.getInstance()
                .suspendMember(team, input.get(USERNAME));
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    private void assignUserToTask(ArgumentManager input) {
        Response response = TeamMenuController.getInstance()
                .assignToTask(team, input.get(TASK), input.get(USERNAME));
        ConsoleHelper.getInstance().println(response.getMessage());
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

    public void showLeaderTeams() {
        Response response = TeamMenuController.getInstance()
                .getLeaderTeams();
        ConsoleHelper.getInstance().println(response.getMessage());
    }

    public void showTheTeam(String teamName) {
        Response response = TeamMenuController.getInstance()
                .getLeaderTeam(teamName);
        ConsoleHelper.getInstance().println(response.getMessage());
        if (response.isSuccess()) {
            this.team = (Team) response.getObject();
            show();
        }
    }

    public void createTeam(String teamName) {
        Response response = TeamMenuController.getInstance()
                .createTeam(teamName);
        ConsoleHelper.getInstance().println(response.getMessage());
    }
}
