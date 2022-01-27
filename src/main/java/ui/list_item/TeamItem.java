package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.Team;

public class TeamItem {

    private Team team;
    private OnItemClickListener onItemClickListener;

    public TeamItem(Team team) {
        this.team = team;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();
        Label label;

        label = new Label(team.getName());

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.team)));

        hBox.getChildren().add(label);

        return hBox;
    }

    public interface OnItemClickListener {

        void onClick(Team team);
    }
}
