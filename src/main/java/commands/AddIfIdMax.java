package commands;
import managers.*;
import models.*;
import utility.*;

/**
 *  добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
 */
public class AddIfIdMax extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public AddIfIdMax(Console console, CollectionManager collectionManager) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
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
        console.println("* Лабораторная работа для сравнения с наибольшим:");
        LabWork receivedLabWork = Ask.askLabWork(console, collectionManager);
        if (receivedLabWork != null && receivedLabWork.validate()) {
            Long maxMinimalPoint = 0L;

            for (LabWork labWork : collectionManager.getCollection()) {
                if (maxMinimalPoint < labWork.getMinimalPoint()) {
                    maxMinimalPoint = labWork.getMinimalPoint();
                }
            }
            if (maxMinimalPoint == 0L) {
                console.println("Коллекция пуста!");
                return false;
            }
            if (receivedLabWork.getMinimalPoint() > maxMinimalPoint) {
                collectionManager.add(receivedLabWork);
                console.println("Лабораторная работа успешно добавлена!");
                return true;
            } else {
                console.println("Лабораторная работа не добавлена, так как ее значение не превышает значение наибольшего элемента этой коллекции");
                return false;
            }
        }
        console.printError("Поля лабораторной работы не валидны! Лабораторная работа не создана!");
        return false;
    }

}