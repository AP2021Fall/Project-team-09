package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.Task;

public class RoadmapItem {

    private Task task;
    private RoadmapItem.OnItemClickListener onItemClickListener;

    public RoadmapItem(Task task) {
        this.task = task;
    }

    public void setOnItemClickListener(RoadmapItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();

        Label label;
        ProgressBar progressBar = new ProgressBar();

        label = new Label(task.getTitle());

        Pane pane = new Pane();

        progressBar.setProgress(task.getProgress() / 100);

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.task)));

        hBox.getChildren().addAll(label, pane, progressBar);

        return hBox;
    }

    public interface OnItemClickListener {

        void onClick(Task task);
    }
}
