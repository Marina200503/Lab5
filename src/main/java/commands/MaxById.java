package commands;

import managers.*;
import models.*;
import utility.*;

/**
 * выводит любой объект из коллекции, значение поля id которого является максимальным
 */
public class MaxById extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public MaxById(Console console, CollectionManager collectionManager){
        super("min_by_minimal_point", "вывести любой объект из коллекции, значение поля Id которого является максимальным");
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
        LabWork labWorkWithMaxId = null;
        for (LabWork labWork: collectionManager.getCollection()){
            if (labWorkWithMaxId == null || labWorkWithMaxId.getId() > labWork.getId()) {
                labWorkWithMaxId = labWork;
            }
        }
        if (labWorkWithMaxId == null) {
            console.println("Коллекция пуста!");
            return false;
        }
        console.println(labWorkWithMaxId);
        return true;
    }
}