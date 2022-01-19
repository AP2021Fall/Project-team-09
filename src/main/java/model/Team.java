package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Team implements Serializable {
    private static ArrayList<Team> teams = new ArrayList<>();
    private static int ID_COUNTER = 1;
    private int id;
    private String name;
    private User leader;
    private LocalDateTime timeOfCreation;
    private Status status;

    private ArrayList<Task> tasks;
    private ArrayList<Board> boards;
    private HashMap<User, Integer> teamMembers;
    private HashMap<User, MemberStatus> membersStatus;

    private ArrayList<Message> chatroom;

    public Team(int id, String name, User leader, LocalDateTime timeOfCreation, Status status, ArrayList<Task> tasks, ArrayList<Board> boards, HashMap<User, Integer> teamMembers, HashMap<User, MemberStatus> membersStatus, ArrayList<Message> chatroom) {
        this.id = id;
        this.name = name;
        this.leader = leader;
        this.timeOfCreation = timeOfCreation;
        this.status = status;
        this.tasks = tasks;
        this.boards = boards;
        this.teamMembers = teamMembers;
        this.membersStatus = membersStatus;
        this.chatroom = chatroom;
    }

    public Team(String name, User leader) {
        this.id = ID_COUNTER++;
        this.name = name;
        this.leader = leader;
        this.status = Status.PENDING;
        this.tasks = new ArrayList<>();
        this.boards = new ArrayList<>();
        this.teamMembers = new LinkedHashMap<>();
        this.membersStatus = new LinkedHashMap<>();
        this.chatroom = new ArrayList<>();
        this.timeOfCreation = LocalDateTime.now();
    }

    public static Team getTeamByName(String teamName) {
        for (Team team : teams)
            if (team.getName().equalsIgnoreCase(teamName))
                return team;
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public static void createTeam(String teamName, User teamLeader) {
        Team team = new Team(teamName, teamLeader);
        teams.add(team);
    }

    public LocalDateTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public static ArrayList<Team> getTeams() {
        return teams;
    }

    public static void setTeams(ArrayList<Team> teams) {
        Team.teams = teams;
    }

    public static Team getTeamOfTask(int id) {
        for (Team team : teams)
            if (team.hasTask(id))
                return team;
        return null;
    }

    public String getName() {
        return name;
    }

    public User getLeader() {
        return leader;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public boolean isPending() {
        return this.status == Status.PENDING;
    }

    public void accept() {
        this.status = Status.ACCEPTED;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }

    public static ArrayList<Team> getPendingTeams() {
        ArrayList<Team> pending = new ArrayList<>();
        for (Team team : teams)
            if (team.isPending())
                pending.add(team);
        return pending;
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

    public int getMemberScore(User user) {
        return this.teamMembers.get(user);
    }

    public boolean isSuspended(User user) {
        return this.membersStatus.get(user) == MemberStatus.SUSPENDED;
    }

    public void createTask(String title, LocalDateTime start, LocalDateTime deadline) {
        Task task = new Task(title, start, deadline);
        this.tasks.add(task);
        Task.addTask(task);
    }

    public void createTask(String title, String priority, LocalDateTime start, LocalDateTime deadline,
                           String description) {
        Task.Priority p = Task.Priority.valueOf(priority.toUpperCase());
        Task task = new Task(title, p, start, deadline, description);
        this.tasks.add(task);
        Task.addTask(task);
    }

    public boolean hasTask(int id) {
        for (Task task : tasks)
            if (task.getId() == id)
                return true;
        return false;
    }

    public Task getTaskById(int id) {
        for (Task task : this.tasks)
            if (task.getId() == id)
                return task;
        return null;
    }

    public Task getTaskByTitle(String taskTitle) {
        for (Task task : this.tasks)
            if (task.getTitle().equalsIgnoreCase(taskTitle))
                return task;
        return null;
    }

    public String getTasksFullInfoFormatted() {
        StringBuilder result = new StringBuilder();

        if (this.tasks.isEmpty())
            return "no task yet";

        this.tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                LocalDateTime dead1 = o1.getTimeOfDeadline();
                LocalDateTime dead2 = o2.getTimeOfDeadline();

                return dead1.compareTo(dead2);
            }
        });

        int index = 1;

        for (Task task : this.tasks) {
            result.append(String.format("%d- title: %s," +
                                    " id: %d," +
                                    " created: %s," +
                                    " start time: %s," +
                                    " deadline: %s," +
                                    " status %s," +
                                    " priority: %s",
                            index++,
                            task.getTitle(),
                            task.getId(),
                            task.getTimeOfCreationFormatted(),
                            task.getStartTimeFormatted(),
                            task.getTimeOfDeadlineFormatted(),
                            task.getStatus().name(),
                            task.getPriority()))
                    .append("\n")
                    .append("assigned users:\n");

            for (User user : task.getAssignedUsers()) {
                result.append(user.getUsername()).append("\n");
            }

            if (task.getAssignedUsers().isEmpty())
                result.append("No user is assigned to this task!\n");
        }
        return result.toString();
    }

    public String getTasksFormatted() {
        StringBuilder result = new StringBuilder();

        if (this.tasks.isEmpty())
            return "no task yet";


        this.tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                LocalDateTime dead1 = o1.getTimeOfDeadline();
                LocalDateTime dead2 = o2.getTimeOfDeadline();

                int compare = dead1.compareTo(dead2);
                if (compare != 0)
                    return compare;

                if (o2.getPriorityNumeric() > o1.getPriorityNumeric())
                    return 1;
                else if (o2.getPriorityNumeric() == o2.getPriorityNumeric())
                    return 0;
                else return -1;
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
                    .append(task.getPriority())
                    .append("\n");

        return result.toString();
    }

    public boolean hasMember(User user) {
        return this.teamMembers.containsKey(user);
    }

    public ArrayList<User> getMembers() {
        return new ArrayList<>(this.teamMembers.keySet());
    }

    public void sendNotification(String body) {
        for (User user : this.teamMembers.keySet())
            user.sendNotification(body);
    }

    public void addMember(User user) {
        this.teamMembers.put(user, 0);
        this.membersStatus.put(user, MemberStatus.ACTIVE);
    }

    public void deleteMember(User user) {
        this.teamMembers.remove(user);
        this.membersStatus.remove(user);
    }

    public void suspendMember(User user) {
        this.membersStatus.put(user, MemberStatus.SUSPENDED);
    }

    public void sendMessage(User sender, String body) {
        this.chatroom.add(new Message(sender, body));
    }

    public String getMessagesFormatted() {
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
            result.append(message.getSender().getUsername())
                    .append(": ")
                    .append(String.format("\"%s\"", message.getBody()))
                    .append("\n");
        return result.toString();
    }

    public ArrayList<Message> getMessages() {
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
        return this.chatroom;
    }

    public String getRoadmapFormatted() {
        StringBuilder result = new StringBuilder();

        if (this.tasks.isEmpty())
            return "no task yet";

        for (Task task : this.tasks)
            result.append(task.getTitle()).append(": ").append(String.format("%.2f done\n", task.getProgress()));

        return result.toString();
    }

    public ArrayList<Task> getRoadmap() {
        return this.tasks;
    }

    public String getScoreboard() {
        List<User> members = new ArrayList<>(this.teamMembers.keySet());

        if (members.isEmpty())
            return "There is no member in this team!";

        members.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int score1 = teamMembers.get(o2);
                int score2 = teamMembers.get(o1);

                if (score1 > score2)
                    return 1;
                else if (score2 > score1)
                    return -1;

                String name1 = o1.getUsername();
                String name2 = o2.getUsername();
                return name1.compareTo(name2);
            }
        });

        int index = 1;
        StringBuilder result = new StringBuilder();
        for (User user : members) {
            result.append(index++)
                    .append(" ")
                    .append(user.getUsername())
                    .append(" ")
                    .append(this.teamMembers.get(user))
                    .append("\n");
        }
        result.append("\n");
        return result.toString();
    }

    public void givePoint(Task task) {
        for (User user : task.getAssignedUsers()) {
            this.teamMembers.put(user, this.teamMembers.get(user) + 10);
        }
    }

    public void takePoint(Task task) {
        for (User user : task.getAssignedUsers()) {
            this.teamMembers.put(user, this.teamMembers.get(user) - 5);
        }
    }

    public String getTasksByCategory(String category) {
        StringBuilder result = new StringBuilder();
        for (Task task : this.tasks)
            if (task.getCategory().equalsIgnoreCase(category))
                result.append(task.getTitle()).append("\n");
        if (result.length() == 0)
            return "no tasks";
        return result.toString();
    }

    public ArrayList<Task> getBoardTasks(Board board) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : this.tasks)
            if (task.getBoard().getId() == board.getId())
                tasks.add(task);
        return tasks;
    }

    public static int getIdCounter() {
        return ID_COUNTER;
    }

    public static void setIdCounter(int idCounter) {
        ID_COUNTER = idCounter;
    }

    public HashMap<User, Integer> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(HashMap<User, Integer> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public HashMap<User, MemberStatus> getMembersStatus() {
        return membersStatus;
    }

    public void setMembersStatus(HashMap<User, MemberStatus> membersStatus) {
        this.membersStatus = membersStatus;
    }

    public ArrayList<Message> getChatroom() {
        return chatroom;
    }

    public void setChatroom(ArrayList<Message> chatroom) {
        this.chatroom = chatroom;
    }

    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }

    public enum MemberStatus {
        ACTIVE, SUSPENDED
    }
}
