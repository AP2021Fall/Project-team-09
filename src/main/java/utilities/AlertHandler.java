package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class AlertHandler {

    private Alert.AlertType type;
    private String message;

    public AlertHandler(Alert.AlertType type, String message) {
        this.type = type;
        this.message = message;
    }

    public void ShowAlert() {
        Alert alert = new Alert(this.type,
                this.message,
                ButtonType.OK);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.showAndWait();
    }

    public Alert ConfirmationAlert() {
        Alert alert = new Alert(type,
                message,
                ButtonType.YES, ButtonType.NO);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.showAndWait();
        return alert;
    }
}
