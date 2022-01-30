package ui.list_item;

import controller.UserController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import model.Board;
import model.Task;
import model.Team;
import utilities.SharedPreferences;

import java.util.ArrayList;

public class BoardCategoryItem {
    private Team team;
    private Board board;
    private String category;
    private ArrayList<Task> tasks;
    private BoardCategoryItem.OnItemClickListener onItemClickListener;

    public BoardCategoryItem(Team team, Board board, String category, ArrayList<Task> tasks) {
        this.team = team;
        this.board = board;
        this.category = category;
        this.tasks = tasks;
    }

    public void setOnItemClickListener(BoardCategoryItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public VBox draw() {
        VBox vBox = new VBox();
        HBox hBox = new HBox();

        vBox.setMinWidth(200);
        vBox.setPrefWidth(200);

        Label label = new Label(category);
        label.setTextAlignment(TextAlignment.CENTER);
        hBox.getChildren().add(label);

        vBox.getChildren().add(hBox);
        vBox.setSpacing(10);
        Object mine = SharedPreferences.get("only_mine");
        boolean onlyMineSelected = false;
        if (mine != null)
            onlyMineSelected = (Boolean) mine;

        for (Task task : this.tasks) {
            if (onlyMineSelected)
                if (task.isInAssignedUsers(UserController.getLoggedUser().getUsername()) == null)
                    continue;
            BoardTaskItem boardTaskItem = new BoardTaskItem(task);
            boardTaskItem.setOnItemClickListener(new BoardTaskItem.OnItemClickListener() {
                @Override
                public void onClick(Task task) {
                    onItemClickListener.onClick(task);
                }

                @Override
                public void onDone(Task task) {
                    onItemClickListener.onDone(task);
                }

                @Override
                public void onNext(Task task) {
                    onItemClickListener.onNext(task);
                }

                @Override
                public void onPre(Task task) {
                    onItemClickListener.onPre(task);
                }

                @Override
                public void addToBoard(Task task) {
                    onItemClickListener.addToBoard(task);
                }
            });
            HBox boardBox = boardTaskItem.draw();
            vBox.getChildren().add(boardBox);
        }

        vBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(hBox, Priority.ALWAYS);

        return vBox;
    }

    public interface OnItemClickListener {

        void onClick(Task task);

        void onDone(Task task);

        void onNext(Task task);

        void onPre(Task task);

        void addToBoard(Task task);
    }
}