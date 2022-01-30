package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.Task;
import model.Team;

public class TaskItem {

    private Task task;
    private Team team;
    private TaskItem.OnItemClickListener onItemClickListener;

    public TaskItem(Team team, Task task) {
        this.task = task;
        this.team = team;
    }

    public void setOnItemClickListener(TaskItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();
        Pane pane = new Pane();

        hBox.setSpacing(10);

        Label label;
        Label label1;
        Label label2;
        Label label3;

        label = new Label(task.getTitle());
        label1 = new Label(task.getPriority());
        label2 = new Label(String.format("Team: %s", team.getName()));
        label3 = new Label(String.format("Deadline: %s", task.getTimeOfDeadlineFormatted()));

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.team, this.task)));

        hBox.getChildren().addAll(label, label1, pane, label2, label3);

        return hBox;
    }

    public interface OnItemClickListener {

        void onClick(Team team, Task task);
    }
}
