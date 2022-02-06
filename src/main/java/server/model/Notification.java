package server.model;

import java.io.Serializable;

public class Notification implements Serializable {

    private String user;
    private String team;
    private String body;

    public Notification(User user, Team team, String body) {
        this.user = user.getUsername();
        if (team != null)
            this.team = team.getName();
        else this.team = null;
        this.body = body;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user.getUsername();
    }

    public Team getTeam() {
        return Team.getTeamByName(this.team);
    }

    public void setTeam(Team team) {
        this.team = team.getName();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "user=" + user +
                ", team=" + team +
                ", body='" + body + '\'';
    }
}
