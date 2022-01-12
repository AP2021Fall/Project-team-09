package controller;

import model.Team;
import model.User;

public class TeamMenuController {

    private final String SUCCESS_FOUND_TEAM =
            "Entering team page...";

    private final String WARN_404_TEAM =
            "Team with name %s not found!";

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
        Team team = Team.getTeamByName(teamName);

        if (team == null)
            return new Response(String.format(WARN_404_TEAM, teamName), false);

        return new Response(SUCCESS_FOUND_TEAM, true, team);
    }

    public void sendMessage(Team team, String body) {
        User user = UserController.getLoggedUser();

        team.sendMessage(user, body);
    }

    public Response showTasks(Team team) {
        return new Response(team.getTasksFormatted(), true);
    }
}
