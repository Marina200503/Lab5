package commands;
import managers.*;
import utility.*;

/**
 * очищает коллекцию
 */
public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    /**
     * выполняет команду
     *
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        collectionManager.getCollection().clear();
        console.println("Коллекция очищена!");
        return true;
    }
}