package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.User;

public class AssignMemberItem {

    private User member;
    private OnItemClickListener onItemClickListener;

    public AssignMemberItem(User member) {
        this.member = member;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();

        Label label;
        label = new Label(member.getUsername());

        Pane pane = new Pane();

        Button button = new Button("Add to team");
        button.getStyleClass().add("custom-btn");

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.member)));

        hBox.getChildren().addAll(label, pane, button);

        return hBox;
    }

    public interface OnItemClickListener {

        void onClick(User member);
    }
}