package commands;
import managers.*;
import models.*;
import utility.*;

//добавление элемента

/**
 * добавляет новый элемент в коллекцию
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        console.println("* Создание новой лабораторной работы:");
        LabWork d = Ask.askLabWork(console, collectionManager);

        if (d != null && d.validate()) {
            collectionManager.add(d);
            console.println("Лабораторная работа успешно добавлена!");
            return true;
        } else {
            console.printError("Поля лабораторной работы не валидны! Лабораторная работа не создана!");
            return false;
        }}
}