package terminal_view;

import controller.CalendarMenuController;
import controller.MResponse;
import exceptions.IllegalCommandException;
import utilities.ConsoleHelper;

public class CalendarMenu implements TerminalView {

    private final String WELCOME_MESSAGE =
            "Welcome to calendar menu";

    private final String SHOW_DEADLINES =
            "calendar --show deadlines";

    private final String CALENDAR =
            "calendar";
    private final String SHOW =
            "show";

    @Override
    public String text() {
        return WELCOME_MESSAGE;
    }

    @Override
    public void showHelp() {
        ConsoleHelper.getInstance().println(SHOW_DEADLINES);
    }

    @Override
    public void parse(ArgumentManager input) {
        if (input.isCommandFollowArg(CALENDAR, SHOW)) {
            showCalendar(input);
        }
    }

    private void showCalendar(ArgumentManager input) {
        try {
            MResponse MResponse = CalendarMenuController
                    .getInstance().getCalendar(input.get(SHOW));
            ConsoleHelper.getInstance().println(MResponse.getMessage());
        } catch (IllegalCommandException e) {
            ConsoleHelper.getInstance().println(e.getMessage());
        }
    }
}
