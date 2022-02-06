package server.controller;

import server.model.Task;
import server.model.User;

public class CalendarMenuController {

    private final String WARN_INVALID_OPERATION =
            "Invalid operation!";

    private final String DEADLINES =
            "deadlines";

    private static CalendarMenuController calendarMenuController = null;

    public static CalendarMenuController getInstance() {
        if (calendarMenuController == null)
            calendarMenuController = new CalendarMenuController();
        return calendarMenuController;
    }

    public MResponse getCalendar(String calendar) {
        if (!calendar.equalsIgnoreCase(DEADLINES))
            return new MResponse(WARN_INVALID_OPERATION, false);

        User user = UserController.getLoggedUser();

        return new MResponse(Task.getDeadlinesFormatted(user), true, Task.getDeadlines(user));
    }
}
