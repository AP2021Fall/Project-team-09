package exceptions;

public class IllegalCommandException extends RuntimeException {
    private final String arg;

    public IllegalCommandException(String arg) {
        super("--" + arg + " not found!");
        this.arg = arg;

    }

    public String getArg() {
        return arg;
    }
}
