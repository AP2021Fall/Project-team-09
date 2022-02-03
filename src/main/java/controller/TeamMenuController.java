package controller;

import model.MRequest;
import model.Team;

public class TeamMenuController {

    private static final String GET_SCOREBOARD_PATH = "/team/scoreboard";
    private static final String GET_ROADMAP_FRM_PATH = "/team/roadmap-frm";
    private static final String GET_ROADMAP_PATH = "/team/roadmap";
    private static final String GET_MESSAGES_FRM_PATH = "/team/messages-frm";
    private static final String GET_MESSAGES_PATH = "/team/messages";
    private static final String GET_TEAM_PATH = "/team/get-team";
    private static final String SEND_MESSAGE_PATH = "/team/send-message";
    private static final String SHOW_TASKS_PATH = "/team/show-tasks";
    private static final String SHOW_TASK_PATH = "/team/show-task";
    private static final String GET_LEADER_TEAMS_PATH = "/team/get-leader-teams";
    private static final String GET_LEADER_TEAM_PATH = "/team/get-leader-team";
    private static final String CREATE_TEAM_PATH = "/team/create-team";
    private static final String GET_ALL_TASKS_PATH = "/team/get-all-tasks";
    private static final String CREATE_TASK_PATH = "/team/create-task";
    private static final String CREATE_TASK_EXT_PATH = "/team/create-task-ext";
    private static final String GET_MEMBERS_PATH = "/team/get-members";
    private static final String ADD_MEMBER_PATH = "/team/add-member";
    private static final String DELETE_MEMBER_PATH = "/team/delete-member";
    private static final String SUSPEND_MEMBER_PATH = "/team/suspend-member";
    private static final String PROMOTE_USER_PATH = "/team/promote-user";
    private static final String ASSIGN_T0_TASK_PATH = "/team/assign-to-task";
    private static final String GET_ALL_USERS_PATH = "/team/get-all-users";

    private static final String TEAM = "team";
    private static final String TASK_ID = "task_id";
    private static final String BODY = "body";
    private static final String TEAM_NAME = "team_name";
    private static final String TITLE = "title";
    private static final String START_TIME = "start_time";
    private static final String DEADLINE = "deadline";
    private static final String PRIORITY = "priority";
    private static final String DESCRIPTION = "description";
    private static final String USERNAME = "username";
    private static final String RATE = "rate";

    private static TeamMenuController teamMenuController = null;

    public static TeamMenuController getInstance() {
        if (teamMenuController == null)
            teamMenuController = new TeamMenuController();
        return teamMenuController;
    }

    public MResponse getScoreboard(Team team) {
        return new MRequest()
                .setPath(GET_SCOREBOARD_PATH)
                .addArg(TEAM, team)
                .get();
    }

    public MResponse getRoadmapFormatted(Team team) {
        return new MRequest()
                .setPath(GET_ROADMAP_FRM_PATH)
                .addArg(TEAM, team)
                .get();
    }

    public MResponse getRoadmap(Team team) {
        return new MRequest()
                .setPath(GET_ROADMAP_PATH)
                .addArg(TEAM, team)
                .get();
    }

    public MResponse getMessagesFormatted(Team team) {
        return new MRequest()
                .setPath(GET_MESSAGES_FRM_PATH)
                .addArg(TEAM, team)
                .get();
    }

    public MResponse getMessages(Team team) {
        return new MRequest()
                .setPath(GET_MESSAGES_PATH)
                .addArg(TEAM, team)
                .get();
    }

    public MResponse getTeam(String teamName) {
        return new MRequest()
                .setPath(GET_TEAM_PATH)
                .addArg(TEAM_NAME, teamName)
                .get();
    }

    public MResponse sendMessage(Team team, String body) {
        return new MRequest()
                .setPath(SEND_MESSAGE_PATH)
                .addArg(TEAM, team)
                .addArg(BODY, body)
                .put();
    }

    public MResponse showTasks(Team team) {
        return new MRequest()
                .setPath(SHOW_TASKS_PATH)
                .addArg(TEAM, team)
                .get();
    }

    public MResponse showTask(Team team, String taskId) {
        return new MRequest()
                .setPath(SHOW_TASK_PATH)
                .addArg(TEAM, team)
                .addArg(TASK_ID, taskId)
                .get();
    }

    public MResponse getLeaderTeams() {
        return new MRequest()
                .setPath(GET_LEADER_TEAMS_PATH)
                .get();
    }

    public MResponse getLeaderTeam(String teamName) {
        return new MRequest()
                .setPath(GET_LEADER_TEAM_PATH)
                .addArg(TEAM_NAME, teamName)
                .get();
    }

    public MResponse createTeam(String teamName) {
        return new MRequest()
                .setPath(CREATE_TEAM_PATH)
                .addArg(TEAM_NAME, teamName)
                .put();
    }

    public MResponse getAllTasks(Team team) {
        return new MRequest()
                .setPath(GET_ALL_TASKS_PATH)
                .addArg(TEAM, team)
                .get();
    }

    public MResponse createTask(Team team, String title,
                                String startTime, String deadline) {
        return new MRequest()
                .setPath(CREATE_TASK_PATH)
                .addArg(TEAM, team)
                .addArg(TITLE, title)
                .addArg(START_TIME, startTime)
                .addArg(DEADLINE, deadline)
                .put();
    }

    public MResponse createTask(Team team, String title,
                                String priority, String startTime, String deadline, String description) {
        return new MRequest()
                .setPath(CREATE_TASK_EXT_PATH)
                .addArg(TEAM, team)
                .addArg(TITLE, title)
                .addArg(PRIORITY, priority)
                .addArg(START_TIME, startTime)
                .addArg(DEADLINE, deadline)
                .addArg(DESCRIPTION, description)
                .put();
    }

    public MResponse getMembers(Team team) {
        return new MRequest()
                .setPath(GET_MEMBERS_PATH)
                .addArg(TEAM, team)
                .get();
    }

    public MResponse addMemberToTeam(Team team, String username) {
        return new MRequest()
                .setPath(ADD_MEMBER_PATH)
                .addArg(TEAM, team)
                .addArg(USERNAME, username)
                .put();
    }

    public MResponse deleteMember(Team team, String username) {
        return new MRequest()
                .setPath(DELETE_MEMBER_PATH)
                .addArg(TEAM, team)
                .addArg(USERNAME, username)
                .delete();
    }

    public MResponse suspendMember(Team team, String username) {
        return new MRequest()
                .setPath(SUSPEND_MEMBER_PATH)
                .addArg(TEAM, team)
                .addArg(USERNAME, username)
                .patch();
    }

    public MResponse promoteUser(Team team, String username, String rate) {
        return new MRequest()
                .setPath(PROMOTE_USER_PATH)
                .addArg(TEAM, team)
                .addArg(USERNAME, username)
                .addArg(RATE, rate)
                .patch();
    }

    public MResponse assignToTask(Team team, String taskId, String username) {
        return new MRequest()
                .setPath(ASSIGN_T0_TASK_PATH)
                .addArg(TEAM, team)
                .addArg(TASK_ID, taskId)
                .addArg(USERNAME, username)
                .put();
    }

    public MResponse getAllUsers(Team team) {
        return new MRequest()
                .setPath(GET_ALL_USERS_PATH)
                .addArg(TEAM, team)
                .get();
    }
}