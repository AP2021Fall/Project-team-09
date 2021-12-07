package terminal_view;

public class ProfileMenu implements TerminalView{
    @Override
    public String text() {
        return "Welcome to profile menu";
    }

    @Override
    public void showHelp() {
        System.out.println("Help of profile menu");
    }

    @Override
    public void parse(ArgumentManager input) {

    }
}
