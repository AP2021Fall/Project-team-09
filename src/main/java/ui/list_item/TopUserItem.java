package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.User;

public class TopUserItem {
    private User member;
    private TopUserItem.OnItemClickListener onItemClickListener;

    public TopUserItem(User member) {
        this.member = member;
    }

    public void setOnItemClickListener(TopUserItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();

        Label label;
        label = new Label(member.getUsername());
        Label label1;
        label1 = new Label(String.valueOf(User.getUserPoints(this.member)));

        hBox.getChildren().addAll(label, label1);

        hBox.setSpacing(10);

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.member)));

        return hBox;
    }

    public interface OnItemClickListener {

        void onClick(User user);
    }
}
