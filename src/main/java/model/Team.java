package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Team implements Serializable {
    private static ArrayList<Team> teams = new ArrayList<>();
    private String name;
    private User leader;

    private ArrayList<Task> tasks;
    private ArrayList<Board> boards;
    private HashMap<User, Integer> teamMembers;

    private ArrayList<Message> chatroom;

    public Team(String name, User leader) {
        this.name = name;
        this.leader = leader;
        this.tasks = new ArrayList<>();
        this.boards = new ArrayList<>();
        this.teamMembers = new LinkedHashMap<>();
        this.chatroom = new ArrayList<>();
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
        return name;
    }

    public User getLeader() {
        return leader;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void addBoard(Board board) {
        this.boards.add(board);
    }

    public void removeBoard(Board board) {
        this.boards.remove(board);
    }

    public Board getBoardByName(String name) {
        for (Board board : this.boards)
            if (board.getName().equalsIgnoreCase(name))
                return board;
        return null;
    }

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }

    public Task getTaskById(int id) {
        for (Task task : this.tasks)
            if(task.getId() == id)
                return task;
        return null;
    }

    public Task getTaskByTitle(String taskTitle) {
        for (Task task : this.tasks)
            if(task.getTitle().equalsIgnoreCase(taskTitle))
                return task;
        return null;
    }

    public String getTasksFormatted() {
        StringBuilder result = new StringBuilder();

        if(this.tasks.isEmpty())
            return "no task yet";


        this.tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                LocalDateTime dead1 = o1.getTimeOfDeadline();
                LocalDateTime dead2 = o2.getTimeOfDeadline();

                if(dead1.isBefore(dead2))
                    return 1;
                else if(dead2.isBefore(dead1))
                    return -1;

                String priority1 = o1.getPriority();
                String priority2 = o2.getPriority();

                return priority1.compareTo(priority2);
            }
        });

        int index = 1;

        for (Task task : this.tasks)
            result.append(index)
                    .append(".")
                    .append(task.getTitle())
                    .append(": id: ")
                    .append(task.getId())
                    .append(", creation date: ")
                    .append(task.getTimeOfCreationFormatted())
                    .append(", deadline: ")
                    .append(task.getTimeOfDeadlineFormatted())
                    .append(", assign to: ")
                    .append(task.getAssignedUsersFormatted())
                    .append(", priority: ")
                    .append(task.getPriority());

        return result.toString();
    }

    public boolean hasMember(User user) {
        return this.teamMembers.containsKey(user);
    }

    public User[] getMembers() {
        return new User[0];
    }

    public void sendMessage(User sender, String body) {
        this.chatroom.add(new Message(sender, body));
    }

    public String getMessages() {
        StringBuilder result = new StringBuilder();

        if (this.chatroom.isEmpty())
            return "no message yet";

        this.chatroom.sort(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                if (o1.getDateTime().isAfter(o2.getDateTime()))
                    return 1;
                else if (o1.getDateTime().isBefore(o2.getDateTime()))
                    return -1;

                return 0;
            }
        });

        for (Message message : this.chatroom)
            result.append(message.getSender().getFirstname())
                    .append(": ")
                    .append(String.format("\"%s\"", message.getBody()));
        return result.toString();
    }

    public String getRoadmap() {
        StringBuilder result = new StringBuilder();

        if (this.tasks.isEmpty())
            return "no task yet";

        for (Task task : this.tasks)
            result.append(task.getTitle()).append(": ").append("% done");
        return result.toString();
    }

    public String getScoreboard() {
        List<User> members = new ArrayList<>(this.teamMembers.keySet());

        members.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int score1 = teamMembers.get(o1);
                int score2 = teamMembers.get(o2);

                if (score1 > score2)
                    return 1;
                else if (score2 > score1)
                    return -1;

                String name1 = o1.getFirstname();
                String name2 = o2.getFirstname();
                return name1.compareTo(name2);
            }
        });

        int index = 1;
        StringBuilder result = new StringBuilder();
        for (User user : members) {
            result.append(index)
                    .append(" ")
                    .append(user.getFirstname())
                    .append(" ")
                    .append(this.teamMembers.get(user));
        }
        return result.toString();
    }
}
