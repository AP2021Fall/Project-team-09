package controller;

import model.Team;
import model.User;

import java.util.ArrayList;
import java.util.Comparator;

public class AdminController {

    private final String SUCCESS_USER_BANED =
            "User removed successfully";
    private final String SUCCESS_ROLE_CHANGED =
            "User role changed to %s successfully";
    private final String SUCCESS_SET_ACCEPTED =
            "All teams status set to accepted!";

    private final String WARN_404_USER =
            "There is no user with this username!";
    private final String WARN_WRONG_ROLE =
            "Wrong role name!";
    private final String WARN_404_PENDING =
            "There are no team in pending status!";
    private final String WARN_404_TEAM =
            "%s team is not available!";
    private final String WARN_NOT_PENDING =
            "Some teams are not in pending status!";

    private static AdminController controller = null;

    public static AdminController getInstance() {
        if (controller == null)
            controller = new AdminController();
        return controller;
    }

    public Response getProfile(String username) {
        User user = User.getUser(username);

        if (user == null)
            return new Response(WARN_404_USER, false);

        return new Response(user.toString(), true, user);
    }

    public Response banUser(String username) {
        User user = User.getUser(username);

        if (user == null)
            return new Response(WARN_404_USER, false);

        User.removeUser(user);
        return new Response(SUCCESS_USER_BANED, true);
    }

    public Response changeRole(String username, String role) {
        User user = User.getUser(username);

        if (user == null)
            return new Response(WARN_404_USER, false);

        if (user.setType(role)) {
            return new Response(String.format(SUCCESS_ROLE_CHANGED, role), true);
        } else {
            return new Response(WARN_WRONG_ROLE, false);
        }
    }

    public Response getPendingTeams() {
        ArrayList<Team> pending = Team.getPendingTeams();

        if (pending.isEmpty())
            return new Response(WARN_404_PENDING, false);

        pending.sort(new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                return o1.getTimeOfCreation().compareTo(o2.getTimeOfCreation());
            }
        });

        StringBuilder result = new StringBuilder();

        for (Team team : pending)
            result.append(team.getName()).append("\n");
        return new Response(result.toString(), true, pending);
    }


    public Response acceptPendingTeams(String[] teams) {
        ArrayList<Team> acceptedTeams = new ArrayList<>();
        for (String team : teams) {
            Team theTeam = Team.getTeamByName(team);
            if (theTeam == null)
                return new Response(String.format(WARN_404_TEAM, team), false);
            if (!theTeam.isPending())
                return new Response(WARN_NOT_PENDING, false);
            acceptedTeams.add(theTeam);
        }
        for (Team team : acceptedTeams)
            team.accept();
        return new Response(SUCCESS_SET_ACCEPTED, true);
    }

    public Response rejectPendingTeams(String[] teams) {
        ArrayList<Team> rejectedTeams = new ArrayList<>();
        for (String team : teams) {
            Team theTeam = Team.getTeamByName(team);
            if (theTeam == null)
                return new Response(String.format(WARN_404_TEAM, team), false);
            if (!theTeam.isPending())
                return new Response(WARN_NOT_PENDING, false);
            rejectedTeams.add(theTeam);
        }
        for (Team team : rejectedTeams)
            team.reject();
        return new Response(SUCCESS_SET_ACCEPTED, true);
    }
}
