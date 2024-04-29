package commands;
import managers.CollectionManager;
import models.LabWork;
import utility.Ask;
import utility.Console;
import java.util.ArrayList;

/**
 * удаляет из коллекции все элементы, превышающие заданный
 */
public class RemoveGreater extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveGreater(Console console, CollectionManager collectionManager){
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
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
        console.println("* Введите лабораторную работу для сравнения:");
        LabWork receivedLabWork = Ask.askLabWork(console, collectionManager);
        if (receivedLabWork != null && receivedLabWork.validate()) {
            int i = 0;
            ArrayList<LabWork> needRemove = new ArrayList<LabWork>();
            for (LabWork labWork : collectionManager.getCollection()) {
                if (labWork.getMinimalPoint() > receivedLabWork.getMinimalPoint()){
                    needRemove.add(labWork);
                    i++;
                }
            }
            for (LabWork labWork:needRemove)
                collectionManager.getCollection().remove(labWork);
            console.println("Удалено "+i+" лабораторных работ");
            return true;
        } else {
            console.printError("Поля лабораторной работы не валидны! Лабораторная работа не сравнима!");
            return false;
        }
    }
}
