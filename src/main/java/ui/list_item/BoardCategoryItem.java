package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import model.Task;

import java.util.List;

public class BoardCategoryItem {
    private String category;
    private List<Task> tasks;
    private BoardCategoryItem.OnItemClickListener onItemClickListener;

    public BoardCategoryItem(String category, List<Task> tasks) {
        this.category = category;
        this.tasks = tasks;
    }

    public void setOnItemClickListener(BoardCategoryItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public VBox draw() {
        VBox vBox = new VBox();
        HBox hBox = new HBox();

        Label label = new Label(category);
        label.setTextAlignment(TextAlignment.CENTER);
        hBox.getChildren().add(label);

        vBox.getChildren().add(hBox);
        vBox.setSpacing(10);

        for (Task task : this.tasks) {
            BoardTaskItem boardTaskItem = new BoardTaskItem(task);
            boardTaskItem.setOnItemClickListener(new BoardTaskItem.OnItemClickListener() {
                @Override
                public void onClick(Task task) {

                }
            });
            HBox boardBox = boardTaskItem.draw();
            vBox.getChildren().add(boardBox);
        }

        vBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.category, this.tasks)));

        return vBox;
    }

    public interface OnItemClickListener {

        void onClick(String category, List<Task> tasks);
    }
}