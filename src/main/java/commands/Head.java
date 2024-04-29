package commands;
import managers.*;
import models.*;
import utility.*;

import java.util.LinkedList;

/**
 * выводит первый элемент коллекции
 */
public class Head extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private LabWork head;

    public Head(Console console, CollectionManager collectionManager) {
        super("add {element}", "вывести первый элемент коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        head = collectionManager.getCollection().getFirst();
        console.println("Первый элемент коллекции: " + head);
        return true;
    }
}