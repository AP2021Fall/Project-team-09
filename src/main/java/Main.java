import controller.SaveAndLoadController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import terminal_view.LoginMenu;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    private double xOffset, yOffset;

    public static void main(String[] args) {
//        SaveAndLoadController.load();
        launch(args);

        new LoginMenu().show();

    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = new File("src/main/resources/ui/pages/auth.fxml").toURI().toURL();
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/home.fxml"));
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}
