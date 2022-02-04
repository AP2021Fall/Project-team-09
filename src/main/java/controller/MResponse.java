package controller;

public class MResponse {
    private String message;
    private boolean success;
    private Object object;

    public MResponse(String message, boolean success) {
        this(message, success, null);
    }

    public MResponse(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object = object;
    }


    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
