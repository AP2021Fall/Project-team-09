package server.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment implements Serializable {

    private String user;
    private String message;
    private LocalDateTime dateTime;

    public Comment(String user, String message) {
        this.user = user;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

    public User getUser() {
        return User.getUser(this.user);
    }

    public void setUser(User user) {
        this.user = user.getUsername();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");
        return this.dateTime.format(formatter);
    }

    @Override
    public String toString() {
        return this.user + ": " + this.message + " - " + this.dateTime;
    }
}
