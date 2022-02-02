package terminal_view;

import controller.ProfileMenuController;
import controller.MResponse;
import controller.TeamMenuController;
import controller.UserController;
import exceptions.IllegalCommandException;
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
    private final String SHOW_ROADMAP_COMMAND =
            "roadmap --show";
    private final String SHOW_CHATROOM_COMMAND =
            "chatroom --show";
    private final String SHOW_TASKS_COMMAND =
            "show tasks";
    private final String SHOW_TASK_COMMAND =
            "show task --id [task id]";
    private final String SHOW_TEAMS_COMMAND =
            "show --teams";
    private final String SHOW_TEAM_COMMAND =
            "show --team <team name>";
    private final String CREATE_TEAM_COMMAND =
            "create --team <team name>";
    private final String SUDO_SHOW_COMMAND =
            "sudo show --all --tasks";
    private final String CREATE_TASK_COMMAND =
            "create task --title <task title> --starttime <start time> " +
                    "--deadline <deadline>";
    private final String SHOW_MEMBERS_COMMAND =
            "show --members";
    private final String ADD_MEMBER_COMMAND =
            "add member --username <username>";
    private final String DELETE_MEMBER_COMMAND =
            "delete member --username <username>";
    private final String SUSPEND_MEMBER_COMMAND =
            "suspend member --username <username>";
    private final String PROMOTE_USER_COMMAND =
            "promote --username --rate <rate>";
    private final String ASSIGN_MEMBER_COMMAND =
            "assign member --task <task id> --username <username>";
    private final String SEND_NOTIF_USER_COMMAND =
            "send --notification [notification] --username [username]";
    private final String SEND_NOTIF_TEAM_COMMAND =
            "send --notification [notification] --team [team name]";

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
    private final String PROMOTE =
            "promote";
    private final String RATE =
            "rate";
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
                .join(SHOW_SCORE_COMMAND)
                .join(SHOW_ROADMAP_COMMAND)
                .join(SHOW_CHATROOM_COMMAND)
                .join(SHOW_TASKS_COMMAND)
                .join(SHOW_TASK_COMMAND)
                .join(SHOW_TEAMS_COMMAND)
                .join(SHOW_TEAM_COMMAND)
                .join(CREATE_TEAM_COMMAND)
                .join(SUDO_SHOW_COMMAND)
                .join(CREATE_TASK_COMMAND)
                .join(SHOW_MEMBERS_COMMAND)
                .join(ADD_MEMBER_COMMAND)
                .join(DELETE_MEMBER_COMMAND)
                .join(SUSPEND_MEMBER_COMMAND)
                .join(PROMOTE_USER_COMMAND)
                .join(ASSIGN_MEMBER_COMMAND)
                .join(SEND_NOTIF_USER_COMMAND)
                .join(SEND_NOTIF_TEAM_COMMAND)
                .printAll();
    }

    @Override
    public void parse(ArgumentManager input) {
        if (input.isCommandIgnoreFlag(ENTER_TEAM)) {
            enterTeamPage(input.extractValueIgnoreFlag(ENTER_TEAM));
        }

        User user = UserController.getLoggedUser();

        if (user.isTeamLeader()) {
            if (input.isCommandFollowArg(SHOW, TEAMS)) {
                showLeaderTeams();
            } else if (input.isCommandFollowArg(SHOW, TEAM)) {
                showTheTeam(input.get(TEAM));
            } else if (input.isCommandFollowArg(CREATE, TEAM)) {
                createTeam(input.get(TEAM));
            }
        }

        if (team != null) {

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
                } else if(input.isCommandFollowArg(PROMOTE, USERNAME, RATE)){
                    promoteMember(input);
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
                showTask(input);
            } else if (input.isCommand(ENTER_BOARD)) {
                SharedPreferences.add(TEAM, team);
                new BoardMenu().show();
            }
        }
    }

    private void showAllTasks() {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .getAllTasks(team);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void createTask(ArgumentManager input) {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .createTask(team, input.get(TITLE), input.get(START_TIME), input.get(DEADLINE));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showMembers() {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .getMembers(team);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void addMemberToTeam(ArgumentManager input) {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .addMemberToTeam(team, input.get(USERNAME));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void deleteMember(ArgumentManager input) {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .deleteMember(team, input.get(USERNAME));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void suspendMember(ArgumentManager input) {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .suspendMember(team, input.get(USERNAME));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void promoteMember(ArgumentManager input) {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .promoteUser(team, input.get(USERNAME), input.get(RATE));
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void assignUserToTask(ArgumentManager input) {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .assignToTask(team, input.get(TASK), input.get(USERNAME));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void enterTeamPage(String teamName) {
        try {
            MResponse MResponse = TeamMenuController.getInstance().getTeam(teamName);
            ConsoleHelper.getInstance().println(MResponse.getMessage());

            if (MResponse.isSuccess())
                this.team = (Team) MResponse.getObject();
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showScoreboard() {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .getScoreboard(team);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showRoadMap() {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .getRoadmapFormatted(team);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showChatRoom() {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .getMessagesFormatted(team);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void sendMessage(ArgumentManager input) {
        try {
            TeamMenuController.getInstance()
                    .sendMessage(team, input.get(MESSAGE));
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showTasks() {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .showTasks(team);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showTask(ArgumentManager input) {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .showTask(team, input.get(ID));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    private void showTeams() {
        try {
            MResponse MResponse = ProfileMenuController.getInstance()
                    .showTeams();
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    public void showLeaderTeams() {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .getLeaderTeams();
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    public void showTheTeam(String teamName) {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .getLeaderTeam(teamName);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
            if (MResponse.isSuccess()) {
                this.team = (Team) MResponse.getObject();
                show();
            }
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }

    public void createTeam(String teamName) {
        try {
            MResponse MResponse = TeamMenuController.getInstance()
                    .createTeam(teamName);
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }
}
