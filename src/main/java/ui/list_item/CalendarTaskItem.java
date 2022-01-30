package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.Task;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CalendarTaskItem {
    private Task task;
    private CalendarTaskItem.OnItemClickListener onItemClickListener;

    public CalendarTaskItem(Task task) {
        this.task = task;
    }

    public void setOnItemClickListener(CalendarTaskItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();
        Pane pane = new Pane();

        hBox.setSpacing(10);

        Label label;
        Label label1;
        Label label3;

        label = new Label(task.getTitle());
        label1 = new Label(task.getPriority());
        label3 = new Label(String.format("Deadline: %s", task.getTimeOfDeadlineFormatted()));

        String deadlineStyle = "deadline-good";

        long remainingDays = LocalDateTime.now()
                .until(task.getTimeOfDeadline(), ChronoUnit.DAYS);

        if (remainingDays > 10)
            deadlineStyle = "deadline-good";
        else if (remainingDays >= 4)
            deadlineStyle = "deadline-bad";
        else
            deadlineStyle = "deadline-verybad";

        label3.getStyleClass().add(deadlineStyle);


        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.task)));

        hBox.getChildren().addAll(label, label1, pane, label3);

        return hBox;
    }

    public interface OnItemClickListener {

        void onClick(Task task);
    }
}
