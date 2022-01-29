package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Task;

public class BoardTaskItem {
    private Task task;
    private BoardTaskItem.OnItemClickListener onItemClickListener;

    public BoardTaskItem(Task task) {
        this.task = task;
    }

    public void setOnItemClickListener(BoardTaskItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        HBox hBox1 = new HBox();

        vBox.setFillWidth(true);

        Label label;
        Label label1;
        Label label2;

        Button pre = new Button("<");
        Button done = new Button("DONE");
        Button next = new Button(">");

        pre.setMaxWidth(Double.MAX_VALUE);
        done.setMaxWidth(Double.MAX_VALUE);
        next.setMaxWidth(Double.MAX_VALUE);

        pre.getStyleClass().add("custom-btn");
        done.getStyleClass().add("custom-btn");
        next.getStyleClass().add("custom-btn");

        pre.setOnMouseClicked(event -> this.onItemClickListener.onPre(this.task));
        done.setOnMouseClicked(event -> this.onItemClickListener.onDone(this.task));
        next.setOnMouseClicked(event -> this.onItemClickListener.onNext(this.task));

        HBox.setHgrow(done, Priority.ALWAYS);
        hBox1.getChildren().addAll(pre, done, next);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(10);

        label = new Label(String.format("Title: %s", task.getTitle()));
        label.setWrapText(true);
        label.wrapTextProperty().set(true);
        label1 = new Label(String.format("Description: %s", task.getDescription()));
        label1.setWrapText(true);
        label1.wrapTextProperty().set(true);
        label2 = new Label(task.getTimeOfDeadlineFormatted());

        vBox.getChildren().addAll(label, label1, new Separator(), label2, new Separator(), hBox1);
        vBox.setSpacing(10);

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(vBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);

        hBox.getChildren().add(vBox);

        return hBox;
    }

    public interface OnItemClickListener {

        void onDone(Task task);

        void onNext(Task task);

        void onPre(Task task);
    }
}