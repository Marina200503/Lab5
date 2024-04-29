package commands;

import managers.*;
import utility.Console;
/**
 * удалить элемент из коллекции по его id
 */
public class RemoveById extends Command{
    private Console console;
    private CollectionManager collectionManager;
    public RemoveById(Console console, CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    /**
     * выполняет команду
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        int id = -1;
        try { id = Integer.parseInt(arguments[1].trim()); } catch (NumberFormatException e) { console.println("ID не распознан"); return false; }

        if (collectionManager.byId(id) == null) {
            console.println("Несуществующий ID");
            return false;
        }
        collectionManager.getCollection().remove(collectionManager.byId(id));
        console.println("Лабораторная работа успешно удалёна!");
        return true;
    }
}
