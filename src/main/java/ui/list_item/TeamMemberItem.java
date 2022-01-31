package ui.list_item;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Team;
import model.User;

public class TeamMemberItem {
    private Team team;
    private User member;
    private TeamMemberItem.OnItemClickListener onItemClickListener;

    public TeamMemberItem(Team team, User member) {
        this.team = team;
        this.member = member;
    }

    public void setOnItemClickListener(TeamMemberItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();

        Label label;
        Label label1;
        Label label2;
        label = new Label(member.getUsername());
        label1 = new Label(member.getType().name());
        label2 = new Label(String.valueOf(team.getMemberScore(this.member)));

        vBox.getChildren().addAll(label, label1, label2);

        Pane pane = new Pane();

        Button button = new Button("REMOVE");
        button.setMaxWidth(Double.MAX_VALUE);
        Button button1 = new Button("MESSAGE");
        button1.setMaxWidth(Double.MAX_VALUE);
        Button button2 = new Button("PROMOTE");
        button2.setMaxWidth(Double.MAX_VALUE);
        Button button3 = new Button("SUSPEND");
        button3.setMaxWidth(Double.MAX_VALUE);
        Button button4 = new Button("ACTIVATE");
        button4.setMaxWidth(Double.MAX_VALUE);

        button.getStyleClass().add("custom-btn");
        button1.getStyleClass().add("custom-btn");
        button2.getStyleClass().add("custom-btn");
        button3.getStyleClass().add("custom-btn");
        button4.getStyleClass().add("custom-btn");

        button.setOnMouseClicked(event -> this.onItemClickListener.onRemove(this.member));
        button1.setOnMouseClicked(event -> this.onItemClickListener.onMessage(this.member));
        button2.setOnMouseClicked(event -> this.onItemClickListener.onPromote(this.member));
        button3.setOnMouseClicked(event -> this.onItemClickListener.onSuspend(this.member));
        button4.setOnMouseClicked(event -> this.onItemClickListener.onActivate(this.member));

        vBox1.getChildren().addAll(button, button1);
        vBox1.setSpacing(5);
        vBox2.getChildren().addAll(button2);
        vBox2.setSpacing(5);

        if (team.isSuspended(this.member))
            vBox2.getChildren().add(button4);
        else vBox2.getChildren().add(button3);

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
//        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.member)));

        hBox.getChildren().addAll(vBox, pane, vBox1, vBox2);
        hBox.setSpacing(10);

        return hBox;
    }

    public interface OnItemClickListener {

        void onRemove(User member);

        void onMessage(User member);

        void onPromote(User member);

        void onSuspend(User member);

        void onActivate(User member);
    }
}
