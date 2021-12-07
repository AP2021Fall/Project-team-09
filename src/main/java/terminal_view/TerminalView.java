package terminal_view;


public interface TerminalView {
    String text();

    default void show(){
        System.out.println(text());
        while(true){
            ArgumentManager argumentManager = ArgumentManager.readInput();
            parse(argumentManager);
        }
    }

    void parse(ArgumentManager input);
}
