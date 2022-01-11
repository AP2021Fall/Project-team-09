
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task implements Serializable {
    private static ArrayList<Task> allTask = new ArrayList<>();
    private static int idCounter = 100;
    private int id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDateTime timeOfCreation;
    private LocalDateTime timeOfDeadline;
    private ArrayList<User> assignedUsers;
    private ArrayList<Comment> comments;

    public Task(String title, Priority priority) {
        this.id = idCounter++;
        this.title = title;
        this.description = "";
        this.priority = priority;
        this.timeOfCreation = LocalDateTime.now();
        this.timeOfDeadline = null;
        assignedUsers = new ArrayList<>();
        comments = new ArrayList<>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDateTime getTimeOfCreation() {
        return this.timeOfCreation;
    }

    public void setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public LocalDateTime getTimeOfDeadline() {
        return timeOfDeadline;
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

    public static Task getTask(int id) {
        for (Task task : allTask)
            if(task.getId() == id)
                return task;
        return null;
    }

    public User isInAssignedUsers(String username) {
        for (User user : assignedUsers)
            if(user.getUsername().equals(username))
                return user;
        return null;
    }

    public void removeFromAssignedUsers(User user) {
        if(this.assignedUsers.contains(user))
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
}
