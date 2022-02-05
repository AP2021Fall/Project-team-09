
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
    private ArrayList<String> assignedUsers;
    private ArrayList<Comment> comments;
    private Board board;
    private String category;
    private Status status;

    public Task(int id, String title, String description, Priority priority, LocalDateTime timeOfCreation, LocalDateTime startTime, LocalDateTime timeOfDeadline, ArrayList<String> assignedUsers, ArrayList<Comment> comments, Board board, String category, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.timeOfCreation = timeOfCreation;
        this.startTime = startTime;
        this.timeOfDeadline = timeOfDeadline;
        this.assignedUsers = assignedUsers;
        this.comments = comments;
        this.board = board;
        this.category = category;
        this.status = status;
    }

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

    public Task(String title, Priority priority, LocalDateTime startTime, LocalDateTime timeOfDeadline,
                String description) {
        this.id = idCounter++;
        this.title = title;
        this.description = description;
        this.priority = priority;
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

    public float getProgress() {
        if (this.board == null)
            return 0;
        if (this.category == null)
            return 0;

        int index = this.board.getCategories().indexOf(this.category);

        if (this.isDone())
            index = this.board.getCategories().size();
        if (this.isFailed())
            index = -100;

        return ((float) index / (float) this.board.getCategories().size()) * 100;
    }

    public static void addTask(Task task) {
        allTask.add(task);
    }

    public static String getDeadlinesFormatted(User user) {
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
                                getTeamName(task.getId())))
                        .append("\n");
            }
        return result.toString();
    }

    public static ArrayList<Task> getDeadlines(User user) {
        ArrayList<Task> tasks = new ArrayList<>();

        for (Task task : allTask)
            if (task.isInAssignedUsers(user.getUsername()) != null) {
                if (task.isDone() || task.hasPassedDeadline())
                    continue;
                tasks.add(task);
            }
        return tasks;
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
        if (this.board == null)
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

    public int getPriorityNumeric() {
        if (this.priority.equals(Priority.LOWEST))
            return 1;
        else if (this.priority.equals(Priority.LOW))
            return 2;
        else if (this.priority.equals(Priority.HIGH))
            return 3;
        else
            return 4;
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

    public void removeBoard() {
        this.board = null;
        this.category = null;
    }

    public boolean moveToNextCategory() {
        if (this.category.equalsIgnoreCase("done"))
            return false;
        this.category = this.board.getNextCategory(this.category);
        return true;
    }

    public boolean isDone() {
        if (this.category == null)
            return false;
        return this.category.equalsIgnoreCase("done");
    }

    public boolean isFailed() {
        if (this.category == null)
            return false;
        return this.category.equalsIgnoreCase("failed");
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

    public ArrayList<String> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(ArrayList<String> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getAssignedUsersFormatted() {
        this.assignedUsers.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        return this.assignedUsers.stream().map(String::toString)
                .collect(Collectors.joining(", "));
    }

    public static Task getTask(int id) {
        for (Task task : allTask)
            if (task.getId() == id)
                return task;
        return null;
    }

    public User isInAssignedUsers(String username) {
        for (String user : assignedUsers)
            if (user.equals(username))
                return User.getUser(user);
        return null;
    }

    public void removeFromAssignedUsers(User user) {
        if (this.assignedUsers.contains(user.getUsername()))
            assignedUsers.remove(user.getUsername());
    }

    public void assignUser(User user) {
        this.assignedUsers.add(user.getUsername());
    }


    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("ID: ").append(this.id).append("\n")
                .append("Title: ").append(this.title).append("\n")
                .append("Description: ").append(this.description).append("\n")
                .append("Priority: ").append(this.getPriority()).append("\n")
                .append("Date and time of creation: ").append(this.timeOfCreation).append("\n")
                .append("Date and time of start: ").append(this.startTime).append("\n")
                .append("Date and time of deadline: ").append(this.timeOfDeadline).append("\n")
                .append("Assigned users: ").append("\n");

        for (String user : assignedUsers)
            string.append(user).append("\n");

        string.append("Comments: ").append("\n");

        comments.sort(new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        });

        for (Comment comment : comments)
            string.append(comment).append("\n");

        return string.toString();
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        Task.idCounter = idCounter;
    }

    enum Priority {
        LOWEST, LOW, HIGH, HIGHEST;
    }

    enum Status {
        IN_PROGRESS, FAILED, DONE
    }
}
