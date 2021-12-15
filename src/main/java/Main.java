import controller.SaveAndLoadController;
import terminal_view.LoginMenu;

public class Main {
    public static void main(String[] args) {
        SaveAndLoadController.load();
        new LoginMenu().show();
    }
}
