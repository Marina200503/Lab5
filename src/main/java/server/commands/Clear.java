package server.commands;
import lib.Console;

import lib.ServerExchangeChannel;
import server.managers.CollectionManager;

import java.io.Serializable;
import java.nio.channels.SocketChannel;


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
    /**
     * выполняет команду
     *
     * @param arguments     - аргументы команды
     * @param clientChannel
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(Serializable entity, SocketChannel clientChannel) {
        collectionManager.getCollection().clear();
        return true;
    }
}