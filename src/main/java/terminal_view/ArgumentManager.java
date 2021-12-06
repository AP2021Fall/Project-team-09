package terminal_view;

import java.util.HashMap;
import java.util.Scanner;

public class ArgumentManager {
    private static Scanner sc = new Scanner(System.in);

    private final String input;
    private HashMap<String,String> arguments;
    private String command;

    public ArgumentManager(String input){
        this.input = input.trim();
        initialize();
    }

    private void initialize() {
        String [] args = input.split("\\s+");
        command = args[0];
    }

    public static void readInput(){

    }
}
