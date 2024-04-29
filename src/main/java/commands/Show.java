package commands;

import managers.CollectionManager;
import utility.Console;
/**
 * вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class Show extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Show(Console console, CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;

    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        console.println(collectionManager);
        return true;
    }

}
