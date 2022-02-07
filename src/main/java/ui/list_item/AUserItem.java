package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.User;

public class AUserItem {
    private User user;
    private AUserItem.OnItemClickListener onItemClickListener;

    public AUserItem(User user) {
        this.user = user;
    }

    public void setOnItemClickListener(AUserItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();

        Label label;
        Label label1;
        label = new Label(user.getUsername());
        label1 = new Label(String.valueOf(User.getUserPoints(this.user)));

        Pane pane = new Pane();


        Button button = new Button("Ban");
        Button button1 = new Button("Set Leader");
        Button button2 = new Button("Set Member");
        Button button3 = new Button("Make Unknown");
        Button button4 = new Button("Make Known");
        button.getStyleClass().add("custom-btn");
        button1.getStyleClass().add("custom-btn");
        button2.getStyleClass().add("custom-btn");
        button3.getStyleClass().add("custom-btn");
        button4.getStyleClass().add("custom-btn");
        button.setOnMouseClicked(event -> onItemClickListener.ban(this.user));
        button1.setOnMouseClicked((event -> onItemClickListener.setLeader(this.user)));
        button2.setOnMouseClicked((event -> onItemClickListener.setMember(this.user)));
        button3.setOnMouseClicked(event -> onItemClickListener.makeUnknown(this.user));
        button4.setOnMouseClicked(event -> onItemClickListener.makeKnown(this.user));

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.user)));

        hBox.getChildren().addAll(label, label1, pane);
        hBox.setSpacing(10);

        if (this.user.isTeamLeader())
            hBox.getChildren().add(button2);
        else
            hBox.getChildren().add(button1);

        if (!this.user.isUnknown()){
            hBox.getChildren().add(button3);
        }else {
            hBox.getChildren().add(button4);
        }

        hBox.getChildren().add(button);

        return hBox;
    }

    public interface OnItemClickListener {

        void onClick(User user);

        void ban(User user);

        void setMember(User user);

        void setLeader(User user);

        void makeUnknown(User user);

        void makeKnown(User user);
    }
}
