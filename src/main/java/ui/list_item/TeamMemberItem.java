package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.User;

public class TeamMemberItem {
    private User member;
    private TeamMemberItem.OnItemClickListener onItemClickListener;

    public TeamMemberItem(User member) {
        this.member = member;
    }

    public void setOnItemClickListener(TeamMemberItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        VBox vBox1 = new VBox();

        Label label;
        Label label1;
        label = new Label(member.getUsername());
        label1 = new Label(member.getType().name());

        vBox.getChildren().addAll(label, label1);

        Pane pane = new Pane();

        Button button = new Button("REMOVE");
        Button button1 = new Button("MESSAGE");

        button.getStyleClass().add("custom-btn");
        button1.getStyleClass().add("custom-btn");

        vBox1.getChildren().addAll(button, button1);

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.member)));

        hBox.getChildren().addAll(vBox, pane, vBox1);

        return hBox;
    }

    public interface OnItemClickListener {

        void onClick(User member);
    }
}
