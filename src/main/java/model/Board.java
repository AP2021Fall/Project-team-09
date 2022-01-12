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

    public String getCategoryTasks(String categoryName) {
        StringBuilder result = new StringBuilder();

        for (Task task : Task.getAllTask())
            if (task.getCategory().equalsIgnoreCase(categoryName))
                result.append(String.format("Task %s by %s is %s",
                                task.getTitle(), task.getAssignedUsers().get(0).getUsername(),
                                "something"))
                        .append("\n");
        return result.toString();
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
            return this.categories.get(this.categories.size() - 1);
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

    private enum State {
        INCOMPLETE, DONE
    }
}
