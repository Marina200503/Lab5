package server.commands;

import lib.Message;
import lib.ServerExchangeChannel;
import lib.models.User;
import server.managers.CollectionManager;
import lib.Console;
import server.managers.SQLManager;

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
    public boolean execute(Serializable ent, SocketChannel clientChannel, Message message) {
        User user = message.getUser();
        if (SQLManager.authenticateUser(user.getUserName(), user.getPassword()) != 0) {
            exchangeChannel.sendMessage(clientChannel, new Message("show", collectionManager.getCollection().toString()));
            return true;
        }
        return false;
    }

}
