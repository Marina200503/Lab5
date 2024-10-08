package server.commands;

import lib.Message;
import lib.ServerExchangeChannel;
import lib.models.User;
import server.managers.CollectionManager;
import lib.models.Difficulty;
import lib.Console;
import server.managers.SQLManager;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

/**
 * вывести количество элементов, значение поля difficulty которых больше заданного
 */
public class CountGreaterThanDifficulty extends Command{
    private final CollectionManager collectionManager;
    private final Console console;
    private final ServerExchangeChannel exchangeChannel;
    public CountGreaterThanDifficulty( Console console, CollectionManager collectionManager, ServerExchangeChannel exchangeChannel){
        super("count_greater_than_difficulty", "выводит количество элементов, значение поля difficulty которых больше заданного");
        this.collectionManager = collectionManager;
        this.console = console;
        this.exchangeChannel = exchangeChannel;

    }

    @Override
    public boolean execute(Serializable difficulty, SocketChannel clientChannel, Message message) {
        User user = message.getUser();
        if (SQLManager.authenticateUser(user.getUserName(), user.getPassword()) != 0) {
            Difficulty userDifficulty = (Difficulty) difficulty; // Считывание с консоли и проверка на валидность
            int k = collectionManager.countGreaterThanDifficulty(userDifficulty);

            exchangeChannel.sendMessage(clientChannel, new Message("count_greater_than_difficulty", k));
            return true;
        } else {
            return false;
        }
    }
}
