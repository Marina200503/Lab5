package commands;
import models.Difficulty;
import models.LabWork;
import utility.*;
import managers.*;
import utility.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * вывести значение поля Difficulty всех элементов в порядке убывания
 */
public class PrintFieldDescendingDifficulty extends Command {
    private Console console;
    private CollectionManager collectionManager;
    public PrintFieldDescendingDifficulty(Console console, CollectionManager collectionManager){
        super("Print_field_descending_difficulty", "вывести значения поля difficulty всех элементов в порядке убывания");
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
        List<LabWork> allLabWorks = new ArrayList<>(collectionManager.getCollection());
        if (allLabWorks.isEmpty()) {
            console.println("Коллекция пуста!");
            return false;
        }
        console.println(collectionManager.getSortedByDifficulty());
        return true;
    }
}