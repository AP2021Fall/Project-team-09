package terminal_view;

import com.sun.org.apache.xpath.internal.Arg;

public interface TerminalView {
    String text();

    default void show(){
        System.out.println(text());
        while(true){
            ArgumentManager argumentManager = ArgumentManager.readInput();

        }
    }

    void parse(ArgumentManager input);
}
