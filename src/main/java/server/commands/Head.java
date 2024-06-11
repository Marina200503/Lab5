package server.commands;

import lib.Console;
import lib.Message;
import lib.ServerExchangeChannel;
import lib.models.LabWork;
import server.managers.CollectionManager;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

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
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(Serializable ent, SocketChannel clientChannel) {
        head = collectionManager.getCollection().getFirst();
        exchangeChannel.sendMessage(clientChannel, new Message("head", head));
        return true;
    }
}