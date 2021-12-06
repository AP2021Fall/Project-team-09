package terminal_view;

import controller.EnvironmentVariables;

public class LoginMenu implements TerminalView{

    @Override
    public String text() {
        return "Welcome to Jira " + EnvironmentVariables.getInstance().getString("VERSION") + "!";
    }

    @Override
    public void parse(ArgumentManager input) {
        if(input.getCommand().equals("user create")){

        }
    }
}
