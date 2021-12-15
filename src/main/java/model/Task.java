
package model;
    import java.io.Serializable;
    import java.time.LocalDate;
    import java.util.ArrayList;

public class Task  implements Serializable {
    private static ArrayList<Task> allTask = new ArrayList<>();
    private static int idCounter = 100;
    private int id;
    private String title;
    private String description;
    private String priority;
    private LocalDate timeOfCreation;
    private String timeOfDeadline;
    private ArrayList<User> assignedUsers;
    private ArrayList<String> comments;

    public Task(String description, String priority) {
        this.id = idCounter++;
        this.description = description;
        this.priority = priority;
        this.timeOfCreation = LocalDate.now();
        this.timeOfDeadline = timeOfDeadline;
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
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(LocalDate timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public String getTimeOfDeadline() {
        return timeOfDeadline;
    }

    public void setTimeOfDeadline(String timeOfDeadline) {
        this.timeOfDeadline = timeOfDeadline;
    }

    public ArrayList<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(ArrayList<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }
}
