package ui;

import controller.Response;
import controller.SaveAndLoadController;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextInputControl;
import utilities.AlertHandler;

import java.time.LocalDate;
import java.util.Date;

public interface GUI {

    default void setText(TextInputControl inputControl, String string) {
        if (inputControl == null)
            return;
        String outPut = "";

        if (string != null && !string.isEmpty())
            outPut = string;
        inputControl.setText(outPut.trim());
    }

    default void setDate(DatePicker date, LocalDate localDate) {
        if (date == null)
            return;

        date.setValue(localDate);
    }

    default String getValue(TextInputControl inputControl) {
        return inputControl.getText().trim();
    }

    default LocalDate getDate(DatePicker datePicker) {
        if (datePicker == null)
            return null;
        return datePicker.getValue();
    }

    default String getComboValue(ComboBox<String> comboBox) {
        return comboBox.getSelectionModel().getSelectedItem();
    }

    default void clearFields(TextInputControl... inputControls) {
        for (TextInputControl inputControl : inputControls)
            if (inputControl != null)
                inputControl.clear();
    }

    default void showSuccessAlert(String message) {
        new AlertHandler(Alert.AlertType.INFORMATION, message.trim()).ShowAlert();
    }

    default void showWarning(String message) {
        new AlertHandler(Alert.AlertType.ERROR, message.trim()).ShowAlert();
    }

    default void showResponse(Response response) {
        if (response.isSuccess())
            showSuccessAlert(response.getMessage());
        else
            showWarning(response.getMessage());
    }

    default void save() {
        SaveAndLoadController.save();
    }

}
