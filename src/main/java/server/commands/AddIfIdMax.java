package server.commands;

import lib.Console;
import lib.Message;
import lib.ServerExchangeChannel;
import lib.models.LabWork;
import lib.models.User;
import server.managers.CollectionManager;
import server.managers.SQLManager;

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


    @Override
    public boolean execute(Serializable mLabWork, SocketChannel clientChannel, Message message) {
        LabWork receivedLabWork = (LabWork) mLabWork;
        User user = message.getUser();
        if (SQLManager.authenticateUser(user.getUserName(), user.getPassword()) != 0) {
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
        else{return false;}

    }
}