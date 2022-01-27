package ui.list_item;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Message;

public class ChatItem {

    private Message message;
    private ChatItem.OnItemClickListener onItemClickListener;

    public ChatItem(Message message) {
        this.message = message;
    }

    public void setOnItemClickListener(ChatItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public VBox draw() {
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        VBox vBox1 = new VBox();
        HBox hBox1 = new HBox();
        VBox vBox2 = new VBox();
        HBox hBox2 = new HBox();


        Label label;
        Label label2;
        Label label3;
        Label label4;

        label = new Label(message.getSender().getUsername());
        label2 = new Label(message.getSender().getType().name());
        vBox1.getChildren().addAll(label, label2);
        hBox.getChildren().add(vBox1);

        label3 = new Label(message.getBody());
        label3.setWrapText(true);

        label4 = new Label(message.getDateTime().toString());
        hBox2.getChildren().add(label4);

        vBox.getChildren().addAll(hBox, hBox1);
        vBox2.getChildren().addAll(label3, new Separator(), hBox2);
        hBox1.getChildren().add(vBox2);
        vBox2.getStyleClass().add("list-item");
        HBox.setHgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(hBox1, Priority.ALWAYS);
        HBox.setHgrow(hBox2, Priority.ALWAYS);
        hBox2.setAlignment(Pos.CENTER_RIGHT);

        hBox.setOnMouseClicked((event -> onItemClickListener.onClick(this.message)));

        return vBox;
    }

    public interface OnItemClickListener {

        void onClick(Message message);
    }
}
