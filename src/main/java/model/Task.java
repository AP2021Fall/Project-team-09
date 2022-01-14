
package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Task implements Serializable {
    private static ArrayList<Task> allTask = new ArrayList<>();
    private static int idCounter = 100;
    private int id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDateTime timeOfCreation;
    private LocalDateTime startTime;
    private LocalDateTime timeOfDeadline;
    private ArrayList<User> assignedUsers;
    private ArrayList<Comment> comments;
    private Board board;
    private String category;
    private Status status;

    public Task(String title, LocalDateTime startTime, LocalDateTime timeOfDeadline) {
        this.id = idCounter++;
        this.title = title;
        this.description = "";
        this.priority = Priority.LOW;
        this.status = Status.IN_PROGRESS;
        this.timeOfCreation = LocalDateTime.now();
        this.startTime = startTime;
        this.timeOfDeadline = timeOfDeadline;
        this.assignedUsers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.board = null;
        this.category = null;
    }

    public static ArrayList<Task> getAllTask() {
        return allTask;
    }

    public static void setAllTask(ArrayList<Task> allTask) {
        Task.allTask = allTask;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void addTask(Task task) {
        allTask.add(task);
    }

    public static String getDeadlines(User user) {
        StringBuilder result = new StringBuilder();

        if (allTask.isEmpty())
            return "no deadlines";

        for (Task task : allTask)
            if (task.isInAssignedUsers(user.getUsername()) != null) {
                if (task.isDone() || task.hasPassedDeadline())
                    continue;
                long remainingDays = LocalDateTime.now()
                        .until(task.getTimeOfDeadline(), ChronoUnit.DAYS);

                String state = "";
                if (remainingDays > 10)
                    state = "*";
                else if (remainingDays >= 4 && remainingDays <= 10)
                    state = "**";
                else
                    state = "***";
                result.append(String.format("%s%s__remaining days: %d__Team: %s",
                        state,
                        task.getTimeOfDeadlineFormatted(),
                        remainingDays,
                        getTeamName(task.getId())));
            }
        return result.toString();
    }

    public static String getTeamName(int taskId) {
        Team team = Team.getTeamOfTask(taskId);
        if (team == null)
            return null;
        return team.getName();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAddedToBoard(Board board) {
        if(this.board == null)
            return false;
        return this.board.getId() == board.getId();
    }

    public boolean hasPassedDeadline() {
        return this.timeOfDeadline.isBefore(LocalDateTime.now());
    }

    public String getPriority() {
        if (this.priority.equals(Priority.LOWEST))
            return "Lowest";
        else if (this.priority.equals(Priority.LOW))
            return "Low";
        else if (this.priority.equals(Priority.HIGH))
            return "High";
        else
            return "Highest";
    }

    public void setPriority(String priority) {
        switch (priority) {
            case "Lowest":
                this.priority = Priority.LOWEST;
                break;
            case "Low":
                this.priority = Priority.LOW;
                break;
            case "High":
                this.priority = Priority.HIGH;
                break;
            case "Highest":
                this.priority = Priority.HIGHEST;
                break;
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
        this.category = board.getFirstCategory();
    }

    public void moveToNextCategory() {
        if (this.category.equalsIgnoreCase("done"))
            return;
        this.category = this.board.getNextCategory(this.category);
    }

    public boolean isDone() {
        return this.category.equalsIgnoreCase("done");
    }

    public LocalDateTime getTimeOfCreation() {
        return this.timeOfCreation;
    }

    public String getTimeOfCreationFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");
        return this.timeOfCreation.format(formatter);
    }

    public String getStartTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");
        return this.startTime.format(formatter);
    }

    public void setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public LocalDateTime getTimeOfDeadline() {
        return timeOfDeadline;
    }

    public String getTimeOfDeadlineFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");
        return this.timeOfDeadline.format(formatter);
    }

    public void setTimeOfDeadline(LocalDateTime timeOfDeadline) {
        this.timeOfDeadline = timeOfDeadline;
    }

    public ArrayList<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(ArrayList<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getAssignedUsersFormatted() {
        this.assignedUsers.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });
        return this.assignedUsers.stream().map(User::getUsername)
                .collect(Collectors.joining(", "));
    }

    public static Task getTask(int id) {
        for (Task task : allTask)
            if (task.getId() == id)
                return task;
        return null;
    }

    public User isInAssignedUsers(String username) {
        for (User user : assignedUsers)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    public void removeFromAssignedUsers(User user) {
        if (this.assignedUsers.contains(user))
            assignedUsers.remove(user);
    }

    public void assignUser(User user) {
        this.assignedUsers.add(user);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("ID: ").append(this.id).append("\n")
                .append("Title: ").append(this.title).append("\n")
                .append("Description: ").append(this.description).append("\n")
                .append("Priority: ").append(this.getPriority()).append("\n")
                .append("Date and time of creation: ").append(this.timeOfCreation).append("\n")
                .append("Date and time of creation: ").append(this.timeOfCreation).append("\n")
                .append("Date and time of deadline: ").append(this.timeOfDeadline).append("\n")
                .append("Assigned users: ").append("\n");

        for (User user : assignedUsers)
            string.append(user.getUsername()).append("\n");

        string.append("Comments: ").append("\n");

        // Todo sort comments desc

        for (Comment comment : comments)
            string.append(comment).append("\n");

        return string.toString();
    }

    enum Priority {
        LOWEST, LOW, HIGH, HIGHEST;
    }

    enum Status {
        IN_PROGRESS, FAILED, DONE
    }
}
