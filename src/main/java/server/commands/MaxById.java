package server.commands;

import lib.Console;
import lib.Message;
import lib.ServerExchangeChannel;
import lib.models.LabWork;
import server.managers.CollectionManager;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

/**
 * выводит любой объект из коллекции, значение поля id которого является максимальным
 */
public class MaxById extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final ServerExchangeChannel exchangeChannel;
    public MaxById(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel){
        super("max_by_id", "вывести любой объект из коллекции, значение поля Id которого является максимальным");
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
    public boolean execute(Serializable ent, SocketChannel clientChannel) {
        LabWork labWorkWithMaxId = null;
        for (LabWork labWork: collectionManager.getCollection()){
            if (labWorkWithMaxId == null || labWorkWithMaxId.getId() < labWork.getId()) {
                labWorkWithMaxId = labWork;
            }
        }
        if (labWorkWithMaxId == null) {
            console.println("Коллекция пуста!");
            return false;
        }
        exchangeChannel.sendMessage(clientChannel, new Message("max_by_id", labWorkWithMaxId));
        return true;
    }
}