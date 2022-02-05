package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

    private static int ID_COUNTER = 1;

    private int id;
    private String sender;
    private String body;
    private LocalDateTime dateTime;

    public Message(int id, String sender, String body, LocalDateTime localDateTime) {
        this.id = id;
        this.sender = sender;
        this.body = body;
        this.dateTime = localDateTime;
    }

    public Message(String sender, String body) {
        this.id = ID_COUNTER++;
        this.sender = sender;
        this.body = body;
        this.dateTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return User.getUser(this.sender);
    }

    public void setSender(User sender) {
        this.sender = sender.getUsername();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static int getIdCounter() {
        return ID_COUNTER;
    }

    public static void setIdCounter(int idCounter) {
        ID_COUNTER = idCounter;
    }
}
