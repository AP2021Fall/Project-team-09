package model;

public class Notification {

    private User user;
    private Team team;
    private String body;

    public Notification(User user, Team team, String body) {
        this.user = user;
        this.team = team;
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "user=" + user.getUsername() +
                ", team=" + team +
                ", body='" + body + '\'';
    }
}
