package controller;

public class Controller {
    private static Controller controller = null;

    public static Controller getInstance() {
        if(controller == null)
            controller = new Controller();
        return controller;
    }
}
