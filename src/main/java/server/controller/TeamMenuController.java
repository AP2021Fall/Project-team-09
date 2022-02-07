package server.controller;

import server.model.Notification;
import server.model.Task;
import server.model.Team;
import server.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;

public class TeamMenuController {

    private final String SUCCESS_FOUND_TEAM =
            "Entering team page...";
    private final String SUCCESS_TEAM_CREATED =
            "Team created successfully! Waiting For Admin’s confirmation…";
    private final String SUCCESS_TASK_CREATED =
            "Task created successfully!";
    private final String SUCCESS_USER_ADDED =
            "User added to the team successfully!";
    private final String SUCCESS_USER_DELETED =
            "User deleted successfully!";
    private final String SUCCESS_USER_SUSPENDED =
            "User suspended successfully!";
    private final String SUCCESS_USER_ACTIVATED =
            "User activated successfully!";
    private final String SUCCESS_MEMBER_ASSIGNED =
            "Member assigned successfully!";
    private final String SUCCESS_PROMOTED =
            "User promoted to team leader successfully!";
    private final String SUCCESS_USERS_RECEIVED =
            "Users received!";
    private final String SUCCESS =
            "Success!";

    private final String WARN_404_TEAM =
            "Team with name %s not found!";
    private final String WARN_404_TEAM_FOR_YOU =
            "There is no team for you!";
    private final String WARN_404_LEADER_TEAM =
            "Team not found!";
    private final String WARN_TEAM_EXISTS =
            "There is another team with this name!";
    private final String WARN_INVALID_NAME =
            "Team name is invalid!";
    private final String WARN_TITLE_INVALID =
            "Title is invalid!";
    private final String WARN_DUPLICATE_TITLE =
            "There is another task with this name!";
    private final String WARN_START_TIME_INVALID =
            "Invalid start time!";
    private final String WARN_DEADLINE_INVALID =
            "Invalid deadline!";
    private final String WARN_404_TEAM_MEMBER =
            "No team member yet";
    private final String WARN_404_USER =
            "No user exists with this username!";
    private final String WARN_USER_EXISTS =
            "User is already a member!";
    private final String WARN_ALREADY_SUSPENDED =
            "User is already suspended";
    private final String WARN_ALREADY_ACTIVATED =
            "User is already suspended";
    private final String WARN_TASK_ID_INVALID =
            "No task exists with this id!";
    private final String WARN_ALREADY_ASSIGNED =
            "Task is already assigned to this user!";
    private final String WARN_WAIT_CONFIRM =
            "Team is waiting for sysadmin confirmation!";
    private final String WARN_USER_SUSPENDED =
            "User is suspended!";
    private final String WARN_USER_TEAM_LEADER =
            "User is team leader!";
    private final String WARN_NOT_TEAM_LEADER =
            "You're not a team leader";
    private final String WARN_EMPTY_MESSAGE =
            "You cannot send empty message!";

    private final String TEAM_NAME_REGEXP =
            "^(?=.{5,12}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$";

    private static TeamMenuController teamMenuController = null;

    public static TeamMenuController getInstance() {
        if (teamMenuController == null)
            teamMenuController = new TeamMenuController();
        return teamMenuController;
    }

    public MResponse getScoreboard(Team team) {
        return new MResponse(team.getScoreboard(), true);
    }

    public MResponse getRoadmapFormatted(Team team) {
        return new MResponse(team.getRoadmapFormatted(), true);
    }

    public MResponse getRoadmap(Team team) {
        return new MResponse(SUCCESS, true, team.getRoadmap());
    }

    public MResponse getMessagesFormatted(Team team) {
        return new MResponse(team.getMessagesFormatted(), true);
    }

    public MResponse getMessages(Team team) {
        return new MResponse(SUCCESS, true, team.getMessages());
    }

    public MResponse getTeam(String teamName) {
        Team team = UserController.getLoggedUser().getMyTeam(teamName);

        if (team == null)
            return new MResponse(String.format(WARN_404_TEAM, teamName), false);

        if (team.isPending())
            return new MResponse(WARN_WAIT_CONFIRM, false);

        return new MResponse(SUCCESS_FOUND_TEAM, true, team);
    }

    public MResponse sendMessage(Team team, String body) {
        User user = UserController.getLoggedUser();

        if (body.isEmpty())
            return new MResponse(WARN_EMPTY_MESSAGE, false);
        if (user.isTeamLeader())
            team.sendNotification(new Notification(user, team, "Team leader has sent a message in chatroom!"));
        team.sendMessage(user, body);
        return new MResponse(SUCCESS, true);
    }

    public MResponse showTasks(Team team) {
        return new MResponse(team.getTasksFormatted(), true);
    }

    public MResponse showTask(Team team, String taskId) {
        int id;
        try {
            id = Integer.parseInt(taskId);
        } catch (Exception e) {
            return new MResponse(WARN_TASK_ID_INVALID, false);
        }

        Task task = team.getTaskById(id);

        if (task == null)
            return new MResponse(WARN_TASK_ID_INVALID, false);

        return new MResponse(task.toString(), true);
    }

    public MResponse getLeaderTeams() {
        User leader = UserController.getLoggedUser();

        ArrayList<Team> teams = leader.getMyTeams();

        if (teams.isEmpty())
            return new MResponse(WARN_404_TEAM_FOR_YOU, false);

        teams.sort(new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                LocalDateTime date1 = o1.getTimeOfCreation();
                LocalDateTime date2 = o2.getTimeOfCreation();

                return date1.compareTo(date2);
            }
        });

        StringBuilder result = new StringBuilder();

        int index = 1;

        for (Team team : teams)
            result.append(String.format("%d- %s", index++, team.getName()));

        return new MResponse(result.toString(), true);
    }

    public MResponse getLeaderTeam(String teamName) {
        User leader = UserController.getLoggedUser();
        Team team = leader.getMyTeam(teamName);

        if (team == null)
            return new MResponse(WARN_404_LEADER_TEAM, false);
        return new MResponse(SUCCESS_FOUND_TEAM, true, team);
    }

    public MResponse createTeam(String teamName) {
        Team team = Team.getTeamByName(teamName);

        if (!UserController.getLoggedUser().isTeamLeader())
            return new MResponse(WARN_NOT_TEAM_LEADER, false);

        if (team != null)
            return new MResponse(WARN_TEAM_EXISTS, false);

        if (teamName.matches(TEAM_NAME_REGEXP))
            return new MResponse(WARN_INVALID_NAME, false);

        Team.createTeam(teamName, UserController.getLoggedUser());

        return new MResponse(SUCCESS_TEAM_CREATED, true);
    }

    public MResponse getAllTasks(Team team) {
        return new MResponse(team.getTasksFullInfoFormatted(), true);
    }

    public MResponse createTask(Team team, String title,
                                String startTime, String deadline) {

        if (title.isEmpty())
            return new MResponse(WARN_TITLE_INVALID, false);

        Task task = team.getTaskByTitle(title);

        if (task != null)
            return new MResponse(WARN_DUPLICATE_TITLE, false);

        LocalDateTime start = isTimeValid(startTime);
        LocalDateTime dead = isTimeValid(deadline);

        if (start == null)
            return new MResponse(WARN_START_TIME_INVALID, false);

        if (dead == null)
            return new MResponse(WARN_DEADLINE_INVALID, false);

        if (dead.isBefore(start))
            return new MResponse(WARN_DEADLINE_INVALID, false);

        team.createTask(title, start, dead);
        return new MResponse(SUCCESS_TASK_CREATED, true);
    }

    public MResponse createTask(Team team, String title,
                                String priority, String startTime, String deadline, String description) {

        if (title.isEmpty())
            return new MResponse(WARN_TITLE_INVALID, false);

        Task task = team.getTaskByTitle(title);

        if (task != null)
            return new MResponse(WARN_DUPLICATE_TITLE, false);

        LocalDateTime start = isTimeValid(startTime);
        LocalDateTime dead = isTimeValid(deadline);

        if (start == null)
            return new MResponse(WARN_START_TIME_INVALID, false);

        if (dead == null)
            return new MResponse(WARN_DEADLINE_INVALID, false);

        if (dead.isBefore(start))
            return new MResponse(WARN_DEADLINE_INVALID, false);

        Task t = team.createTask(title, priority, start, dead, description);
        return new MResponse(SUCCESS_TASK_CREATED, true, t);
    }

    private LocalDateTime isTimeValid(String time) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");

        try {
            LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
            if (dateTime.isBefore(now))
                return null;

            return dateTime;
        } catch (DateTimeParseException exception) {
            return null;
        }
    }

    public MResponse getMembers(Team team) {
        ArrayList<User> teamMembers = team.getMembers();

        if (teamMembers.isEmpty())
            return new MResponse(WARN_404_TEAM_MEMBER, false);

        StringBuilder result = new StringBuilder();

        int index = 1;

        for (User user : teamMembers)
            result.append(String.format("%d- %s", index++, user.getUsername())).append("\n");

        return new MResponse(result.toString(), true, teamMembers);
    }

    public MResponse addMemberToTeam(Team team, String username) {
        User user = User.getUser(username);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (team.hasMember(user))
            return new MResponse(WARN_USER_EXISTS, false);

        if (team.getLeader().getUsername().equalsIgnoreCase(username))
            return new MResponse(WARN_USER_TEAM_LEADER, false);

        team.addMember(user);
        System.out.println("this is the team to be updated");
        System.out.println(team.getName());
        System.out.println(team.getMembers());
        System.out.println(team.getMembersPoints());
        user.sendNotification(new Notification(UserController.getLoggedUser(),
                team, String.format("You have been added to \"%s\" team!", team.getName())));
        SaveAndLoadController.save();
        return new MResponse(SUCCESS_USER_ADDED, true);
    }

    public MResponse deleteMember(Team team, String username) {
        User user = User.getUser(username);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (!team.hasMember(user))
            return new MResponse(WARN_404_USER, false);

        if (team.getLeader().getUsername().equalsIgnoreCase(username))
            return new MResponse(WARN_USER_TEAM_LEADER, false);

        team.deleteMember(user);
        user.sendNotification(new Notification(UserController.getLoggedUser(),
                team, String.format("You have been deleted from \"%s\" team!", team.getName())));
        return new MResponse(SUCCESS_USER_DELETED, true);
    }

    public MResponse suspendMember(Team team, String username) {
        User user = User.getUser(username);
        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (!team.hasMember(user))
            return new MResponse(WARN_404_USER, false);

        if (team.isSuspended(user))
            return new MResponse(WARN_ALREADY_SUSPENDED, false);

        if (team.getLeader().getUsername().equalsIgnoreCase(username))
            return new MResponse(WARN_USER_TEAM_LEADER, false);

        team.suspendMember(user);
        user.sendNotification(new Notification(UserController.getLoggedUser(),
                team, String.format("You have been suspended from \"%s\" team!", team.getName())));
        return new MResponse(SUCCESS_USER_SUSPENDED, true);
    }

    public MResponse activateMember(Team team, String username) {
        User user = User.getUser(username);
        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (!team.hasMember(user))
            return new MResponse(WARN_404_USER, false);

        if (!team.isSuspended(user))
            return new MResponse(WARN_ALREADY_ACTIVATED, false);

        if (team.getLeader().getUsername().equalsIgnoreCase(username))
            return new MResponse(WARN_USER_TEAM_LEADER, false);

        team.activateMember(user);
        user.sendNotification(new Notification(UserController.getLoggedUser(),
                team, String.format("You have been activated in \"%s\" team!", team.getName())));
        return new MResponse(SUCCESS_USER_ACTIVATED, true);
    }

    public MResponse promoteUser(Team team, String username, String rate) {
        User user = User.getUser(username);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (!team.hasMember(user))
            return new MResponse(WARN_404_USER, false);

        if (team.isSuspended(user))
            return new MResponse(WARN_USER_SUSPENDED, false);

        if (team.getLeader().getUsername().equalsIgnoreCase(username))
            return new MResponse(WARN_USER_TEAM_LEADER, false);

        user.setType("team leader");
        team.setLeader(user);
        UserController.getLoggedUser().setType("teamMember");
        user.sendNotification(new Notification(UserController.getLoggedUser(),
                team, String.format("You have been promoted to \"%s\" in \"%s\" team!", user.getType().name(), team.getName())));
        return new MResponse(SUCCESS_PROMOTED, true);
    }

    public MResponse assignToTask(Team team, String taskId, String username) {
        User user = User.getUser(username);

        if (user == null)
            return new MResponse(WARN_404_USER, false);

        if (!team.hasMember(user))
            return new MResponse(WARN_404_USER, false);

        if (team.isSuspended(user))
            return new MResponse(WARN_USER_SUSPENDED, false);

        if (team.getLeader().getUsername().equalsIgnoreCase(username))
            return new MResponse(WARN_USER_TEAM_LEADER, false);

        int tId;
        try {
            tId = Integer.parseInt(taskId);
        } catch (Exception exception) {
            return new MResponse(WARN_TASK_ID_INVALID, false);
        }

        Task task = team.getTaskById(tId);

        if (task == null)
            return new MResponse(WARN_TASK_ID_INVALID, false);

        if (task.isInAssignedUsers(username) != null)
            return new MResponse(WARN_ALREADY_ASSIGNED, false);

        task.assignUser(user);
        team.sendNotification(new Notification(team.getLeader(), team,
                String.format("\"%s\" has been assigned to \"%s\" task", user.getUsername(), task.getTitle())));
        return new MResponse(SUCCESS_MEMBER_ASSIGNED, true);
    }

    public MResponse getAllUsers(Team team) {

        ArrayList<User> users = new ArrayList<>();

        for (User user : User.getAllUsers()) {
            if (!team.hasMember(user))
                if (!user.isTeamLeader())
                    users.add(user);
        }

        return new MResponse(SUCCESS_USERS_RECEIVED, true, users);
    }
}
