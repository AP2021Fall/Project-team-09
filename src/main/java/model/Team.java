package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {
    private static ArrayList<Team> teams = new ArrayList<>();
    String name;
    User leader;

    public Team(String name , User leader) {
        this.name=name;
        this.leader=leader;
    }


    public static Team getTeamByName(String teamName) {
        return null;
    }

    public static ArrayList<Team> getTeams() {
        return teams;
    }

    public static void setTeams(ArrayList<Team> teams) {
        Team.teams = teams;
    }

    public String getName() {
        return name ;
    }
    

    public User getLeader() {
        return leader;
    }

    public User[] getMembers() {
        return new User[0];
    }
}
