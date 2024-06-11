package server.commands;

import lib.Console;
import lib.ServerExchangeChannel;
import lib.models.LabWork;
import server.managers.CollectionManager;

import java.io.Serializable;
import java.nio.channels.SocketChannel;


/**
 *  добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
 */
public class AddIfIdMax extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final ServerExchangeChannel exchangeChannel;

    public AddIfIdMax(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.exchangeChannel = exchangeChannel;
    }

    /**
     * выполняет команду
     *
     * @param arguments     - аргументы команды
     * @param clientChannel
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(Serializable mLabWork, SocketChannel clientChannel) {
        LabWork receivedLabWork = (LabWork)mLabWork;
        ((LabWork)mLabWork).setId(collectionManager.getFreeId());
        if (receivedLabWork != null && receivedLabWork.validate()) {
            Long maxMinimalPoint = 0L;

            for (LabWork labWork : collectionManager.getCollection()) {
                if (maxMinimalPoint < labWork.getMinimalPoint()) {
                    maxMinimalPoint = labWork.getMinimalPoint();
                }
            }
            if (maxMinimalPoint == 0L) {
                return false;
            }
            if (receivedLabWork.getMinimalPoint() > maxMinimalPoint) {
                collectionManager.add(receivedLabWork);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}