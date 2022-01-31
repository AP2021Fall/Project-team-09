package ui.list_item;

import controller.UserController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.Task;
import model.User;
import utilities.SharedPreferences;

public class TaskMemberItem {
    private User member;
    private TaskMemberItem.OnItemClickListener onItemClickListener;

    public TaskMemberItem(User member) {
        this.member = member;
    }

    public void setOnItemClickListener(TaskMemberItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HBox draw() {
        HBox hBox = new HBox();

        Label label;
        Button button;
        Button button1;

        label = new Label(member.getUsername());

        Pane pane = new Pane();
        hBox.getChildren().addAll(label, pane);

        button = new Button("ADD");
        button1 = new Button("REMOVE");

        Task task = (Task) SharedPreferences.get("task");

        User user = null;
        if (UserController.getLoggedUser().isTeamLeader()) {
            if (task != null) {
                user = task.isInAssignedUsers(this.member.getUsername());
                if (!task.isDone()) {
                    if (user == null) {
                        button.setOnMouseClicked(event -> this.onItemClickListener.onAdd(this.member));
                        hBox.getChildren().add(button);
                    } else {
                        button1.setOnMouseClicked(event -> this.onItemClickListener.onRemove(this.member));
                        hBox.getChildren().add(button1);
                    }
                }
            }
        }

        button.getStyleClass().add("custom-btn");
        button1.getStyleClass().add("custom-btn");

        hBox.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
//        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.member)));
        return hBox;
    }

    public interface OnItemClickListener {

        void onAdd(User member);

        void onRemove(User member);
    }
}
