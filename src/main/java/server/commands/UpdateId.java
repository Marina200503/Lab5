package server.commands;
import lib.ServerExchangeChannel;
import server.managers.CollectionManager;
import lib.models.LabWork;
import lib.Console;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

/**
 * обновляет значение элемента коллекции, id которого равен заданному
 */
public class UpdateId extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final ServerExchangeChannel exchangeChannel;

    public UpdateId(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel) {
        super("update_id {element}", "обновить значение элемента коллекции, id которого равен заданному");
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
    public boolean execute(Serializable labWork, SocketChannel clientChannel) {
        LabWork receivedLabWork = (LabWork)labWork;
        if (receivedLabWork != null && receivedLabWork.validate()) {
            collectionManager.getCollection().remove(collectionManager.byId(receivedLabWork.getId()));
            receivedLabWork.setId(receivedLabWork.getId());
            collectionManager.add(receivedLabWork);
            console.println("Лабораторная работа успешно изменёна!");
            return true;
        } else {
            console.println("Поля лабораторной работы не валидны! Лабораторная работа не создана!");
            return false;
        }
    }
}
