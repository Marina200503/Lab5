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
import java.util.LinkedList;


/**
 * очищает коллекцию
 */
public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final ServerExchangeChannel exchangeChannel;

    public Clear(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
        this.exchangeChannel = exchangeChannel;
    }

    @Override
    public boolean execute(Serializable entity, SocketChannel clientChannel, Message message) {
        User user = message.getUser();
        LinkedList<LabWork> labworks = collectionManager.getCollection();
        for (LabWork labWork : labworks) {
            if (SQLManager.authenticateUser(user.getUserName(), user.getPassword()) != 0) {
                if (user.getUser_id() == (labWork).getUser_id()) {
                    labworks.remove(labWork);
                }
            }
        }
        return true;
    }
}