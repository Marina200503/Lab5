package client.commands;

import client.utility.Ask;
import lib.Console;
import lib.ExchangeChannel;
import lib.Message;
import lib.models.LabWork;

import java.util.ArrayList;

/**
 * удаляет из коллекции все элементы, превышающие заданный
 */
public class RemoveGreater extends Command {
    private final Console console;
    private final ExchangeChannel exchangeChannel;

    public RemoveGreater(Console console, ExchangeChannel exchangeChannel){
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
        this.console = console;
        this.exchangeChannel = exchangeChannel;
    }
    /**
     * выполняет команду
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        console.println("* Введите лабораторную работу для сравнения:");
        LabWork receivedLabWork = Ask.askLabWork(console);




        if (receivedLabWork != null && receivedLabWork.validate()) {
            int i = 0;
            ArrayList<LabWork> needRemove = new ArrayList<LabWork>();
            exchangeChannel.sendMesssage(new Message("remove_greater", receivedLabWork));
//            for (LabWork labWork : collectionManager.getCollection()) {
//                if (labWork.getMinimalPoint() > receivedLabWork.getMinimalPoint()){
//                    needRemove.add(labWork);
//                    i++;
//                }
//            }
//            for (LabWork labWork:needRemove)
//                collectionManager.getCollection().remove(labWork);
//            console.println("Удалено "+i+" лабораторных работ");
//            return true;
//        } else {
//            console.printError("Поля лабораторной работы не валидны! Лабораторная работа не сравнима!");
//            return false;
        }
        return true;
    }
}
