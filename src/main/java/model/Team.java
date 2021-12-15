package model;

public class Team {
   String name;
   User leader;

    public Team(String name , User leader) {
        this.name=name;
        this.leader=leader;
    }


    public static Team getTeamByName(String teamName) {
        return null;
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
