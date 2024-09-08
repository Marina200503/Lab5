package server.commands;

import lib.Message;
import lib.ServerExchangeChannel;
import lib.models.User;
import server.managers.CollectionManager;
import lib.models.LabWork;
import lib.Console;
import server.managers.SQLManager;

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


    @Override
    public boolean execute(Serializable labWork, SocketChannel clientChannel, Message message) {
        User user = message.getUser();
        if (SQLManager.authenticateUser(user.getUserName(), user.getPassword()) != 0) {
            LabWork receivedLabWork = (LabWork) labWork;
            if (user.getUser_id() == collectionManager.byId(receivedLabWork.getId()).getUser_id()) {
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
        return false;
    }
}
