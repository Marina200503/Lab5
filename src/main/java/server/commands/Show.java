package server.commands;

import lib.Message;
import lib.ServerExchangeChannel;
import server.managers.CollectionManager;
import lib.Console;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

/**
 * вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class Show extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final ServerExchangeChannel exchangeChannel;
    public Show(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
        this.exchangeChannel = exchangeChannel;

    }


    @Override
    public boolean execute(Serializable ent, SocketChannel clientChannel) {
        exchangeChannel.sendMessage(clientChannel, new Message("show", collectionManager.getCollection().toString()));
        return true;
    }

}
