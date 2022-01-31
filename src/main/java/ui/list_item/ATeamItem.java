package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.Team;
import model.User;

public class ATeamItem {
    private Team team;
    private ATeamItem.OnItemClickListener onItemClickListener;

    public ATeamItem(Team team) {
        this.team = team;
    }

    public void setOnItemClickListener(ATeamItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();

        Label label;
        label = new Label(team.getName());

        Pane pane = new Pane();

        Button button = new Button("Accept");
        Button button1 = new Button("Reject");
        button.getStyleClass().add("custom-btn");
        button1.getStyleClass().add("custom-btn");
        button.setOnMouseClicked((event -> onItemClickListener.accept(this.team)));
        button1.setOnMouseClicked((event -> onItemClickListener.reject(this.team)));

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
//        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.user)));

        hBox.getChildren().addAll(label, pane);
        hBox.setSpacing(10);

        if (this.team.isPending()) {
            hBox.getChildren().addAll(button, button1);
        }

        return hBox;
    }

    public interface OnItemClickListener {

        void accept(Team team);

        void reject(Team team);
    }
}
