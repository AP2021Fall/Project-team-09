package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {

    private static int ID_COUNTER = 1;

    private int id;
    private String name;
    private ArrayList<String> categories;
    private State state;

    public Board(int id, String name, ArrayList<String> categories, State state) {
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.state = state;
    }

    public Board(String name) {
        this.id = ID_COUNTER++;
        this.name = name;
        this.categories = new ArrayList<>();
        this.state = State.INCOMPLETE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryTasksFormatted(String categoryName) {
        StringBuilder result = new StringBuilder();

        for (Task task : Task.getAllTask())
            if (task.getCategory() != null && task.getBoard() != null)
                if (task.getCategory().equalsIgnoreCase(categoryName) &&
                        task.getBoard().getId() == this.id)
                    result.append(String.format("Task %s by %s is %s",
                                    task.getTitle(), task.getAssignedUsers().get(0).getUsername(),
                                    "something"))
                            .append("\n");
        return result.toString();
    }

    public ArrayList<Task> getCategoryTasks(String categoryName) {
        ArrayList<Task> tasks = new ArrayList<>();

        for (Task task : Task.getAllTask())
            if (task.getCategory() != null && task.getBoard() != null)
                if (task.getCategory().equalsIgnoreCase(categoryName) &&
                        task.getBoard().getId() == this.id)
                    tasks.add(task);
        return tasks;
    }

    public ArrayList<String> getCategories() {
        return this.categories;
    }

    public String getFirstCategory() {
        return this.categories.get(0);
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public boolean hasCategory(String categoryName) {
        for (String category : this.categories)
            if (category.equalsIgnoreCase(categoryName))
                return true;
        return false;
    }

    public String getNextCategory(String categoryName) {
        int index = this.categories.indexOf(categoryName);

        if (index < this.categories.size() - 1)
            return this.categories.get(index + 1);
        return "done";
    }

    public int addCategory(int index, String categoryName) {
        try {
            this.categories.add(index, categoryName);
        } catch (IndexOutOfBoundsException exception) {
            return -1;
        }
        return index;
    }

    public int addCategory(String categoryName) {
        this.categories.add(categoryName);
        return this.categories.size() - 1;
    }

    public State getState() {
        return this.state;
    }

    public void setDone() {
        this.state = State.DONE;
    }

    public boolean isDone() {
        return this.state == State.DONE;
    }

    private float getCompletion(Team team, Board board) {
        int count = 0;
        ArrayList<Task> boardTasks = team.getBoardTasks(board);
        for (Task task : boardTasks)
            if (task.isDone())
                count++;
        if (count > 0)
            return ((float) count / (float) boardTasks.size()) * 100;
        return 0;
    }

    private float getFailed(Team team, Board board) {
        int count = 0;
        ArrayList<Task> boardTasks = team.getBoardTasks(board);
        for (Task task : boardTasks)
            if (task.isFailed())
                count++;
        if (count > 0)
            return ((float) count / (float) boardTasks.size()) * 100;
        return 0;
    }

    public String getInfo(String teamLeader, Team team, Board board) {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Board name: %s\n", this.getName()))
                .append(String.format("Board completion: %.2f\n", this.getCompletion(team, board)))
                .append(String.format("Board failed: %.2f\n", this.getFailed(team, board)))
                .append(String.format("Board leader: %s\n", teamLeader))
                .append("Board tasks:\n");

        ArrayList<Task> boardTasks = team.getBoardTasks(board);
        StringBuilder highest = new StringBuilder();
        StringBuilder high = new StringBuilder();
        StringBuilder low = new StringBuilder();
        StringBuilder lowest = new StringBuilder();

        highest.append("Highest priority:\n");
        high.append("High priority:\n");
        low.append("Low priority:\n");
        lowest.append("Lowest priority:\n");

        for (Task task : boardTasks)
            if (task.getPriority().equalsIgnoreCase("highest"))
                highest.append(task);
            else if (task.getPriority().equalsIgnoreCase("high"))
                high.append(task);
            else if (task.getPriority().equalsIgnoreCase("low"))
                low.append(task);
            else if (task.getPriority().equalsIgnoreCase("lowest"))
                lowest.append(task);

        result.append(highest);
        result.append(high);
        result.append(low);
        result.append(lowest);
        return result.toString();
    }

    public static int getIdCounter() {
        return ID_COUNTER;
    }

    public static void setIdCounter(int idCounter) {
        ID_COUNTER = idCounter;
    }

    public void setState(State state) {
        this.state = state;
    }

    private enum State {
        INCOMPLETE, DONE
    }

}
