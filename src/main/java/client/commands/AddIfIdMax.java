package client.commands;

import client.utility.Ask;
import lib.Console;
import lib.ExchangeChannel;
import lib.Message;
import lib.models.LabWork;


/**
 *  добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
 */
public class AddIfIdMax extends Command {
    private final Console console;
    private final ExchangeChannel exchangeChannel;

    public AddIfIdMax(Console console, ExchangeChannel exchangeChannel) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.console = console;
        this.exchangeChannel = exchangeChannel;
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
        LabWork receivedLabWork = Ask.askLabWork(console);
        if (receivedLabWork != null && receivedLabWork.validate()) {

            exchangeChannel.sendMesssage(new Message("add_if_max", receivedLabWork));
            return true;
//            Long maxMinimalPoint = 0L;
//
//            for (LabWork labWork : collectionManager.getCollection()) {
//                if (maxMinimalPoint < labWork.getMinimalPoint()) {
//                    maxMinimalPoint = labWork.getMinimalPoint();
//                }
//            }
//            if (maxMinimalPoint == 0L) {
//                console.println("Коллекция пуста!");
//                return false;
//            }
//            if (receivedLabWork.getMinimalPoint() > maxMinimalPoint) {
//                collectionManager.add(receivedLabWork);
//                console.println("Лабораторная работа успешно добавлена!");
//                return true;
//            } else {
//                console.println("Лабораторная работа не добавлена, так как ее значение не превышает значение наибольшего элемента этой коллекции");
//                return false;
//            }
        }
        console.printError("Поля лабораторной работы не валидны! Лабораторная работа не создана!");
        return false;
    }

}