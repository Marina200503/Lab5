package commands;

import managers.CollectionManager;
import models.Difficulty;
import models.LabWork;
import utility.Ask;
import utility.Console;

import java.util.Collection;

/**
 * вывести количество элементов, значение поля difficulty которых больше заданного
 */
public class CountGreaterThanDifficulty extends Command{
    private final CollectionManager collectionManager;
    private final Console console;
    public CountGreaterThanDifficulty( Console console, CollectionManager collectionManager){
        super("count_geater_than_difficulty", "выводит количество элементов, значение поля difficulty которых больше заданного");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public boolean execute(String[] args) {
        console.println("* Введите значение Difficulty для сравнения:");
        Difficulty uesrDifficulty = Ask.askDifficulty(console);// Считывание с консоли и проверка на валидность
        int k = 0;
        for(LabWork labWork : collectionManager.getCollection()){
            if (labWork.getDifficulty().getValue() > uesrDifficulty.getValue()){
                k++;
            }
        }
        console.print("Количество элементов, значение поля difficulty которых больше заданного: " + k);
        return true;
    }
}
