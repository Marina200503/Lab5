package client.commands;
import lib.Console;

/**
 * завершить программу (без сохранения в файл)
 */
public class Exit extends Command {
    private final Console console;
    public Exit(Console console){
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
    }

    /**
     * выполняет команду
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        console.println("Завершение выполнения...");
        return true;
    }
}
