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
 * удалить элемент из коллекции по его id
 */
public class RemoveById extends Command{
    private Console console;
    private CollectionManager collectionManager;
    private ServerExchangeChannel exchangeChannel;
    public RemoveById(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel) {
        super("remove_by_id id", "удалить элемент из коллекции по его id");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(Serializable id, SocketChannel clientChannel, Message message) {
        User user = message.getUser();
         LabWork labWork = collectionManager.byId((int) id);
        if (SQLManager.authenticateUser(user.getUserName(), user.getPassword()) != 0) {
            if (user.getUser_id() == collectionManager.byId((int) id).getUser_id()) {
                collectionManager.getCollection().remove(collectionManager.byId((int) id));
                return true;
            }
        }
        return false;
    }
}
