package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.Team;

public class RequestTeamItem {

    private Team team;
    private TeamItem.OnItemClickListener onItemClickListener;

    public RequestTeamItem(Team team) {
        this.team = team;
    }

    public void setOnItemClickListener(TeamItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();
        Label label;
        Pane pane = new Pane();
        Label label1;

        label = new Label(team.getName());
        label1 = new Label(team.getStatus().name());

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.team)));

        hBox.getChildren().addAll(label, pane, label1);

        return hBox;
    }

    public interface OnItemClickListener {

        void onClick(Team team);
    }
}
