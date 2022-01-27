package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.Board;

public class BoardItem {

    private Board board;
    private BoardItem.OnItemClickListener onItemClickListener;

    public BoardItem(Board board) {
        this.board = board;
    }

    public void setOnItemClickListener(BoardItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();

        Label label;
        Button button;

        label = new Label(board.getName());

        Pane pane = new Pane();

        button = new Button("Remove");
        button.getStyleClass().add("custom-btn");

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.board)));

        hBox.getChildren().addAll(label, pane, button);

        return hBox;
    }

    public interface OnItemClickListener {

        void onClick(Board board);
    }
}
