package server.commands;

import lib.Console;
import lib.ServerExchangeChannel;
import server.managers.CollectionManager;

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
    /**
     * выполняет команду
     *
     * @param arguments     - аргументы команды
     * @param clientChannel
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(Serializable id, SocketChannel clientChannel) {

        collectionManager.getCollection().remove(collectionManager.byId((int)id));
        return true;
    }
}
