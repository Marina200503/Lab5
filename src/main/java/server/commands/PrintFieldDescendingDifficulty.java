package server.commands;

import lib.Console;
import lib.Message;
import lib.ServerExchangeChannel;
import lib.models.User;
import server.managers.CollectionManager;
import server.managers.SQLManager;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

/**
 * вывести значение поля Difficulty всех элементов в порядке убывания
 */
public class PrintFieldDescendingDifficulty extends Command {
    private Console console;
    private CollectionManager collectionManager;
    private final ServerExchangeChannel exchangeChannel;
    public PrintFieldDescendingDifficulty(Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel){
        super("print_field_descending_difficulty", "вывести значения поля difficulty всех элементов в порядке убывания");
        this.console = console;
        this.collectionManager = collectionManager;
        this.exchangeChannel = exchangeChannel;
    }

    @Override
    public boolean execute(Serializable ent, SocketChannel clientChannel, Message message) {
        User user = message.getUser();
        if (SQLManager.authenticateUser(user.getUserName(), user.getPassword()) != 0) {

            exchangeChannel.sendMessage(clientChannel, new Message("print_field_descending_difficulty", (Serializable) collectionManager.getSortedByDifficulty()));
            return true;
        } else {
            return false;
        }
    }
}