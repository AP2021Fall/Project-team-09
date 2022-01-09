package terminal_view;

import exceptions.IllegalCommandException;
import utilities.ConsoleHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentManager {

    private static ConsoleHelper consoleHelper = new ConsoleHelper();

    private final String input;
    private HashMap<String, String> arguments;
    private HashSet<String> contains;
    private String command;

    public ArgumentManager(String input) {
        this.input = input.trim();
        contains = new HashSet<>();
        arguments = new HashMap<>();
        initialize();
    }

    private void initialize() {
        this.command = input.split("--")[0].trim();
        readContains();
        readArguments();
    }

    private void readArguments() {
        Pattern pattern;
        pattern = Pattern.compile("--[a-zA-Z0-9]+ (\".+\"|[^-\\s]+)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String arg = matcher.group(0); // --key value
            String key = arg.split(" ", 2)[0].substring(2);
            String value = arg.split(" ", 2)[1].replace("\"", "");
            arguments.put(key.toLowerCase(), value);
        }
    }

    private void readContains() {
        String[] args = input.split("\\s+");
        for (int i = 1; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                contains.add(args[i].substring(2).toLowerCase());
            }
        }
    }

    public String get(String input) {
        if (contains(input))
            return arguments.get(input);
        else {
            throw new IllegalCommandException(input);
        }
    }

    public boolean contains(String input) {
        return arguments.containsKey(input);
    }

    public static ArgumentManager readInput() {
        return new ArgumentManager(consoleHelper.read());
    }

    public String getCommand() {
        return command.toLowerCase();
    }

    public boolean isCommand(String input) {
        return this.command.equalsIgnoreCase(input);
    }
}
