package terminal_view;

public class TeamMenu implements TerminalView{
    @Override
    public String text() {
        return "Welcome to team menu";
    }

    @Override
    public void showHelp() {
        System.out.println("Help of team menu");
    }

    @Override
    public void parse(ArgumentManager input) {
    }
}
