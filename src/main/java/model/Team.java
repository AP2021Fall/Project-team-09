package model;

public class Team {
   String name;
   String leader;

    public Team(String name , String leader) {
        this.name=name;
        this.leader=leader;
    }

    public static Team getTeamByName(String teamName) {
        return null;
    }

    public String getName() {
        return name ;
    }

    public String  getLeader() {
        return leader;
    }

    public User[] getMembers() {
        return new User[0];
    }

}
