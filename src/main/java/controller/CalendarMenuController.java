package controller;

import model.MRequest;

public class CalendarMenuController {

    private final String GET_CALENDAR_PATH =
            "/calendar";

    private static CalendarMenuController calendarMenuController = null;

    public static CalendarMenuController getInstance() {
        if (calendarMenuController == null)
            calendarMenuController = new CalendarMenuController();
        return calendarMenuController;
    }

    public MResponse getCalendar(String calendar) {
        return new MRequest()
                .setPath(GET_CALENDAR_PATH)
                .addArg("calendar", calendar)
                .get();
    }
}
