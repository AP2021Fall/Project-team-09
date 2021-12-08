
package model;
        import java.util.ArrayList;

public class Task  {
    private static ArrayList<Task> allTask = new ArrayList<>();
    private int id;
    private String title;
    private String description;
    private String priority;
    private String timeOfCreation;
    private String timeOfDeadline;
    private String assignedusers;
    private String comments;
    private ArrayList<User> chekassignedusers;

    public Task(int id, String description, String priority,
                String timeOfCreation, String timeOfDeadline,
                String assignedusers, String comments) {
        generateId();
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.timeOfCreation = timeOfCreation;
        this.timeOfDeadline = timeOfDeadline;
        this.assignedusers = assignedusers;
        this.comments = comments;
        this.chekassignedusers = new ArrayList<>();
        allTask.add(this);
    }
    private void generateId(){
        if(allTask.isEmpty()){
            this.id=0;
        }else{
            int lastIndex = allTask.size()-1;
            int lastId = allTask.get(lastIndex).getId();
            this.id = lastId +1 ;
        }


    }

    public ArrayList<User> getChekassignedusers() {
        return chekassignedusers;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public String getTimeOfDeadline() {
        return timeOfDeadline;
    }

    public String getAssignedusers() {
        return assignedusers;
    }

    public String getComments() {
        return comments;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setTimeOfDeadline(String timeOfDeadline) {
        this.timeOfDeadline = timeOfDeadline;
    }

    public void setAssignedusers(String assignedusers) {
        this.assignedusers = assignedusers;
    }
}
