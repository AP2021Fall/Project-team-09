package controller;

import model.Task;
import model.Team;
import model.User;

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
    private final String SUCCESS_MEMBER_ASSIGNED =
            "Member assigned successfully!";
    private final String SUCCESS_PROMOTED =
            "User promoted to team leader successfully!";

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

    private final String TEAM_NAME_REGEXP =
            "^(?=.{5,12}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$";

    private static TeamMenuController teamMenuController = null;

    public static TeamMenuController getInstance() {
        if (teamMenuController == null)
            teamMenuController = new TeamMenuController();
        return teamMenuController;
    }

    public Response getScoreboard(Team team) {
        return new Response(team.getScoreboard(), true);
    }

    public Response getRoadmap(Team team) {
        return new Response(team.getRoadmap(), true);
    }

    public Response getMessages(Team team) {
        return new Response(team.getMessages(), true);
    }

    public Response getTeam(String teamName) {
        Team team = UserController.getLoggedUser().getMyTeam(teamName);

        if (team == null)
            return new Response(String.format(WARN_404_TEAM, teamName), false);

        if (team.isPending())
            return new Response(WARN_WAIT_CONFIRM, false);

        return new Response(SUCCESS_FOUND_TEAM, true, team);
    }

    public void sendMessage(Team team, String body) {
        User user = UserController.getLoggedUser();

        team.sendMessage(user, body);
    }

    public Response showTasks(Team team) {
        return new Response(team.getTasksFormatted(), true);
    }

    public Response showTask(Team team, String taskId) {
        int id;
        try {
            id = Integer.parseInt(taskId);
        } catch (Exception e) {
            return new Response(WARN_TASK_ID_INVALID, false);
        }

        Task task = team.getTaskById(id);

        if (task == null)
            return new Response(WARN_TASK_ID_INVALID, false);

        return new Response(task.toString(), true);
    }

    public Response getLeaderTeams() {
        User leader = UserController.getLoggedUser();

        ArrayList<Team> teams = leader.getMyTeams();

        if (teams.isEmpty())
            return new Response(WARN_404_TEAM_FOR_YOU, false);

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

        return new Response(result.toString(), true);
    }

    public Response getLeaderTeam(String teamName) {
        User leader = UserController.getLoggedUser();
        Team team = leader.getMyTeam(teamName);

        if (team == null)
            return new Response(WARN_404_LEADER_TEAM, false);
        return new Response(SUCCESS_FOUND_TEAM, true, team);
    }

    public Response createTeam(String teamName) {
        Team team = Team.getTeamByName(teamName);

        if (team != null)
            return new Response(WARN_TEAM_EXISTS, false);

        if (teamName.matches(TEAM_NAME_REGEXP))
            return new Response(WARN_INVALID_NAME, false);

        Team.createTeam(teamName, UserController.getLoggedUser());

        return new Response(SUCCESS_TEAM_CREATED, true);
    }

    public Response getAllTasks(Team team) {
        return new Response(team.getTasksFullInfoFormatted(), true);
    }

    public Response createTask(Team team, String title,
                               String startTime, String deadline) {

        if (title.isEmpty())
            return new Response(WARN_TITLE_INVALID, false);

        Task task = team.getTaskByTitle(title);

        if (task != null)
            return new Response(WARN_DUPLICATE_TITLE, false);

        LocalDateTime start = isTimeValid(startTime);
        LocalDateTime dead = isTimeValid(deadline);

        if (start == null)
            return new Response(WARN_START_TIME_INVALID, false);

        if (dead == null)
            return new Response(WARN_DEADLINE_INVALID, false);

        if (dead.isBefore(start))
            return new Response(WARN_DEADLINE_INVALID, false);

        team.createTask(title, start, dead);
        return new Response(SUCCESS_TASK_CREATED, true);
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

    public Response getMembers(Team team) {
        ArrayList<User> teamMembers = team.getMembers();

        if (teamMembers.isEmpty())
            return new Response(WARN_404_TEAM_MEMBER, false);

        StringBuilder result = new StringBuilder();

        int index = 1;

        for (User user : teamMembers)
            result.append(String.format("%d- %s", index++, user.getUsername())).append("\n");

        return new Response(result.toString(), true);
    }

    public Response addMemberToTeam(Team team, String username) {
        User user = User.getUser(username);

        if (user == null)
            return new Response(WARN_404_USER, false);

        if (team.hasMember(user))
            return new Response(WARN_USER_EXISTS, false);

        if (team.getLeader().getUsername().equalsIgnoreCase(username))
            return new Response(WARN_USER_TEAM_LEADER, false);

        team.addMember(user);
        return new Response(SUCCESS_USER_ADDED, true);
    }

    public Response deleteMember(Team team, String username) {
        User user = User.getUser(username);

        if (user == null)
            return new Response(WARN_404_USER, false);

        if (!team.hasMember(user))
            return new Response(WARN_404_USER, false);

        if (team.getLeader().getUsername().equalsIgnoreCase(username))
            return new Response(WARN_USER_TEAM_LEADER, false);

        team.deleteMember(user);
        return new Response(SUCCESS_USER_DELETED, true);
    }

    public Response suspendMember(Team team, String username) {
        User user = User.getUser(username);
        if (user == null)
            return new Response(WARN_404_USER, false);

        if (!team.hasMember(user))
            return new Response(WARN_404_USER, false);

        if (team.isSuspended(user))
            return new Response(WARN_ALREADY_SUSPENDED, false);

        if (team.getLeader().getUsername().equalsIgnoreCase(username))
            return new Response(WARN_USER_TEAM_LEADER, false);

        team.suspendMember(user);
        return new Response(SUCCESS_USER_SUSPENDED, true);
    }

    public Response promoteUser(Team team, String username, String rate) {
        User user = User.getUser(username);

        if (user == null)
            return new Response(WARN_404_USER, false);

        if (!team.hasMember(user))
            return new Response(WARN_404_USER, false);

        if (team.isSuspended(user))
            return new Response(WARN_USER_SUSPENDED, false);

        if (team.getLeader().getUsername().equalsIgnoreCase(username))
            return new Response(WARN_USER_TEAM_LEADER, false);

        user.setType("team leader");
        team.setLeader(user);
        return new Response(SUCCESS_PROMOTED, true);
    }

    public Response assignToTask(Team team, String taskId, String username) {
        User user = User.getUser(username);

        if (user == null)
            return new Response(WARN_404_USER, false);

        if (!team.hasMember(user))
            return new Response(WARN_404_USER, false);

        if (team.isSuspended(user))
            return new Response(WARN_USER_SUSPENDED, false);

        if (team.getLeader().getUsername().equalsIgnoreCase(username))
            return new Response(WARN_USER_TEAM_LEADER, false);

        int tId;
        try {
            tId = Integer.parseInt(taskId);
        } catch (Exception exception) {
            return new Response(WARN_TASK_ID_INVALID, false);
        }

        Task task = team.getTaskById(tId);

        if (task == null)
            return new Response(WARN_TASK_ID_INVALID, false);

        if (task.isInAssignedUsers(username) != null)
            return new Response(WARN_ALREADY_ASSIGNED, false);

        task.assignUser(user);
        return new Response(SUCCESS_MEMBER_ASSIGNED, true);
    }
}
