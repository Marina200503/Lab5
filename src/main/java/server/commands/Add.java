package server.commands;

import client.utility.Ask;
import lib.Console;
import lib.ExchangeChannel;
import lib.Message;
import lib.ServerExchangeChannel;
import lib.models.LabWork;
import lib.models.User;
import server.managers.CollectionManager;
import server.managers.SQLManager;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

//добавление элемента

/**
 * добавляет новый элемент в коллекцию
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final ServerExchangeChannel exchangeChannel;

    public Add(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
        this.exchangeChannel = exchangeChannel;
    }


    @Override
    public boolean execute(Serializable labwork, SocketChannel clientChannel, Message message) {
        User user = message.getUser();
        if (SQLManager.authenticateUser(user.getUserName(), user.getPassword())!= 0){
        collectionManager.add((LabWork)labwork);
        return true;}
        else{
            return false;
        }
    }
}