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
 * выводит первый элемент коллекции
 */
public class Head extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private LabWork head;

    private final ServerExchangeChannel exchangeChannel;

    public Head(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel) {
        super("head", "вывести первый элемент коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.exchangeChannel = exchangeChannel;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(Serializable ent, SocketChannel clientChannel, Message message) {
        User user = message.getUser();
        if (SQLManager.authenticateUser(user.getUserName(), user.getPassword()) != 0) {
            LinkedList<LabWork> labworks = collectionManager.getCollection();
            if (!labworks.isEmpty())
                head = labworks.getFirst();

            exchangeChannel.sendMessage(clientChannel, new Message("head", head));
            return true;
        } else {
            return false;
        }
    }
}