package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Notification;
import model.Task;

public class NotificationItem {
    private Notification notification;
    private NotificationItem.OnItemClickListener onItemClickListener;

    public NotificationItem(Notification notification) {
        this.notification = notification;
    }

    public void setOnItemClickListener(NotificationItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public VBox draw() {
        VBox vBox = new VBox();

        Label label = new Label(this.notification.getUser());
        Label label1 = new Label(this.notification.getBody());


        vBox.getStyleClass().add("list-item");
        vBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.notification)));

        vBox.getChildren().addAll(label, label1);

        return vBox;
    }

    public interface OnItemClickListener {

        void onClick(Notification notification);
    }
}
